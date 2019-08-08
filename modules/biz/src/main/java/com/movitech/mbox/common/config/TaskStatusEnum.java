package com.movitech.mbox.common.config;


/**
 * 任务状态（枚举维护 0:延期申请 1：办结 2：暂停 3：取消）
 * felix
 */
public enum TaskStatusEnum {

	Status_0("0","延期申请"),
	Status_1("1","办结"),
	Status_2("2","暂停"),
	Status_3("3","取消");


	private final String value;
	private final String description;


	private TaskStatusEnum(String value, String description) {
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
		for(TaskStatusEnum one: TaskStatusEnum.values()){
			if(one.getValue().equals(value)){
				return one.getDescription();
			}
		}
		return "";
	}
}