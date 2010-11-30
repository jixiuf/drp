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
		<title>物料维护</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="css/drp.css">
		<script src="js/jquery.js"></script>
		<script language="javascript">
	
</script>
	</head>


	<body class="body1">

        <form name="itemForm" target="_self" method='post' action="basedata/material/material!doUpload" id="itemForm" enctype="multipart/form-data">
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
                            <b>基础数据管理&gt;&gt;物料维护&gt;&gt;上传物料图片</b><span style='color: red'> <s:iterator value="#action.actionMessages" var='msg'> <s:property value='#msg' /> </s:iterator>  <s:iterator value="#action.actionErrors" var='msg'> <s:property value='#msg' /> </s:iterator> </span>
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
                    <tr>
                        <td width="22%" height="29">
                            <div align="right">
                                <font color="#FF0000">*</font>选择图片:&nbsp;
                            </div>
                        </td>
                        <td width="78%">
                            <input name="pictureFile" type="file" class="text1" size="40" maxlength="40">
                        </td>
                    </tr>
                </table>
                   <input name="m.id" type="hidden"   value="<s:property value='#action.m.id' />" > 
                     <input type='hidden' name='pm.pageNow' value='<s:property value='#parameters["pm.pageNow"]'/>'>
                <input type='hidden' name='pm.pageSize' value='<s:property value='#parameters["pm.pageSize"]'/>'>
                <input type='hidden' name='pm.rowCount' value='<s:property value='#parameters["pm.rowCount"]'/>'>
                <hr width="97%" align="center" size="0">
                <div align="center">
                    <input name="btn_upload" class="button1" type="submit"
                        id="btn_upload" value="上传">
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <input name="btnBack" class="button1" type="button" id="btnBack"
                        value="返回" onClick="history.go(-1)">
                </div>
            </div>
        </form>
        <s:debug></s:debug>
    </body>
</html>
