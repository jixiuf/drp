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
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>分销商详细信息</title>
        <link rel="stylesheet" type='text/css' href="css/drp.css">
  </head>
<body class="body1">
        <form name="clientForm" target="_self" id="clientForm">
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
                            <b>基础数据管理&gt;&gt;分销商维护&gt;&gt;分销商详细信息</b>
                        </td>
                    </tr>
                </table>
                <hr width="97%" align="center" size="0">
                <table width="95%" height="267" border="0" cellpadding="0"
                    cellspacing="0">
                    <tr>
                        <td width="22%">
                            <div align="right">
                                分销商代码:&nbsp;
                            </div>
                        </td>
                        <td width="78%">
                        <s:property value="#action.distrib.clientno"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div align="right">
                                分销商名称:&nbsp;
                            </div>
                        </td>
                        <td>
                        <s:property value="#action.distrib.name"/>
                        </td>
                    </tr>
                               <tr>
                        <td>
                            <div align="right">
                                分销商所在地:&nbsp;
                            </div>
                        </td>
                        <td>
                        <s:property value="#action.client.region.name"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div align="right">
                                分销商类型:&nbsp;
                            </div>
                        </td>
                        <td>
                      <s:property value="#action.distrib.distribType.name"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div align="right">
                                银行帐号:&nbsp;
                            </div>
                        </td>
                        <td>
                             <s:property value="#action.distrib.bankno"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div align="right">
                                联系电话:&nbsp;
                            </div>
                        </td>
                        <td>
                         <s:property value="#action.distrib.phone"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div align="right">
                                地址:&nbsp;
                            </div>
                        </td>
                        <td>
                        <s:property value="#action.distrib.address"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div align="right">
                                邮编:&nbsp;
                            </div>
                        </td>
                        <td>
                       <s:property value="#action.distrib.zipcode"/>
                        </td>
                    </tr>
                </table>
                <hr width="97%" align="center" size="0">
                <div align="center">
                    <input name="btnBack" class="button1" type="button" id="btnBack"
                        value="返回" onClick=" window.close();history.go(-1)">
                </div>
            </div>
        </form>
    </body>
</html>
