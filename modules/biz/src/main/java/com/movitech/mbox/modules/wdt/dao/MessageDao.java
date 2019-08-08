package com.movitech.mbox.modules.wdt.dao;

import com.movitech.mbox.common.persistence.CrudDao;
import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface MessageDao extends CrudDao<Message>{
   public int updateSelective(Message message);
   public int batchInsert(List<Message> list);
   public List<String> findAllMessageIdByTaskClose();
   public int deleteAll(@Param("typelist")List<String> typelist,@Param("list")List<String> list);

   /**
    * 获得总数量
    * @param userId
    * @return
    */
   public int getMessageAmount(@Param("userId")String userId);

   /**
    * 获得代办数量
    * @param userId
    * @return
    */
   public int getTodaoMessageAmount(@Param("userId")String userId);

   /**
    * 获得待接收的数量
    * @param userId
    * @return
    */
   public int getTaskMessage(@Param("userId")String userId);
   
   public int updateRead(@Param("id")String id);

   public List<String> getTaskItemName(@Param("type")String type,@Param("taskId")String taskId);


   public int deleteByTypeAndTaskId(@Param("type")String type,@Param("taskId")String taskId);
   
   public int deleteByTypeAndItemId(@Param("type")String type,@Param("itemId")String itemId);
   
   Message getSubmitMessage(Message message);
   
   public int updateUrgeMessage(Message message);
   
   public int deleteByPinId(@Param("pinIds")List<String> typelist);
   public int deleteById(@Param("id")String id);
   
   public int updateReadIds(@Param("ids")List<String> ids);
}