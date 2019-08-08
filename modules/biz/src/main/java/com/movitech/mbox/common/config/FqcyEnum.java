package com.movitech.mbox.common.config;


/**
 * 汇报频率（枚举维护：0：每日 1：每周 2：每月 3：每隔天）
 * felix
 */
public enum FqcyEnum {

	Status_0("0","每日"),
	Status_1("1","每周"),
	Status_2("2","每月"),
	Status_3("3","每隔天");


	private final String value;
	private final String description;


	private FqcyEnum(String value, String description) {
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
		for(FqcyEnum one: FqcyEnum.values()){
			if(one.getValue().equals(value)){
				return one.getDescription();
			}
		}
		return "";
	}

}