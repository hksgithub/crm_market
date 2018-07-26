package com.service;

import java.util.List;

import com.entity.BaJieDailyCost;
import com.entity.BaJieKeyWord;

public interface BaJieService {

	public boolean saveBaJieKeyWord(BaJieKeyWord baJieKW);
	
	public boolean saveBaJieDailyCost(BaJieDailyCost baJieDC);
	
	public boolean isExistKeyWordByOrderNo(String orderNo);
	
	public boolean isExistDailyCostByDate(String perDay);
	
	public List<BaJieKeyWord> parseOrderDetail(String orderDetail,String orderNo);
	
	public List<BaJieDailyCost> parseDailyCost(String daily);
}
