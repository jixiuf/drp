<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head lang="zh_CN">
		<base href="<%=basePath%>">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		     <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="css/dtree.css">
		      <link rel="stylesheet" type="text/css"   href="css/drp.css" />
		<script type='text/javascript' src='js/dom_dtree.js'></script>
		<script type="text/javascript">
		   var basePath='<%=basePath%>';
		   var distribId='<s:property value="#action.distrib.id"/>';
		 function modDistrib(){
			 if (distribId==""){
				 alert("请选择要修改的分销商,再点此按钮");
				 return ;
			 }
              var url=basePath+'basedata/distrib/distrib!preModDistrib?distrib.id='+distribId;
		 	 window.self.location=url;

		}
			
	function deleteDistrib() {

		if (distribId == "") {
			alert("请选择要修改的分销商,再点此按钮");
			return;
		}
		if (window.confirm("真的要删除吗")) {
			var url = basePath + 'basedata/distrib/distrib!delDistrib?distrib.id='
					+ distribId;
			window.self.location = url;
		}
	}
	function showDistribDetail() {
		if (distribId == "") {
			alert("请选择要修改的分销商,再点此按钮");
			return;
		}
		var url = basePath
				+ 'basedata/distrib/distrib!showDistribDetail?distrib.id='
				+ distribId;
		window.self.location = url;

	}
	function addDistrib() {
		if (distribId == "") {
            alert("请点击所属地区,再点此按钮");
            return;
        }
		var url = basePath
				+ "basedata/distrib/distrib!preAddDistrib?region.id=<s:property value='#action.distrib.region.id'/>";
		window.self.location = url;
	}
</script>

  
   
        <title>分销商维护</title>
    </head>

    <body class="body1">

        <form id="clientForm" name="clientForm" method="post" action="">
            <table width="95%" border="0" cellspacing="0" cellpadding="0"
                height="8">
                <tr>
                    <td width="522" class="p1" height="2" nowrap="nowrap">
                        <img src="img/mark_arrow_02.gif" width="14" height="14" />
                        
                        &nbsp;
                        <b>基础数据管理&gt;&gt;分销商维护</b>
                    </td>
                </tr>
            </table>
            <hr width="97%" align="center" size="0" />
            <p/>
            <p/>
            <table width="95%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="213">
                        <div align="right">
                            当前分销商名称：
                        </div>
                    </td>
                    <td width="410">
                        <label>

                          
                            <input name="DistribName" type="text" class="text1"
                                id="DistribName" readonly="readonly"  value="<s:property value='#action.distrib.name'/>" />
                        </label>
                    </td>
                </tr>
            </table>
            <p/>
                <label>
                    <br />
                </label>
            <hr />
            <p align="center">
                <input name="btnModifyClient" type="button" class="button1"
                    id="btnModifyClient" onClick="modDistrib()"
                    value="修改分销商" />
                &nbsp;
                <input name="btindeleteDistrib" type="button" class="button1"
                    id="btindeleteDistrib" value="删除分销商"  onclick="deleteDistrib()"/>
                &nbsp;
                <input name="btnViewDetail" type="button" class="button1"
                    id="btnViewDetail" onClick="showDistribDetail()"
                    value="查看详细信息" />
                &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;
                            <input name="btnaddDistrib" type="button" class="button1"
                                id="btnaddDistrib" onClick="addDistrib()" value="添加分销商" />
                       
            </p>
        </form>
        
            <div id='msg'><s:property value='#action.actionMessages'/> </div>


        <s:debug></s:debug>
    </body>
</html>
