<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
 
<html>
   <title>流向单明细信息</title>
    <head>  <base href="<%=basePath%>">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
          <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css"   href="css/drp.css" />
    <script type='text/javascript'  src='js/jquery.js'></script>
    </head>

    <body class="body1">
 
        <div align="center">
            <form name="flowCardForm" id="flowCardForm">
                <table width="95%" border="0" cellspacing="2" cellpadding="2">
                    <tr>
                        <td>&nbsp;
                            
                      </td>
                    </tr>
                </table>
                <table width="95%" border="0" cellspacing="0" cellpadding="0"   
                    height="8">
                    <tr>
                        <td width="522" class="p1" height="2" nowrap>
                            <img src="img/mark_arrow_03.gif" width="14" height="14">
                            &nbsp;
                             <b>流向单详细信息</b>
                        </td>
                    </tr>
                </table>
                <hr width="97%" align="center" size=0>
                <table width="95%" border="0" cellpadding="0" cellspacing="0"  align="center"   >
                    <tr>
                        <td width="8%" nowrap="nowrap">
                            <div align="right">
                                &nbsp;&nbsp;&nbsp;流向单号:&nbsp;
                            </div>
                        </td>
                        <td width="7%" nowrap="nowrap">
                        <s:property value="#action.fc.flowcardNo"/>
                        </td>
                        <td width="13%" nowrap="nowrap">
                            <div align="right">
                                &nbsp;&nbsp;&nbsp;供方分销商代码:&nbsp;
                            </div>
                        </td>
                        <td width="6%">
                              <s:property value="#action.fc.distrib.id"/>
                        </td>
                        <td width="15%" nowrap="nowrap">
                            <div align="right">
                                <div align="right">
                                    &nbsp;&nbsp;&nbsp;供方分销商名称:&nbsp;
                                </div>
                            </div>
                        </td>
                        <td width="18%" nowrap="nowrap">
                    <s:property value="#action.fc.distrib.name"/>
                        </td>
                        <td width="10%" nowrap="nowrap">
                            &nbsp;&nbsp;&nbsp;录入日期:&nbsp;                       </td>
                        <td width="23%" nowrap="nowrap">
                          <s:property value="#action.fc.recordDate"/> 
                        </td>
                    </tr>
                </table>
                <hr width="97%" align="center" size=0>
                <table width="95%" border="1" cellspacing="0" cellpadding="0"
                    align="center" class="table1">
                    <tbody>
                    
                    <tr>
                        <td width="89" class="rd6">
                            需方客户代码
                        </td>
                        <td width="116" class="rd6">
                            需方客户名称
                        </td>
                        <td width="74" class="rd6">
                            物料代码
                        </td>
                        <td width="136" class="rd6">
                            物料名称
                        </td>
                        <td width="120" class="rd6">
                            规格
                        </td>
                        <td width="103" class="rd6">
                            型号
                        </td>
                        <td width="68" class="rd6">
                            计量单位
                        </td>
                        <td width="69" class="rd6">
                            操作数量
                        </td>
                    </tr>
                    
                        <s:set var="rows" value="0" />
                    <s:iterator status="st" var ='fcd' value='#action.fc.flowcardDetails'>
 
                    <tr>
                        <td class="rd8">
                           <s:property value="#fcd.client.id"/>
                        </td>
                        <td class="rd8">
                         <s:property value="#fcd.client.name"/>
                        </td>
                        <td class="rd8">
                            <s:property value="#fcd.material.id"/>
                        </td>
                        <td class="rd8">
                           <s:property value="#fcd.material.name"/>
                        </td>
                        <td class="rd8">
                          <s:property value="#fcd.material.guige"/>
                        </td>
                        <td class="rd8">
                             <s:property value="#fcd.material.xinghao"/>
                        </td>
                         <td class="rd8">
                             <s:property value="#fcd.material.materialItemUnit.name"/>
                        </td>
                         <td class="rd8">
                             <s:property value="#fcd.materialCount"/>
                        </td>
                    </tr>
                    
                    
 
                        <s:if test="#st.last">
                            <s:set name="rows" value="#st.count" />
                        </s:if>
                    </s:iterator>
                    <s:if test="rows<#action.pm.pageSize">
                    <s:iterator begin="1" end='#action.pm.pageSize-#rows'>
                    
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
                     <td class="rd8">
                            &nbsp;
                        </td>
                  
                        </tr>

                    </s:iterator>
                </s:if>
                 
          </tbody>
                </table>
                <p>
                    <input name="btnClose" type="button" id="btnClose"
                        onClick="window.close()" value="关闭">
                </p>
            </form>
        </div>
    </body>
</html>

