package com.movitech.mbox.modules.wdt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.movitech.mbox.common.utils.StringUtils;

import java.util.Date;

/**
 * Created by gorden on 2017/8/12.
 */
public class WdtTaskH5Vo {
    //发起人
    private String name,importantType,messageType,type;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createDate;
    private boolean isnew;//是否最新
    private  int progress;
    
    private String id; //taskId
    private String messageId;//消息id
    private String isRead;//消息id
    private String subTitle;
    private String senderName,reciverName;
    private String itemName;
    private  boolean isReplay;
    private  String itemId;

    private String pinId;
    public String getReciverName() {
        return reciverName;
    }

    public void setReciverName(String reciverName) {
        this.reciverName = reciverName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public boolean isReplay() {
        return isReplay;
    }

    public void setReplay(boolean replay) {
        isReplay = replay;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getName() {
       /* if(StringUtils.isNotEmpty(messageType)){
           if("1".equals(messageType)){
               return  "【催办提醒】"+name;
           }else if ("4".equals(messageType)){
               return  "【新任务提醒】"+name;
           }else if ("0".equals(messageType)){
               return  "【预警提醒】"+name;
           }else if ("2".equals(messageType)){
               return  "【进度提醒】"+name;
           }else if ("3".equals(messageType)){
               return  "【逾期提醒】"+name;
           }
        }*/
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImportantType() {
        return importantType;
    }

    public void setImportantType(String importantType) {
        this.importantType = importantType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isIsnew() {
//        Date now=new Date();
//        double day=DateUtils.getDistanceOfTwoDate(createDate,now);
//        if(day<7)return true;
//        else {
//            return false;
//        }
    	return isnew;
    }

    public void setIsnew(boolean isnew) {
        this.isnew = isnew;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public String getPinId() {
		return pinId;
	}

	public void setPinId(String pinId) {
		this.pinId = pinId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    
}
