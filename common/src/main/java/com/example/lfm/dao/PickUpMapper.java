package com.example.lfm.dao;

import com.example.lfm.entity.PickUp;
import java.util.List;

public interface PickUpMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pick_up
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long pickUpId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pick_up
     *
     * @mbg.generated
     */
    int insert(PickUp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pick_up
     *
     * @mbg.generated
     */
    PickUp selectByPrimaryKey(Long pickUpId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pick_up
     *
     * @mbg.generated
     */
    List<PickUp> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pick_up
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PickUp record);
//通过学生id获取该学生所有的订单信息
    List<PickUp> selectByStudentId(Long studentId);
//通过学生id以及订单状态获取对应的订单信息
    List<PickUp> selectBySIdStatus(Long studentId, String status);

}