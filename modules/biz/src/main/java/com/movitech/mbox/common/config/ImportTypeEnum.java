package com.movitech.mbox.common.config;

public enum ImportTypeEnum {
	
	Status_0("0","重要"),
	Status_1("1","普通");
	
	private final String value;
	private final String description;


	private ImportTypeEnum(String value, String description) {
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
		for(ImportTypeEnum one: ImportTypeEnum.values()){
			if(one.getValue().equals(value)){
				return one.getDescription();
			}
		}
		return "";
	}

}
