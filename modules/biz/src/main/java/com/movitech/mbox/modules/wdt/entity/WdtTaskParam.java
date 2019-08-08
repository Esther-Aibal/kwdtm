package com.movitech.mbox.modules.wdt.entity;

import com.movitech.mbox.common.persistence.DataEntity;
import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.utils.DateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gorden on 2017/8/12.
 */
public class WdtTaskParam extends DataEntity<WdtTaskParam> {
  private String ownerId,  createId, userId,  joinId,type;
  	private Set<String> types;
  	private String messageTag;
  	private String executionStatus;
    public Set<String> getTypes() {
        if(type!=null){
            Set<String> set=new HashSet<>();
            set.add(type);
            return set;
        }
        return types;
    }

    public void setTypes(Set<String> types) {
        this.types = types;
    }

    private Integer newTaskConfigDays;
    private String  search;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public WdtTaskParam(String userId, Page page) {
        this.userId = userId;
        setPage(page);
    }
    
    public WdtTaskParam(String userId,String executionStatus,Integer newTaskConfigDays, Page page) {
		this.userId = userId;
		this.newTaskConfigDays = newTaskConfigDays;
		this.executionStatus =executionStatus;
		setPage(page);
	}


	public WdtTaskParam() {
        super();
    }
    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJoinId() {
        return joinId;
    }

    public void setJoinId(String joinId) {
        this.joinId = joinId;
    }
    
	public Integer getNewTaskConfigDays() {
		return newTaskConfigDays;
	}
	
	public void setNewTaskConfigDays(Integer newTaskConfigDays) {
		this.newTaskConfigDays = newTaskConfigDays;
	}

	public String getMessageTag() {
		return messageTag;
	}

	public void setMessageTag(String messageTag) {
		this.messageTag = messageTag;
	}

	public String getExecutionStatus() {
		return executionStatus;
	}

	public void setExecutionStatus(String executionStatus) {
		this.executionStatus = executionStatus;
	}
    
}
