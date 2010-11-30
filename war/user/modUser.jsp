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
    
    <title>My JSP 'addUser.jsp' starting page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

<script type='text/javascript' src='js/jquery.js'></script>

<script type='text/javascript' >


String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}
function validateUserName(){
       var val=$("#userName").val();
       if (val.trim()==""){
            $("#userNameMsg").empty().append("<font color='red'>用户名不能为空</font>");
            return false;
            }else if (val.search(/(^\d+)/)>-1){
            
            $("#userNameMsg").empty().append("<font color='red'>用户名不能以数字开头</font>");
            return false;
            }else if (val.length<5){
                 $("#userNameMsg").empty().append("<font color='red'>用户名至少5位</font>");
            return false;
            }else if (val.length>10){
                $("#userNameMsg").empty().append("<font color='red'>用户名最多为10位</font>");
                return false;
                }else {
                $("#userNameMsg").empty().append("<font color='green'>恭喜，此名可用</font>");
                return true;
            }
}

function validateEmail() {
    var val=$("#email").val();
     if (val.trim()==""){
          $("#emailMsg").empty().append("<font color='red'> 不能为空</font>");
          return false;
          } else if (val.search(/^(\w)+@(\w|-)+\.(\w+)$/) ==-1    ){
              $("#emailMsg").empty().append("<font color='red'> 邮箱格式不对</font>");
          return false;
          }else{
        	  $("#emailMsg").empty();
        	  return true;
          }

}
function validatePasswrod() {
    var val=$("#password").val();
     if (val.trim()==""){
          $("#passwordMsg").empty().append("<font color='red'>不能为空</font>");
          return false;
          } else if (val.length<6){
               $("#passwordMsg").empty().append("<font color='red'>至少6位</font>");
          return false;
         
          }else if (val.search(/\d+/)>-1  && val.search(/[a-zA-Z]+/)>-1 &&val.search(/[_!$@-]/)>-1 ) {
              $("#passwordMsg").empty().append("密码强度<font color='maroon'>：强</font>");
          return true;
          }else if (  val.search(/\d+/)>-1  &&  val.search(/[a-zA-Z]+/)>-1   ) {
              $("#passwordMsg").empty().append("密码强度<font color='blue'>：中</font>");
          return true;
          } else{
              $("#passwordMsg").empty().append("密码强度<font color='green'>：弱</font>");
          return true;
          }
  
         
   

}

function passwordMatch() {
    var password2=$("#password2").val();
    var password=$("#password").val();
    if (password2!=password){
          $("#passwordMsg2").empty().append("<font color='red'> 密码不匹配，请重新输入</font>");
          return false;
    }else{
          $("#passwordMsg2").empty().append("<font color='green'> 恭喜，密码 匹配 。。。</font>");
          return true;
    }
        
}
$(


    function() {
        $("#userName").select();
        $("#userName").blur(validateUserName);
        $("#email").blur(validateEmail);
        $("#password").blur(validatePasswrod);
        $("#password2").blur(passwordMatch);
        $("#submit").click(
                function() {
                    if (!(validateUserName() && validateEmail()
                            && validatePasswrod() && passwordMatch())) {
                        alert("请修改错误之处后，再提交 ");
                        return false;

                    }
                });

          //刷新
        $("#reload").click(
                function(){
                    window.self.location.reload();
                }
                );

    }


 

    )
</script>

  </head>
  
  <body> 

   <font style=""   >系统管理&gt;&gt;<a href='user/user!listUser'>用户维护</a>&gt;&gt;修改用户</font>
   <input type='button'  value='刷新当前页' id='reload' > 
  <hr/>
        <div align="center">
        
            <h1 align="center">
               修改用户
            </h1>
            <div id='errorMsg' style='color:red'>
            
            <s:iterator value="#action.actionErrors"  var='error' >
    
             <s:property value="error" /><br/>
            
            </s:iterator>
        </div>
            <form action="user/user!modUser"
                target="_self">
                      <input name="user.id" type="hidden" id='userid' value='<s:property value='#action.user.id'/>'  >
                <span style="color: red">*</span>用户名： 
                <input name="user.name" type="text" id='userName' maxlength="10" value='<s:property value='#action.user.name'/>'  ><span id='userNameMsg'></span>
                <br>
                <span style="color: red">*</span> email&nbsp;&nbsp;:
                <input name="user.email"  value='<s:property value='#action.user.email'/>' id='email'  type="text"><span id="emailMsg"></span>(登陆时使用  )
                <br>
                <span style="color: red">*</span>密&nbsp;&nbsp;码：
                <input name="user.password"  value='<s:property value='#action.user.password'/>' id='password' type="password"><span id="passwordMsg"></span>
                <br>
                <span style="color: red">*</span> 验证密码
                <input name="password2"  value='<s:property value='#action.user.password'/>'  id='password2'  type="password"><span id="passwordMsg2"></span>
                <br>
                联系&nbsp;方式
                <input name="user.phone"    value='<s:property value='#action.user.phone'/>' type="text">
                <br>
                <input type="hidden" name='pageModel.pageSize' value="<s:property value='#action.pageModel.pageSize' />" >
                <input type="hidden" name='pageModel.pageNow' value="<s:property value='#action.pageModel.pageNow' />" >
                <input type="hidden" name='pageModel.rowCount' value="<s:property value='#action.pageModel.rowCount' />" >
    <input   type="submit" id ='submit' value='提交'>
    <input type='reset' value='重置' >
            </form>
        </div>

<s:debug></s:debug>
    </body>
</html>
