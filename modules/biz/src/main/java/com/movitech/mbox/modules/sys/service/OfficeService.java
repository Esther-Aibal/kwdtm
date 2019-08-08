/**
 * 
 */
package com.movitech.mbox.modules.sys.service;

import java.util.List;
import java.util.Map;

import com.movitech.mbox.modules.sys.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movitech.mbox.common.service.TreeService;
import com.movitech.mbox.modules.sys.dao.OfficeDao;
import com.movitech.mbox.modules.sys.entity.Office;
import com.movitech.mbox.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
public class OfficeService extends TreeService<OfficeDao, Office> {

    public List<Office> findAll(){
        return UserUtils.getOfficeList();
    }

    public List<Office> findList(Boolean isAll){
        if (isAll != null && isAll){
            return UserUtils.getOfficeAllList();
        }else{
            return UserUtils.getOfficeList();
        }
    }
    
    @Transactional(readOnly = true)
    public List<Office> findList(Office office){
        office.setParentIds(office.getParentIds()+"%");
        return dao.findByParentIdsLike(office);
    }
    
    @Transactional(readOnly = false)
    public void save(Office office) {
        super.save(office);
        UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
    }
    
    @Transactional(readOnly = false)
    public void delete(Office office) {
        super.delete(office);
        UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
    }

    /**
     * 批量插入
     * @param offices
     */
    public void batchInsert(List<Office> offices,int type) {
        //Global.getMessagePropertiesVal("")
        int SIZE=30;
        if (offices == null || offices.size() < 1)
            return;
        int m = offices.size() / SIZE;
        for (int j = 0; j <= m; j++) {
            if (j == m) {
                List<Office> tempList = offices.subList(SIZE * j,
                        offices.size());
                if (tempList != null && tempList.size() > 0) {
                    dao.batchInsert(tempList,type);
                }
            } else {
                List<Office> tempList = offices
                        .subList(j * SIZE, (j + 1) * SIZE);
                if (tempList != null && tempList.size() > 0) {
                    dao.batchInsert(tempList,type);
                }
            }
        }
    }
    public  void  deleteAllData(int type){
        dao.deleteAllData(type);
    }

}
