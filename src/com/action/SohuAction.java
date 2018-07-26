package com.action;

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

import com.dao.impl.SohuDaoImpl;
import com.entity.Sohu;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.SohuService;
import com.util.DataHandling;
import com.util.DateTool;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
 
public class SohuAction extends ActionSupport implements ModelDriven<Sohu>{
 
	/**
	 * 搜狐
	 */
	private static final long serialVersionUID = 1656515904670560641L;
 
    protected HttpServletRequest request = ServletActionContext.getRequest();
    @Autowired
    private SohuService sohuFansService;
    private Map<String,Object> jsonData = new HashMap<String, Object>(); 
    public Map<String, Object> getJsonData() {
		return jsonData;
	}

	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}
    @Override
	// 模型驱动
	public Sohu getModel() {
		// TODO Auto-generated method stub
		return null;
	}
 
	/**
	 * 阅读量数据处理
	 * @return
	 * @throws java.text.ParseException
	 */
	public String isSohuRead(){
		try{
			String jsonstr = request.getParameter("read");
			if(jsonstr==null || jsonstr==""){
				jsonData = DataHandling.returnError();
				return SUCCESS;
			}
			// 获取数据
			String s = DataHandling.getFromBASE64(jsonstr);
			String str = JSONObject.fromObject(s).getString("result");
			JSONObject j_obj = JSONObject.fromObject(str);
			String userid= request.getParameter("uid");
			if(j_obj!=null && j_obj.size()>0 && userid != null && userid != ""){	
				JSONArray arr_list = j_obj.getJSONArray("list");
				if(arr_list!=null && arr_list.size()>0){
					for (int i = 0; i < arr_list.size(); i++) {
						String day = arr_list.getJSONObject(i).getString("date");
						int read_num = arr_list.getJSONObject(i).getInt("diff");
						Sohu s_sohu = (Sohu) sohuFansService.findWith(day);								
						if(s_sohu==null){
							Sohu sohu = new Sohu();
							sohu.setUid(userid);
							sohu.setDay(day);
							sohu.setCreate_time(new Date());
							sohu.setRead_num(read_num);
							sohuFansService.save(sohu);
						}else{
							s_sohu.setRead_num(read_num);
							s_sohu.setCreate_time(new Date());
							sohuFansService.update(s_sohu);
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
	
	/**
	 * 发文量数据处理
	 * @return
	 * @throws java.text.ParseException
	 */
	public String isSohuArticle() {
		try{
			String jsonstr = request.getParameter("article");
			if(jsonstr==null || jsonstr==""){
				jsonData = DataHandling.returnError();
				return SUCCESS;
			}
			String userid= request.getParameter("uid");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if(startDate=="" || startDate==null || endDate=="" || endDate==null){ 
				jsonData = DataHandling.returnError();
				return SUCCESS;
			}
			String s = DataHandling.getFromBASE64(jsonstr);
			JSONObject jsonobj = JSONObject.fromObject(s);
			JSONArray array = jsonobj.getJSONArray("result");
	 
			if (array != null && array.size() > 0 && userid != null && userid != "") {
				//两个日期的相差天数
				int datenum = DateTool.daysBetween(startDate, endDate);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			    Date start_time = sdf.parse(startDate);
				for(int d=0;d<=datenum;d++){
					//时间相加天数后的时间
					Date startd = DateTool.getNextDateByDayCount(start_time, d);
					String date = sdf.format(startd);
					//计算发文量
					int puclicnum = this.dataValidation(array,date);
					Sohu s_sohu = (Sohu) sohuFansService.findWith(date);			 						
					if(s_sohu==null){	
						Sohu sohu = new Sohu();
						sohu.setUid(userid);
						sohu.setDay(date);
						sohu.setCreate_time(new Date());
						sohu.setPublic_num(puclicnum);	
						sohuFansService.save(sohu);
					}else{
						s_sohu.setPublic_num(puclicnum);
						s_sohu.setCreate_time(new Date());
						sohuFansService.update(s_sohu);
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
	 * 计算文章的转发量
	 * @param day 
	 * @param arr_list 
	 * @return
	 * @throws java.text.ParseException
	 */
	public Integer dataValidation(JSONArray array, String day){
		int num = 0;
		for (int j = 0; j < array.size(); j++) {
			 String date = array.getJSONObject(j).getString("date");
			 if(date.equals(day)){
				 num++;
			 }
		}
		return num;	
	}
 
	
}


