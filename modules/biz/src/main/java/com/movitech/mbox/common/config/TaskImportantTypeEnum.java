package com.movitech.mbox.common.config;


/**
 * 任务回复枚举（枚举维护： 0：主任务进度反馈 1：主任务评论 2：节点进度汇报 3：节点评论）
 * felix
 */
public enum TaskImportantTypeEnum {

	Status_0("0","重要"),
	Status_1("1","普通");


	private final String value;
	private final String description;


	private TaskImportantTypeEnum(String value, String description) {
		this.value = value;
		this.description = description;
	}


	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	public static String getDescription(String value){
		for(TaskImportantTypeEnum one: TaskImportantTypeEnum.values()){
			if(one.getValue().equals(value)){
				return one.getDescription();
			}
		}
		return "";
	}
}