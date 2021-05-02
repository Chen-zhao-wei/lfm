package com.example.lfm.controller;

import com.alipay.api.AlipayApiException;
import com.example.lfm.dao.ActTaskMapper;
import com.example.lfm.entity.ActTask;
import com.example.lfm.entity.Washing;
import com.example.lfm.service.TaskService;
import com.example.lfm.service.WashService;
import com.example.lfm.utils.ReturnMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/task")
@Api(value = "任务")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @ApiOperation("发布任务")
    @PostMapping("/newTask")
    public ReturnMessage<Object> newTask(@RequestBody ActTask task, HttpServletRequest request) {
        return taskService.newTask(task,request);
    }

    @ApiOperation("查看发布的任务 ")
    @PostMapping("/publicorderList/{status}")
    public ReturnMessage<Object> publicorderList(@PathVariable() String status, HttpServletRequest request) {
        return taskService.publicorderList(status,request);
    }

    @ApiOperation("查看接受的任务 ")
    @PostMapping("/takeTaskList/{status}")
    public ReturnMessage<Object> takeTaskList(@PathVariable() String status, HttpServletRequest request) {
        return taskService.takeTaskList(status,request);
    }

    /**
     * 获取订单信息
     */
    @ApiOperation("查看具体订单 ")
    @PostMapping("/getOrderInfo/{taskId}")
    public ReturnMessage<Object> getOrderInfo(@PathVariable() Long taskId) {
        return taskService.getOrderInfo(taskId);
    }

    @ApiOperation("手机支付 ")
    @GetMapping("/pay")
    public ReturnMessage<Object> pay(Long taskId) {
        return taskService.pay(taskId);
    }

    @ApiOperation("取消订单 ")
    @GetMapping("/canceltaskOrder")
    public ReturnMessage<Object> canceltaskOrder(Long taskId) throws IOException, AlipayApiException {
        return taskService.canceltaskOrder(taskId);
    }

    @ApiOperation("确认收货 ")
    @GetMapping("/confirm")
    public ReturnMessage<Object> confirm(Long taskId)  {
        return taskService.confirm(taskId);
    }

    /**
     * 获取订单信息
     */
    @ApiOperation("接单 ")
    @PostMapping("/takeOrder/{taskId}")
    public ReturnMessage<Object> takeOrder(@PathVariable() Long taskId, HttpServletRequest request) {
        return taskService.takeOrder(taskId, request);
    }

}
