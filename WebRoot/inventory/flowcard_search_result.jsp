<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
                
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
                          <td width="31" height="32" class="rd6">
                            <input type="checkbox" name="selectAll"   >
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
                      
                      <td width="31" class="rd8">
                            <input type="checkbox" name="fc.id" class="checkbox1"  
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
                        </td>
                        </tr>

                    </s:iterator>
                </s:if>
                 
                   </tbody>
                </table>
