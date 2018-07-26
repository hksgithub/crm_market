package com.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;


import com.entity.TmallLicense;
import com.entity.WeiboArticle;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.SohuService;
import com.service.TmallLicenseService;

import net.sf.json.JSONObject;

public class TmallLicenseAction extends  ActionSupport implements ModelDriven<TmallLicense> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3415574721472421340L;
    
	protected HttpServletRequest request = ServletActionContext.getRequest();
    
    private TmallLicenseService tmallLicenseService;
    private Map<String,Object> jsonData = new HashMap<String, Object>(); 
    public Map<String, Object> getJsonData() {
		return jsonData;
	}

	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}
	
	@Override
	public TmallLicense getModel() {
		// TODO Auto-generated method stub
		return null;
	}
    
	public void setTmallLicenseService(TmallLicenseService tmallLicenseService) {
		this.tmallLicenseService = tmallLicenseService;
	}

	/**
	 * 天猫营业执照处理
	 * @return
	 * @throws java.text.ParseException
	 */
	public String isTmallLicense(){
		String id="123456789";
		String tmall_shop_name="天猫";
		String business_license_address="123456789";
		String url="wwwwwwwwwwwww";
		Date now =new Date(); 
		List<TmallLicense> tlList = tmallLicenseService.findWith(tmall_shop_name);
		TmallLicense tmallLicense =new TmallLicense();
		tmallLicense.setBusiness_license_address(business_license_address);
		tmallLicense.setCreate_time(now);
		tmallLicense.setShop_link(url);
		tmallLicense.setTmall_shop_name(tmall_shop_name);
		if(tlList.size()<=0) {
			tmallLicenseService.save(tmallLicense);
		}else {
			tmallLicenseService.updateShop(tmallLicense);
		}
		jsonData.put("code","0");
		jsonData.put("msg","数据为空");
	    return SUCCESS; 	 
	}
	
}
