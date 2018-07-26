package com.entity;

import java.util.Date;

/**
 * @author hekangshang
 *
 */
public class WeiboFans implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String uid;
	private String day;
	private Integer care_num;
	private Integer cancle_num;
	private Integer total_num;
	private Date create_time;
 
	/** default constructor */
	public WeiboFans() {
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

	
	public Integer getCare_num() {
		return care_num;
	}

	public void setCare_num(Integer care_num) {
		this.care_num = care_num;
	}

	public Integer getCancle_num() {
		return cancle_num;
	}

	public void setCancle_num(Integer cancle_num) {
		this.cancle_num = cancle_num;
	}

	public Integer getTotal_num() {
		return total_num;
	}

	public void setTotal_num(Integer total_num) {
		this.total_num = total_num;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
  
	

}
