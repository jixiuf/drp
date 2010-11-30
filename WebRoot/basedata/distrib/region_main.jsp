<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
 
<html>
    <head>    <base href="<%=basePath%>">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0"> 
        <link rel="stylesheet" href="css/drp.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>分销商维护</title>
        <script type="text/javascript">
        var basePath="<%=basePath%>";

    function addRegion() { 
        window.self.location = "<%=basePath%>basedata/distrib/region_add.jsp?pid=<s:property value='#action.region.id'/>&name="+escape("<s:property value='#action.region.name'/>");  
    }
    
    function modifyRegion() {
        window.self.location =  "<%=basePath%>basedata/distrib/region_mod.jsp?id=<s:property value='#action.region.id'/>&name="+escape("<s:property value='#action.region.name'/>");  
    }
    
    function deleteRegion() {
        var pid='<s:property value='#action.region.parent.id'/>';
        if (pid ==null||pid==""){
       alert("根节点 不能删 ");
       return ;
        }
      var confirm_delete=  confirm("删除地区将会连同其下的所有分销商删除，\n如果其他板块使用了此地区名，则此地区名会被保留,而只会删除其下的所有分销商，\n您真的确定执行删除操作???");
      if(confirm_delete){
          var url=basePath+"basedata/distrib/distrib!deleteDistribRegion?region.id=<s:property value='#action.region.id'/>";
          window.self.location=url;
         
      }
        
    }
    
    function addClient() {
    	   var url=basePath+"basedata/distrib/distrib!preAddDistrib?region.id=<s:property value='#action.region.id'/>";
        window.self.location = url;
    }
    function  deleteAllDistribs(){
    	   var url=basePath+"basedata/distrib/distrib!deleteAllDistribsOfRegion?region.id=<s:property value='#action.region.id'/>";
        window.self.location = url;

    }
</script>
    </head>

    <body class="body1">
        <form id="clientForm" name="clientForm" method="post" action="">
            <table width="95%" border="0" cellspacing="0" cellpadding="0"
                height="8">
                <tr>
                    <td width="522" class="p1" height="2" nowrap="nowrap">
                        <img src="img/mark_arrow_02.gif" width="14" height="14" />
                        &nbsp;
                        <b>基础数据管理&gt;&gt;分销商维护&gt;&gt;地区维护</b>
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
                            当前区域名称：
                        </div>
                    </td>
                    <td width="410">
                        <label>
                            <input name="name" type="text" class="text1" id="name" value="<s:property value='#action.region.name'/>"
                                 readonly="readonly" />
                        </label>
                    </td>
                </tr>
            </table>
            <p></p>
            <label>
                <br />
            </label>
            <hr />
			<table align="center" >
				<tbody>
					<tr>
						<td>
							<input name="btnAddRegion" type="button" class="button1"
								id="btnAddRegion" onClick="addRegion()" value="添加子区域" />&nbsp;&nbsp;
						</td>
						<td>
							<input name="btnDeleteRegion" type="button" class="button1"
								id="btnDeleteRegion" value="删除区域及其下分销商" onClick="deleteRegion()" />&nbsp;&nbsp;&nbsp;
						</td>
						<td>
							<input name="btnModifyRegion" type="button" class="button1"
								id="btnModifyRegion" onClick="modifyRegion()" value="修改区域" />
						</td>
					</tr>
					<tr  >
						<td  ><br/>
							<input name="btnAddClient" type="button" class="button1"
								id="btnAddClient" onClick="addClient()" value="添加分销商" />
						</td>
						<td  ><br/>
                            <input name="btnAddClient" type="button" class="button1"
                                id="btnDeleteAllDistribs" onClick="deleteAllDistribs()" value="删除此地区名下的所有分销商" />
                        </td>
					</tr>
				</tbody>
			</table>
		</form>
        <s:debug></s:debug>
    </body>
</html>

