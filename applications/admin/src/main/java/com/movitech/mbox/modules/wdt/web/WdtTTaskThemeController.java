/**
 * 
 */
package com.movitech.mbox.modules.wdt.web;

import com.google.common.collect.Lists;
import com.movitech.mbox.common.config.Global;
import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.modules.sys.entity.Menu;
import com.movitech.mbox.modules.sys.service.SystemService;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskTemplete;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskTheme;
import com.movitech.mbox.modules.wdt.service.WdtTTaskTempleteService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskThemeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 任务分类Controller
 * @author felix
 * @version 2017-09-01
 */
@Controller
@RequestMapping(value = "${adminPath}/theme/wdtTTaskTheme")
public class WdtTTaskThemeController extends BaseController {

    @Autowired
    private WdtTTaskThemeService wdtTTaskThemeService;

    @Autowired
    private WdtTTaskTempleteService wdtTTaskTempleteService;
    
    @ModelAttribute
    public WdtTTaskTheme get(@RequestParam(required=false) String id) {
        WdtTTaskTheme entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = wdtTTaskThemeService.get(id);
        }
        if (entity == null){
            entity = new WdtTTaskTheme();
        }
        return entity;
    }

    
    @RequiresPermissions("theme:wdtTTaskTheme:view")
    @RequestMapping(value = {"list", ""})
    public String list(Model model) {
        List<WdtTTaskTheme> list = Lists.newArrayList();
        List<WdtTTaskTheme> sourcelist = wdtTTaskThemeService.findAll();
        WdtTTaskTheme.sortList(list, sourcelist, WdtTTaskTheme.getRootId(), true);
        model.addAttribute("list", list);
        return "modules/wdt/theme/wdtTTaskThemeList";
    }

    @RequiresPermissions("theme:wdtTTaskTheme:view")
    @RequestMapping(value = "form")
    public String form(WdtTTaskTheme wdtTTaskTheme, Model model) {
        model.addAttribute("wdtTTaskTheme", wdtTTaskTheme);
        WdtTTaskTheme theme=new WdtTTaskTheme();
        theme.setParentCategoryId(WdtTTaskTheme.getRootId());
        List<WdtTTaskTheme> list=wdtTTaskThemeService.findList(theme);
        theme.setCategoryId(WdtTTaskTheme.getRootId());
        theme.setCategoryName("无");
        list.add(0,theme);
        model.addAttribute("parents",list);
        return "modules/wdt/theme/wdtTTaskThemeForm";
    }

    @RequiresPermissions("theme:wdtTTaskTheme:edit")
    @RequestMapping(value = "save")
    public String save(WdtTTaskTheme wdtTTaskTheme, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, wdtTTaskTheme)){
            return form(wdtTTaskTheme, model);
        }
        wdtTTaskThemeService.save(wdtTTaskTheme);
        addMessage(redirectAttributes, "保存任务分类成功");
        return "redirect:"+Global.getAdminPath()+"/theme/wdtTTaskTheme/?repage";
    }
    
    @RequiresPermissions("theme:wdtTTaskTheme:edit")
    @RequestMapping(value = "delete")
    public String delete(WdtTTaskTheme wdtTTaskTheme, RedirectAttributes redirectAttributes) {
        int result = wdtTTaskThemeService.deleteTheme(wdtTTaskTheme);
        if(result == -1){
        	addMessage(redirectAttributes, "删除失败，任务分类已删除");
        }else if (result == -2){
        	addMessage(redirectAttributes, "删除失败，该任务分类有子分类，请先删除子分类");
        }else if (result == -3){
        	addMessage(redirectAttributes, "删除失败，任务分类已在任务中使用");
        }else{
        	addMessage(redirectAttributes, "删除任务分类成功");
        }
        return "redirect:"+Global.getAdminPath()+"/theme/wdtTTaskTheme/?repage";
    }

    @RequiresPermissions("theme:wdtTTaskTheme:view")
    @RequestMapping(value = "info")
    public String info(WdtTTaskTheme wdtTTaskTheme, Model model) {
        model.addAttribute("wdtTTaskTheme", wdtTTaskTheme);
        WdtTTaskTemplete wdtTTaskTemplete=new WdtTTaskTemplete();
        wdtTTaskTemplete.setThemeId(wdtTTaskTheme.getId());
        List<WdtTTaskTemplete> list= wdtTTaskTempleteService.findList(wdtTTaskTemplete);
        model.addAttribute("list", list);
        return "modules/wdt/theme/wdtTTaskThemeInfo";
    }

    

}