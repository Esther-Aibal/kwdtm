/**
 * 
 */
package com.movitech.mbox.modules.wdt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.service.CrudService;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskRelation;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskRelationDao;

/**
 * 子任务关联Service
 * @author Aibal.He
 * @version 2017-08-21
 */
@Service
@Transactional(readOnly = true)
public class WdtTTaskRelationService extends CrudService<WdtTTaskRelationDao, WdtTTaskRelation> {

    public WdtTTaskRelation get(String id) {
        return super.get(id);
    }
    
    public List<WdtTTaskRelation> findList(WdtTTaskRelation wdtTTaskRelation) {
        return super.findList(wdtTTaskRelation);
    }
    
    public Page<WdtTTaskRelation> findPage(Page<WdtTTaskRelation> page, WdtTTaskRelation wdtTTaskRelation) {
        return super.findPage(page, wdtTTaskRelation);
    }
    
    @Transactional(readOnly = false)
    public void save(WdtTTaskRelation wdtTTaskRelation) {
        super.save(wdtTTaskRelation);
    }
    
    @Transactional(readOnly = false)
    public void delete(WdtTTaskRelation wdtTTaskRelation) {
        super.delete(wdtTTaskRelation);
    }
    
}