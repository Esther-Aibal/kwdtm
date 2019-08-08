package com.movitech.mbox.common.config;


/**
 * 任务层级（枚举维护 0-主任务 1-节点）
 * felix
 */
public enum TaskTypeEnum {

	Status_0("0","主任务"),
	Status_1("1","节点");


	private final String value;
	private final String description;


	private TaskTypeEnum(String value, String description) {
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
		for(TaskTypeEnum one: TaskTypeEnum.values()){
			if(one.getValue().equals(value)){
				return one.getDescription();
			}
		}
		return "";
	}
}