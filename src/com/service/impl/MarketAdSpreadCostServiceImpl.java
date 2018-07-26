package com.service.impl;

import com.dao.MarketAdSpreadCostDao;
import com.entity.MarketAdSpreadCost;
import com.service.MarketAdSpreadCostService;

public class MarketAdSpreadCostServiceImpl implements MarketAdSpreadCostService {

	private MarketAdSpreadCostDao costDao;
	
	public void setCostDao(MarketAdSpreadCostDao costDao) {
		this.costDao = costDao;
	}

	@Override
	public boolean saveAdCost(MarketAdSpreadCost cost) {
		return costDao.saveAdCost(cost);
	}

	@Override
	public boolean findAdCostBySourceAndDay(String source, String day) {
		return costDao.findAdCostBySourceAndDay(source, day);
	}

}
