package com.movitech.mbox.modules.wdt.taskStatus.entity;

import java.io.Serializable;
import java.util.Date;

public class TaskBanjie implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_task_banjie_log.id
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_task_banjie_log.type
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_task_banjie_log.task_id
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    private String taskId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_task_banjie_log.task_item_id
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    private String taskItemId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_task_banjie_log.content
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_task_banjie_log.summary
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    private String summary;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_task_banjie_log.status
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_task_banjie_log.creater_id
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    private String createrId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_task_banjie_log.approver_id
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    private String approverId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_task_banjie_log.approve_time
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    private Date approveTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_task_banjie_log.approve_remark
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    private String approveRemark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_task_banjie_log.process_Id
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    private String processId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_task_banjie_log.create_by
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    private String createBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_task_banjie_log.create_date
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    private Date createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_task_banjie_log.update_by
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    private String updateBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_task_banjie_log.update_date
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    private Date updateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wdt_t_task_banjie_log.del_flag
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    private String delFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table wdt_t_task_banjie_log
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_task_banjie_log.id
     *
     * @return the value of wdt_t_task_banjie_log.id
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_task_banjie_log.id
     *
     * @param id the value for wdt_t_task_banjie_log.id
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_task_banjie_log.type
     *
     * @return the value of wdt_t_task_banjie_log.type
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_task_banjie_log.type
     *
     * @param type the value for wdt_t_task_banjie_log.type
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_task_banjie_log.task_id
     *
     * @return the value of wdt_t_task_banjie_log.task_id
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_task_banjie_log.task_id
     *
     * @param taskId the value for wdt_t_task_banjie_log.task_id
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_task_banjie_log.task_item_id
     *
     * @return the value of wdt_t_task_banjie_log.task_item_id
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public String getTaskItemId() {
        return taskItemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_task_banjie_log.task_item_id
     *
     * @param taskItemId the value for wdt_t_task_banjie_log.task_item_id
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public void setTaskItemId(String taskItemId) {
        this.taskItemId = taskItemId == null ? null : taskItemId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_task_banjie_log.content
     *
     * @return the value of wdt_t_task_banjie_log.content
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_task_banjie_log.content
     *
     * @param content the value for wdt_t_task_banjie_log.content
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_task_banjie_log.summary
     *
     * @return the value of wdt_t_task_banjie_log.summary
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public String getSummary() {
        return summary;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_task_banjie_log.summary
     *
     * @param summary the value for wdt_t_task_banjie_log.summary
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_task_banjie_log.status
     *
     * @return the value of wdt_t_task_banjie_log.status
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_task_banjie_log.status
     *
     * @param status the value for wdt_t_task_banjie_log.status
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_task_banjie_log.creater_id
     *
     * @return the value of wdt_t_task_banjie_log.creater_id
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public String getCreaterId() {
        return createrId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_task_banjie_log.creater_id
     *
     * @param createrId the value for wdt_t_task_banjie_log.creater_id
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public void setCreaterId(String createrId) {
        this.createrId = createrId == null ? null : createrId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_task_banjie_log.approver_id
     *
     * @return the value of wdt_t_task_banjie_log.approver_id
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public String getApproverId() {
        return approverId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_task_banjie_log.approver_id
     *
     * @param approverId the value for wdt_t_task_banjie_log.approver_id
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public void setApproverId(String approverId) {
        this.approverId = approverId == null ? null : approverId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_task_banjie_log.approve_time
     *
     * @return the value of wdt_t_task_banjie_log.approve_time
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public Date getApproveTime() {
        return approveTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_task_banjie_log.approve_time
     *
     * @param approveTime the value for wdt_t_task_banjie_log.approve_time
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_task_banjie_log.approve_remark
     *
     * @return the value of wdt_t_task_banjie_log.approve_remark
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public String getApproveRemark() {
        return approveRemark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_task_banjie_log.approve_remark
     *
     * @param approveRemark the value for wdt_t_task_banjie_log.approve_remark
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public void setApproveRemark(String approveRemark) {
        this.approveRemark = approveRemark == null ? null : approveRemark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_task_banjie_log.process_Id
     *
     * @return the value of wdt_t_task_banjie_log.process_Id
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public String getProcessId() {
        return processId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_task_banjie_log.process_Id
     *
     * @param processId the value for wdt_t_task_banjie_log.process_Id
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public void setProcessId(String processId) {
        this.processId = processId == null ? null : processId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_task_banjie_log.create_by
     *
     * @return the value of wdt_t_task_banjie_log.create_by
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_task_banjie_log.create_by
     *
     * @param createBy the value for wdt_t_task_banjie_log.create_by
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_task_banjie_log.create_date
     *
     * @return the value of wdt_t_task_banjie_log.create_date
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_task_banjie_log.create_date
     *
     * @param createDate the value for wdt_t_task_banjie_log.create_date
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_task_banjie_log.update_by
     *
     * @return the value of wdt_t_task_banjie_log.update_by
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_task_banjie_log.update_by
     *
     * @param updateBy the value for wdt_t_task_banjie_log.update_by
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_task_banjie_log.update_date
     *
     * @return the value of wdt_t_task_banjie_log.update_date
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_task_banjie_log.update_date
     *
     * @param updateDate the value for wdt_t_task_banjie_log.update_date
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wdt_t_task_banjie_log.del_flag
     *
     * @return the value of wdt_t_task_banjie_log.del_flag
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wdt_t_task_banjie_log.del_flag
     *
     * @param delFlag the value for wdt_t_task_banjie_log.del_flag
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wdt_t_task_banjie_log
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
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
        TaskBanjie other = (TaskBanjie) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getTaskItemId() == null ? other.getTaskItemId() == null : this.getTaskItemId().equals(other.getTaskItemId()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getSummary() == null ? other.getSummary() == null : this.getSummary().equals(other.getSummary()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreaterId() == null ? other.getCreaterId() == null : this.getCreaterId().equals(other.getCreaterId()))
            && (this.getApproverId() == null ? other.getApproverId() == null : this.getApproverId().equals(other.getApproverId()))
            && (this.getApproveTime() == null ? other.getApproveTime() == null : this.getApproveTime().equals(other.getApproveTime()))
            && (this.getApproveRemark() == null ? other.getApproveRemark() == null : this.getApproveRemark().equals(other.getApproveRemark()))
            && (this.getProcessId() == null ? other.getProcessId() == null : this.getProcessId().equals(other.getProcessId()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getUpdateDate() == null ? other.getUpdateDate() == null : this.getUpdateDate().equals(other.getUpdateDate()))
            && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wdt_t_task_banjie_log
     *
     * @mbggenerated Wed Aug 30 19:35:08 CST 2017
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getTaskItemId() == null) ? 0 : getTaskItemId().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getSummary() == null) ? 0 : getSummary().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreaterId() == null) ? 0 : getCreaterId().hashCode());
        result = prime * result + ((getApproverId() == null) ? 0 : getApproverId().hashCode());
        result = prime * result + ((getApproveTime() == null) ? 0 : getApproveTime().hashCode());
        result = prime * result + ((getApproveRemark() == null) ? 0 : getApproveRemark().hashCode());
        result = prime * result + ((getProcessId() == null) ? 0 : getProcessId().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        return result;
    }
}