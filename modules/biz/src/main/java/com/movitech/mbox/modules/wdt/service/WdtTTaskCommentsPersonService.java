/**
 * 
 */
package com.movitech.mbox.modules.wdt.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.service.CrudService;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskCommentsPerson;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskCommentsPersonDao;

/**
 * 进度@人员Service
 * @author Aibal.He
 * @version 2017-10-27
 */
@Service
@Transactional(readOnly = true)
public class WdtTTaskCommentsPersonService extends CrudService<WdtTTaskCommentsPersonDao, WdtTTaskCommentsPerson> {

    public WdtTTaskCommentsPerson get(String id) {
        return super.get(id);
    }
    
    public List<WdtTTaskCommentsPerson> findList(WdtTTaskCommentsPerson wdtTTaskCommentsPerson) {
        return super.findList(wdtTTaskCommentsPerson);
    }
    
    public Page<WdtTTaskCommentsPerson> findPage(Page<WdtTTaskCommentsPerson> page, WdtTTaskCommentsPerson wdtTTaskCommentsPerson) {
        return super.findPage(page, wdtTTaskCommentsPerson);
    }
    
    @Transactional(readOnly = false)
    public void save(WdtTTaskCommentsPerson wdtTTaskCommentsPerson) {
        super.save(wdtTTaskCommentsPerson);
    }
    
    @Transactional(readOnly = false)
    public void delete(WdtTTaskCommentsPerson wdtTTaskCommentsPerson) {
        super.delete(wdtTTaskCommentsPerson);
    }
    @Transactional(readOnly = false)
    public void batchInsert(String comentOrResponceId,List<String> atPersons){
    	if(atPersons!=null &&atPersons.size()>0){
    		List<WdtTTaskCommentsPerson> list = new LinkedList<WdtTTaskCommentsPerson>();
    		for (String string : atPersons) {
    			WdtTTaskCommentsPerson e = new WdtTTaskCommentsPerson();
    			e.setComentsId(comentOrResponceId);
    			e.setUser(new User(string));
    			e.preInsert();
    			list.add(e);
			}
    		dao.deleteByComentOrResponceId(comentOrResponceId);
    		dao.batchInsert(list);
    	}
    	
    }
    public List<WdtTTaskCommentsPerson> getAtPersonByCOrRId(String comentOrResponceId){
    	return dao.getAtPersonByCOrRId(comentOrResponceId);
    }
}