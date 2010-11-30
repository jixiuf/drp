
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
     
                $( function() {
                    var  searchInput = $("#searchInput");
                      searchInput.select();
                    searchInput.keyup(search);
                     $("#btnQuery").click(search);
                      //分页的四个按钮
                      var listMaterialsPath=basePath+'inventory/inventory!listMaterials';
                       
                      $("#btnTopPage").click( function() {
                           window.self.location= listMaterialsPath+ "?pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=1&pm.rowCount=<s:property value='parameters["pm.rowCount"]'/>";
                      });
                        $("#btnPreviousPage").click( function() {
                               window.self.location=listMaterialsPath+"?pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow-1'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
                          });
                        $("#btnNextPage").click( function() {
                            window.self.location=listMaterialsPath+"?pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow+1'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
                       });
                        $("#btnBottomPage").click( function() {
                            var url=listMaterialsPath+"?pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageCount'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
                              window.self.location=url;
                          });
                        
                       $("#btnOk").click( selectOk );
                       //live  一种绑定事件的方式,与bind()不同的的live 可以为现在不存在 但是将来 可来存在的匹配绑定事件，如ajax 新添加 的元素可也因此 绑定了
                        $(":radio[name='selectFlag']").live("dblclick",selectOk) ;

                   });
                //选 择完毕执行，关闭当前窗口
                function selectOk(){
                    var selectedRadio=$(":radio[name='selectFlag'][checked='true']");
                    var materialNo=selectedRadio.val();
                    var materialId=selectedRadio.nextAll('input[name="m.id"]').val();
                    var materialName=selectedRadio.nextAll('input[name="m.name"]').val();
                    var materialGuige=selectedRadio.nextAll('input[name="m.guige"]').val();
                    var materialXinghao=selectedRadio.nextAll('input[name="m.xinghao"]').val();
                    var materialUnit=selectedRadio.nextAll('input[name="m.unit"]').val();
                    var materialType=selectedRadio.nextAll('input[name="m.materialType"]').val();
                     var par_doc= $(self.opener.document);

                   
               var index='<s:property value="#parameters['index']" />';
           if(index==""){
               var  materialNoInput=   par_doc.find("#materialNo");
               materialNoInput .val(materialNo);
               materialNoInput.select();
               par_doc.find("#materialId").val(materialId);
               par_doc.find("#materialName").val(materialName);
               par_doc.find("#materialGuige").val(materialGuige);
               par_doc.find("#materialXinghao").val(materialXinghao);
               par_doc.find("#materialUnit").val(materialUnit);
               par_doc.find("#materialType").val(materialType);

           }else {
        	     var par_cur_row= par_doc.find("tr[index='"+index+"']");
        	     par_cur_row.find("#fc\\.flowcardDetails\\["+index+"\\]\\.material\\.id").val(materialId);
                 par_cur_row.find("#fc\\.flowcardDetails\\["+index+"\\]\\.material\\.no").val(materialNo);
                 par_cur_row.find("#fc\\.flowcardDetails\\["+index+"\\]\\.material\\.name").val(materialName);
                 par_cur_row.find("#fc\\.flowcardDetails\\["+index+"\\]\\.material\\.guige").val(materialGuige);
                 par_cur_row.find("#fc\\.flowcardDetails\\["+index+"\\]\\.material\\.xinghao").val(materialXinghao);
                 par_cur_row.find("#fc\\.flowcardDetails\\["+index+"\\]\\.material\\.materialItemUnit\\.name").val(materialUnit);
                 par_cur_row.find("#fc\\.flowcardDetails\\["+index+"\\]\\.material\\.materialType\\.name").val(materialType);
        	     /*
                 par_cur_row.find(":input[name='fc.flowcardDetails["+index+"].material.id']").val(materialId);
                 par_cur_row.find(":input[name='fc.flowcardDetails["+index+"].material.no']").val(materialNo);
                 par_cur_row.find(":input[name='fc.flowcardDetails["+index+"].material.name']").val(materialName);
                 par_cur_row.find(":input[name='fc.flowcardDetails["+index+"].material.guige']").val(materialGuige);
                 par_cur_row.find(":input[name='fc.flowcardDetails["+index+"].material.xinghao']").val(materialXinghao);
                 par_cur_row.find(":input[name='fc.flowcardDetails["+index+"].material.materialItemUnit.name']").val(materialUnit);
                 par_cur_row.find(":input[name='fc.flowcardDetails["+index+"].material.materialType.name']").val(materialType);
                 */
           }

                     
              
                  window.opener.focus();
                  window.close();
                }
    function search() {
        var th = $("#searchInput");
        var empty = /\s+/g;
        if (empty.test(th.val())) {
            return;
        }
        var searchActionPath = basePath
                + "inventory/inventory!searchMaterials?m.name="
                + encodeURI(encodeURI(th.val()));
        var urlStr = searchActionPath
                + "&pm.pageSize=<s:property value='#parameters["pm.pageSize"]'/>&pm.pageNow=<s:property value='#parameters["pm.pageNow"]'/>&pm.rowCount=<s:property value='#parameters["pm.rowCount"]'/>"
        $.ajax( {
            url : urlStr,
            cache : false,
            success : function(html) {
                $("#searchresult").empty().append($(html));
            }
        });
    };
</script>
    </head>
  
<body class="body1">

        <form name="clientForm">
            <div align="center">
                <table width="95%" border="0" cellspacing="0" cellpadding="0"
                    height="34">
                    <tr>
                        <td width="522" class="p1" height="34" nowrap="nowrap">
                            <img src="img/search.gif" width="32" height="32">
                            &nbsp;
                            <b>请选择物料</b>
                        </td>
                    </tr>
                </table>
                <hr width="100%" align="center" size="0">
                <table width="95%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="17%" height="29">
                            <div align="left">
                                物料代码/名称:
                            </div>
                        </td>
                        <td width="57%">
                            <input name="client.name" type="text" class="text1"
                                id="searchInput" size="50" maxlength="50">
                        </td>
                        <td width="26%">
                            <div align="left">
                                <input name="btnQuery" type="button" class="button1"
                                    id="btnQuery" value="查询">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td height="16">
                            <div align="right"></div>
                        </td>
                        <td>
                            &nbsp;
                        </td>
                        <td>
                            <div align="right"></div>
                        </td>
                    </tr>
                </table>

            </div>
            <div id='searchresult'>
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
                  
                    <td width="170" class="rd6">
                        物料代码
                    </td>
                    <td width="222" class="rd6">
                        物料名称
                    </td>
                    <td width="195" class="rd6">
                        物料规格
                    </td>
                    <td width="293" class="rd6">
                        物料型号
                    </td>
                    <td width="293" class="rd6">
                        类别
                    </td>
                    <td width="293" class="rd6">
                        计量单位
                    </td>  <td width="35" class="rd6">
                        选择
                    </td>
                </tr>    <s:set var="rows" value="0" />
                <s:iterator value='#request.materials' var='m' status="st" >
               
                    <tr>
                   
                    <td width="170" class="rd8">
                               <a href="#"
                    onClick="window.open('basedata/material/material!listSingleMaterial?m.id=<s:property value="#m.id" />', '物料详细信息', 'width=400, height=400, scrollbars=no');return false;"> 
                           <s:property value='#m.no'/></a>
                    </td>
                    <td width="222" class="rd8">
                         <s:property value='#m.name'/>
                    </td>
                    <td width="195" class="rd8">
                    <s:property value='#m.guige'/>
                    </td>
                    <td width="293" class="rd8">
                       <s:property value='#m.xinghao'/>
                    </td>
                    <td width="293" class="rd8">
                         <s:property value='#m.materialType.name'/>
                    </td>
                    <td width="293" class="rd8">
                        <s:property value='#m.materialItemUnit.name'/>
                    </td>
                      <td class="rd8">
                      <s:if test="#m.no==#action.m.no"><input type="radio" name="selectFlag"   checked="checked" value="<s:property value='#m.no'/>"></s:if>
                      <s:else>  <input type="radio" name="selectFlag"   value="<s:property value='#m.no'/>"></s:else>
                        <input type="hidden" name="m.id"   value="<s:property value='#m.id'/>">
                        <input type="hidden" name="m.name"   value="<s:property value='#m.name'/>">
                        <input type="hidden" name="m.guige"   value="<s:property value='#m.guige'/>">
                        <input type="hidden" name="m.xinghao"   value="<s:property value='#m.xinghao'/>">
                        <input type="hidden" name="m.materialType"   value="<s:property value='#m.materialType.name'/>">
                          <input type="hidden" name="m.unit"   value="<s:property value='#m.materialItemUnit.name'/>">
                    </td>
                </tr>
                 
        
           
                    <s:if test="#st.last">
                            <s:set name="rows" value="#st.count" />
                        </s:if>
                </s:iterator>
                     <s:iterator begin="1" end='#action.pm.pageSize-#rows'>
           <tr>  <td class="rd8">
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
            </table>
            </div>
            <table width="95%" height="30" border="0" align="center"
                cellpadding="0" cellspacing="0" class="rd1">
                <tr>
                    <td nowrap="nowrap" class="rd19" height="2">
                        <div align="left">
                            <font color="#FFFFFF">&nbsp;共&nbsp;<s:property
                                    value='#action.pm.pageCount' />&nbsp;页</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <font color="#FFFFFF">当前第</font>&nbsp;
                            <font color="#FF0000"><s:property
                                    value='parameters["pm.pageNow"]' />
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
                       
                            <input name="btnOk" class="button1" type="button" id="btnOk"
                                value="确定" >
                            <input name="btnClose" class="button1" type="button"
                                id="btnClose" value="关闭" onClick="window.close()">
                        </div>
                    </td>
                </tr>
            </table>
        </form>
     <div align="right" style=" font-size:13px">可以尝试双击单选按钮</div>   
    
可以尝试双击
    </body>
</html>
