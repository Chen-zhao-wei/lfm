package com.example.lfm.controller;

import com.example.lfm.entity.SysAddress;
import com.example.lfm.service.AddressService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.lfm.utils.ReturnMessage;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/address")
@Api(value = "学生地址控制器")
public class AddressController {
    private static Logger logger = LoggerFactory.getLogger(UmsMerberController.class);
    @Autowired
    private AddressService addressService;

    @PostMapping("/InsertAd")
    public ReturnMessage<Object> InsertAd(@RequestBody SysAddress address){
        return addressService.inputaddress(address);
    }

    @PostMapping("/EditAd")
    public ReturnMessage<Object> EditAd(@RequestBody SysAddress address){
        return addressService.updateaddress(address);
    }

    @PostMapping("/DelAd/{aId}")
    public ReturnMessage<Object> DelAd(@PathVariable()Long aId){
        return addressService.delddress(aId);
    }

    @GetMapping("/SelAD/{aId}")
    public ReturnMessage<Object> SelAD(@PathVariable() Long aId){
        return addressService.selectByid(aId);
    }

    @GetMapping("/SelADs")
    public ReturnMessage<Object> SelADs(HttpServletRequest request){
        return addressService.selectBystudentid(request);
    }

    @GetMapping("/SelBydefault")
    public ReturnMessage<Object> SelBydefault(HttpServletRequest request){
        return addressService.SelBydefault(request);
    }
}
