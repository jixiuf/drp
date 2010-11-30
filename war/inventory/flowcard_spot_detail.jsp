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
		<title>添加流向单抽查描述</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="css/drp.css" />
		<script type='text/javascript' src='js/jquery.js'></script>
		<script type="text/javascript">
		$(
				function(){
					$("#spotDesc").select();

				} );
		
		</script>
	</head>

	<body class="body1">
		<form target="_self" action="inventory/flowcard!spotFlowcard"
			method="post">
			<div align="center">
				<table width="95%" border="0" cellspacing="2" cellpadding="2">
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
				</table>
				<table width="95%" border="0" cellspacing="0" cellpadding="0"
					height="8">
					<tr>
						<td width="522" class="p1" height="2" nowrap>
							<img src="img/mark_arrow_03.gif" width="14" height="14">
							&nbsp;
							<b>分销商库存管理&gt;&gt;流向单抽查&gt;&gt;添加</b><span style='color: red'>
								<s:iterator value="#action.actionMessages" var='msg'>
									<s:property value='#msg' />
								</s:iterator> <s:iterator value='#action.actionErrors' var='error'>
									<s:property value="#error" />
								</s:iterator> </span>
						</td>

					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" height="110" border="0" cellpadding="0"
					cellspacing="0">
					<tr>
						<td height="29">
							<div align="right">
								流向单号:
							</div>
						</td>
						<td>
							<s:property value="#action.fc.flowcardNo" />
						</td>
					</tr>
					<tr>
						<td width="22%" height="29">
							<div align="right">
								抽查人:&nbsp;
							</div>
						</td>
						<td width="78%">
							<s:property value="#session.LoginedUser.name" />
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								抽查时间:&nbsp;
							</div>
						</td>
						<td>
							 
							<s:date name="#request.now" format="yyyy-MM-dd hh:mm:ss"  />
 
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>抽查结果描述:&nbsp;
							</div>
						</td>
						<td>
							<input  name="fc.spotDesc" type="text" class="text1" value='<s:property  value="#action.fc.spotDesc" />'
								id="spotDesc" size="100" maxlength="100">
						</td>
					</tr>
				</table>
    <input type='hidden' name='fc.id'
                    value='<s:property value="#action.fc.id"/>'>
				<input type='hidden' name='pm.pageNow'
					value='<s:property value="#parameters['pm.pageNow']"/>'>
				<input type='hidden' name='pm.pageSize'
					value='<s:property value="#parameters['pm.pageSize']"/>'>
				<input type='hidden' name='pm.rowCount'
					value='<s:property value="#parameters['pm.rowCount']"/>'>
				<hr width="97%" align="center" size=0>
				<div align="center">
					<input name="btnAdd" class="button1" type="submit" id="btnAdd"
						value="添加">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="btnBack" class="button1" type="button" id="btnBack"
						value="返回" onClick=
	history.go(-1);
>
				</div>
			</div>
		</form>
	</body>
</html>
