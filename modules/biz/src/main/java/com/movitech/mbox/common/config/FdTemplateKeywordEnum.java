package com.movitech.mbox.common.config;


/**
 * 人员类型（枚举维护：0：阅知人 1：管理层）
 * felix
 */
public enum FdTemplateKeywordEnum {


	Status_2400("1","0","2400","主任务办结"),
	Status_2401("1","1","2401","子任务办结"),
	Status_2402("3","1","2402","子任务取消"),
	Status_2403("0","1","2403","子任务延期"),
	Status_2404("2","1","2404","子任务暂停"),
	Status_2405("3","0","2424","主任务取消"),
	Status_2406("0","0","2425","主任务延期"),
	Status_2407("2","0","2426","主任务暂停");

	private final String taskStatusType;
	private final String taskType;
	private final String value;
	private final String description;


	private FdTemplateKeywordEnum(String taskStatusType, String taskType,String value, String description) {
		this.taskStatusType = taskStatusType;
		this.taskType = taskType;
		this.value = value;
		this.description = description;
	}


	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	public String getTaskStatusType() {
		return taskStatusType;
	}

	public String getTaskType() {
		return taskType;
	}

	public static String getDescription(String value){
		for(FdTemplateKeywordEnum one: FdTemplateKeywordEnum.values()){
			if(one.getValue().equals(value)){
				return one.getDescription();
			}
		}
		return "";
	}
	public static String getValue(String taskStatusType,String taskType){
		for(FdTemplateKeywordEnum one: FdTemplateKeywordEnum.values()){
			if(one.getTaskStatusType().equals(taskStatusType)&&one.getTaskType().equals(taskType)){
				return one.getValue();
			}
		}
		return "";
	}

}