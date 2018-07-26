package com.service;

import java.util.List;

import com.entity.TanJi;

public interface TanJiService {

	public boolean saveTanJi(TanJi tanji);
	
	public boolean removeTanji(String companyName);
	
	public boolean getTanJi(String companyName);
	
	public List<TanJi> parseTanJiJson(String json);
}
