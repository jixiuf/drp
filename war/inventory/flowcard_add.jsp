<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>添加流向单维护</title>

		<link rel="stylesheet" href="css/drp.css">
		<script type='text/javascript' src="js/jquery.js"></script>
		<script type='text/javascript'>
        var basePath='<%=basePath%>';
        var selectClientWindow;
        var selectTerminalWindow;
        var selectMaterialWindow;
      var index=1;
      var modelRow ;
        $(function(){
       
          
        	//添加 新的一行数据 ，
        	    $("#btnAddNewLine").click(
                	    function(){ 
                    	  
        	    	 	var newLine=modelRow.clone(true) .attr("index",index).attr("id",'row_'+index) ;
        	         //  $("#terminals").children().append(newLine);
        	         newLine.find(':input').attr('index',index) ;

            	     //给每个含 [0] 的input 改名 ，成[index ]  如fc.flowcardDetails[0].client.clientno 改成fc.flowcardDetails[1].client.clientno
        	         newLine.find(':input').each( function (){
            	         var th= $(this);
            	      var name=th.attr('name');
            	      // name 中含有[0] ,的将[0]用 [index] 替换 ，index 为全局变量中的index 的值 ，如1 ,2 ,3 
            	      var regExpContainO= /[0]/
            	      if (regExpContainO.test(name)){
                	     name=name.replace('[0]' ,'['+index+']');
                	     th.attr('name',name);
                	     th.attr('id',name);
            	      }
            	         }
        	         );

        	         

        	         
        	         newLine.insertBefore( $("#endOfRow") );
        	         index++;

                	    }
                	    );

         	   //选择需方客户（可以是分销商，也可以是终端客户） 
             $(":button[name='selectTerminalBtn']").live('click',   
                        function(){ 
                            var th_button =$(this); 
                           var cur_index=  th_button.attr("index") ; 
                           var cur_row=$( "tr[index='"+cur_index+"']"); 
                         
                       
                           var url = basePath
                           + "inventory/flowcard!listAllClientAndTerminals?index="+cur_index+"&pm.pageSize=<s:property value='#parameters["pm.pageSize"]'/>&pm.pageNow=<s:property value='#parameters["pm.pageNow"]'/>&pm.rowCount=<s:property value='#parameters["pm.rowCount"]'/>";


                             if(selectTerminalWindow==null ||selectTerminalWindow.closed){
                           	 selectTerminalWindow=    	 window.open(url , '选择需方客户',  'width=700, height=400, scrollbars=no');
                        	 //  selectTerminalWindow = window.open(url, '选择需方客户可以是分销商或者终端 客户', 'width=700, height=400, scrollbars=no');
                         }else{
                            selectTerminalWindow.focus();
                            }
                        }
                        );

             //选择物料
             $(":button[name='selectMaterialBtn']").live('click',   
                        function(){ 
                            var th_button =$(this); 
                           var cur_index=  th_button.attr("index") ; 
                           var cur_row=$( "tr[index='"+cur_index+"']"); 
                         
                         //  var cur_materialId= cur_row.find(":input[name='fc.flowcardDetails["+cur_index+"].material.id']").val(); //需方客户的代码 ，可以为空，不为空时，打开的窗口中此项则为默认选中的项
                           var url = basePath
                           + "inventory/inventory!listMaterials?index="+cur_index+"&pm.pageSize=<s:property value='#parameters["pm.pageSize"]'/>&pm.pageNow=<s:property value='#parameters["pm.pageNow"]'/>&pm.rowCount=<s:property value='#parameters["pm.rowCount"]'/>";
                           if(selectMaterialWindow==null ||selectMaterialWindow.closed){
                        	   selectMaterialWindow = window.open(url, '选择物料',
                                   'width=700, height=400, scrollbars=no');
                           }else{
                        	   selectMaterialWindow.focus();
                           }
                        }
                        );

          
               
       //删除 一行
             $(":button[name='del']").live('click',   
                     function(){ 
                         var th_button =$(this); 
                        var cur_index=  th_button.attr("index") ; 
                        var cur_row=$( "tr[index='"+cur_index+"']"); 
                        cur_row.remove();
                     }
                     );
        	    
                /**
                 * 打开 选择   供方分销商的窗口
                 */
                    $("#clientNo").add("#clientName").dblclick(filterClients  );
                    $("#btnSelectDistrib").click(filterClients); 

                    $("#saveFlowcard").submit(
                            function(){
                               
                                var returnFlag=false;
                            
                                var distrib=$("#clientId"); //供方分销商
                               if( distrib.val()==""){
                                   alert("供方分销商不能为空!!");
                                   return  false;

                               }
                      
                        $(".materialCount").each(
                                        function (){
                                            var th= $(this);
                                            th.val(th.val().trim());
                                           if ( th.val()==""|| isNaN(th.val())   ) {
                                               th.focus();
                                               alert("操作数量不能为空,且必须是数字（可以是小数）");
                                               returnFlag=true;
                                               return  ;
                                           }
                                        }
                                        );
                     if(returnFlag){
                         return false;

                     }
                             
                        //保证  需方客户不能是供方分销商本身
                  
                        var terminals=$(".terminalId");//所有需方客户
                        terminals.each(
                                function(){
                                    var th=$(this);
                                    if(th.val()==distrib.val()){
                                        alert( "需方客户不能是供方分销商本身!!!");
                                        returnFlag=true;
                                        return ;
                                    }else if (th.val()=="" ) {
                                        alert("需方客户不能为空");
                                        returnFlag=true;
                                        return ;

                                    }
                        
                                } );

                        if(returnFlag){
                            return false;

                        }
                        $(".materialId").each(
                                function(){
                                    var th=$(this);
                                    if (th.val().trim()=="" ){
                                        alert("物料不能为空");
                                        returnFlag=true;
                                        return ;
                                    }

                                    } );
                        if(returnFlag){
                            return false;
                        }
                              return true;
                              
                            } );




                    $("#fiscalPeriodId").change(
                            function(){
                                var selectedFc=$(this);
                                var selectedFcId=selectedFc.val();
                              var selectedDiv=selectedFc.nextAll("#"+selectedFcId);
                              var startDate=selectedDiv.children("[name='selectedStartdate']").val();
                               $("#fiscalPeriodStartDate").val(startDate);
                              var endDate=selectedDiv.children("[name='selectedEnddate']").val();
                               $("#fiscalPeriodEndDate").val(endDate);
                            } );
                    

                    

        } );

      
    
        //添加供方分销商
        function filterClients()  {
            var clientNo=$("#clientNo").val();
            var url = basePath
                    + "inventory/inventory!listDistribs?distrib.clientno="+clientNo+"&pm.pageSize=<s:property value='#parameters["pm.pageSize"]'/>&pm.pageNow=<s:property value='#parameters["pm.pageNow"]'/>&pm.rowCount=<s:property value='#parameters["pm.rowCount"]'/>";
          
            if(selectClientWindow==null ||selectClientWindow.closed){
            	selectClientWindow = window.open(url, '选择分销商',
                    'width=700, height=400, scrollbars=no');
            }else{
            	selectClientWindow();
            }
           
        };

        String.prototype.trim = function() {
            return this.replace(/(^\s*)|(\s*$)/g, "");
        }
                

        </script>
	</head>

	<body class="body1">
		<div align="center">
			<form name="saveFlowcard" method="post" id='saveFlowcard'
				action="inventory/flowcard!saveFlowcard">
				<table width="95%" border="0" cellspacing="2" cellpadding="2">
					<tr>
						<td>&nbsp;
							


					  </td>
					</tr>
				</table>
				<table width="95%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="522" class="p1" height="2" nowrap>
							<img src="img/mark_arrow_03.gif" width="14" height="14">
							&nbsp;
							<b>分销商库存管理&gt;&gt;流向单维护&gt;&gt;添加</b>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr width='97%'>
						<td nowrap="nowrap" height="29" width='13%'>
							<div>
								<font color="#FF0000">*</font>供方分销商代码:&nbsp;
							</div>
						</td>
						<td width='32%'>
							<input name="fc.distrib.clientno" type="text" class="text1"
								id="clientNo" readonly="true">
							<input name="fc.distrib.id" type="hidden" id="clientId">
							<input type="button" id="btnSelectDistrib" value="..."
								class="button1">

						</td>

						<td>
							<div align="right">
								供方分销商名称:&nbsp;
							</div>
						</td>
						<td align="left">
							<input name="fc.distrib.name" type="text" class="text1"
								id="clientName" size="32" maxlength="32" readonly="true">
						</td>
						<td>

						</td>
						<td>&nbsp;
							


					  </td>

					</tr>

					<tr>

						<td>
							会计核算期 ：
						</td>
						<td align="left">


							<select id='fiscalPeriodId' name="fc.fiscalPeriod.id"
								lang="zh_CN">
								<s:iterator value="#request.fps" var='fp' status="st">
									<option value="<s:property value='#fp.id'/>">
										<s:property value='#fp.year' />
										-
										<s:property value='#fp.month' />
									</option>
								</s:iterator>

							</select>
							<s:iterator value="#request.fps" var='fp2' status="st">
								<div id="<s:property value='#fp2.id'/>" class="hiddenValue"
									style="display: none;">
									<input name='selectedStartdate' type='hidden'
										value="<s:property value='#fp2.startdate'/>">
									<input name='selectedEnddate' type='hidden'
										value="<s:property value='#fp2.enddate'/>">
								</div>
							</s:iterator>

						</td>
						<td align="right">
							开始日期:
						</td>
						<td align="left">

							<input align="right" type="text" class="text1"
								id="fiscalPeriodStartDate" size="10" maxlength="10"
								value="<s:property value='#request.fps[0].startdate'/>"
								readonly="readonly">
						</td>
						<td align="right">
							结束日期:
						</td>
						<td align="left">
							<input name="it.material.name" type="text" class="text1"
								value="<s:property value='#request.fps[0].enddate'/>"
								id="fiscalPeriodEndDate" size="10" maxlength="10"
								readonly="readonly">
						</td>

					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="93%" height="55" border="0" align="center" cellpadding="0"
					id='terminals' style="width: value_or_percent; table-layout: auto;"
					name="tblFlowCardDetail">
					<tr width="95%">
						<td width="6%" height="24" nowrap>&nbsp;
							
					  </td>
						<td width="11%" nowrap>
							<div align="left">
								<font color="#FF0000">*</font> 需方客户代码
							</div>
						</td>
						<td width="9%" nowrap>
							<div align="left">
								需方客户名称
							</div>
						</td>
						<td width="7%" nowrap>
							<div></div>
						</td>
						<td width="8%" nowrap>
							<font color="#FF0000">*</font>物料代码
						</td>
						<td width="11%" nowrap>
							<div align="left">
								物料名称
							</div>
						</td>
						<td width="9%" nowrap>
							规格
						</td>
						<td width="9%" nowrap>
							型号
						</td>
						<td width="10%" nowrap>
							计量单位
						</td>
						<td width="10%" nowrap>
							<font color="#FF0000">*</font>操作数量
						</td>
						<td width="10%" nowrap>
							<div align="left">
								删除
							</div>
						</td>
					</tr>
					<div>
						<tr align="center" valign="middle" id='modelRow' index="0" style="width:auto;"
							width='95%'>

							<td nowrap>

								<div align="justify">
									<input index="0" value='' type='hidden' class="terminalId"
										size="5" id='fc.flowcardDetails[0].client.id'
										readonly="readonly" name='fc.flowcardDetails[0].client.id' />
									<input type='button' index="0" class='button1' value='...'
										name='selectTerminalBtn' />
								</div>
							</td>
							<td nowrap>
								<input name='fc.flowcardDetails[0].client.clientno' type='text'
									id='fc.flowcardDetails[0].client.clientno' value='' size="12"
									readonly="readonly" index="0" />
							</td>
							<td nowrap>
								<input name='fc.flowcardDetails[0].client.name' type='text'
									id='fc.flowcardDetails[0].client.name' value='' size="12"
									readonly="readonly" />
								&nbsp;
							</td>




							<td nowrap>
								<input index="0" value='' type='hidden'
									name='fc.flowcardDetails[0].material.id' class='materialId'
									id='fc.flowcardDetails[0].material.id' readonly="readonly" />
								&nbsp;
								<input index="0" type='button' class='button1' value='...'
									name='selectMaterialBtn' />
							</td>
							<td nowrap>
								<input name='fc.flowcardDetails[0].material.no' type='text'
									id='fc.flowcardDetails[0].material.no' value='' size="12"
									readonly="readonly" index="0" />
							</td>
							<td nowrap>
								<input name='fc.flowcardDetails[0].material.name' type='text'
									id='fc.flowcardDetails[0].material.name' value='' size="12"
									readonly="readonly" index="0" />
							</td>
							<td nowrap>
								<input name='fc.flowcardDetails[0].material.guige' type='text'
									id='fc.flowcardDetails[0].material.guige' value='' size="12"
									readonly="readonly" index="0" />
								&nbsp;&nbsp;
							</td>
							<td nowrap>
								<input name='fc.flowcardDetails[0].material.xinghao' type='text'
									id='fc.flowcardDetails[0].material.xinghao' value='' size="12"
									readonly="readonly" index="0" />
								&nbsp;&nbsp;
							</td>
							<td nowrap>
								<input
									name='fc.flowcardDetails[0].material.materialItemUnit.name'
									type='text'
									id='fc.flowcardDetails[0].material.materialItemUnit.name'
									value='' size="12" readonly="readonly" index="0" />
								&nbsp;&nbsp;
							</td>
							<td nowrap>
								<input name='fc.flowcardDetails[0].materialCount' type='text'
									class="materialCount" id='fc.flowcardDetails[0].materialCount'
									value='' size="12" index="0" />
								&nbsp;&nbsp;
							</td>
							<td nowrap>
								<input index="0" value='删除' class='button1' size="10" name='del'
									type='button' />
							</td>
						</tr>
					</div>
					<tr id='endOfRow'>
						<!-- don't delete this -->
					</tr>
			  </table>
				<p>

					<input type="hidden" name="fc.status" value='L'>
					<input type='hidden' name='pm.pageSize'
						value="<s:property value='#parameters["pm.pageSize"]'/>">
					<input type='hidden' name='pm.pageNow'
						value="<s:property value='#parameters["pm.pageNow"]'/>">
					<input type='hidden' name='pm.rowCount'
						value="<s:property value='#parameters["pm.rowCount"]'/>">

					<input type="submit" id="btnSave" value="保存" class="button1">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" id="btnBack"
						onClick="self.window.history.go(-1)" class="button1" value="返回">
					<input align="right" type="button" id="btnAddNewLine"
						class="button1" value="加入一行">
				</p>
				<p>&nbsp;
					


			  </p>
				<p>&nbsp;
					


			  </p>

			</form>
			<p>&nbsp;
				


			</p>
		</div>

	</body>
	<script type="text/javascript">
        modelRow=$("#modelRow").clone(true);
    </script>
</html>
