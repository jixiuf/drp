<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>添加分销商</title>
        <link rel="stylesheet" href="css/drp.css">
      
         <base href="<%=basePath%>">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
             <link rel="stylesheet" type="text/css" href="css/dtree.css">
    </head>

	<body class="body1">
		<form name="form1" method='post'
			action="basedata/distrib/distrib!addDistrib">
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
                        <td width="522" class="p1" height="2" nowrap="nowrap">
                            <img src="img/mark_arrow_03.gif" width="14" height="14">
                            &nbsp;
                            <b>基础数据管理&gt;&gt;分销商维护&gt;&gt;添加分销商</b>
                        </td>
                    </tr>
                </table>
           <div  align="left" style='color:red'>  <s:property value='#action.actionMessages'/></div>   
                <hr width="97%" align="center" size="0">
   <table width="95%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="22%" height="29">
                            <div align="right">
                                <font color="#FF0000">*</font>分销商代码:&nbsp;
                            </div>
                        </td>
                        <td width="78%">
                            <input name="distrib.clientno" id='clientno'  type="text" class="text1" value="<s:property value='#action.distrib.clientno'/>"  size="32" maxlength="32">
                        </td>
                    </tr>
                    <tr>
                        <td height="26">
                            <div align="right">
                                <font color="#FF0000">*</font>分销商名称:&nbsp;
                            </div>
                        </td>
                        <td>
                            <input name="distrib.name" type="text" class="text1" value='<s:property value="#action.distrib.name"/>' id="clientName" size="40" maxlength="40">
                        </td>
                    </tr>
                    <tr>
                        <td height="15">
                            <div align="right">
                                <font color="#FF0000">*</font>分销商类型:&nbsp;
                            </div>
                        </td>
                        <td>
                            <select name="distrib.distribType.id" class="select1"
                                id="clientLevel">
                                <s:iterator value="#request.distribTypes" var='clientType'>
                                    <s:if test='%{#action.distrib.distribType.id == #clientType.id}'>
                                        <option selected="selected"
                                            value="<s:property value='#clientType.id'/>">
                                            <s:property value='#clientType.name' />
                                        </option>
                                    </s:if>
                                    <s:else>
                                        <option 
                                            value="<s:property value='#clientType.id'/>">
                                            <s:property value='#clientType.name' />
                                        </option>
                                    </s:else>
                                </s:iterator>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td height="26">
                            <div align="right">
                                银行帐号:&nbsp;
                            </div>
                        </td>
                        <td>
                            <input name="distrib.bankno" type="text" class="text1" value='<s:property value="#action.distrib.bankno"/>'
                                id="bankAcctNo" size="10" maxlength="10">
                        </td>
                    </tr>
                    <tr>
                        <td height="26">
                            <div align="right">
                                联系电话:&nbsp;
                            </div>
                        </td>
                        <td>
                            <input name="distrib.phone" type="text" class="text1" value='<s:property value="#action.distrib.phone"/>'
                                id="contactTel" size="10" maxlength="10">
                        </td>
                    </tr>
                    <tr>
                        <td height="26">
                            <div align="right">
                                地址:&nbsp;
                            </div>
                        </td>
                        <td>
                            <input name="distrib.address" type="text" class="text1" value='<s:property value="#action.distrib.address"/>'
                                id="address" size="10" maxlength="10">
                        </td>
                    </tr>
                    <tr>
                        <td height="26">
                            <div align="right">
                                联系人 :&nbsp;
                            </div>
                        </td>
                        <td>
                            <input name="distrib.contactor" type="text" class="text1" value='<s:property value="#action.distrib.contactor"/>'
                                size="10" maxlength="10"> 
                        </td>
                    </tr>
                    <tr>
                        <td height="26">
                            <div align="right">
                                邮编 :&nbsp;
                            </div>
                        </td>
                        <td>
                            <input name="distrib.zipcode" type="text" class="text1" size="10"  value='<s:property value="#action.distribzipcode"/>'
                                maxlength="10">
                        </td>
                    </tr>
                </table>
			 
				<input name="distrib.region.id" type="hidden" value="<s:property value='#action.region.id'/>">
				<hr width="97%" align="center" size="0">
				<div align="center">
					<input name="btnAdd" class="button1" type="submit"  id="btnAdd"
						value="添加">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="btnBack" class="button1" type="button" id="btnBack"
						value="返回" onclick="history.go(-1)" />
				</div>
			</div>
		</form>
		<s:debug></s:debug>
	</body>



</html>
