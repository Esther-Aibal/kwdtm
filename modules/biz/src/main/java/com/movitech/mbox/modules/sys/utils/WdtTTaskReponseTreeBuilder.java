package com.movitech.mbox.modules.sys.utils;

import java.util.ArrayList;
import java.util.List;

import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.modules.wdt.entity.ext.WdtTTaskReponseTreeNode;

public class WdtTTaskReponseTreeBuilder {
	/** 
     * 两层循环实现建树 
     * @param WdtTTaskReponseTreeNodes 传入的树节点列表 
     * @return 
     */  
    public static List<WdtTTaskReponseTreeNode> bulid(List<WdtTTaskReponseTreeNode> WdtTTaskReponseTreeNodes,String comentsId) {  
  
        List<WdtTTaskReponseTreeNode> trees = new ArrayList<WdtTTaskReponseTreeNode>();  
  
        for (WdtTTaskReponseTreeNode WdtTTaskReponseTreeNode : WdtTTaskReponseTreeNodes) {  
  
            if (comentsId.equals(WdtTTaskReponseTreeNode.getComentsId()) 
            		&& StringUtils.isEmpty(WdtTTaskReponseTreeNode.getParentId())) {  
                trees.add(WdtTTaskReponseTreeNode);  
            }  
  
            for (WdtTTaskReponseTreeNode it : WdtTTaskReponseTreeNodes) {  
                if (it.getParentId().equals(WdtTTaskReponseTreeNode.getId())) {  
                    if (WdtTTaskReponseTreeNode.getChildren() == null) {  
                        WdtTTaskReponseTreeNode.setChildren(new ArrayList<WdtTTaskReponseTreeNode>());  
                    }  
                    WdtTTaskReponseTreeNode.getChildren().add(it);  
                }  
            }  
        }  
        return trees;  
    }  
  
    /** 
     * 使用递归方法建树 
     * @param WdtTTaskReponseTreeNodes 
     * @return 
     */  
    public static List<WdtTTaskReponseTreeNode> buildByRecursive(List<WdtTTaskReponseTreeNode> WdtTTaskReponseTreeNodes) {  
        List<WdtTTaskReponseTreeNode> trees = new ArrayList<WdtTTaskReponseTreeNode>();  
        for (WdtTTaskReponseTreeNode WdtTTaskReponseTreeNode : WdtTTaskReponseTreeNodes) {  
            if ("1".equals(WdtTTaskReponseTreeNode.getParentId())) {  
                trees.add(findChildren(WdtTTaskReponseTreeNode,WdtTTaskReponseTreeNodes));  
            }  
        }  
        return trees;  
    }  
  
    /** 
     * 递归查找子节点 
     * @param WdtTTaskReponseTreeNodes 
     * @return 
     */  
    public static WdtTTaskReponseTreeNode findChildren(WdtTTaskReponseTreeNode WdtTTaskReponseTreeNode,List<WdtTTaskReponseTreeNode> WdtTTaskReponseTreeNodes) {  
        for (WdtTTaskReponseTreeNode it : WdtTTaskReponseTreeNodes) {  
            if(WdtTTaskReponseTreeNode.getId().equals(it.getParentId())) {  
                if (WdtTTaskReponseTreeNode.getChildren() == null) {  
                    WdtTTaskReponseTreeNode.setChildren(new ArrayList<WdtTTaskReponseTreeNode>());  
                }  
                WdtTTaskReponseTreeNode.getChildren().add(findChildren(it,WdtTTaskReponseTreeNodes));  
            }  
        }  
        return WdtTTaskReponseTreeNode;  
    }  
}
