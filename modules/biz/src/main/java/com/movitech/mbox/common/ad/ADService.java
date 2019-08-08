package com.movitech.mbox.common.ad;

import com.movitech.mbox.common.config.Global;
import com.movitech.mbox.common.utils.IdGen;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.modules.sys.entity.Area;
import com.movitech.mbox.modules.sys.entity.Office;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.service.OfficeService;
import com.movitech.mbox.modules.sys.service.SystemService;
import com.movitech.mbox.modules.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.provider.MD5;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.*;

/**
 * Created by Administrator on 2017/8/8.
 */
@Service
public class ADService {
   /* ldap_url=${ldap_url}
    ldap_adminname =${ldap_adminname}
    ldap_adminpassword*/
    static  String LDAP_URL = Global.getConfig("ldap_url");       //ldap://192.168.1.11:389"
    static  String adminName =Global.getConfig("ldap_adminname"); //devfinance@kaisagroup.com"; //注意用户名的
    // 写法：domain\User 或 User@domain.com
    static  String adminPassword =Global.getConfig("ldap_adminpassword"); //abc.123!@#" 密码
    @Autowired
    UserService userService;
    @Autowired
    OfficeService officeService;
 public List<ADDataVo>  getFromRemote(){

     List<ADDataVo> list=null;
     Hashtable HashEnv = new Hashtable();
     HashEnv.put(Context.SECURITY_PRINCIPAL, adminName); //AD User
     HashEnv.put(Context.SECURITY_CREDENTIALS, adminPassword); //AD Password
     HashEnv.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory"); //LDAP工厂类
     HashEnv.put(Context.PROVIDER_URL, LDAP_URL);
     HashEnv.put(Context.SECURITY_AUTHENTICATION, "simple"); //LDAP访问安全级别
     try {
         LdapContext ctx = new InitialLdapContext(HashEnv, null);
         SearchControls searchCtls = new SearchControls(); //Create the search controls
         searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE); //Specify the search scope
         String searchFilter = "objectClass=User"; //specify the LDAP search filter
         // String searchFilter = "objectClass=Group"; //specify the LDAP search filter
         String searchBase = "OU=佳兆业集团,DC=kaisagroup,DC=com"; //Specify the Base for the search//搜索域节点
         //String searchBase = "OU=佳兆业集团,DC=kaisagroup,DC=com"; //Specify the Base for the search//搜索域节点
         // String searchBase = "CN=金融集团开发ID,OU=系统管理员,DC=kaisagroup,DC=com"; //Specify the Base for the search//搜索域节点
         int totalResults = 0;
         NamingEnumeration answer = ctx.search(searchBase, searchFilter,searchCtls);
         if(answer==null||answer.equals(null)){
             System.out.println("answer is null");
         }else{
             System.out.println("answer not null");
         }

         System.out.println(answer.hasMoreElements());
         list=new ArrayList<>();
         while (answer.hasMoreElements()) {
             ADDataVo vo=new ADDataVo();
             totalResults++;
             SearchResult sr = (SearchResult) answer.next();
            // System.out.println("************************************************");
             System.out.println(sr.getName());
             vo.setDepartDesc(sr.getName());
             Attributes Attrs = sr.getAttributes();
             if (Attrs != null) {
                 try {
                     for (NamingEnumeration ne = Attrs.getAll(); ne.hasMore(); ) {
                         Attribute Attr = (Attribute) ne.next();
                         System.out.println("AttributeID属性名=" + Attr.getID().toString());
                         if("name".equals(Attr.getID().toString())){
                             //读取属性值
                             for (NamingEnumeration e = Attr.getAll(); e.hasMore();) {
                                 //System.out.println("AttributeValues属性值=" + e.next().toString());
                                 vo.setName(e.next().toString());
                             }
                         }
                         if("mail".equals(Attr.getID().toString())){
                             //读取属性值
                             for (NamingEnumeration e = Attr.getAll(); e.hasMore();) {
                                 //System.out.println("AttributeValues属性值=" + e.next().toString());
                                 vo.setMail(e.next().toString());
                             }
                         }
                         if("sAMAccountName".equals(Attr.getID().toString())){
                             //读取属性值
                             for (NamingEnumeration e = Attr.getAll(); e.hasMore();) {
                                 //System.out.println("AttributeValues属性值=" + e.next().toString());
                                 vo.setsAMAccountName(e.next().toString());
                             }
                         }
                         if("telephoneNumber".equals(Attr.getID().toString())){
                             //读取属性值
                             for (NamingEnumeration e = Attr.getAll(); e.hasMore();) {
                                 // System.out.println("AttributeValues属性值=" + e.next().toString());
                                 vo.setTelephoneNumber(e.next().toString());
                             }
                         }

                           /* //读取属性值
                            for (NamingEnumeration e = Attr.getAll(); e.hasMore();) {
                                System.out.println("AttributeValues属性值=" + e.next().toString());
                            }*/
                            /*System.out.println("---------------");
                            //读取属性值
                            Enumeration values = Attr.getAll();
                            if (values != null) { // 迭代
                                while (values.hasMoreElements()) {
                                    System.out.println("AttributeValues=" + values.nextElement());
                                }
                            }
                            System.out.println("    ---------------");*/
                         System.out.println("============================================");
                     }

                 }
                 catch (NamingException e) {
                     System.err.println("Throw Exception : " + e);
                 }
             }
             list.add(vo);
         }
         System.out.println("Number: " + totalResults);
         System.out.println(list.size());
         ctx.close();
     }
     catch (NamingException e) {
         e.printStackTrace();
         System.err.println("Throw Exception : " + e);
     }
     return  list;
 }
 /*
    处理从ad域服务器请求的数据
 * */
 public  void handData(List<ADDataVo> adDataVoList){
     if(adDataVoList==null||adDataVoList.size()<1)return;
     Map<String,Office> officeMap=new HashMap<>();
     List<User> userList=new ArrayList<>();
     User tempUser=new User();tempUser.setId("1");
     Area area=new Area(); area.setId("1");
     Office tempOffice=new Office();
     tempOffice.setId("1");
     for(ADDataVo dataVo:adDataVoList){
         String desc=dataVo.getDepartDesc();
         /****
          * 每次遍历都有组织结构list
          */
         List<Office> officeTreeList=new ArrayList<>();
         if(StringUtils.isNotEmpty(desc)){
            String[] deptStr= desc.split(",");
            if(deptStr!=null&&deptStr.length>0){
                for(String dep:deptStr){
                     if(dep.contains("OU")){
                         //当前office
                         String restDept=dep.replace("OU=","");
                         Office getOffice=officeMap.get(restDept);
                         if(getOffice==null){
                             Office office=new Office();
                             office.setId(IdGen.uuid());
                             office.setName(restDept);
                             office.setSort(1);
                             office.setArea(area);
                             office.setType("1");
                             office.setGrade("1");
                             office.setCreateDate(new Date());
                             office.setUpdateDate(new Date());
                             office.setCreateBy(tempUser);
                             office.setUpdateBy(tempUser);
                             office.setDelFlag("0");
                            /* String parentStr=deptStr[i+1];
                             if(parentStr.contains("OU")){
                                 String parentDept=parentStr.replace("OU=","");
                                 Office parentOffice=officeMap.get(parentDept);
                                 if(parentDept==null){
                                     Office poffice=new Office();
                                     poffice.setId(IdGen.uuid());
                                     poffice.setName(parentDept);
                                     poffice.setCreateDate(new Date());
                                     poffice.setUpdateDate(new Date());
                                     poffice.setCreateBy(tempUser);
                                     poffice.setUpdateBy(tempUser);
                                     poffice.setDelFlag("0");
                                     office.setParent(poffice);
                                     officeMap.put(parentDept,parentOffice);
                                 }else {
                                     office.setParent(parentOffice);
                                 }
                             }*/
                             //放进map中
                             officeMap.put(restDept,office);
                             officeTreeList.add(office);
                         }else {//如果不等于空
                             officeTreeList.add(getOffice);
                         }
                     }
                }
                /**
                 * 每次遍历都需要重新构建新的user
                 */
                for(int i=0;i<officeTreeList.size();i++){
                    Office office=officeTreeList.get(i);
                    List<Office> subListoffice=officeTreeList.subList(i,officeTreeList.size());
                    String parents="0,1,";
                    int tempcount=0;
                    for(Office sub:subListoffice){
                        if(tempcount==0){
                            tempcount++;
                            continue;
                        }
                        parents+=sub.getId()+",";
                        tempcount++;
                    }
                    office.setParentIds(parents);
                    if(i<officeTreeList.size()-1)
                    office.setParent(officeTreeList.get(i+1));
                    else {
                        office.setParent(tempOffice);
                    }
                }
                User user=new User();
                user.setId(dataVo.getsAMAccountName());
                user.setEmail(dataVo.getMail());
                user.setName(dataVo.getName());
                user.setLoginName(dataVo.getsAMAccountName());
                user.setMobile(dataVo.getTelephoneNumber());
                user.setPassword(SystemService.entryptPassword(dataVo.getsAMAccountName()));
                if(officeTreeList.size()>0)
                user.setOffice(officeTreeList.get(0));
                user.setCreateDate(new Date());
                user.setCreateBy(tempUser);
                user.setUpdateBy(tempUser);
                user.setUpdateDate(new Date());
                if(officeTreeList.size()>1) {
                    user.setCompany(officeTreeList.get(1));
                }else{
                    user.setCompany(officeTreeList.get(0));
                }
                user.setDelFlag("0");
                userList.add(user);
            }
         }
     }
     insertDb(userList,officeMap);
 }
 public void  insertDb(List<User> list,Map<String,Office> maps){
    userService.deleteAllData(1);
     userService.batchInsert(list,1);
     userService.deleteAllData(0);
     userService.batchInsert(list,0);
     // 用for循环的方式 遍历
     List<Office> officeList=new ArrayList<>();
     for (Map.Entry<String, Office> m :maps.entrySet())  {
         officeList.add(m.getValue());
     }
     officeService.deleteAllData(1);
     officeService.batchInsert(officeList,1);
    officeService.deleteAllData(0);
     officeService.batchInsert(officeList,0);
 }

}
