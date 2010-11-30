<%@ page language="java" pageEncoding="UTF-8"%>
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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<title>添加流向单维护</title>
		<link rel="stylesheet" href="css/drp.css">
		<script type='text/javascript' src="js/jquery.js"></script>


		<script type="text/javascript">
		  var basePath='<%=basePath%>';
	$(function() {
		$("#flowCardForm").submit(function() {
			var returnFlag = false;
			var adjustCounts = $(".adjustCount");
			for ( var i = 0; i < adjustCounts.length; i++) {
				var th = $(adjustCounts.get(i));
				th.val(th.val().trim());
				if ((th.val() == "" || isNaN(th.val()))) {
					th.focus();
					alert("操作数量不能为空,且必须是数字（可以是小数）");
					returnFlag = true;
					break;
				}

			}

			if (returnFlag) {
				return false;

			}
			return true;

		});

	});

	String.prototype.trim = function() {
		return this.replace(/(^\s*)|(\s*$)/g, "");
	}
</script>

	</head>

	<body class="body1">
		<div align="center">
			<form name="flowCardForm" action="inventory/flowcard!adjustFlowcard"
				method="post" id="flowCardForm">
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
							<b>分销商库存管理&gt;&gt;流向单审核&gt;&gt;调整</b>
						</td>
					</tr>
				</table>
				<hr width="95%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="11%" height="29">
							<div align="right">
								流向单号:
							</div>
						</td>
						<td width="10%">
						   <s:if test="#fc.fushen!=null"><SPAN  style="color:red">*</span></s:if>
                            <a href="#"
                                onClick="window.open(basePath+'inventory/flowcard!showFlowcasrdDetail?fc.id=<s:property value="#action.fc.id" />&pm.pageSize=<s:property value="#action.pm.pageSize"/>&pm.pageNow=<s:property value="#action.pm.pageNow"/>&pm.rowCount=<s:property value="#action.pm.rowCount"/>', '流向单详细信息', 'width=850, height=400, scrollbars=no');return false">
                                <s:property value="#action.fc.flowcardNo"/></a>

						</td>
						<td width="13%">
							<div align="right">
								供方分销商代码:&nbsp;
							</div>
						</td>
						<td width="10%">
							<s:property value="#action.fc.distrib.clientno" />
						</td>
						<td width="13%">
							<div align="right">
								供方分销商名称:&nbsp;
							</div>
						</td>
						<td width="17%">
							<s:property value="#action.fc.distrib.name" />
						</td>
						<td width="8%">
							<div align="right">
								入库日期:
							</div>
						</td>
						<td width="18%">
							<s:property value="#action.fc.recordDate" />
						</td>
					</tr>
				</table>
				<table width="95%" border="1" align="center" cellpadding="0"
					cellspacing="0" class="table1" id="tblFlowCardDetail">
					<tr bordercolor="#FFFFFF" bgcolor="#FFFFFF">
						<td height="22" width="86" class="rd6">
							<div align="left">
								需方客户代码
							</div>
						</td>
						<td height="22" width="100" class="rd6">
							<div align="left">
								需方客户名称
							</div>
						</td>
						<td height="22" width="70" class="rd6">
							<div align="left">
								物料代码
							</div>
						</td>
						<td height="22" width="87" class="rd6">
							<div align="left">
								物料名称
							</div>
						</td>
						<td class="rd6">
							规格
						</td>
						<td class="rd6">
							型号
						</td>
						<td class="rd6">
							计量单位
						</td>
						<td class="rd6">
							原始数量
						</td>
						<td class="rd6">
							<font color="#FF0000">*</font>调整数量
						</td>
						<td class="rd6">
							<div align="left">
								<font color="#FF0000">*</font>调整原因
							</div>
						</td>
					</tr>
					<s:iterator value="#action.fc.flowcardDetails" var="fcd"
						status="st">
						<tr>
							<td class="rd8">
							<input type="hidden" value="<s:property value="#fcd.id"/>" name="fc.flowcardDetails[<s:property value="#st.index"/>].id" >
								<s:property value="#fcd.client.clientno" />
							</td>
							<td class="rd8">
								<s:property value="#fcd.client.name" />
							</td>
							<td class="rd8">
								<s:property value="#fcd.material.no" />
							</td>
							<td class="rd8">
								<s:property value="#fcd.material.name" />
							</td>
							<td class="rd8">
								<s:property value="#fcd.material.guige" />
							</td>
							<td class="rd8">
								<s:property value="#fcd.material.xinghao" />
							</td>
							<td class="rd8">
								<s:property value="#fcd.material.materialType.name" />
							</td>
							<td class="rd8">
								<s:property value="#fcd.materialCount" />
							</td>

							<td class="rd8">
								<input class='adjustCount' name="fc.flowcardDetails[<s:property value="#st.index"/>].adjustCount" type="text" class="text1" id="adjustQty"
									value='<s:property value="#fcd.adjustCount"/>' size="10"
									maxlength="10">
							</td>
							<td class="rd8">
								<input name="fc.flowcardDetails[<s:property value="#st.index"/>].adjustReason" type="text" class="text1"
									value='<s:property value="#fcd.adjustReason"/>'
									id="adjustReason" size="40" maxlength="40">
							</td>
							
							
						</tr>
					</s:iterator>
				</table>
				<p>
					<p>
						<p>
							<hr width="95%" align="center" size=0>
							<p>
								<input name="btnModify" type="submit" id="btnModify" value="修改">
								&nbsp;
								<input name="btnBack" type="button" id="btnBack"
									onClick="history.go(-1)" value="返回">
							</p>
							<p>
								&nbsp;
							</p>
							<p>
								&nbsp;


							</p>
						 
							<input type='hidden' name="fc.id" value='<s:property value="#action.fc.id" />'>
							<input type='hidden' name='pm.pageSize'
								value="<s:property value='#parameters["pm.pageSize"]'/>">
							<input type='hidden' name='pm.pageNow'
								value="<s:property value='#parameters["pm.pageNow"]'/>">
							<input type='hidden' name='pm.rowCount'
								value="<s:property value='#parameters["pm.rowCount"]'/>">
			</form>
			<p>
				&nbsp;
			</p>
		</div>
	</body>
</html>
