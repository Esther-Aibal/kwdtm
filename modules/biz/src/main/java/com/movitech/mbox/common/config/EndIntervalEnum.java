package com.movitech.mbox.common.config;

/**
 * 截止区间 1 一周内 2 一个月内 3三个月内 4 一年内 
 * @author Aibal.He
 *
 */
public enum EndIntervalEnum {

	Status_1("1","一周内"),
	Status_2("2","一个月内 "),
	Status_3("3","三个月内"),
	Status_4("4","一年内");


	private final String value;
	private final String description;


	private EndIntervalEnum(String value, String description) {
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
		for(EndIntervalEnum one: EndIntervalEnum.values()){
			if(one.getValue().equals(value)){
				return one.getDescription();
			}
		}
		return "";
	}
}