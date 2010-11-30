<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>分销商级别分布图</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="css/drp.css" />
		<script type='text/javascript' src='js/jquery.js'></script>
		<script type='text/javascript'>
	    var basePath='<%=basePath%>';
	var flag=false;
	$(function() {
		var firstLevelRegion = $("#firstLevelRegion");
		firstLevelRegion.live("change", function() {
			var urlStr =basePath+"report/report!listChildRegion?region.id="+ firstLevelRegion.val();

			   $.ajax( {
			        url : urlStr,
			        cache : false,
			        success : function(html) {
			            $("#secondLevelArea").empty().append($(html));
			            reportDistribLevel();
			        }
			    });

			 
		} );
		//下载word 文档
		$("#currentPage").click(
				function (){
					   var firstLevelRegion = $("#firstLevelRegion");
				        var secondLevelRegion = $("#secondLevelRegion");
				        var regionId = secondLevelRegion.val();
				       
				     var    paramStr =$(":radio[name='paramStr'][checked='true']").val();
				        if (regionId == "-1") {
				            regionId = firstLevelRegion.val();
				        }
				        var urlStr = basePath
				                + "report/report!reportDistribLevelInWord?region.id="
				                + regionId+"&paramStr="+paramStr+"&randomNum="+Math.random();
		             //   $("#download").get(0).src=urlStr;
				        window.open(urlStr, '选择物料'     );
				}
				);

	$("#btnQuery").click( reportDistribLevel );
  $("#secondLevelRegion").live("change",reportDistribLevel);
	$("input[type='radio'][name='paramStr']").change(  reportDistribLevel);
	})

	function  reportDistribLevel(){
		var firstLevelRegion = $("#firstLevelRegion");
		var secondLevelRegion = $("#secondLevelRegion");
		var regionId = secondLevelRegion.val();
		var image = $("#image");

	 var 	paramStr =$(":radio[name='paramStr'][checked='true']").val();
 
	
		if (regionId == "-1") {
			regionId = firstLevelRegion.val();
		}
		var urlStr = basePath
				+ "report/report!reportDistribLevel?region.id="
				+ regionId+"&paramStr="+paramStr+"&randomNum="+Math.random();
		image.get(0).src = urlStr;
	}
</script>
	</head>
	<body class="body1">
		<form name="clientLevelChartForm" target="_self"
			id="clientLevelChartForm">
			<div align="center">
				<table width="95%" border="0" cellspacing="0" cellpadding="0"
					height="35">
					
					<tr >
						<td width="522" class="p1" height="14" nowrap>
							<img src="img/mark_arrow_02.gif" width="14">
							<b>统计/报表管理&gt;&gt;分销商级别分布图</b>
						</td>
					</tr>
				</table>
				<hr width="100%" align="center" size=0>
				<table width="95%" height="20" border="0" cellpadding="0"
					cellspacing="0">
					<tr>

						<td width="14%" height="30">
							<div align="right">
								分销大区:&nbsp;
							</div>
						</td>
						<td width="13%">
							<select name="firstLevelRegion" class="select1"
								id="firstLevelRegion">
								<option value="-1"  selected="selected"  >
									--全部--
								</option>
								<s:iterator value="#request.firstLevelRegions"
									var="firstLevelRegion">
									<option value="<s:property value="#firstLevelRegion.id"/>">
										<s:property value="#firstLevelRegion.name" />
									</option>
								</s:iterator>
							</select>
						</td>

						<td width="13%">
							<div align="right">
								分销省:&nbsp;
							</div>
						</td>
						 
						<td width="16%"    id="secondLevelArea" >
								<select name="secondLevelRegion" class="select1"   
								id="secondLevelRegion">
								<option value="-1"   selected="selected"  >
									--全部--
								</option>
								<s:iterator value="#request.secondLevelRegions"
									var="secondLevelRegion">
									<option value="<s:property value="#secondLevelRegion.id"/>">
										<s:property value="#secondLevelRegion.name" />
									</option>
								</s:iterator>
							</select>
						</td> 
						<td width="16%">
							<input name="paramStr" type="radio" value="pie" 
							checked="true"	 >
							饼图
							<input name="paramStr" type="radio" value="bar">
							柱状图
						</td>
						<td width="28%">
							<input name="btnQuery" type="button" class="button1"
								id="btnQuery" value="查询"> <br/>
						</td>
					</tr>
				</table>
				<hr width="100%" align="center" size=0>
			</div>	
			<table width="95%" border="0" cellpadding="0" cellspacing="0" align="left" >
				<tr align="left">
				<div align="left" >
				 将各个大区及各分销省市分销商分布图导出为Word文档 
                    <input type="button" id='currentPage' class="button1"   value="导出" >
					<td  align="left">
						<img alt="loading ..."    scrolling="auto" id='image' name="image" align="middle"
							src="report/report!reportDistribLevel?region.id=-1&paramStr=pie"  
						 ></img>
						
					</td>
				
					
					</div>
				</tr>
			 
			</table>
    
			 
		</form>
	</body>
</html>

