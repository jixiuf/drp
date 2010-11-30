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

		<title>My JSP 'fiscal_period_add.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
		<title>修改会计核算期间</title>
		<link rel="stylesheet" href="../style/drp.css">
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>/js/date/WdatePicker.js"></script>
		<base href="<%=basePath%>">
		<script src="js/jquery.js" type='text/javascript'>
	
</script>
		<script type='text/javascript'>
	function getKeyCode(e) {
		if (window.event) {
			return window.event.keyCode;
		} else if (e.which) {//firefox 
			return e.which;
		}
	}

	$(function() {
		$("#fiscalYear").keypress(function(event) {
			var code = getKeyCode(event);
			if ((code >= 48 && code <= 57) || code == 8) {//如果按下的不是数字键,并且不是删除键
					//	setKeyCode(event,48);  //相当于什么键都 没按
					//  alert(event.type);
					return true;

				} else {
					return false; //jquery  return false 阻止浏览器默认事件
				}
			});
		$("#fiscalMonth").keypress(function(event) {
			var code = getKeyCode(event);
			if ((code >= 48 && code <= 57) || code == 8) {//如果按下的不是数字键,并且不是删除键
					//    setKeyCode(event,48);  //相当于什么键都 没按
					//  alert(event.type);
					return true;

				} else {
					return false; //jquery  return false 阻止浏览器默认事件
				}
			});

		$("#startdate").blur(function() {
			var val = $(this).val();
			var reg = /\d{4}-\d{1,2}-\d{1,2}/;
			if (!reg.test(val)) {
				$("#startDateMsg").empty().append('日期格式不对');
			} else {
				$("#startDateMsg").empty();
			}
		});
		$("#enddate").blur(function() {
			var val = $(this).val();
			var reg = /\d{4}-\d{1,2}-\d{1,2}/;
			if (!reg.test(val)) {
				$("#endDateMsg").empty().append('日期格式不对');
			} else {
				$("#endDateMsg").empty();
			}
		});

	}

	)
</script>
	</head>

	<body class="body1">
		<form name="fiscalYearPeriodForm" target="_self" method='post'
			action='basedata/fiscalPeriod!addFiscalPeriod'
			id="fiscalYearPeriodForm">
			<div align="center">
				<table width="95%" border="0" cellspacing="2" cellpadding="2">
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
				</table>
				<table width="95%" border="0" cellspacing="0" cellpadding="0"
					height="25">
					<tr>
						<td width="522" class="p1" height="25" nowrap="nowrap">
							<img src="img/mark_arrow_03.gif" width="14" height="14">
							&nbsp;
							<b>基础数据管理&gt;&gt;会计核算期间维护&gt;&gt;添加 </b>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size='0'>
				<input name="fp.id" type="hidden"
					value="<s:property value='#action.fp.id'/>">
				<span style='color: red'> <s:property value="#request.msg" />
				</span>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="22%" height="29">
							<div align="right">
								核算年:&nbsp;
							</div>
						</td>
						<td width="78%">
							<input name="fp.year" type="text" class="text1"
								value="<s:property value='#action.fp.year'/>" id="fiscalYear"
								size="10" maxlength="4">
							&nbsp;&nbsp;格式:4位数字的年份，如2008
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								核算月:&nbsp;
							</div>
						</td>
						<td>
							<input name="fp.month" type="text" class="text1"
								value="<s:property value='#action.fp.month'/>" id="fiscalMonth"
								size="10" maxlength="10">
							&nbsp;&nbsp;格式:2位数字的月份 ，如12
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>开始日期:&nbsp;
							</div>
						</td>
						<td>
							<label>
								<input type="text" name="fp.startdate" onClick=
	WdatePicker();;
size="10" maxlength="10" id='startdate'
									value="<s:date  format='yyyy-MM-dd' name='#action.fp.startdate'/>">
								&nbsp;&nbsp;格式:2008-12-01
								<span id='startDateMsg' style='color: red'></span>
							</label>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>结束日期:&nbsp;
							</div>
						</td>
						<td>
							<input name="fp.enddate" type="text" id="enddate"
								onClick=
	WdatePicker();;
value="<s:date  format='yyyy-MM-dd' name='#action.fp.enddate'/>"
								size="10" maxlength="10">
							<span id='endDateMsg' style='color: red'></span>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>是否可用:&nbsp;
							</div>
						</td>
						<td>
							<select name='fp.status'>

								<option selected="selected" value='Y'>
									Y
								</option>
								<option value='N'>
									N
								</option>
							</select>
						</td>
					</tr>
				</table>

				<input type='hidden' name='pm.pageNow'
					value="<s:property value='#session.pm.pageNow'/>">
				<input type='hidden' name='pm.pageSize'
					value="<s:property value='#session.pm.pageSize'/>">
				<input type='hidden' name='pm.rowCount'
					value="<s:property value='#session.pm.rowCount'/>">

				<hr width="97%" align="center" size='0'>
				<div align="center">
					<input class="button1" type="submit" id="btnModify" value="添加">
					&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;
					<input class="button1" type="button" id="btnBack" value="返回"
						onClick=
	history.go(-1);
>
					<input class="button1" type="button" id="btnReload" value="刷新"
						onClick=
	self.location.reload();
>
				</div>
			</div>
		</form>
		<s:debug></s:debug>
	</body>
</html>
