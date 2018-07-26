package com.dao;

import com.entity.BaJieDailyCost;
import com.entity.BaJieKeyWord;

public interface BaJieDao {

	public boolean saveBaJieKeyWord(BaJieKeyWord baJieKW);
	
	public boolean saveBaJieDailyCost(BaJieDailyCost baJieDC);
	
	public boolean isExistKeyWordByOrderNo(String orderNo);
	
	public boolean isExistDailyCostByDate(String perDay);
}
