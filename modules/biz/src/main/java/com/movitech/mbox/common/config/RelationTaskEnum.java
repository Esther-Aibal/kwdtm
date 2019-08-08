package com.movitech.mbox.common.config;

/**
 * 是否是关联任务（枚举维护：0：子任务 1：主任务）
 * 枚举
 * @author Aibal.He
 *
 */
public enum RelationTaskEnum {

	Status_0("0","子任务"),
	Status_1("1","主任务");


	private final String value;
	private final String description;


	private RelationTaskEnum(String value, String description) {
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
