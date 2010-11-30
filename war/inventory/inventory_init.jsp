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
    <link rel="stylesheet" type="text/css"   href="css/drp.css" />
    <script type='text/javascript'  src='js/jquery.js'></script>
       <script type='text/javascript' >
       var basePath='<%=basePath%>';
       var initInventoryPath=basePath+'inventory/inventory!initInventory';
       var selectClientWindow=null;

       $( function() {
           $("#btnTopPage").click( function() {
                window.self.location= initInventoryPath+ "?pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=1&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
           });
             $("#btnPreviousPage").click( function() {
                    window.self.location=initInventoryPath+"?pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow-1'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
               });
             $("#btnNextPage").click( function() {
                 window.self.location=initInventoryPath+"?pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow+1'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
            });
             
             $("#btnBottomPage").click( function() {
                 var url=initInventoryPath+"?pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageCount'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
                   window.self.location=url;
               });
                $("#btnAdd").click( function() {
                     var url=basePath+"inventory/inventory_init_add.jsp?pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
                       window.self.location=url;
                   });
                   $("#btnModify").click( function() {
                       var checkboxs= $(":checkbox[name='it.id'][checked='true']") ;
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
                   
                 
                   $("#btnDelete").click(
                           function(){
                        var checkedUser=$(":checkbox[name='it.id'][checked='true']");
                   if (checkedUser.length==0){
                       alert('请选择要删除的库存清单，可以多选');
                       return false;
                   } else{
                       var paramStr="";
                       for(var i=0;i<checkedUser.length-1;i++){
                           paramStr+=(checkedUser.get(i).value+",");
                       }
                       paramStr+=(checkedUser.get(checkedUser.length-1).value);
                      var deleteConfirm=window.confirm("真的要删除这些库存清单?");
                      if(deleteConfirm){
                          var url='<%=basePath%>inventory/inventory!delInventorys?inventoryIds='+paramStr+"&pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
                            window.self.location=url;
                      }
                   }
                        
                           }
                           );
                   $("#btnSendCheck").click(
                           function(){
                        var checkedUser=$(":checkbox[name='it.id'][checked='true']");
                   if (checkedUser.length==0){
                       alert('请选择要送审的库存清单，可以多选');
                       return false;
                   } else{
                       var paramStr="";
                       for(var i=0;i<checkedUser.length-1;i++){
                           paramStr+=(checkedUser.get(i).value+",");
                       }
                       paramStr+=(checkedUser.get(checkedUser.length-1).value);
                      var deleteConfirm=window.confirm("真的要送审这些库存清单?");
                      if(deleteConfirm){
                          var url='<%=basePath%>inventory/inventory!sendToCheckInventory?inventoryIds='+paramStr+"&pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
                            window.self.location=url;
                      }
                   }
                        
                           }
                           );
 

                   //选择所有，
                   $(":checkbox[name='selectAll']").live("click",
                           function(){
                               var ischecked=$(this).attr('checked');
                               if(ischecked){
                                   $(":checkbox[name='it.id']").attr('checked',true);
                               }else{
                                     $(":checkbox[name='it.id']").attr('checked',false);
                               }
                           }
                        );
                   $("#reload").click(
                           function(){

                               var url=initInventoryPath+"?pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
                              window.self.location=url;
                            
                           }

                           );

           
/**
 * 打开 选择分销商的窗口
 */
	$("#clientNo").add("#clientName") .dblclick(filterClients  );
	$("#btnSelectClient").click(filterClients);
	/**
	 * 打开 选择物料的窗口
	 */
	$("#materialNo").add("#materialName").dblclick(filterMaterials  );
	$("#btnSelectItemNo").click(filterMaterials);

	/**三种情况下触发searchInventory
	*/
	var oldClientNo;
	$("#clientNo").blur(function(){
		var newClientNo=$(this).val();
		if (newClientNo==oldClientNo){
            return ;
		}
		oldClientNo=newClientNo
		searchInventory();
	}
		);
        var oldMaterialNo;
	  $("#materialNo").blur(function(){
	        var newMaterialNo=$(this).val();
	        if (newMaterialNo==oldMaterialNo){
	            return ;
	        }
	        oldMaterialNo=newMaterialNo
	        searchInventory();
	    }
	        );     
	  $("#btnQuery").click(searchInventory);
	  //重置物料
	  $("#btnResetMaterial").click(  

                function(){
                    $("#materialId").val("");
                	$("#materialNo").val("");
                	$("#materialName").val("");
                	searchInventory();
 
                  } 
			  );
	//  重置分销商
      $("#btnResetClient").click(  

              function(){
                  $("#clientNo").val("");
                  $("#clientId").val("");
                  $("#clientName").val("");
                  searchInventory();

                } 
            );
	  

	});

       /**
        * 根据选中的分销商和物料，查出此分销 商下针对 该种物料的所有的存货清单
        */
	  function  searchInventory() {  
    var clientNo=$("#clientNo").val().trim();
    var materialNo=$("#materialNo").val().trim();
    var urlStr = basePath
            + "inventory/inventory!searchInventoryByClientOrMaterial?it.distrib.clientno="+encodeURI(encodeURI(clientNo))+"&it.material.no="+encodeURI(encodeURI(materialNo))+"&pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";

    $.ajax( {
        url : urlStr,
        cache : false,
        success : function(html) {
            $("#searchresult").empty().append($(html));
        }
    });
                };

            

                function filterMaterials() {  
                    var materialNo=$("#materialNo").val();
                    var url = basePath
                            + "inventory/inventory!listMaterials?m.no="+materialNo+"&pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
                    if(selectClientWindow==null ||selectClientWindow.closed){
                        selectClientWindow = window.open(url, '选择物料',
                            'width=700, height=400, scrollbars=no');
                    }else{
                          selectClientWindow.focus();
                    }
                };
                function filterClients()  {
                    var clientNo=$("#clientNo").val();
                    var url = basePath
                            + "inventory/inventory!listDistribs?distrib.clientno="+clientNo+"&pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
                    if(selectClientWindow==null ||selectClientWindow.closed){
                        selectClientWindow = window.open(url, '选择分销商',
                            'width=700, height=400, scrollbars=no');
                    }else{
                          selectClientWindow.focus();
                    }
                };

       String.prototype.trim = function() {
    	    return this.replace(/(^\s*)|(\s*$)/g, "");
    	}
</script>
	</head>
  
<body class="body1">
        <form name="修改inventory"   action="inventory/inventory!preModInventory?pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>"    >
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
                            <img src="img/mark_arrow_02.gif" width="14" height="14">
                            &nbsp;
                            <b>分销商库存管理&gt;&gt;分销商库存数量初始化</b> <span style='color: red'> <s:iterator value="#action.actionMessages" var='msg'> <s:property value='#msg' /> </s:iterator><s:iterator value='#action.actionErrors' var ='error' ><s:property  value="#error" /> </s:iterator> </span>
                        </td>
                    </tr>
                </table>
                <hr width="97%" align="center" size="0">
                <table width="95%" border="0" cellpadding="5" cellspacing="0">
                    <tr   >
                        <td width="14%"  >
                            <div align="right">
                                分销商代码:&nbsp;
                            </div>
                        </td>
                        <td width="22%">
                            <input   type="text" class="text1" id="clientNo"
                                size="32" maxlength="32" readonly="readonly">
                               <input name="it.distrib.id" type="hidden" class="text1" id="clientId" >
                        </td>
                        
                      
                        <td width="14%">
                            <div align="right">
                                分销商名称:&nbsp;
                            </div>
                        </td>
                        <td width="17%">
                            <input  type="text" class="text1"
                                id="clientName" size="32" maxlength="32" readonly="readonly">
                        </td>  <td   >  <input name="btnSelectClient" type="button" id="btnSelectClient"
                                value="筛选..." class="button1"
                                > 
                        
                            <input name="btnResetClient" type="button" class="button1"
                                id="btnResetClient" value="重置"    > 
                        </td>   <td  >
                            <input name="btnQuery"    type="button" class="button1"
                                id="btnQuery" value="查询"    >
                         </td>
                    </tr>
                    <tr>
                        <td  >
                            <div align="right">
                                物料代码:&nbsp;
                            </div>
                        </td>
                        <td>
                            <input type="text" class="text1" id="materialNo" name="it.material.no"
                                size="32" maxlength="32" readonly="readonly">
                                 <input name='it.material.id' type="hidden" class="text1" id="materialId"
                                size="32" maxlength="32" readonly="readonly">
                        </td>
                        
                      
                        <td>
                            <div align="right">
                                物料名称:&nbsp;
                            </div>
                        </td>
                        <td>
                            <input   type="text" class="text1" id="materialName"
                                size="32" maxlength="32" readonly="readonly">
                        </td>  <td    >
                            <input    type="button" id="btnSelectItemNo"
                                value="筛选..." class="button1"> 
                      
                            <input name="btnReset" type="button" class="button1"
                                id="btnResetMaterial" value="重置">
                        </td>
                    </tr>
				
				</table>
                <table height='8' width="95%">
                    <tr>
                        <td height='3'></td>
                    </tr>
                </table>
                <table width="95%" height="20" border="0" cellspacing="0"
                    id="toolbar" class="rd1">
                    <tr>
                        <td class="rd19" width="434">
                            <font color="#FFFFFF">查询列表</font>
                        </td>
                        <td nowrap="nowrap" class="rd16" width="489">
                            <div align="right"></div>
                        </td>
                    </tr>
                </table>
                
                <div id='searchresult'>
                
                <table width="95%" border="1" cellspacing="0" cellpadding="0"
                    align="center" class="table1">
                    <tbody>
                    <tr>
                     
                        <td width="120" class="rd6">
                            分销商代码
                        </td>
                        <td width="194" class="rd6">
                            分销商名称
                        </td>
                        <td width="93" class="rd6">
                            物料代码
                        </td>
                        <td width="144" class="rd6">
                            物料名称
                        </td>
                        <td width="119" class="rd6">
                            规格
                        </td>
                        <td width="110" class="rd6">
                            型号
                        </td>
                        <td width="79" class="rd6">
                            计量单位
                        </td>
                        <td width="94" class="rd6">
                            数量
                        </td>
                          <td width="31" height="32" class="rd6">
                            <input type="checkbox" name="selectAll"   >
                        </td>
                    </tr>
                  <s:set var="rows" value="0" />
                    <s:iterator status="st" var ='it' value='#request.inventorys'>
                    <tr>
                      
                        <td width="120" class="rd8">
                        <a  onclick='window.open("<%=basePath%>basedata/distrib/distrib!showDistribDetail?distrib.id=<s:property value='#it.distrib.id'/>","分销商详细信息" ,"width=500, height=400, scrollbars=no" );return false; ' href=''>
                          <s:property value='#it.distrib.clientno'/></a>
                        </td>
                        <td width="194" class="rd8">
                                <s:property value='#it.distrib.name'/>
                        </td>
                          <td width="93" class="rd8">
                           <a href="#"
                    onClick="window.open('<%=basePath%>basedata/material/material!listSingleMaterial?m.id=<s:property value="#it.material.id" />', '物料详细信息', 'width=400, height=400, scrollbars=no');return false;"> 
                           <s:property value='#it.material.no'/>
                </a>
                        </td>
                        <td width="144" class="rd8">
                         <s:property value='#it.material.name'/>
                        </td>
                        <td width="119" class="rd8">
                        <s:property value='#it.material.guige'/>
                        </td>
                        <td width="110" class="rd8">
                        <s:property value='#it.material.xinghao'/>
                        </td>
                        <td width="79" class="rd8">
                              <s:property value='#it.material.materialItemUnit.name'/>
                        </td>
                        <td width="94" class="rd8">
                            <s:property value='#it.initcount' />
                        </td>
                          <td width="31" class="rd8">
                            <input type="checkbox" name="it.id" class="checkbox1"  
                                value="<s:property value='#it.id' />">
                        </td>
                    </tr>
                        <s:if test="#st.last">
                            <s:set name="rows" value="#st.count" />
                        </s:if>
                       
                    </s:iterator>
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
                 
                   </tbody>
                </table></div>
                    <input type='hidden' name='pm.pageNow'   value='<s:property value="#parameters['pm.pageNow']"/>'>
                <input type='hidden' name='pm.pageSize'  value='<s:property value="#parameters['pm.pageSize']"/>' >
                <input type='hidden' name='pm.rowCount'  value='<s:property value="#parameters['pm.rowCount']"/>' >
                <table width="95%" border="0" cellspacing="0" cellpadding="0"
                    height="30" class="rd1">
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
                                <input type="button" class="button1" id="btnAdd"
                                    value="添加" >
                                <input class="button1" type="button"
                                    id="btnDelete" value="删除"/>
                                <input  class="button1" type="submit"
                                    id="btnModify" value="修改"
                                   >
                                     <input  class="button1" type="button"
                                    id="btnSendCheck" value="送审"
                                   >
                                   
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </form>
  
  
  </body>
</html>
