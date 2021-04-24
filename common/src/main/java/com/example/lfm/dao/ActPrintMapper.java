package com.example.lfm.dao;

import com.example.lfm.entity.ActPrint;

import java.util.List;

public interface ActPrintMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table act_print
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long printId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table act_print
     *
     * @mbg.generated
     */
    int insert(ActPrint record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table act_print
     *
     * @mbg.generated
     */
    ActPrint selectByPrimaryKey(Long printId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table act_print
     *
     * @mbg.generated
     */
    List<ActPrint> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table act_print
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ActPrint record);

    ActPrint selectByStudentId(Long studentId);

    ActPrint selectBySIdStatus(Long studentId, String status);
}