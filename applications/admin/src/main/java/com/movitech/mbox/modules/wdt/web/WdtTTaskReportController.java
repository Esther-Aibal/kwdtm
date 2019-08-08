/**
 *
 */
package com.movitech.mbox.modules.wdt.web;


import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.modules.wdt.taskStatus.req.TaskReportReq;
import com.movitech.mbox.modules.wdt.taskStatus.resp.TaskReportResp;
import com.movitech.mbox.modules.wdt.taskStatus.service.TaskReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * 任务报表
 */
@Controller
@RequestMapping(value = "${adminPath}/rest/wdt")
public class WdtTTaskReportController extends BaseController {

    @Autowired
    private TaskReportService taskReportService;



    @RequestMapping(value = {"report", ""})
    public String search(TaskReportReq req, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("req",req);
        TaskReportResp resp=taskReportService.getReport(req);
        model.addAttribute("resp",resp);
        return  "modules/wdt/task/report";
    }
    
}