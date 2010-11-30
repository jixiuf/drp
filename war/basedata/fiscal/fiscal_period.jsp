<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
		<title>会计核算期间维护</title>
		<link rel="stylesheet" type='text/css' href="css/drp.css">
		<script src="js/jquery.js" type='text/javascript'></script>
		<script type='text/javascript'>


	$( function() {
		//分页的四个按钮
		 
		$("#btnTopPage").click( function() {
             window.self.location="basedata/fiscalPeriod!listFiscalPeriod?pm.pageSize=<s:property value='#session.pm.pageSize'/>&pm.pageNow=1&pm.rowCount=<s:property value='#session.pm.rowCount'/>";
		});
		  $("#btnPreviousPage").click( function() {
	             window.self.location="basedata/fiscalPeriod!listFiscalPeriod?pm.pageSize=<s:property value='#session.pm.pageSize'/>&pm.pageNow=<s:property value='#session.pm.pageNow-1'/>&pm.rowCount=<s:property value='#session.pm.rowCount'/>";
	        });
		  $("#btnNextPage").click( function() {
              window.self.location="basedata/fiscalPeriod!listFiscalPeriod?pm.pageSize=<s:property value='#session.pm.pageSize'/>&pm.pageNow=<s:property value='#session.pm.pageNow+1'/>&pm.rowCount=<s:property value='#session.pm.rowCount'/>";
         });
	      
		  $("#btnBottomPage").click( function() {
			  var url="basedata/fiscalPeriod!listFiscalPeriod?pm.pageSize=<s:property value='#session.pm.pageSize'/>&pm.pageNow=<s:property value='#session.pm.pageCount'/>&pm.rowCount=<s:property value='#session.pm.rowCount'/>";
	            window.self.location=url;
	        });
	         $("#btnAdd").click( function() {
	              var url="<%=basePath%>basedata/fiscal/fiscal_period_add.jsp?pm.pageSize=<s:property value='#session.pm.pageSize'/>&pm.pageNow=<s:property value='#session.pm.pageNow'/>&pm.rowCount=<s:property value='#session.pm.rowCount'/>";
	                window.self.location=url;
	            });
	            $("#btnModify").click( function() {
	                var radios=$(":radio[name='fp.id']") ;

	                var count=0;
                    for (var i=0;i<radios.length;i++){
                     if    (radios[i].checked)
                           count++;
                    }
                    if(count==0){
                        alert("请选择要修改的数据");
		              return false;

                    }
                    return true;
	                });
		   
	})
</script>
	</head>
	<body class="body1">
		<form name="fiscalYearPeriodForm"    action="basedata/fiscalPeriod!preModFiscalPeriod"  method="get"   >
			<div align="center">
				<table width="95%" border="0" cellspacing="0" cellpadding="0"
					height="35">
					<tr>
						<td class="p1" height="18" nowrap="nowrap">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td width="522" class="p1" height="17" nowrap="nowrap">
							<img src="img/mark_arrow_02.gif" width="14" height="14">
							&nbsp;
							<b>基础数据管理&gt;&gt;会计核算期间维护</b>
						</td>
					</tr>
				</table>
				<hr width="100%" align="center" size="0">
			</div>
			<span style='color:red'><s:property value='#request.msg'/></span>
			<table width="95%" height="20" border="0" align="center"
				cellspacing="0" class="rd1" id="toolbar">
				<tr>
					<td width="49%" class="rd19">
						<font color="#FFFFFF">查询列表</font>
					</td>
					<td width="27%" nowrap="nowrap" class="rd16">
						<div align="right"></div>
					</td>
				</tr>
			</table>
			<table width="95%" border="1" cellspacing="0" cellpadding="0"
				align="center" class="table1">
				<tr>
					
					<td width="123" class="rd6">
						核算年
					</td>
					<td width="146" class="rd6">
						核算月
					</td>
					<td width="188" class="rd6">
						开始日期
					</td>
					<td width="204" class="rd6">
						结束日期
					</td>
					<td width="172" class="rd6">
						核算期状态
					</td><td width="79" class="rd6">
                        选择
                    </td>
				</tr>
				<!-- 取得所有会计核算期 -->
				<s:set var="rows" value="0" />
				<s:iterator value="#request.fps" var="fiscalPeriod" status="st">
					<tr>
				
						<td class="rd8">
							<s:property value="#fiscalPeriod.year" />
						</td>
						<td class="rd8">
							<s:property value="#fiscalPeriod.month" />
						</td>
						<td class="rd8">
							<s:date format="yyyy-MM-dd" name="#fiscalPeriod.startdate" />

						</td>
						<td class="rd8">
							<s:date format="yyyy-MM-dd" name="#fiscalPeriod.enddate" />

						</td>
						<td class="rd8">
							<s:if test='#fiscalPeriod.status=="Y"'>
							可用
							</s:if>
							<s:else>
							不可用
							</s:else>

						</td>     <td class="rd8">
                        
                                <input type="radio" name="fp.id" 
                                    value="<s:property value='#fiscalPeriod.id'  />">
                        
                    
                        </td>
					</tr>
					<s:if test="#st.last">
						<s:set name="rows" value="#st.count" />
					</s:if>
				</s:iterator>

				<s:if test="rows<#session.pm.pageSize">
					<s:iterator begin="1" end='#session.pm.pageSize-#rows'>
						<tr>
							<td class="rd8">
								&nbsp;
							</td>
							<td class="rd8">
								&nbsp;
							</td>
							<td class="rd8">
								&nbsp;
							</td>
							<td class="rd8">
								&nbsp;
							</td>
							<td class="rd8">
								&nbsp;
							</td>
							<td class="rd8">
								&nbsp;
							</td>
						</tr>

					</s:iterator>
				</s:if>



			</table>      
		  <input type='hidden' name='pm.pageNow' value="<s:property value='#session.pm.pageNow'/>">
                <input type='hidden' name='pm.pageSize'  value="<s:property value='#session.pm.pageSize'/>">
                <input type='hidden' name='pm.rowCount' value="<s:property value='#session.pm.rowCount'/>">
			<table width="95%" height="30" border="0" align="center"
				cellpadding="0" cellspacing="0" class="rd1">
				<tr>
					<td nowrap="nowrap" class="rd19" height="2">
						<div align="left">
							<font color="#FFFFFF">&nbsp;共&nbsp;<s:property
									value='#session.pm.pageCount' />&nbsp;页</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<font color="#FFFFFF">当前第</font>&nbsp;
							<font color="#FF0000"><s:property
									value='#session.pm.pageNow' />
							</font>&nbsp;
							<font color="#FFFFFF">页</font>
						</div>
					</td>
					<td nowrap="nowrap" class="rd19">
						<div align="right">
							<input class="button1" type="button" id="btnTopPage"
								value="|&lt;&lt; " title="首页">
							<input class="button1" type="button" id="btnPreviousPage"
								value=" &lt;  " title="上页">
							<input class="button1" type="button" id="btnNextPage"
								value="  &gt; " title="下页">
							<input class="button1" type="button" id="btnBottomPage"
								value=" &gt;&gt;|" title="尾页">
							<input type="button" class="button1" id="btnAdd" value="添加" />
							<input class="button1" type="submit" id="btnModify" value="修改">
						</div>
					</td>
				</tr>
			</table>
			<p>
				&nbsp;
			</p>
		</form>
	</body>
</html>
