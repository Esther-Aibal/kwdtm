package com.movitech.mbox.modules.wdt.h5Rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.modules.base.vo.HResult;
import com.movitech.mbox.modules.wdt.entity.report.DelayTask;
import com.movitech.mbox.modules.wdt.service.WdtTTaskReportService;

/**
 * 任务延期 H5
 * @author Aibal.He
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/h5/taskDelay")
public class DelayReportH5Rest {

	@Autowired
	private WdtTTaskReportService wdtTTaskReportService;
	
	@RequestMapping(value = "searchTask",method = RequestMethod.POST)
	@ResponseBody
    public HResult<Object> searchTask(@RequestBody DelayTask delayTask) {
        Page<DelayTask> page = wdtTTaskReportService.searchTaskPage(new Page<DelayTask>(delayTask.getStart()/delayTask.getLength()+1, delayTask.getLength()), delayTask); 
		HResult<Object> result = new HResult<>(true);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		result.setValue(map);
		return result;
    }
	
	@RequestMapping(value = "detail",method = RequestMethod.POST)
	@ResponseBody
    public HResult<Object> detail(@RequestBody DelayTask delayTask) {
		HResult<Object> result = new HResult<>(true);
        DelayTask entity = wdtTTaskReportService.detail(delayTask); 
        Map<String, Object> map = new HashMap<String, Object>();
		map.put("delayTask", entity);
		result.setValue(map);
		return result;
    }
}
