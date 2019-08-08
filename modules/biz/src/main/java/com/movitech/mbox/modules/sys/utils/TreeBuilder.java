package com.movitech.mbox.modules.sys.utils;

import java.util.ArrayList;
import java.util.List;

public class TreeBuilder {
	/** 
     * 两层循环实现建树 
     * @param TreeNodes 传入的树节点列表 
     * @return 
     */  
    public static List<TreeNode> bulid(List<TreeNode> TreeNodes) {  
  
        List<TreeNode> trees = new ArrayList<TreeNode>();  
  
        for (TreeNode TreeNode : TreeNodes) {  
  
            if ("0".equals(TreeNode.getParentId())) {  
                trees.add(TreeNode);  
            }  
  
            for (TreeNode it : TreeNodes) {  
                if (it.getParentId() == TreeNode.getId()) {  
                    if (TreeNode.getChildren() == null) {  
                        TreeNode.setChildren(new ArrayList<TreeNode>());  
                    }  
                    TreeNode.getChildren().add(it);  
                }  
            }  
        }  
        return trees;  
    }  
  
    /** 
     * 使用递归方法建树 
     * @param TreeNodes 
     * @return 
     */  
    public static List<TreeNode> buildByRecursive(List<TreeNode> TreeNodes) {  
        List<TreeNode> trees = new ArrayList<TreeNode>();  
        for (TreeNode TreeNode : TreeNodes) {  
            if ("1".equals(TreeNode.getParentId())) {  
                trees.add(findChildren(TreeNode,TreeNodes));  
            }  
        }  
        return trees;  
    }  
  
    /** 
     * 递归查找子节点 
     * @param TreeNodes 
     * @return 
     */  
    public static TreeNode findChildren(TreeNode TreeNode,List<TreeNode> TreeNodes) {  
        for (TreeNode it : TreeNodes) {  
            if(TreeNode.getId().equals(it.getParentId())) {  
                if (TreeNode.getChildren() == null) {  
                    TreeNode.setChildren(new ArrayList<TreeNode>());  
                }  
                TreeNode.getChildren().add(findChildren(it,TreeNodes));  
            }  
        }  
        return TreeNode;  
    }  
}
