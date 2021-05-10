package com.example.lfm.dao;

import com.example.lfm.entity.SysFeedbackMedia;
import java.util.List;

public interface SysFeedbackMediaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_feedback_media
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long feedbackMediaId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_feedback_media
     *
     * @mbg.generated
     */
    int insert(SysFeedbackMedia record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_feedback_media
     *
     * @mbg.generated
     */
    SysFeedbackMedia selectByPrimaryKey(Long feedbackMediaId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_feedback_media
     *
     * @mbg.generated
     */
    List<SysFeedbackMedia> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_feedback_media
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SysFeedbackMedia record);

    String selectUrl(Long feedbackId);
}