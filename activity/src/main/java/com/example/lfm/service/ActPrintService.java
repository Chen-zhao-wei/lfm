package com.example.lfm.service;

import com.alipay.api.AlipayApiException;
import com.example.lfm.entity.ActPrint;
import com.example.lfm.utils.ReturnMessage;

import java.io.IOException;

public interface ActPrintService {
    /**
     * 新增打印
     */
    ReturnMessage<Object> newprint(ActPrint print);

    /**
     * 订单列表
     */
    ReturnMessage<Object> orderList(Long studentId,String status);

    /**
     *
     * 订单详情
     */
    ReturnMessage<Object> SelectByKey(Long printId);

    ActPrint getActPrintById(Long printId);

    /**
     * 手机支付
     */
    ReturnMessage<Object> getOrderInfo(Long orderId);

    /**
     * 取消订单
     */
    ReturnMessage<Object> cancelorder(Long orderId);

    /**
     *
     * 退款
     */
    ReturnMessage<Object> refund(Long printId) throws IOException, AlipayApiException;
}
