package com.dao;

import com.entity.MarketAdSpreadCost;

public interface MarketAdSpreadCostDao {

	public boolean saveAdCost(MarketAdSpreadCost cost);
	
	public boolean findAdCostBySourceAndDay(String source , String day);
}
