package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTool {
	/**
	 *  判断字符串是否为时间格式
	 * @param str
	 * @return
	 */
	public static boolean isValidDate(String str) {
       boolean convertSuccess=true;   
       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       try {     
          format.setLenient(false);
          format.parse(str);
       } catch (java.text.ParseException e) {
           convertSuccess=false;
       } 
       return convertSuccess;
	}
	
	/**
	 * 返回时间相加后的时间
	 * @param date
	 * @param dayCount
	 * @return
	 * @throws ParseException 
	 */
	public static Date getNextDateByDayCount(Date date, int dayCount) throws ParseException{
		Calendar scalendar = Calendar.getInstance();
		scalendar.setTime(date);
	    scalendar.add(Calendar.DATE, dayCount);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String d = sdf.format(scalendar.getTime());  
	    Date d_date=sdf.parse(d); 
		return d_date;
	}
	
	/**
	 * 功能描述：返回分
	 * @param date 日期
	 * @return 返回分钟
	 */
	public static int getMinute(Date date) {
		Calendar scalendar = Calendar.getInstance();
		scalendar.setTime(date);
	    return scalendar.get(Calendar.MINUTE);
	}
	
	 /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }    
      
	/** 
	*字符串的日期格式的计算 
	*/  
    public static int daysBetween(String smdate,String bdate) throws ParseException{  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));     
    }  
    
    public static String getDateSubtract(Date beginDate,int daysum) throws ParseException{
    	SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) - daysum);
		Date endDate = dft.parse(dft.format(date.getTime()));
		return dft.format(endDate);
    }
    
    public static Date simpleDateFormat(String str) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Date start_time = sdf.parse(str);
		return start_time;		
	}
    
}
