package com.example.lfm.entity;

import java.io.Serializable;
import java.util.Date;

public class SysAddress implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_address.address_id
     *
     * @mbg.generated
     */
    private Long addressId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_address.student_id
     *
     * @mbg.generated
     */
    private Long studentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_address.address
     *
     * @mbg.generated
     */
    private String address;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_address.take_number
     *
     * @mbg.generated
     */
    private String takeNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_address.take_name
     *
     * @mbg.generated
     */
    private String takeName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_address.default_flag
     *
     * @mbg.generated
     */
    private String defaultFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_address.del_flag
     *
     * @mbg.generated
     */
    private String delFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_address.create_by
     *
     * @mbg.generated
     */
    private String createBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_address.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_address.update_by
     *
     * @mbg.generated
     */
    private String updateBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_address.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_address.remark
     *
     * @mbg.generated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sys_address
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_address.address_id
     *
     * @return the value of sys_address.address_id
     *
     * @mbg.generated
     */
    public Long getAddressId() {
        return addressId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_address.address_id
     *
     * @param addressId the value for sys_address.address_id
     *
     * @mbg.generated
     */
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_address.student_id
     *
     * @return the value of sys_address.student_id
     *
     * @mbg.generated
     */
    public Long getStudentId() {
        return studentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_address.student_id
     *
     * @param studentId the value for sys_address.student_id
     *
     * @mbg.generated
     */
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_address.address
     *
     * @return the value of sys_address.address
     *
     * @mbg.generated
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_address.address
     *
     * @param address the value for sys_address.address
     *
     * @mbg.generated
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_address.take_number
     *
     * @return the value of sys_address.take_number
     *
     * @mbg.generated
     */
    public String getTakeNumber() {
        return takeNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_address.take_number
     *
     * @param takeNumber the value for sys_address.take_number
     *
     * @mbg.generated
     */
    public void setTakeNumber(String takeNumber) {
        this.takeNumber = takeNumber == null ? null : takeNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_address.take_name
     *
     * @return the value of sys_address.take_name
     *
     * @mbg.generated
     */
    public String getTakeName() {
        return takeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_address.take_name
     *
     * @param takeName the value for sys_address.take_name
     *
     * @mbg.generated
     */
    public void setTakeName(String takeName) {
        this.takeName = takeName == null ? null : takeName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_address.default_flag
     *
     * @return the value of sys_address.default_flag
     *
     * @mbg.generated
     */
    public String getDefaultFlag() {
        return defaultFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_address.default_flag
     *
     * @param defaultFlag the value for sys_address.default_flag
     *
     * @mbg.generated
     */
    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag == null ? null : defaultFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_address.del_flag
     *
     * @return the value of sys_address.del_flag
     *
     * @mbg.generated
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_address.del_flag
     *
     * @param delFlag the value for sys_address.del_flag
     *
     * @mbg.generated
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_address.create_by
     *
     * @return the value of sys_address.create_by
     *
     * @mbg.generated
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_address.create_by
     *
     * @param createBy the value for sys_address.create_by
     *
     * @mbg.generated
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_address.create_time
     *
     * @return the value of sys_address.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_address.create_time
     *
     * @param createTime the value for sys_address.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_address.update_by
     *
     * @return the value of sys_address.update_by
     *
     * @mbg.generated
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_address.update_by
     *
     * @param updateBy the value for sys_address.update_by
     *
     * @mbg.generated
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_address.update_time
     *
     * @return the value of sys_address.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_address.update_time
     *
     * @param updateTime the value for sys_address.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_address.remark
     *
     * @return the value of sys_address.remark
     *
     * @mbg.generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_address.remark
     *
     * @param remark the value for sys_address.remark
     *
     * @mbg.generated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_address
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", addressId=").append(addressId);
        sb.append(", studentId=").append(studentId);
        sb.append(", address=").append(address);
        sb.append(", takeNumber=").append(takeNumber);
        sb.append(", takeName=").append(takeName);
        sb.append(", defaultFlag=").append(defaultFlag);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}