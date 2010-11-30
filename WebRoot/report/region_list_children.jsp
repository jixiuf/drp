<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	<select name="secondLevelRegion" class="select1"   
								id="secondLevelRegion">
								<option value="-1"   selected="selected"  >
									--全部--
								</option>
								<s:iterator value="#request.regionChildren"
									var="secondLevelRegion">
									<option value="<s:property value="#secondLevelRegion.id"/>">
										<s:property value="#secondLevelRegion.name" />
									</option>
								</s:iterator>
							</select>
