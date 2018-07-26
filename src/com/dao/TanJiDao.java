package com.dao;

import java.util.List;

import com.entity.TanJi;

public interface TanJiDao {

	public boolean saveTanJi(TanJi tanji);
	
	public boolean removeTanji(String companyName);
	
	public boolean getTanJi(String companyName);
}
