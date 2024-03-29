/**
 * 
 */
package com.movitech.mbox.modules.wdt.entity.ext;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.movitech.mbox.common.config.FqcyEnum;
import com.movitech.mbox.common.config.ImportTypeEnum;
import com.movitech.mbox.common.persistence.DataEntity;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.wdt.entity.Warning;
import com.movitech.mbox.modules.wdt.entity.WdtTFileInfo;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskItem;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskPerson;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskTemplete;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskTheme;
import com.movitech.mbox.modules.wdt.taskStatus.entity.WdtTTaskExtension;

/**
 * 任务Entity
 * @author Aibal.He
 * @version 2017-08-07
 */
public class TaskH5 extends DataEntity<TaskH5> {
    
    private static final long serialVersionUID = 1L;
    private String themeId;        // 任务主题主键
    private String name;        // 任务名称
    private String isTemplete;        // 任务内容是否从模板取（枚举维护：0：是 1：否）
    private String content;        // 任务内容（不从模板取）
    private String target;        // 任务目标
    private String importantType;        // 重要程度（枚举维护：0：重要 1：普通）
    private Date startDate;        // 开始日期
    private Date endDate;        // 截止日期
    private Date infactCompletionTime;        // 实际完成日期
    private String fqcy;        // 汇报频率（枚举维护：0：每日 1：每周 2：每月 3：每隔天）
    private Integer days=0;        // 天数
    private User owner;        // 主责人
    private String isHaveSplit;        // 是否有节点（枚举维护：0：是 1：否）
    private String isApplyExtension;        // 是否申请过延期（枚举维护：0：申请过 1：未申请过延期）
    private String executionStatus;        // 执行状态（枚举维护：0：草稿 1：待接收 2：已拒绝 3：进行中 4：暂停 5：取消 6：逾期未完成 7：已完成未办结 8：办结）
    private User taskCreateUser;        // 任务创建人
    private String progress;        // 任务进度
    private String isRelationTask; //是否是关联任务（枚举维护：0：是 1：否）
  //-----------------------------非持久--------------------------------------
    private List<String> subscribers; //订阅者
    private List<String> managers; //管理者
    private List<String> participant; //参与者
    private List<WdtTTaskPerson> allPerson; //参与者
    private String userId;//当前用户id
    private List<String> fileIds;//附件id
    
    private int start;
    private int length;
    
    private int planDays;
    private int leavingDays;
    private List<String> relatedProject; //关联项目编号id
    
    private List<WdtTFileInfo> fileList; //项目关联附件
    private String fqcyDesc; //汇报频率
    private String progressFmt; // 任务进度取整
    private String subscribersName; //订阅者
    private String managersName; //管理者
    private String participantName; //参与者
    
    private String importantTypeDesc; //重要程度详细
    private Date  warningDate;
    
    private WdtTTaskTheme wdtTTaskTheme;//有主题的时候显示主题
    private List<WdtTTaskTemplete> wdtTTaskTempleteList;//任务内容模板
    private String taskId;//父任务id
    
    private String ipAddr;
    private String reponseCount;//回复数
    private String readCount = "0";//阅读数
    private TaskH5 parent;//主任务
    private List<String> parentPersons; //参与者
    private WdtTTaskExtension wdtTTaskExtension;//延期申请
    private Warning warning;//预警
    private int showColor =0;//颜色 0 绿色 2 橙色 1 红色
    private String projectNames;
    private String projectIds;
    private List<WdtTTaskItem> items;
    private String interval;//时间区间 1一周内 2一个月内3三个月内4一年内
    public Date getWarningDate() {
        return warningDate;
    }

    public void setWarningDate(Date warningDate) {
        this.warningDate = warningDate;
    }

    public TaskH5() {
        super();
    }

    public TaskH5(String id){
        super(id);
    }

    @Length(min=0, max=10, message="任务主题主键长度必须介于 0 和 10 之间")
    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }
    
    @Length(min=0, max=200, message="任务名称长度必须介于 0 和 200 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Length(min=0, max=10, message="任务内容是否从模板取（枚举维护：0：是 1：否）长度必须介于 0 和 10 之间")
    public String getIsTemplete() {
        return isTemplete;
    }

    public void setIsTemplete(String isTemplete) {
        this.isTemplete = isTemplete;
    }
    
    public String getContent() {
    	if(content == null){
    		content ="";
    	}
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
    
    @Length(min=0, max=10, message="重要程度（枚举维护：0：重要 1：普通）长度必须介于 0 和 10 之间")
    public String getImportantType() {
        return importantType;
    }

    public void setImportantType(String importantType) {
        this.importantType = importantType;
        if(importantType != null){
        	this.importantTypeDesc = ImportTypeEnum.getDescription(importantType);
        }
    }
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getInfactCompletionTime() {
        return infactCompletionTime;
    }

    public void setInfactCompletionTime(Date infactCompletionTime) {
        this.infactCompletionTime = infactCompletionTime;
    }
    
    @Length(min=0, max=10, message="汇报频率（枚举维护：0：每日 1：每周 2：每月 3：每隔天）长度必须介于 0 和 10 之间")
    public String getFqcy() {
        return fqcy;
    }

    public void setFqcy(String fqcy) {
        this.fqcy = fqcy;
        if(fqcy != null){
        	this.fqcyDesc = FqcyEnum.getDescription(fqcy);
        }
    }
    
    @Length(min=0, max=11, message="天数长度必须介于 0 和 11 之间")
    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
		this.days = days;
	}
	
    public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Length(min=0, max=10, message="是否有节点（枚举维护：0：是 1：否）长度必须介于 0 和 10 之间")
    public String getIsHaveSplit() {
        return isHaveSplit;
    }

    public void setIsHaveSplit(String isHaveSplit) {
        this.isHaveSplit = isHaveSplit;
    }
    
    @Length(min=0, max=10, message="是否申请过延期（枚举维护：0：申请过 1：未申请过延期）长度必须介于 0 和 10 之间")
    public String getIsApplyExtension() {
        return isApplyExtension;
    }

    public void setIsApplyExtension(String isApplyExtension) {
        this.isApplyExtension = isApplyExtension;
    }
    
    @Length(min=0, max=10, message="执行状态（枚举维护：0：草稿 1：待接收 2：已拒绝 3：进行中 4：暂停 5：取消 6：逾期未完成 7：已完成未办结 8：办结）长度必须介于 0 和 10 之间")
    public String getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(String executionStatus) {
        this.executionStatus = executionStatus;
    }
    
    public User getTaskCreateUser() {
		return taskCreateUser;
	}

	public void setTaskCreateUser(User taskCreateUser) {
		this.taskCreateUser = taskCreateUser;
	}

	@Length(min=0, max=11, message="任务进度长度必须介于 0 和 11 之间")
    public String getProgress() {
		
        if(progress == null){
        	progress ="0";
        }
        double progressAsDouble = Double.parseDouble(progress);
		DecimalFormat df = new DecimalFormat("0");
		df.setRoundingMode(RoundingMode.HALF_UP);  
        return df.format(progressAsDouble);
    }

    public void setProgress(String progress) {
        this.progress = progress;
        if(progress != null){
        	double progressAsDouble = Double.parseDouble(progress);
			DecimalFormat df = new DecimalFormat("0");
			this.progressFmt = df.format(progressAsDouble);
        }
    }
	public List<String> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(List<String> subscribers) {
		this.subscribers = subscribers;
	}

	public List<String> getManagers() {
		return managers;
	}

	public void setManagers(List<String> managers) {
		this.managers = managers;
	}

	public List<String> getParticipant() {
		return participant;
	}

	public void setParticipant(List<String> participant) {
		this.participant = participant;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<WdtTTaskPerson> getAllPerson() {
		return allPerson;
	}

	public void setAllPerson(List<WdtTTaskPerson> allPerson) {
		this.allPerson = allPerson;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getPlanDays() {
		return planDays;
	}

	public void setPlanDays(int planDays) {
		this.planDays = planDays;
	}

	public int getLeavingDays() {
		return leavingDays;
	}

	public void setLeavingDays(int leavingDays) {
		this.leavingDays = leavingDays;
	}

	public List<String> getRelatedProject() {
		return relatedProject;
	}

	public void setRelatedProject(List<String> relatedProject) {
		this.relatedProject = relatedProject;
	}

	public List<WdtTFileInfo> getFileList() {
		return fileList;
	}

	public void setFileList(List<WdtTFileInfo> fileList) {
		this.fileList = fileList;
	}

	public List<String> getFileIds() {
		return fileIds;
	}

	public void setFileIds(List<String> fileIds) {
		this.fileIds = fileIds;
	}

	public String getSubscribersName() {
		return subscribersName;
	}

	public void setSubscribersName(String subscribersName) {
		this.subscribersName = subscribersName;
	}

	public String getManagersName() {
		return managersName;
	}

	public void setManagersName(String managersName) {
		this.managersName = managersName;
	}

	public String getParticipantName() {
		return participantName;
	}

	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}

	public String getFqcyDesc() {
		return fqcyDesc;
	}

	public void setFqcyDesc(String fqcyDesc) {
		this.fqcyDesc = fqcyDesc;
	}

	public String getProgressFmt() {
		return progressFmt;
	}

	public void setProgressFmt(String progressFmt) {
		this.progressFmt = progressFmt;
	}

	public String getImportantTypeDesc() {
		return importantTypeDesc;
	}

	public void setImportantTypeDesc(String importantTypeDesc) {
		this.importantTypeDesc = importantTypeDesc;
	}

	public WdtTTaskTheme getWdtTTaskTheme() {
		return wdtTTaskTheme;
	}

	public void setWdtTTaskTheme(WdtTTaskTheme wdtTTaskTheme) {
		this.wdtTTaskTheme = wdtTTaskTheme;
	}

	public List<WdtTTaskTemplete> getWdtTTaskTempleteList() {
		return wdtTTaskTempleteList;
	}

	public void setWdtTTaskTempleteList(List<WdtTTaskTemplete> wdtTTaskTempleteList) {
		this.wdtTTaskTempleteList = wdtTTaskTempleteList;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getIsRelationTask() {
		return isRelationTask;
	}

	public void setIsRelationTask(String isRelationTask) {
		this.isRelationTask = isRelationTask;
	}

	public String getReponseCount() {
		return reponseCount;
	}

	public void setReponseCount(String reponseCount) {
		this.reponseCount = reponseCount;
	}

	public String getReadCount() {
		return readCount;
	}

	public void setReadCount(String readCount) {
		this.readCount = readCount;
	}

	public TaskH5 getParent() {
		return parent;
	}

	public void setParent(TaskH5 parent) {
		this.parent = parent;
	}

	public List<String> getParentPersons() {
		return parentPersons;
	}

	public void setParentPersons(List<String> parentPersons) {
		this.parentPersons = parentPersons;
	}

	public WdtTTaskExtension getWdtTTaskExtension() {
		return wdtTTaskExtension;
	}

	public void setWdtTTaskExtension(WdtTTaskExtension wdtTTaskExtension) {
		this.wdtTTaskExtension = wdtTTaskExtension;
	}

	public Warning getWarning() {
		return warning;
	}

	public void setWarning(Warning warning) {
		this.warning = warning;
	}

	public int getShowColor() {
		return showColor;
	}

	public void setShowColor(int showColor) {
		this.showColor = showColor;
	}

	public String getProjectNames() {
		return projectNames;
	}

	public void setProjectNames(String projectNames) {
		this.projectNames = projectNames;
	}

	public String getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(String projectIds) {
		this.projectIds = projectIds;
	}

	public List<WdtTTaskItem> getItems() {
		return items;
	}

	public void setItems(List<WdtTTaskItem> items) {
		this.items = items;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}


}