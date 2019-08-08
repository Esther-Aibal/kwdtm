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
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.sys.utils.WdtTTaskReponseTreeBuilder;
import com.movitech.mbox.modules.wdt.dao.MessageDao;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskCommentsDao;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskDao;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskPersonDao;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskReponseDao;
import com.movitech.mbox.modules.wdt.dao.ext.ExtWdtTTaskCommentsDao;
import com.movitech.mbox.modules.wdt.entity.WdtTTask;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskComments;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskCommentsPerson;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskItem;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskPerson;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskReponse;
import com.movitech.mbox.modules.wdt.entity.ext.ExtWdtTTaskComments;
import com.movitech.mbox.modules.wdt.entity.ext.WdtTTaskReponseTreeNode;

/**
 * 进度汇报Service
 * @author Aibal.He
 * @version 2017-08-07
 */
@Service
@Transactional(readOnly = true)
public class WdtTTaskCommentsService extends CrudService<WdtTTaskCommentsDao, WdtTTaskComments> {

	@Autowired
	private WdtTTaskItemService wdtTTaskItemService;
	
	@Autowired
	private ExtWdtTTaskCommentsDao extWdtTTaskCommentsDao;
	@Autowired
	private WdtTTaskDao wdtTTaskDao;
	
	@Autowired
	private WdtTTaskReponseDao wdtTTaskReponseDao;
	
	@Autowired
	private WdtTTaskPersonDao wdtTTaskPersonDao;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private WdtTTaskCommentsPersonService wdtTTaskCommentsPersonService;
    public WdtTTaskComments get(String id) {
        return super.get(id);
    }
    
    public List<WdtTTaskComments> findList(WdtTTaskComments wdtTTaskComments) {
        return super.findList(wdtTTaskComments);
    }
    
    public Page findPage(Page page, WdtTTaskComments wdtTTaskComments) {
        wdtTTaskComments.setPage(page);
        List<ExtWdtTTaskComments> list = extWdtTTaskCommentsDao.findList(wdtTTaskComments);
        if(list!=null && list.size()>0){
        	for (ExtWdtTTaskComments ext : list) {
        		//加入@person
        		List<WdtTTaskCommentsPerson> persons = wdtTTaskCommentsPersonService.getAtPersonByCOrRId(ext.getId());
        		if(persons!=null &&persons.size()>0){
        			ext.setAtPersonList(persons);
        			List<String> atPerson = new LinkedList<String>();
        			for (WdtTTaskCommentsPerson wdtTTaskCommentsPerson : persons) {
        				atPerson.add(wdtTTaskCommentsPerson.getUser().getId());
					}
        			ext.setAtPerson(atPerson);
        		}
        		List<WdtTTaskReponseTreeNode> treeNodes= wdtTTaskReponseDao.findByCommentsId(ext.getId());
        		if(treeNodes!=null && treeNodes.size()>0){
        			for (WdtTTaskReponseTreeNode wdtTTaskReponseTreeNode : treeNodes) {
        				List<WdtTTaskCommentsPerson> personResponces = wdtTTaskCommentsPersonService.getAtPersonByCOrRId(wdtTTaskReponseTreeNode.getId());
                		if(personResponces!=null &&personResponces.size()>0){
                			wdtTTaskReponseTreeNode.setAtPersonList(personResponces);
                			List<String> atPerson = new LinkedList<String>();
                			for (WdtTTaskCommentsPerson wdtTTaskCommentsPerson : personResponces) {
                				atPerson.add(wdtTTaskCommentsPerson.getUser().getId());
        					}
                			wdtTTaskReponseTreeNode.setAtPerson(atPerson);
                		}
					}
        		}
        		List<WdtTTaskReponseTreeNode> tree = WdtTTaskReponseTreeBuilder.bulid(treeNodes, ext.getId());
        		ext.setWdtTTaskReponseList(tree);
			}
        }
        page.setList(list);
        return page;
    }
    
    public int findPinNo(WdtTTaskComments wdtTTaskComments) {
        return extWdtTTaskCommentsDao.findPinNo(wdtTTaskComments);
    }
    @Transactional(readOnly = false)
    public void save(WdtTTaskComments wdtTTaskComments) {
        super.save(wdtTTaskComments);
    }
    
    @Transactional(readOnly = false)
    public void delete(WdtTTaskComments wdtTTaskComments) {
        super.delete(wdtTTaskComments);
    }

    /**
     * 汇报进度，
     * @param wdtTTaskComments
     */
    @Transactional(readOnly = false)
	public int saveComments(WdtTTaskComments wdtTTaskComments) {
    	wdtTTaskComments.preInsert();
		// TODO Auto-generated method stub
		if(TaskCommentTypeEnum.Status_2.getValue().equals(wdtTTaskComments.getType())){
			//节点汇报
			WdtTTaskItem wdtTTaskItem=wdtTTaskItemService.get(wdtTTaskComments.getTaskItemId());
			if(wdtTTaskItem==null){
			    return -2;
            }
			if(wdtTTaskItem.getStartDate()==null||wdtTTaskItem.getStartDate().getTime()>new Date().getTime()){
			    return -3;
            }
			wdtTTaskComments.setTaskId(wdtTTaskItem.getTaskId());
			wdtTTaskItem.setProgress(wdtTTaskComments.getSchedule());
			wdtTTaskItemService.save(wdtTTaskItem);
			extWdtTTaskCommentsDao.deleteItemMessage(wdtTTaskItem.getId());
			List<String> personIds= getPersonIdsByTaskId(wdtTTaskItem.getTaskId(),null);
    		//除去@中的参与人
			if(wdtTTaskComments.getAtPerson() != null){
				personIds.removeAll(wdtTTaskComments.getAtPerson());
			}
			messageService.addTaskMessageFormat(TaskTypeEnum.Status_1.getValue(),wdtTTaskItem.getTaskId(),wdtTTaskComments.getTaskItemId(),personIds,UserUtils.getUser().getId(),MessageTypeEnum.Status_6.getValue(),wdtTTaskComments.getId(),null );
			//加入@者
			if(wdtTTaskComments.getAtPerson()!=null && wdtTTaskComments.getAtPerson().size()>0){
				messageService.addTaskMessageFormat(TaskTypeEnum.Status_1.getValue(),wdtTTaskItem.getTaskId(),wdtTTaskComments.getTaskItemId(),wdtTTaskComments.getAtPerson(),UserUtils.getUser().getId(),MessageTypeEnum.Status_12.getValue(),wdtTTaskComments.getId(),null );
			
				//加commentperson表
				wdtTTaskCommentsPersonService.batchInsert(wdtTTaskComments.getId(), wdtTTaskComments.getAtPerson());
			}
		}else if(TaskCommentTypeEnum.Status_0.getValue().equals(wdtTTaskComments.getType())){
			WdtTTask wdtTTask=wdtTTaskDao.get(wdtTTaskComments.getTaskId());
			if(wdtTTask==null){
			    return -1;
            }
			if(!wdtTTask.getOwner().getId().equals(UserUtils.getUser().getId())){
				return -3;
			}
			if(!wdtTTask.getExecutionStatus().equals(ExecutionStatusEnum.Status_3.getValue())
					&&!wdtTTask.getExecutionStatus().equals(ExecutionStatusEnum.Status_6.getValue())
					&&!wdtTTask.getExecutionStatus().equals(ExecutionStatusEnum.Status_7.getValue())
					&&!wdtTTask.getExecutionStatus().equals(ExecutionStatusEnum.Status_9.getValue())
				){
				return -4;
			}
			List<String> personIds= getPersonIdsByTaskId(wdtTTaskComments.getTaskId(),null);
    		//除去@中的参与人
			if(wdtTTaskComments.getAtPerson()!=null){
				personIds.removeAll(wdtTTaskComments.getAtPerson());
			}
			messageService.addTaskMessageFormat(TaskTypeEnum.Status_0.getValue(),wdtTTaskComments.getTaskId(),null,personIds,UserUtils.getUser().getId(),MessageTypeEnum.Status_5.getValue(),wdtTTaskComments.getId(),null );
			//加入@者
			if(wdtTTaskComments.getAtPerson()!=null && wdtTTaskComments.getAtPerson().size()>0){
				messageService.addTaskMessageFormat(TaskTypeEnum.Status_0.getValue(),wdtTTaskComments.getTaskId(),null,wdtTTaskComments.getAtPerson(),UserUtils.getUser().getId(),MessageTypeEnum.Status_11.getValue(),wdtTTaskComments.getId() ,null);
				//加commentperson表
				wdtTTaskCommentsPersonService.batchInsert(wdtTTaskComments.getId(), wdtTTaskComments.getAtPerson());
			}
		}
		else{
			//其他
		}
		
		wdtTTaskComments.setUser(wdtTTaskComments.getCreateBy());
		dao.insert(wdtTTaskComments);
		
		return 1;
	}
    /**
     * 删除进度汇报或者进度反馈
     * @param wdtTTaskComments
     * @return
     */
    @Transactional(readOnly = false)
	public int deleteComments(WdtTTaskComments wdtTTaskComments){
    	int i = 0;
    	String currentUserId = UserUtils.getUser().getId();
    	WdtTTaskComments entity = dao.get(wdtTTaskComments.getId());
    	if(DateUtils.subDate(entity.getCreateDate(), new Date())!=0){
    		return -2;
    	}
    	if(entity != null){
    		WdtTTask wdtTTask=wdtTTaskDao.get(entity.getTaskId());
    		if(!(wdtTTask.getExecutionStatus().equals(ExecutionStatusEnum.Status_3.getValue())
    				||wdtTTask.getExecutionStatus().equals(ExecutionStatusEnum.Status_6.getValue()))){
    			return -4;
    		}
    		
    		if(TaskCommentTypeEnum.Status_2.getValue().equals(entity.getType())){
    			//如果是汇报，删除后需要查看最新的进度汇报进度并更新节点
    			WdtTTaskItem wdtTTaskItem=wdtTTaskItemService.get(entity.getTaskItemId());
    			if(!(wdtTTaskItem.getExecutionStatus().equals(ExecutionStatusEnum.Status_3.getValue())
        				||wdtTTaskItem.getExecutionStatus().equals(ExecutionStatusEnum.Status_6.getValue()))){
        			return -4;
        		}
    			if(!(wdtTTaskItem.getOwnerId().equals(currentUserId)||wdtTTask.getOwner().getId().equals(currentUserId))){
    				return -3;
    			}
    			i = dao.delete(wdtTTaskComments);
    			WdtTTaskComments currentNew = dao.getNewCommentsByTaskItemId(entity.getTaskItemId());
    			wdtTTaskItem.setProgress(currentNew == null ?"0" :currentNew.getSchedule());
    			wdtTTaskItemService.save(wdtTTaskItem);
    			
    		}else{
    			if(!wdtTTask.getOwner().getId().equals(currentUserId)){
    				return -3;
    			}
    			i = dao.delete(wdtTTaskComments);
    		}
    		if(i>0){
    			//删除 下面的回复
    			List<String> pinIds = wdtTTaskReponseDao.getIdsByCommentsId(wdtTTaskComments.getId());
    			wdtTTaskReponseDao.deleteByCommentsId(wdtTTaskComments.getId());
    			pinIds.add(wdtTTaskComments.getId());
    			//删除有关进度反馈或者回复所有消息 
    			messageDao.deleteByPinId(pinIds);
    			return 1;
    		}else{
    			return -1;
    		}
    	}else{
    		return -1;
    	}
    	
    }
    
    
    /**
     * 更新汇报进度，
     * @param wdtTTaskComments
     */
    @Transactional(readOnly = false)
    public int updateComments(WdtTTaskComments wdtTTaskComments) {
        // TODO Auto-generated method stub
        WdtTTaskComments one=dao.get(wdtTTaskComments.getId());
        if(one!=null){
            if(TaskCommentTypeEnum.Status_2.getValue().equals(one.getType())){
                //更新节点进度
                WdtTTaskItem wdtTTaskItem=wdtTTaskItemService.get(one.getTaskItemId());
                wdtTTaskItem.setProgress(wdtTTaskComments.getSchedule());
                wdtTTaskItemService.save(wdtTTaskItem);
                List<String> personIds= getPersonIdsByTaskId(one.getTaskId(),null);
        		//除去@中的参与人
        		personIds.removeAll(wdtTTaskComments.getAtPerson());
                messageService.addTaskMessageFormat(TaskTypeEnum.Status_1.getValue(),one.getTaskId(),wdtTTaskComments.getTaskItemId(),personIds,UserUtils.getUser().getId(),MessageTypeEnum.Status_6.getValue(),one.getId(),null);
              //加入@者
    			if(wdtTTaskComments.getAtPerson()!=null && wdtTTaskComments.getAtPerson().size()>0){
    				messageService.addTaskMessageFormat(TaskTypeEnum.Status_1.getValue(),one.getTaskId(),wdtTTaskComments.getTaskItemId(),wdtTTaskComments.getAtPerson(),UserUtils.getUser().getId(),MessageTypeEnum.Status_12.getValue(),wdtTTaskComments.getId(),null );
    				//加commentperson表
    				wdtTTaskCommentsPersonService.batchInsert(wdtTTaskComments.getId(), wdtTTaskComments.getAtPerson());
    			}
            }else if(TaskCommentTypeEnum.Status_0.getValue().equals(one.getType())){
            	
            	WdtTTask wdtTTask=wdtTTaskDao.get(one.getTaskId());
            	if(wdtTTask==null){
    			    return -2;
                }
    			if(!wdtTTask.getOwner().getId().equals(UserUtils.getUser().getId())){
    				return -3;
    			}
    			if(!wdtTTask.getExecutionStatus().equals(ExecutionStatusEnum.Status_3.getValue())
    					&&!wdtTTask.getExecutionStatus().equals(ExecutionStatusEnum.Status_6.getValue())
    					&&!wdtTTask.getExecutionStatus().equals(ExecutionStatusEnum.Status_7.getValue())
    					&&!wdtTTask.getExecutionStatus().equals(ExecutionStatusEnum.Status_9.getValue())
    				){
    				return -4;
    			}
    			List<String> personIds= getPersonIdsByTaskId(wdtTTaskComments.getTaskId(),null);
        		//除去@中的参与人
        		personIds.removeAll(wdtTTaskComments.getAtPerson());
    			messageService.addTaskMessageFormat(TaskTypeEnum.Status_0.getValue(),wdtTTaskComments.getTaskId(),null,personIds,UserUtils.getUser().getId(),MessageTypeEnum.Status_5.getValue() ,one.getId(),null);
    			//加入@者
    			if(wdtTTaskComments.getAtPerson()!=null && wdtTTaskComments.getAtPerson().size()>0){
    				messageService.addTaskMessageFormat(TaskTypeEnum.Status_0.getValue(),wdtTTaskComments.getTaskId(),null,wdtTTaskComments.getAtPerson(),UserUtils.getUser().getId(),MessageTypeEnum.Status_11.getValue(),wdtTTaskComments.getId() ,null);
    				//加commentperson表
    				wdtTTaskCommentsPersonService.batchInsert(wdtTTaskComments.getId(), wdtTTaskComments.getAtPerson());
    			}
    			
            }
            one.setSchedule(wdtTTaskComments.getSchedule());
            one.setContent(wdtTTaskComments.getContent());
            one.preUpdate();
            dao.update(one);
            
            
        }else{
        	return -1;
        }
        return 1;
    }
    
    /**
     * 
     * @param taskId
     * @param type  0  1  2  3
     * @return
     */
    public List<String> getPersonIdsByTaskId(String taskId,String type){
    	WdtTTaskPerson searchWdtTTaskPerson = new WdtTTaskPerson();
    	searchWdtTTaskPerson.setTaskId(taskId);
    	searchWdtTTaskPerson.setType(type);
    	return wdtTTaskPersonDao.findIds(searchWdtTTaskPerson);
    }
    
    public ExtWdtTTaskComments getCommentsByATPinId(String pinId , String messageType){
    	
    	if(messageType.equals(MessageTypeEnum.Status_11.getValue()) || messageType.equals(MessageTypeEnum.Status_12.getValue())){
    		// 进度或反馈
    		WdtTTaskComments wdtTTaskComments = new WdtTTaskComments();
    		wdtTTaskComments.setId(pinId);
    		List<ExtWdtTTaskComments> list = extWdtTTaskCommentsDao.findList(wdtTTaskComments);
            if(list!=null && list.size()==1){
            	return list.get(0);
            }
    	}else if (messageType.equals(MessageTypeEnum.Status_13.getValue()) || messageType.equals(MessageTypeEnum.Status_14.getValue())
    			|| messageType.equals(MessageTypeEnum.Status_15.getValue())){
    		//回复
    		WdtTTaskReponse wdtTTaskReponse = wdtTTaskReponseDao.get(pinId);
    		if(wdtTTaskReponse!= null){
    			WdtTTaskComments wdtTTaskComments = new WdtTTaskComments();
        		wdtTTaskComments.setId(wdtTTaskReponse.getComentsId());
        		List<ExtWdtTTaskComments> list = extWdtTTaskCommentsDao.findList(wdtTTaskComments);
                if(list!=null && list.size()==1){
                	ExtWdtTTaskComments extWdtTTaskComments = list.get(0);
                	List<WdtTTaskReponseTreeNode> treeNodes= wdtTTaskReponseDao.findByResponseId(wdtTTaskReponse.getId());
                	if(StringUtils.isNotEmpty(wdtTTaskReponse.getParentId())){
                		WdtTTaskReponseTreeNode treeNode = wdtTTaskReponseDao.getByResponseId(wdtTTaskReponse.getParentId());
                		treeNodes.add(treeNode);
                	}
            		List<WdtTTaskReponseTreeNode> tree = WdtTTaskReponseTreeBuilder.bulid(treeNodes, extWdtTTaskComments.getId());
            		extWdtTTaskComments.setWdtTTaskReponseList(tree);
            		return extWdtTTaskComments;
                }
    		}
    	}
    	
    	return null;
    }
    
}