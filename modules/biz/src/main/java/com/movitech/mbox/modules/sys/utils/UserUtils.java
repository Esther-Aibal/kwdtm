/**
 * 
 */
package com.movitech.mbox.modules.sys.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.google.common.collect.Maps;
import com.movitech.mbox.common.service.BaseService;
import com.movitech.mbox.common.utils.CacheUtils;
import com.movitech.mbox.common.utils.SpringContextHolder;
import com.movitech.mbox.modules.sys.dao.AreaDao;
import com.movitech.mbox.modules.sys.dao.DictDao;
import com.movitech.mbox.modules.sys.dao.MenuDao;
import com.movitech.mbox.modules.sys.dao.OfficeDao;
import com.movitech.mbox.modules.sys.dao.RoleDao;
import com.movitech.mbox.modules.sys.dao.UserDao;
import com.movitech.mbox.modules.sys.entity.Area;
import com.movitech.mbox.modules.sys.entity.Menu;
import com.movitech.mbox.modules.sys.entity.Office;
import com.movitech.mbox.modules.sys.entity.Role;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.security.SystemAuthorizingRealm.Principal;

import net.sf.json.JSONArray;

/**
 * 用户工具类
 * @author ThinkGem
 * @version 2013-12-05
 */
public class UserUtils {

    private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
    private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
    private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
    private static AreaDao areaDao = SpringContextHolder.getBean(AreaDao.class);
    private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);
    private static DictDao dictDao = SpringContextHolder.getBean(DictDao.class);
    
    public static final String USER_CACHE = "userCache";
    public static final String USER_CACHE_ID_ = "id_";
    public static final String USER_CACHE_LOGIN_NAME_ = "ln";
    public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "oid_";

    public static final String CACHE_ROLE_LIST = "roleList";
    public static final String CACHE_MENU_LIST = "menuList";
    public static final String CACHE_AREA_LIST = "areaList";
    public static final String CACHE_OFFICE_LIST = "officeList";
    public static final String CACHE_OFFICE_ALL_LIST = "officeAllList";
    public static final String CACHE_OFFICE_LIST_PC="officeAllListPC";
    public static final String CACHE_USER_MAP = "userMap";
    public static final String CACHE_OFFICE_NAME_MAP = "officeNameMap";
    /**
     * 根据ID获取用户
     * @param id
     * @return 取不到返回null
     */
    public static User get(String id){
        User user = (User)CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
        if (user ==  null){
            user = userDao.get(id);
            if (user == null){
                return null;
            }
            user.setRoleList(roleDao.findList(new Role(user)));
            CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
            CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
        }
        return user;
    }
    
    /**
     * 根据登录名获取用户
     * @param loginName
     * @return 取不到返回null
     */
    public static User getByLoginName(String loginName){
        User user = (User)CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName);
        if (user == null){
            user = userDao.getByLoginName(new User(null, loginName));
            if (user == null){
                return null;
            }
            user.setRoleList(roleDao.findList(new Role(user)));
            CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
            CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
        }
        return user;
    }
    
    /**
     * 清除当前用户缓存
     */
    public static void clearCache(){
        removeCache(CACHE_ROLE_LIST);
        removeCache(CACHE_MENU_LIST);
        removeCache(CACHE_AREA_LIST);
        removeCache(CACHE_OFFICE_LIST);
        removeCache(CACHE_OFFICE_ALL_LIST);
        removeCache(CACHE_OFFICE_LIST_PC);
        removeCache(CACHE_USER_MAP);
        removeCache(CACHE_OFFICE_NAME_MAP);
        UserUtils.clearCache(getUser());
    }
    
    /**
     * 清除指定用户缓存
     * @param user
     */
    public static void clearCache(User user){
        CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
        CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName());
        CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getOldLoginName());
//        if (user.getOffice() != null && user.getOffice().getId() != null){
//            CacheUtils.remove(USER_CACHE, USER_CACHE_LIST_BY_OFFICE_ID_ + user.getOffice().getId());
//        }
    }
    
    /**
     * 获取当前用户
     * @return 取不到返回 new User()
     */
    public static User getUser(){
        Principal principal = getPrincipal();
        if (principal!=null){
            User user = get(principal.getId());
            if (user != null){
            	List<String> roleids = dictDao.findValueListByType("bus_admin");
            	StringBuffer sb  = new StringBuffer();
            	if(roleids!=null &&roleids.size()>0){
            		for (String string : roleids) {
            			sb.append(","+string);
					}
            		sb.append(",");
            	}
            	String superPower  = sb.toString();
            	if(user.getRoleIdList()!=null &&user.getRoleIdList().size()>0 ){
            		for (String roleid : user.getRoleIdList()) {
						if(superPower.contains(","+roleid+",")){
							user.setIsSuperPower(true);
							break;
						}
					}
            	}
                return user;
            }
            return new User();
        }
        // 如果没有登录，则返回实例化空的User对象。
        return new User();
    }

    /**
     * 获取当前用户角色列表
     * @return
     */
    public static List<Role> getRoleList(){
        @SuppressWarnings("unchecked")
        List<Role> roleList = (List<Role>)getCache(CACHE_ROLE_LIST);
        if (roleList == null){
            User user = getUser();
            if (user.isAdmin()){
                roleList = roleDao.findAllList(new Role());
            }else{
                Role role = new Role();
                role.getSqlMap().put("dsf", BaseService.dataScopeFilter(user.getCurrentUser(), "o", "u"));
                roleList = roleDao.findList(role);
            }
            putCache(CACHE_ROLE_LIST, roleList);
        }
        return roleList;
    }
    
    /**
     * 获取当前用户授权菜单
     * @return
     */
    public static List<Menu> getMenuList(){
        @SuppressWarnings("unchecked")
        List<Menu> menuList = (List<Menu>)getCache(CACHE_MENU_LIST);
        if (menuList == null){
            User user = getUser();
            if (user.isAdmin()){
                menuList = menuDao.findAllList(new Menu());
            }else{
                Menu m = new Menu();
                m.setUserId(user.getId());
                menuList = menuDao.findByUserId(m);
            }
            putCache(CACHE_MENU_LIST, menuList);
        }
        return menuList;
    }
    
    public static Object[] getH5MenuList(){
    	Set<Integer> result =  new HashSet<Integer>();
    	Menu m = new Menu();
    	User user = getUser();
        m.setUserId(user.getId());
        List<Menu> menuList = menuDao.findByUserId(m);
        if(menuList!= null &&menuList.size()>0){
        	for (Menu menu : menuList) {
        		if(menu.getHref() == null){
        		}else if(menu.getHref().equals("/rest/wdt/task")){
        			result.add(0);
        			result.add(1);
        			result.add(2);
        		}else if(menu.getHref().equals("/rest/wdt/report")){
        			result.add(3);
        		}
			}
        }
        return result.toArray();
    }
    
    /**
     * 获取当前用户授权的区域
     * @return
     */
    public static List<Area> getAreaList(){
        @SuppressWarnings("unchecked")
        List<Area> areaList = (List<Area>)getCache(CACHE_AREA_LIST);
        if (areaList == null){
            areaList = areaDao.findAllList(new Area());
            putCache(CACHE_AREA_LIST, areaList);
        }
        return areaList;
    }
    
    /**
     * 获取当前用户有权限访问的部门
     * @return
     */
    public static List<Office> getOfficeList(){
        @SuppressWarnings("unchecked")
        List<Office> officeList = (List<Office>)getCache(CACHE_OFFICE_LIST);
        if (officeList == null){
            User user = getUser();
            if (user.isAdmin()){
                officeList = officeDao.findAllList(new Office());
            }else{
                Office office = new Office();
                office.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "a", ""));
                officeList = officeDao.findList(office);
            }
            putCache(CACHE_OFFICE_LIST, officeList);
        }
        return officeList;
    }
    public static JSONArray findAllOffice(){
    	List<TreeNode> officeList = (List<TreeNode>)getCache(CACHE_OFFICE_LIST_PC); 
    	if(officeList == null){
    		officeList = new LinkedList<TreeNode>();
        	Office office = new Office();
        	String extId ="1";
        	office.setParentIds("%,"+extId+",%");
            List<TreeNode> list = officeDao.findByParentIdsLikeAboutTree(office);
            officeList = TreeBuilder.buildByRecursive(list);  
           
            putCache(CACHE_OFFICE_LIST_PC, officeList);
    	}
        return JSONArray.fromObject(officeList);
    }
    
    public static String getOfficeNameByUserId(String userId){
    	Map<String, String> officeMap = (Map<String, String>)getCache(CACHE_OFFICE_NAME_MAP); 
    	if(officeMap == null){
    		officeMap = new HashMap<String, String>();
        	Office office = new Office();
        	String extId ="1";
        	office.setParentIds("%,"+extId+",%");
            List<TreeNode> list = officeDao.findByParentIdsLikeAboutTree(office);
            if(list!=null && list.size()>0){
            	for (TreeNode treeNode : list) {
            		officeMap.put(treeNode.getId(), treeNode.getName());
				}
            }
            putCache(CACHE_OFFICE_NAME_MAP, officeMap);
            
    	}
    	User user = userDao.get(userId);
        return officeMap.get(user.getOffice().getId());
    }
    
    /**
     * 获取当前用户有权限访问的部门
     * @return
     */
    public static List<Office> getOfficeAllList(){
        @SuppressWarnings("unchecked")
        List<Office> officeList = (List<Office>)getCache(CACHE_OFFICE_ALL_LIST);
        if (officeList == null){
            officeList = officeDao.findAllList(new Office());
        }
        return officeList;
    }
    
    /**
     * 获取授权主要对象
     */
    public static Subject getSubject(){
        return SecurityUtils.getSubject();
    }
    
    /**
     * 获取当前登录者对象
     */
    public static Principal getPrincipal(){
        try{
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal)subject.getPrincipal();
            if (principal != null){
                return principal;
            }
//            subject.logout();
        }catch (UnavailableSecurityManagerException e) {
            
        }catch (InvalidSessionException e){
            
        }
        return null;
    }
    
    public static Session getSession(){
        try{
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null){
                session = subject.getSession();
            }
            if (session != null){
                return session;
            }
//            subject.logout();
        }catch (InvalidSessionException e){
            
        }
        return null;
    }
    
    // ============== User Cache ==============
    
    public static Object getCache(String key) {
        return getCache(key, null);
    }
    
    public static Object getCache(String key, Object defaultValue) {
//        Object obj = getCacheMap().get(key);
        Object obj = getSession().getAttribute(key);
        return obj==null?defaultValue:obj;
    }

    public static void putCache(String key, Object value) {
//        getCacheMap().put(key, value);
        getSession().setAttribute(key, value);
    }

    public static void removeCache(String key) {
//        getCacheMap().remove(key);
        getSession().removeAttribute(key);
    }
    
//    public static Map<String, Object> getCacheMap(){
//        Principal principal = getPrincipal();
//        if(principal!=null){
//            return principal.getCacheMap();
//        }
//        return new HashMap<String, Object>();
//    }
    
}
