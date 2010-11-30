<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'addUserSuccess.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type='text/javascript' src='js/jquery.js'></script>

<script type='text/javascript' >
$(
function(){
	$("#returnBack").click(function(){
		window.self.location="<%=basePath%>user/user!listUser?pageModel.pageNow=1&pageModel.pageSize=3";
	});
	   $("#returnAddUser").click(function(){
	        window.self.location="<%=basePath%>user/addUser.jsp";
	    });
	
}
		)
</script>
  </head>
  
  <body>
  user add success!!!<br/>
  <hr/>
  <input type="button"  value='返回系统管理页面' id='returnBack' >
    <input type="button"  value='返回继续添加新用户' id='returnAddUser' >
  </body>
</html>
