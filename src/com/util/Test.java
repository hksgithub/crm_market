package com.util;
 
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
 
import com.opensymphony.xwork2.ActionSupport;

/**
 * 描述：顾问业绩列表下载
 * @author  庞锐
 * @date    2018年4月18日上午10:11:02
 * @version 1.0
 */

@Controller
@Scope("prototype")
public class Test extends ActionSupport {
	/*private static final long serialVersionUID = 1L;
	
	@Resource 
	private DownloadOwnUserPerformanceService  downloadPFService;
	@Resource
	private UserAndDepartmentService userAndDepartmentService;
	
	@SuppressWarnings("unused")
	public String downloadPerformance() throws Exception{
		
		HttpServletRequest  request = ServletActionContext.getRequest();
		//request.getSession().setMaxInactiveInterval(10000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 	
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd"); 
		String month = request.getParameter("month");
		Date date = DateUtil.getNextDateByMonthCount(DateUtil.getToday(), -1);		
		if(month==null||month==""){
			month = DateUtil.getDateTimeStr(date);
			month = month.substring(0,7);
			System.out.println("month="+month);
		}		
		String dataSource = "oa_monitor";

		List<String[]> ownUserPerformanceList = new ArrayList<String[]>();				
		//业绩明细数据
		List <Map<String,String>> list = downloadPFService.getPerformanceDetailByMonth(dataSource,month);

		if(list!=null&&list.size()>0){
			for(int i = 0 ; i < list.size() ; i++){
				Map<String,String> detailMap = list.get(i);					
				String aId= detailMap.get("ITEM_A_ID");	
				System.out.println("aId---"+aId);
				TlkAgreementList tlkAgreement = downloadPFService.getAgreementById(aId); //查询协议表
				System.out.println("tlkAgreement===="+tlkAgreement);
				if(tlkAgreement==null){
					continue;
				}		
				
				String nick= detailMap.get("ITEM_NICK");
				String OwnUserName = detailMap.get("ITEM_OWN_USERNAME");
				String dept = detailMap.get("DEPT");
				String center = detailMap.get("ITEM_CENTER_DEPT");								
				TlkTCrmShop tlkTCrmShop = downloadPFService.getShopCode(nick); //查询店铺表
				String shopCode = tlkTCrmShop.getItemShopCode();
				Object realGain = detailMap.get("ITEM_REAL_GAIN");
				String beforeSplitAchievement = realGain.toString();
				String afterSplitAchievement =  realGain.toString();
				System.out.println("id----"+tlkAgreement.getId());
				String contractId = tlkAgreement.getItemContractId();
				String type = tlkAgreement.getItemType();
				String content = detailMap.get("ORDER_TYPE");
				String isOrder = "是";
				if("主单".equals(detailMap.get("SAVE_TYPE"))){
					content =  detailMap.get("PACKAGE_NAME");
					isOrder = "否";
				}
				String gainPerMonth ="";
				String serviceDay = "";
				String days = "";
				String basicIncome = "";
				String period = "";
				String gainPerDay = "";
				if(!"散单".equals(detailMap.get("item_type"))){
					List<TlkAgreementPerMonthAchievement> tlkAgreementPerMonthAchievementList = downloadPFService.getAchievementByAidAndMonthAndName( aId, month, OwnUserName);
					if(tlkAgreementPerMonthAchievementList!=null&&tlkAgreementPerMonthAchievementList.size()>0){
						TlkAgreementPerMonthAchievement perMonthAchievement = tlkAgreementPerMonthAchievementList.get(0);
						 gainPerMonth = perMonthAchievement.getItemGainPerMonth();
						 serviceDay =String.valueOf((Object)detailMap.get("ITEM_DAYS")) ;
						 days = String.valueOf((Object)detailMap.get("ITEM_DAYS")) ;
						 basicIncome = tlkAgreement.getItemOwnAmount();
						 period = tlkAgreement.getItemPeriod();
						 gainPerDay = tlkAgreement.getItemOwnGainPerDay();						
					}
				}	
				String remark = "";
				String arr[] = new String[26];
				arr[0] = nick;
				arr[1] = shopCode;
				arr[2] = month;
				arr[3] = aId;
				arr[4] = contractId;
				arr[5] = basicIncome;
				arr[6] = period;
				arr[7] = gainPerDay;
				arr[8] = days;
				arr[9] = gainPerMonth;
				arr[10] = "";
				arr[11] = "";
				arr[12] = "";
				arr[13] = "";
				
				arr[14] = beforeSplitAchievement;
				arr[15] = "";			
				arr[16] = "";
				
				arr[17] = serviceDay;
				arr[18] = afterSplitAchievement;
				arr[19] = OwnUserName;
				arr[20] = dept;
				arr[21] = center;
				arr[22] = content;
				arr[23] = isOrder;
				arr[24] = type;
				arr[25] = "";
							
				//业绩拆分数据
				String pId =String.valueOf((Object)detailMap.get("p_id"));
				List<ConfirmedSplitPerformance> splitPerformanceList = downloadPFService.getSplitPerformanceByPid(dataSource,pId);
				if(splitPerformanceList!=null&&splitPerformanceList.size()>0){
					for(int s = 0 ; s < splitPerformanceList.size() ; s++){
						ConfirmedSplitPerformance split = splitPerformanceList.get(s);
						//每天拆分表
						String splitOwnUserName = split.getUsername();
						Integer denominator = split.getDenominator();
						Integer coefficient = split.getCoefficient();
						BigDecimal afterSplitAchievement1 = split.getPerformance();
						String splitContent = split.getService();
						String splitDept = split.getDept();
						String splitCenter = split.getCenter();
						Integer splitServiceDay = null;						
						List<ConfirmedSplitPerformanceDays> SplitPerformanceDaysList = downloadPFService.getSplitPerformanceDaysByPidAndName(dataSource,pId,OwnUserName);
						String splitPerformance[] = new String[26];
						System.arraycopy(arr,0,splitPerformance,0,26);
						if(SplitPerformanceDaysList!=null&&SplitPerformanceDaysList.size()>0){
							splitServiceDay = SplitPerformanceDaysList.get(0).getPerformance();	
							splitPerformance[17] = splitServiceDay.toString();
						}	
						
						splitPerformance[15] = denominator.toString();
						splitPerformance[16] = coefficient.toString();
						splitPerformance[18] = afterSplitAchievement1.toString();						
						splitPerformance[19] = splitOwnUserName;
						splitPerformance[20] = splitDept;
						splitPerformance[21] = splitCenter;
						splitPerformance[22] = splitContent;
														
						ownUserPerformanceList.add(splitPerformance);
					}
					
				}else{
				    //没有确认业绩时
					if("N".equals(detailMap.get("is_confirm"))){						
						//查询上级领导
						TUser centerManager = userAndDepartmentService.getCenterLeaderByUserName(OwnUserName);
						if(centerManager!=null){
							OwnUserName = centerManager.getName();
							System.out.println(userAndDepartmentService.getDeptByUid(centerManager.getId()));
							dept = userAndDepartmentService.getDeptByUid(centerManager.getId()).getName();
							center = userAndDepartmentService.getCenterByUid(centerManager.getId()).getName();
							
							//人事异动查询
							Date df = format.parse(month);
							String endDay = format2.format(DateUtil.lastDayOfMonth(df));
							List<TlkTBpmHrTransferApply> tlkTBpmHrTransferApplyList = userAndDepartmentService.isTransferByName(detailMap.get("ITEM_OWN_USERNAME"),endDay);
							if(tlkTBpmHrTransferApplyList!=null&&tlkTBpmHrTransferApplyList.size()>0){
								
								List<TUser> manager = null;
								dept = tlkTBpmHrTransferApplyList.get(0).getItemDepartment();
								center = tlkTBpmHrTransferApplyList.get(0).getItemCenter();									
	
								if(manager!=null&&manager.size()>0){
									OwnUserName = manager.get(0).getName();
								}																		
							}	
							remark = "原"+detailMap.get("ITEM_OWN_USERNAME")+("主单".equals(detailMap.get("SAVE_TYPE"))?"协议":"工单")+"业绩，未确认归到中心负责人";
						}		
					}

					arr[19] = OwnUserName;
					arr[20] = dept;
					arr[21] = center;
					arr[25] = remark;
					ownUserPerformanceList.add(arr);
				}

				//工单业绩数据
				if(!"散单".equals(detailMap.get("item_type"))){
					String designPrice = "";
					String servicePrice = "";
					String diamondPrice = "" ;
					String operationPrice = "";
					
					//统计推广工单的金额
					String centers = "推广中心','数据中心','项目中心";
					List <Map<String,String>> OrderPriceList =  null;
					if(OrderPriceList!=null&OrderPriceList.size()>0){
						diamondPrice = OrderPriceList.get(0).get("total_amount");
					}	
					//运营工单金额
					String centers1 = "运营中心','创意营销中心','品牌孵化中心";
					List <Map<String,String>> operationOrderPriceList =  null;
					if(operationOrderPriceList!=null&&operationOrderPriceList.size()>0){
						operationPrice = operationOrderPriceList.get(0).get("total_amount");
					}
					if((diamondPrice!=null&&diamondPrice!=""&&Double.parseDouble(diamondPrice)>0)||
							(operationPrice!=null&&operationPrice!=""&&Double.parseDouble(operationPrice)>0)){
						
						String pf2[] = new String[26];
						System.arraycopy(arr,0,pf2,0,26);						
						pf2[5] = "";
						pf2[6] = "";
						pf2[7] = "";
						pf2[8] = "";
						pf2[9] = "";
						pf2[10] = "";
						pf2[11] = diamondPrice;
						pf2[12] = operationPrice;						
						pf2[13] = "";
						pf2[14] = "";
						pf2[17] = "";
						pf2[18] = "";																																				
							
						ownUserPerformanceList.add(pf2);
					}										
				}																					
			}
		}	
		
		//退款业绩数据
		List <Map<String,String>> refunList = null;
		if(refunList!=null&&refunList.size()>0){
			for (int r = 0;r<refunList.size();r++){
				
				String rf[] = new String[26];
				rf[0] = refunList.get(r).get("item_nick");
				rf[1] = refunList.get(r).get("item_shop_code");
				rf[2] = month;
				rf[3] = refunList.get(r).get("item_a_id");
				rf[4] = refunList.get(r).get("id");
				rf[5] = refunList.get(r).get("item_basic_income");
				rf[6] = refunList.get(r).get("item_period");
				rf[7] = refunList.get(r).get("item_own_gain_per_day");
				rf[8] = "";
				rf[9] = "";
				rf[10] = "";
				rf[11] = "";
				rf[12] = "";
				rf[13] = "";				
				rf[14] = "";
				rf[15] = "";			
				rf[16] = "";				
				rf[17] = "";
				rf[18] = String.valueOf((Object)refunList.get(r).get("item_performance"));
				rf[19] = refunList.get(r).get("item_to_username");
				rf[20] = refunList.get(r).get("item_to_dept_name");
				rf[21] = refunList.get(r).get("item_to_center_name");
				rf[22] = refunList.get(r).get("item_service");
				rf[23] = "否";
				rf[24] = refunList.get(r).get("item_type");
				rf[25] = "";					
				ownUserPerformanceList.add(rf);					
			}																			
		}	*/
	
	
	
	
		
		//业绩导出功能
//		String title = month+"市场数据报表";
//		String[] header = {"账号","发文量", "阅读量", "转发率", "收藏率","点赞/评论数", "净增粉丝数", "累计粉丝", "发文量环比", "阅读量环比","转发率环比", "收藏率环比", "点赞/评论率环比", "净增粉丝量环比"};
//		String fileName = month+new String("市场数据报表".getBytes("utf-8"), "ISO8859-1")+".xls";
//		int fontHeight = 16;
//		HttpServletResponse response = ServletActionContext.getResponse();
//		HSSFWorkbook wb = ExceportxlsUtil.exportExcelDown(title,header,ownUserPerformanceList,fileName, fontHeight);			
//		response.setContentType("application/vnd.ms-excel");  
//        response.setHeader("Content-disposition", "attachment;filename="+fileName);  
//        OutputStream ouputStream = response.getOutputStream();  
//        wb.write(ouputStream);  
//        ouputStream.flush();  
//        ouputStream.close();  
//		System.out.println("下载完成");
//		
//		return null;		
//	}
	

}

