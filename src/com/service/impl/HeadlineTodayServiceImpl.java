package com.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.dao.HeadlineTodayDao;
import com.entity.HeadlineToday;
import com.service.HeadlineTodayService;
import com.util.DataHandling;
import com.util.DateTool;
@Component
public class HeadlineTodayServiceImpl extends BaseServiceImpl<Integer,HeadlineToday> implements HeadlineTodayService {
	// 注入serviceDao、
	@Resource
    private HeadlineTodayDao headlineTodayDao;
 
	@Override
	public Object findWith(String day) {
		return headlineTodayDao.findWith(day);
	}
 
	/**
	 * 获取头条数据
	 * @param day 选择时间
	 * @param last_date 选择时间的前一天
	 * @param start_date 十四天前
	 * @param end_date 七天前
	 * @return
	 */
	@Override
	public Map<String, String> selectDate(String last_date,String day,String start_date,String end_date) {
		Map<String,String> headline_map = DataHandling.returnMarketMap();
		headline_map.put("account_number", "今日头条");
		try{
			List<HeadlineToday> headlineToday = headlineTodayDao.selectDate(start_date, day); 
			DecimalFormat df = new DecimalFormat("########0.00");	
			if(headlineToday!=null && headlineToday.size()>0){	
				double public_num = 0.0;
				double last_public_num = 0.0;
				double read_num = 0.0;
				double last_read_num = 0.0;
				double transmit_ratio = 0.0;
				double last_transmit_ratio = 0.0;
				double collection_ratio = 0.0;
				double last_collection_ratio = 0.0;
				double comment_ratio = 0.0;
				double last_comment_ratio = 0.0;
				double care_num = 0.0;
				double fans_num = 0.0;
				double last_care_num = 0.0;
				double public_hb = 0.0;
				double read_hb = 0.0; 
				double collection_hb = 0.0; 
				double comment_hb = 0.0; 
				double care_hb = 0.0; 
				double forwarding_num = 0.0;
				double last_forwarding_num = 0.0;
				double transmit_hb = 0.0;
				
				for(int i=0;i<headlineToday.size();i++){
					 String day_str = headlineToday.get(i).getDay();
					 Date day_date = DateTool.simpleDateFormat(day_str);
					 double p_num = 0.0;
					 if(headlineToday.get(i).getPublic_num()!=null){
						 p_num = headlineToday.get(i).getPublic_num();
					 }
					 double r_num = 0.0;
					 if(headlineToday.get(i).getRead_num()!=null){
						 r_num = headlineToday.get(i).getRead_num();
					 }
					 double collection_num = 0.0;
					 if(headlineToday.get(i).getCollection_num()!=null){
						 collection_num = headlineToday.get(i).getCollection_num();
					 }
					 double comment_num = 0.0;
					 if(headlineToday.get(i).getComment_num()!=null){
						 comment_num = headlineToday.get(i).getComment_num();
					 }
					 double f_num = 0.0;
					 if(headlineToday.get(i).getFans_num()!=null){
						 f_num = headlineToday.get(i).getFans_num();
					 }
					 double care_n = 0.0;
					 if(headlineToday.get(i).getCare_num()!=null){
						 care_n = headlineToday.get(i).getCare_num();
					 }
					 double forward_num = 0.0;
					 if(headlineToday.get(i).getForwarding_num()!=null){
						 forward_num = headlineToday.get(i).getForwarding_num();
					 }
					 double cancel_num = 0.0;
					 if(headlineToday.get(i).getCancel_num()!=null){
						 cancel_num = headlineToday.get(i).getCancel_num();
					 }
					 //上周数据
					 if(day_date.getTime()>=DateTool.simpleDateFormat(start_date).getTime() 
							 && day_date.getTime()<DateTool.simpleDateFormat(end_date).getTime()){
						 last_public_num += p_num;
						 last_read_num += r_num;
						 last_care_num += (care_n-cancel_num);
						 last_forwarding_num += forward_num;
						 if(r_num!=0.0){
							 //上周转发率
							 last_transmit_ratio += forward_num/r_num;						 
							 //上周收藏率
							 last_collection_ratio += collection_num/r_num;
							 //上周点赞/评论率
							 last_comment_ratio += comment_num/r_num;
						 }				 
					 }
	 
					 
					 //本周数据
					 if(day_date.getTime()>=DateTool.simpleDateFormat(end_date).getTime() 
							 && day_date.getTime()<DateTool.simpleDateFormat(day).getTime()){					 
						 public_num += p_num;				 
						 read_num += r_num;
						 care_num += (care_n-cancel_num);
						 forwarding_num += forward_num;
						 //本周累计粉丝			 
						 fans_num = f_num;
						 if(r_num!=0.0){
							 //本周转发率
							 transmit_ratio += forward_num/r_num;
							 //本周收藏率
							 collection_ratio += collection_num/r_num;
							 //本周点赞/评论率
							 comment_ratio += comment_num/r_num;
						 }		 			 
					 }	 				 
				}
				headline_map.put("public_num", (int)public_num+ "");
				headline_map.put("read_num", (int)read_num+ "");
				 //本周收藏率
	            collection_ratio = collection_ratio/7;
	            //评论率
	            comment_ratio = comment_ratio/7;
	            //本周转发率
	            transmit_ratio = transmit_ratio/7;
				 
				headline_map.put("transmit_ratio",  Math.round(transmit_ratio*100)+"%");			
				headline_map.put("collection_ratio", Math.round(collection_ratio*100)+"%");			
				headline_map.put("comment_ratio", Math.round(comment_ratio*100)+"%");
				headline_map.put("care_num",(int)care_num+ "");
				headline_map.put("fans_num", (int)fans_num+"");
		 
				//上周转发率
				last_transmit_ratio = last_transmit_ratio/7;
				//上周收藏率
	            last_collection_ratio = last_collection_ratio/7;
	            //上周点赞/评论率
	            last_comment_ratio = last_comment_ratio/7;
	            
				headline_map.put("public_hb", DataHandling.getPublicHb(public_num, last_public_num));
				headline_map.put("read_hb", DataHandling.getReadHb(read_num, last_read_num));
		        headline_map.put("collection_hb", DataHandling.getCollectionHb(collection_ratio, last_collection_ratio));
		        headline_map.put("comment_hb", DataHandling.getCommentHb(comment_ratio, last_comment_ratio));
		        headline_map.put("transmit_hb",DataHandling.getTransmitHb(transmit_ratio, last_transmit_ratio));	
				headline_map.put("care_hb", DataHandling.getCareHb(care_num, last_care_num));
			} 
			return headline_map;
		}catch(Exception e){
			return headline_map;
		}
	}
}
