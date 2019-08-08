package com.movitech.mbox.common.config;


/**
 * 是否枚举（枚举维护：0：是 1：否）
 * felix
 */
public enum YesNoEnum {

	Status_0("0","是"),
	Status_1("1","否");


	private final String value;
	private final String description;


	private YesNoEnum(String value, String description) {
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
		for(YesNoEnum one: YesNoEnum.values()){
			if(one.getValue().equals(value)){
				return one.getDescription();
			}
		}
		return "";
	}
}