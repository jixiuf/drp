<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
		<link rel="stylesheet" type="text/css" href="css/dtree.css">
		<script type='text/javascript'  src='js/dtree.js'></script>
  </head>
    <frameset rows="*"  cols="187,*"   framespacing="0" frameborder="yes"
        border="1">
        <frame src="basedata/distrib/distrib!listDistrib"   name="distribTreeFrame"
        
            scrolling="auto" id="clientTreeFrame" />
        <frame src="basedata/distrib/distrib_main.jsp" name="distribDispAreaFrame"
            id="clientDispAreaFrame" />
    </frameset>

    <noframes>
        <body>
        </body>
    </noframes>
</html>
