/**
 * 
 */
package com.movitech.mbox.common.config;

/**
 * 
 * 删除标记（枚举维护 0-未删除 1-已删除）
 * @author Jack.Gong
 *
 */
public enum DeleteStatusEnum {
	
	Status_0("0", "未删除"),
	Status_1("1", "已删除");
	
	private final String value;
	private final String description;


	private DeleteStatusEnum(String value, String description) {
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
		for(DeleteStatusEnum one: DeleteStatusEnum.values()){
			if(one.getValue().equals(value)){
				return one.getDescription();
			}
		}
		return "";
	}
}
