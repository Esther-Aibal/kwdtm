/**
 * 
 */
package com.movitech.mbox.modules.sys.service;

import com.movitech.mbox.common.service.BaseService;
import com.movitech.mbox.common.utils.Collections3;
import com.movitech.mbox.modules.sys.dao.UserDao;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import org.activiti.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserService extends BaseService {
    
    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;
    
    @Autowired
    private UserDao userDao;

    private IdentityService identityService;

    /**
     * 获取用户
     * @param id
     * @return
     */
    public User getUser(String id) {
        return UserUtils.get(id);
    }
    
    /**
     * 获取数据库用户信息
     * @param id
     * @return
     */
    public User getUserFromDb(String id) {
        return userDao.get(id);
    }

    @Transactional(readOnly = false)
    public void updateUser(User user) {
        user.preUpdate();
        userDao.update(user);
    }

    /**
     * 根据登录名获取用户
     * @param loginName
     * @return
     */
    public User getUserByLoginName(String loginName) {
        return UserUtils.getByLoginName(loginName);
    }


    /**
     * 验证Token
     * @param token
     * @return
     */
    public User getUserByToken(String token){
        List<User> userList = userDao.getUserByToken(token);
        if(!Collections3.isEmpty(userList)){
            return userList.get(0);
        }else{
            return null;
        }
    }

    /**
     * 批量插入
     * @param users
     */
    public void batchInsert(List<User> users,int type) {
        //Global.getMessagePropertiesVal("")
        int SIZE=30;
        if (users == null || users.size() < 1)
            return;
        int m = users.size() / SIZE;
        for (int j = 0; j <= m; j++) {
            if (j == m) {
                List<User> tempList = users.subList(SIZE * j,
                        users.size());
                if (tempList != null && tempList.size() > 0) {
                    userDao.batchInsert(tempList,type);
                }
            } else {
                List<User> tempList = users
                        .subList(j * SIZE, (j + 1) * SIZE);
                if (tempList != null && tempList.size() > 0) {
                    userDao.batchInsert(tempList,type);
                }
            }
        }
    }
    public  void  deleteAllData(int type){
        userDao.deleteAllData(type);
    }
    /**
     * 根据登录名获取用户数据/数据实时查询
     * @param loginName
     * @return
     */
    public User _getUserByLoginName(String loginName) {
        return  userDao.getByLoginName(new User(null, loginName));
    }

}
