package com.example.lfm.dao;

import com.example.lfm.entity.ActTask;
import java.util.List;

public interface ActTaskMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table act_task
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long taskId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table act_task
     *
     * @mbg.generated
     */
    int insert(ActTask record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table act_task
     *
     * @mbg.generated
     */
    ActTask selectByPrimaryKey(Long taskId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table act_task
     *
     * @mbg.generated
     */
    List<ActTask> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table act_task
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ActTask record);

    List<ActTask> selectBySendId(Long studentSendId, String delFlag);

    List<ActTask> selectBySIdStatus(Long studentSendId, String status, String delFlag);

    List<ActTask> selectByTakeTaskId(Long studentRealizeId, String delFlag);

    List<ActTask> selectByTIdStatus(Long studentRealizeId, String status, String delFlag);
}