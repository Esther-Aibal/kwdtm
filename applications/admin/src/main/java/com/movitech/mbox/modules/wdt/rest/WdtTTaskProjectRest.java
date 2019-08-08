/**
 * 
 */
package com.movitech.mbox.modules.wdt.rest;


import com.movitech.mbox.common.ad.MAprojectService;
import com.movitech.mbox.common.rest.HResult;
import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.taskProject.service.TaskProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 任务关联项目，rest
 * @author felix.jin
 * 2017年8月29日
 */
@RestController
@RequestMapping(value = "${adminPath}/rest/task/project")
public class WdtTTaskProjectRest extends BaseController {

	@Autowired
	private MAprojectService maprojectService;
	
	@Autowired
	private TaskProjectService taskProjectService;

	@RequestMapping(value = "list", method = RequestMethod.POST)
	public HResult<Object> list() {
		User u=UserUtils.getUser();
		return new HResult<Object>(maprojectService.getMaProjectList(u.getId()));
	}

}