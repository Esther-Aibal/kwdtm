package com.movitech.mbox.common.config;


/**
 * 错误提示
 */
public class ErrorMessage {

	public static  String SYS_ERROR="系统异常";
	public static  String NO_TASK_ERROR="任务不存在";
	public static  String NO_TASK_ITEM_ERROR="任务节点不存在";
	public static  String NO_TASK_ITEM_ERROR2="任务节点状态不是进行中";
	public static  String NO_TASK_ITEM_ERROR3="任务节点进度不是100%";
	public static  String NO_TASK_ITEM_ERROR4="未到节点开始时间，不允许操作";
	public static  String NO_TASK_ITEM_ERROR5="任务节点未完成，请先对节点进行完成操作";
	public static  String NO_TASK_ITEM_PERSON_ERROR="任务节点负责人不存在";
	public static final String NO_TASK_TEPE_ERROR = "任务状态不存在";
	public static final String TASK_OA_ERROR3 = "任务OA申请不存在";
	public static final String TASK_OA_ERROR4 = "任务已审批";
	
	public static final String NO_TASK_PROCESS_ERROR = "任务整体汇报不存在";
	public static final String TASK_STATE_ERROR = "该状态下没有此操作权限，请联系管理员";
	public static final String TASK_USER_ERROR = "您没有此操作权限，请联系管理员";
	
	public static final String TASK_ITEM_UPDATE_ERROR = "节点更新失败或者节点更新不全";
	public static final String TASK_UPDATE_ERROR = "任务更新失败";
	public static final String BANJIE_TASK_SAVE_ERROR = "办结任务信息保存失败";
	public static final String SUCCESS = "操作成功";
	public static final String BANJIE_SUCCESS = "任务办结成功";
	
	public static final String COMPLETE_TASK_SUCCESS = "任务完成成功";
	public static final String PAY_ATTENTION_TASK_SUCCESS = "关注任务成功";
	public static final String CANCEL_ATTENTION_TASK_SUCCESS = "取消关注任务成功";
	public static final String NO_ERROR = "传值异常，请联系维护人员";
	public static final String DELETE_ERROR = "已删除，无需重复操作";
	public static final String DELETE_CURRENTDAT_ERROR = "只能删除今天的";
	public static final String TASK_TURNBACK_ERROR="该状态下任务不能进行回退操作";
	public static final String TASK_TURNBACK_OK="回退成功";
}