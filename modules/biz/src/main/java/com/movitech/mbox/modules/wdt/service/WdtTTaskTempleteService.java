/**
 * 
 */
package com.movitech.mbox.modules.wdt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.service.CrudService;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskTemplete;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskTempleteDao;

/**
 * 任务内容模板Service
 * @author Aibal.He
 * @version 2017-08-18
 */
@Service
@Transactional(readOnly = true)
public class WdtTTaskTempleteService extends CrudService<WdtTTaskTempleteDao, WdtTTaskTemplete> {

    public WdtTTaskTemplete get(String id) {
        return super.get(id);
    }
    
    public List<WdtTTaskTemplete> findList(WdtTTaskTemplete wdtTTaskTemplete) {
        return super.findList(wdtTTaskTemplete);
    }
    
    public Page<WdtTTaskTemplete> findPage(Page<WdtTTaskTemplete> page, WdtTTaskTemplete wdtTTaskTemplete) {
        return super.findPage(page, wdtTTaskTemplete);
    }
    
    @Transactional(readOnly = false)
    public void save(WdtTTaskTemplete wdtTTaskTemplete) {
        super.save(wdtTTaskTemplete);
    }
    
    @Transactional(readOnly = false)
    public void delete(WdtTTaskTemplete wdtTTaskTemplete) {
        super.delete(wdtTTaskTemplete);
    }
    public boolean checkSingle(WdtTTaskTemplete wdtTTaskTemplete){
    	WdtTTaskTemplete current = dao.get(wdtTTaskTemplete.getId());
    	int count = dao.getCountAboutSort(wdtTTaskTemplete);
    	if(count ==0 || (count ==1 && current!= null && current.getSortNum().equals(wdtTTaskTemplete.getSortNum()))){
    		return true;
    	}else{
    		return false;
    	}
    }
}