package com.entity;

import java.util.Date;

public class BusinessStaff implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	private String id;
	
	private String shop_name;
	
    private String store_category;
    
    private String month_date;
    
	private String payment_amount;
	
	private String customer_price;
	
	private int payments_number;
    
	private Date create_time;
	
	private String user_account;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public String getStore_category() {
		return store_category;
	}

	public void setStore_category(String store_category) {
		this.store_category = store_category;
	}


	public String getMonth_date() {
		return month_date;
	}

	public void setMonth_date(String month_date) {
		this.month_date = month_date;
	}

	public String getPayment_amount() {
		return payment_amount;
	}

	public void setPayment_amount(String payment_amount) {
		this.payment_amount = payment_amount;
	}

	public String getCustomer_price() {
		return customer_price;
	}

	public void setCustomer_price(String customer_price) {
		this.customer_price = customer_price;
	}

	public int getPayments_number() {
		return payments_number;
	}

	public void setPayments_number(int payments_number) {
		this.payments_number = payments_number;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getUser_account() {
		return user_account;
	}

	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}
    
	
}
