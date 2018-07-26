package com.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.entity.ShopLink;
import com.entity.ShopStatus;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import sun.misc.BASE64Decoder;

public class DataHandling extends ActionSupport  {
 
	private static final long serialVersionUID = 1656515904670560641L;
 
	/**
	 * 返回成功信息
	 * @return
	 */
	public static Map<String, Object> returnSuccess(){
		Map<String,Object> jsonData = new HashMap<String,Object>();  
		jsonData.put("code","1");
		jsonData.put("msg","数据处理完毕");
		return jsonData;
	}
	
	/**
	 * 返回失败信息
	 * @return
	 */
	public static Map<String, Object> returnError(){
		Map<String,Object> jsonData = new HashMap<String,Object>();  
		jsonData.put("code","0");
		jsonData.put("msg","数据处理失败");
		return jsonData;
	}
	//返回店铺采集状态
	
	public static Map<String, Object> returnkeyWordStatus(int status,int running){
		Map<String,Object> jsonData = new HashMap<String,Object>(); 
		String s_status=String.valueOf(status);
		String s_running=String.valueOf(running);
		jsonData.put("status",s_status);
		jsonData.put("runing", s_running);
		return jsonData;
	}
	//返回搜索词
	public static Map<String,Object > returnSearchWord(List<ShopStatus> shopStatus){
		JSONArray jsArr = JSONArray.fromObject(shopStatus); 
		jsArr = JSONArray.fromObject(jsArr.toString()); 
		Map<String, Object> shopLinkMap = new HashMap<String, Object>();
		shopLinkMap.put("data", jsArr);	
		return shopLinkMap;
	}
	//返回店铺采集状态
	public static Map<String, Object> returnkeyWordStatusUse(int status,int running){
		Map<String,Object> jsonData = new HashMap<String,Object>(); 
		String s_status=String.valueOf(status);
		String s_running=String.valueOf(running);
		jsonData.put("status",s_status);
		jsonData.put("runing", s_running);
		return jsonData;
	}
	//返回查询的店铺
	public static Map<String,Object > returnShopLink(List<ShopLink> shopLinks){
		JSONArray jsArr = JSONArray.fromObject(shopLinks); 
		jsArr = JSONArray.fromObject(jsArr.toString()); 
		Map<String, Object> shopLinkMap = new HashMap<String, Object>();

		shopLinkMap.put("data", jsArr);	
		return shopLinkMap;
	}
	
	//仅返回店铺ID和店铺名
	public static Map<String,Object > returnTaoShop(List shops){
		JSONArray jsArr = JSONArray.fromObject(shops); 
		jsArr = JSONArray.fromObject(jsArr.toString()); 
		Map<String, Object> shopLinkMap = new HashMap<String, Object>();

		shopLinkMap.put("data", jsArr);	
		return shopLinkMap;
	}
	
	/**
	 * 返回marketmap
	 * @return
	 */
	public static Map<String, String> returnMarketMap(){
		Map<String,String> market_map = new HashMap<String,String>();
		market_map.put("account_number", "");
		market_map.put("public_num", 0+"");
		market_map.put("read_num", 0+"");
		market_map.put("transmit_ratio", 0+"");
		market_map.put("collection_ratio", 0+"");
		market_map.put("comment_ratio", 0+"");
		market_map.put("care_num", 0+"");
		market_map.put("fans_num", 0+"");
		market_map.put("public_hb", "");
		market_map.put("read_hb", "");
		market_map.put("transmit_hb", "");
		market_map.put("collection_hb", "");
		market_map.put("comment_hb", "");
		market_map.put("care_hb", "");
		return market_map;
	}
	
	// 将 s 进行 BASE64 编码 
	public static String getBASE64(String s) { 
		if (s == null) return null; 
		return (new sun.misc.BASE64Encoder()).encode( s.getBytes() ); 
	} 
	 
	// 将 BASE64 编码的字符串 s 进行解码 
	public static String getFromBASE64(String s) { 
		if (s == null) return null; 
		BASE64Decoder decoder = new BASE64Decoder(); 
		try { 
			byte[] b = decoder.decodeBuffer(s); 
			return new String(b,"utf-8"); 
			} catch (Exception e) { 
			return null; 
		} 
	}
	
	//发文量环比
	public static String getPublicHb(double public_num,double last_public_num){
		if(last_public_num!=0.0 && last_public_num!=0){
			return Math.round((public_num-last_public_num)/last_public_num*100)+"%";
		}
		return "";
	}
	
	//阅读量环比
	public static String getReadHb(double read_num,double last_read_num){
		if(last_read_num!=0.0 && last_read_num!=0){
			return Math.round((read_num-last_read_num)/last_read_num*100)+"%";			 					
		}
		return "";
	}
	
	//转发率环比
	public static String getTransmitHb(double transmit_ratio,double last_transmit_ratio){
		if(transmit_ratio!=0.0 && last_transmit_ratio!=0){
			return Math.round((transmit_ratio-last_transmit_ratio)/last_transmit_ratio*100)+"%";			 					
		}
		return "";
	}
	
	//收藏率环比  
	public static String getCollectionHb(double collection_ratio,double last_collection_ratio){
		if(collection_ratio!=0.0 && last_collection_ratio!=0){
			return Math.round((collection_ratio-last_collection_ratio)/last_collection_ratio*100)+"%";			 					
		}
		return "";
	}
	
	
	//点赞、评论率环比
	public static String getCommentHb(double comment_ratio,double last_comment_ratio){
		if(last_comment_ratio!=0.0 && last_comment_ratio!=0){
			return Math.round((comment_ratio-last_comment_ratio)/last_comment_ratio*100)+"%";			 					
		}
		return "";
	}
	
	//净增粉丝量环比 
	public static String getCareHb(double care_num,double last_care_num){
		if(last_care_num!=0.0 && last_care_num!=0){
			return Math.round((care_num-last_care_num)/last_care_num*100)+"%";			 					
		}
		return "";
	}
    
	public static Map<String, Object> returnAlreadyExist(){
		Map<String,Object> jsonData = new HashMap<String,Object>();  
		jsonData.put("code","1");
		jsonData.put("msg","已经存在");
		return jsonData;
	}

	public static Map<String, Object> returnNotExist(){
		Map<String,Object> jsonData = new HashMap<String,Object>();  
		jsonData.put("code","0");
		jsonData.put("msg","没抓过");
		return jsonData;
	}
	
}
