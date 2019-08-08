/**
 * 
 */
package com.movitech.mbox.modules.wdt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.service.CrudService;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskHandover;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskHandoverDao;

/**
 * 任务移交Service
 * @author felix
 * @version 2017-08-09
 */
@Service
@Transactional(readOnly = true)
public class WdtTTaskHandoverService extends CrudService<WdtTTaskHandoverDao, WdtTTaskHandover> {

    public WdtTTaskHandover get(String id) {
        return super.get(id);
    }
    
    public List<WdtTTaskHandover> findList(WdtTTaskHandover wdtTTaskHandover) {
        return super.findList(wdtTTaskHandover);
    }
    
    public Page<WdtTTaskHandover> findPage(Page<WdtTTaskHandover> page, WdtTTaskHandover wdtTTaskHandover) {
        return super.findPage(page, wdtTTaskHandover);
    }
    
    @Transactional(readOnly = false)
    public void save(WdtTTaskHandover wdtTTaskHandover) {
        super.save(wdtTTaskHandover);
    }

    @Transactional(readOnly = false)
    public void delete(WdtTTaskHandover wdtTTaskHandover) {
        super.delete(wdtTTaskHandover);
    }
    
}