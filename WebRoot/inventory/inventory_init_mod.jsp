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

    <title>My JSF 'inventory_init_add.jsp' starting page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
         <link rel="stylesheet" type="text/css"   href="css/drp.css" />
         <script type='text/javascript'  src='js/jquery.js'></script>
         <script type='text/javascript' >
         var basePath='<%=basePath%>';
         var selectClientWindow;
                $( function() {
                    
                    /**
                     * 打开 选择分销商的窗口
                     */
                        $("#btnSelectClient")
                                    .click(
                                            function() {
                                                var clientNo=$("#clientNo").val();
                                                var url = basePath
                                                        + "inventory/inventory!listDistribs?distrib.clientno="+clientNo+"&pm.pageSize=<s:property value='#parameters["pm.pageSize"]
                                                        '/>&pm.pageNow=<s:property value='#parameters["pm.pageNow"]'/>&pm.rowCount=<s:property value='#parameters["pm.rowCount"]'/>";
                                                if(selectClientWindow==null ||selectClientWindow.closed){
                                                    selectClientWindow = window.open(url, '选择分销商',
                                                        'width=700, height=400, scrollbars=no');
                                                }else{
                                                      selectClientWindow.focus();
                                                }
                                            }

                                    );
                        /**
                         * 打开 选择物料的窗口
                         */
                        $("#btnSelectMaterialNo")
                        .click(
                                function() {  
                                    var materialNo=$("#materialNo").val();
                                    var url = basePath
                                            + "inventory/inventory!listMaterials?m.no="+materialNo+"&pm.pageSize=<s:property value='#parameters["pm.pageSize"]
                                            '/>&pm.pageNow=<s:property value='#parameters["pm.pageNow"]'/>&pm.rowCount=<s:property value='#parameters["pm.rowCount"]'/>";
                                    if(selectClientWindow==null ||selectClientWindow.closed){
                                        selectClientWindow = window.open(url, '选择物料',
                                            'width=700, height=400, scrollbars=no');
                                    }else{
                                          selectClientWindow.focus();
                                    }
                                }

                        );
                      

                   })
       </script>

</head>
<body class="body1">
        <form name="InvIniQtyForm" method='post' action="inventory/inventory!modInventory" target="_self" id="InvIniQtyForm">
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
                        <td width="522" class="p1" height="2" nowrap='nowrap'>
                            <img src="img/mark_arrow_03.gif" width="14" height="14">
                            &nbsp;
                            <b>分销商库存管理&gt;&gt;分销商库存数量初始化&gt;&gt;修改</b> <span style='color: red'> <s:iterator value="#action.actionMessages" var='msg'> <s:property value='#msg' /> </s:iterator> </span>
                        </td>
                    </tr>
                </table>
                <hr width="97%" align="center" size="0">
                <table width="95%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="22%" height="29">
                            <div align="right">
                                <font color="#FF0000">*</font>分销商代码:&nbsp;
                            </div>
                        </td>
                        <td width="78%">
                            <input type="text" class="text1" id="clientNo"  name='it.distrib.clientno' value='<s:property value="#action.it.distrib.clientno"/>'
                                size="32" maxlength="32" readonly="readonly">
                            
                            <input name="btnSelectClient" type="button" id="btnSelectClient"
                                value="..." class="button1">
                        </td>
                    </tr>
                    <tr>
                        <td height="26">
                            <div align="right">
                                分销商名称:&nbsp;
                            </div>
                        </td>
                        <td>
                            <input type="text" class="text1" name='it.distrib.name'
                                id="clientName" size="32" maxlength="32" value='<s:property value="#action.it.distrib.name"/>' readonly="readonly">
                                
                        </td>
                    </tr>
                    <tr>
                        <td height="26">
                            <div align="right">
                                <font color="#FF0000">*</font>物料代码:&nbsp;
                            </div>
                        </td>
                        <td>
                            <input  type="text" class="text1" id="materialNo" name='it.material.no' value='<s:property value="#action.it.material.no"/>'
                                size="32" maxlength="32" readonly="readonly">
                        
                            <input  type="button" id="btnSelectMaterialNo"
                                value="..." class="button1" >
                        </td>
                    </tr>
                    <tr>
                        <td height="26">
                            <div align="right">
                                物料名称:&nbsp;
                            </div>
                        </td>
                        <td>
                            <input  type="text" class="text1" id="materialName"  name='it.material.name' value='<s:property value="#action.it.material.name"/>'
                                size="32" maxlength="32" readonly="readonly">
                        </td>
                    </tr>
                    <tr>
                        <td height="26">
                            <div align="right">
                                物料规格:&nbsp;
                            </div>
                        </td>
                        <td>
                            <input  type="text" class="text1" id="materialGuige" size="32" name='it.material.guige' value='<s:property value="#action.it.material.guige"/>'
                                maxlength="32" readonly="readonly">
                        </td>
                    </tr>
                    <tr>
                        <td height="26">
                            <div align="right">
                                物料型号:&nbsp;
                            </div>
                        </td>
                        <td>
                            <input   type="text" class="text1" id="materialXinghao"  name ='it.material.xinghao' value='<s:property value="#action.it.material.xinghao"/>'
                                size="32" maxlength="32" readonly="readonly">
                        </td>
                    </tr>
            
                              <tr>
                        <td height="26">
                            <div align="right">
                         物料类型:&nbsp;
                            </div>
                        </td>
                        <td>
                            <input   type="text" class="text1" id="materialType" size="32"   name='it.material.materialType.name' value='<s:property value="#action.it.material.materialType.name"/>'
                                maxlength="32" readonly="readonly">
                        </td>
                    </tr>        <tr>
                        <td height="26">
                            <div align="right">
                                计量单位:&nbsp;
                            </div>
                        </td>
                        <td>
                            <input type="text" class="text1" id="materialUnit" size="32" name='it.material.materialItemUnit.name'  value='<s:property value="#action.it.material.materialItemUnit.name"/>'
                                maxlength="32" readonly="readonly">
                        </td>
                    </tr>
                    <tr>
                        <td height="26">
                            <div align="right">
                                <font color="#FF0000">*</font>初始数量:&nbsp;
                            </div>
                        </td>
                        <td>
                            <input name="it.initcount" type="text" value='0' class="text1" id="initQty"   value='<s:property value="#action.it.initcount"/>'
                                size="32" maxlength="32">
                        </td>
                    </tr>
                </table>
                <input name="it.incount" value='0' type="hidden"  >
               <input name="it.outcount" value='0'  type="hidden"  >
                <input name="it.status" value='N'  type="hidden" >
                   <input name="it.id" type="hidden"  value='<s:property value="#action.it.id"/>' id="id" >
                <input name="it.distrib.id" type="hidden"  value='<s:property value="#action.it.distrib.id"/>' id="clientId" >
               <input name="it.material.id" type="hidden" value='<s:property value="#action.it.material.id"/>' id="materialId" >
                <input type='hidden' name='pm.pageNow'   value='<s:property value="#parameters['pm.pageNow']"/>'>
                <input type='hidden' name='pm.pageSize'  value='<s:property value="#parameters['pm.pageSize']"/>' >
                <input type='hidden' name='pm.rowCount'  value='<s:property value="#parameters['pm.rowCount']"/>' >
                <hr width="97%" align="center" size="0">
                <div align="center">
                    <input  class="button1" type="submit" id="btnAdd"
                        value="修改">
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <input class="button1" type="button" id="btnBack"
                        value="返回" onClick="history.go(-1)">
                </div>
            </div>
        </form>
    </body>
</html>