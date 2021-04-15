package com.example.lfm.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.example.lfm.entity.ActPrint;
import com.example.lfm.service.ActPrintService;
import com.example.lfm.utils.ReturnMessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.lfm.utils.ReturnMessage;
import org.thymeleaf.util.StringUtils;

@RestController
@RequestMapping("/print")
@Api(value = "打印控制器")
public class PrintController {
    @Autowired
    private ActPrintService printService;

    @ApiOperation("新增打印")
    @PostMapping("/newprint")
    public ReturnMessage<Object> newprint(@RequestBody ActPrint print) {
        return printService.newprint(print);
    }

    @ApiOperation("查看个人打印订单 ")
    @PostMapping("/orderList/{studentId}/{status}")
    public ReturnMessage<Object> orderList(@PathVariable() Long studentId, @PathVariable() String status) {
        return printService.orderList(studentId, status);
    }

    @ApiOperation("查看具体订单 ")
    @PostMapping("/SelectByKey/{printId}")
    public ReturnMessage<Object> SelectByKey(@PathVariable() Long printId) {
        return printService.SelectByKey(printId);
    }

    /**
     * 获取订单信息
     */
    @ApiOperation("手机支付 ")
    @GetMapping("/getOrderInfo")
    public ReturnMessage<Object> getOrderInfo(Long printId) {
        return printService.getOrderInfo(printId);
    }
}
