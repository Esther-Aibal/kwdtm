package com.movitech.mbox.modules.wdt.operateLog.entity;

import java.io.Serializable;
import java.util.Date;

public class OperateLog implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_operate_log.id
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_operate_log.type
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_operate_log.task_id
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    private String taskId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_operate_log.task_item_id
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    private String taskItemId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_operate_log.user_id
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_operate_log.content
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_operate_log.ip_address
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    private String ipAddress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_operate_log.operator_date
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    private String operatorDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_operate_log.create_by
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    private String createBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_operate_log.create_date
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    private Date createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_operate_log.update_by
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    private String updateBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_operate_log.update_date
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    private Date updateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_operate_log.del_flag
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    private String delFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table wdt_t_operate_log
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    
    private String logeType;//操作类型
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_operate_log.id
     *
     * @return the value of wdt_t_operate_log.id
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_operate_log.id
     *
     * @param id the value for wdt_t_operate_log.id
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_operate_log.type
     *
     * @return the value of wdt_t_operate_log.type
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_operate_log.type
     *
     * @param type the value for wdt_t_operate_log.type
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_operate_log.task_id
     *
     * @return the value of wdt_t_operate_log.task_id
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_operate_log.task_id
     *
     * @param taskId the value for wdt_t_operate_log.task_id
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_operate_log.task_item_id
     *
     * @return the value of wdt_t_operate_log.task_item_id
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public String getTaskItemId() {
        return taskItemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_operate_log.task_item_id
     *
     * @param taskItemId the value for wdt_t_operate_log.task_item_id
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public void setTaskItemId(String taskItemId) {
        this.taskItemId = taskItemId == null ? null : taskItemId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_operate_log.user_id
     *
     * @return the value of wdt_t_operate_log.user_id
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_operate_log.user_id
     *
     * @param userId the value for wdt_t_operate_log.user_id
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_operate_log.content
     *
     * @return the value of wdt_t_operate_log.content
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_operate_log.content
     *
     * @param content the value for wdt_t_operate_log.content
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_operate_log.ip_address
     *
     * @return the value of wdt_t_operate_log.ip_address
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_operate_log.ip_address
     *
     * @param ipAddress the value for wdt_t_operate_log.ip_address
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_operate_log.operator_date
     *
     * @return the value of wdt_t_operate_log.operator_date
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public String getOperatorDate() {
        return operatorDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_operate_log.operator_date
     *
     * @param operatorDate the value for wdt_t_operate_log.operator_date
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public void setOperatorDate(String operatorDate) {
        this.operatorDate = operatorDate == null ? null : operatorDate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_operate_log.create_by
     *
     * @return the value of wdt_t_operate_log.create_by
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_operate_log.create_by
     *
     * @param createBy the value for wdt_t_operate_log.create_by
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_operate_log.create_date
     *
     * @return the value of wdt_t_operate_log.create_date
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_operate_log.create_date
     *
     * @param createDate the value for wdt_t_operate_log.create_date
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_operate_log.update_by
     *
     * @return the value of wdt_t_operate_log.update_by
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_operate_log.update_by
     *
     * @param updateBy the value for wdt_t_operate_log.update_by
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_operate_log.update_date
     *
     * @return the value of wdt_t_operate_log.update_date
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_operate_log.update_date
     *
     * @param updateDate the value for wdt_t_operate_log.update_date
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_operate_log.del_flag
     *
     * @return the value of wdt_t_operate_log.del_flag
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_operate_log.del_flag
     *
     * @param delFlag the value for wdt_t_operate_log.del_flag
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public String getLogeType() {
		return logeType;
	}

	public void setLogeType(String logeType) {
		this.logeType = logeType;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wdt_t_operate_log
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        OperateLog other = (OperateLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getTaskItemId() == null ? other.getTaskItemId() == null : this.getTaskItemId().equals(other.getTaskItemId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getIpAddress() == null ? other.getIpAddress() == null : this.getIpAddress().equals(other.getIpAddress()))
            && (this.getOperatorDate() == null ? other.getOperatorDate() == null : this.getOperatorDate().equals(other.getOperatorDate()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getUpdateDate() == null ? other.getUpdateDate() == null : this.getUpdateDate().equals(other.getUpdateDate()))
            && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()))
            && (this.getLogeType() == null ? other.getLogeType() == null : this.getLogeType().equals(other.getLogeType()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wdt_t_operate_log
     *
     * @mbggenerated Tue Aug 22 10:40:14 CST 2017
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getTaskItemId() == null) ? 0 : getTaskItemId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getIpAddress() == null) ? 0 : getIpAddress().hashCode());
        result = prime * result + ((getOperatorDate() == null) ? 0 : getOperatorDate().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        result = prime * result + ((getLogeType() == null) ? 0 : getLogeType().hashCode());
        return result;
    }
}