package com.entity;

import java.util.Date;

public class TmallLicense implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String tmall_shop_name;

	private String shop_link;

	private String business_license_address;

	private Date create_time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTmall_shop_name() {
		return tmall_shop_name;
	}

	public void setTmall_shop_name(String tmall_shop_name) {
		this.tmall_shop_name = tmall_shop_name;
	}

	public String getShop_link() {
		return shop_link;
	}

	public void setShop_link(String shop_link) {
		this.shop_link = shop_link;
	}

	public String getBusiness_license_address() {
		return business_license_address;
	}

	public void setBusiness_license_address(String business_license_address) {
		this.business_license_address = business_license_address;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

}
