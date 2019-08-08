<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${fns:getConfig("productName")}</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/common/jeesite.css">
	<%--<%@include file="/WEB-INF/views/modules/wdt/include/commonCss.jsp"%>--%>
	<%--<%@include file="/WEB-INF/views/modules/wdt/include/bootstrap3Head.jsp"%>--%>
	<style>
		.page-body .main-content{padding: 10px 80px;}



/* 导航高度错乱 */
.page-body .menu-content {
	box-sizing: border-box;
}


/* 导航标签箭头居顶错位 */
.dropdown .caret {
	margin-top: 0;
	border-top-color: #666;
	vertical-align: middle;
}

.menu-content span {
    font-size:14px;
}

.menu-content span.active .caret {
    border-top-color: #fff;
}


#wide-bread {border-bottom:6px solid #fff;}
#wide-bread ol {width:1200px;box-sizing:border-box;margin:0 auto;background:url('${ctxStatic}/wdt/img/new/icon_home.png') no-repeat center left;padding-left:20px;}
#wide-bread ol li a {text-shadow: none;}
	</style>
	<%--<script src="${ctxStatic}/task/js/bootstrap.min.js?v=3.3.5"></script>--%>
	<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js"></script>
</head>
<body>
<c:set var="pageName" value="任务管理平台"/>
<c:set var="activePage" value="系统设置"/>
<%@include file="/WEB-INF/views/modules/wdt/include/pageHead.jsp"%>
<div class="page-body">
<%@include file="/WEB-INF/views/modules/wdt/include/menuContent.jsp"%>

<!-- <div id="wide-bread">
    <ol class="breadcrumb">
        <li><a href="${ctx}/rest/wdt/homePage">首页</a></li>
        <li><a href="javascript:;">系统设置</a></li>
        <li><a href="javascript:;">角色管理</a></li>
    </ol>
</div> -->

<div class="main-content">

	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/role/">角色列表</a></li>
		<shiro:hasPermission name="sys:role:edit"><li><a href="${ctx}/sys/role/form">角色添加</a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr><th>角色名称</th><th>英文名称</th><shiro:hasPermission name="sys:role:edit"><th>操作</th></shiro:hasPermission></tr>
		<c:forEach items="${list}" var="role">
			<tr>
				<td><a href="${ctx}/sys/role/form?id=${role.id}">${role.name}</a></td>
				<td><a href="${ctx}/sys/role/form?id=${role.id}">${role.enname}</a></td>
				<shiro:hasPermission name="sys:role:edit"><td>
					<%-- <a href="${ctx}/sys/role/assign?id=${role.id}">分配</a> --%>
					<a href="${ctx}/sys/role/form?id=${role.id}">修改</a>
					<a href="${ctx}/sys/role/delete?id=${role.id}" onclick="return confirmx('确认要删除该角色吗？', this.href)">删除</a>
				</td></shiro:hasPermission>	
			</tr>
		</c:forEach>
	</table>
</div>
	</div>

</body>
</html>