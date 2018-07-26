package com.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.dao.BaJieDao;
import com.entity.BaJieDailyCost;
import com.entity.BaJieKeyWord;
import com.service.BaJieService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BaJieServiceImpl implements BaJieService {

	private BaJieDao bjdao;
	
	public void setBjdao(BaJieDao bjdao) {
		this.bjdao = bjdao;
	}
	
	@Override
	public boolean saveBaJieKeyWord(BaJieKeyWord baJieKW) {
		// TODO Auto-generated method stub
		return bjdao.saveBaJieKeyWord(baJieKW);
	}

	@Override
	public boolean saveBaJieDailyCost(BaJieDailyCost baJieDC) {
		// TODO Auto-generated method stub
		return bjdao.saveBaJieDailyCost(baJieDC);
	}

	@Override
	public boolean isExistKeyWordByOrderNo(String orderNo) {
		// TODO Auto-generated method stub
		return bjdao.isExistKeyWordByOrderNo(orderNo);
	}

	@Override
	public boolean isExistDailyCostByDate(String perDay) {
		// TODO Auto-generated method stub
		return bjdao.isExistDailyCostByDate(perDay);
	}

	@Override
	public List<BaJieKeyWord> parseOrderDetail(String orderDetail,String orderNo){
		List<BaJieKeyWord> ls = new ArrayList<BaJieKeyWord>();
		Date dd = new Date();
		try {
			JSONArray arr = JSONArray.fromObject(orderDetail);
			for(int k = 0 ; k < arr.size() ; k ++){
				JSONObject obj = arr.getJSONObject(k).getJSONObject("orderItemDto");
				BaJieKeyWord keyWord = new BaJieKeyWord();
				String id = UUID.randomUUID().toString().replace("-", "").toLowerCase();
				keyWord.setCreateTime(dd);
				keyWord.setEndTime(obj.get("endTime").toString());
				keyWord.setStartTime(obj.get("staTime").toString());
				keyWord.setId(id);
				keyWord.setKeyWord(obj.getString("searchName"));
				keyWord.setOrderNo(orderNo);
				keyWord.setPrice(Integer.valueOf(obj.get("searchPrice").toString()));
				
				ls.add(keyWord);
			}						
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ls;
	}
	
	@Override
	public List<BaJieDailyCost> parseDailyCost(String daily){
		List<BaJieDailyCost> ls = new ArrayList<BaJieDailyCost>();
		Date dd = new Date();
		try {
			JSONObject obj = JSONObject.fromObject(daily);
			String dateList = obj.getJSONObject("data").get("date").toString().replace("[", "").replace("]", "");		
			String clickSumList = obj.getJSONObject("data").get("clickSum").toString().replace("[", "").replace("]", "");
			String consumptionList = obj.getJSONObject("data").get("consumption").toString().replace("[", "").replace("]", "");
			String []date = dateList.split(",");
			String []clickSum = clickSumList.split(",");
			String []consumption = consumptionList.split(",");
			for(int k = 0 ; k < date.length ; k ++){
				BaJieDailyCost cost = new BaJieDailyCost();
				String id = UUID.randomUUID().toString().replace("-", "").toLowerCase();
				cost.setId(id);
				cost.setCreateTime(dd);
				cost.setClickSum(Integer.valueOf(clickSum[k]));
				cost.setConsumption(Double.valueOf(consumption[k]));
				cost.setPerDay(date[k].replace("\"", ""));
				
				ls.add(cost);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ls;
	}

}
