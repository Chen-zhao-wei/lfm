package com.example.lfm.service;

import com.alipay.api.AlipayApiException;
import com.example.lfm.entity.ActPrint;
import com.example.lfm.utils.ReturnMessage;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ActPrintService {
    /**
     * 新增打印
     */
    ReturnMessage<Object> newprint(ActPrint print, HttpServletRequest request);

    /**
     * 订单列表
     */
    ReturnMessage<Object> orderList(String status, HttpServletRequest request);

    /**
     *
     * 订单详情
     */
    ReturnMessage<Object> getOrderinfo(Long printId);

    ActPrint getActPrintById(Long printId);

    /**
     * 手机支付
     */
    ReturnMessage<Object> pay(Long orderId);

    /**
     * 取消订单
     */
    ReturnMessage<Object> cancelorder(Long orderId) throws IOException, AlipayApiException;

    /**
     *
     * 退款
     */
    ReturnMessage<Object> refund(Long printId) throws IOException, AlipayApiException;


    ReturnMessage<Object> updateByPrimaryKey(ActPrint actPrint);

    ReturnMessage<Object> confirm(Long printId);
}
