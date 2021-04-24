package com.example.lfm.service;


import com.example.lfm.entity.SysStudent;
import com.example.lfm.pojo.phoneCode;
import com.example.lfm.utils.ReturnMessage;

import javax.servlet.http.HttpServletRequest;

public interface UserMerberService {
     ReturnMessage<Object> generateAuthCode(String telephone);

     ReturnMessage<Object> register(phoneCode phoneCode);


     ReturnMessage<Object> register1(SysStudent student);

     ReturnMessage<Object> login(SysStudent student);

     ReturnMessage<Object> update(SysStudent student,HttpServletRequest request);

     ReturnMessage<Object> updatePassword(String password,HttpServletRequest request);

     ReturnMessage<Object> selectBykey(HttpServletRequest request);

    ReturnMessage<Object> getSchool();
}
