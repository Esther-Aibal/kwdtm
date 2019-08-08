<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>${fns:getConfig("productName")}</title>
<%--<meta name="decorator" content="default"/>--%>
<link rel="stylesheet" href="${ctxStatic}/common/jeesite.css">
<%--<%@include file="/WEB-INF/views/modules/wdt/include/commonCss.jsp"%>--%>
<%@include file="/WEB-INF/views/modules/wdt/include/bootstrap3Head.jsp"%>
<%--<script src="${ctxStatic}/jquery/jquery.min.js"></script>--%>
<%--<script src="${ctxStatic}/bootstrap/3.3.7/bootstrap.min.js"></script>--%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#btnExport").click(function(){
			top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
				if(v=="ok"){
					$("#searchForm").attr("action","${ctx}/sys/user/export");
					$("#searchForm").submit();
				}
			},{buttonsFocus:1});
			top.$('.jbox-body .jbox-icon').css('top','55px');
		});
		$("#btnImport").click(function(){
			$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
		});
	});
	function page(n,s){
		if(n) $("#pageNo").val(n);
		if(s) $("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/sys/user/list");
		$("#searchForm").submit();
    	return false;
    }
</script>
<style>
.btn-search-task{
    height: 28px;
    width: 80px;
    color: white;
    background: #ee7701;
    border-radius: 3px;
    border:none;
    font-family: Microsoft YaHei;
    font-weight: bold;
}

.page-body{
	padding: 10px 80px;
}
.form-search input{
	line-height: 20px;
}
.pagination ul>li {
	display: inline;
	line-height: 20px;
}
.pagination ul>li:first-child>a, .pagination ul>li:first-child>span {
	border-left-width: 1px;
	-webkit-border-bottom-left-radius: 4px;
	border-bottom-left-radius: 4px;
	-webkit-border-top-left-radius: 4px;
	border-top-left-radius: 4px;
	-moz-border-radius-bottomleft: 4px;
	-moz-border-radius-topleft: 4px;
}
.pagination ul>.disabled>span, .pagination ul>.disabled>a, .pagination ul>.disabled>a:hover, .pagination ul>.disabled>a:focus {
	color: #999;
	cursor: default;
	background-color: transparent;
}
.pagination ul>li>a, .pagination ul>li>span {
	float: left;
	padding: 4px 12px;
	line-height: 20px;
	text-decoration: none;
	background-color: #fff;
	border: 1px solid #ddd;
	border-left-width: 0;
}


.dropdown-menu {
	border: medium none;
	display: none;
	float: left;
	font-size: 12px;
	left: 0;
	list-style: none outside none;
	padding: 0;
	position: absolute;
	text-shadow: none;
	top: 100%;
	z-index: 1000;
	border-radius: 0;
	box-shadow: 0 0 3px rgba(86, 96, 117, .3)
}

.dropdown-menu>li>a {
	border-radius: 3px;
	color: inherit;
	line-height: 25px;
	margin: 4px;
	text-align: left;
	font-weight: 400
}

.dropdown-menu>li>a.font-bold {
	font-weight: 600
}

.navbar-top-links .dropdown-menu li {
	display: block
}

.navbar-top-links .dropdown-menu li:last-child {
	margin-right: 0
}

.navbar-top-links .dropdown-menu li a {
	padding: 3px 20px;
	min-height: 0
}

.navbar-top-links .dropdown-menu li a div {
	white-space: normal
}
.dropdown-menu>li>a {
    margin:0;
    padding:4px;
    border-radius:0;
}
.dropdown-menu>li>a:hover {
	background: #F5F5F5;
	color:#000;
}
.dropdown-menu .divider {
    height:0;
    margin:0;
}

#wide-bread {border-bottom:6px solid #fff;}
#wide-bread ol {width:1200px;box-sizing:border-box;margin:0 auto;background:url('${ctxStatic}/wdt/img/new/icon_home.png') no-repeat center left;padding-left:20px;}
#wide-bread ol li a {text-shadow: none;}

</style>
</head>
<body>

<!-- <div id="wide-bread">
    <ol class="breadcrumb">
        <li><a href="${ctx}/rest/wdt/homePage">首页</a></li>
        <li><a href="javascript:;">系统设置</a></li>
        <li><a href="javascript:;">用户管理</a></li>
    </ol>
</div> -->

<div class="page-body">
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/sys/user/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/sys/user/import/template">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/list">用户列表</a></li>
		<%-- <shiro:hasPermission name="sys:user:edit"><li><a href="${ctx}/sys/user/form">用户添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>登录名：</label><form:input path="loginName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>姓&nbsp;&nbsp;&nbsp;名：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn-search-task" type="submit" value="查询" onclick="return page();" style="margin-left:20px;box-shadow:none;padding-left:26px;padding-right:26px;border:none;color:#fff;text-shadow:none;" onclick="window.dt.ajax.reload();"/>
				<!-- <input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
				<input id="btnImport" class="btn btn-primary" type="button" value="导入"/> --></li>
			<li class="clearfix"></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>

	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th class="sort-column login_name"><span style="color: #000000">登录名</span></th><th class="sort-column name"><span style="color: #000000">姓名</span></th><th>电话</th><th>手机</th><%--<th>角色</th> --%><shiro:hasPermission name="sys:user:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="user">
			<tr>
				<td><a href="${ctx}/sys/user/form?id=${user.id}">${user.loginName}</a></td>
				<td>${user.name}</td>
				<td>${user.phone}</td>
				<td>${user.mobile}</td><%--
				<td>${user.roleNames}</td> --%>
				<shiro:hasPermission name="sys:user:edit"><td>
    				<a href="${ctx}/sys/user/form?id=${user.id}"><span style="color: #2fa4e7">修改</span></a>
					<%-- <a href="${ctx}/sys/user/delete?id=${user.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a> --%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</div>
</body>
</html>