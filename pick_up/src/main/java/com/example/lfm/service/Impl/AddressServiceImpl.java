package com.example.lfm.service.Impl;

import com.example.lfm.dao.SysAddressMapper;
import com.example.lfm.dao.SysStudentMapper;
import com.example.lfm.entity.SysAddress;
import com.example.lfm.entity.SysStudent;
import com.example.lfm.service.AddressService;
import com.example.lfm.service.UserMerberService;
import com.example.lfm.utils.JwtTokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.example.lfm.utils.ReturnMessage;
import com.example.lfm.utils.ReturnMessageUtil;

import javax.servlet.http.HttpServletRequest;

@Service
public class AddressServiceImpl implements AddressService {
    private static Logger logger = LoggerFactory.getLogger(UserMerberService.class);
    @Autowired
    private SysAddressMapper addressMapper;
    @Autowired
    private SysStudentMapper studentMapper;

    @Override
    @Transactional
    public ReturnMessage<Object> inputaddress(SysAddress address) {
        if(StringUtils.isEmpty(address.getStudentId())||StringUtils.isEmpty(address.getStudentId())|| StringUtils.isEmpty(address.getAddress())||StringUtils.isEmpty(address.getTakeName())||StringUtils.isEmpty(address.getTakeNumber())||StringUtils.isEmpty(address.getDefaultFlag())){
            return ReturnMessageUtil.error(0, "必填项不可为空！");
        }
        if(address.getDefaultFlag().equals("0")){
            if(!StringUtils.isEmpty(addressMapper.SelBydefault(address.getStudentId(),"0"))){
                SysAddress address1=addressMapper.SelBydefault(address.getStudentId(),"0");
                address1.setDefaultFlag("1");
                addressMapper.updateByPrimaryKey(address1);
            }
        }

        if(addressMapper.insert(address)==1){
            return ReturnMessageUtil.sucess();
        }else   return ReturnMessageUtil.error(0,"添加失败！");
    }

    @Override
    public ReturnMessage<Object> delddress(Long addressId) {
        if(StringUtils.isEmpty(addressId)||addressMapper.selectByPrimaryKey(addressId)!=null){
            return ReturnMessageUtil.error(0,"该地址信息不存在！");
        }
        if(addressMapper.deleteByPrimaryKey(addressId)==1){
            return ReturnMessageUtil.sucess();
        }else return ReturnMessageUtil.error(0,"删除地址失败");

    }

    @Override
    public ReturnMessage<Object> updateaddress(SysAddress address) {
        if(StringUtils.isEmpty(address.getStudentId())||StringUtils.isEmpty(address.getStudentId())|| StringUtils.isEmpty(address.getAddress())||StringUtils.isEmpty(address.getTakeName())||StringUtils.isEmpty(address.getTakeNumber())){
            return ReturnMessageUtil.error(0, "必填项不可为空！");
        }
        if(addressMapper.updateByPrimaryKey(address)==1){
            return ReturnMessageUtil.sucess();
        }else     return ReturnMessageUtil.error(0, "更新地址失败！");
    }

    @Override
    public ReturnMessage<Object> selectByid(Long addressId) {
        if (StringUtils.isEmpty(addressId)||addressMapper.selectByPrimaryKey(addressId)==null){
            return ReturnMessageUtil.error(0, "地址不存在！");
        }
        return ReturnMessageUtil.sucess(addressMapper.selectByPrimaryKey(addressId));
    }

    @Override
    public ReturnMessage<Object> selectBystudentid(HttpServletRequest request) {
        String token = request.getHeader("x-auth-token");
        if(token==null){
            return ReturnMessageUtil.error(0, "获取token失败");
        }
        String studentName= JwtTokenUtils.getStudentName(token);
        SysStudent student=studentMapper.selectByName(studentName);
        Long studentid=student.getStudentId();
        if(StringUtils.isEmpty(addressMapper.selectByStudentid(studentid))){
            return ReturnMessageUtil.error(0, "暂无地址！");
        }
        return ReturnMessageUtil.sucess(addressMapper.selectByStudentid(studentid));
    }

    @Override
    public ReturnMessage<Object> SelBydefault(HttpServletRequest request) {
        String token = request.getHeader("x-auth-token");
        if(token==null){
            return ReturnMessageUtil.error(0, "获取token失败");
        }
        String studentName= JwtTokenUtils.getStudentName(token);
        SysStudent student =studentMapper.selectByName(studentName);
        Long studentId=student.getStudentId();
        if(addressMapper.SelBydefault(studentId,"0")!=null){
            return ReturnMessageUtil.sucess(addressMapper.SelBydefault(studentId,"0"));
        }else
        return ReturnMessageUtil.error(0, "获取默认地址失败！");
    }

}
