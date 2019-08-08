/**
 * 
 */
package com.movitech.mbox.modules.com.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.service.CrudService;
import com.movitech.mbox.modules.com.entity.ComDocument;
import com.movitech.mbox.modules.com.dao.ComDocumentDao;

/**
 * 文章Service
 * @author Aibal.He
 * @version 2017-06-23
 */
@Service
@Transactional(readOnly = true)
public class ComDocumentService extends CrudService<ComDocumentDao, ComDocument> {

    public ComDocument get(String id) {
        return super.get(id);
    }
    
    public List<ComDocument> findList(ComDocument comDocument) {
        return super.findList(comDocument);
    }
    
    public Page<ComDocument> findPage(Page<ComDocument> page, ComDocument comDocument) {
        return super.findPage(page, comDocument);
    }
    
    @Transactional(readOnly = false)
    public void save(ComDocument comDocument) {
        super.save(comDocument);
    }
    
    @Transactional(readOnly = false)
    public void delete(ComDocument comDocument) {
        super.delete(comDocument);
    }
    
}