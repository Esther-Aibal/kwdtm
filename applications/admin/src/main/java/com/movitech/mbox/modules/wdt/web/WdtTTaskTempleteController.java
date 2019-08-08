/**
 * 
 */
package com.movitech.mbox.modules.wdt.web;

import com.movitech.mbox.common.config.Global;
import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.modules.base.vo.HResult;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskTemplete;
import com.movitech.mbox.modules.wdt.service.WdtTTaskTempleteService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 任务内容模板Controller
 * @author felix
 * @version 2017-09-05
 */
@Controller
@RequestMapping(value = "${adminPath}/theme/wdtTTaskTemplete")
public class WdtTTaskTempleteController extends BaseController {

    @Autowired
    private WdtTTaskTempleteService wdtTTaskTempleteService;
    
    @ModelAttribute
    public WdtTTaskTemplete get(@RequestParam(required=false) String id) {
        WdtTTaskTemplete entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = wdtTTaskTempleteService.get(id);
        }
        if (entity == null){
            entity = new WdtTTaskTemplete();
        }
        return entity;
    }
    
    @RequiresPermissions("theme:wdtTTaskTheme:view")
    @RequestMapping(value = "form")
    public String form(WdtTTaskTemplete wdtTTaskTemplete, Model model) {
        model.addAttribute("wdtTTaskTemplete", wdtTTaskTemplete);
        model.addAttribute("themeId",wdtTTaskTemplete.getThemeId());
        return "modules/wdt/theme/wdtTTaskTempleteForm";
    }

    @RequiresPermissions("theme:wdtTTaskTheme:edit")
    @RequestMapping(value = "save")
    public String save(WdtTTaskTemplete wdtTTaskTemplete, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, wdtTTaskTemplete)){
            return form(wdtTTaskTemplete, model);
        }
        wdtTTaskTempleteService.save(wdtTTaskTemplete);
        addMessage(redirectAttributes, "保存任务内容模板成功");
        return "redirect:"+Global.getAdminPath()+"/theme/wdtTTaskTheme/info?id="+wdtTTaskTemplete.getThemeId();
    }
    
    @RequiresPermissions("theme:wdtTTaskTheme:edit")
    @RequestMapping(value = "delete")
    public String delete(WdtTTaskTemplete wdtTTaskTemplete, RedirectAttributes redirectAttributes) {
        wdtTTaskTempleteService.delete(wdtTTaskTemplete);
        addMessage(redirectAttributes, "删除任务内容模板成功");
        return "redirect:"+Global.getAdminPath()+"/theme/wdtTTaskTheme/info?id="+wdtTTaskTemplete.getThemeId();
    }
    
    @RequiresPermissions("theme:wdtTTaskTheme:edit")
    @RequestMapping(value = "checkSingle")
    @ResponseBody
    public HResult<Boolean> checkSingle(WdtTTaskTemplete wdtTTaskTemplete, Model model, RedirectAttributes redirectAttributes) {
    	boolean flag = wdtTTaskTempleteService.checkSingle(wdtTTaskTemplete);
    	return new HResult<>(flag);
    }
}