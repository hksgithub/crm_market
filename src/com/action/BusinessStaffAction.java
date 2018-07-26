package com.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.entity.BusinessStaff;
import com.entity.TmallLicense;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.BusinessStaffService;
import com.util.DataHandling;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BusinessStaffAction extends ActionSupport implements ModelDriven<BusinessStaff>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8646518511962203905L;
	protected HttpServletRequest request = ServletActionContext.getRequest();
    private BusinessStaffService businessStaffService;
    private Map<String,Object> jsonData = new HashMap<String, Object>(); 
    public Map<String, Object> getJsonData() {
		return jsonData;
	}
	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}
	@Override
	public BusinessStaff getModel() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setBusinessStaffService(BusinessStaffService businessStaffService) {
		this.businessStaffService = businessStaffService;
	}
    
	public String isShopTurnover() {
		Date now =new Date();
		HttpServletRequest request = ServletActionContext.getRequest();
		String jsonstr = request.getParameter("turnover");	
		if (jsonstr == null || jsonstr == "") {
			jsonData = DataHandling.returnError();
			return SUCCESS;
		}
		String s = DataHandling.getFromBASE64(jsonstr);
		String str = JSONObject.fromObject(s).getString("data");
		JSONArray jsonArray = JSONArray.fromObject(str); 
	    JSONObject j_obj = jsonArray.getJSONObject(0); 
		String shopName=request.getParameter("shopName");
		String storeCategory=request.getParameter("storeCategory");
		String userAccount=request.getParameter("userAccount");
		if (j_obj != null && j_obj.size() > 0 && shopName != null && shopName != "") {
		 String month=j_obj.getString("month_date");
		 String payAmt=j_obj.getString("payAmt");
		 String payPct=j_obj.getString("payPct");
		 int  payItmCnt=j_obj.getInt("payItmCnt");
		 BusinessStaff s_businessStaff =(BusinessStaff)businessStaffService.findWith(shopName,month);
		 if(s_businessStaff==null) {
			 BusinessStaff  businessStaff =new BusinessStaff();
			 businessStaff.setCustomer_price(payPct);
			 businessStaff.setPayment_amount(payAmt);
			 businessStaff.setMonth_date(month);
			 businessStaff.setPayments_number(payItmCnt);
			 businessStaff.setShop_name(shopName);
			 businessStaff.setStore_category(storeCategory);
			 businessStaff.setCreate_time(now);
			 businessStaff.setUser_account(userAccount);
			 businessStaffService.save(businessStaff);
		 }else {
		    s_businessStaff.setCustomer_price(payPct);
		    s_businessStaff.setPayment_amount(payAmt);
		    s_businessStaff.setPayments_number(payItmCnt);
		    s_businessStaff.setUser_account(userAccount);
		    businessStaffService.updateShop(s_businessStaff);
		 }
			jsonData = DataHandling.returnSuccess();
			return SUCCESS;
		}
		jsonData = DataHandling.returnError();
		return SUCCESS;
	}
}
