package com.movitech.mbox.modules.wdt.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.movitech.mbox.common.utils.SpringContextHolder;
import com.movitech.mbox.modules.sys.dao.OfficeDao;
import com.movitech.mbox.modules.sys.entity.Office;
import com.movitech.mbox.modules.sys.utils.TreeBuilder;
import com.movitech.mbox.modules.sys.utils.TreeNode;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskThemeDao;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskTheme;

import net.sf.json.JSONArray;

/**
 * 任务 工具类
 * @author Aibal.He
 *
 */
public class TaskUtils extends UserUtils{
	
	private static WdtTTaskThemeDao wdtTTaskThemeDao = SpringContextHolder.getBean(WdtTTaskThemeDao.class);
	
	/**
	 * 任务主题树
	 * @return
	 */
	public static JSONArray findAllTheme(){
		List<TreeNode> themeAllList = new LinkedList<TreeNode>();
        List<TreeNode> list = wdtTTaskThemeDao.findAllUsedTheme();
        themeAllList = TreeBuilder.buildByRecursive(list);  
        return JSONArray.fromObject(themeAllList);
    }
	/**
	 * 任务主题树
	 * @return
	 */
	public static WdtTTaskTheme getThemeById(String id ){
		Map<String,WdtTTaskTheme> map = new HashMap<String,WdtTTaskTheme>();; 
    		List<WdtTTaskTheme> themeAllList = new LinkedList<WdtTTaskTheme>();
    		WdtTTaskTheme wdtTTaskTheme = new WdtTTaskTheme();
    		wdtTTaskTheme.setDelFlag("0");
    		themeAllList = wdtTTaskThemeDao.findList(wdtTTaskTheme);
    		map = new HashMap<String,WdtTTaskTheme>();
    		if(themeAllList!=null && themeAllList.size()>0){
    			for (WdtTTaskTheme t : themeAllList) {
    				map.put(t.getCategoryId(), t);
				}
    		}  	
        return map.get(id);
    }
}
