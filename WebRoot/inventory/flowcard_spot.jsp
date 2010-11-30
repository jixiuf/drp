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
       var flowcardPath=basePath+'inventory/flowcard!listFlowcardsForSpot';
       var selectClientWindow=null;

       $( function() {
           $("#btnTopPage").click( function() {
                window.self.location= flowcardPath+ "?pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=1&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
           });
             $("#btnPreviousPage").click( function() {
                    window.self.location=flowcardPath+"?pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow-1'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
               });
             $("#btnNextPage").click( function() {
                 window.self.location=flowcardPath+"?pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow+1'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
            });
             
         
                
                   $("#btnSpot").click(
                           function(){
                        var checkedFlowcard=$(":radio[name='fc.id'][checked='true']");
                   if (checkedFlowcard.length==0){
                       alert('请选择要抽查的流向单');
                       return false;
                   } else  {
                          var url='<%=basePath%>inventory/flowcard!proSpotFlowcard?fc.id='+checkedFlowcard.val()+"&pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
                            window.self.location=url;
                   }
                           }
                           );

 
 
 
                  
           
/**
 * 打开 选择分销商的窗口
 */
    $("#clientNo").add("#clientName").dblclick(filterClients  );
    $("#btnSelectClient").click(filterClients);
 
    /**三种情况下触发searchFlowcards
    */
    var oldClientNo;
    $("#clientNo").blur(function(){
        var newClientNo=$(this).val();
        if (newClientNo==oldClientNo){
            return ;
        }
        oldClientNo=newClientNo
        searchFlowcards();
    }
        );
      $("#btnQuery").click(searchFlowcards);
    //  重置分销商
      $("#btnResetClient").click(  
              function(){
                  $("#clientId").val("");
                  $("#clientNo").val("");
                  $("#clientName").val("");
                  searchFlowcards();

                } 
            );

      $("#fiscalPeriodId").change(
              function(){
                  var selectedFc=$(this);
                  var selectedFcId=selectedFc.val();
                var selectedDiv=selectedFc.nextAll("#"+selectedFcId);
                var startDate=selectedDiv.children("[name='selectedStartdate']").val();
                 $("#fiscalPeriodStartDate").val(startDate);
                var endDate=selectedDiv.children("[name='selectedEnddate']").val();
                 $("#fiscalPeriodEndDate").val(endDate);
                 searchFlowcards();
              } );
      
      

    });

       /**
        * 根据选中的分销商和物料，查出此分销 商下针对 该种物料的所有的存货清单
        */
      function  searchFlowcards() {  
    var clientId =$("#clientId").val().trim();
    var fiscalPeriodId=$("#fiscalPeriodId").val().trim();
    var urlStr = basePath
            + "inventory/flowcard!searchFlowCardsByClientIdAndFiscalPeriodidForSpot?fc.status=A&fc.distrib.id="+ clientId+"&fc.fiscalPeriod.id="+fiscalPeriodId+"&pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";

    $.ajax( {
        url : urlStr,
        cache : false,
        success : function(html) {
            $("#searchresult").empty().append($(html));
        }
    });
                };

                //过滤分销商
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
        <form      >
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
                            <b>分销商库存管理&gt;&gt;流向单抽查 </b>  <span style='color: red'> <s:iterator value="#action.actionMessages" var='msg'> <s:property value='#msg' /> </s:iterator><s:iterator value='#action.actionErrors' var ='error' ><s:property  value="#error" /> </s:iterator> </span>
                        </td>
                    </tr>
                </table>
                <hr width="97%" align="center" size="0">
                <table width="95%" border="0" cellpadding="5" cellspacing="0">
                    <tr   >
                        <td    >
                          
                             
                                 分销商代码:&nbsp;
                           
                        </td>
                        <td  >
                            <input   type="text" class="text1" id="clientNo"
                                  maxlength="32" readonly="readonly">  <input name="btnSelectClient" type="button" id="btnSelectClient" value="..." class="button1" >
                             <input   type="button" class="button1"
                                id="btnResetClient" value="重置"    > 
                               <input name="it.client.id" type="hidden" class="text1" id="clientId" >
                        </td>
                        
                      
                        <td  >
                            <div align="right">
                                分销商名称:&nbsp;
                            </div>
                        </td>
                        <td  >
                            <input  type="text" class="text1"
                                id="clientName"   maxlength="32" readonly="readonly">
                        </td> 
                         
                        
                         
                         
                          <td  >
                            <input name="btnQuery"    type="button" class="button1"
                                id="btnQuery" value="查询"    >
                         </td>
                    </tr>
          
                    <tr>

<td>    会计核算期 ：</td>
						<td align="left">
						
						 
							<select  id = 'fiscalPeriodId' name="fiscalPeriodId" lang="zh_CN"   >
								<s:iterator value="#request.fps" var='fp' status="st"     >
									<option value="<s:property value='#fp.id'/>"  >
										<s:property value='#fp.year' /> - <s:property value='#fp.month' />
									</option>
								</s:iterator>
							 
							</select>
							 <s:iterator value="#request.fps" var='fp2' status="st"     >
                                       <div   id="<s:property value='#fp2.id'/>" class="hiddenValue" style="display: none;"> 
                                            <input    name='selectedStartdate' type='hidden'  value="<s:property value='#fp2.startdate'/>">
                                            <input name='selectedEnddate'  type='hidden' value="<s:property value='#fp2.enddate'/>">
                                       </div>
                                </s:iterator>
							  
						</td>
						<td align="right" > 开始日期:</td>
						<td align="left">
							
							<input align="right" type="text" class="text1"
								id="fiscalPeriodStartDate" size="10" maxlength="10" value="<s:property value='#request.fps[0].startdate'/>"
								readonly="readonly">
						</td>
						<td align="right" > 结束日期:</td>
						<td align="left">
                            <input name="it.material.name" type="text" class="text1" value="<s:property value='#request.fps[0].enddate'/>"
                                id="fiscalPeriodEndDate"  size="10" maxlength="10"
                                readonly="readonly">
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
                        <td class="rd6">
                            流向单号
                        </td>
                        <td class="rd6">
                            供方分销商代码
                        </td>
                        <td class="rd6">
                            供方分销商名称
                        </td>
                        <td class="rd6">
                            录入人
                        </td>
                        <td class="rd6">
                            录入日期
                        </td>
                        
                                <td class="rd6">
                                    单据状态
                                </td>
                                <td class="rd6">
                                    抽查标志
                                </td>
                                <td class="rd6">
                                    抽查人
                                </td>
                                <td class="rd6">
                                    抽查结果描述
                                </td>
                          <td width="31" height="32" class="rd6">
                            选择
                        </td>
                    </tr>
                  <s:set var="rows" value="0" />
            
                    <s:iterator status="st" var ='fc' value='#request.flowcards'>
                    <tr>
                        <td width="88" class="rd8">
                            <a href="#"
                                onClick="window.open(basePath+'inventory/flowcard!showFlowcasrdDetail?fc.id=<s:property value="#fc.id" />&pm.pageSize=<s:property value="#action.pm.pageSize"/>&pm.pageNow=<s:property value="#action.pm.pageNow"/>&pm.rowCount=<s:property value="#action.pm.rowCount"/>', '流向单详细信息', 'width=850, height=400, scrollbars=no');return false">
                                <s:property value="#fc.flowcardNo"/></a>
                        </td>
                        <td width="158" class="rd8">
                        <a href="basedata/distrib/distrib!showDistribDetail?distrib.id=<s:property value='#fc.distrib.id'/>">  <s:property value='#fc.distrib.clientno'/></a>
                                  
                        </td>
                        <td width="211" class="rd8">
                             <s:property value='#fc.distrib.name'/>
                        </td>
                        <td width="198" class="rd8">
                             <s:property value='#fc.recorder.name'/>  
                        </td>
                        <td width="197" class="rd8">
                        <s:date name="#fc.recordDate"   format="yyyy-MM-dd hh:mm:ss" />
                         
                        </td>
                      
                                    <td width="197" class="rd8">
                                        <s:if test='#fc.status=="L"'>录入</s:if>
                                        <s:elseif test='#fc.status=="S"'>送审</s:elseif>
                                        <s:elseif test='#fc.status=="A"'>已审核</s:elseif>
                                    </td>
                                    <td width="197" class="rd8">
                                        <s:if test="#fc.spotter==null">未抽查</s:if>
                                        <s:else>已抽查</s:else>
                                    </td>
                                    <td width="197" class="rd8">
                                        <s:property value="#fc.spotter.name" />
                                    </td>
                                    <td width="197" class="rd8">
                                        <s:property value="#fc.spotDesc" />
                                    </td>
                      <td width="31" class="rd8">
                            <input type="radio" name="fc.id"  
                                value="<s:property value='#fc.id' />">
                        </td>
                      
                      
                        
                    </tr>
                        <s:if test="#st.last">
                            <s:set name="rows" value="#st.count" />
                        </s:if>
                    </s:iterator>
                    <s:if test="rows<#action.pm.pageSize">
                    <s:iterator begin="1" end='#action.pm.pageSize-#rows'>
                        <tr>
                   <td width="37" class="rd8">
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
                        </td><td class="rd8">
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
                            
                             
                                     <input  class="button1" type="button"
                                    id="btnSpot" value="抽查"
                                   >
                                   
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </form>

 
  </body>
</html>
