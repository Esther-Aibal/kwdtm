package com.movitech.mbox.modules.wdt.rest;

import com.movitech.mbox.common.ad.MAJobService;
import com.movitech.mbox.common.ad.MAprojectService;
import com.movitech.mbox.common.config.*;
import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.modules.base.vo.HResult;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.entity.*;
import com.movitech.mbox.modules.wdt.entity.ext.ExtWdtTTaskComments;
import com.movitech.mbox.modules.wdt.operateLog.service.OperateLogService;
import com.movitech.mbox.modules.wdt.req.TaskCommentReq;
import com.movitech.mbox.modules.wdt.service.*;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/rest/wdt/project")
public class ProjectController extends BaseController {
    @Autowired
	MAprojectService mAprojectService;
	@RequestMapping(value="getUrl")
	@ResponseBody
	public HResult geProjectUrl(String projectId) throws  Exception{
        if(StringUtils.isEmpty(projectId))return  new HResult(false);
		String userId=UserUtils.getUser().getId();
		//byte[] encodeBase64 = Base64.encodeBase64(userId.getBytes("UTF-8"));
		//String endname=new String(encodeBase64);
		return  mAprojectService.getProjectDetail(userId,projectId);
	}
}
