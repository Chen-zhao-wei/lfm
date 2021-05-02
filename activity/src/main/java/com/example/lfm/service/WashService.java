package com.example.lfm.service;

import com.alipay.api.AlipayApiException;
import com.example.lfm.entity.Washing;
import com.example.lfm.utils.ReturnMessage;

import javax.servlet.http.HttpServletRequest;

public interface WashService {
    ReturnMessage<Object> newWash(Washing washing, HttpServletRequest request);

    ReturnMessage<Object> orderList(String status, HttpServletRequest request);

    ReturnMessage<Object> getOrderInfo(Long washingId);

    ReturnMessage<Object> pay(Long washId);

    ReturnMessage<Object> cancelWashOrder(Long washId) throws AlipayApiException;

    ReturnMessage<Object> confirm(Long washingId);

    Washing getwashById(Long washId);

    ReturnMessage<Object> updateByPrimaryKey(Washing washing);
}
