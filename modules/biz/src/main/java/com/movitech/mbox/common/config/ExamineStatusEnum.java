/**
 * 
 */
package com.movitech.mbox.common.config;

/**
 * 最新状态: （枚举维护：0：待审批 1：审批通过 2：审批不通过）
 * @author Jack.Gong
 *
 */
public enum ExamineStatusEnum {
	
	Status_0("0", "待审批"),
	Status_1("1", "审批通过"),
	Status_2("2", "审批不通过");
	
	
	private final String value;
	private final String description;


	private ExamineStatusEnum(String value, String description) {
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
		for(ExamineStatusEnum one: ExamineStatusEnum.values()){
			if(one.getValue().equals(value)){
				return one.getDescription();
			}
		}
		return "";
	}

}
