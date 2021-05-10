package com.example.lfm.service;

import com.alipay.api.AlipayApiException;
import com.example.lfm.entity.ActTask;
import com.example.lfm.utils.ReturnMessage;
import javafx.concurrent.Task;

import javax.servlet.http.HttpServletRequest;

public interface TaskService {
    ReturnMessage<Object> newTask(ActTask task, HttpServletRequest request);

    ReturnMessage<Object> publicorderList(String status, HttpServletRequest request);

    ReturnMessage<Object> takeTaskList(String status, HttpServletRequest request);

    ReturnMessage<Object> getOrderInfo(Long taskId);

    ReturnMessage<Object> pay(Long taskId);

    ReturnMessage<Object> canceltaskOrder(Long taskId) throws AlipayApiException;

    ReturnMessage<Object> confirm(Long taskId);

    ReturnMessage<Object> takeOrder(Long taskId, HttpServletRequest request);

    ActTask getTaskById(Long taskId);

    ReturnMessage<Object> updateByPrimaryKey(ActTask task);

    ReturnMessage<Object> takeOrderList(HttpServletRequest request);

    ReturnMessage<Object> getTakeTaskInfo(Long taskId);

    ReturnMessage<Object> cancelTakeOrder(Long taskId);
}
