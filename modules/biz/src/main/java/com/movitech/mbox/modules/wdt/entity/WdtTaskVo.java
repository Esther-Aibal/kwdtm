package com.movitech.mbox.modules.wdt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.movitech.mbox.common.utils.DateUtils;
import com.movitech.mbox.common.utils.StringUtils;

import java.util.Date;

/**
 * Created by gorden on 2017/8/12.
 */
public class WdtTaskVo {
    //发起人
    private String name,importantType,messageType,type;
    private String createDate;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date endDate;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date startDate;
    private boolean isnew;//是否最新
    private  int progress;
    private  boolean isReplay;
    private  String itemId;
    
    private String pinId;

    private String executionStatus;  
    private int showColor =0;//颜色 0 绿色 2 橙色 1 红色
    private Warning warning;//预警
    
    private String senderName;
    private String reciverName;
    private String messageReciverName;
    private String isParticipant;
    
    private int rowType = 1;//0 title 1 data 
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    private String id; //taskId
    private String messageId;//消息id
    private String isRead;//消息id

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
    @JsonIgnore
    public String  subTitle;

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
    public String getTitleName(){
    	return name;
    }
    public String getName() {
        String rename="";
        //5：进度反馈 6：进度汇报 7：反馈回复 8：汇报回复

        if(StringUtils.isNotEmpty(messageType)){
           if("1".equals(messageType)){
               rename=  "【催办提醒】"+name;
           }else if ("4".equals(messageType)){
               rename=  "【新任务提醒】"+name;
           }else if ("0".equals(messageType)){
               rename=  "【预警提醒】"+name;
           }else if ("2".equals(messageType)){
               rename=  "【进度提醒】"+name;
           }else if ("3".equals(messageType)){
               rename=  "【逾期提醒】"+name;
           }else if("5".equals(messageType)){
               rename=  "【整体汇报】"+name;
           }else if("6".equals(messageType)){
               rename=  "【节点汇报】"+name;
           }else if("7".equals(messageType)){
               rename=  "【整体汇报回复】"+name;
           }else if("8".equals(messageType)){
               rename=  "【节点汇报回复】"+name;
           }else if("10".equals(messageType)){
               rename=  "【催办建议】"+name;
           }else if("11".equals(messageType)){
               rename=  "【整体汇报@提醒】"+name;
           }else if("12".equals(messageType)){
               rename=  "【节点汇报@提醒】"+name;
           }else if("13".equals(messageType)){
               rename=  "【整体汇报回复@提醒】"+name;
           }else if("14".equals(messageType)){
               rename=  "【节点汇报回复@提醒】"+name;
           }else if("15".equals(messageType)){
               rename=  "【催办建议@提醒】"+name;
           }else if("16".equals(messageType)){
               rename=  "【@提醒】"+name;
           }
        }else {
            return name;
        }
        if(StringUtils.isNotEmpty(subTitle)){
             rename=rename+"    ——"+subTitle;
        }
        if("7".equals(messageType)||"8".equals(messageType)||"10".equals(messageType)){
            rename=  rename +"（"+senderName+"回复了"+reciverName+"）";
        }
        if("5".equals(messageType)){
        	rename=  rename +"（"+senderName+"新增了整体汇报）";
        }else if("6".equals(messageType)){
            rename=  rename +"（"+senderName+"新增了节点汇报）";
        }
        if("11".equals(messageType)||"12".equals(messageType)||"13".equals(messageType)||"14".equals(messageType)||"15".equals(messageType)||"16".equals(messageType)){
            rename=  rename +" @"+messageReciverName;
        }
        return rename;
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

    public String getCreateDate() {
        if(startDate!=null && endDate!=null)
        {
          return   DateUtils.formatDate(startDate)+"至"+DateUtils.formatDate(endDate);
        }
        return createDate;
    }

    public void setCreateDate(String createDate) {
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

	public String getExecutionStatus() {
		return executionStatus;
	}

	public void setExecutionStatus(String executionStatus) {
		this.executionStatus = executionStatus;
	}

	public int getShowColor() {
		return showColor;
	}

	public void setShowColor(int showColor) {
		this.showColor = showColor;
	}

	public Warning getWarning() {
		return warning;
	}

	public void setWarning(Warning warning) {
		this.warning = warning;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getReciverName() {
		return reciverName;
	}

	public void setReciverName(String reciverName) {
		this.reciverName = reciverName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessageReciverName() {
		return messageReciverName;
	}

	public void setMessageReciverName(String messageReciverName) {
		this.messageReciverName = messageReciverName;
	}

	public String getIsParticipant() {
		return isParticipant;
	}

	public void setIsParticipant(String isParticipant) {
		this.isParticipant = isParticipant;
	}

	public int getRowType() {
		return rowType;
	}

	public void setRowType(int rowType) {
		this.rowType = rowType;
	}
    
}
