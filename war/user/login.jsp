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
<script type='text/javascript' src='js/jquery.js'></script>
<script type='text/javascript' >
String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}
function validateEmail() {
    var val=$("#email").val();
     if (val.trim()==""){
          $("#emailMsg").empty().append("<font color='red'> 不能为空</font>");
          return false;
          } else if (val.search(/^(\w)+@(\w|-)+\.(\w+)$/) ==-1    ){
              $("#emailMsg").empty().append("<font color='red'> 邮箱格式不对</font>");
          return false;
          }
     $("#emailMsg").empty() ;
     return true;

}
function validatePasswrod() {
    var val=$("#password").val();
     if (val.trim()==""){
          $("#passwordMsg").empty().append("<font color='red'>不能为空</font>");
          return false;
          } else if (val.length<6){
               $("#passwordMsg").empty().append("<font color='red'>至少6位</font>");
          return false;
          }
     $("#passwordMsg").empty() ;
     return true;
}
$(
    function() {
        $("#email").select();
        $("#email").blur(validateEmail);
        $("#password").blur(validatePasswrod);
  



		//刷新
		$("#reload").click(function() {
			window.self.location.reload();
		});

	}

	)
</script>

  </head>
  
  <body> 

   <font style=""   >系统管理&gt;&gt;<a href='user/user!listUser'>用户登陆</a>&gt;&gt</font> 
   <input type='button'  class="button1" value='刷新当前页' id='reload' > 
  <hr/>
        <div align="center">
        无论您是主动还是被动来到此页面，请先登陆
            <h1 align="center">
           用户登陆
            </h1>
            <div id='errorMsg' style='color:red'>
            
            <s:iterator value="#action.actionErrors"  var='error' >
    
             <s:property value="error" /><br/>
            
            </s:iterator>
        </div>
		 
           <form action="user/user!login" method="post" > 
				<span style="color: red">*</span> email&nbsp;&nbsp;:
				<input name="user.email"
				 id='email'
					type="text">
				<span id="emailMsg"></span>(登陆时使用 )
				<br>
				<span style="color: red">*</span>密&nbsp;&nbsp;码：
				<input name="user.password"
					id='password' type="password">
				<span id="passwordMsg"></span>
				<br>
				<input type="hidden" name='dynamicURL' value='<s:property value="#parameters['dynamicURL']"/>'>
				<input type="submit" id='submit' value='提交'>
				<input type='reset' value='重置'>
			</form>
		
		</div>
 
    </body>
</html>
