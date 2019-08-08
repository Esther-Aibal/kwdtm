<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${fns:getConfig("productName")}</title>
	<%--<meta name="decorator" content="default"/>--%>
	<%--<%@include file="/WEB-INF/views/modules/wdt/include/commonCss.jsp"%>--%>
	<%@include file="/WEB-INF/views/modules/wdt/include/bootstrap3Head.jsp"%>
	<style>
		.page-header{margin:0}
		.dropdown-menu>li>a{color: white;}
		.accordion-heading{padding: 10px;font-weight: bold;}
	</style>
</head>
<body>
<c:set var="pageName" value="任务管理平台"/>
<c:set var="activePage" value="系统设置"/>
<%@include file="/WEB-INF/views/modules/wdt/include/pageHead.jsp"%>
<div class="page-body">
	<%@include file="/WEB-INF/views/modules/wdt/include/menuContent.jsp"%>
	<div id="content" class="row-fluid">
		<%--<div id="left" class="accordion-group">--%>
			<%--<div class="accordion-heading">--%>
		    	<%--<a class="accordion-toggle">组织机构<i class="icon-refresh pull-right fa fa-refresh" onclick="refreshTree();"></i></a>--%>
		    <%--</div>--%>
			<%--<div id="ztree" class="ztree"></div>--%>
		<%--</div>--%>
		<%--<div id="openClose" class="close">&nbsp;</div>--%>
		<div id="right" style="width:100%">
			<iframe id="officeContent" src="${ctx}/sys/user/list" width="100%" height="1400px" frameborder="0"></iframe>
		</div>
	</div>
</div>
<sys:message content="${message}"/>
<%--<%@include file="/WEB-INF/views/include/treeview.jsp" %>--%>
<%--<style type="text/css">--%>
	<%--.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}--%>
<%--</style>--%>
	<%--<script type="text/javascript">--%>
		<%--var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'}},--%>
			<%--callback:{onClick:function(event, treeId, treeNode){--%>
					<%--var id = treeNode.id == '0' ? '' :treeNode.id;--%>
					<%--$('#officeContent').attr("src","${ctx}/sys/user/list?office.id="+id+"&office.name="+treeNode.name);--%>
				<%--}--%>
			<%--}--%>
		<%--};--%>
		<%----%>
		<%--function refreshTree(){--%>
			<%--$.getJSON("${ctx}/sys/office/treeData",function(data){--%>
				<%--data = data.length ? data : ${fns:findAllOffice()}--%>
				<%--$.fn.zTree.init($("#ztree"), setting, data).expandAll(true);--%>
			<%--});--%>
		<%--}--%>
		<%--refreshTree();--%>
		 <%----%>
		<%--var leftWidth = 250; // 左侧窗口大小--%>
		<%--var htmlObj = $("html"), mainObj = $("#main");--%>
		<%--var frameObj = $("#left, #openClose, #right, #right iframe");--%>
		<%--function wSize(){--%>
			<%--var strs = getWindowSize().toString().split(",");--%>
			<%--htmlObj.css({"overflow-x":"hidden", "overflow-y":"hidden"});--%>
			<%--mainObj.css("width","auto");--%>
			<%--frameObj.height(strs[0] - 5);--%>
			<%--var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());--%>
			<%--$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);--%>
			<%--$(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);--%>
		<%--}--%>
	<%--</script>--%>
	<%--<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>--%>
<script>
//	document.getElementById('officeContent').height = window.innerHeight+123+'px';
</script>
</body>
</html>