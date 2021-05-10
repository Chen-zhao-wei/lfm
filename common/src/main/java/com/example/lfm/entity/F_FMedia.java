package com.example.lfm.entity;

import com.example.lfm.entity.SysFeedback;
import com.example.lfm.entity.SysFeedbackMedia;

import java.util.List;

public class F_FMedia {
    private SysFeedback feedback;
    private List<SysFeedbackMedia> feedbackMedia;

    public F_FMedia(SysFeedback feedback, List<SysFeedbackMedia> feedbackMedia) {
        this.feedback = feedback;
        this.feedbackMedia = feedbackMedia;
    }

    public SysFeedback getFeedback() {
        return feedback;
    }

    public void setFeedback(SysFeedback feedback) {
        this.feedback = feedback;
    }

    public List<SysFeedbackMedia> getFeedbackMedia() {
        return feedbackMedia;
    }

    public void setFeedbackMedia(List<SysFeedbackMedia> feedbackMedia) {
        this.feedbackMedia = feedbackMedia;
    }
}
