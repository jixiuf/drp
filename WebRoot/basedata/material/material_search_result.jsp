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

		<td width="155" class="rd6">
			物料代码
		</td>
		<td width="155" class="rd6">
			物料名称
		</td>
		<td width="155" class="rd6">
			物料规格
		</td>
		<td width="155" class="rd6">
			物料型号
		</td>
		<td width="138" class="rd6">
			类别
		</td>
		<td width="101" class="rd6">
			计量单位
		</td>
		<td width="35" class="rd6">
			<input type="checkbox" name="selectAll" id='selectAll'>
		</td>
	</tr>
	<s:set var="rows" value="0" />
	<s:iterator value="#request.materials" var='material' status="st">
		<tr>
			<td class="rd8">
				<a href="#"
					onClick="window.open('basedata/material/material!listSingleMaterial?m.id=<s:property value="#material.id" />', '物料详细信息', 'width=400, height=400, scrollbars=no');return false;"><s:property
						value="#material.no" />
				</a>
			</td>
			<td class="rd8">
				<s:property value="#material.name" />
			</td>
			<td class="rd8">
				<s:property value="#material.guige" />
			</td>
			<td class="rd8">
				<s:property value="#material.xinghao" />
			</td>
			<td class="rd8">
				<s:property value="#material.materialType.name" />
			</td>
			<td class="rd8">
				<s:property value="#material.materialItemUnit.name" />
			</td>
			<s:if test="#st.last">
				<s:set name="rows" value="#st.count" />
			</s:if>
			<td class="rd8">
				<input type="checkbox" name="m.id"
					value='<s:property value="#material.id" />' class="checkbox1">
			</td>

		</tr>
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
			</tr>

		</s:iterator>
	</s:if>


</table>