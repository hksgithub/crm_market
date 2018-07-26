package com.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.dao.WeiboArticleDao;
import com.entity.WeiboArticle;
import com.service.WeiboArticleService;
import com.util.DataHandling;
import com.util.DateTool;
@Component
public class WeiboArticleServiceImpl extends BaseServiceImpl<Integer,WeiboArticle> implements WeiboArticleService {
	// 注入serviceDao
	@Resource
    private WeiboArticleDao weiboArticleDao;
    
	@Override
	public Object findWith(String day) {
		return weiboArticleDao.findWith(day);
	}
 
	@Override
	public Map<String, String> selectDate(String last_date, String day, String start_date, String end_date) {
		Map<String,String> weiboArticle = DataHandling.returnMarketMap();
		weiboArticle.put("account_number", "微博");
		try{
			List <Map<String,String>> weiboArticleList = weiboArticleDao.selectDate(start_date, day); 	
			DecimalFormat df = new DecimalFormat("########0.00");
					
			if(weiboArticleList!=null && weiboArticleList.size()>0){
				double public_num = 0.0;
				double last_public_num = 0.0;
				double read_num = 0.0;
				double last_read_num = 0.0;
				double transmit_ratio = 0.0;
				double last_transmit_ratio = 0.0;
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
				for(int i=0;i<weiboArticleList.size();i++){
	                String day_str = weiboArticleList.get(i).get("DAY");
	                Date day_date = DateTool.simpleDateFormat(day_str);
	                double p_num = 0.0;
	                if(weiboArticleList.get(i).get("PUBLIC_NUM")!=null && weiboArticleList.get(i).get("PUBLIC_NUM")!=""){
	                    p_num = Double.parseDouble(String.valueOf((Object)weiboArticleList.get(i).get("PUBLIC_NUM")));
	                }
	                double r_num = 0.0;
	                if(weiboArticleList.get(i).get("READ_NUM")!=null && weiboArticleList.get(i).get("READ_NUM")!=""){
	                    r_num = Double.parseDouble(String.valueOf((Object)weiboArticleList.get(i).get("READ_NUM")));
	                }
	  
	                double comment_num = 0.0;
	                if(weiboArticleList.get(i).get("COMMENT_NUM")!=null && weiboArticleList.get(i).get("COMMENT_NUM")!=""){
	                    comment_num = Double.parseDouble(String.valueOf((Object)weiboArticleList.get(i).get("COMMENT_NUM")));
	                }
	                double f_num = 0.0;
	                if(weiboArticleList.get(i).get("TOTAL_NUM")!=null && weiboArticleList.get(i).get("TOTAL_NUM")!=""){
	                    f_num = Double.parseDouble(String.valueOf((Object)weiboArticleList.get(i).get("TOTAL_NUM")));
	                }
	                double care_n = 0.0;
	                if(weiboArticleList.get(i).get("CARE_NUM")!=null && weiboArticleList.get(i).get("CARE_NUM")!=""){
	                    care_n = Double.parseDouble(String.valueOf((Object)weiboArticleList.get(i).get("CARE_NUM")));
	                }
	                double forward_num = 0.0;
	                if(weiboArticleList.get(i).get("FORWARDING_NUM")!=null && weiboArticleList.get(i).get("FORWARDING_NUM")!=""){
	                    forward_num = Double.parseDouble(String.valueOf((Object)weiboArticleList.get(i).get("FORWARDING_NUM")));
	                }
	                double cancle_num = 0.0;
	                if(weiboArticleList.get(i).get("CANCLE_NUM")!=null && weiboArticleList.get(i).get("CANCLE_NUM")!=""){
	                	cancle_num = Double.parseDouble(String.valueOf((Object)weiboArticleList.get(i).get("CANCLE_NUM")));
	                }
	                //上周数据  PRAISE_NUM
	                if(day_date.getTime()>=DateTool.simpleDateFormat(start_date).getTime() 
	                        && day_date.getTime()<DateTool.simpleDateFormat(end_date).getTime()){
	                    last_public_num += p_num;
	                    last_read_num += r_num;
	                    last_care_num += care_n;
	                    last_forwarding_num += forward_num;
	                    if(r_num!=0.0){
	                    	//上周转发率
	                    	last_transmit_ratio += forward_num/r_num;
	                        //上周点赞/评论率
	                        last_comment_ratio += comment_num/r_num;
	                    }               
	                }
	
	                
	                //本周数据
	                if(day_date.getTime()>=DateTool.simpleDateFormat(end_date).getTime() 
	                        && day_date.getTime()<DateTool.simpleDateFormat(day).getTime()){                    
	                    public_num += p_num;                
	                    read_num += r_num;
	                    care_num += care_n;
	                    forwarding_num += forward_num;
	                    //本周累计粉丝            
	                    fans_num = f_num;
	                    if(r_num!=0.0){
	                        //本周转发率
	                        transmit_ratio += forward_num/r_num;
	                        //本周点赞/评论率
	                        comment_ratio += comment_num/r_num;
	                    }                   
	                }                   
	           }
			   weiboArticle.put("public_num", (int)public_num+ "");
			   weiboArticle.put("read_num", (int)read_num+ "");
	 
	           //评论率
	           comment_ratio = comment_ratio/7;
	           //本周转发率
	           transmit_ratio = transmit_ratio/7;    
	           
	           weiboArticle.put("transmit_ratio", Math.round(transmit_ratio*100)+"%");                   
	           weiboArticle.put("comment_ratio", Math.round(comment_ratio*100)+"%");
	           weiboArticle.put("care_num",(int)care_num+ "");
	           weiboArticle.put("fans_num", (int)fans_num+"");
	           
	           //上周点赞/评论率
	           last_comment_ratio = last_comment_ratio/7;
	           //上周转发率
	           last_transmit_ratio = last_transmit_ratio/7; 
	        
	           weiboArticle.put("public_hb", DataHandling.getPublicHb(public_num, last_public_num));
	           weiboArticle.put("read_hb", DataHandling.getReadHb(read_num, last_read_num));
	           weiboArticle.put("collection_hb", DataHandling.getCollectionHb(0, 0));
	           weiboArticle.put("comment_hb", DataHandling.getCommentHb(comment_ratio, last_comment_ratio));
	           weiboArticle.put("transmit_hb",DataHandling.getTransmitHb(transmit_ratio, last_transmit_ratio));	
	           weiboArticle.put("care_hb", DataHandling.getCareHb(care_num, last_care_num));
			}	
			return weiboArticle;
		}catch(Exception e){
			return weiboArticle;
		}
	}
	
}
