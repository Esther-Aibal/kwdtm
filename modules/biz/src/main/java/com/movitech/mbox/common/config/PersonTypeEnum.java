package com.movitech.mbox.common.config;


/**
 * 人员类型（枚举维护：0：阅知人 1：管理层）
 * felix
 */
public enum PersonTypeEnum {

	Status_0("0","阅知人"),
	Status_1("1","管理层"),
	Status_2("2","参与者"),
	Status_3("3","子任务");//3：主任务带过来的所有人-只有子任务有这儿类型

	private final String value;
	private final String description;


	private PersonTypeEnum(String value, String description) {
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
		for(PersonTypeEnum one: PersonTypeEnum.values()){
			if(one.getValue().equals(value)){
				return one.getDescription();
			}
		}
		return "";
	}
}