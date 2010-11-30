<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
	$(function() {
		setInterval(function() {
			var userMsg = $("#userMsg");
			$.ajax( {
				url : "user/login_head_reload.jsp",
				cache : false,
				
				success : function(html) {
				userMsg.empty().append($(html));
				}
			});

		}, 3000)
	}

	);
</script>
	</head>
	<body bgcolor="gray" >
		<div id='showArea' style="position: absolute; top: 0; right: 30px"
			align="right">
			<span id="userMsg">  当前用户:
    <span style="color: red;"> <s:if
            test="#session.LoginedUser==null"> 未登陆 </s:if> <s:else>
            <s:property value='#session.LoginedUser.name' />
        </s:else> </span>
 </span> 
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a target='right' href="about.html" >关于&nbsp;</a>
 
    <s:a>帮助&nbsp;</s:a>
    <s:a action="/user/user!logout"> 注销</s:a>
    <a href="user/user!proLogin" target="right">登陆</a>
      
		</div>
	</body>
</html>
