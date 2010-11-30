
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
                      var listClientsPath=basePath+'inventory/inventory!listFiscalPeriods';
                       
                      $("#btnTopPage").click( function() {
                           window.self.location= listClientsPath+ "?pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=1&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
                      });
                        $("#btnPreviousPage").click( function() {
                               window.self.location=listClientsPath+"?pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow-1'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
                          });
                        $("#btnNextPage").click( function() {
                            window.self.location=listClientsPath+"?pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageNow+1'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
                       });
                        $("#btnBottomPage").click( function() {
                            var url=listClientsPath+"?pm.pageSize=<s:property value='#action.pm.pageSize'/>&pm.pageNow=<s:property value='#action.pm.pageCount'/>&pm.rowCount=<s:property value='#action.pm.rowCount'/>";
                              window.self.location=url;
                          });
                        
                       $("#btnOk").click( selectOk );
                       //live  一种绑定事件的方式,与bind()不同的的live 可以为现在不存在 但是将来 可来存在的匹配绑定事件，如ajax 新添加 的元素可也因此 绑定了
                        $(":radio[name='selectFlag']").live("dblclick",selectOk) ;

                   });
                //选 择完毕执行，关闭当前窗口
                function selectOk(){
                    var selectedRadio=$(":radio[name='selectFlag'][checked='true']");
                    var fiscalPeriodId=selectedRadio.val();
                    var fiscalPeriodYear=selectedRadio.nextAll('input[name="fp.year"]').val();
                    var fiscalPeriodMonth=selectedRadio.nextAll('input[name="fp.month"]').val();
                    var fiscalPeriodStartDate=selectedRadio.nextAll('input[name="fp.startdate"]').val();
                    var fiscalPeriodEndDate=selectedRadio.nextAll('input[name="fp.enddate"]').val();
                     var par_doc= $(self.opener.document);
                     par_doc.find("#fiscalPeriodYear").val(fiscalPeriodYear);
                     par_doc.find("#fiscalPeriodMonth").val(fiscalPeriodMonth);
                     par_doc.find("#fiscalPeriodStartDate").val(fiscalPeriodStartDate);
                     par_doc.find("#fiscalPeriodEndDate").val(fiscalPeriodEndDate);
                  var fiscalPeriodIdInput= par_doc.find("#fiscalPeriodId");
                  fiscalPeriodIdInput.val(fiscalPeriodId)
                    window.opener.focus();
                  fiscalPeriodIdInput.focus();
                  window.close();
                }
	function search() {
		var th = $("#searchInput");
		var empty = /\s+/g;
		if (empty.test(th.val())) {
			return;
		}
		var searchActionPath = basePath
				+ "inventory/inventory!searchFiscalPeriods?fiscalPeriod.year="
				+  th.val().trim() ;
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

	String.prototype.trim = function() {
	    return this.replace(/(^\s*)|(\s*$)/g, "");
	}
		
</script>
	</head>
  
<body class="body1">
        <form name="fiscalPeriodForm">
            <div align="center">
                <table width="95%" border="0" cellspacing="0" cellpadding="0"
                    height="34">
                    <tr>
                        <td width="522" class="p1" height="34" nowrap="nowrap">
                            <img src="img/search.gif" width="32" height="32">
                            &nbsp;
                            <b>请选择会计核算期</b>
                        </td>
                    </tr>
                </table>
                <hr width="100%" align="center" size="0">
                <table width="95%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="17%" height="29">
                            <div align="left">
                         会计核算期 年/月:
                            </div>
                        </td>
                        <td width="57%">
                            <input name="fp.year" type="text" class="text1"
                                id="searchInput" size="50" maxlength="50">
                        </td>
                        <td width="26%">
                            <div align="left">
                                <input type="button" class="button1"
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
                        会计核算期信息
                    </td>
                    <td nowrap="nowrap" height="10" class="p3">
                        &nbsp;
                    </td>
                </tr>
            </table>
            <table width="95%" border="1" cellspacing="0" cellpadding="0"
                align="center" class="table1">
                <tr>
						<td class="rd6">
							核算年
						</td>
						<td class="rd6">
							核算月
						</td>
						<td class="rd6">
							开始日期
						</td>
						<td class="rd6">
							结束日期
						</td>
						<td class="rd6">
							选择
						</td>
					</tr>      <s:set var="rows" value="0" />
                <s:iterator value='#request.fps' var='fp' status="st" >
                <tr>
                
                  
                    <td class="rd8">           
                    <s:property value='#fp.year'/>
                    </td>
                    <td class="rd8">
           <s:property value='#fp.month'/>
                    </td>
                    <td class="rd8">
                           <s:property value='#fp.startdate'/>
                    </td> 
                        <td class="rd8">
                           <s:property value='#fp.enddate'/>
                    </td> 
                     <td class="rd8">
                     <s:if test="#action.fp.id==#fp.id"> <input type="radio" name="selectFlag"   checked="checked" value="<s:property value='#fp.id'/>"></s:if>
                       <s:else> <input type="radio" name="selectFlag"   value="<s:property value='#fp.id'/>"></s:else>
                        <input type="hidden" name="fp.year"   value="<s:property value='#fp.year'/>">
                        <input type="hidden" name="fp.month"   value="<s:property value='#fp.month'/>">
                        <input type="hidden" name="fp.startdate"   value="<s:property value='#fp.startdate'/>">
                        <input type="hidden" name="fp.enddate"   value="<s:property value='#fp.enddate'/>">
                    </td>
                </tr>
                    <s:if test="#st.last">
                            <s:set name="rows" value="#st.count" />
                        </s:if>
                </s:iterator>
                     <s:iterator begin="1" end='#action.pm.pageSize-#rows'>
           <tr>     <td class="rd6">
                   &nbsp;
                        </td>
                        <td class="rd6">
                             &nbsp;
                        </td>
                        <td class="rd6">
                           &nbsp;
                        </td>
                        <td class="rd6">
                            &nbsp; 
                        </td>
                        <td class="rd6">
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
                       
                            <input name="btnOk" class="button1" type="button" id="btnOk"
                                value="确定">
                            <input name="btnClose" class="button1" type="button"
                                id="btnClose" value="关闭" onClick="window.close()">
                        </div>
                    </td>
                </tr>
            </table>
        </form>
     <div align="right" style=" font-size:13px">可以尝试双击单选按钮</div>   
	
		<s:debug></s:debug>
    </body>
</html>
