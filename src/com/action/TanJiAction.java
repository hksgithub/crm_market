package com.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.entity.TanJi;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.TanJiService;
import com.util.DataHandling;

public class TanJiAction extends ActionSupport implements ModelDriven<TanJi>{

	private static final long serialVersionUID = 1L;
	
	protected HttpServletRequest request;
	
	private TanJiService tanJiService;
	
	public void setTanJiService(TanJiService tanJiService) {
		this.tanJiService = tanJiService;
	}

	private Map<String,Object> jsonData = new HashMap<String, Object>();
	
	@Override
	public TanJi getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String,Object> getJsonData() {
		return jsonData;
	}

	public void setJsonData(Map<String,Object> jsonData) {
		this.jsonData = jsonData;
	}

	/**
	 * 探迹数据入库
	 * @return
	 */
	public String getTanJiData(){		
		HttpServletRequest request = ServletActionContext.getRequest();
		String jsonResult = request.getParameter("JsonResult");
		String shopName = request.getParameter("ShopName");
		String companyName = request.getParameter("CompanyName");
		
		if(null == jsonResult || "" == jsonResult){
			jsonData = DataHandling.returnError();
			return SUCCESS;
		}
		try {
			boolean flag = tanJiService.getTanJi(companyName);
			//抓过不入库
			if(flag){	
				jsonData = DataHandling.returnSuccess();
				return SUCCESS;
				//tanJiService.removeTanji(companyName);
			}
			List<TanJi> tans = tanJiService.parseTanJiJson(jsonResult);
			for(int k = 0 ; k < tans.size() ; k ++){
				TanJi tan = tans.get(k);
				String id = UUID.randomUUID().toString().replace("-", "").toLowerCase();
				tan.setId(id);				
				tan.setShopName(shopName);
				tan.setCompanyName(companyName);
				tanJiService.saveTanJi(tan);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			jsonData = DataHandling.returnError();
			return SUCCESS;
		}
		jsonData = DataHandling.returnSuccess();
		return SUCCESS;
	}
	
	/**
	 * 探迹数据删除
	 * @return
	 */
	public String removeTanJiData(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String companyName = request.getParameter("companyName");
		if(null == companyName || "" == companyName){
			jsonData = DataHandling.returnError();
			return SUCCESS;
		}
		try {
			tanJiService.removeTanji(companyName);
		} catch (Exception e) {
			jsonData = DataHandling.returnError();
			return SUCCESS;
		}
		jsonData = DataHandling.returnSuccess();
		return SUCCESS;
	}
	
	/**
	 * 探迹数据查询
	 * @return
	 */
	public String findTanJiData(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String companyName = request.getParameter("companyName");
		if(null == companyName || "" == companyName){
			jsonData = DataHandling.returnError();
			return SUCCESS;
		}
		try {
			boolean flag = tanJiService.getTanJi(companyName);
			if(flag){
				jsonData = DataHandling.returnAlreadyExist();
				return SUCCESS;
			}
		} catch (Exception e) {
			jsonData = DataHandling.returnError();
			return SUCCESS;
		}
		jsonData = DataHandling.returnNotExist();
		return SUCCESS;
	
	}
}
