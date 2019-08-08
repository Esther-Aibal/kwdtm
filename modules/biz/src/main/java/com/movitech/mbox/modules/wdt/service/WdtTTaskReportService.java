/**
 * 
 */
package com.movitech.mbox.modules.wdt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.utils.DateReportUtils;
import com.movitech.mbox.common.utils.DateUtils;
import com.movitech.mbox.modules.wdt.dao.report.DelayReportDao;
import com.movitech.mbox.modules.wdt.entity.report.ActDoCompare;
import com.movitech.mbox.modules.wdt.entity.report.DelayTask;
import com.movitech.mbox.modules.wdt.entity.report.DelayTaskItem;
import com.movitech.mbox.modules.wdt.entity.report.OptLog;

/**
 * 任务报表Service
 * @author Aibal.He
 * @version 2017-08-07
 */
@Service
@Transactional(readOnly = true)
public class WdtTTaskReportService {
	
	@Autowired
	private DelayReportDao delayReportDao;
	
	/**
	 * 延迟列表
	 * @param page
	 * @param entity
	 * @return
	 */
	public Page<DelayTask> searchTaskPage(Page<DelayTask> page ,DelayTask entity){
		entity.setPage(page);
		List<DelayTask> list = delayReportDao.findTaskList(entity);
		if(list != null && list.size()>0){
			for (DelayTask delayTask : list) {
				delayAnalysis(delayTask);
			}
			
		}
        page.setList(list);
        return page;
	}
	/**
	 * 延迟详情
	 * @param entity
	 * @return
	 */
	public DelayTask detail(DelayTask entity){
		DelayTask delayTask = delayReportDao.getTask(entity);
		if(delayTask!= null ){
			delayAnalysis(delayTask);
		}
		return delayTask;
	}
	/**
	 * 延迟分析
	 * @param delayTask
	 */
	private void delayAnalysis(DelayTask delayTask){
		int molecule = 0;//分子
		int denominator = 0;//分母
		delayTask.setCurrentDate(new Date());
		List<DelayTaskItem> delayTaskItemList = delayReportDao.getTaskItemList(delayTask);
		
		if(delayTaskItemList!=null && delayTaskItemList.size()>0){
			for (DelayTaskItem delayTaskItem : delayTaskItemList) {
				molecule = molecule + delayTaskItem.getDelayCount();
				denominator = denominator + delayTaskItem.getPlanCount();
				if(delayTaskItem.getPlanCount() == 0){
					delayTaskItem.setDelayRate("0.00");
				}else{
					delayTaskItem.setDelayRate(String.format("%.2f",(double) delayTaskItem.getDelayCount()*100/delayTaskItem.getPlanCount()));
				}
			}
		}	
		if(denominator>0){
			delayTask.setDelayCount(molecule);
			delayTask.setDelayRate(String.format("%.2f",(double) molecule*100/denominator));
		}else{
			delayTask.setDelayRate("0.00");
		}
		delayTask.setDelayTaskItemList(delayTaskItemList);
	}
	/**
	 * 延迟分析
	 * @param delayTask
	 */
	/*private void delayAnalysis(DelayTask delayTask){
		int molecule = 0;//分子
		int denominator = 0;//分母
		delayTask.setCurrentDate(new Date());
		List<DelayTaskItem> delayTaskItemList = delayReportDao.getTaskItemList(delayTask);
		
		if(delayTaskItemList!=null && delayTaskItemList.size()>0){
			for (DelayTaskItem delayTaskItem : delayTaskItemList) {
				if(delayTaskItem.getActEndDate() == null){
					delayTaskItem.setActEndDate(DateReportUtils.getDayStart());
				}
				int fqcy = delayTask.getFqcy() == null ? 0 :Integer.parseInt(delayTask.getFqcy());
				Integer actDoCount = null;
				if(fqcy<3 && fqcy>=0){
					delayTaskItem.setFqcy(delayTask.getFqcy());
					actDoCount = delayReportDao.getActualCount(delayTaskItem);
				}else{
					//每隔几天算法
					delayTaskItem.setDays(delayTask.getDays());
					actDoCount= delayReportDao.getActualCountBySpecial(delayTaskItem);
				}
				if(actDoCount == null ) actDoCount =0;
				int planCount = 0;
				OptLog optLog = new OptLog();
				if(delayTaskItem.getExecutionStatus().equals("3")||delayTaskItem.getExecutionStatus().equals("6")){
					planCount = DateUtils.subDate(delayTaskItem.getStartDate(), new Date());	
				}else{
					planCount = DateUtils.subDate(delayTaskItem.getStartDate(), delayTaskItem.getActEndDate())+1;
				}
				if(fqcy == 0){
				}else if(fqcy == 1){
					planCount = planCount/6;
				}else if(fqcy == 2){
					planCount = planCount/29;
				}else if(fqcy == 3){
					planCount = planCount/Integer.parseInt(delayTask.getDays());
				}
				//减去例外时间
				optLog.setTaskId(delayTask.getId());
				List<OptLog> optLogList = delayReportDao.getOptLogList(optLog);
				if(optLogList!= null &&optLogList.size()>0){
					int no03=-1;
					Date date03 = null;
					int no07=0;
					Date date07 = null;
					int no08=0;
					Date date08 = null;
					int no54=0;
					Date date54 = null;
					boolean compareTransferFlag = false;
					boolean comparePauseFlag = false;
					for (OptLog optLog2 : optLogList) {
						if(optLog2.getLogeType().equals("03")){
							
							no03++;
							date03 = optLog2.getCreateDate();
						}else if(optLog2.getLogeType().equals("07")){
							
							no07++;
							date07 = optLog2.getCreateDate();
						}else if(optLog2.getLogeType().equals("08")){
							
							no08++;
							date08 = optLog2.getCreateDate();
						}else if(optLog2.getLogeType().equals("54")){
							if(no54-no08<=0){
								no54++;
								date54 = optLog2.getCreateDate();
							}
							
						}
						if(no03 >0 && no07 == no03 && !compareTransferFlag){
							int num = DateUtils.subDate(date07, date03);
							if(num>0 && date03.after(delayTaskItem.getStartDate()) && date07.before(delayTaskItem.getActEndDate())){
								if(fqcy == 0){
								}else if(fqcy == 1){
									num = num/6;
								}else if(fqcy == 2){
									num = num/29;
								}else if(fqcy == 3){
									num = num/Integer.parseInt(delayTask.getDays());
								}
								planCount =  planCount -num;
							}
							compareTransferFlag = true;
						}
						if(no08 >0 && no08 == no54 && !comparePauseFlag){
							int num = DateUtils.subDate(date54, date08);
							if(num>0 &&date08.after(delayTaskItem.getStartDate()) && date54.before(delayTaskItem.getActEndDate())){
								if(fqcy == 0){
								}else if(fqcy == 1){
									num = num/6;
								}else if(fqcy == 2){
									num = num/29;
								}else if(fqcy == 3){
									num = num/Integer.parseInt(delayTask.getDays());
								}
								planCount =  planCount -num;
							}
							comparePauseFlag = true;
						}
						if(no07 != no03){
							compareTransferFlag =false;
						}
						if(no08 != no54){
							comparePauseFlag =false;
						}
					}
				}
				delayTaskItem.setPlanCount(planCount);
				if(delayTaskItem.getPlanCount()>0){
					delayTaskItem.setDelayRate(String.format("%.2f", (double)(delayTaskItem.getPlanCount()-actDoCount)*100/delayTaskItem.getPlanCount()));
				}else{
					delayTaskItem.setDelayRate("0.00");
				}
				molecule = molecule + actDoCount;
				denominator = denominator + delayTaskItem.getPlanCount();
				delayTaskItem.setDelayCount(delayTaskItem.getPlanCount()-actDoCount);
			}
		}	
		delayTask.setDelayCount(denominator - molecule);
		if(denominator>0){
			delayTask.setDelayRate(String.format("%.2f",(double) (denominator-molecule)*100/denominator));
		}else{
			delayTask.setDelayRate("0.00");
		}
		delayTask.setDelayTaskItemList(delayTaskItemList);
	}*/
}