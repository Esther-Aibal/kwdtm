package com.movitech.mbox.common.config;


/**
 * 任务回复枚举（枚举维护： 0：主任务进度反馈 1：主任务评论 2：节点进度汇报 3：节点评论）
 * felix
 */
public enum TaskCommentTypeEnum {

	Status_0("0","主任务整体汇报"),
	Status_1("1","主任务评论"),
	Status_2("2","节点汇报"),
	Status_3("3","节点评论");


	private final String value;
	private final String description;


	private TaskCommentTypeEnum(String value, String description) {
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
		for(TaskCommentTypeEnum one: TaskCommentTypeEnum.values()){
			if(one.getValue().equals(value)){
				return one.getDescription();
			}
		}
		return "";
	}
}