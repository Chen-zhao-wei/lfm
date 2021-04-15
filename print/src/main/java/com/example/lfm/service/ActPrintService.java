package com.example.lfm.service;

import com.example.lfm.entity.ActPrint;
import com.example.lfm.utils.ReturnMessage;

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

    ReturnMessage<Object> getOrderInfo(Long orderId);
}
