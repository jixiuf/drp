<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
                   
                    <td width="170" class="rd8"><a href="#"
                    onClick="window.open('basedata/material/material!listSingleMaterial?m.id=<s:property value="#m.id" />', '物料详细信息', 'width=400, height=400, scrollbars=no');return false;"> 
                         <s:property value='#m.no'/>
                </a>
                    
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
                      <s:if test="#m.no==#action.m.no"><input type="radio" name="selectFlag"   checked="checked" value="<s:property value='#m.id'/>"></s:if>
                      <s:else>  <input type="radio" name="selectFlag"   value="<s:property value='#m.no'/>"></s:else>
                            <input type="hidden" name="m.id"   value="<s:property value='#m.no'/>">
                        <input type="hidden" name="m.name"   value="<s:property value='#m.name'/>">
                             <input type="hidden" name="m.guige"   value="<s:property value='#m.guige'/>">
                        <input type="hidden" name="m.xinghao"   value="<s:property value='#m.xinghao'/>">
                        <input type="hidden" name="m.unit"   value="<s:property value='#m.materialType.name'/>">
                          <input type="hidden" name="m.materialType"   value="<s:property value='#m.materialType.name'/>">
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