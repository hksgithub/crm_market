package com.util;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 描述：Excel导出功能
 */
public class ExceportxlsUtil {

  public static HSSFWorkbook exportExcelDown(String title,String[] header,List<Map<String, String>> marketlist,String fileName,int fontSize){
    
     HSSFWorkbook workbook = new HSSFWorkbook();                        // 创建工作簿对象
     HSSFSheet sheet = workbook.createSheet(title);                     // 创建工作表
     int r = 0;                              //横数
     HSSFRow row = sheet.createRow(r);  
     if(fontSize==0){
       fontSize = 14;
     }
     
       //设置字体
       HSSFFont font = workbook.createFont();
       font.setFontHeightInPoints((short) fontSize);     
       font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//字体加粗
       //表头样式
       HSSFCellStyle titleStyle = workbook.createCellStyle();  
       titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  //水平居中
       titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中 
       titleStyle.setFont(font);
       
       //内容样式
       HSSFCellStyle titleStyle1 = workbook.createCellStyle();  
       titleStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);  //水平居中
       titleStyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中 
     try {
         int i = 0;
       //-------------------------第一行表头------------------------
         for (; i < header.length; i++) {  
             HSSFCell cell = row.createCell(i);  
             cell.setCellValue(header[i]);  
             cell.setCellStyle(titleStyle);  
             sheet.autoSizeColumn(i);  
         } 
         r++;
         //------------------------内容----------------------------
        for(int j = 0;j<marketlist.size();j++ ){
           HSSFRow row2 = sheet.createRow(r);
           Map<String, String> market_map = marketlist.get(j); 
 
           HSSFCell cell0 = row2.createCell(0); 
           cell0.setCellValue(market_map.get("account_number"));
           cell0.setCellStyle(titleStyle1);  
           sheet.autoSizeColumn(0);
           
           HSSFCell cell1 = row2.createCell(1); 
           cell1.setCellValue(market_map.get("public_num"));
           cell1.setCellStyle(titleStyle1);  
           sheet.autoSizeColumn(1);
           
           HSSFCell cell2 = row2.createCell(2);
           cell2.setCellValue(market_map.get("read_num"));
           cell2.setCellStyle(titleStyle1);  
           sheet.autoSizeColumn(2);
           
           HSSFCell cell3 = row2.createCell(3); 
           cell3.setCellValue(market_map.get("transmit_ratio"));
           cell3.setCellStyle(titleStyle1);  
           sheet.autoSizeColumn(3);
           
           HSSFCell cell4 = row2.createCell(4); 
           cell4.setCellValue(market_map.get("collection_ratio"));
           cell4.setCellStyle(titleStyle1);  
           sheet.autoSizeColumn(4);
           
           HSSFCell cell5 = row2.createCell(5); 
           cell5.setCellValue(market_map.get("comment_ratio"));
           cell5.setCellStyle(titleStyle1);  
           sheet.autoSizeColumn(5);
           
           HSSFCell cell6 = row2.createCell(6); 
           cell6.setCellValue(market_map.get("care_num"));
           cell6.setCellStyle(titleStyle1);  
           sheet.autoSizeColumn(6);
           
           HSSFCell cell7 = row2.createCell(7);
           cell7.setCellValue(market_map.get("fans_num"));
           cell7.setCellStyle(titleStyle1);  
           sheet.autoSizeColumn(7);
           
           HSSFCell cell8 = row2.createCell(8);
           cell8.setCellValue(market_map.get("public_hb"));
           cell8.setCellStyle(titleStyle1);  
           sheet.autoSizeColumn(8);
           
           HSSFCell cell9 = row2.createCell(9);
           cell9.setCellValue(market_map.get("read_hb"));
           cell9.setCellStyle(titleStyle1);  
           sheet.autoSizeColumn(9);
           
           HSSFCell cell10 = row2.createCell(10); 
           cell10.setCellValue(market_map.get("transmit_hb"));
           cell10.setCellStyle(titleStyle1);  
           sheet.autoSizeColumn(10);
           
           HSSFCell cell11 = row2.createCell(11);
           cell11.setCellValue(market_map.get("collection_hb"));
           cell11.setCellStyle(titleStyle1);  
           sheet.autoSizeColumn(11);
           
           HSSFCell cell12 = row2.createCell(12);
           cell12.setCellValue(market_map.get("comment_hb"));
           cell12.setCellStyle(titleStyle1);  
           sheet.autoSizeColumn(12);
           
           HSSFCell cell13 = row2.createCell(13);
           cell13.setCellValue(market_map.get("care_hb"));
           cell13.setCellStyle(titleStyle1);  
           sheet.autoSizeColumn(13);
 
           r++;
        }        
        System.out.println("导出完成！");
    } catch (Exception e) {
      System.out.println("导出出错！");
    } 
     return workbook;  
  }  
}
