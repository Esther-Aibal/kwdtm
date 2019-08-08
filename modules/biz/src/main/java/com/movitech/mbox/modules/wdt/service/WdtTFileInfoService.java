/**
 * 
 */
package com.movitech.mbox.modules.wdt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.service.CrudService;
import com.movitech.mbox.modules.wdt.entity.WdtTFileInfo;
import com.movitech.mbox.modules.wdt.dao.WdtTFileInfoDao;

/**
 * 附件Service
 * @author Aibal.He
 * @version 2017-08-09
 */
@Service
@Transactional(readOnly = true)
public class WdtTFileInfoService extends CrudService<WdtTFileInfoDao, WdtTFileInfo> {

    public WdtTFileInfo get(String id) {
        return super.get(id);
    }
    
    public List<WdtTFileInfo> findList(WdtTFileInfo wdtTFileInfo) {
        return super.findList(wdtTFileInfo);
    }
    
    public Page<WdtTFileInfo> findPage(Page<WdtTFileInfo> page, WdtTFileInfo wdtTFileInfo) {
        return super.findPage(page, wdtTFileInfo);
    }
    
    @Transactional(readOnly = false)
    public void save(WdtTFileInfo wdtTFileInfo) {
        super.save(wdtTFileInfo);
    }
    @Transactional(readOnly = false)
    public void insert(WdtTFileInfo wdtTFileInfo) {
        dao.insert(wdtTFileInfo);
    }
    
    @Transactional(readOnly = false)
    public void delete(WdtTFileInfo wdtTFileInfo) {
        super.delete(wdtTFileInfo);
    }
    @Transactional(readOnly = false)
    public void updateTable(WdtTFileInfo wdtTFileInfo) {
        dao.update(wdtTFileInfo);
    }


    public WdtTFileInfo getFileByPath(String path){
       return dao.getFileByPath(path);
    }
}