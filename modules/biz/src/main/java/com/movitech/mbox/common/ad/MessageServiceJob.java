package com.movitech.mbox.common.ad;

import com.movitech.mbox.common.utils.DateUtils;
import com.movitech.mbox.common.utils.IdGen;
import com.movitech.mbox.modules.wdt.dao.MessageDao;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskDao;
import com.movitech.mbox.modules.wdt.dao.ext.ExtWdtTTaskItemDao;
import com.movitech.mbox.modules.wdt.entity.Message;
import com.movitech.mbox.modules.wdt.entity.WdtTTask;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskItem;
import com.movitech.mbox.modules.wdt.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by gorden on 2017/8/8.
 *
 */
@Service("messageServiceJob")
public class MessageServiceJob {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    WdtTTaskDao wdtTTaskDao;
    @Autowired
    ExtWdtTTaskItemDao extWdtTTaskItemDao;
    @Autowired
    MessageService messageService;
    @Autowired
    MessageDao messageDao;
    public void messagejob() {
        logger.debug(" Message job  start....");
        //把所有完结消息删除
       List<String> closeMesssgIds=messageDao.findAllMessageIdByTaskClose();
       // messageDao.deleteAll(null,closeMesssgIds);
        //0：预警提醒（按预警时间提醒） 1：催办提醒 2：进度提醒（按主任务汇报频率）3：逾期提醒  4：新任务提醒）
        //维护此类消息的生命周期
        List<String> typelist=new ArrayList<>();
        typelist.add("0");typelist.add("1");typelist.add("2");typelist.add("3");typelist.add("4");
        messageDao.deleteAll(typelist,closeMesssgIds);
        //job生成消息
        List<String> listexecution=new ArrayList<>();
        listexecution.add("3");listexecution.add("6");
        List<WdtTTask> taskList = wdtTTaskDao.findListByExecutionStatus(listexecution, null);
        if (taskList == null || taskList.size() < 1) return;
        List<Message> messages = new ArrayList<>();
        for (WdtTTask wdtTTask : taskList) {
            int messagecount=0;
            List<WdtTTaskItem> taskItems = extWdtTTaskItemDao.getTaskItemByTaskId(wdtTTask.getId());
            if (taskItems == null || taskItems.size() < 1) continue;
            //（0：每日 1：每周 2：每月 3：每隔天）
            //20171012 加入逾期未完成时，每日提醒
            Date start = wdtTTask.getStartDate();
            Date now = new Date();
            if (now.before(start)) continue;
            if (wdtTTask.getExecutionStatus().equals("6") ||"0".equals(wdtTTask.getFqcy())) {//每日
                //生成消息提醒
                for (WdtTTaskItem item : taskItems) {
                    Date itemStartTime=item.getStartDate();
                    Date itemEndtTime=item.getRequiredCompletionTime();
                    if (now.before(itemStartTime)) continue;
                    Message message = new Message();
                    message.setId(IdGen.uuid());
                    message.setType("1");
                    message.setTaskId(wdtTTask.getId());
                    message.setSenderId(wdtTTask.getTaskCreateUser().getId());
                    message.setTaskItemId(item.getId());
                    message.setReciverId(item.getOwnerId());
                    message.setMessageType("2");
                    message.setIsRead("0");
                    message.setIsFromSystem("0");
                    message.setCreateDate(new Date());
                    message.setUpdateDate(new Date());
                    message.setDelFlag("0");
                    messages.add(message);
                    messagecount++;
                }
            } else if ("1".equals(wdtTTask.getFqcy())) {//每周
                //日期相差多少天
                int subDays = DateUtils.subDate(start, now);
                //生成消息提醒
                Date startTime=DateUtils.addDays(now,-6);
                Date midTime=DateUtils.addDays(now,-1);
                for (WdtTTaskItem item : taskItems) {
                    //节点时间范围
                	if(item.getExecutionStatus().equals("6")){
                		Message message = new Message();
                        message.setId(IdGen.uuid());
                        message.setType("1");
                        message.setTaskId(wdtTTask.getId());
                        message.setSenderId(wdtTTask.getTaskCreateUser().getId());
                        message.setTaskItemId(item.getId());
                        message.setReciverId(item.getOwnerId());
                        message.setMessageType("2");
                        message.setIsRead("0");
                        message.setIsFromSystem("0");
                        message.setCreateDate(new Date());
                        message.setUpdateDate(new Date());
                        message.setDelFlag("0");
                        messages.add(message);
                        messagecount++;
                        
                	}else if ((subDays) % 6 == 0) {
	                    Date itemStartTime=item.getStartDate();
	                    Date itemEndtTime=item.getRequiredCompletionTime();
	                    if (now.before(itemStartTime)) continue;
	                    int midsum=wdtTTaskDao.getTaskCommentAmount(item.getId(),startTime,midTime);
	                    if(midsum>0)continue;
	                    Message message = new Message();
	                    message.setId(IdGen.uuid());
	                    message.setType("1");
	                    message.setTaskId(wdtTTask.getId());
	                    message.setSenderId(wdtTTask.getTaskCreateUser().getId());
	                    message.setTaskItemId(item.getId());
	                    message.setMessageType("2");
	                    message.setIsRead("0");
	                    message.setIsFromSystem("0");
	                    message.setCreateDate(new Date());
	                    message.setUpdateDate(new Date());
	                    message.setReciverId(item.getOwnerId());
	                    message.setDelFlag("0");
	                    messages.add(message);
	                    messagecount++;
	                }
                }
                
            } else if ("2".equals(wdtTTask.getFqcy())) {//每月
                //日期相差多少天
                int subDays = DateUtils.subDate(start, now);
                Date starttime=DateUtils.addDays(now,-29);
                Date midTime=DateUtils.addDays(now,-1);
                
                    //生成消息提醒
                for (WdtTTaskItem item : taskItems) {
                	if (item.getExecutionStatus().equals("6")){
                		Message message = new Message();
	                    message.setId(IdGen.uuid());
	                    message.setType("1");
	                    message.setSenderId(wdtTTask.getTaskCreateUser().getId());
	                    message.setTaskId(wdtTTask.getId());
	                    message.setTaskItemId(item.getId());
	                    message.setReciverId(item.getOwnerId());
	                    message.setMessageType("2");
	                    message.setIsRead("0");
	                    message.setIsFromSystem("0");
	                    message.setCreateDate(new Date());
	                    message.setUpdateDate(new Date());
	                    message.setDelFlag("0");
	                    messages.add(message);
	                    messagecount++;
                	}else if((subDays) % 29 == 0){
	                    Date itemStartTime=item.getStartDate();
	                    Date itemEndtTime=item.getRequiredCompletionTime();
	                    if (now.before(itemStartTime)) continue;
	                    int midsum=wdtTTaskDao.getTaskCommentAmount(item.getId(),starttime,midTime);
	                    if(midsum>0)continue;
	                    Message message = new Message();
	                    message.setId(IdGen.uuid());
	                    message.setType("1");
	                    message.setSenderId(wdtTTask.getTaskCreateUser().getId());
	                    message.setTaskId(wdtTTask.getId());
	                    message.setTaskItemId(item.getId());
	                    message.setReciverId(item.getOwnerId());
	                    message.setMessageType("2");
	                    message.setIsRead("0");
	                    message.setIsFromSystem("0");
	                    message.setCreateDate(new Date());
	                    message.setUpdateDate(new Date());
	                    message.setDelFlag("0");
	                    messages.add(message);
	                    messagecount++;
                	}
                }
            } else if ("3".equals(wdtTTask.getFqcy())) {//如果是每隔几天
                //日期相差多少天
                int subDays = DateUtils.subDate(start, now);
                int days = wdtTTask.getDays();
                Date stattime=DateUtils.addDays(now,0-days);
                Date midTime=DateUtils.addDays(now,-1);
                
                //生成消息提醒
                for (WdtTTaskItem item : taskItems) {
                	if (item.getExecutionStatus().equals("6")){
                		Message message = new Message();
                        message.setId(IdGen.uuid());
                        message.setType("1");
                        message.setSenderId(wdtTTask.getTaskCreateUser().getId());
                        message.setTaskId(wdtTTask.getId());
                        message.setTaskItemId(item.getId());
                        message.setReciverId(item.getOwnerId());
                        message.setMessageType("2");
                        message.setIsRead("0");
                        message.setIsFromSystem("0");
                        message.setCreateDate(new Date());
                        message.setUpdateDate(new Date());
                        message.setDelFlag("0");
                        messages.add(message);
                        messagecount++;
                	}else if (days != 0 && ((subDays) % days == 0)) {
                        Date itemStartTime=item.getStartDate();
                        Date itemEndtTime=item.getRequiredCompletionTime();
                        if (now.before(itemStartTime)) continue;
                        int midsum=wdtTTaskDao.getTaskCommentAmount(item.getId(),stattime,midTime);
                        if(midsum>0)continue;
                        Message message = new Message();
                        message.setId(IdGen.uuid());
                        message.setType("1");
                        message.setSenderId(wdtTTask.getTaskCreateUser().getId());
                        message.setTaskId(wdtTTask.getId());
                        message.setTaskItemId(item.getId());
                        message.setReciverId(item.getOwnerId());
                        message.setMessageType("2");
                        message.setIsRead("0");
                        message.setIsFromSystem("0");
                        message.setCreateDate(new Date());
                        message.setUpdateDate(new Date());
                        message.setDelFlag("0");
                        messages.add(message);
                        messagecount++;
                    }
                }
            }
            //如果此任务新增任务大于0条,把之前的进度消息给删除
            if(messagecount>0){
                messageDao.deleteByTypeAndTaskId("2",wdtTTask.getId());
            }
            logger.debug(" messssge job  end....");
        }

        List<Message> duemessage= dueTask();
        if(duemessage!=null&&duemessage.size()>0){
           messages.addAll(duemessage);
        }
        List<Message> wmessage= waringTask();
        if(wmessage!=null&&wmessage.size()>0){
            messages.addAll(wmessage);
        }
        messageService.batchInsert(messages);
        Set<String> set=new HashSet<>();
        if(messages!=null&&messages.size()>0){
            for(Message message:messages){
                set.add(message.getReciverId());
            }
        }
        messageService.sendMessage(set);
    }
    /**
     * 新任务提醒
     */
    /*private List<Message> newTask(){
        List<WdtTTask> taskList=wdtTTaskDao.findListByExecutionStatus("1",null);
        List<Message> messages=new ArrayList<>();
        for(WdtTTask tTask:taskList){
            Message message=new Message();
            message.setId(IdGen.uuid());
            message.setType("0");
            message.setTaskId(tTask.getId());
            //message.setTaskItemId(item.getId());
            message.setReciverId(tTask.getOwner().getId());
            message.setMessageType("4");
            message.setIsRead("0");
            message.setIsFromSystem("0");
            message.setCreateDate(new Date());
            message.setUpdateDate(new Date());
            message.setDelFlag("0");
            messages.add(message);
        }
        return  messages;
    }*/

    /**
     * 逾期任务
     */
    private List<Message> dueTask(){
        List<String> typelist=new ArrayList<>();
        typelist.add("3");typelist.add("6");
        List<WdtTTask> taskList=wdtTTaskDao.findListByExecutionStatus(typelist,getStartTime());
        List<Message> messages=new ArrayList<>();
        if(taskList==null||taskList.size()<1) return null;
        for(WdtTTask tTask:taskList){
            Message message=new Message();
            message.setId(IdGen.uuid());
            message.setType("0");
            message.setTaskId(tTask.getId());
            //message.setTaskItemId(item.getId());
            message.setReciverId(tTask.getOwner().getId());
            message.setMessageType("3");
            message.setIsRead("0");
            message.setSenderId(tTask.getTaskCreateUser().getId());
            message.setIsFromSystem("0");
            message.setCreateDate(new Date());
            message.setUpdateDate(new Date());
            message.setDelFlag("0");
            messages.add(message);
            messageDao.deleteByTypeAndTaskId("3",tTask.getId());
        }
        return messages;
    }
    /**
     * 预警任务
     */
    private List<Message> waringTask(){

        List<WdtTTask> taskList=wdtTTaskDao.getByWarningTask(new Date());
        List<Message> messages=new ArrayList<>();
        if(taskList==null||taskList.size()<1) return null;
        for(WdtTTask tTask:taskList){
            Message message=new Message();
            message.setId(IdGen.uuid());
            message.setType("0");
            message.setTaskId(tTask.getId());
            //message.setTaskItemId(item.getId());
            message.setReciverId(tTask.getOwner().getId());
            message.setMessageType("0");
            message.setIsRead("0");
            message.setIsFromSystem("0");
            message.setSenderId(tTask.getTaskCreateUser().getId());
            message.setCreateDate(tTask.getWarningDate());
            message.setUpdateDate(new Date());
            message.setDelFlag("0");
            messages.add(message);
            messageDao.deleteByTypeAndTaskId("0",tTask.getId());
        }
        return messages;
    }
    public static Date getStartTime(){
              Calendar todayStart = Calendar.getInstance();
               todayStart.set(Calendar.HOUR_OF_DAY, 0);
               todayStart.set(Calendar.MINUTE, 0);
               todayStart.set(Calendar.SECOND, 0);
               todayStart.set(Calendar.MILLISECOND, 0);
               return todayStart.getTime();
    }
}
