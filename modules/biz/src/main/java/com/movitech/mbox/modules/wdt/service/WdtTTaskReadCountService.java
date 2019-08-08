/**
 * 
 */
package com.movitech.mbox.modules.wdt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.service.CrudService;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskReadCount;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskReadCountDao;

/**
 * 阅读数Service
 * @author Aibal.He
 * @version 2017-08-25
 */
@Service
@Transactional(readOnly = true)
public class WdtTTaskReadCountService extends CrudService<WdtTTaskReadCountDao, WdtTTaskReadCount> {

    public WdtTTaskReadCount get(String id) {
        return super.get(id);
    }
    
    public List<WdtTTaskReadCount> findList(WdtTTaskReadCount wdtTTaskReadCount) {
        return super.findList(wdtTTaskReadCount);
    }
    
    public Page<WdtTTaskReadCount> findPage(Page<WdtTTaskReadCount> page, WdtTTaskReadCount wdtTTaskReadCount) {
        return super.findPage(page, wdtTTaskReadCount);
    }
    
    @Transactional(readOnly = false)
    public void save(WdtTTaskReadCount wdtTTaskReadCount) {
        super.save(wdtTTaskReadCount);
    }
    
    @Transactional(readOnly = false)
    public void delete(WdtTTaskReadCount wdtTTaskReadCount) {
        super.delete(wdtTTaskReadCount);
    }
    
    @Transactional(readOnly = false)
    public void updateReadCountByTaskId(String taskId){
    	int updateC =dao.updateReadCountByTaskId(taskId);
    	if(updateC == 0){
    		WdtTTaskReadCount entity = new WdtTTaskReadCount();
    		entity.setTaskId(taskId);
    		entity.setReadCount("1");
    		entity.preInsert();
    		dao.insert(entity);
    	}
    }
}