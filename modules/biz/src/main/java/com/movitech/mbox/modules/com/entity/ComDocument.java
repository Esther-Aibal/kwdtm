/**
 * 
 */
package com.movitech.mbox.modules.com.entity;

import org.hibernate.validator.constraints.Length;

import com.movitech.mbox.common.persistence.DataEntity;

/**
 * 文章Entity
 * @author Aibal.He
 * @version 2017-06-23
 */
public class ComDocument extends DataEntity<ComDocument> {
    
    private static final long serialVersionUID = 1L;
    private String title;        // 标题
    private String content;        // 内容
    
    public ComDocument() {
        super();
    }

    public ComDocument(String id){
        super(id);
    }

    @Length(min=1, max=200, message="标题长度必须介于 1 和 200 之间")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
}