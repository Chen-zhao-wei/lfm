package com.example.lfm.controller;

import com.alipay.api.AlipayApiException;
import com.example.lfm.entity.PickUp;
import com.example.lfm.entity.Washing;
import com.example.lfm.service.WashService;
import com.example.lfm.utils.ReturnMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/washing")
@Api(value = "洗衣")
public class WashController {
    @Autowired
    private WashService washService;

    @ApiOperation("新增代取")
    @PostMapping("/newWash")
    public ReturnMessage<Object> newWash(@RequestBody Washing washing, HttpServletRequest request) {
        return washService.newWash(washing,request);
    }

    @ApiOperation("查看个人打印订单 ")
    @PostMapping("/orderList/{status}")
    public ReturnMessage<Object> orderList(@PathVariable() String status, HttpServletRequest request) {
        return washService.orderList(status,request);
    }

    @ApiOperation("确认收货 ")
    @GetMapping("/confirm")
    public ReturnMessage<Object> confirm(Long washingId)  {
        return washService.confirm(washingId);
    }
    /**
     * 获取订单信息
     */
    @ApiOperation("查看具体订单 ")
    @PostMapping("/getOrderInfo/{washingId}")
    public ReturnMessage<Object> getOrderInfo(@PathVariable() Long washingId) {
        return washService.getOrderInfo(washingId);
    }

    @ApiOperation("手机支付 ")
    @GetMapping("/pay")
    public ReturnMessage<Object> pay(Long washingId) {
        return washService.pay(washingId);
    }

    @ApiOperation("取消订单 ")
    @GetMapping("/cancelWashOrder")
    public ReturnMessage<Object> cancelWashOrder(Long washingId) throws IOException, AlipayApiException {
        return washService.cancelWashOrder(washingId);
    }



}
