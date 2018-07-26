package com.entity;

import java.util.Date;

/**
 * @author hekangshang
 *
 */
public class PublicNumber implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String uid;
	private String day;
	private Integer care_num;
	private Integer public_num;
	private Integer read_num;
	private Integer collection_num;
	private Integer fans_num;
	private Integer forwarding_num;
	private Date create_time;
 
	/** default constructor */
	public PublicNumber() {
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

	public Integer getCollection_num() {
		return collection_num;
	}

	public void setCollection_num(Integer collection_num) {
		this.collection_num = collection_num;
	}

	public Integer getFans_num() {
		return fans_num;
	}

	public void setFans_num(Integer fans_num) {
		this.fans_num = fans_num;
	}
 
	public Integer getForwarding_num() {
		return forwarding_num;
	}

	public void setForwarding_num(Integer forwarding_num) {
		this.forwarding_num = forwarding_num;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
  
	

}
