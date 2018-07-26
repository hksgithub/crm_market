package com.entity;

import java.util.Date;

public class BaJieDailyCost implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private Integer clickSum;
	private double consumption;
	private String perDay;
	private Date createTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getClickSum() {
		return clickSum;
	}
	public void setClickSum(Integer clickSum) {
		this.clickSum = clickSum;
	}
	public double getConsumption() {
		return consumption;
	}
	public void setConsumption(double consumption) {
		this.consumption = consumption;
	}
	public String getPerDay() {
		return perDay;
	}
	public void setPerDay(String perDay) {
		this.perDay = perDay;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
