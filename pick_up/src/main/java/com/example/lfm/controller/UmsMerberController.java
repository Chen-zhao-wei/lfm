package com.example.lfm.controller;

import com.example.lfm.entity.File;
import com.example.lfm.entity.SysStudent;
import com.example.lfm.pojo.phoneCode;
import com.example.lfm.utils.FastDFSUtils;
import com.example.lfm.utils.RedisCache;
import com.example.lfm.service.UserMerberService;
import com.example.lfm.utils.ReturnMessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.lfm.utils.ReturnMessage;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@RestController
@RequestMapping("/app")
@Api(value = "学生信息控制器")
public class UmsMerberController {
    private static Logger logger = LoggerFactory.getLogger(UmsMerberController.class);
    //短信平台相关参数
    //这个不用改
    private String apiUrl = "https://sms_developer.zhenzikj.com";
    //榛子云系统上获取
    private String appId = "100862";
    private String appSecret = "62358d10-bc0e-4152-a52c-578a8debc9b9";

    @Autowired
    private UserMerberService memberService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private FastDFSUtils fastDFSUtils;
    /**
     * 用户注册
     * 通过用户名密码注册
     */
    @ApiOperation("用户名注册")
    @RequestMapping(value = "/register1", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMessage<Object> register1(@RequestBody SysStudent student) {
        logger.info(student+"我是user:");
        return memberService.register1(student);
    }

    /****
     * 用户注册
     * 通过手机短信注册
     */
    @ApiOperation("短信注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMessage<Object> register(@RequestBody phoneCode phoneCode) {
        return memberService.register(phoneCode);
    }

    /*****
     * 获取验证码
     * 60秒后验证码会在redis中消失
     */
    @RequestMapping(value = "/getAuthCode", method = RequestMethod.GET)
    @ResponseBody
    public ReturnMessage<Object> getAuthCode(@RequestParam String telephone) {
        return memberService.generateAuthCode(telephone);
    }

    @ApiOperation("用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMessage<Object> login(@RequestBody SysStudent student) {
        return memberService.login(student);
    }

    @ApiOperation("修改个人信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMessage<Object> update(@RequestBody SysStudent student) {

        return memberService.update(student);
    }

    @ApiOperation("获取个人信息")
    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    @ResponseBody
    public ReturnMessage<Object> selectBykey(HttpServletRequest request) {
        return memberService.selectBykey(request);
    }

    @ApiOperation("获取学校信息")
    @RequestMapping(value = "/getSchool", method = RequestMethod.GET)
    @ResponseBody
    public ReturnMessage<Object> getSchool() {
        return memberService.getSchool();
    }

    /**
     * 上传文件接口
     */
    @ApiOperation("头像上传 ")
    @PostMapping("/uploadAvatar")
    public ReturnMessage<Object> uploadAvatar(@RequestParam("file") MultipartFile file) throws IOException
    {
        if (!file.isEmpty())
        {
            String fileUrl  = fastDFSUtils.uploadAvatar(file);
            return ReturnMessageUtil.sucess(fileUrl);
        }
        return ReturnMessageUtil.error(0,"文件不存在");
    }
}

