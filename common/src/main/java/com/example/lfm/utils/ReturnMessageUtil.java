package com.example.lfm.utils;

public class ReturnMessageUtil {
    /**
     * 无异常 请求成功并有具体内容返回
     * @param object
     * @return
     */
    public static ReturnMessage<Object> sucess(Object object) {
        ReturnMessage<Object> message = new ReturnMessage<Object>(1,"成功",object);
        return message;
    }
    /**
     * 无异常 请求成功并无具体内容返回
     * @return
     */
    public static ReturnMessage<Object> sucess() {
        ReturnMessage<Object> message = new ReturnMessage<Object>(1,"成功",true);
        return message;
    }
    /**
     * 有自定义错误异常信息
     * @param code
     * @param msg
     * @return
     */
    public static ReturnMessage<Object> error(Integer code,String msg) {
        ReturnMessage<Object> message = new ReturnMessage<Object>(code,msg,false);
        return message;
    }
}