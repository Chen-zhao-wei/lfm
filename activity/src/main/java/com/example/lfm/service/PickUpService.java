package com.example.lfm.service;


import com.alipay.api.AlipayApiException;
import com.example.lfm.entity.PickUp;
import com.example.lfm.utils.ReturnMessage;

import javax.servlet.http.HttpServletRequest;

public interface PickUpService {
    ReturnMessage<Object> newPickUp(PickUp pickUp, HttpServletRequest request);

    ReturnMessage<Object> orderList(String status, HttpServletRequest request);

    ReturnMessage<Object> getOrderInfo(Long pickUpId);

    ReturnMessage<Object> pay(Long printId);

    ReturnMessage<Object> cancelPickUpOrder(Long printId) throws AlipayApiException;
}
