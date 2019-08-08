/**
 * 
 */
package com.movitech.mbox.modules.wdt.dao.report;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.movitech.mbox.common.persistence.CrudDao;
import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.report.WdtTItemReport;

/**
 * 汇报情况类DAO接口
 * @author Aibal.He
 * @version 2017-10-26
 */
@MyBatisDao
public interface WdtTItemReportDao extends CrudDao<WdtTItemReport> {
	public void deleteCurrentData (WdtTItemReport wdtTItemReport);
	public int batchInsert(@Param("list") List<WdtTItemReport> list);
    
}