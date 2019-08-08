/**
 * 
 */
package com.movitech.mbox.modules.sys.dao;

import com.movitech.mbox.common.persistence.TreeDao;
import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.sys.entity.Office;
import com.movitech.mbox.modules.sys.utils.TreeNode;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 机构DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
    /**
     *
     * @param list
     * @param type type==0 操作sys_office
     *             type==1 操作sys_office_temp
     * @return
     */
    public int batchInsert(@Param("list") List<Office> list,@Param("type")int type);

    /**
     *
     * @param type type==0 操作sys_office
     *             type==1 操作sys_office_temp
     */
    public void deleteAllData(@Param("type")int type);
    
    List<TreeNode> findByParentIdsLikeAboutTree(Office office);
}
