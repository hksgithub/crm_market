package com.action;

import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.opensymphony.xwork2.Action;
import com.service.HeadlineTodayService;
import com.service.PublicNumberService;
import com.service.QianniuService;
import com.service.SohuService;
import com.service.WeiboArticleService;
import com.util.DataHandling;
import com.util.DateTool;
import com.util.ExceportxlsUtil;

public class MarketReportAction {
	@Autowired
    private HeadlineTodayService headlineTodayService;
	@Autowired
    private PublicNumberService publicNumberService;
	@Autowired
    private QianniuService qianniuService;
	@Autowired
	private SohuService sohuService;
	@Autowired
    private WeiboArticleService weiboArticleService;
	
	private Map<String,Object> jsonData = new HashMap<String, Object>();  
	
    public Map<String, Object> getJsonData() {
		return jsonData;
	}

	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}
	
	public String showMarketReport(){
		return Action.SUCCESS;
	}
	
	/**
	 *  搜索数据
	 * @return
	 * @throws ParseException
	 */
	public String getMarketReportData(){
		try{
			List<Map<String, String>> marketlist = this.getMarketData();
			if(marketlist!=null && marketlist.size()>0){
				jsonData.put("code","1");
				jsonData.put("market",marketlist);
				return Action.SUCCESS;
			}
			jsonData.put("code","0");
			return Action.SUCCESS;
		}catch(Exception e){
			jsonData.put("code","0");
			return Action.SUCCESS;
		}
	}
	
	/*
	 * 市场周报数据导出
	 */
	public String getMarketExcel() {
		try{
			HttpServletRequest  request = ServletActionContext.getRequest();
			String day = request.getParameter("day");
			if(day!=null || day!=""){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
				//选择时间
				Date beginDate =sdf.parse(day);  
				//十四天前
				String start_date = DateTool.getDateSubtract(beginDate,14);
				//七天前
				String end_date = DateTool.getDateSubtract(beginDate,7);
				//一天前
				String last_date = DateTool.getDateSubtract(beginDate,1);
				//今日头条
				
				List<Map<String, String>> marketlist = this.getMarketData();
				String title = end_date+"~"+last_date+"市场数据报表";
				String[] header = {"账号","发文量", "阅读量", "转发率", "收藏率","点赞/评论数", "净增粉丝数", "累计粉丝", "发文量环比", "阅读量环比","转发率环比", "收藏率环比", "点赞/评论率环比", "净增粉丝量环比"};
				String fileName = end_date+"~"+last_date+new String("市场数据报表".getBytes("utf-8"), "ISO8859-1")+".xls";
				int fontHeight = 16;
				HttpServletResponse response = ServletActionContext.getResponse();
				HSSFWorkbook wb = ExceportxlsUtil.exportExcelDown(title,header,marketlist,fileName, fontHeight);			
				response.setContentType("application/vnd.ms-excel");  
		        response.setHeader("Content-disposition", "attachment;filename="+fileName);  
		        //response.reset();
		        OutputStream ouputStream = response.getOutputStream(); 
		        wb.write(ouputStream);  
		        ouputStream.flush();  
		        ouputStream.close();  
				return null;
			}
			return null;
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 获取平台数据
	 * @return
	 * @throws ParseException
	 */
	public List<Map<String, String>> getMarketData() {
		List<Map<String, String>> marketlist = new ArrayList<Map<String, String>>(); 
		try{
			HttpServletRequest  request = ServletActionContext.getRequest();
			String day = request.getParameter("day");
			if(day!=null || day!=""){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
				//选择时间
				Date beginDate =sdf.parse(day);  
				//十四天前
				String start_date = DateTool.getDateSubtract(beginDate,14);
				//七天前
				String end_date = DateTool.getDateSubtract(beginDate,7);
				//一天前
				String last_date = DateTool.getDateSubtract(beginDate,1);
				//今日头条
				Map<String, String> headline_map = headlineTodayService.selectDate(last_date,day,start_date,end_date);
				//微博 
				Map<String, String> weibo_map = weiboArticleService.selectDate(last_date,day,start_date,end_date);
				//JD先生公众号
				String user = "JD先生";
				Map<String, String> publicNumber_map = publicNumberService.selectDate(user,last_date,day,start_date,end_date);			
				//大麦圈公众号
				String user_a = "大麦圈";
				Map<String, String> damaipublicNumber_map = publicNumberService.selectDate(user_a,last_date,day,start_date,end_date);					
				damaipublicNumber_map.put("account_number", "大麦圈服务号");
				//大麦电商订阅号
				String user_b = "大麦电商";
				Map<String, String> damai_map = publicNumberService.selectDate(user_b,last_date,day,start_date,end_date);
				damai_map.put("account_number", "大麦电商订阅号");
				//千牛
				Map<String, String> qianniu_map = qianniuService.selectDate(last_date,day,start_date,end_date);
				//搜狐
				Map<String, String> sohu_map = sohuService.selectDate(last_date,day,start_date,end_date);
				marketlist.add(headline_map);
				marketlist.add(weibo_map);
				marketlist.add(publicNumber_map);
				marketlist.add(damaipublicNumber_map);
				marketlist.add(damai_map);
				marketlist.add(qianniu_map);
				marketlist.add(sohu_map);
				//合计
				Map<String,String> total_map = DataHandling.returnMarketMap();
				String average_transmit_ratio = Math.round((Integer.parseInt(publicNumber_map.get("transmit_ratio").replace("%",""))+Integer.parseInt(damaipublicNumber_map.get("transmit_ratio").replace("%",""))+Integer.parseInt(damai_map.get("transmit_ratio").replace("%",""))+Integer.parseInt(headline_map.get("transmit_ratio").replace("%",""))+Integer.parseInt(weibo_map.get("transmit_ratio").replace("%","")))/5)+"%";
				String average_collection_ratio = Math.round((Integer.parseInt(headline_map.get("collection_ratio").replace("%",""))+Integer.parseInt(publicNumber_map.get("collection_ratio").replace("%",""))+Integer.parseInt(damaipublicNumber_map.get("collection_ratio").replace("%",""))+Integer.parseInt(damai_map.get("collection_ratio").replace("%",""))+Integer.parseInt(qianniu_map.get("collection_ratio").replace("%","")))/5)+"%";
				String average_comment_ratio = Math.round((Integer.parseInt(headline_map.get("comment_ratio").replace("%",""))+Integer.parseInt(weibo_map.get("comment_ratio").replace("%",""))+Integer.parseInt(qianniu_map.get("comment_ratio").replace("%","")))/3)+"%";
				
				total_map.put("account_number", "合计");
				total_map.put("public_num", (Integer.parseInt(headline_map.get("public_num"))+Integer.parseInt(weibo_map.get("public_num"))+Integer.parseInt(publicNumber_map.get("public_num"))+Integer.parseInt(damai_map.get("public_num"))+Integer.parseInt(damaipublicNumber_map.get("public_num"))+Integer.parseInt(qianniu_map.get("public_num"))+Integer.parseInt(sohu_map.get("public_num")))+"");
				total_map.put("read_num", (Integer.parseInt(headline_map.get("read_num"))+Integer.parseInt(weibo_map.get("read_num"))+Integer.parseInt(damai_map.get("read_num"))+Integer.parseInt(publicNumber_map.get("read_num"))+Integer.parseInt(damaipublicNumber_map.get("read_num"))+Integer.parseInt(qianniu_map.get("read_num"))+Integer.parseInt(sohu_map.get("read_num")))+"");
				total_map.put("transmit_ratio", average_transmit_ratio);
				total_map.put("collection_ratio", average_collection_ratio);
				total_map.put("comment_ratio", average_comment_ratio);
				total_map.put("care_num", (Integer.parseInt(headline_map.get("care_num"))+Integer.parseInt(weibo_map.get("care_num"))+Integer.parseInt(publicNumber_map.get("care_num"))+Integer.parseInt(damaipublicNumber_map.get("care_num"))+Integer.parseInt(damai_map.get("care_num"))+Integer.parseInt(qianniu_map.get("care_num"))+Integer.parseInt(sohu_map.get("care_num")))+"");
				total_map.put("fans_num", (Integer.parseInt(headline_map.get("fans_num"))+Integer.parseInt(weibo_map.get("fans_num"))+Integer.parseInt(publicNumber_map.get("fans_num"))+Integer.parseInt(damaipublicNumber_map.get("fans_num"))+Integer.parseInt(damai_map.get("fans_num"))+Integer.parseInt(qianniu_map.get("fans_num"))+Integer.parseInt(sohu_map.get("fans_num")))+"");
				total_map.put("public_hb", "");
				total_map.put("read_hb", "");
				total_map.put("transmit_hb", "");
				total_map.put("collection_hb", "");
				total_map.put("comment_hb", "");
				total_map.put("care_hb", "");
				
				marketlist.add(total_map);
				return marketlist;
			}	
			return marketlist;
		}catch(Exception e){
			return marketlist;
		}
	}
}