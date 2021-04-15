package com.example.lfm.service;

import com.example.lfm.entity.F_FMedia;
import com.example.lfm.utils.ReturnMessage;

public interface FeedbackService {
    /**
     * 反馈
     */
    ReturnMessage<Object> AddFeedback(F_FMedia fFMedia);
}
