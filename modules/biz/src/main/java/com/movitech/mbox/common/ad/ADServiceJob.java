package com.movitech.mbox.common.ad;

import com.movitech.mbox.common.utils.IdGen;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.modules.sys.entity.Area;
import com.movitech.mbox.modules.sys.entity.Office;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.service.OfficeService;
import com.movitech.mbox.modules.sys.service.UserService;
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
 * 定时抓取AD域账号信息
 */
@Service("aDServiceJob")
public class ADServiceJob {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    ADService adService;
    public void adAccountDownLoad(){
        logger.debug("Ad account infomation from AD start....");

        List<ADDataVo> fromRemoteData = adService.getFromRemote();
        adService.handData(fromRemoteData);
        logger.debug("Ad account infomation from AD start....");
    }
    
}
