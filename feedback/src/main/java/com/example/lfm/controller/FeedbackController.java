package com.example.lfm.controller;

import com.example.lfm.entity.FeedbackVo;
import com.example.lfm.service.FeedbackService;
import com.example.lfm.entity.F_FMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.lfm.utils.ReturnMessage;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    /**
     * 将反馈信息插入到反馈表Feedback和媒体表FeedbackMedia
     *
     * @return
     */
    @PostMapping("/AddFeedback")
    public ReturnMessage<Object> AddFeedback(@RequestBody FeedbackVo feedbackVo, HttpServletRequest request) {
        return feedbackService.AddFeedback(feedbackVo,request);
    }

    @GetMapping("/getFeedback")
    public ReturnMessage<Object> getFeedback(HttpServletRequest request) {
        return feedbackService.getFeedback(request);
    }
}
