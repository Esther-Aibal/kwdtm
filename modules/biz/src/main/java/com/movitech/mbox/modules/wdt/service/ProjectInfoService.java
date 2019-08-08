/**
 * 
 */
package com.movitech.mbox.modules.wdt.service;

import com.movitech.mbox.modules.wdt.dao.MessageDao;
import com.movitech.mbox.modules.wdt.dao.ProjectInfoDao;
import com.movitech.mbox.modules.wdt.entity.Message;
import com.movitech.mbox.modules.wdt.entity.ProjectInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service
 * @author xuqifei
 * @version 2017-08-07
 */
@Service
@Transactional(readOnly = false)
public class ProjectInfoService {
 @Autowired
 private ProjectInfoDao projectInfoDao;
	/**
	 * 批量插入
	 * @param
	 */
	public void batchInsert(List<ProjectInfo> infos) {

		int SIZE=30;
		if (infos == null || infos.size() < 1)
			return;
		int m = infos.size() / SIZE;
		for (int j = 0; j <= m; j++) {
			if (j == m) {
				List<ProjectInfo> tempList = infos.subList(SIZE * j,
						infos.size());
				if (tempList != null && tempList.size() > 0) {
					projectInfoDao.batchInsert(tempList);
				}
			} else {
				List<ProjectInfo> tempList = infos
						.subList(j * SIZE, (j + 1) * SIZE);
				if (tempList != null && tempList.size() > 0) {
					projectInfoDao.batchInsert(tempList);
				}
			}
		}
	}




}