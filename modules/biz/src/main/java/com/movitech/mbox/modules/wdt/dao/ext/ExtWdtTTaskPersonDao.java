/**
 * 
 */
package com.movitech.mbox.modules.wdt.dao.ext;

import java.util.List;

import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskPerson;
import org.apache.ibatis.annotations.Param;


/**
 *任务干系人DAO补充
 * @author felix.jin
 * 2017年8月8日
 */
@MyBatisDao
public interface ExtWdtTTaskPersonDao {

	int deletePerson(@Param("ids") List<String> ids);

	List<WdtTTaskPerson> getReaders(String taskItemId);

	List<User> getReaderUsers(String taskItemId);

}