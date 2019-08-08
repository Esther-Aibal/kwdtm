/**
 *
 */
package com.movitech.mbox.modules.wdt.operateLog.service;



import com.movitech.mbox.common.config.DeleteStatusEnum;
import com.movitech.mbox.common.config.OperateLogEnum;
import com.movitech.mbox.common.config.TaskTypeEnum;
import com.movitech.mbox.common.utils.DateUtils;
import com.movitech.mbox.common.utils.IdGen;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.utils.UserUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskItemDao;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskItem;
import com.movitech.mbox.modules.wdt.operateLog.dao.OperateLogMapper;
import com.movitech.mbox.modules.wdt.operateLog.dao.ext.OperateLogMapperExt;
import com.movitech.mbox.modules.wdt.operateLog.entity.OperateLog;
import com.movitech.mbox.modules.wdt.operateLog.entity.OperateLogExample;
import com.movitech.mbox.modules.wdt.req.TaskItemReq;

import java.util.Date;


/**
 * 操作日志service
 * @author felix.jin
 * 2017年8月22日
 */
@Service
@Transactional(readOnly = true)
public class OperateLogService {

	@Autowired
	private OperateLogMapper operateLogMapper;

	@Autowired
	private OperateLogMapperExt operateLogMapperExt;
	
	@Autowired
	private WdtTTaskItemDao wdtTTaskItemDao;

	@Autowired
	ThreadPoolTaskExecutor executor;


	public Page<OperateLog> findPage(TaskItemReq req) {
		Page<OperateLog> page=new Page<OperateLog>(req.getStart()/req.getLength()+1, req.getLength());
		OperateLogExample example=new OperateLogExample();
		example.createCriteria().andTaskIdEqualTo(req.getTaskId());
		int count=operateLogMapper.countByExample(example);
		page.setCount(count);
		if(count==0){
			page.setList(null);
			return page;
		}
//		example.setLimitStart(req.getStart());
//		example.setLimitEnd(req.getLength());
//		example.setOrderByClause("create_date desc");
//		page.setList(operateLogMapper.selectByExample(example));
		page.setList(operateLogMapperExt.getList(req));
		return page;
	}


	

	/**
	 * 插入task的操作日志
	 * @param operate 操作内容 在 OperateLogEnum枚举里取
	 * @param taskId  taskId
	 * @param taskItemId  操作节点id,没有传null
	 * @return
	 */
	@Transactional(readOnly = false)
	public int save(OperateLogEnum opLog,String ipAddr,String taskId,String taskItemId) {
		User u=UserUtils.getUser();
		executor.execute(new Runnable() {
			@Override
			public void run() {
					OperateLog log=new OperateLog();
					log.setContent(opLog.getDescription());
					log.setId(IdGen.uuid());
					if(taskId==null&&taskItemId!=null){
						WdtTTaskItem item = wdtTTaskItemDao.get(taskItemId);
						if(item!=null){
							log.setTaskId(item.getTaskId());
						}
					}else{
						log.setTaskId(taskId);
					}
					if(taskItemId==null){
						log.setType(TaskTypeEnum.Status_0.getValue());
					}else{
						log.setType(TaskTypeEnum.Status_1.getValue());
					}
					
					log.setTaskItemId(taskItemId);
					//log.setIpAddress(ipAddr);
					log.setCreateDate(new Date());
					log.setUpdateDate(log.getCreateDate());
					log.setOperatorDate(DateUtils.formatDate(log.getCreateDate(),"yyyy-MM-dd HH:mm"));
					log.setDelFlag(DeleteStatusEnum.Status_0.getValue());
					log.setLogeType(opLog.getValue());
					//User u= UserUtils.getUser();
					if(u!=null){
						log.setCreateBy(u.getId());
						log.setUpdateBy(u.getId());
						log.setUserId(u.getId());
						log.setIpAddress(u.getLoginIp());
					}
					if(!StringUtils.isEmpty(ipAddr)){
						log.setIpAddress(ipAddr);
					}
					operateLogMapper.insertSelective(log);
			}
		});
		return 1;
	}
}