package com.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.entity.MarketAdSpreadCost;
import com.opensymphony.xwork2.ActionSupport;
import com.service.MarketAdSpreadCostService;
import com.util.DataHandling;

public class MarketAdSpreadCostAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	protected HttpServletRequest request;
	private Map<String,Object> jsonData = new HashMap<String, Object>();
	private MarketAdSpreadCostService costService;
	
	public Map<String, Object> getJsonData() {
		return jsonData;
	}
	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}
	
	public void setCostService(MarketAdSpreadCostService costService) {
		this.costService = costService;
	}
	
	public String saveAdCost(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String dataSource = request.getParameter("dataSource");
		String day = request.getParameter("day");
		String plan = request.getParameter("plan");
		String cost = request.getParameter("cost");
		
		if(null == dataSource || "" == dataSource){
			jsonData = DataHandling.returnError();
			return SUCCESS;
		}
		try {
			boolean flag = costService.findAdCostBySourceAndDay(dataSource, day);
			if(flag){
				jsonData = DataHandling.returnSuccess();
				return SUCCESS;
			}
			String id = UUID.randomUUID().toString().replace("-", "").toLowerCase();
			Date dd = new Date();
			MarketAdSpreadCost adSpread = new MarketAdSpreadCost();
			adSpread.setCreateTime(dd);
			adSpread.setDataSource(dataSource);
			adSpread.setDay(day);
			adSpread.setPlan(plan);
			adSpread.setId(id);
			adSpread.setCost(Double.valueOf(cost));
			costService.saveAdCost(adSpread);
		} catch (Exception e) {
			jsonData = DataHandling.returnError();
			return SUCCESS;
		}
		jsonData = DataHandling.returnSuccess();
		return SUCCESS;
	}
	
}
