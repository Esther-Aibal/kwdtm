/**
 * 
 */
package com.movitech.mbox.modules.wdt.service;


import com.movitech.mbox.common.config.Global;
import com.movitech.mbox.common.utils.HttpRequest;
import com.movitech.mbox.common.utils.IdGen;
import com.movitech.mbox.modules.wdt.dao.*;

import com.movitech.mbox.modules.wdt.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service
 * @author xuqifei
 * @version 2017-08-07
 */
@Service
@Transactional(readOnly = false)
public class MessageService{
  protected Logger logger = LoggerFactory.getLogger(getClass());
  static String MESSAGETYPE="content=您有未读消息[0]条&all=false&msgCode="+Global.getConfig("msgCode")+"&param=h5&title=任务管理&username=[1]";
  static String EMM_URL=Global.getConfig("emm_url");;
 @Autowired
 MessageDao messageDao;
 @Autowired
 ThreadPoolTaskExecutor executor;
 @Autowired
 WdtTTaskDao  tTaskDao;
	/**
	 * 批量插入
	 * @param
	 */
	public void batchInsert(List<Message> messages) {
		//Global.getMessagePropertiesVal("")
		int SIZE=30;
		if (messages == null || messages.size() < 1)
			return;
		int m = messages.size() / SIZE;
		for (int j = 0; j <= m; j++) {
			if (j == m) {
				List<Message> tempList = messages.subList(SIZE * j,
						messages.size());
				if (tempList != null && tempList.size() > 0) {
					messageDao.batchInsert(tempList);
				}
			} else {
				List<Message> tempList = messages
						.subList(j * SIZE, (j + 1) * SIZE);
				if (tempList != null && tempList.size() > 0) {
					messageDao.batchInsert(tempList);
				}
			}
		}
	}

	public void updateRead(String id) {
		messageDao.updateRead(id);
	}
	
	public boolean updateReadIds(List<String> ids) {
		if(ids!=null && ids.size()>0){
			messageDao.updateReadIds(ids);
			return true;
		}else{
			return false;
		}
	}
	/***
	 *
	 * 推送未读消息数量
	 * */
    public void sendMessage(Set<String> userIds){
    	if(userIds==null||userIds.size()<1||"1".equals(Global.getConfig("emm_message_on")))return;
    	executor.execute(new Runnable() {
			@Override
			public void run() {
				for (String id:userIds){
					WdtTaskParam param=new WdtTaskParam();
					param.setUserId(id);
					//获取未读消息
					int count=tTaskDao.getMessageCounts(param);
					String urlparam=MESSAGETYPE.replace("[0]",count+"").replace("[1]",id);
					String re=HttpRequest.post(EMM_URL,urlparam);
					logger.info(re);
				}
			}
		});

	}

    
    /**
	 * 
	 * @param type 任务层级（枚举维护 0-主任务 1-节点）
	 * @param taskId 主任务id
	 * @param reciverId 接收者id 
	 * @param senderId 当前登录者
	 * @param messageType 0：预警提醒（按预警时间提醒） 1：催办提醒 2：进度提醒（按主任务汇报频率）3：逾期提醒  4：新任务提醒 5：新增反馈 6：新增汇报 7：反馈回复 8：汇报回复
	 * @param pinId 定位id
	 */
	public void addTaskMessageFormat(String type,String taskId,String taskItemId,List<String> reciverIds,String senderId,String messageType,String pinId,String content ){
		//更新message表
		if(reciverIds!= null && reciverIds.size()>0){
			Set<String> set=new HashSet<>();
			set.addAll(reciverIds);
			
			 Iterator<String> it = set.iterator(); 
			 while (it.hasNext()) {
				 Message message = new Message();
					message.setType(type);
					message.setTaskId(taskId);
					message.setTaskItemId(taskItemId);
					message.setReciverId(it.next());
					message.setSenderId(senderId);
					message.setMessageType(messageType);//新任务提醒
					message.setId(IdGen.uuid());
					message.setCreateDate(new Date());
					message.setCreateBy(senderId);
					message.setUpdateBy(senderId);
					message.setUpdateDate(message.getCreateDate());
					message.setIsRead("0");
					message.setDelFlag("0");
					message.setPinId(pinId);
					message.setContent(content);
					messageDao.insert(message);
			 }	
			//推送消息
			sendMessage(set);
		}
		
	}
	
	public Message checkSubmitMessage(String taskId){
		Message message = new Message();
		message.setTaskId(taskId);
		message.setMessageType("4");
		return messageDao.getSubmitMessage(message);
	}
	public void updateMessage(Message message){
		message.setUpdateDate(new Date());
		message.setIsRead("0");
		message.setDelFlag("0");
		messageDao.update(message);
		
		//推送消息
		Set<String> set=new HashSet<>();
		set.add(message.getReciverId());
		sendMessage(set);
	}
	public Message getById(String id){
		return messageDao.get(id);
	}
}