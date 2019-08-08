/**
 * 
 */
package com.movitech.mbox.modules.com.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.movitech.mbox.common.config.Global;
import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.modules.com.entity.ComDocument;
import com.movitech.mbox.modules.com.service.ComDocumentService;

/**
 * 文章Controller
 * @author Aibal.He
 * @version 2017-06-23
 */
@Controller
@RequestMapping(value = "${adminPath}/com/comDocument")
public class ComDocumentController extends BaseController {

    @Autowired
    private ComDocumentService comDocumentService;
    
    @ModelAttribute
    public ComDocument get(@RequestParam(required=false) String id) {
        ComDocument entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = comDocumentService.get(id);
        }
        if (entity == null){
            entity = new ComDocument();
        }
        return entity;
    }
    
    @RequiresPermissions("com:comDocument:view")
    @RequestMapping(value = {"list", ""})
    public String list(ComDocument comDocument, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ComDocument> page = comDocumentService.findPage(new Page<ComDocument>(request, response), comDocument); 
        model.addAttribute("page", page);
        return "modules/com/comDocumentList";
    }

    @RequiresPermissions("com:comDocument:view")
    @RequestMapping(value = "form")
    public String form(ComDocument comDocument, Model model) {
        model.addAttribute("comDocument", comDocument);
        return "modules/com/comDocumentForm";
    }

    @RequiresPermissions("com:comDocument:edit")
    @RequestMapping(value = "save")
    public String save(ComDocument comDocument, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, comDocument)){
            return form(comDocument, model);
        }
        String content =comDocument.getContent();
        content = content.replaceAll("&amp;", "&").replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">").replaceAll("&apos;", "'")
                .replaceAll("&quot;", "\"");
        comDocument.setContent(content);
        comDocumentService.save(comDocument);
        addMessage(redirectAttributes, "保存文章成功");
        return "redirect:"+Global.getAdminPath()+"/com/comDocument/?repage";
    }
    
    @RequiresPermissions("com:comDocument:edit")
    @RequestMapping(value = "delete")
    public String delete(ComDocument comDocument, RedirectAttributes redirectAttributes) {
        comDocumentService.delete(comDocument);
        addMessage(redirectAttributes, "删除文章成功");
        return "redirect:"+Global.getAdminPath()+"/com/comDocument/?repage";
    }

}