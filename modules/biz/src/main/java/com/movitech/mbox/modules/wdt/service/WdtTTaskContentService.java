/**
 * 
 */
package com.movitech.mbox.modules.wdt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.service.CrudService;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskContent;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskContentDao;

/**
 * 任务内容内容Service
 * @author Aibal.He
 * @version 2017-08-18
 */
@Service
@Transactional(readOnly = true)
public class WdtTTaskContentService extends CrudService<WdtTTaskContentDao, WdtTTaskContent> {

    public WdtTTaskContent get(String id) {
        return super.get(id);
    }
    
    public List<WdtTTaskContent> findList(WdtTTaskContent wdtTTaskContent) {
        return super.findList(wdtTTaskContent);
    }
    
    public Page<WdtTTaskContent> findPage(Page<WdtTTaskContent> page, WdtTTaskContent wdtTTaskContent) {
        return super.findPage(page, wdtTTaskContent);
    }
    
    @Transactional(readOnly = false)
    public void save(WdtTTaskContent wdtTTaskContent) {
        super.save(wdtTTaskContent);
    }
    
    @Transactional(readOnly = false)
    public void delete(WdtTTaskContent wdtTTaskContent) {
        super.delete(wdtTTaskContent);
    }
    
}