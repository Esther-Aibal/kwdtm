package com.movitech.mbox.modules.wdt.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.movitech.mbox.common.ad.ADDataVo;
import com.movitech.mbox.common.ad.ADService;
import com.movitech.mbox.common.ad.ADServiceJob;
import com.movitech.mbox.common.ad.DelayServiceJob;
import com.movitech.mbox.common.ad.MAprojectService;
import com.movitech.mbox.common.ad.MessageServiceJob;
import com.movitech.mbox.common.config.Global;
import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.modules.base.vo.HResult;
import com.movitech.mbox.modules.wdt.service.ProjectInfoService;

@Controller
@RequestMapping(value = "${adminPath}/rest/wdt/test")
public class TestCaseController extends BaseController {
    @Autowired
	ADService adService;
    @Autowired
	MAprojectService mAprojectService;
    @Autowired
	ProjectInfoService projectInfoService;
    
    @Autowired
    MessageServiceJob messagejob;
    @Autowired
    ADServiceJob aDServiceJob;
    
    @Autowired
    DelayServiceJob delayServiceJob;
    
    
	@RequestMapping(value = "test01",method = RequestMethod.GET)
	@ResponseBody
    public HResult<Object> searchTask(HttpServletRequest request, HttpServletResponse response) {
		List<ADDataVo> fromRemote = adService.getFromRemote();
		adService.handData(fromRemote);
		return new HResult<Object>(true);
    }
	@RequestMapping(value = "test02",method = RequestMethod.GET)
	@ResponseBody
	public HResult<Object> test02(HttpServletRequest request, HttpServletResponse response) {
		Global.getConfig("ldap_adminname");
		return new HResult<Object>(true);
	}
	@RequestMapping(value = "test03",method = RequestMethod.GET)
	@ResponseBody
	public HResult<Object> test03(HttpServletRequest request, HttpServletResponse response) {
		HResult result=new HResult<Object>(true);
		/*Object[] objects=mAprojectService.getProjectList();
		Gson  gson=new Gson();
		ProjectInfoWrapper projectInfoWrapper= gson.fromJson(objects[0].toString(), ProjectInfoWrapper.class);
		projectInfoService.batchInsert(projectInfoWrapper.getProjectList());*/
		//result.setValue(objects);
		return result;
	}
	@RequestMapping(value = "test04",method = RequestMethod.GET)
	@ResponseBody
	public HResult<Object> test04(HttpServletRequest request, HttpServletResponse response) {

		return mAprojectService.getProjectDetail("ailimin", "bca8befb-22f2-4d3f-9065-53b4557ed890");

	}
	
	@RequestMapping(value = "test05")
	public void test05(HttpServletRequest request, HttpServletResponse response) {

		messagejob.messagejob();

	}
	
	@RequestMapping(value = "test06")
	public void test06(HttpServletRequest request, HttpServletResponse response) {

		aDServiceJob.adAccountDownLoad();

	}
	
	@RequestMapping(value = "test07")
	public void test07(HttpServletRequest request, HttpServletResponse response) {

		delayServiceJob.delayjob();

	}
}
