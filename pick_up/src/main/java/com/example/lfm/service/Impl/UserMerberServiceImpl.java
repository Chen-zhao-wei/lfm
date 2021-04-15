package com.example.lfm.service.Impl;


import com.example.lfm.dao.SysSchoolMapper;
import com.example.lfm.dao.SysStudentMapper;
import com.example.lfm.entity.SysSchool;
import com.example.lfm.entity.SysStudent;
import com.example.lfm.pojo.phoneCode;
import com.example.lfm.service.UserMerberService;
import com.example.lfm.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserMerberServiceImpl implements UserMerberService {
    private static Logger logger = LoggerFactory.getLogger(UserMerberService.class);
    @Autowired
    private SysStudentMapper studentMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SysSchoolMapper schoolMapper;
    /***
     * 注入redis模版
     */
    @Autowired
    private RedisCache redisCache;
    /****
     * 注入配置文件数据
     */
//    @Value("${redis.key.prefix.authCode}")
//    private String REDIS_KEY_PREFIX_AUTH_CODE;
//    //过期时间60秒
//    @Value("${redis.key.expire.authCode}")
//    private Long AUTH_CODE_EXPIRE_SECONDS;


    @Override
    public ReturnMessage<Object> generateAuthCode(String telephone) {
        String expression = "((^((0\\d{2,3})-)(\\d{7,8})(-(\\d{3,}))?$)|(^((13[0-9])|(15[^4,\\D])|(18[0-9])|(14[5,7])|(17[0,1,3,5-8]))\\d{8}$))";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(telephone);
        if (!matcher.matches()){
            return ReturnMessageUtil.error(0, "手机号格式不正确！");
        }
        if(studentMapper.selectByPhone(telephone)!=null){
            return ReturnMessageUtil.error(0, "该手机号已注册过了！");
        }
        String code = NumberUtils.generateCode(6);
//        加入redis ，设置5分钟过期
        redisCache.setCacheObject(telephone, code, 1, TimeUnit.MINUTES);
        return ReturnMessageUtil.sucess(code);
    }

    @Override
    @Transactional
    public ReturnMessage<Object> register(phoneCode phoneCode) {
        String telephone=phoneCode.getTelephone();
        String authCode= phoneCode.getCode();
        /****
         * 手机号和注册码前台用户发送
         */
        String expression = "((^((0\\d{2,3})-)(\\d{7,8})(-(\\d{3,}))?$)|(^((13[0-9])|(15[^4,\\D])|(18[0-9])|(14[5,7])|(17[0,1,3,5-8]))\\d{8}$))";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(telephone);
        if (!matcher.matches()){
            return ReturnMessageUtil.error(0, "手机号格式不正确！");
        }
        if(studentMapper.selectByPhone(telephone)!=null){
            return ReturnMessageUtil.error(0, "该手机号已注册过了！");
        }
        String redisToken = redisCache.getCacheObject(telephone);
        if(redisToken == null){
            logger.error("##########当前号码在redis中不存在,telephone:{}",telephone);
            return ReturnMessageUtil.error(0, "当前号码在redis中不存在！");
        }
        logger.info(authCode+redisCache.getCacheObject(telephone)+"=========");
        if(!redisCache.getCacheObject(telephone).equals(authCode)){
            return ReturnMessageUtil.error(0, "验证码与手机号不匹配！");
        }

        Long expire = stringRedisTemplate.getExpire(telephone);
        logger.info(expire+"++++++++++=======");
        if(expire <= 0){
            logger.error("##########当前用户token已失效,telephone:{}",telephone);
            return ReturnMessageUtil.error(0, "当前号码token已失效！");
        }
        if (StringUtils.isEmpty(phoneCode.getSchoolId())||StringUtils.isEmpty(phoneCode.getSchoolId())){
            return ReturnMessageUtil.error(0,"必填项不能为空");
        }
        if(!StringUtils.isEmpty(studentMapper.selectBySchSN(phoneCode.getSchoolId(),phoneCode.getStudentNumber()))){
            return ReturnMessageUtil.error(0,"该学校已存在该学号");
        }
        String name=telephone;
        while(studentMapper.selectByName(name)!=null){
            name=name+"#";
        }
        SysStudent student=new SysStudent();
        student.setStudentName(name);
        student.setPhoneNumber(telephone);
        student.setPassword("123456");
        student.setSchoolId(phoneCode.getSchoolId());
        student.setStudentNumber(phoneCode.getStudentNumber());
        if(studentMapper.insert(student)==0){
            return ReturnMessageUtil.error(0, "注册失败！");
        }
        TokenUtil tokenUtil=new TokenUtil();
        String token = null;
        try {
            token = JwtTokenUtils.createToken(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info(token+"token");
        if (token != null) {
            redisCache.setCacheObject(name,token);
            redisCache.expire(name,60000);
            return ReturnMessageUtil.sucess(token);
        } else {
            return ReturnMessageUtil.error(0, "获取token失败！");
        }
    }

    @Override
    public ReturnMessage<Object> register1(SysStudent student) {
        if(student.getStudentName()==null||studentMapper.selectByName(student.getStudentName())!=null){
            return ReturnMessageUtil.error(0, "用户名已存在！");
        }
        if (StringUtils.isEmpty(student.getStudentNumber())||StringUtils.isEmpty(student.getSchoolId())){
            return ReturnMessageUtil.error(0,"必填项不能为空");
        }
        if(studentMapper.insert(student)==0){
            return ReturnMessageUtil.error(0, "注册失败！");
        }
        TokenUtil tokenUtil=new TokenUtil();
        SysStudent student1=studentMapper.selectByName(student.getStudentName());
        String token = null;
        try {
            token = JwtTokenUtils.createToken(student.getStudentName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (token != null) {

            redisCache.setCacheObject(student1.getStudentName(),token);
            redisCache.expire(student1.getStudentName(),60000);
            return ReturnMessageUtil.sucess(token);
        } else {
            throw new SbException(100, "获取token失败！");
        }

    }

    @Override
    public ReturnMessage<Object> login(SysStudent student) {
        logger.info(student.getStudentName()+"=========");
        if (student.getStudentName()==null||studentMapper.selectByName(student.getStudentName())==null){
            return ReturnMessageUtil.error(0, "该用户名不存在！");
        }
        SysStudent student1=studentMapper.selectByName(student.getStudentName());
        logger.info("student.getPassword()+++++"+student.getPassword());
        logger.info("student1.getPassword()+++++"+student1.getPassword());
        if(student.getPassword()==null||!(student1.getPassword().equals(student.getPassword()))){
            return ReturnMessageUtil.error(0, "密码错误！");
        }

        String token = null;
        try {
            token = JwtTokenUtils.createToken(student1.getStudentName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (token != null) {
            redisCache.setCacheObject(student1.getStudentName(),token,30,TimeUnit.MINUTES);
            logger.info(stringRedisTemplate.getExpire(student1.getStudentName())+"=============");
            return ReturnMessageUtil.sucess(token);
        } else {
            return ReturnMessageUtil.error(0, "获取token失败！");
        }
    }

    @Override
    public ReturnMessage<Object> update(SysStudent student) {
        if(student==null||student.getStudentName()==null||student.getPassword()==null||student.getPhoneNumber()==null){
        //关键数据不可为空
            logger.info(student+"========");
            return ReturnMessageUtil.error(0, "必填项不可为空！");
        }
        //修改的用户名必须保持唯一：
        // 1.用户名是否有改动
        // 2.改动之后数据库中是否已经存在
        if(!student.getStudentName().equals(studentMapper.selectByPrimaryKey(student.getStudentId()).getStudentName())&&studentMapper.selectByName(student.getStudentName())!=null){
            logger.info(student+"=========");
            return ReturnMessageUtil.error(0, "用户名已存在！");
        }

        String expression = "((^((0\\d{2,3})-)(\\d{7,8})(-(\\d{3,}))?$)|(^((13[0-9])|(15[^4,\\D])|(18[0-9])|(14[5,7])|(17[0,1,3,5-8]))\\d{8}$))";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(student.getPhoneNumber());
        if (!matcher.matches()){
            return ReturnMessageUtil.error(0, "手机号格式不正确！");
        }
        if(studentMapper.updateByPrimaryKey(student)==1){
            return ReturnMessageUtil.sucess();
        }
        else
        return ReturnMessageUtil.error(0, "更新失败！");
    }

    @Override
    public ReturnMessage<Object> selectBykey(HttpServletRequest request) {
        String token = request.getHeader("x-auth-token");
        if(token==null){
            return ReturnMessageUtil.error(0, "获取token失败");
        }
        String studentName= JwtTokenUtils.getStudentName(token);
        SysStudent sysStudent=this.studentMapper.selectByName(studentName);
        if (StringUtils.isEmpty(sysStudent)) {
            return ReturnMessageUtil.error(0, "该用户不存在");
        }
        return ReturnMessageUtil.sucess(sysStudent);
    }

    @Override
    public ReturnMessage<Object> getSchool() {
        List<SysSchool> schools=schoolMapper.selectAll();
        if(StringUtils.isEmpty(schools)){
            return ReturnMessageUtil.error(0,"暂无学校信息");
        }
        return ReturnMessageUtil.sucess(schools);
    }
}
