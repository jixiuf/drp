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
    
    <title>My JSP 'listUser.jsp' starting page</title>
    
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

	$( function() {
		//转向添加用户界面
		$("#addUser").click( function() {
			window.self.location="<%=basePath%>user/addUser.jsp?pageModel.pageNow=<s:property value='#action.pageModel.pageNow' />&pageModel.pageSize=<s:property value='#action.pageModel.pageSize' />&pageModel.rowCount=<s:property value='#action.pageModel.rowCount' />";
		});
		//选择所有，
		$(":checkbox[name='all']").click(
				function(){
					var ischecked=$(this).attr('checked');
					if(ischecked){
						$(":checkbox[name='user']").attr('checked',true);
					}else{
						  $(":checkbox[name='user']").attr('checked',false);
					}
				}
				);

		//刷新
		$("#reload").click(
				function(){
					window.self.location.reload();
				}
				);

        $("#modUser").click(
                function(){
             var checkedUser=$(":checkbox[name='user'][checked='true']");
        if (checkedUser.length==0){
            alert('请选择要修改的用户');
            return false;
        }else if (checkedUser.length>1){
        	alert('只能选择一个用户，进行操作');
        	return false;
        }else{
            var userid=checkedUser.get(0).value;
               window.self.location='<%=basePath%>user/user!preModUser?user.id='+userid+"&pageModel.pageNow=<s:property value='#action.pageModel.pageNow' />&pageModel.pageSize=<s:property value='#action.pageModel.pageSize' />&pageModel.rowCount=<s:property value='#action.pageModel.rowCount' />";
        }
                }
                );

        $("#delUser").click(
        	    function(){
             var checkedUser=$(":checkbox[name='user'][checked='true']");
        if (checkedUser.length==0){
            alert('请选择要删除的用户');
            return false;
        } else{
            var paramStr="";
            for(var i=0;i<checkedUser.length-1;i++){
                paramStr+=(checkedUser.get(i).value+",");
            }
            paramStr+=(checkedUser.get(checkedUser.length-1).value);
           var deleteConfirm=window.confirm("真的要删除这些用户?");
           if(deleteConfirm){
               var rowCount=<s:property value='#action.pageModel.rowCount'/>;
               rowCount=rowCount-checkedUser.length;
                 window.self.location='<%=basePath%>user/user!delUser?userIds='+paramStr+"&pageModel.pageNow=<s:property value='#action.pageModel.pageNow' />&pageModel.pageSize=<s:property value='#action.pageModel.pageSize' />&pageModel.rowCount="+rowCount;
           }
        }
             
                }
                );
	})
</script>
  </head>

	<body>

	 <font   >系统管理&gt;&gt;<a href='user/user!listUser?pageModel.pageNow=1&pageModel.pageSize=3&pageModel.rowCount=<s:property value='#action.pageModel.rowCount' />'>用户维护</a> </font>       <input type='button' id='reload' value='刷新'>
	<hr/>
	查询列表                        <span id='page'>
                   <a href="user/user!listUser?pageModel.pageNow=1&pageModel.pageSize=<s:property value='#action.pageModel.pageSize' />&pageModel.rowCount=<s:property value='#action.pageModel.rowCount' />" >首页</a>
                        <a href="user/user!listUser?pageModel.pageNow=<s:property value='#action.pageModel.pageNow-1' />&pageModel.pageSize=<s:property value='#action.pageModel.pageSize' />&pageModel.rowCount=<s:property value='#action.pageModel.rowCount' />" >上一页</a>
                           <a href="user/user!listUser?pageModel.pageNow=<s:property value='#action.pageModel.pageNow+1' />&pageModel.pageSize=<s:property value='#action.pageModel.pageSize' />&pageModel.rowCount=<s:property value='#action.pageModel.rowCount' />" >下一页</a>
            <a href="user/user!listUser?pageModel.pageNow=<s:property value='#action.pageModel.pageCount' />&pageModel.pageSize=<s:property value='#action.pageModel.pageSize' />&pageModel.rowCount=<s:property value='#action.pageModel.rowCount' />" >尾页</a>             
                            <s:property value='#action.pageModel.pageNow' />/<s:property value='#action.pageModel.pageCount' /> 共<s:property value='#action.pageModel.rowCount' />行
       
       </span>
	<form action="">
		<table style="" border='1px'>
			<thead>
		 
				<tr>
				<td>
                        <input type="checkbox"   name='all' id='all' >
                    </td>
					<td>
						用户Id
					</td>
					<td>
						用户名
					</td>
					<td>
						email
					</td>
					<td>
						创建日期
					</td>
					<td>
						联系方式
					</td>

				</tr>

			</thead>
			<tbody>
				<s:iterator var="user" value="#session.users" status="st" >
					<tr>
						<td width="2px" >
							<input type="checkbox" name='user' value='<s:property value="#user.id" />' id='userFlag<s:property value="#st.index" />'  >
						</td>
						<td>
							<s:property value="#user.id" />
						</td>
						<td>
							<s:property value="#user.name" />
						</td>
						<td>
							<s:property value="#user.email" />
						</td>
						<td>
							<s:property value="#user.createdate" />
						</td>
						<td>
							<s:property value="#user.phone" />
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
              <div  >
                        <input type='button' id='addUser' value='添加'>
                        <input type='button' id='modUser' value='修改'>
                        <input type='button' id='delUser' value='删除'>

        </div>  
</form>
	</body>
</html>
