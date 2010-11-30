<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
   
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="css/dtree.css">
	<link rel="stylesheet" type="text/css" href="css/tab.css">

<script type="text/javascript" src="js/dtree.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery_tab.js"></script>
  </head>

	<body>

		<div class="tabs">
			<div class="tabItem">
				<div class="parentTab">
					分销商库存管理
				</div>
				<div class="children">
					<div class="childTab">
						<a href="inventory/inventory!initInventory?pm.pageNow=1&pm.pageSize=8" target="right"><img
								src="img/admin.gif" /> <br />分销商库存数量初始化</a>
								<br> <br>
					</div  >
					<div class="childTab">
						<a href="inventory/inventory!preConfirmInventorys?pm.pageNow=1&pm.pageSize=8" target="right"><img
								src="img/admin.gif" /> <br />分销商库存数量初始化确认</a>
								    <br> <br>
					</div>
					<div class="childTab">
						<a href="inventory/flowcard!listFlowcards?fc.status=L&pm.pageNow=1&pm.pageSize=8" target="right"><img
								src="img/admin.gif" /> <br />流向单维护</a>
								    <br> <br>
					</div>
					<div class="childTab">
                        <a href="inventory/flowcard!listFlowcardsS?fc.status=S&pm.pageNow=1&pm.pageSize=8" target="right"><img
                                src="img/admin.gif" /> <br />流向单审核</a>
                                    <br>
                                       <br>
                    </div>
                    <div class="childTab">
                        <a href="inventory/flowcard!listFlowcardsForSpot?fc.status=S&pm.pageNow=1&pm.pageSize=8" target="right"><img
                                src="img/admin.gif" /> <br />流向单抽查</a>
                                    <br> <br>
                    </div>
                    <div class="childTab">
                        <a href="inventory/flowcard!listFlowcardsForFuShen?fc.status=S&pm.pageNow=1&pm.pageSize=8" target="right"><img
                                src="img/admin.gif" /> <br />流向单复审</a>
                                    <br> <br>
                    </div>
                       <div class="childTab">
                        <a href="frame/right.jsp" target="right"><img
                                src="img/admin.gif" /> <br />盘点结果维护</a>
                                <br/><br/>
                                   
                    </div>
                        <div class="childTab">
                        <a href="frame/right.jsp" target="right"><img
                                src="img/admin.gif" /> <br />盘点结果审核</a>
                                   
                    </div>
				</div>
			</div>
		
	 
	       <div class="tabItem">
                <div class="parentTab">
                    结帐管理
                </div>
                <div class="children">
                    <div class="childTab">
                        <a href="frame/right.jsp" target="right"><img
                                src="img/admin.gif" /> <br />分销商库存数量初始化2</a>
                    </div>
                    <div class="childTab">
                        <a href="frame/right.jsp" target="right"><img
                                src="img/admin.gif" /> <br />分销商库存数量初始化2</a>
                    </div>
                    <div class="childTab">
                        <a href="frame/right.jsp" target="right"><img
                                src="img/admin.gif" /> <br />分销商库存数量初始化2</a>
                    </div>
                </div>
            </div>
                    <div class="tabItem">
                <div class="parentTab">
              付款结算 管理
                </div>
                <div class="children">
                    <div class="childTab">
                        <a href="frame/right.jsp" target="right"><img
                                src="img/admin.gif" /> <br />分销商库存数量初始化2</a>
                    </div>
                    <div class="childTab">
                        <a href="frame/right.jsp" target="right"><img
                                src="img/admin.gif" /> <br />分销商库存数量初始化2</a>
                    </div>
                    <div class="childTab">
                        <a href="frame/right.jsp" target="right"><img
                                src="img/admin.gif" /> <br />分销商库存数量初始化2</a>
                    </div>
                </div>
            </div>
                    <div class="tabItem">
                <div class="parentTab">
                    分销费管理
                </div>
                <div class="children">
                    <div class="childTab">
                        <a href="frame/right.jsp" target="right"><img
                                src="img/admin.gif" /> <br />分销商库存数量初始化2</a>
                    </div>
                    <div class="childTab">
                        <a href="frame/right.jsp" target="right"><img
                                src="img/admin.gif" /> <br />分销商库存数量初始化2</a>
                    </div>
                    <div class="childTab">
                        <a href="frame/right.jsp" target="right"><img
                                src="img/admin.gif" /> <br />分销商库存数量初始化2</a>
                    </div>
                </div>
            </div>
                    <div class="tabItem">
                <div class="parentTab">
                    统计报表管理
                </div>
                <div class="children">
                    <div class="childTab">
                        <a href="report/report!distribLevelMain" target="right"><img
                                src="img/admin.gif" /> <br />分销商级别分布图</a>
                    </div>
                    <div class="childTab">
                        <a href="frame/right.jsp" target="right"><img
                                src="img/admin.gif" /> <br />流向单录入审核报告</a>
                    </div>
                    <div class="childTab">
                        <a href="report/report!spotDistrib" target="right"><img
                                src="img/admin.gif" /> <br />流向单抽查报告</a>
                    </div>
                         <div class="childTab">
                        <a href="frame/right.jsp" target="right"><img
                                src="img/admin.gif" /> <br />分销商月度（调拨）明细表</a>
                    </div>     <div class="childTab">
                        <a href="frame/right.jsp" target="right"><img
                                src="img/admin.gif" /> <br />分销商 库存</a>
                    </div>
                </div>
            </div>
                    <div class="tabItem">
                <div class="parentTab">
                   基础数据管理
                </div>
                <div class="children">
                    <div class="childTab">
                        <a href="basedata/fiscalPeriod!listFiscalPeriod?pm.pageSize=5&pm.pageNow=1" target="right"><img
                                src="img/admin.gif" /> <br />会计核算期间维护 </a>
                    </div>
                    <div class="childTab">
                        <a href="basedata/material/material!listMaterials" target="right"><img
                                src="img/admin.gif" /> <br />物料维护</a>
                    </div>
                    <div class="childTab">
                        <a href="basedata/distrib/distrib_frame.jsp" target="right"><img
                                src="img/admin.gif" /> <br />分销商维护</a>
                    </div>
                     <div class="childTab">
                        <a href="frame/right.jsp" target="right"><img
                                src="img/admin.gif" /> <br />终端客户维护</a>
                    </div>
                </div>
            </div>
                                <div class="tabItem">
                <div class="parentTab">
                 系统管理
                </div>
                <div class="children">
                    <div class="childTab">
                        <a href="user/user!listUser?pageModel.pageSize=3&pageModel.pageNow=1" target="right"><img
                                src="img/admin.gif" />  <br />用户维护</a>
                    </div>
                    <br />
                     <div class="childTab">
                        <a href="user/user!proLogin" target="right"><img
                                src="img/admin.gif" /> <br />用户登陆</a>
                    </div>
                    
                </div>
            </div>
            </div>
	 

	 
 




	</body>
</html>
