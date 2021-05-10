package com.example.lfm.service.Impl;

import com.example.lfm.dao.SysFeedbackMapper;
import com.example.lfm.dao.SysFeedbackMediaMapper;
import com.example.lfm.dao.SysStudentMapper;
import com.example.lfm.entity.FeedbackVo;
import com.example.lfm.entity.SysFeedback;
import com.example.lfm.entity.SysFeedbackMedia;
import com.example.lfm.entity.F_FMedia;
import com.example.lfm.service.FeedbackService;
import com.example.lfm.utils.JwtTokenUtils;
import com.sun.org.apache.bcel.internal.generic.LALOAD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.example.lfm.utils.ReturnMessage;
import com.example.lfm.utils.ReturnMessageUtil;
import com.example.lfm.utils.SbException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private SysFeedbackMapper feedbackMapper;
    @Autowired
    private SysFeedbackMediaMapper feedbackMediaMapper;
    @Autowired
    private SysStudentMapper studentMapper;
    @Override

    public ReturnMessage<Object> AddFeedback(FeedbackVo feedbackVo, HttpServletRequest request) {
        String token = request.getHeader("x-auth-token");
        if(token==null){
            return ReturnMessageUtil.error(0, "获取token失败");
        }
        Long studentId= JwtTokenUtils.getStudentId(token);
        if(StringUtils.isEmpty(studentId)||StringUtils.isEmpty(StringUtils.isEmpty(studentMapper.selectByPrimaryKey(studentId)))){
            return ReturnMessageUtil.error(0, "学生不存在！");
        }
        SysFeedback feedback = new SysFeedback();
        feedback.setCreateTime(new Date());
        feedback.setStudentId(studentId);
        feedback.setStatus("0");
        feedback.setDelFlag("0");
        feedback.setFeedbackContent(feedbackVo.getFeedbackContent());
        feedback.setOrderId(feedbackVo.getOrderId());
        feedback.setOrderType(feedbackVo.getOrderType());
        List<SysFeedbackMedia> feedbackMedias = new ArrayList<SysFeedbackMedia>();
        List<String> url=feedbackVo.getUrl();
        //        1. 判断feedback 是否为空  2. 判断uId是否为空
        if (StringUtils.isEmpty(feedback)) {
            throw new SbException(0, "输入不合法");
        }
        //          2.判断反馈内容是否为空
        if (StringUtils.isEmpty(feedback.getFeedbackContent())) {
            throw new SbException(0, "反馈内容不能为空!");
        }
        //          3.数据操作
        Long feedbackId = feedbackMapper.insert(feedback);
        for(String u:url){
            SysFeedbackMedia feedbackMedia=new SysFeedbackMedia();
            feedbackMedia.setFeedbackMediaUrl(u);
            feedbackMedia.setFeedbackId(feedbackId);
            feedbackMedia.setCreateTime(new Date());
            feedbackMedia.setStatus("0");
            feedbackMedias.add(feedbackMedia);
        }
        if (!StringUtils.isEmpty(feedbackMedias)) {
            for(int i=0;feedbackMedias.size()>i;i++){
                feedbackMedias.get(i).setFeedbackId(feedback.getFeedbackId());

                int flag1 = feedbackMediaMapper.insert(feedbackMedias.get(i));
                if (flag1 == 0) {
                    throw new SbException(0, "反馈媒体添加失败!");
                }
                System.out.println("第"+i+"个媒体，id"+feedback.getFeedbackId());
            }
        }
        return  ReturnMessageUtil.sucess();
    }

    @Override
    public ReturnMessage<Object> getFeedback(HttpServletRequest request) {
        String token = request.getHeader("x-auth-token");
        if(token==null){
            return ReturnMessageUtil.error(0, "获取token失败");
        }
        Long studentId= JwtTokenUtils.getStudentId(token);
        if(StringUtils.isEmpty(studentId)||StringUtils.isEmpty(StringUtils.isEmpty(studentMapper.selectByPrimaryKey(studentId)))){
            return ReturnMessageUtil.error(0, "学生不存在！");
        }
        if(StringUtils.isEmpty(feedbackMapper.selectByStudentId(studentId))){
            return ReturnMessageUtil.error(0,"暂无反馈信息");
        }
        return ReturnMessageUtil.sucess(feedbackMapper.selectByStudentId(studentId));
    }
}
