<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<style>
<!--
.navbar-inverse .nav li.dropdown.open>.dropdown-toggle, .navbar-inverse .nav li.dropdown.active>.dropdown-toggle, .navbar-inverse .nav li.dropdown.open.active>.dropdown-toggle{background-color:#0A246A}
-->
</style>
	
	<div class="navbar navbar-inverse" style="margin: 0px;">
		<div class="navbar-inner" style=" background: #0A246A;color: #FFF;font-size: 12px;">
			<div style="float: left;width:400px;">
				<img src="${ctx}/plug-in/busi/img/guohe_logo_01.png;" style="margin-left:-15px;"/>
				<span style="margin-left:-10px;font-size: 26px;font-weight: 700;color:#FFF;"></span>
			</div>
			<div class="navbar-form pull-right" style="margin: 5px;">
			<span style="cursor: pointer;" title="个人信息" 
			<c:if test="${sessionScope.versionType != 0}">
			onclick="javascript:window.location.href='${ctx}/user/person_info';"
			</c:if>
			>${sessionScope.SESSION_INFO.user.trueName }
			</span>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<span style="cursor: pointer;" onclick="javascript:window.location.href='${ctx}/exit';">注销</span>
			&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<span style="cursor: pointer;" onclick="javascript:window.location.href='${ctx}/about';">关于</span>
			</div>
			<div style="margin: 30px 0px 0px 0px;">
				
				<ul id="log_ul" class="nav" style="font-size: 12px;margin: 0px;">
					<t:permission type="cluster" menuId="-1">
						<t:permission type="menu" menuId="10000"><li><a href="${ctx}/home/main">概况</a></li></t:permission>
						
						<t:permission type="menu" menuId="11000">
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown">日志搜索 <b class="caret"></b></a>
								<ul class="dropdown-menu">
									<t:permission type="menu" menuId="11010"><li><a href="${ctx}/search">日志搜索</a></li></t:permission>
									<t:permission type="menu" menuId="11020"><li><a href="${ctx}/real_monitor">实时监控</a></li></t:permission>
									<t:permission type="menu" menuId="11030">
										<li class="dropdown-submenu"><a tabindex="-1" href="#">搜索计划</a>
											<ul class="dropdown-menu">
												<t:permission type="menu" menuId="11031"><li><a href="${ctx}/warn_monitor">通知监控</a></li></t:permission>
												<t:permission type="menu" menuId="11032"><li><a href="${ctx}/schedule_result">执行结果</a></li></t:permission>
											</ul>
										</li>
									</t:permission>
								</ul>
							</li>	
						</t:permission>
						
						<t:permission type="menu" menuId="12000">
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown">流量搜索 <b class="caret"></b></a>
								<ul class="dropdown-menu">
									<t:permission type="menu" menuId="12010"><li><a href="${ctx}/flow/search">搜索工具</a></li></t:permission>
									<t:permission type="menu" menuId="12020"><li><a href="${ctx}/flow/spi/graph">流量会话分析</a></li></t:permission>
									<t:permission type="menu" menuId="12030"><li><a href="${ctx}/flow/spi/view">流量会话统计</a></li></t:permission>
									<t:permission type="menu" menuId="12040"><li><a href="${ctx}/flow/spi/connections">流量连接分析</a></li></t:permission>
								</ul>
							</li>	
						</t:permission>
						<t:permission type="menu" menuId="13000">
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown">应用 <b class="caret"></b></a>
								<ul class="dropdown-menu">
									<c:forEach var="app" items="${sessionScope.AppMenuList}">
										<li><a target="_blank" href="${ctx}/config/dashboard/index?appId=${app.appid}"> ${app.appname}</a></li>
									</c:forEach>
								</ul>
							<%-- <a href="${ctx}/config/dashboard/index" target="_blank">应用</a> --%>
							
						</li></t:permission>
						<t:permission type="menu" menuId="14000"><li><a href="${ctx}/esWarn/warn_page">报警</a></li></t:permission>
						<t:permission type="menu" menuId="15000">
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown">违规监控 <b class="caret"></b></a>
								<ul class="dropdown-menu">
									<t:permission type="menu" menuId="15010"><li><a  href="${ctx}/srvmon">当日违规事件监测</a></li></t:permission>
									<t:permission type="menu" menuId="15020"><li><a  href="${ctx}/srvmonstatis">历史违规事件统计</a></li></t:permission>
								</ul>
						</li></t:permission>
						<t:permission type="menu" menuId="16000">
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown">网络服务 <b class="caret"></b></a>
								<ul class="dropdown-menu">
									<t:permission type="menu" menuId="16010"><li><a  href="${ctx}/survey">网络访问概况</a></li></t:permission>
									<t:permission type="menu" menuId="16020"><li><a  href="${ctx}/netWorkService">网络服务统计</a></li></t:permission>
									<t:permission type="menu" menuId="16030"><li><a  href="${ctx}/servicehistory">历史网络服务查询</a></li></t:permission>
									<t:permission type="menu" menuId="16040"><li><a  href="${ctx}/srvmonassets">资产对外访问统计</a></li></t:permission>
									<t:permission type="menu" menuId="16050"><li><a  href="${ctx}/srvmonregion/srvmoncountrywide">全国访客地域分布</a></li></t:permission>
									<t:permission type="menu" menuId="16060"><li><a  href="${ctx}/srvmonregion/srvmonprovince">省内访客地域分布</a></li></t:permission>
									<t:permission type="menu" menuId="16070"><li><a  href="${ctx}/terminal">访客终端类型分析</a></li></t:permission>
								</ul>
						</li></t:permission>
				
						<t:permission type="menu" menuId="1400"><li><a href="${ctx}/web_upload">文件上传</a></li></t:permission>
						
					</t:permission>
				</ul>
			</div>
			<div style="margin: 30px 0px 0px 0px;">
				<ul class="nav pull-right" style="font-size: 12px;margin: 0px;">
					<c:if test="${sessionScope.versionType != 0}">
					<t:permission type="menu" menuId="18000">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown">用户管理 <b class="caret"></b></a>
							<ul class="dropdown-menu">
								<t:permission type="menu" menuId="18010"><li><a href="${ctx}/user/main">用户管理</a></li></t:permission>
								<t:permission type="menu" menuId="18020"><li><a href="${ctx}/role/main">角色管理</a></li></t:permission>
							</ul>
						</li>
					</t:permission>
					</c:if>
					
					<t:permission type="menu" menuId="19000">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">系统管理 <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<t:permission type="menu" menuId="19010">
								<li class="dropdown-submenu pull-left"><a tabindex="-1" href="#">黑名单管理</a>
									<ul class="dropdown-menu">
										<t:permission type="menu" menuId="19011"><li><a href="${ctx}/domainList/domain">域名管理</a></li></t:permission>
										<t:permission type="menu" menuId="19012"><li><a href="${ctx}/ipList/ip">IP管理</a></li></t:permission>
										<t:permission type="menu" menuId="19013"><li><a href="${ctx}/emailList/email">邮箱管理</a></li></t:permission>
										<t:permission type="menu" menuId="19014"><li><a href="${ctx}/urlList/url">URL管理</a></li></t:permission>
									</ul>
								</li>
							</t:permission>
							
							<t:permission type="menu" menuId="19130">
							<li class="dropdown-submenu pull-left"><a tabindex="-1" href="#">资产管理</a>
									<ul class="dropdown-menu">
										<t:permission type="menu" menuId="19131"><li><a href="${ctx}/assetRegist/index">资产登记</a></li></t:permission>
										<%-- <li><a href="${ctx}/asset/index">资产登记</a></li> --%>
										<t:permission type="menu" menuId="19132"><li><a href="${ctx}/zclist/resource">资源管理</a></li></t:permission>
										<t:permission type="menu" menuId="19133"><li><a href="${ctx}/organize/index">机构设置</a></li></t:permission>
										<t:permission type="menu" menuId="19134"><li><a href="${ctx}/zclist/index">字典设置</a></li></t:permission>
										<t:permission type="menu" menuId="19135"><li><a href="${ctx}/visitRule/index">访问规则</a></li></t:permission>
										<t:permission type="menu" menuId="19136"><li><a href="${ctx}/asset/assetService">资产服务</a></li></t:permission>
									</ul>
							</li>
							</t:permission>
							
							
							
							<t:permission type="menu" menuId="19020"><li><a href="#">白名单管理</a></li></t:permission>
							
							<t:permission type="cluster" menuId="-1">
							
							<t:permission type="menu" menuId="19030">
								<li class="divider"></li>
								<li class="dropdown-submenu pull-left"><a tabindex="-1" href="#">日志数据源</a>
									<ul class="dropdown-menu">
										<t:permission type="menu" menuId="19031"><li><a href="${ctx}/config/datasource/toConfigGroup">数据源分组</a></li>
										<li class="divider"></li></t:permission>
										<t:permission type="menu" menuId="19032"><li><a href="${ctx}/config/datasource/toSysLog">SYSLOG配置</a></li></t:permission>
										<t:permission type="menu" menuId="19033"><li><a href="${ctx}/config/datasource/toPullFile">抓取文件配置</a></li></t:permission>
										<t:permission type="menu" menuId="19034"><li><a href="${ctx}/config/datasource/toDataBase">数据库读取配置</a></li></t:permission>
										<t:permission type="menu" menuId="19035"><li><a href="${ctx}/config/datasource/config_import">配置导入</a>
										</li><li class="divider"></li></t:permission>
										<t:permission type="menu" menuId="19036">
										<li><a href="${ctx}/config/host_info">主机信息设置</a></li></t:permission>
										<t:permission type="menu" menuId="19037"><li><a href="${ctx}/config/dn_info">数据源类别设置</a></li></t:permission>
									</ul>
								</li>
							</t:permission>
							
							<t:permission type="menu" menuId="19040"><li><a href="${ctx}/config/toNormalization">日志字段正规化</a></li></t:permission>
							
							<t:permission type="menu" menuId="19050"><li><a href="${ctx}/config/toSearchMng">日志搜索管理</a></li></t:permission>
							<t:permission type="menu" menuId="19060"><li><a href="${ctx}/config/toSchedule">日志搜索计划管理</a></li></t:permission>
							
							<t:permission type="menu" menuId="19070">
							<li class="divider"></li>
								<li class="dropdown-submenu pull-left"><a tabindex="-1" href="#">应用管理</a>
									<ul class="dropdown-menu">
<%-- 										<t:permission type="menu" menuId="1981"><li><a href="${ctx}/config/dashboard/widgetType">组件分类管理</a></li></t:permission>
 --%>										<t:permission type="menu" menuId="19071"><li><a href="${ctx}/app/mng/widget">组件管理</a></li></t:permission>
<%-- 										<t:permission type="menu" menuId="1983"><li><a href="${ctx}/config/dashboard/menuList">应用菜单管理</a></li></t:permission>
 --%>										<t:permission type="menu" menuId="19072"><li><a href="${ctx}/app/mng/app">应用管理</a></li></t:permission>
									</ul>
								</li>
							</t:permission>
							
							<t:permission type="menu" menuId="19080"><li class="divider"></li>
							<li><a href="${ctx}/config/toTimeRangeConfig">时间范围设置</a></li></t:permission>
							</t:permission>
							
							<t:permission type="menu" menuId="19090">
							<li class="divider"></li>
								<li><a href="${ctx}/config/network_config">网络设置</a></li></t:permission>
							<c:if test="${sessionScope.versionType != 0}">
							<t:permission type="menu" menuId="19100"><li><a href="${ctx}/config/cluster_config">集群设置</a></li>
							</t:permission>
							</c:if>
							
							<t:permission type="menu" menuId="19110"><li><a href="#">参数设置</a></li></t:permission>
							<t:permission type="menu" menuId="19120">
								<li class="divider"></li>
								<li><a href="${ctx}/esInfo/index">索引存储监控</a></li></t:permission>
								<t:permission type="menu" menuId="19140"><li><a href="${ctx}/config/system_config">系统配置</a></li></t:permission>
						</ul>
					</li>
					</t:permission>
				</ul>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		var fl = '<%=session.getAttribute("FL")%>';
	</script>
