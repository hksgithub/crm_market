package com.service;

import com.entity.MarketAdSpreadCost;

public interface MarketAdSpreadCostService {

	public boolean saveAdCost(MarketAdSpreadCost cost);
	
	public boolean findAdCostBySourceAndDay(String source , String day);

}
