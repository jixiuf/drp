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
	<link rel="stylesheet" type="text/css" href="css/drp.css" >
	 <title>添加区域节点</title>
	</head>
  
 
  <body class="body1">
  <form  method='post' action="<%=basePath%>basedata/distrib/distrib!addRegion">
            <table width="95%" border="0" cellspacing="0" cellpadding="0"
                height="8">
                <tr>
                    <td width="522" class="p1" height="2" nowrap="nowrap">
                        <img src="img/mark_arrow_03.gif" width="14" height="14" />
                        &nbsp;
                        <b>基础数据管理&gt;&gt;分销商维护&gt;&gt;添加区域节点</b>
                    </td>
                </tr>
            </table>
            <hr width="97%" align="center" size="0" />
            <p></p>
            <p></p>
            <table width="95%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="213">
                        <div align="right">
                            <font color="#FF0000">*</font>区域名称：
                        </div>
                    </td>
                    <td width="410">
                        <label>
                            <input name="region.name" type="text" class="text1" id="name" />
                        </label>
                    </td>
                </tr>
                 <tr>
                    <td width="213">
                        <div align="right">
                          &nbsp; &nbsp;父区域名称：
                        </div>
                    </td>
               
                    <td width="410">
                        <label> 
                        <s:property escape="false" value='#parameters.name'/> 
                        </label>
                    </td>
                </tr>
            </table>
               <input type='hidden'  name='region.parent.id' value='<s:property value="#parameters.pid"/>' >
            <p></p>
            <label>
                <br />
            </label>
            <hr />
            <p align="center">
                <input name="btnAdd" class="button1" type="submit" value="添加" />
                &nbsp;&nbsp;&nbsp;&nbsp;
                <input name="btnBack" class="button1" type="button" value="返回"
                    onclick="history.go(-1)" />
            </p>
        </form>
    </body>
</html>
