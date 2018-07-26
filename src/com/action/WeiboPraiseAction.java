package com.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.entity.WeiboPraise;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.WeiboPraiseService;
import com.util.DataHandling;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WeiboPraiseAction extends ActionSupport implements ModelDriven<WeiboPraise>{
 
	/**
	 * 微博评论点赞
	 */
	private static final long serialVersionUID = 1656515904670560641L;
 
	protected HttpServletRequest request = ServletActionContext.getRequest();
	@Autowired
    private WeiboPraiseService weiboPraiseService;
    private Map<String,Object> jsonData = new HashMap<String,Object>();   
    public Map<String, Object> getJsonData() {
		return jsonData;
	}

	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}
    @Override
	// 模型驱动
	public WeiboPraise getModel() {
		// TODO Auto-generated method stub
		return null;
	}
 	 
	/**
	 * 点赞数据处理
	 * @return
	 * @throws java.text.ParseException
	 */
	public String isWeiboPraise(){
		try{
			String jsonstr = request.getParameter("praise");
			if(jsonstr==null || jsonstr==""){
				jsonData = DataHandling.returnError();
				return SUCCESS; 
			}
			//获取数据
			String s = DataHandling.getFromBASE64(jsonstr);
			JSONObject jsonobj = JSONObject.fromObject(s);
			JSONArray array = jsonobj.getJSONObject("data").getJSONArray("list_data");
			JSONObject userobj = jsonobj.getJSONObject("data").getJSONObject("userInfo");
			String userid = userobj.getString("uid");
			jsonData = new HashMap<String,Object>();  
			if(array!=null && array.size()>0 && userid!=null && userid!=""){
				 for (int i = 0; i < array.size(); i++) { 
		        	String day = array.getJSONObject(i).getString("day");
		        	int comment_num = array.getJSONObject(i).getInt("comment");
		        	int praise_num = array.getJSONObject(i).getInt("like");	 
		          	int read_num = array.getJSONObject(i).getInt("read");
		        	int forwarding_num = array.getJSONObject(i).getInt("reposted");
		        	WeiboPraise s_weiboPraise = (WeiboPraise) weiboPraiseService.findWith(day);        	
		        	if(s_weiboPraise==null){	
		        		WeiboPraise weiboPraise = new WeiboPraise();
			        	weiboPraise.setUid(userid);
			        	weiboPraise.setDay(day);
		        		weiboPraise.setComment_num(comment_num);		
		        		weiboPraise.setPraise_num(praise_num);	
		        		weiboPraise.setForwarding_num(forwarding_num);
		        		weiboPraise.setRead_num(read_num);
						weiboPraise.setCreate_time(new Date());
						weiboPraiseService.save(weiboPraise);
		        	}else{
		        		s_weiboPraise.setComment_num(comment_num);		
		        		s_weiboPraise.setPraise_num(praise_num);	
		        		s_weiboPraise.setForwarding_num(forwarding_num);
		        		s_weiboPraise.setRead_num(read_num);
		        		s_weiboPraise.setCreate_time(new Date());
		        		weiboPraiseService.update(s_weiboPraise);
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


