/**
 * 
 */
package com.movitech.mbox.modules.wdt.rest;



import com.movitech.mbox.modules.wdt.operateLog.entity.OperateLog;
import com.movitech.mbox.modules.wdt.operateLog.service.OperateLogService;
import com.movitech.mbox.modules.wdt.req.TaskItemReq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.rest.HResult;
import com.movitech.mbox.common.web.BaseController;

/**
 * 操作日志rest
 * @author felix.jin
 * 2017年8月22日
 */
@RestController
@RequestMapping(value = "${adminPath}/rest/operateLog")
public class WdtTOperateLogRest extends BaseController {
	
	@Autowired
	private OperateLogService operateLogService;

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public HResult<Object> list(@RequestBody TaskItemReq req) {
    	Page<OperateLog> page= operateLogService.findPage(req);
        return new HResult<Object>(page);
    }
   

}