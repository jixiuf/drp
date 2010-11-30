<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<span>
	当前用户:
	<span style="color: red;"> <s:if
			test="#session.LoginedUser==null"> 未登陆 </s:if> <s:else>
			<s:property value='#session.LoginedUser.name' />
		</s:else> </span>
 
 </span>
