package com.movitech.mbox.common.config;


/**
 * 消息类型（枚举维护：0：预警提醒（按预警时间提醒） 1：催办提醒 2：进度提醒（按主任务汇报频率）3：逾期提醒  4：新任务提醒 5：进度反馈6：新增汇报 7：反馈回复 8：汇报回复）
 * @author Aibal.He
 *
 */
public enum MessageTypeEnum {

	Status_0("0","预警提醒（按预警时间提醒）"),
	Status_1("1","催办提醒 "),
	Status_2("2","进度提醒（按主任务汇报频率）"),
	Status_3("3","逾期提醒"),
	Status_4("4","新任务提醒 "),
	Status_5("5","整体汇报"),
	Status_6("6","节点汇报"),
	Status_7("7","反馈回复 "),
	Status_8("8","汇报回复 "),
	Status_9("9","新增催办建议 "),
	Status_10("10","催办建议回复 "),
	Status_11("11","整体汇报@ "),
	Status_12("12","节点汇报@ "),
	Status_13("13","整体汇报回复@ "),
	Status_14("14","节点汇报回复@ "),
	Status_15("15","催办建议回复@ "),
	Status_16("16","@消息 ");
	private final String value;
	private final String description;


	private MessageTypeEnum(String value, String description) {
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
		for(MessageTypeEnum one: MessageTypeEnum.values()){
			if(one.getValue().equals(value)){
				return one.getDescription();
			}
		}
		return "";
	}
}