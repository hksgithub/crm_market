package com.entity;

import java.util.Date;

public class ShopStatus {
	
	private String id;
	
	private String word;
	
	private int status;
	
	private Date  create_time;
	
	private int runing;
	
    private Date end_time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public int getRuning() {
		return runing;
	}

	public void setRuning(int runing) {
		this.runing = runing;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}


}
