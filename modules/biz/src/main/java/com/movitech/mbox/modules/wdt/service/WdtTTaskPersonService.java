/**
 * 
 */
package com.movitech.mbox.modules.wdt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.service.CrudService;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskPerson;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskPersonDao;

/**
 * 任务干系人Service
 * @author Aibal.He
 * @version 2017-08-07
 */
@Service
@Transactional(readOnly = true)
public class WdtTTaskPersonService extends CrudService<WdtTTaskPersonDao, WdtTTaskPerson> {

    public WdtTTaskPerson get(String id) {
        return super.get(id);
    }
    
    public List<WdtTTaskPerson> findList(WdtTTaskPerson wdtTTaskPerson) {
        return super.findList(wdtTTaskPerson);
    }
    
    public Page<WdtTTaskPerson> findPage(Page<WdtTTaskPerson> page, WdtTTaskPerson wdtTTaskPerson) {
        return super.findPage(page, wdtTTaskPerson);
    }
    
    @Transactional(readOnly = false)
    public void save(WdtTTaskPerson wdtTTaskPerson) {
        super.save(wdtTTaskPerson);
    }
    
    @Transactional(readOnly = false)
    public void delete(WdtTTaskPerson wdtTTaskPerson) {
        super.delete(wdtTTaskPerson);
    }
    @Transactional(readOnly = false)
    public void deleteBy(WdtTTaskPerson wdtTTaskPerson) {
        dao.deleteBy(wdtTTaskPerson);
    }
    
}