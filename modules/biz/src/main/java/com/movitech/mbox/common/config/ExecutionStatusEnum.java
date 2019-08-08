package com.movitech.mbox.common.config;


/**
 * 执行状态（枚举维护：0：草稿 1：待接收 2：已拒绝 3：进行中 4：暂停 5：取消 6：逾期未完成
 * 7：按时完成 8：按时办结  9：逾期完成  10：逾期办结）
 * felix
 */
public enum ExecutionStatusEnum {

	Status_0("0","草稿"),
	Status_1("1","待接收"),
	Status_2("2","已拒绝"),
	Status_3("3","进行中"),
	Status_4("4","暂停"),
	Status_5("5","取消"),
	Status_6("6","逾期未完成"),
	Status_7("7","按时完成"),
	Status_8("8","按时办结"),
	Status_9("9","逾期完成"),
	Status_10("10","逾期办结");


	private final String value;
	private final String description;


	private ExecutionStatusEnum(String value, String description) {
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
		for(ExecutionStatusEnum one: ExecutionStatusEnum.values()){
			if(one.getValue().equals(value)){
				return one.getDescription();
			}
		}
		return "";
	}
}