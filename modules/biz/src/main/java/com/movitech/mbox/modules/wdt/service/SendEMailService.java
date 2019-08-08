/**
 * 
 */
package com.movitech.mbox.modules.wdt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.movitech.mbox.common.config.Global;
import com.movitech.mbox.common.utils.HttpRequest;
import com.movitech.mbox.common.utils.JsonUtil;
import com.movitech.mbox.modules.wdt.taskStatus.req.TaskMailReq;

/**
 * 发送电子邮件
 */
@Service
public class SendEMailService {
	@Autowired
	public ThreadPoolTaskExecutor executor;
	
    public void sendEmailForSingle(String email,String name,String title ,String content){
    	//发送邮件提醒 异步
		executor.execute(new Runnable() {
			@Override
			public void run() {
				String url=Global.getConfig("exchange_mail_url");
				TaskMailReq req=new TaskMailReq(email,name);
				req.setTitle(title);
				req.setContent(content);
				HttpRequest.sendPost(url,JsonUtil.toJson(req));

				
			}
		});
    }
    public void sendEmailForMult(List<TaskMailReq> entitys){
    	//发送邮件提醒 异步
		executor.execute(new Runnable() {
			@Override
			public void run() {
				String url=Global.getConfig("exchange_mail_url");
				if(entitys!=null && entitys.size()>0){
					for (TaskMailReq taskMailReq : entitys) {
						HttpRequest.sendPost(url,JsonUtil.toJson(taskMailReq));
					}
				}
		
			}
		});
    }
}