package com.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.entity.PublicNumber;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.PublicNumberService;
import com.util.DataHandling;
import com.util.DateTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
 
public class PublicNumberAction extends ActionSupport implements ModelDriven<PublicNumber>{
 
	/**
	 * 公众号
	 */
	private static final long serialVersionUID = 1656515904670560641L;
 
	protected HttpServletRequest request = ServletActionContext.getRequest();
	@Autowired
    private PublicNumberService publicNumberService;
    private Map<String,Object> jsonData = new HashMap<String,Object>();
    public Map<String, Object> getJsonData() {
		return jsonData;
	}

	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}
    @Override
	// 模型驱动
	public PublicNumber getModel() {
		// TODO Auto-generated method stub
		return null;
	}
 
	/**
	 * 净增关注、累计粉丝数
	 * 
	 * @return
	 */
	public String isPublicNumberFans() {
		try{
			String jsonstr = request.getParameter("fans");
			if (jsonstr == null || jsonstr == "") {
				jsonData = DataHandling.returnError();
				return SUCCESS;
			}
			// 获取数据
			String s = DataHandling.getFromBASE64(jsonstr);
			JSONObject jsonobj = JSONObject.fromObject(s);
			JSONObject userobj = jsonobj.getJSONObject("user_info");
			// 公众号
			String user = userobj.getString("nick_name");
			boolean statu = user.contains("电商"); 
			boolean status = user.contains("圈"); 
	        if(statu){  
	        	user = "大麦电商"; 
	        } 
	        if(status){
	        	user = "大麦圈"; 
	        }
	        
			JSONArray array = jsonobj.getJSONArray("category_list");
			if (user != null && user != "" && array != null && array.size() > 0) {
				for (int i = 0; i < array.size(); i++) {
					String jobj = array.getJSONObject(i).getString("list");
					JSONArray json = JSONArray.fromObject(jobj);
					if (json.size() > 0) {
						for (int j = 0; j < json.size(); j++) {
							String date = json.getJSONObject(j).getString("date");
							int care_num = json.getJSONObject(j).getInt("netgain_user");
							int fans_num = json.getJSONObject(j).getInt("cumulate_user");
							
							PublicNumber s_publicNumber = (PublicNumber) publicNumberService.findWith(user,date);
							if (s_publicNumber==null) {
								PublicNumber publicNumber = new PublicNumber();
								publicNumber.setUid(user);
								publicNumber.setDay(date);
								publicNumber.setCare_num(care_num);
								publicNumber.setFans_num(fans_num);
								publicNumber.setCreate_time(new Date());
								publicNumberService.save(publicNumber);
							} else {
								s_publicNumber.setCare_num(care_num);
								s_publicNumber.setFans_num(fans_num);
								s_publicNumber.setCreate_time(new Date());
								publicNumberService.update(s_publicNumber);
							}
						}
					}
					break;
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
	 * 发文量
	 * @return
	 * @throws ParseException 
	 */
	public String isPublicNumberArticle() {
		try{
			String jsonstr = request.getParameter("article");
			if(jsonstr==null || jsonstr==""){
				jsonData = DataHandling.returnError();
				return SUCCESS;
			}
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if(startDate=="" || startDate==null || endDate=="" || endDate==null){ 
				jsonData = DataHandling.returnError();
				return SUCCESS;
			}
		 
			//获取数据
			String s = DataHandling.getFromBASE64(jsonstr);
			JSONObject jsonobj = JSONObject.fromObject(s);
			JSONObject userobj = jsonobj.getJSONObject("user_info");
			//公众号
			String user = userobj.getString("nick_name");
			boolean statu = user.contains("电商"); 
			boolean status = user.contains("圈"); 
	        if(statu){  
	        	user = "大麦电商"; 
	        } 
	        if(status){
	        	user = "大麦圈"; 
	        }
	        
			String str = jsonobj.getString("total_article_data");
			//去除头尾的双引号
			str=str.substring(0,1).equals("\"")?str.substring(1,str.length()-1):str;
			str=str.substring(str.length()-1,str.length()).equals("\"")?str.substring(0,str.length()-1):str;
			JSONObject article_obj = JSONObject.fromObject(str);
	 
			if(user!=null && user!="" && article_obj!=null && article_obj.size()>0){	
				JSONArray arr_list = article_obj.getJSONArray("list");
				if(arr_list!=null && arr_list.size()>0){
					//两个日期的相差天数
					int datenum = DateTool.daysBetween(startDate, endDate);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				    Date start_time = sdf.parse(startDate);
					for(int d=0;d<=datenum;d++){
						Date startd = DateTool.getNextDateByDayCount(start_time, d);
						String date = sdf.format(startd);
						int puclicnum = this.dataValidation(arr_list,date);
						PublicNumber s_publicNumber = (PublicNumber) publicNumberService.findWith(user,date);
			        	if(s_publicNumber==null){	
			        		PublicNumber publicNumber = new PublicNumber();
			        		publicNumber.setUid(user);
							publicNumber.setDay(date);					 
							publicNumber.setPublic_num(puclicnum);
							publicNumber.setCreate_time(new Date());
							publicNumberService.save(publicNumber);
			        	}else{
			        		s_publicNumber.setCreate_time(new Date());
			        		s_publicNumber.setPublic_num(puclicnum);
			        		publicNumberService.update(s_publicNumber);
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
	 * 计算文章的转发量
	 * @param day 
	 * @param arr_list 
	 * @return
	 * @throws java.text.ParseException
	 */
	public Integer dataValidation(JSONArray arr_list, String day){
		int num = 0;
		for (int j = 0; j < arr_list.size(); j++) {
			 String date = arr_list.getJSONObject(j).getString("publish_date");
			 if(date.equals(day)){
				 num++;
			 }
		}
		return num;	
	}
	
	/**
	 * 阅读、收藏数
	 * 
	 * @return
	 */
	public String isPublicNumberRead(){
		try{
			String jsonstr = request.getParameter("read");
			if(jsonstr==null || jsonstr==""){
				jsonData = DataHandling.returnError();
				return SUCCESS;
			}
			//获取数据
			String s = DataHandling.getFromBASE64(jsonstr);
			JSONObject jsonobj = JSONObject.fromObject(s);
			JSONObject userobj = jsonobj.getJSONObject("user_info");
			//公众号
			String user = userobj.getString("nick_name");
			boolean statu = user.contains("电商"); 
			boolean status = user.contains("圈"); 
	        if(statu){  
	        	user = "大麦电商"; 
	        } 
	        if(status){
	        	user = "大麦圈"; 
	        }
	        
			JSONArray array = jsonobj.getJSONArray("item");
			
			if(user!=null && user!="" && array!=null && array.size()>0){			
				 for (int i = 0; i < array.size(); i++) { 
					 JSONObject jobj = array.getJSONObject(i);
					 String user_source = jobj.getString("user_source");
					 if(user_source.equals("99999999")){
						 String date = jobj.getString("ref_date");
						 int read_num = jobj.getInt("int_page_read_count");			 
						 int collection_num = jobj.getInt("add_to_fav_count");	
						 int forwarding = jobj.getInt("share_count");
						 PublicNumber s_publicNumber = (PublicNumber) publicNumberService.findWith(user,date);
			        	 if(s_publicNumber==null){	
			        		 PublicNumber publicNumber = new PublicNumber();
							 publicNumber.setUid(user);
							 publicNumber.setDay(date);					 
							 publicNumber.setRead_num(read_num);
							 publicNumber.setCollection_num(collection_num);
							 publicNumber.setForwarding_num(forwarding);
							 publicNumber.setCreate_time(new Date());
							 publicNumberService.save(publicNumber);
			        	 }else{
			        		 s_publicNumber.setRead_num(read_num);
			        		 s_publicNumber.setCollection_num(collection_num);
			        		 s_publicNumber.setForwarding_num(forwarding);
			        		 s_publicNumber.setCreate_time(new Date());
			        		 publicNumberService.update(s_publicNumber);
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
	
	public String getMarketReportData(){
		
		return Action.SUCCESS;
	}
 
}


