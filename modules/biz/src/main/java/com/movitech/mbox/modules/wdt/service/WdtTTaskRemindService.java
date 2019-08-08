/**
 * 
 */
package com.movitech.mbox.modules.wdt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.service.CrudService;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskRemind;
import com.movitech.mbox.modules.wdt.entity.WdtTaskVo;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskRemindDao;

/**
 * 自定义提醒Service
 * @author Aibal.He
 * @version 2018-01-24
 */
@Service
@Transactional(readOnly = true)
public class WdtTTaskRemindService extends CrudService<WdtTTaskRemindDao, WdtTTaskRemind> {

    public WdtTTaskRemind get(String id) {
        return super.get(id);
    }
    
    public List<WdtTTaskRemind> findList(WdtTTaskRemind wdtTTaskRemind) {
        return super.findList(wdtTTaskRemind);
    }
    
    public Page<WdtTTaskRemind> findPage(Page<WdtTTaskRemind> page, WdtTTaskRemind wdtTTaskRemind) {
        return super.findPage(page, wdtTTaskRemind);
    }
    
    @Transactional(readOnly = false)
    public void save(WdtTTaskRemind wdtTTaskRemind) {
        super.save(wdtTTaskRemind);
    }
    
    @Transactional(readOnly = false)
    public void delete(WdtTTaskRemind wdtTTaskRemind) {
        super.delete(wdtTTaskRemind);
    }
    @Transactional(readOnly = false)
    public int doneRemind(WdtTTaskRemind wdtTTaskRemind){
    	return dao.doneRemind(wdtTTaskRemind);
    	
    }
    
	public List<WdtTTaskRemind> getUndoRemindkForCalendar(String userId, String endDate) {
		if(com.movitech.mbox.common.utils.StringUtils.isNotEmpty(endDate)){
			endDate = endDate +" 23:59:59";
		}
		List<WdtTTaskRemind> myWaitingTaskList = dao.getUndoRemindkForCalendar(userId,endDate);
		
		return myWaitingTaskList;
	}
}