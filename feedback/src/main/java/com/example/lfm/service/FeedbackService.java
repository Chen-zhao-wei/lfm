package com.example.lfm.service;

import com.example.lfm.entity.F_FMedia;
import com.example.lfm.entity.FeedbackVo;
import com.example.lfm.utils.ReturnMessage;

import javax.servlet.http.HttpServletRequest;

public interface FeedbackService {
    /**
     * 反馈
     */
    ReturnMessage<Object> AddFeedback(FeedbackVo feedbackVo, HttpServletRequest request);

    ReturnMessage<Object> getFeedback(HttpServletRequest request);
}
