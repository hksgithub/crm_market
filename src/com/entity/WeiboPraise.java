package com.entity;

import java.util.Date;

/**
 * @author hekangshang
 *
 */
public class WeiboPraise implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String uid;
	private String day;
	private Integer comment_num;
	private Integer praise_num;
	private Integer forwarding_num;
    private Integer read_num;
	private Date create_time;
 
	/** default constructor */
	public WeiboPraise() {
	}

	public String getId() {
		return id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	
	public Integer getComment_num() {
		return comment_num;
	}

	public void setComment_num(Integer comment_num) {
		this.comment_num = comment_num;
	}

	public Integer getPraise_num() {
		return praise_num;
	}

	public void setPraise_num(Integer praise_num) {
		this.praise_num = praise_num;
	}
	

	public Integer getForwarding_num() {
		return forwarding_num;
	}

	public void setForwarding_num(Integer forwarding_num) {
		this.forwarding_num = forwarding_num;
	}	
	 
    public Integer getRead_num() {
        return read_num;
    }

    public void setRead_num(Integer read_num) {
        this.read_num = read_num;
    }

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
  
	

}
