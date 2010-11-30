<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head lang="zh_CN" >
    <base href="<%=basePath%>">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <link rel="stylesheet" type="text/css" href="css/dtree.css">
        <script type='text/javascript'  src='js/dom_dtree.js'></script>
                <script type='text/javascript'  src='js/jquery.js'></script>
        <script type='text/javascript'>
    var tree = new dTree("tree");
    //this.add_(id, pid, name, url, title, target, null,null, open);
    $( function() {

   <s:iterator value="#request.regions" var="region"  >
        tree.add('<s:property value="#region.id"  escapeJavaScript="true"   />', '<s:property value="#region.parent.id"  default="" escapeJavaScript="true" />', '<s:property value="#region.name" escapeJavaScript="true"  />【地区】', 'basedata/distrib/distrib!region?region.id=<s:property value="#region.id"/>', '<s:property value="#region.name" escapeJavaScript="true"  />', 'distribDispAreaFrame',true);
      </s:iterator>
      <s:iterator value="#request.distributions" var="d"  >
      tree.add('<s:property value="#d.id"  />', '<s:property value="#d.region.id"  default="rootOfAllDistributions" escapeJavaScript="false" />', '<s:property value="#d.name" escapeJavaScript="false"  />【分销商】', 'basedata/distrib/distrib!distrib?distrib.id=<s:property value="#d.id"/>', '<s:property value="#d.name"  />', 'distribDispAreaFrame',true);
    </s:iterator>
        $("#distrib_tree").html(tree.toString());
//------------------------- 
 var  ctl=$("#ctl").click(
		 function(){
			 var th=$(this);
			 if (th.val()=='折叠'){
				 tree.closeAll();
				 th.val('展开');
			 }else{
				 tree.openAll();
                 th.val('折叠');
			 }
		 }
		 );

        
    }

    )
</script>

    </head>
  
  <body> 
<input type='button' value='折叠' id='ctl'  ><br/>
 
    <div id='distrib_tree'></div>
   
    
    <s:debug></s:debug>
  </body>
</html>
