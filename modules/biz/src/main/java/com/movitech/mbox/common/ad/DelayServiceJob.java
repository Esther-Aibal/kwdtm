package com.movitech.mbox.common.ad;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movitech.mbox.common.config.ExecutionStatusEnum;
import com.movitech.mbox.common.utils.DateUtils;
import com.movitech.mbox.modules.wdt.dao.report.DelayReportDao;
import com.movitech.mbox.modules.wdt.dao.report.WdtTItemReportDao;
import com.movitech.mbox.modules.wdt.entity.report.DelayTaskItem;
import com.movitech.mbox.modules.wdt.entity.report.WdtTItemReport;

/**
 * Created by gorden on 2017/8/8.
 *
 */
@Service("delayServiceJob")
public class DelayServiceJob {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private WdtTItemReportDao wdtTItemReportDao;
    @Autowired
    private DelayReportDao delayReportDao;

    public void delayjob() {
        logger.debug(" delay job  start....");
       //获取所有当天 需要检索的任务
        DelayTaskItem entity = new DelayTaskItem();
        //TODO 时间需要格式化到第二天凌晨
        Date currentEnd = DateUtils.getNextDayStartTime();
        entity.setCurrentDate(currentEnd);
        List<DelayTaskItem> list = new LinkedList<DelayTaskItem>();
        list.addAll(delayReportDao.getAllIngTaskItemList(entity));
        list.addAll(delayReportDao.getAllOverTodayTaskItemList(entity));
        List<WdtTItemReport> itemReports = new LinkedList<WdtTItemReport>();
        if(list!=null && list.size()>0){
        	for (DelayTaskItem delayTaskItem : list) {
        		Date currentTop = DateUtils.getStartTime();
        		Boolean doInsert =false;
        		int days = 0;
        		int fqcy = delayTaskItem.getFqcy() == null ?0:Integer.parseInt(delayTaskItem.getFqcy());
        		int reportType = (delayTaskItem.getExecutionStatus().equals(ExecutionStatusEnum.Status_3.getValue())||delayTaskItem.getExecutionStatus().equals(ExecutionStatusEnum.Status_7.getValue()))? 1:0;
        		String doItFlag = reportType == 1?"2":"1";
        		String reportComment = null;
        		int count = 0;
        		Date begin  = null;
        		if(fqcy == 0){
        			//获取当天
        			begin  = currentTop;
        			doInsert = true;
				}else if(fqcy == 1){
					//获取本周汇报数，今天是不是周日，如果不是周日不做插入操作
					int subDays = DateUtils.subDate(delayTaskItem.getStartDate(), currentTop)+1;
					if(subDays%7 == 0){
						begin  = DateUtils.addDays(currentTop, -6);
						doInsert = true;
						
					}
				}else if(fqcy == 2){
					//获取本月
					int subDays = DateUtils.subDate(delayTaskItem.getStartDate(), currentTop)+1;
					if(subDays%30 == 0){
						begin  = DateUtils.addDays(currentTop, -29);
						doInsert = true;
					}
				}else if(fqcy == 3){
					//隔天
					int subDays = DateUtils.subDate(delayTaskItem.getStartDate(), currentTop)+1;
					days = Integer.parseInt(delayTaskItem.getDays());
					if(subDays%days == 0){
						begin  = DateUtils.addDays(currentTop, -(days-1));
						doInsert = true;
					}
				}
        		
        		if(doInsert){
        			delayTaskItem.setStartDate(begin);
        			delayTaskItem.setEndDate(currentEnd);
        			count = delayReportDao.getActualCurrentCount(delayTaskItem);
        			reportComment = count==0?"0":doItFlag;
        			WdtTItemReport e = new WdtTItemReport();
        			e.setDays(days);
        			e.setFqcy(delayTaskItem.getFqcy());
        			e.setTaskId(delayTaskItem.getTaskId());
        			e.setItemName(delayTaskItem.getId());
        			e.setReportType(reportType);
        			e.setReportComment(reportComment);
        			e.preInsert();
            		itemReports.add(e);
        		}
        		
        		
			}

        	if(itemReports.size()>0){
        		//删除当天插入数据
        		WdtTItemReport wdtTItemReport = new WdtTItemReport();
        		wdtTItemReport.setCreateDate(new Date());
        		wdtTItemReportDao.deleteCurrentData(wdtTItemReport);
        		//查询最近数据
        		wdtTItemReportDao.batchInsert(itemReports);
        	}
        }

    }
 
}
