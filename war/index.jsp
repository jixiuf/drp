<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>分销管理系统</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">



	</head>
	<frameset border="0"  rows="15%,85%" framespacing="0"   >
	 
			<frame scrolling="no" noresize="noresize" src="logo.html">
 

		<frameset frameborder="no"  border="1px" cols="18%,82%">

			<frame name="left" src="frame/left.jsp">
			<frameset  frameborder="no" rows="5%,95%"   >
			     <frame  scrolling="no" noresize="noresize" src="head2.jsp">
				<frame name="right" frameborder="0"  src="frame/right.jsp">
		
			</frameset>


		</frameset>
	</frameset>
	<noframes>
		<body>
		</body>
	</noframes>

</html>
