<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
        
                <table width="95%" border="1" cellspacing="0" cellpadding="0"
                    align="center" class="table1">
                    <tbody>
                    <tr>
                     
                        <td width="86" class="rd6">
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
                      
                        <td width="86" class="rd8">
                             <a  onclick='window.open("basedata/distrib/distrib!showDistribDetail?distrib.id=<s:property value='#it.distrib.id'/>","分销商详细信息" ,"width=500, height=400, scrollbars=no" );return false; ' href=''>
                          <s:property value='#it.distrib.clientno'/></a>
                        </td>
                        <td width="194" class="rd8">
                                <s:property value='#it.distrib.name'/>
                        </td>
                             <td width="93" class="rd8">
                           <a href="#"
                    onClick="window.open('basedata/material/material!listSingleMaterial?m.id=<s:property value="#it.material.id" />', '物料详细信息', 'width=400, height=400, scrollbars=no');return false;"> 
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
                </table>