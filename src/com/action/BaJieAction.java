package com.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.util.StringUtils;

import com.entity.BaJieDailyCost;
import com.entity.BaJieKeyWord;
import com.entity.HeadlineToday;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.BaJieService;
import com.util.DataHandling;

public class BaJieAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	protected HttpServletRequest request;
	
	private Map<String,Object> jsonData = new HashMap<String, Object>();
	
	private BaJieService baJieService;

	public Map<String, Object> getJsonData() {
		return jsonData;
	}

	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}

	public void setbaJieService(BaJieService baJieService) {
		this.baJieService = baJieService;
	}
	
	public String saveBaJieKeyWord(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String orderDetail = request.getParameter("orderDetail");
		String orderNo = request.getParameter("orderNo");
		
		if(StringUtils.isEmpty(orderDetail)){
			jsonData = DataHandling.returnError();
			return SUCCESS;
		}
		
		try {
			List<BaJieKeyWord> ls = baJieService.parseOrderDetail(orderDetail, orderNo);
			for(int k = 0 ; k < ls.size() ; k ++){
				boolean flag = baJieService.isExistKeyWordByOrderNo(orderNo);
				if(!flag){
					baJieService.saveBaJieKeyWord(ls.get(k));
				}				
			}
			
		} catch (Exception e) {
			//e.printStackTrace();
			jsonData = DataHandling.returnError();
			return SUCCESS;
		}
		jsonData = DataHandling.returnSuccess();
		return SUCCESS;
	}
	
	public String saveBaJieDailyCost(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String dailyCost = request.getParameter("dailyCost");		
		
		if(StringUtils.isEmpty(dailyCost)){
			jsonData = DataHandling.returnError();
			return SUCCESS;
		}
		
		try {
			List<BaJieDailyCost> ls = baJieService.parseDailyCost(dailyCost);
			for(int k = 0 ; k < ls.size() ; k ++){
				BaJieDailyCost cost = ls.get(k);
				boolean flag = baJieService.isExistDailyCostByDate(cost.getPerDay());
				if(!flag){
					baJieService.saveBaJieDailyCost(cost);
				}
			}
			
		} catch (Exception e) {
			//e.printStackTrace();
			jsonData = DataHandling.returnError();
			return SUCCESS;
		}
		jsonData = DataHandling.returnSuccess();
		return SUCCESS;
	
	}
}
