package com.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.entity.WeiboFans;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.WeiboFansService;
import com.util.DataHandling;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WeiboFansAction extends ActionSupport implements ModelDriven<WeiboFans> {

	/**
	 * 微博粉丝
	 */
	private static final long serialVersionUID = 1656515904670560641L;

	protected HttpServletRequest request = ServletActionContext.getRequest();
	@Autowired
	private WeiboFansService weiboFansService;
	private Map<String, Object> jsonData = new HashMap<String, Object>();

	public Map<String, Object> getJsonData() {
		return jsonData;
	}

	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}

	@Override
	// 模型驱动
	public WeiboFans getModel() {
		// TODO Auto-generated method stub
		return null;
	}
 
	/**
	 * 粉丝数据处理
	 * 
	 * @return
	 * @throws java.text.ParseException
	 */
	public String isWeiboFans() {
		try{
			String jsonstr = request.getParameter("fans");
			if (jsonstr == null || jsonstr == "") {
				jsonData = DataHandling.returnError();
				return SUCCESS;
			}
			// 获取数据
			String s = DataHandling.getFromBASE64(jsonstr);
			JSONObject jsonobj = JSONObject.fromObject(s);
			JSONArray array = jsonobj.getJSONObject("data").getJSONArray("list_data");
			JSONObject userobj = jsonobj.getJSONObject("data").getJSONObject("userInfo");
			String userid = userobj.getString("uid");			
			jsonData = new HashMap<String, Object>();
			if (array != null && array.size() > 0 && userid != null && userid != "") {
				for (int i = 0; i < array.size(); i++) {
					String day = array.getJSONObject(i).getString("day");
					int care_num = array.getJSONObject(i).getInt("followers_incr");
					int cancle_num = array.getJSONObject(i).getInt("followers_decr");
					int total_num = array.getJSONObject(i).getInt("followers");
					WeiboFans s_weiboFans = (WeiboFans) weiboFansService.findWith(day);	
					if (s_weiboFans==null) {	
						WeiboFans weiboFans = new WeiboFans();
						weiboFans.setUid(userid);
						weiboFans.setDay(day);
						weiboFans.setCare_num(care_num);
						weiboFans.setCancle_num(cancle_num);
						weiboFans.setTotal_num(total_num);
						weiboFans.setCreate_time(new Date());
						weiboFansService.save(weiboFans);
					}else{
						s_weiboFans.setCare_num(care_num);
						s_weiboFans.setCancle_num(cancle_num);
						s_weiboFans.setTotal_num(total_num);
						s_weiboFans.setCreate_time(new Date());
						weiboFansService.update(s_weiboFans);
					}
				}
				jsonData = DataHandling.returnSuccess();
				return SUCCESS;
			}
			jsonData = DataHandling.returnError();
			return SUCCESS;     
		}catch(Exception e){
		  jsonData = DataHandling.returnError();
		  return SUCCESS;
		}
		
	}

}


