package com.entity;

import java.util.Date;

/**
 * @author hekangshang
 *
 */
public class Sohu implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String uid;
	private String day;
	private Integer public_num;
	private Integer read_num;
	private Date create_time;
 
	/** default constructor */
	public Sohu() {
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

	
	public Integer getPublic_num() {
		return public_num;
	}

	public void setPublic_num(Integer public_num) {
		this.public_num = public_num;
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
