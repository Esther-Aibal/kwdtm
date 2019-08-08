package com.movitech.mbox.modules.wdt.h5Rest;


import com.movitech.mbox.common.rest.HResult;
import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.modules.wdt.taskStatus.req.TaskReportReq;
import com.movitech.mbox.modules.wdt.taskStatus.resp.TaskReportResp;
import com.movitech.mbox.modules.wdt.taskStatus.service.TaskReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 任务报表 H5
 * @author felix.jin
 *
 */
@RestController
@RequestMapping(value = "${adminPath}/h5/task")
public class TaskReportH5Rest extends BaseController {

	@Autowired
	private TaskReportService taskReportService;

	@RequestMapping(value = "report",method = RequestMethod.POST)
	public HResult<Object> search(TaskReportReq req) {
		TaskReportResp resp=taskReportService.getReport(req);
		resp.setRows(null);
		return new HResult<Object>(resp);
	}
 
}