package com.example.lfm.controller;

import com.alipay.api.AlipayApiException;
import com.example.lfm.entity.ActPrint;
import com.example.lfm.entity.PickUp;
import com.example.lfm.service.PickUpService;
import com.example.lfm.utils.ReturnMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/pick_up")
@Api(value = "代取快递")
public class PicckUpController {
    @Autowired
    private PickUpService pickUpService;

    @ApiOperation("新增代取")
    @PostMapping("/newPickUp")
    public ReturnMessage<Object> newPickUp(@RequestBody PickUp pickUp, HttpServletRequest request) {
        return pickUpService.newPickUp(pickUp,request);
    }

    @ApiOperation("查看个人打印订单 ")
    @PostMapping("/orderList/{status}")
    public ReturnMessage<Object> orderList(@PathVariable() String status, HttpServletRequest request) {
        return pickUpService.orderList(status,request);
    }

    /**
     * 获取订单信息
     */
    @ApiOperation("查看具体订单 ")
    @PostMapping("/getOrderInfo/{pickUpId}")
    public ReturnMessage<Object> getOrderInfo(@PathVariable() Long pickUpId) {
        return pickUpService.getOrderInfo(pickUpId);
    }

    @ApiOperation("手机支付 ")
    @GetMapping("/pay")
    public ReturnMessage<Object> pay(Long printId) {
        return pickUpService.pay(printId);
    }

    @ApiOperation("取消订单 ")
    @GetMapping("/cancelPickUpOrder")
    public ReturnMessage<Object> cancelPickUpOrder(Long printId) throws IOException, AlipayApiException {
        return pickUpService.cancelPickUpOrder(printId);
    }


}
