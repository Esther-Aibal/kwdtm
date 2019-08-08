/**
 * 
 */
package com.movitech.mbox.modules.wdt.entity.ext;

import com.movitech.mbox.common.persistence.DataEntity;

/**
 * 任务Entity
 * @author Aibal.He
 * @version 2017-08-07
 */
public class OfficeUser extends DataEntity<OfficeUser> {
    
    private static final long serialVersionUID = 1L;
    
    private String userName;
    private String officeId;
    private String useId;
    private String type;
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getUseId() {
		return useId;
	}

	public void setUseId(String useId) {
		this.useId = useId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    
    

}