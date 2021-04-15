package com.example.lfm.controller;

import com.example.lfm.service.FeedbackService;
import com.example.lfm.entity.F_FMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.lfm.utils.ReturnMessage;

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
    public ReturnMessage<Object> AddFeedback(@RequestBody F_FMedia fMedia) {
        return feedbackService.AddFeedback(fMedia);
    }
}
