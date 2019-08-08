/**
 * 
 */
package com.movitech.mbox.modules.wdt.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movitech.mbox.common.config.ExecutionStatusEnum;
import com.movitech.mbox.common.config.MessageTypeEnum;
import com.movitech.mbox.common.config.TaskCommentTypeEnum;
import com.movitech.mbox.common.config.TaskTypeEnum;
import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.service.CrudService;
import com.movitech.mbox.common.utils.DateUtils;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.modules.wdt.entity.WdtTTask;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskComments;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskItem;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskReponse;
import com.movitech.mbox.modules.wdt.entity.ext.AtMessage;
import com.movitech.mbox.modules.wdt.entity.ext.ExtWdtTTaskReponse;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.dao.MessageDao;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskCommentsDao;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskDao;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskItemDao;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskReponseDao;
import com.movitech.mbox.modules.wdt.dao.ext.ExtWdtTTaskReponseDao;

/**
 * 任务或节点回复Service
 * @author Aibal.He
 * @version 2017-08-22
 */
@Service
@Transactional(readOnly = true)
public class WdtTTaskReponseService extends CrudService<WdtTTaskReponseDao, WdtTTaskReponse> {
	
	@Autowired
	private ExtWdtTTaskReponseDao extWdtTTaskReponseDao;
	@Autowired
	private WdtTTaskItemDao wdtTTaskItemDao;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private WdtTTaskCommentsService wdtTTaskCommentsService;
	
	@Autowired
	private WdtTTaskDao wdtTTaskDao;
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private WdtTTaskCommentsDao wdtTTaskCommentsdao;
	
	@Autowired
	private WdtTTaskCommentsPersonService wdtTTaskCommentsPersonService;
	
    public WdtTTaskReponse get(String id) {
        return super.get(id);
    }
    
    public List<WdtTTaskReponse> findList(WdtTTaskReponse wdtTTaskReponse) {
        return super.findList(wdtTTaskReponse);
    }
    
    public Page<WdtTTaskReponse> findPage(Page<WdtTTaskReponse> page, WdtTTaskReponse wdtTTaskReponse) {
        return super.findPage(page, wdtTTaskReponse);
    }
    
    @Transactional(readOnly = false)
    public void save(WdtTTaskReponse wdtTTaskReponse) {
        super.save(wdtTTaskReponse);
    }
    @Transactional(readOnly = false)
    public int saveReponse(WdtTTaskReponse wdtTTaskReponse) {
    	wdtTTaskReponse.preInsert();
    	if(StringUtils.isNotEmpty(wdtTTaskReponse.getTaskItemId())){
    		//节点汇报回复
    		WdtTTaskItem wdtTTaskItem=wdtTTaskItemDao.get(wdtTTaskReponse.getTaskItemId());
    		if(wdtTTaskItem==null){
    			return -2;
    		}
    		WdtTTaskComments one=wdtTTaskCommentsdao.get(wdtTTaskReponse.getComentsId());
    		String messageType = null ;
    		if(one.getType().equals("2")){
    			messageType = MessageTypeEnum.Status_8.getValue();
    			
    		}else if (one.getType().equals("4")){
    			messageType = MessageTypeEnum.Status_10.getValue();
    		}
    		List<String> personIds= wdtTTaskCommentsService.getPersonIdsByTaskId(wdtTTaskItem.getTaskId(),null) ;
    		//除去@中的参与人
    		if(wdtTTaskReponse.getAtPerson()!=null){
    			personIds.removeAll(wdtTTaskReponse.getAtPerson());
    		}
    		messageService.addTaskMessageFormat(TaskTypeEnum.Status_1.getValue(),wdtTTaskItem.getTaskId(),wdtTTaskReponse.getTaskItemId(),personIds,UserUtils.getUser().getId(),messageType,wdtTTaskReponse.getId(),null);
    		//加入@者
			if(wdtTTaskReponse.getAtPerson()!=null && wdtTTaskReponse.getAtPerson().size()>0){
				String atMmessageType = null ;
				if(one.getType().equals("2")){
	    			atMmessageType = MessageTypeEnum.Status_14.getValue();
	    			
	    		}else if (one.getType().equals("4")){
	    			atMmessageType = MessageTypeEnum.Status_15.getValue();
	    		}
				messageService.addTaskMessageFormat(TaskTypeEnum.Status_1.getValue(),wdtTTaskItem.getTaskId(),wdtTTaskReponse.getTaskItemId(),wdtTTaskReponse.getAtPerson(),UserUtils.getUser().getId(),atMmessageType,wdtTTaskReponse.getId(),null );
				//加commentperson表
				wdtTTaskCommentsPersonService.batchInsert(wdtTTaskReponse.getId(), wdtTTaskReponse.getAtPerson());
			}
    	}
    	if(StringUtils.isNotEmpty(wdtTTaskReponse.getTaskId())){
    		WdtTTask wdtTTask=wdtTTaskDao.get(wdtTTaskReponse.getTaskId());
			if(wdtTTask==null){
			    return -1;
            }
			List<String> personIds= wdtTTaskCommentsService.getPersonIdsByTaskId(wdtTTaskReponse.getTaskId(),null);
    		//除去@中的参与人
			if(wdtTTaskReponse.getAtPerson()!=null){
				personIds.removeAll(wdtTTaskReponse.getAtPerson());
			}
			messageService.addTaskMessageFormat(TaskTypeEnum.Status_0.getValue(),wdtTTaskReponse.getTaskId(),null,personIds,UserUtils.getUser().getId(),MessageTypeEnum.Status_7.getValue(),wdtTTaskReponse.getId(),null );
			//加入@者
			if(wdtTTaskReponse.getAtPerson()!=null && wdtTTaskReponse.getAtPerson().size()>0){
				messageService.addTaskMessageFormat(TaskTypeEnum.Status_0.getValue(),wdtTTaskReponse.getTaskId(),null,wdtTTaskReponse.getAtPerson(),UserUtils.getUser().getId(),MessageTypeEnum.Status_13.getValue(),wdtTTaskReponse.getId(),null );
				//加commentperson表
				wdtTTaskCommentsPersonService.batchInsert(wdtTTaskReponse.getId(), wdtTTaskReponse.getAtPerson());
			}
    		
    	}
    	dao.insert(wdtTTaskReponse);
        return 0;
    }
    
    @Transactional(readOnly = false)
    public int saveAtMessage(AtMessage atMessage) {
    	String type = TaskTypeEnum.Status_0.getValue();
    	if(StringUtils.isNotEmpty(atMessage.getTaskItemId())){
    		type = TaskTypeEnum.Status_1.getValue();
    		//节点汇报回复
    		WdtTTaskItem wdtTTaskItem=wdtTTaskItemDao.get(atMessage.getTaskItemId());
    		if(wdtTTaskItem==null){
    			return -2;
    		}
    		atMessage.setTaskId(wdtTTaskItem.getTaskId());
    	}else{
    		WdtTTask wdtTTask=wdtTTaskDao.get(atMessage.getTaskId());
    		if(wdtTTask==null){
    		    return -1;
            }	
    	}
    	
		if(atMessage.getAtPerson()!=null && atMessage.getAtPerson().size()>0){
			String atMmessageType = MessageTypeEnum.Status_16.getValue();
			messageService.addTaskMessageFormat(type,atMessage.getTaskId(),atMessage.getTaskItemId(),atMessage.getAtPerson(),UserUtils.getUser().getId(),atMmessageType,null,atMessage.getContent() );
		}
        return 0;
    }
    @Transactional(readOnly = false)
	public int deleteReponse(WdtTTaskReponse wdtTTaskReponse){
    	String currentUserId = UserUtils.getUser().getId();
    	WdtTTaskReponse entity = dao.get(wdtTTaskReponse.getId());
    	
    	if(entity != null){
    		if(DateUtils.subDate(entity.getCreateDate(), new Date())!=0){
        		return -2;
        	}
    		
    		if(!entity.getUser().getId().equals(currentUserId)){
				return -3;
			}
			//如果是汇报，删除后需要查看最新的进度汇报进度并更新节点
			int i = dao.delete(entity);
			if(i>0){
				//回复回复id
				List<String> pinIds = dao.getIdsByReponseId(wdtTTaskReponse.getId());
				pinIds.add(entity.getId());
				messageDao.deleteByPinId(pinIds);
			}else{
				return -1;
			}
			return 1;
    	}else{
    		return -1;
    	}
    	
    }
    @Transactional(readOnly = false)
    public void delete(WdtTTaskReponse wdtTTaskReponse) {
        super.delete(wdtTTaskReponse);
    }
    
    /**
     * 取主任务进度反馈的评论和主任务评论的前10条评论
     * @return
     */
	public List<ExtWdtTTaskReponse> getResponseTOP10(String userId) {
		return extWdtTTaskReponseDao.getResponseTOP10(userId);
	}
}