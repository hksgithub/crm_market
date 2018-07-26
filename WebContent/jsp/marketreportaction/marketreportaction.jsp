<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
    request.setAttribute("path",path);
    request.setAttribute("basePath",basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>市场周报</title>
<script src="${basePath}/jsp/marketreportaction/js/My97DatePicker/WdatePicker.js"></script>
<script src="${basePath}/jsp/marketreportaction/js/jquery.js"></script>
<script src="${basePath}/jsp/marketreportaction/js/jquery.min.js"></script>
<script src="${basePath}/jsp/marketreportaction/js/jquery-ui.min.js"></script>
<script src="${basePath}/jsp/marketreportaction/js/jquery-ui-1.9.2.custom.dialog.min.js"></script>
<script src="${basePath}/jsp/marketreportaction/css/jquery-ui-1.9.2.custom.css"></script>
<script src="${basePath}/jsp/marketreportaction/css/jquery-ui.min.css"></script>
<style type="text/css">
/* 对话框背景 */
.ui-dialog{
background-color:#F5F5F5;
}

/* 按钮设置 */
.ui-dialog-buttonset {
    text-align: center;
}

/* 隐藏默认关闭按钮 */
.no-close .ui-dialog-titlebar-close {
  display: none;
}

td  
{  
    text-align:center;  
} 
</style>
</head>
<body>
<div id="main">

<div align="center" id="title">
<h2>市场周报</h2>
</div>
<div id="search">
	 <form id="form" action="getMarketReportDatan.action" method="post">
	<table width="450"  align="center" cellspacing="0" border="1">
	<tbody>
      <tr> 
            <td style="border: 1px solid rgb(180, 204, 238);width:250px;">
          	  日期 ：<input type="text" class="Wdate" name="day"  id="month" onClick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd'})"></td>
            <td style="border: 1px solid rgb(180, 204, 238);">
            <a href="javascript:void(0)" id="check_data_btn" class="btn btn-primary btn-flat">
            <i class="fa fa-search"></i>搜索</a></td>
            <td style="border: 1px solid rgb(180, 204, 238);"><input type="hidden" name="export"  id="export" value=""/>
            <a href="javascript:void(0)" id="check_data_export" class="btn btn-primary btn-flat"><i
                 class="fa fa-search"></i> 导出 </a></td>
       </tr>
       <iframe id="downloadcsv" style="display:none"></iframe>
    </tbody>   
    </table>
    </form>
</div>
<div align="center" style="color:#FF0000">例：选择日期4月8日，则统计4月1日~4月7日数据 </div>
<p></p>
<div id="content">
<table width="1200" style="border: 1px solid rgb(180, 204, 238);" align="center" cellspacing="0" border="1">
    <thead>
        <tr>
            <th style="border: 1px solid rgb(180, 204, 238);">账号</th>
            <th style="border: 1px solid rgb(180, 204, 238);">发文量</th>
            <th style="border: 1px solid rgb(180, 204, 238);">阅读量</th>  
            <th style="border: 1px solid rgb(180, 204, 238);">转发率</th>
            <th style="border: 1px solid rgb(180, 204, 238);">收藏率</th>     
            <th style="border: 1px solid rgb(180, 204, 238);">点赞/评论率</th> 
            <th style="border: 1px solid rgb(180, 204, 238);">净增粉丝数</th> 
            <th style="border: 1px solid rgb(180, 204, 238);">累计粉丝</th> 
            <th style="border: 1px solid rgb(180, 204, 238);">发文量环比</th> 
            <th style="border: 1px solid rgb(180, 204, 238);">阅读量环比</th> 
            <th style="border: 1px solid rgb(180, 204, 238);">转发率环比</th> 
            <th style="border: 1px solid rgb(180, 204, 238);">收藏率环比</th> 
            <th style="border: 1px solid rgb(180, 204, 238);">点赞/评论率环比</th> 
            <th style="border: 1px solid rgb(180, 204, 238);">净增粉丝量环比</th>     
        </tr>			
    </thead>
    <tbody id="data_body">
    </tbody>
</table>
<p>&nbsp;</p>

</div>
</div>


<script type="text/javascript"> 
 var basePath = "${basePath}";
 
//搜索
$('#check_data_btn').click(function () {
   //$("#form").submit();
   var day = $('input[name=day]').val();
   if(day==''){
	    alert('请选择时间!');
	    return;
	}
   var tbody=window.document.getElementById("data_body"); 
   $.ajax({  
		url:basePath+"/getMarketReportData.action?day="+day,  
		type : "post",
		async : true,  
		dataType : "json",  
		success : function(datas) {	
			if(datas.code==1){
		 		//var list = datas.market;
		 		$("#data_body").empty();
	     		var str = "";  
	            var data = datas.market;  
	            for (i in data) {  
	                str += "<tr class='text-center'>" +  
	                "<td>" + data[i].account_number + "</td>" +  
	                "<td>" + data[i].public_num + "</td>" +  
	                "<td>" + data[i].read_num + "</td>" +  
	                "<td>" + data[i].transmit_ratio + "</td>" +  
	                "<td>" + data[i].collection_ratio + "</td>" +  
	                "<td>" + data[i].comment_ratio + "</td>" +  
	                "<td>" + data[i].care_num + "</td>" +  
	                "<td>" + data[i].fans_num + "</td>" +  
	                "<td>" + data[i].public_hb + "</td>" +  
	                "<td>" + data[i].read_hb + "</td>" +  
	                "<td>" + data[i].transmit_hb + "</td>" +  
	                "<td>" + data[i].collection_hb + "</td>" +  
	                "<td>" + data[i].comment_hb + "</td>" +  
	                "<td>" + data[i].care_hb + "</td>" +  
	                "</tr>";  
	            }  
	            tbody.innerHTML = str;  
			}
		},
		error:function(){
			alert("查询失败");
		}
	});
});

//导出
$('#check_data_export').click(function () {
	var day = $('input[name=day]').val();
   	if(day==''){
	    alert('请选择时间!');
	    return;
	}
   	$('#downloadcsv').attr('src',basePath+"/getMarketExcel.action?day="+day); 
    /* $.ajax({  
 		url:basePath+"/getMarketExcel.action?day="+day,  
 		type : "post",
 		async : true,  
 		dataType : "json" 
 	}); */
});
 
</script> 
</body>
</html>