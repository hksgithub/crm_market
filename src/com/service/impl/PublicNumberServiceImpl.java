package com.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dao.PublicNumberDao;
import com.dao.impl.PublicNumberDaoImpl;
import com.entity.PublicNumber;
import com.service.PublicNumberService;
import com.util.DataHandling;
import com.util.DateTool;
@Component
public class PublicNumberServiceImpl extends BaseServiceImpl<Integer,PublicNumber> implements PublicNumberService {
	// 注入serviceDao
	@Resource
    private PublicNumberDao publicNumberDao;
  
    @Override
	public Object findWith(String user,String day) {
		return publicNumberDao.findWith(user,day);
	}
 
    @Override
	public Map<String, String> selectDate(String user,String last_date, String day, String start_date, String end_date) {
    	Map<String,String> publicNumber_map = DataHandling.returnMarketMap();
	    publicNumber_map.put("account_number", user);
	    try{
	    	List<PublicNumber> publicNumberList = publicNumberDao.selectDate(user,start_date, day);  
		    DecimalFormat df = new DecimalFormat("########0.00");
		    
		    if(publicNumberList!=null && publicNumberList.size()>0){  
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
				for(int i=0;i<publicNumberList.size();i++){
		            String day_str = publicNumberList.get(i).getDay();
		            Date day_date = DateTool.simpleDateFormat(day_str);
		            double p_num = 0.0;
		            if(publicNumberList.get(i).getPublic_num()!=null){
		                p_num = publicNumberList.get(i).getPublic_num();
		            }
		            double r_num = 0.0;
		            if(publicNumberList.get(i).getRead_num()!=null){
		                r_num = publicNumberList.get(i).getRead_num();
		            }
		            double collection_num = 0.0;
		            if(publicNumberList.get(i).getCollection_num()!=null){
		                collection_num = publicNumberList.get(i).getCollection_num();
		            }
	//	            double comment_num = 0.0;
	//	            if(publicNumberList.get(i).getComment_num()!=null){
	//	                comment_num = publicNumberList.get(i).getComment_num();
	//	            }
		            double f_num = 0.0;
		            if(publicNumberList.get(i).getFans_num()!=null){
		                f_num = publicNumberList.get(i).getFans_num();
		            }
		            double care_n = 0.0;
		            if(publicNumberList.get(i).getCare_num()!=null){
		                care_n = publicNumberList.get(i).getCare_num();
		            }
		            double forward_num = 0.0;
		            if(publicNumberList.get(i).getForwarding_num()!=null){
		                forward_num = publicNumberList.get(i).getForwarding_num();
		            }
	//	            double cancel_num = 0.0;
	//	            if(publicNumberList.get(i).getCancel_num()!=null){
	//	                cancel_num = publicNumberList.get(i).getCancel_num();
	//	            }
		            //上周数据
		            if(day_date.getTime()>=DateTool.simpleDateFormat(start_date).getTime() 
		                    && day_date.getTime()<DateTool.simpleDateFormat(end_date).getTime()){
		                last_public_num += p_num;
		                last_read_num += r_num;
		                last_care_num += care_n;
		                last_forwarding_num += forward_num;
		                if(r_num!=0.0){
		                	//上周转发率
							last_transmit_ratio += forward_num/r_num;			
		                    //上周收藏率
		                    last_collection_ratio += collection_num/r_num;
		                    //上周点赞/评论率
	//	                    last_comment_ratio += comment_num/r_num;
		                }               
		            }
		
		            
		            //本周数据
		            if(day_date.getTime()>=DateTool.simpleDateFormat(end_date).getTime() 
		                    && day_date.getTime()<DateTool.simpleDateFormat(day).getTime()){                    
		                public_num += p_num;                
		                read_num += r_num;
	//	                care_num += (care_n-cancel_num);
		                forwarding_num += forward_num;
		                //净增粉丝
		                care_num += care_n;
		                //本周累计粉丝            
		                fans_num = f_num;
		                if(r_num!=0.0){
		                    //本周转发率
		                    transmit_ratio += forward_num/r_num;
		                    //本周收藏率
		                    collection_ratio += collection_num/r_num;
		                    //本周点赞/评论率
	//	                    comment_ratio += comment_num/r_num;
		                }                   
		            }  
				}
		   
		       publicNumber_map.put("public_num", (int)public_num+ "");
		       publicNumber_map.put("read_num", (int)read_num+ "");
		       //本周收藏率
	           collection_ratio = collection_ratio/7;
	           //评论率
	           comment_ratio = comment_ratio/7;
	           //本周转发率
	           transmit_ratio = transmit_ratio/7;
		       
		       publicNumber_map.put("transmit_ratio", Math.round(transmit_ratio*100)+"%");          
		       publicNumber_map.put("collection_ratio", Math.round(collection_ratio*100)+"%");          
		       //publicNumber_map.put("comment_ratio", df.format(comment_ratio)+ "");
		       publicNumber_map.put("care_num",(int)care_num+ "");
		       publicNumber_map.put("fans_num", (int)fans_num+"");
		    
		       //上周转发率
		       last_transmit_ratio = last_transmit_ratio/7;
		       //上周收藏率
	           last_collection_ratio = last_collection_ratio/7;
	           //上周点赞/评论率
	           last_comment_ratio = last_comment_ratio/7;
	           
	           publicNumber_map.put("public_hb", DataHandling.getPublicHb(public_num, last_public_num));
	           publicNumber_map.put("read_hb", DataHandling.getReadHb(read_num, last_read_num));
	           publicNumber_map.put("collection_hb", DataHandling.getCollectionHb(collection_ratio, last_collection_ratio));
	           publicNumber_map.put("comment_hb", DataHandling.getCommentHb(comment_ratio, last_comment_ratio));
	           publicNumber_map.put("transmit_hb",DataHandling.getTransmitHb(transmit_ratio, last_transmit_ratio));	
	           publicNumber_map.put("care_hb", DataHandling.getCareHb(care_num, last_care_num));
		    } 
		    return publicNumber_map;
	    }catch(Exception e){
	    	return publicNumber_map;
	    }
    }
 
}
