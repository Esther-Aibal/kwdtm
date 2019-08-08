/**
 * 
 */
package com.movitech.mbox.modules.wdt.entity;

import java.text.DecimalFormat;

import org.hibernate.validator.constraints.Length;

import com.movitech.mbox.common.persistence.DataEntity;

/**
 * 附件Entity
 * @author Aibal.He
 * @version 2017-08-09
 */
public class WdtTFileInfo extends DataEntity<WdtTFileInfo> {
    
    private static final long serialVersionUID = 1L;
    private String fileName;        // 文件名称
    private String fileOldName;        // 原始文件名称
    private String filePath;        // 文件存放路径
    private String fileSize;        // 文件大小
    private String fileType;        // 文件类型
    private String fileDesc;        // 文件描述
    private String fileCatagoryId;        // 附件分类(枚举维护：0：任务 1：节点  2：汇报 3：评论)
    private String bizTableName;        // 业务表名
    private String bizTablePkName;        // 业务表主键字段名
    private String bizTablePkValue;        // 业务表主键字段值
    private String fieldName;        // 文件所对应业务表中的字段名
    private String fieldToken;        // 文件所对应业务表中的字段值
    
    private String fileSizeM; //文件大小M 
    
    public WdtTFileInfo() {
        super();
    }

    public WdtTFileInfo(String id){
        super(id);
    }

    @Length(min=0, max=200, message="文件名称长度必须介于 0 和 200 之间")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    @Length(min=0, max=200, message="原始文件名称长度必须介于 0 和 200 之间")
    public String getFileOldName() {
        return fileOldName;
    }

    public void setFileOldName(String fileOldName) {
        this.fileOldName = fileOldName;
    }
    
    @Length(min=0, max=200, message="文件存放路径长度必须介于 0 和 200 之间")
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    @Length(min=0, max=11, message="文件大小长度必须介于 0 和 11 之间")
    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
        if(fileSize != null){
        	double fileSizeAsDouble = Double.parseDouble(fileSize);
			DecimalFormat df = new DecimalFormat("0.00");
			this.fileSizeM = df.format(fileSizeAsDouble/1024/1024);
        }
    }
    
    @Length(min=0, max=200, message="文件类型长度必须介于 0 和 200 之间")
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    
    @Length(min=0, max=200, message="文件描述长度必须介于 0 和 200 之间")
    public String getFileDesc() {
        return fileDesc;
    }

    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc;
    }
    
    @Length(min=0, max=64, message="附件分类(枚举维护：0：任务 1：节点  2：汇报 3：评论)长度必须介于 0 和 64 之间")
    public String getFileCatagoryId() {
        return fileCatagoryId;
    }

    public void setFileCatagoryId(String fileCatagoryId) {
        this.fileCatagoryId = fileCatagoryId;
    }
    
    @Length(min=0, max=200, message="业务表名长度必须介于 0 和 200 之间")
    public String getBizTableName() {
        return bizTableName;
    }

    public void setBizTableName(String bizTableName) {
        this.bizTableName = bizTableName;
    }
    
    @Length(min=0, max=200, message="业务表主键字段名长度必须介于 0 和 200 之间")
    public String getBizTablePkName() {
        return bizTablePkName;
    }

    public void setBizTablePkName(String bizTablePkName) {
        this.bizTablePkName = bizTablePkName;
    }
    
    @Length(min=0, max=200, message="业务表主键字段值长度必须介于 0 和 200 之间")
    public String getBizTablePkValue() {
        return bizTablePkValue;
    }

    public void setBizTablePkValue(String bizTablePkValue) {
        this.bizTablePkValue = bizTablePkValue;
    }
    
    @Length(min=0, max=200, message="文件所对应业务表中的字段名长度必须介于 0 和 200 之间")
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    
    @Length(min=0, max=200, message="文件所对应业务表中的字段值长度必须介于 0 和 200 之间")
    public String getFieldToken() {
        return fieldToken;
    }

    public void setFieldToken(String fieldToken) {
        this.fieldToken = fieldToken;
    }

	public String getFileSizeM() {
		return fileSizeM;
	}

	public void setFileSizeM(String fileSizeM) {
		this.fileSizeM = fileSizeM;
	}
    
}