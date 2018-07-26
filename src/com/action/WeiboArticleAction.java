package com.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.entity.WeiboArticle;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.WeiboArticleService;
import com.util.DataHandling;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WeiboArticleAction extends ActionSupport implements ModelDriven<WeiboArticle>{
 
	/**
	 * 微博文章
	 */
	private static final long serialVersionUID = 3466755801757538322L;
 
	protected HttpServletRequest request = ServletActionContext.getRequest();
	@Autowired
    private WeiboArticleService weiboArticleService;
    private Map<String,Object> jsonData = new HashMap<String,Object>();  		

	public Map<String, Object> getJsonData() {
		return jsonData;
	}

	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}

    @Override
	// 模型驱动
	public WeiboArticle getModel() {
		// TODO Auto-generated method stub
		return null; 
	}
 	 
	/**
	 * 文章数据
	 * @return
	 * @throws java.text.ParseException
	 */
	public String isWeiboArticle(){	
		try{
			String jsonstr = request.getParameter("article"); 
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
			if(array!=null && array.size()>0 && userid!=null && userid!=""){
			  for (int i = 0; i < array.size(); i++) { 
		        	String day = array.getJSONObject(i).getString("day");		
		        	int public_num = array.getJSONObject(i).getInt("weibo");        	 	
		        	WeiboArticle s_weiboArticle = (WeiboArticle) weiboArticleService.findWith(day);        	
		        	if(s_weiboArticle==null){  
		        		WeiboArticle weiboArticle = new WeiboArticle();	       
		        		weiboArticle.setUid(userid);
			        	weiboArticle.setDay(day);
						weiboArticle.setPublic_num(public_num);  
						weiboArticle.setCreate_time(new Date());	
						weiboArticleService.save(weiboArticle);
		        	}else{		        		
		        		s_weiboArticle.setPublic_num(public_num);
		        		s_weiboArticle.setCreate_time(new Date());	
		        		weiboArticleService.update(s_weiboArticle);
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
