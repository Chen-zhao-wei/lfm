package com.example.lfm.dao;

import com.example.lfm.entity.SysAddress;

import java.util.List;

public interface SysAddressMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_address
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long addressId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_address
     *
     * @mbg.generated
     */
    int insert(SysAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_address
     *
     * @mbg.generated
     */
    SysAddress selectByPrimaryKey(Long addressId);


    List<SysAddress> selectByStudentid(Long studentId);

    SysAddress SelBydefault(Long studentId, String defaultFlag);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_address
     *
     * @mbg.generated
     */
    List<SysAddress> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_address
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SysAddress record);


}