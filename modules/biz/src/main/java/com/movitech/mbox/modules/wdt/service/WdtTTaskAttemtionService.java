/**
 * 
 */
package com.movitech.mbox.modules.wdt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movitech.mbox.common.config.ErrorMessage;
import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.service.CrudService;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskAttemtion;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskAttemtionDao;

/**
 * 关注任务Service
 * @author Jack.Gong
 * @version 2017-08-17
 */
@Service
@Transactional(readOnly = true)
public class WdtTTaskAttemtionService extends CrudService<WdtTTaskAttemtionDao, WdtTTaskAttemtion> {

    public WdtTTaskAttemtion get(String id) {
        return super.get(id);
    }
    
    public List<WdtTTaskAttemtion> findList(WdtTTaskAttemtion wdtTTaskAttemtion) {
        return super.findList(wdtTTaskAttemtion);
    }
    
    public Page<WdtTTaskAttemtion> findPage(Page<WdtTTaskAttemtion> page, WdtTTaskAttemtion wdtTTaskAttemtion) {
        return super.findPage(page, wdtTTaskAttemtion);
    }
    
    @Transactional(readOnly = false)
    public void save(WdtTTaskAttemtion wdtTTaskAttemtion) {
        super.save(wdtTTaskAttemtion);
    }
    
    @Transactional(readOnly = false)
    public void delete(WdtTTaskAttemtion wdtTTaskAttemtion) {
        super.delete(wdtTTaskAttemtion);
    }

	public WdtTTaskAttemtion getFocusedTaskByTaskIdAndUserId(WdtTTaskAttemtion wdtTTaskAttemtion) {
		return dao.getFocusedTaskByTaskIdAndUserId(wdtTTaskAttemtion);
	}
	@Transactional(readOnly = false)
	public int cancelAttentionTask(WdtTTaskAttemtion wdtTTaskAttemtion){
		return dao.cancelAttentionTask(wdtTTaskAttemtion);
	}
}