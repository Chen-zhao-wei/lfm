package com.example.lfm.service.Impl;

import com.example.lfm.dao.SysFeedbackMapper;
import com.example.lfm.dao.SysFeedbackMediaMapper;
import com.example.lfm.entity.SysFeedback;
import com.example.lfm.entity.SysFeedbackMedia;
import com.example.lfm.entity.F_FMedia;
import com.example.lfm.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.example.lfm.utils.ReturnMessage;
import com.example.lfm.utils.ReturnMessageUtil;
import com.example.lfm.utils.SbException;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private SysFeedbackMapper feedbackMapper;
    @Autowired
    private SysFeedbackMediaMapper feedbackMediaMapper;

    @Override

    public ReturnMessage<Object> AddFeedback(F_FMedia fFMedia) {
        SysFeedback feedback = fFMedia.getFeedback();
        List<SysFeedbackMedia> feedbackMedias = fFMedia.getFeedbackMedia();
        //        1. 判断feedback 是否为空  2. 判断uId是否为空
        if (StringUtils.isEmpty(feedback) && (feedback.getFeedbackId() == null || feedback.getFeedbackId() < 0)) {
            throw new SbException(400, "输入不合法");
        }
        //          2.判断反馈内容是否为空
        if (StringUtils.isEmpty(feedback.getFeedbackContent())) {
            throw new SbException(100, "反馈信息错误!");
        }
        //          3.数据操作
        int flag = feedbackMapper.insert(feedback);
        if (flag == 0) {
            throw new SbException(100, "反馈信息添加失败!");
        }
        if (!StringUtils.isEmpty(feedbackMedias)) {
            for(int i=0;feedbackMedias.size()>i;i++){
                feedbackMedias.get(i).setFeedbackId(feedback.getFeedbackId());
                int flag1 = feedbackMediaMapper.insert(feedbackMedias.get(i));
                if (flag1 == 0) {
                    throw new SbException(100, "反馈媒体添加失败!");
                }
                System.out.println("第"+i+"个媒体，id"+feedback.getFeedbackId());
            }
        }
        return  ReturnMessageUtil.sucess(true);
    }
}
