package com.movitech.mbox.common.config;


/**
 * 操作内容
 * @author felix.jin
 * 2017年8月22日
 */
public enum OperateLogEnum {
	URL0("0","rest/wdt/task/delete","删除任务"),
	URL11("00","rest/wdt/task/saveDraftTask","保存草稿任务"),
	URL111("01","rest/wdt/task/saveTask","提交任务"),
	URL113("01","rest/wdt/task/submit","提交任务"),
	URL115("04","rest/wdt/task/partUpdate","进行中提交任务"),
	
	URL17("02","rest/task/refuse","拒绝任务"),
	URL18("03","rest/wdt/task/receiveTask", "接收任务"),
	URL19("05","rest/wdt/task/completeTask", "完成任务"),
	URL112("07","rest/task/handover", "移交任务"),
	URL114("08","rest/task/continue", "继续任务"),
	URL31("06","wdt/wdtTTaskBanjieLog/save", "任务办结"),
	URL20("09","rest/wdt/urgetask/saveUrges", "任务催办"),
	
	URL12("31","rest/task/banjie","申请办结任务"),
	URL13("32","rest/task/cancel","申请取消任务"),
	URL14("34","rest/task/pause","申请暂停任务"),
	URL15("33","rest/task/extension","申请延期任务"),
	
	
	URLB4("54","rest/task/pause","同意暂停申请"),
	
	URLN12("61","rest/task/banjie","办结任务"),
	URLN13("62","rest/task/cancel","取消任务"),
	URLN14("63","rest/task/pause","暂停任务"),
	URLN15("64","rest/task/extension","延期任务"),
	URL41("01","rest/wdt/taskChild/save","新增子任务"),
	
	URL16("40","rest/wdt/task/payAttentionTask","关注任务"),
	URL161("41","rest/wdt/task/payAttentionTask","取消关注"),
	URL21("","rest/wdtTTaskItem/save","新增节点"),
	URL211("","rest/wdtTTaskItem/save","修改节点"),
	URL22("22","rest/wdtTTaskItem/finish","完成节点"),
	URL23("","rest/wdtTTaskItem/handover","移交节点"),
	URL24("","rest/wdtTTaskItem/handover","新增节点汇报"),
	URL25("","rest/wdtTTaskItem/handover","更新节点汇报"),
	
	URL51("","rest/wdt/task/saveProgressFeedback","新增任务整体汇报"),
	URL52("","rest/wdt/task/saveProgressFeedback","更新任务整体汇报"),
	URL32("","rest/wdt/task/saveProgressReponse","节点汇报回复"),
	URL33("","rest/wdt/task/saveProgressReponse","整体汇报回复"),
	
	
	// mobile log
	
	URLH512("02","h5/taskStatus/refuse","拒绝任务"),
	URLH513("03","h5/taskStatus/receive", "接收任务"),
	URLH517("05","h5/taskStatus/complete", "完成任务"),
	URLH521("07","h5/taskStatus/handover", "移交任务"),
	
	URLH5CI("","h5/taskItem/finish","完成节点"),
	URLH5MI("","h5/taskItem/handover","移交节点"),
	URLH551("","h5/task/saveProgressFeedback","新增任务整体汇报"),
	URLH524("","h5/taskItem/saveProgressComment","新增节点汇报"),
	URLH532("","h5/task/saveProgressReponse","节点汇报回复"),
	URLH533("","h5/task/saveProgressReponse","整体汇报回复"),
	//URLH5C("","h5/taskStatus/continue", "继续任务"),
	//URLH5E("","h5/taskStatus/extension", "延期任务"),
	//URLH5P("","h5/taskStatus/handover", "暂停任务"),
	//URLH5CANCEL("","h5/taskStatus/handover", "取消任务"),
	//URLH5BANJIE("","h5/taskStatus/banjie", "办结任务"),
	//URLH5AI("","h5/taskItem/save","新增节点"),
	//URLH5UI("","h5/taskItem/save","修改节点"),
	URLH552("","h5/task/saveProgressFeedback","更新任务整体汇报"),
	URLH525("","h5/taskItem/saveProgressComment","更新节点汇报"),
	URL6("","rest/wdt/task/saveAtMessage","@消息"),
	URL71("","/rest/task/recall","撤回"),
	URL72("","/rest/task/turnBack","回退");
	private final String lable;
	private final String value;
	private final String description;


	private OperateLogEnum(String value,String lable , String description) {
		this.lable= lable;
		this.value = value;
		this.description = description;
	}

	public String getLable() {
		return lable;
	}

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	public static String getDescription(String value){
		for(OperateLogEnum one: OperateLogEnum.values()){
			if(value.indexOf(one.getValue())>0){
				return one.getDescription();
			}
		}
		return null;
	}
}