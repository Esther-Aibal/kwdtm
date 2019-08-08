package com.movitech.mbox.common.ad;

import com.google.gson.Gson;
import com.movitech.mbox.common.config.Global;
import com.movitech.mbox.common.utils.IdGen;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.modules.sys.entity.Area;
import com.movitech.mbox.modules.sys.entity.Office;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.service.OfficeService;
import com.movitech.mbox.modules.sys.service.SystemService;
import com.movitech.mbox.modules.sys.service.UserService;
import com.movitech.mbox.modules.wdt.dao.ProjectInfoDao;
import com.movitech.mbox.modules.wdt.entity.ProjectInfoWrapper;
import com.movitech.mbox.modules.wdt.service.ProjectInfoService;
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
 */
@Service("MAJobService")
public class MAJobService {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    MAprojectService mAprojectService;
    @Autowired
    ProjectInfoService projectInfoService;
    @Autowired
    ProjectInfoDao    projectInfoDao;
    //暂时不用
  /* public void serviceMaProject(){
       Object[] objects=mAprojectService.getProjectList();
       Gson gson=new Gson();
       ProjectInfoWrapper projectInfoWrapper= gson.fromJson(objects[0].toString(), ProjectInfoWrapper.class);
       if(projectInfoWrapper.getResult()==0){
           objects=mAprojectService.getProjectList();
           projectInfoWrapper=gson.fromJson(objects[0].toString(), ProjectInfoWrapper.class);
       }else {
           if(projectInfoWrapper.getProjectList()!=null&&projectInfoWrapper.getProjectList().size()>0) {
               logger.debug("迈安回填数据的数量："+projectInfoWrapper.getProjectList().size());
               projectInfoDao.deleteAll();
               projectInfoService.batchInsert(projectInfoWrapper.getProjectList());
               logger.debug("迈安回填数据的进入db："+projectInfoWrapper.getProjectList().size());
           }
       }

   }*/
}
