package com.movitech.mbox.modules.wdt.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.movitech.mbox.common.config.ErrorMessage;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskSuggest;
import com.movitech.mbox.modules.wdt.service.WdtTTaskSuggestService;
@Controller
@RequestMapping(value = "${adminPath}/rest/wdt/wdtTTaskSuggest")
public class SuggestController {
	
	@Autowired
    private WdtTTaskSuggestService wdtTTaskSuggestService;
	
	@RequestMapping(value = "saveSuggest", method = RequestMethod.POST)
    @ResponseBody
    public com.movitech.mbox.common.rest.HResult<Object> saveMessage(@RequestBody WdtTTaskSuggest wdtTTaskSuggest, HttpServletRequest request) {
    	if(StringUtils.isEmpty(wdtTTaskSuggest.getSuggest())){

    		return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.NO_ERROR);
		}
		wdtTTaskSuggestService.insert(wdtTTaskSuggest);
    	return com.movitech.mbox.common.rest.HResult.success();
    }
	
	
}
