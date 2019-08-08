/**
 * 
 */
package com.movitech.mbox.modules.wdt.entity.report;

import org.hibernate.validator.constraints.Length;

import com.movitech.mbox.common.persistence.DataEntity;

/**
 * 汇报情况类Entity
 * @author Aibal.He
 * @version 2017-10-26
 */
public class WdtTItemReport extends DataEntity<WdtTItemReport> {
    
    private static final long serialVersionUID = 1L;
    private String taskId;        // 任务主键
    private String itemName;        // 节点名称
    private Integer reportType;        // 汇报类型（0：不正常汇报 1：正常汇报）
    private String reportComment;        // 汇报情况（0：未汇报 1：延期汇报 2：正常汇报）
    private String fqcy;        // 汇报频率（枚举维护：0：每日 1：每周 2：每月 3：每隔天）
    private Integer days;        // 天数
    
    public WdtTItemReport() {
        super();
    }

    public WdtTItemReport(String id){
        super(id);
    }

    @Length(min=0, max=64, message="任务主键长度必须介于 0 和 64 之间")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    @Length(min=0, max=200, message="节点名称长度必须介于 0 和 200 之间")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }
    
    @Length(min=0, max=10, message="汇报情况（0：未汇报 1：延期汇报 2：正常汇报）长度必须介于 0 和 10 之间")
    public String getReportComment() {
        return reportComment;
    }

    public void setReportComment(String reportComment) {
        this.reportComment = reportComment;
    }
    
    @Length(min=0, max=10, message="汇报频率（枚举维护：0：每日 1：每周 2：每月 3：每隔天）长度必须介于 0 和 10 之间")
    public String getFqcy() {
        return fqcy;
    }

    public void setFqcy(String fqcy) {
        this.fqcy = fqcy;
    }
    
    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
    
}