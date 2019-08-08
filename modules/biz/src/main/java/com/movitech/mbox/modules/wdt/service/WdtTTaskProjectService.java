/**
 * 
 */
package com.movitech.mbox.modules.wdt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.service.CrudService;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskProject;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskProjectDao;

/**
 * 任务项目关联Service
 * @author Jack.Gong
 * @version 2017-08-09
 */
@Service
@Transactional(readOnly = true)
public class WdtTTaskProjectService extends CrudService<WdtTTaskProjectDao, WdtTTaskProject> {

    public WdtTTaskProject get(String id) {
        return super.get(id);
    }
    
    public List<WdtTTaskProject> findList(WdtTTaskProject wdtTTaskProject) {
        return super.findList(wdtTTaskProject);
    }
    
    public Page<WdtTTaskProject> findPage(Page<WdtTTaskProject> page, WdtTTaskProject wdtTTaskProject) {
        return super.findPage(page, wdtTTaskProject);
    }
    
    @Transactional(readOnly = false)
    public void save(WdtTTaskProject wdtTTaskProject) {
        super.save(wdtTTaskProject);
    }
    
    @Transactional(readOnly = false)
    public void delete(WdtTTaskProject wdtTTaskProject) {
        super.delete(wdtTTaskProject);
    }
    
}