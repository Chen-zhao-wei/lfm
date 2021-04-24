package com.example.lfm.service;
import com.example.lfm.entity.SysAddress;
import com.example.lfm.utils.ReturnMessage;

import javax.servlet.http.HttpServletRequest;

public interface AddressService {
    /**
     * 存储地址
     */
    ReturnMessage<Object> inputaddress(SysAddress address,HttpServletRequest request);

    /**
     * 删除地址
     */
    ReturnMessage<Object> delddress(Long addressId);

    /**
     * 编辑地址
     */
    ReturnMessage<Object> updateaddress(SysAddress address,HttpServletRequest request);

    /**
     * 选择地址
     */
    ReturnMessage<Object> selectByid(Long addressId);

    /**
     * 获取学生的所有地址
     */
    ReturnMessage<Object> selectBystudentid(HttpServletRequest request);

    /**
     * 获取学生的默认地址
     */

    ReturnMessage<Object> SelBydefault(HttpServletRequest request);

    /**
     * 更改默认地址
     */
    ReturnMessage<Object> changedefault(Long aId,HttpServletRequest request);
}
