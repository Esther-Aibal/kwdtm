/**
 * 
 */
package com.movitech.mbox.modules.wdt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.service.CrudService;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskTheme;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskThemeDao;
import com.movitech.mbox.modules.wdt.dao.ext.ExtWdtTTaskThemeDao;

/**
 * 任务主题Service
 * @author Aibal.He
 * @version 2017-08-18
 */
@Service
@Transactional(readOnly = true)
public class WdtTTaskThemeService extends CrudService<WdtTTaskThemeDao, WdtTTaskTheme> {

	@Autowired
	private ExtWdtTTaskThemeDao extWdtTTaskThemeDao;
	
    public WdtTTaskTheme get(String id) {
        return super.get(id);
    }
    
    public List<WdtTTaskTheme> findList(WdtTTaskTheme wdtTTaskTheme) {
        return super.findList(wdtTTaskTheme);
    }
    
    public Page<WdtTTaskTheme> findPage(Page<WdtTTaskTheme> page, WdtTTaskTheme wdtTTaskTheme) {
    	 return super.findPage(page,wdtTTaskTheme);
    }
    

	@Transactional(readOnly = false)
    public void save(WdtTTaskTheme wdtTTaskTheme) {
        super.save(wdtTTaskTheme);
    }
    
    @Transactional(readOnly = false)
    public void delete(WdtTTaskTheme wdtTTaskTheme) {
        super.delete(wdtTTaskTheme);
    }
    @Transactional(readOnly = false)
    public int deleteTheme(WdtTTaskTheme wdtTTaskTheme) {
    	WdtTTaskTheme current = super.get(wdtTTaskTheme.getId());
    	if(current!=null){
    		WdtTTaskTheme entity = new WdtTTaskTheme();
    		entity.setParentCategoryId(wdtTTaskTheme.getCategoryId());
    		List<WdtTTaskTheme > list = dao.findList(entity);
    		if(list!=null && list.size()>0){
    			return -2;
    		}
    		int count = dao.checkInUsedCount(current.getCategoryId());
    		if(count >0){
				return -3;
			}
    		super.delete(wdtTTaskTheme);
			return 0;
    		
    	}else{
    		return -1;
    	}
        
    }
	public List<WdtTTaskTheme> findAll() {
		// TODO Auto-generated method stub
		return extWdtTTaskThemeDao.findAll();
	}

    
}