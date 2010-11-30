<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
		<title>物料维护</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">

		<link rel="stylesheet" type='text/css' href="css/drp.css">
		      <script src="js/jquery.js" type='text/javascript'></script>
        <script type='text/javascript'>


    $( function() {
        $("#searchInput").select();
        //分页的四个按钮
         
        $("#btnTopPage").click( function() {
             window.self.location="basedata/material/material!listMaterials?pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=1&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
        });
          $("#btnPreviousPage").click( function() {
                 window.self.location="basedata/material/material!listMaterials?pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow-1'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
            });
          $("#btnNextPage").click( function() {
              window.self.location="basedata/material/material!listMaterials?pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow+1'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
         });
          
          $("#btnBottomPage").click( function() {
              var url="basedata/material/material!listMaterials?pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageCount'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
                window.self.location=url;
            });
             $("#btnAdd").click( function() {
                  var url="<%=basePath%>basedata/material/material!preAddMaterial?pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
                    window.self.location=url;
                });
                $("#btnModify").click( function() {
                    var checkboxs= $(":checkbox[name='m.id'][checked='true']") ;
                    var count =checkboxs.length;
                    if(count==0){
                        alert("请选择要修改的数据");
                      return false;

                    }else if (count>1){
                    	  alert("一次只能修改一个");
                    	   return false;
                    }
                    return true;
                    });
                
                //复制修改后添加
                $("#btnCopy").click( function() {
                    var checkboxs= $(":checkbox[name='m.id'][checked='true']") ;
                    var count =checkboxs.length;
                    if(count==0){
                        alert("请选择要复制的数据");
                        return ;
                    }else if (count>1){
                          alert("一次只能复制一个");
                          return ;
                    } 
                
                     var url="<%=basePath%>basedata/material/material!preAddMaterial?m.id="+checkboxs[0].value+"&pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
                    window.self.location=url;
                  
                    });
                $("#btnDelete").click(
                        function(){
                     var checkedUser=$(":checkbox[name='m.id'][checked='true']");
                if (checkedUser.length==0){
                    alert('请选择要删除的物料');
                    return false;
                } else{
                    var paramStr="";
                    for(var i=0;i<checkedUser.length-1;i++){
                        paramStr+=(checkedUser.get(i).value+",");
                    }
                    paramStr+=(checkedUser.get(checkedUser.length-1).value);
                   var deleteConfirm=window.confirm("真的要删除这些物料?");
                   if(deleteConfirm){
                       var url='<%=basePath%>basedata/material/material!delMaterials?delMaterialsIds='+paramStr+"&pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
                         window.self.location=url;
                   }
                }
                     
                        }
                        );

/**
 * 图片上传
 */                
                $("#btnUpload").click( function() {
                    var checkboxs= $(":checkbox[name='m.id'][checked='true']") ;
                  var count =checkboxs.length;
              
               
                    if(count==0){
                        alert("请选择要处理的物料");
                        return ;
                    }else if (count>1){
                          alert("一次只能处理一个");
                          return ;
                    } 
                     var url="<%=basePath%>basedata/material/material!preUpload?m.id="+checkboxs.val()+"&pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
                    window.self.location=url;
                  
                    });

                //选择所有，
                $(":checkbox[name='selectAll']").click(
                        function(){
                        	var ischecked=$(this).attr('checked');
                            if(ischecked){
                                $(":checkbox[name='m.id']").attr('checked',true);
                            }else{
                                  $(":checkbox[name='m.id']").attr('checked',false);
                            }
                        }
	                 );
                $("#reload").click(
                        function(){

                            var url="<%=basePath%>basedata/material/material!listMaterials?pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
                           window.self.location=url;
                         
                        }

                        );
    	$("#searchInput").keyup(searchSubmit);
		$("#searchInput").blur(searchSubmit);
		  $("#btnQuery").click(searchSubmit);
		
	
	});

	function searchSubmit( ) {
		var th = $("#searchInput");
		var empty=/\s+/g;
		if(empty.test(th.val())){
			return ;
 
		}
		var urlStr = "basedata/material/material!searchMaterials?m.name="
				+ encodeURI(encodeURI(th.val()))
				+ "&pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>"
		$.ajax( {
			url : urlStr,
			cache : false,
			success : function(html) {
				$("#Searchresult").empty().append(html);
			}
		});

	}
</script>
 

	</head>
  
<body class="body1">
        <form name="itemForm"  onsubmit="return false"  id='searchForm' method='post'   >
     
            <div align="center">
                <table width="95%" border="0" cellspacing="2" cellpadding="2">
                    <tr>
                        <td>
                            &nbsp;
                        </td>
                    </tr>
                </table>
				<table width="95%" border="0" cellspacing="0" cellpadding="0"
					height="8">
					<tr>
						<td width="522" class="p1" height="2" nowrap="nowrap">
							<img src="img/mark_arrow_02.gif" alt="我" width="14" height="14">
							&nbsp;
							<b>基础数据管理&gt;&gt;物料维护</b> <span style='color: red'> <s:iterator value="#action.actionMessages" var='msg'> <s:property value='#msg' /> </s:iterator> </span>
						</td>
					</tr>
				</table>
			

				<hr width="97%" align="center">
                <table width="95%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="17%" height="29">
                            <div align="left">
                                物料代码/名称:
                            </div>
                        </td>
                        <td width="45%">
                            <input name="m.name" type="text" class="text1"
                                id="searchInput" size="50" maxlength="50">
                        </td>
                        <td width="36%" align="left">
                            <div align="left">
								<input name="btnQuery" type="button" class="button1"
									id="btnQuery" value="查询">
							</div>
                        </td>
                    </tr>
                </table>
            </div>
            </form>
               <form name="itemForm2"  id='form2'  method='post' action="basedata/material/material!preModMaterial"  >
               <div id='Searchresult'>
            <table width="95%" border="0" cellspacing="0" cellpadding="0"
                class="rd1" align="center">
                <tr>
                    <td nowrap="nowrap" height="10" class="p2">
                        物料信息
                    </td>
                    <td nowrap="nowrap" height="10" class="p3">
                        &nbsp;
                    </td>
                </tr>
            </table>
            <table width="95%" border="1" cellspacing="0" cellpadding="0"
                align="center" class="table1">
                <tr>
                  
                    <td width="155" class="rd6">
                        物料代码
                    </td>
                    <td width="155" class="rd6">
                        物料名称
                    </td>
                    <td width="155" class="rd6">
                        物料规格
                    </td>
                    <td width="155" class="rd6">
                        物料型号
                    </td>
                    <td width="138" class="rd6">
                        类别
                    </td>
                    <td width="101" class="rd6">
                        计量单位
                    </td>  <td width="35" class="rd6">
                        <input type="checkbox" name="selectAll"  id='selectAll'>
                    </td>
                </tr> <s:set var="rows" value="0" />
                <s:iterator value="#request.materials" var='material' status="st">
                <tr>   
                    <td class="rd8">
                        <a href="#"
                            onClick="window.open('basedata/material/material!listSingleMaterial?m.id=<s:property value="#material.id" />', '物料详细信息', 'width=400, height=400, scrollbars=no');return false;"><s:property value="#material.no"/></a>
                    </td>
						<td class="rd8">
							<s:property value="#material.name" />
						</td>
						<td class="rd8">
							<s:property value="#material.guige" />
						</td>
						<td class="rd8">
							<s:property value="#material.xinghao" />
						</td>
						<td class="rd8">
							<s:property value="#material.materialType.name" />
						</td>
						<td class="rd8">
							<s:property value="#material.materialItemUnit.name" />
						</td>
						<s:if test="#st.last">
							<s:set name="rows" value="#st.count" />
						</s:if>
						<td class="rd8">
							<input type="checkbox" name="m.id" value='<s:property value="#material.id" />' class="checkbox1">
						</td>

					</tr>   </s:iterator>
				<s:if test="rows<#action.pm.pageSize">
					<s:iterator begin="1" end='#action.pm.pageSize-#rows'>
						<tr>
							<td class="rd8">
								&nbsp;
							</td>
							<td class="rd8">
								&nbsp;
							</td>
							<td class="rd8">
								&nbsp;
							</td>
							<td class="rd8">
								&nbsp;
							</td>
							<td class="rd8">
								&nbsp;
							</td>
							<td class="rd8">
								&nbsp;
							</td>
						</tr>

					</s:iterator>
				</s:if>

				 
            </table></div>
			<input type='hidden' name='pm.pageNow'
				value='<s:property value="#action.pm.pageNow"/>'>
			<input type='hidden' name='pm.pageSize'
				value='<s:property value="#action.pm.pageSize"/>'>
			<input type='hidden' name='pm.rowCount'
				value='<s:property value="#action.pm.rowCount"/>'>
			<table width="95%" height="30" border="0" align="center"
                cellpadding="0" cellspacing="0" class="rd1">
                <tr>
                    <td nowrap="nowrap" class="rd19" height="2">
                        <div align="left">
                            <font color="#FFFFFF">&nbsp;共&nbsp;<s:property
                                    value='#action.pm.pageCount' />&nbsp;页</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <font color="#FFFFFF">当前第</font>&nbsp;
                            <font color="#FF0000"><s:property
                                    value='#action.pm.pageNow' />
                            </font>&nbsp;
                            <font color="#FFFFFF">页</font>
                        </div>
                    </td>
                    <td nowrap="nowrap" class="rd19">
                        <div align="right">
                            <input class="button1" type="button" id="btnTopPage"
                                value="|&lt;&lt; " title="首页">
                            <input class="button1" type="button" id="btnPreviousPage"
                                value=" &lt;  " title="上页">
                            <input class="button1" type="button" id="btnNextPage"
                                value="  &gt; " title="下页">
                            <input class="button1" type="button" id="btnBottomPage"
                                value=" &gt;&gt;|" title="尾页">
                      <input name="btnAdd" type="button" class="button1" id="btnAdd"
                                value="添加" >
                            <input  class="button1" type="submit"
                                id="btnModify" value="修改" >
                                   <input  class="button1" type="button"
                               title='先复制一份，并在其基础上修改，添加' id="btnCopy" value="复制添加" >
                                  <input   class="button1" type="button"
                                id="btnDelete" value="删除"  >
                            <input  class="button1" type="button"
                                id="btnUpload" value="上传图片"  >
                        </div>
                    </td>
                </tr>   
            </table>
         <div align="right">  <input  align="right" type='button'   id ='reload' class="button1" value='刷新  ' >&nbsp;&nbsp;&nbsp;&nbsp;</div>
         
        </form> 
        <s:debug></s:debug>
    </body>
</html>
