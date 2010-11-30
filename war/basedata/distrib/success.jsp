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
        <link rel="stylesheet" type="text/css" href="css/drp.css">
        <title>删除区域节点</title>
        <script type="text/javascript">
     function onload_(){
     setTimeout( 'my_reload()',1000);
     }
     //刷新父帧
     function my_reload(){
            var url="<%=basePath%>basedata/distrib/distrib_frame.jsp";
            window.parent.location=url;
     }

     </script>
    </head>


    <body onload='onload_()' class="body1">
 
    <div id='msg' style='color:red;'  ><s:property value='#action.actionMessages'/> </div>
    <hr/>
    两秒后自动跳转，刷新页面!!!1
     
    </body>
</html>
