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
       <title>物料详细信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" href="css/drp.css">
		<script language="javascript">

		function list(){
			var url="basedata/material/material!listMaterials?pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
			   window.self.location=url;
		}
        </script>
	</head>

    <body class="body1">
        <form name="itemForm" target="_self" id="itemForm">
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
                            <b>基础数据管理&gt;&gt;物料维护&gt;&gt;物料详细信息</b>
                        </td>
                    </tr>
                </table>
                <hr width="97%" align="center" size="0">
                  <table width="95%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td height="29">
                            <div align="right">
                                物料代码:&nbsp;
                            </div>
                        </td>
                       
                        <td>
                        
                          <s:property value="#action.m.no"/>
                        </td>
                    </tr>
                    <tr>
                        <td height="26">
                            <div align="right">
                                物料名称:&nbsp;
                            </div>
                        </td>
                        <td>
                         <s:property value="#action.m.name"/>
                        </td>
                    </tr>
                    <tr>
                        <td height="26">
                            <div align="right">
                                物料规格:&nbsp;
                            </div>
                        </td>
                        <td>
                        <s:property value="#action.m.guige"/>
                        </td>
                    </tr>
                    <tr>
                        <td height="26">
                            <div align="right">
                                物料型号:&nbsp;
                            </div>
                        </td>
                        <td>
                        <s:property value="#action.m.xinghao"/>
                        </td>
                    </tr>
                    <tr>
                        <td height="26">
                            <div align="right">
                                类别:&nbsp;
                            </div>
                        </td>
                        <td>
                        <s:property value="#action.m.materialType.name"/>
                        </td>
                    </tr>
                    <tr>
                        <td height="26">
                            <div align="right">
                                计量单位:&nbsp;
                            </div>
                        </td>
                        <td>
                           <s:property value="#action.m.materialItemUnit.name"/>
                        </td>
                    </tr>
                    <tr>
                        <td height="74">
                            <div align="right">
                                图片:&nbsp;
                            </div>
                        </td>
                        <td>
                    
                            <s:if test="#action.m.pictFilename!=null">
                                <img src='upload/<s:property value="#action.m.pictFilename"/>'
                                    alt="" width="85" height="49">
                            </s:if>
                        </td>
                    </tr>
                  
                </table>
                <hr width="97%" align="center" size="0">
                <div align="center">
                   
                             <input name="btnBack" class="button1" type="button" id="btnBack"
                        value="返回" onClick="window.close();list()">
                </div>
            </div>
        </form>
        <s:debug></s:debug>
    </body>
</html>
