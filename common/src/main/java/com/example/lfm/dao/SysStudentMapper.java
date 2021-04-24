package com.example.lfm.dao;

import com.example.lfm.entity.SysStudent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysStudentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_student
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long studentId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_student
     *
     * @mbg.generated
     */
    int insert(SysStudent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_student
     *
     * @mbg.generated
     */
    SysStudent selectByPrimaryKey(Long studentId);


    SysStudent selectByName(String name);

    SysStudent selectBySchSN(@Param("schoolId") Long schoolId, @Param("studentNumber") String studentNumber);

    SysStudent selectByPhone(String phoneNumber);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_student
     *
     * @mbg.generated
     */
    List<SysStudent> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_student
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SysStudent record);
}