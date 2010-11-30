<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
              <table width="95%" border="0" cellspacing="0" cellpadding="0"
                class="rd1" align="center">
                <tr>
                    <td nowrap="nowrap" height="10" class="p2">
                        分销商信息
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
			分销商代码
		</td>
		<td class="rd6">
			分销商名称
		</td>
		<td class="rd6">
			分销商类型
		</td>
		<td class="rd6">
			分销商所在地
		</td>
		<td class="rd6">
			选择
		</td>
	</tr>      <s:set var="rows" value="0" />
                <s:iterator value='#request.clients' var='c' status="st" >
                <tr>
                
                 
                    <td class="rd8">              
                    <a  onclick='window.open("basedata/distrib/distrib!showDistribDetail?client.id=<s:property value='#c.id'/>","分销商详细信息" ,"width=500, height=400, scrollbars=no" );return false; ' href=''>
                          <s:property value='#c.clientno'/></a>
                    </td>
                    <td class="rd8">
           <s:property value='#c.name'/>
                    </td>
                    <td class="rd8">
                        <s:property value='#c.distribType.name'/>
                                 <s:property value='#c.terminalType.name'/>
                    </td>
                         <td class="rd8">
                           <s:property value='#c.region.name'/>
                    </td>
                    
                    
                      <td class="rd8">
                     <s:if test="#action.client.clientno==#c.clientno"> <input type="radio" name="selectFlag"   checked="checked" value="<s:property value='#c.clientno'/>"></s:if>
                       <s:else> <input type="radio" name="selectFlag"   value="<s:property value='#c.clientno'/>"></s:else>
                        <input type="hidden" name="client.name"   value="<s:property value='#c.name'/>">
                               <input type="hidden" name="client.id"   value="<s:property value='#c.id'/>">
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
                </tr>
                    </s:iterator>
            </table>