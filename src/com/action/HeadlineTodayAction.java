package com.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.entity.HeadlineToday;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.HeadlineTodayService;
import com.util.DataHandling;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HeadlineTodayAction extends ActionSupport implements ModelDriven<HeadlineToday>{
 
	/**
	 * 今日头条
	 */
	private static final long serialVersionUID = 1656515904670560641L;
	// 模型驱动
    //private WeiboFans weiboFans = new WeiboFans();
    protected HttpServletRequest request;//请求对象
    @Autowired
    private HeadlineTodayService headlineTodayService;
    private Map<String,Object> jsonData = new HashMap<String, Object>();  
    public Map<String, Object> getJsonData() {
		return jsonData;
	}

	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}
    @Override
	// 模型驱动
	public HeadlineToday getModel() {
		// TODO Auto-generated method stub
		return null;
	}
 
	/**
	 * 数据处理
	 * 
	 * @return
	 * @throws java.text.ParseException
	 */
	public String isHeadlineToday() {
		try{	
			HttpServletRequest request = ServletActionContext.getRequest();
			String jsonstr = request.getParameter("article");	 
			if (jsonstr == null || jsonstr == "") {
				jsonData = DataHandling.returnError();
				return SUCCESS;
			}
			String s = DataHandling.getFromBASE64(jsonstr);
			String str = JSONObject.fromObject(s).getString("data");
			JSONObject j_obj = JSONObject.fromObject(str);
			String userid = request.getParameter("uid"); 
			if (j_obj != null && j_obj.size() > 0 && userid != null && userid != "") {
				String day = j_obj.getString("end_date");
				int read_num = j_obj.getInt("go_detail_count");
				int public_num = j_obj.getInt("publish_num");
				HeadlineToday s_headlineToday = (HeadlineToday) headlineTodayService.findWith(day);		
				if (s_headlineToday==null) {	
					HeadlineToday headlineToday = new HeadlineToday();	 
					headlineToday.setCreate_time(new Date());
					headlineToday.setDay(day);
					headlineToday.setPublic_num(public_num);
					headlineToday.setRead_num(read_num);
					headlineToday.setUid(userid);
					headlineTodayService.save(headlineToday);
				}else{
					s_headlineToday.setPublic_num(public_num);
					s_headlineToday.setRead_num(read_num);
					s_headlineToday.setCreate_time(new Date());
					headlineTodayService.update(s_headlineToday);
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
	
	/**
	 * 头条转发量
	 * @return
	 */
	public String headlineTodayForwarding(){
		try{
			HttpServletRequest request = ServletActionContext.getRequest();
			String jsonstr = request.getParameter("forward");
			if (jsonstr == null || jsonstr == "") {
				jsonData = DataHandling.returnError();
				return SUCCESS;
			}
			String s = DataHandling.getFromBASE64(jsonstr);
			String str = JSONObject.fromObject(s).getString("data");
			JSONArray array = JSONObject.fromObject(str).getJSONArray("data_list");
			String userid = request.getParameter("uid");
			if (array != null && array.size() > 0 && userid != null && userid != "") {
				for (int i = 0; i < array.size(); i++) {
					String day = array.getJSONObject(i).getString("date");
					int comment_num = array.getJSONObject(i).getInt("comment_count");
					int collection_num = array.getJSONObject(i).getInt("repin_count");
					int forwarding_num = array.getJSONObject(i).getInt("share_count");
					HeadlineToday s_headlineToday = (HeadlineToday) headlineTodayService.findWith(day);			
					if (s_headlineToday==null) {
						HeadlineToday headlineToday = new HeadlineToday();
						headlineToday.setComment_num(comment_num);
						headlineToday.setCollection_num(collection_num);
						headlineToday.setForwarding_num(forwarding_num);
						headlineToday.setCreate_time(new Date());
						headlineToday.setDay(day);
						headlineToday.setUid(userid);
						headlineTodayService.save(headlineToday);
					}else{
						s_headlineToday.setComment_num(comment_num);
						s_headlineToday.setCollection_num(collection_num);
						s_headlineToday.setForwarding_num(forwarding_num);
						s_headlineToday.setCreate_time(new Date());
						headlineTodayService.update(s_headlineToday);
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
	
	/**
	 * 头条新增粉丝
	 * @return
	 */
	public String headlineTodayFans(){
		try{
			HttpServletRequest request = ServletActionContext.getRequest();
			String jsonstr = request.getParameter("fans");
			if (jsonstr == null || jsonstr == "") {
				jsonData = DataHandling.returnError();
				return SUCCESS;
			}
			String s = DataHandling.getFromBASE64(jsonstr);
			String str = JSONObject.fromObject(s).getString("data");
			JSONArray array = JSONObject.fromObject(str).getJSONArray("data_list");
			String userid = request.getParameter("uid");
			if (array != null && array.size() > 0 && userid != null && userid != "") {
				for (int i = 0; i < array.size(); i++) {
					String day = array.getJSONObject(i).getString("date");
					int care_num = array.getJSONObject(i).getInt("new_like_count");
					int cancel_num = array.getJSONObject(i).getInt("new_dislike_count");
					int fans_num = array.getJSONObject(i).getInt("total_subscriber_count");
					HeadlineToday s_headlineToday = (HeadlineToday) headlineTodayService.findWith(day);			
					if (s_headlineToday==null) {	
						HeadlineToday headlineToday = new HeadlineToday();
						headlineToday.setCreate_time(new Date());
						headlineToday.setDay(day);
						headlineToday.setCare_num(care_num);
						headlineToday.setCancel_num(cancel_num);
						headlineToday.setFans_num(fans_num);
						headlineToday.setUid(userid);
						headlineTodayService.save(headlineToday);
					}else{	
						s_headlineToday.setCare_num(care_num);
						s_headlineToday.setCancel_num(cancel_num);
						s_headlineToday.setFans_num(fans_num);
						s_headlineToday.setCreate_time(new Date());
						headlineTodayService.update(s_headlineToday);
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


