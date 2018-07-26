package com.entity;

import java.util.Date;

/**
 * @author hekangshang
 *
 */
public class WeiboArticle implements java.io.Serializable {
    
    private static final long serialVersionUID = 1L;
    private String id;
    private String day;
    private String uid;
    private Integer public_num;
    private Date create_time;
 
    /** default constructor */
    public WeiboArticle() {
    }

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getPublic_num() {
        return public_num;
    }

    public void setPublic_num(Integer public_num) {
        this.public_num = public_num;
    }
 
    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
  
}