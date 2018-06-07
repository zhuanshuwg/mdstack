<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="sidebar-left" class="span1">
	<div class="nav-collapse sidebar-nav">
		<ul class="nav nav-tabs nav-stacked main-menu">
			<li><a class="dropmenu" href="#"><i
					class="fa-icon-folder-close-alt"></i><span class="hidden-tablet">
						监控台</span></a>
				<ul>
					<li><a class="submenu" href="${ctx}/toRealMonitor"><i
							class="fa-icon-file-alt"></i><span class="hidden-tablet">
								实时监控</span></a></li>
					<li><a class="submenu" href="${ctx}/toRealStatistics"><i
							class="fa-icon-file-alt"></i><span class="hidden-tablet">
								实时统计</span></a></li>
				</ul>
			</li>
			<%-- <li><a href="${ctx}/user/toList"><i
					class="fa-icon-bar-chart"></i><span class="hidden-tablet">
						用户管理</span></a></li> --%>
			<li><a href="${ctx}/query/toHistoryWarn"><i
					class="fa-icon-bar-chart"></i><span class="hidden-tablet">
						历史报警查询</span></a></li>
			<li><a href="${ctx}/query/toHistoryPT"><i
					class="fa-icon-bar-chart"></i><span class="hidden-tablet">
						网络质量历史查询</span></a></li>
			<li><a class="dropmenu" href="#"><i
					class="fa-icon-folder-close-alt"></i><span class="hidden-tablet">
						统计报表</span></a>
				<ul>
					<li><a class="submenu" href="${ctx}/report/toDayReport"><i
							class="fa-icon-file-alt"></i><span class="hidden-tablet">
								日报</span></a></li>
					<li><a class="submenu" href="${ctx}/report/toWeekReport"><i
							class="fa-icon-file-alt"></i><span class="hidden-tablet">
								周报</span></a></li>
					<li><a class="submenu" href="${ctx}/report/toMonthReport"><i
							class="fa-icon-file-alt"></i><span class="hidden-tablet">
								月报</span></a></li>
				</ul>
			</li>
			
			<li><a class="dropmenu" href="#"><i
					class="fa-icon-folder-close-alt"></i><span class="hidden-tablet">
						配置管理</span></a>
				<ul>
					<li><a class="submenu" href="${ctx}/config/toMeetingplace"><i
							class="fa-icon-file-alt"></i><span class="hidden-tablet">
								会场管理</span></a></li>
					<li><a class="submenu" href="${ctx}/config/toMeeting"><i
							class="fa-icon-file-alt"></i><span class="hidden-tablet">
								会议管理</span></a></li>
					<li><a class="submenu" href="${ctx}/config/toMeetingroom"><i
							class="fa-icon-file-alt"></i><span class="hidden-tablet">
								评审室管理</span></a></li>
					<li><a class="submenu" href="${ctx}/config/toNetworkoperator"><i
							class="fa-icon-file-alt"></i><span class="hidden-tablet">
								运营商管理</span></a></li>
				</ul>
			</li>
		</ul>
	</div>
</div>
<a id="main-menu-toggle" class="hidden-phone open"><i
	class="fa-icon-reorder"></i></a>

