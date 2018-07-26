package com.action;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.dao.impl.QianniuDaoImpl;
import com.entity.Qianniu;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.QianniuService;
import com.util.DataHandling;
import com.util.DateTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class QianniuAction extends ActionSupport implements ModelDriven<Qianniu>{
 
	/**
	 * 千牛号
	 */
	private static final long serialVersionUID = 1656515904670560641L;
 
    protected HttpServletRequest request;//请求对象
    @Autowired
    private QianniuService qianniuService;
    private Map<String,Object> jsonData = new HashMap<String, Object>();  
    public Map<String, Object> getJsonData() {
		return jsonData;
	}

	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}
    @Override
	// 模型驱动
	public Qianniu getModel() {
		// TODO Auto-generated method stub
		return null;
	}
 	 
	/**
	 * 数据处理
	 * 
	 * @return
	 * @throws java.text.ParseException
	 * @throws UnsupportedEncodingException 
	 */
	public String isQianniu() {
		try{
			HttpServletRequest request = ServletActionContext.getRequest();
			String jsonstr = request.getParameter("qianniu");
			if (jsonstr == null || jsonstr == "") {
				jsonData = DataHandling.returnError();
				return SUCCESS;
			}

			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if (startDate == "" || startDate == null || endDate == "" || endDate == null) {
				jsonData = DataHandling.returnError();
				return SUCCESS;
			}
			String s = DataHandling.getFromBASE64(jsonstr);
			JSONArray array = JSONArray.fromObject(s);
			String userid = request.getParameter("uid");
	 
			if (array != null && array.size() > 0 && userid != null && userid != "") {
				// 两个日期的相差天数
				int datenum = DateTool.daysBetween(startDate, endDate);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date start_time = sdf.parse(startDate);
				for (int d = 0; d <= datenum; d++) {
					// 时间相加天数后的时间
					Date startd = DateTool.getNextDateByDayCount(start_time, d);
					String date = sdf.format(startd);
					Map<String, Integer> map = this.dataValidation(array, date);
					if (map.size() > 0) {
						int public_num = 0;
						int collections = 0;
						int comments = 0;			 
						public_num = map.get("public_num");
						collections = map.get("collections");
						comments = map.get("comments");
				 
						Qianniu s_qianniu = (Qianniu) qianniuService.findWith(date);				
						if (s_qianniu==null) {
							Qianniu qianniu = new Qianniu();
							qianniu.setCollection_num(collections);
							qianniu.setComment_num(comments);
							qianniu.setCreate_time(new Date());
							qianniu.setDay(date);
							qianniu.setUid(userid);
							qianniu.setPublic_num(public_num);
							qianniuService.save(qianniu);
						} else {
							s_qianniu.setCollection_num(collections);
							s_qianniu.setComment_num(comments);
							s_qianniu.setPublic_num(public_num);
							s_qianniu.setCreate_time(new Date());
							qianniuService.update(s_qianniu);
						}
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
	
	/*
	* 千牛阅读数
	*/
	public String isQianniuRead() {
		try{
			HttpServletRequest request = ServletActionContext.getRequest();
			String jsonstr = request.getParameter("read");
			if (jsonstr == null || jsonstr == "") {
				jsonData = DataHandling.returnError();
				return SUCCESS;
			}
				
			String str = DataHandling.getFromBASE64(jsonstr);
			JSONArray array = JSONArray.fromObject(str);
			String userid = request.getParameter("uid");
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if (array != null && array.size() > 0 && userid != null && userid != "") {
				 for (int i = 0; i < array.size(); i++) { 
					    String day_s = array.getJSONObject(i).getString("x");		
			        	int read_num = array.getJSONObject(i).getInt("y"); 
			        	long lt = new Long(day_s);	 
			        	String date = formatter.format(lt);
			        	Qianniu s_qianniu = (Qianniu) qianniuService.findWith(date);				
						if (s_qianniu==null) {
							Qianniu qianniu = new Qianniu();
							qianniu.setCreate_time(new Date());
							qianniu.setDay(date);
							qianniu.setRead_num(read_num);
							qianniu.setUid(userid);
							qianniuService.save(qianniu);
						} else {
							s_qianniu.setRead_num(read_num);
							s_qianniu.setCreate_time(new Date());
							qianniuService.update(s_qianniu);
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
	
	/*
	* 千牛阅读数
	*/
	public String isQianniuFans() {
		try{
			HttpServletRequest request = ServletActionContext.getRequest();
			String jsonstr = request.getParameter("fans");
			if (jsonstr == null || jsonstr == "") {
				jsonData = DataHandling.returnError();
				return SUCCESS;
			}
				
			String s = DataHandling.getFromBASE64(jsonstr);
			JSONObject jsonobj = JSONObject.fromObject(s).getJSONObject("data");
			JSONArray array = jsonobj.getJSONArray("data");
			String userid = request.getParameter("uid");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (array != null && array.size() > 0 && userid != null && userid != "") {
				 for (int i = 0; i < array.size(); i++) { 
					 	int fans_num = array.getInt(i);
					 	String date = jsonobj.getString("pointStart");			 	
					    Date start_date = sdf.parse(date);
					    start_date = DateTool.getNextDateByDayCount(start_date, i);
					    date = sdf.format(start_date);
			        	Qianniu s_qianniu = (Qianniu) qianniuService.findWith(date);				
						if (s_qianniu==null) {
							Qianniu qianniu = new Qianniu();
							qianniu.setCreate_time(new Date());
							qianniu.setDay(date);
							qianniu.setFans_num(fans_num);
							qianniu.setUid(userid);
							qianniuService.save(qianniu);
						} else {
							s_qianniu.setFans_num(fans_num);
							s_qianniu.setCreate_time(new Date());
							qianniuService.update(s_qianniu);
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
	
	public Map<String, Integer> dataValidation(JSONArray array, String day){
		Map<String,Integer> map = new HashMap<String,Integer>();  
		int public_num = 0; 
		int collections_n = 0;
		int comments_n = 0;
		for (int j = 0; j < array.size(); j++) {
			String date = array.getJSONObject(j).getString("release_time");    	 
        	int collections = array.getJSONObject(j).getInt("collections");
        	int comments = array.getJSONObject(j).getInt("number_comments");
			if(date.equals(day)){
				public_num++;			 
				collections_n += collections;
				comments_n += comments;
			 }
		}
		map.put("public_num", public_num);
		map.put("collections", collections_n);
		map.put("comments", comments_n);
		return map;	
	}
 
}


