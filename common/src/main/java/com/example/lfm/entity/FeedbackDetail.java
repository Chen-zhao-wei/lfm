package com.example.lfm.entity;

import java.util.List;

public class FeedbackDetail {
    private Long feedbackId;

    private String feedbackContent;

    private Long orderId;

    private String orderType;

    private List<String> feedbackMediaUrls;

    private String manageContent;

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public List<String> getFeedbackMediaUrls() {
        return feedbackMediaUrls;
    }

    public void setFeedbackMediaUrls(List<String> feedbackMediaUrls) {
        this.feedbackMediaUrls = feedbackMediaUrls;
    }

    public String getManageContent() {
        return manageContent;
    }

    public void setManageContent(String manageContent) {
        this.manageContent = manageContent;
    }
}
