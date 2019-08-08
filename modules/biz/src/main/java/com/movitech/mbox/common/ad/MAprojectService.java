package com.movitech.mbox.common.ad;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.movitech.mbox.common.config.Global;
import com.movitech.mbox.common.utils.CXFUtil;
import com.movitech.mbox.common.utils.IdGen;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.modules.base.vo.HResult;
import com.movitech.mbox.modules.sys.entity.Area;
import com.movitech.mbox.modules.sys.entity.Office;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.service.OfficeService;
import com.movitech.mbox.modules.sys.service.SystemService;
import com.movitech.mbox.modules.sys.service.UserService;
import com.movitech.mbox.modules.wdt.entity.ProjectInfo;
import com.movitech.mbox.modules.wdt.entity.ProjectInfoWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * Created by gorden on 2017/8/8.
 *
 */
@Service
public class MAprojectService {
    //需要文件配置
    protected Logger logger = LoggerFactory.getLogger(getClass());
    static String MA_WSDL=Global.getConfig("ma_wsdl");
    static String MA_RWDB="RWDB/projectList.dfx";
    static String MA_RWDB_DETAIL="RWDB/projectDetail.dfx";
    public Object  getProjectList(String loginName){
       // @徐其飞 麻烦下 把第一个接口换成 String projectDetail (String userid,String proid,String dpsid)   userid 传用户名  proid 传字符窜project   dpsid 传RWDB/projectList.dfx
        String[] strArray={loginName,"",MA_RWDB};
        return  CXFUtil.wsAction(MA_WSDL,"projectDetail",strArray);
    }
    //ProURL
    public HResult getProjectDetail(String userId, String proId){
        HResult resultH=new HResult(true);
        String[] strArray={userId,proId,MA_RWDB_DETAIL};
        Object object= CXFUtil.wsAction(MA_WSDL,"projectDetail",strArray);
        String result=object.toString();
        Gson gson=new Gson();
        ProjectInfoWrapper projectInfoWrapper = gson.fromJson(result, ProjectInfoWrapper.class);
        if(projectInfoWrapper.getResult()==1){
            resultH.setUrl(projectInfoWrapper.getProURL());
            resultH.setMessage("result is ok ");
        }else {
            resultH.setMessage("server is gone away  ");
            resultH.setResult(false);
        }
        return resultH;
    }

    /**
     * webservice 调用项目列表
     * @param  loginName 域账号
     * @return
     */
    public List<ProjectInfo> getMaProjectList(String loginName){
        Object object=getProjectList(loginName);
        Gson gson=new Gson();
        ProjectInfoWrapper projectInfoWrapper= gson.fromJson(object.toString(), ProjectInfoWrapper.class);
        if(projectInfoWrapper.getResult()==0){
            object=getProjectList(loginName);
            projectInfoWrapper=gson.fromJson(object.toString(), ProjectInfoWrapper.class);
        }
        if(projectInfoWrapper.getProjectList()!=null&&projectInfoWrapper.getProjectList().size()>0) {
              return  projectInfoWrapper.getProjectList();
        }
        return  null;
    }
}
