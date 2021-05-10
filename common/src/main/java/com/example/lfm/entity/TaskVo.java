package com.example.lfm.entity;

import java.util.Date;

public class TaskVo {
    //任务id
    private Long taskId;
    //标题
    private String taskTitle;
    //内容
    private String taskDescribe;
    //金额
    private Double fee;
    //发布人名
    private String studentName;
    //发布时间
    private Date createTime;
    //任务地址
    private String address;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescribe() {
        return taskDescribe;
    }

    public void setTaskDescribe(String taskDescribe) {
        this.taskDescribe = taskDescribe;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
