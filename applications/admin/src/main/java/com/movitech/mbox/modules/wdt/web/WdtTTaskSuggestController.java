/**
 * 
 */
package com.movitech.mbox.modules.wdt.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.movitech.mbox.common.config.ErrorMessage;
import com.movitech.mbox.common.config.Global;
import com.movitech.mbox.common.config.OperateLogEnum;
import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskSuggest;
import com.movitech.mbox.modules.wdt.entity.ext.AtMessage;
import com.movitech.mbox.modules.wdt.service.WdtTTaskSuggestService;

/**
 * 意见收集Controller
 * @author Aibal.He
 * @version 2018-01-11
 */
@Controller
@RequestMapping(value = "${adminPath}/wdt/wdtTTaskSuggest")
public class WdtTTaskSuggestController extends BaseController {

    @Autowired
    private WdtTTaskSuggestService wdtTTaskSuggestService;
    
    @ModelAttribute
    public WdtTTaskSuggest get(@RequestParam(required=false) String id) {
        WdtTTaskSuggest entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = wdtTTaskSuggestService.get(id);
        }
        if (entity == null){
            entity = new WdtTTaskSuggest();
        }
        return entity;
    }
    
    @RequiresPermissions("wdt:wdtTTaskSuggest:view")
    @RequestMapping(value = {"list", ""})
    public String list(WdtTTaskSuggest wdtTTaskSuggest, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<WdtTTaskSuggest> page = wdtTTaskSuggestService.findPage(new Page<WdtTTaskSuggest>(request, response), wdtTTaskSuggest); 
        model.addAttribute("page", page);
        return "modules/wdt/wdtTTaskSuggestList";
    }

    @RequiresPermissions("wdt:wdtTTaskSuggest:view")
    @RequestMapping(value = "form")
    public String form(WdtTTaskSuggest wdtTTaskSuggest, Model model) {
        model.addAttribute("wdtTTaskSuggest", wdtTTaskSuggest);
        return "modules/wdt/wdtTTaskSuggestForm";
    }

    @RequiresPermissions("wdt:wdtTTaskSuggest:edit")
    @RequestMapping(value = "save")
    public String save(WdtTTaskSuggest wdtTTaskSuggest, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, wdtTTaskSuggest)){
            return form(wdtTTaskSuggest, model);
        }
        wdtTTaskSuggestService.save(wdtTTaskSuggest);
        addMessage(redirectAttributes, "保存意见收集成功");
        return "redirect:"+Global.getAdminPath()+"/wdt/wdtTTaskSuggest/?repage";
    }
    
    @RequiresPermissions("wdt:wdtTTaskSuggest:edit")
    @RequestMapping(value = "delete")
    public String delete(WdtTTaskSuggest wdtTTaskSuggest, RedirectAttributes redirectAttributes) {
        wdtTTaskSuggestService.delete(wdtTTaskSuggest);
        addMessage(redirectAttributes, "删除意见收集成功");
        return "redirect:"+Global.getAdminPath()+"/wdt/wdtTTaskSuggest/?repage";
    }

}