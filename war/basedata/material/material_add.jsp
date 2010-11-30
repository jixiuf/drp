<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>添加物料</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="description" content="This is my page">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		
		<link rel="stylesheet" href="css/drp.css">
		<script  type='text/javascript' src="js/client_validate.js"></script>
		    <script type='text/javascript' src="js/jquery.js"></script>
        <script type='text/javascript'>
                $( function() {
                        $("#itemNo").select();
                    }
 
                 )
        </script>
		
	</head>

	<body class="body1">
        <form name="itemForm" target="_self" id="itemForm" method='post'  action="basedata/material!addMaterial"    >
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
                        <td width="522" class="p1" height="25" nowrap="nowrap" >
                            <img src="img/mark_arrow_03.gif" width="14" height="14">
                            &nbsp;
                            <b>基础数据管理&gt;&gt;物料维护&gt;&gt;添加</b> <span style='color: red'> <s:iterator value="#action.actionMessages" var='msg'> <s:property value='#msg' /> </s:iterator> </span>
                        </td>
                    </tr>
                </table>   
                <hr width="97%" align="center" size="0">
                <table width="95%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="22%" height="29">
                            <div align="right">
                                <font color="#FF0000">*</font>物料代码:&nbsp;
                            </div>
                        </td>
                        <td width="78%">
                            <input name="m.no" type="text" class="text1" id="itemNo" value='<s:property value="#action.m.no"/>' size="32" maxlength="32">
                        </td>
                    </tr>
                    <tr>
                        <td height="26">
                            <div align="right">
                                <font color="#FF0000">*</font>物料名称:&nbsp;
                            </div>
                        </td>
                        <td>
                            <input name="m.name" type="text" class="text1" id="itemName" value='<s:property value="#action.m.name"/>'
                                size="20" maxlength="20">
                        </td>
                    </tr>
                    <tr>
                        <td height="26">
                            <div align="right">
                                物料规格:&nbsp;
                            </div>
                        </td>
                        <td>
                            <label>
                                <input name="m.guige" type="text" class="text1" id="spec" value='<s:property value="#action.m.guige"/>'
                                    size="30" maxlength="30">
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td height="26">
                            <div align="right">
                                物料型号:&nbsp;
                            </div>
                        </td>
                        <td>
                            <input name="m.xinghao" type="text" class="text1" id="pattern" value='<s:property value="#action.m.xinghao"/>'
                                size="20" maxlength="20">
                        </td>
                    </tr>
                    <tr>
                        <td height="26">
                            <div align="right">
                                <font color="#FF0000">*</font>类别:&nbsp;
                            </div>
                        </td>
                        <td>
							<select name="m.materialType.id" class="select1" >
								<s:iterator value="#request.materialTypes" var='materialType'>
								<s:if
                                        test='%{#action.m.materialType.id == #materialType.id}'>
										<option value="<s:property value='#materialType.id'/>"
											selected="selected">
											<s:property value='#materialType.name' />
										</option>
									</s:if>
									<s:else>
										<option value="<s:property value='#materialType.id'/>">
											<s:property value='#materialType.name' />
										</option>
									</s:else>

								</s:iterator>
							</select>
						</td>
                    </tr>
                    <tr>
                        <td height="26">
                            <div align="right">
                                <font color="#FF0000">*</font>计量单位:&nbsp;
                            </div>
                        </td>
                        <td>
							<select name="m.materialItemUnit.id" class="select1" id="category">
								<s:iterator value="#request.materialItemUnits" var='materialItemUnit'>
									<s:if
										test='%{#action.material.materialItemUnit.id == #materialItemUnit.id}'>
										<option value="<s:property value='#materialItemUnit.id'/>"
											selected="selected">
											<s:property value='#materialItemUnit.name' />
										</option>
									</s:if>
									<s:else>
										<option value="<s:property value='#materialItemUnit.id'/>">
											<s:property value='#materialItemUnit.name' />
										</option>
									</s:else>
								</s:iterator>
							</select>
						</td>
                    </tr>
      
                </table>
                  <input type='hidden' name='m.pictFilename' value="<s:property value='#action.m.pictFilename'/>">
                <input type='hidden' name='pm.pageNow' value='<s:property value='#parameters["pm.pageNow"]'/>'>
                <input type='hidden' name='pm.pageSize' value='<s:property value='#parameters["pm.pageSize"]'/>'>
                <input type='hidden' name='pm.rowCount' value='<s:property value='#parameters["pm.rowCount"]'/>'>
                <hr width="97%" align="center" size='0'>
                <div align="center">
                    <input name="btnAdd" class="button1" type="submit" id="btnAdd"
                        value="添加">
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <input name="btnBack" class="button1" type="button" id="btnBack"
                        value="返回" onClick="history.go(-1)">     <input name="btnBack" class="button1" type="button" id="btnBack"
                        value="刷新" onClick="window.self.location.reload()">
                </div>
            </div>
        </form>
        <s:debug></s:debug>
    </body>
</html>
