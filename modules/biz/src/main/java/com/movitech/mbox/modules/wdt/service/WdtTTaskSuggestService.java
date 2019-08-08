/**
 * 
 */
package com.movitech.mbox.modules.wdt.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.service.CrudService;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskSuggest;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskSuggestDao;

/**
 * 意见收集Service
 * @author Aibal.He
 * @version 2018-01-11
 */
@Service
@Transactional(readOnly = true)
public class WdtTTaskSuggestService extends CrudService<WdtTTaskSuggestDao, WdtTTaskSuggest> {

    public WdtTTaskSuggest get(String id) {
        return super.get(id);
    }
    
    public List<WdtTTaskSuggest> findList(WdtTTaskSuggest wdtTTaskSuggest) {
        return super.findList(wdtTTaskSuggest);
    }
    
    public Page<WdtTTaskSuggest> findPage(Page<WdtTTaskSuggest> page, WdtTTaskSuggest wdtTTaskSuggest) {
        return super.findPage(page, wdtTTaskSuggest);
    }
    
    @Transactional(readOnly = false)
    public void save(WdtTTaskSuggest wdtTTaskSuggest) {
        super.save(wdtTTaskSuggest);
    }
    
    @Transactional(readOnly = false)
    public void delete(WdtTTaskSuggest wdtTTaskSuggest) {
        super.delete(wdtTTaskSuggest);
    }
    @Transactional(readOnly = false)
    public void insert(WdtTTaskSuggest entity){
    	entity.setInputDate(new Date());
    	entity.setInputPerson(UserUtils.getUser());
    	entity.preInsert();
    	dao.insert(entity);
    }
}