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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by gorden on 2017/8/8.
 *
 */
@Service("taskJob")
public class TaskServiceJob {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    WdtTTaskDao wdtTTaskDao;


    /**
     * 逾期任务状态修改
     */
    public void dueTask(){
        List<String> typelist=new ArrayList<>();
        typelist.add("3");
        List<WdtTTask> taskList=wdtTTaskDao.findListByExecutionStatus(typelist,MessageServiceJob.getStartTime());
        List<String> list=new ArrayList<>();
        if(taskList!=null&& taskList.size()>0){
            for (WdtTTask tTask:taskList){
                list.add(tTask.getId());
            }
            wdtTTaskDao.batchUpdateDueStatus("6",list);
        }
    }


}
