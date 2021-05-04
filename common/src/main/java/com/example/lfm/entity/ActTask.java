package com.example.lfm.entity;

import java.io.Serializable;
import java.util.Date;

public class ActTask implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column act_task.task_id
     *
     * @mbg.generated
     */
    private Long taskId;

    private Date cancelTime;
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column act_task.student_send_id
     *
     * @mbg.generated
     */
    private Long studentSendId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column act_print.pay_time
     *
     * @mbg.generated
     */
    private Date payTime;


    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column act_print.delivery_time
     *
     * @mbg.generated
     */
    private Date takeTime;
    private Date checkTime;

    public Date getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(Date takeTime) {
        this.takeTime = takeTime;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column act_print.finish_time
     *
     * @mbg.generated
     */
    private Date finishTime;
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column act_task.student_realize_id
     *
     * @mbg.generated
     */
    private Long studentRealizeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column act_task.status
     *
     * @mbg.generated
     */
    private String status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column act_task.task_describe
     *
     * @mbg.generated
     */
    private String taskDescribe;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column act_task.fee
     *
     * @mbg.generated
     */
    private Double fee;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column act_task.address_id
     *
     * @mbg.generated
     */
    private Long addressId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column act_task.create_by
     *
     * @mbg.generated
     */
    private String createBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column act_task.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column act_task.update_by
     *
     * @mbg.generated
     */
    private String updateBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column act_task.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column act_task.remark
     *
     * @mbg.generated
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column act_task.task_title
     *
     * @mbg.generated
     */
    private String taskTitle;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column act_task.user_check_id
     *
     * @mbg.generated
     */
    private Long userCheckId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column act_task.check_content
     *
     * @mbg.generated
     */
    private String checkContent;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column act_task.del_flag
     *
     * @mbg.generated
     */
    private String delFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table act_task
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column act_task.task_id
     *
     * @return the value of act_task.task_id
     *
     * @mbg.generated
     */
    public Long getTaskId() {
        return taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column act_task.task_id
     *
     * @param taskId the value for act_task.task_id
     *
     * @mbg.generated
     */
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column act_task.student_send_id
     *
     * @return the value of act_task.student_send_id
     *
     * @mbg.generated
     */
    public Long getStudentSendId() {
        return studentSendId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column act_task.student_send_id
     *
     * @param studentSendId the value for act_task.student_send_id
     *
     * @mbg.generated
     */
    public void setStudentSendId(Long studentSendId) {
        this.studentSendId = studentSendId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column act_task.student_realize_id
     *
     * @return the value of act_task.student_realize_id
     *
     * @mbg.generated
     */
    public Long getStudentRealizeId() {
        return studentRealizeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column act_task.student_realize_id
     *
     * @param studentRealizeId the value for act_task.student_realize_id
     *
     * @mbg.generated
     */
    public void setStudentRealizeId(Long studentRealizeId) {
        this.studentRealizeId = studentRealizeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column act_task.status
     *
     * @return the value of act_task.status
     *
     * @mbg.generated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column act_task.status
     *
     * @param status the value for act_task.status
     *
     * @mbg.generated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column act_task.task_describe
     *
     * @return the value of act_task.task_describe
     *
     * @mbg.generated
     */
    public String getTaskDescribe() {
        return taskDescribe;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column act_task.task_describe
     *
     * @param taskDescribe the value for act_task.task_describe
     *
     * @mbg.generated
     */
    public void setTaskDescribe(String taskDescribe) {
        this.taskDescribe = taskDescribe == null ? null : taskDescribe.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column act_task.fee
     *
     * @return the value of act_task.fee
     *
     * @mbg.generated
     */
    public Double getFee() {
        return fee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column act_task.fee
     *
     * @param fee the value for act_task.fee
     *
     * @mbg.generated
     */
    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column act_task.address_id
     *
     * @return the value of act_task.address_id
     *
     * @mbg.generated
     */
    public Long getAddressId() {
        return addressId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column act_task.address_id
     *
     * @param addressId the value for act_task.address_id
     *
     * @mbg.generated
     */
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column act_task.create_by
     *
     * @return the value of act_task.create_by
     *
     * @mbg.generated
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column act_task.create_by
     *
     * @param createBy the value for act_task.create_by
     *
     * @mbg.generated
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column act_task.create_time
     *
     * @return the value of act_task.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column act_task.create_time
     *
     * @param createTime the value for act_task.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column act_task.update_by
     *
     * @return the value of act_task.update_by
     *
     * @mbg.generated
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column act_task.update_by
     *
     * @param updateBy the value for act_task.update_by
     *
     * @mbg.generated
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column act_task.update_time
     *
     * @return the value of act_task.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column act_task.update_time
     *
     * @param updateTime the value for act_task.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column act_task.remark
     *
     * @return the value of act_task.remark
     *
     * @mbg.generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column act_task.remark
     *
     * @param remark the value for act_task.remark
     *
     * @mbg.generated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column act_task.task_title
     *
     * @return the value of act_task.task_title
     *
     * @mbg.generated
     */
    public String getTaskTitle() {
        return taskTitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column act_task.task_title
     *
     * @param taskTitle the value for act_task.task_title
     *
     * @mbg.generated
     */
    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle == null ? null : taskTitle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column act_task.user_check_id
     *
     * @return the value of act_task.user_check_id
     *
     * @mbg.generated
     */
    public Long getUserCheckId() {
        return userCheckId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column act_task.user_check_id
     *
     * @param userCheckId the value for act_task.user_check_id
     *
     * @mbg.generated
     */
    public void setUserCheckId(Long userCheckId) {
        this.userCheckId = userCheckId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column act_task.check_content
     *
     * @return the value of act_task.check_content
     *
     * @mbg.generated
     */
    public String getCheckContent() {
        return checkContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column act_task.check_content
     *
     * @param checkContent the value for act_task.check_content
     *
     * @mbg.generated
     */
    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent == null ? null : checkContent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column act_task.del_flag
     *
     * @return the value of act_task.del_flag
     *
     * @mbg.generated
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column act_task.del_flag
     *
     * @param delFlag the value for act_task.del_flag
     *
     * @mbg.generated
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table act_task
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", taskId=").append(taskId);
        sb.append(", studentSendId=").append(studentSendId);
        sb.append(", studentRealizeId=").append(studentRealizeId);
        sb.append(", status=").append(status);
        sb.append(", taskDescribe=").append(taskDescribe);
        sb.append(", fee=").append(fee);
        sb.append(", addressId=").append(addressId);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", remark=").append(remark);
        sb.append(", taskTitle=").append(taskTitle);
        sb.append(", userCheckId=").append(userCheckId);
        sb.append(", checkContent=").append(checkContent);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}