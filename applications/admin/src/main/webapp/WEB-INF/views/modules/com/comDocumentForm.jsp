<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>文章管理</title>
    <meta name="decorator" content="default"/>
    <!-- 配置文件 -->
    <script type="text/javascript" src="${ctxStatic}/ueditor/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="${ctxStatic}/ueditor/ueditor.all.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function(form){
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function(error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
        /* 实例化编辑器 */
        var ue = UE.getEditor('content', {
                toolbars: [
                    ['fullscreen','simpleupload', 'link', '|', 'undo', 'redo', '|',
                     'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|',
                     'forecolor', 'backcolor', '|',
                     'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                     'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                     'directionalityltr', 'directionalityrtl', 'indent', '|',
                     'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify','|',
                     'imagenone', 'imageleft', 'imageright', 'imagecenter']
                ],
                autoHeightEnabled: true,
                autoFloatEnabled: true
            });
    </script>
</head>
<body>
    <ul class="nav nav-tabs">
        <li><a href="${ctx}/com/comDocument/">文章列表</a></li>
        <li class="active"><a href="${ctx}/com/comDocument/form?id=${comDocument.id}">文章<shiro:hasPermission name="com:comDocument:edit">${not empty comDocument.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="com:comDocument:edit">查看</shiro:lacksPermission></a></li>
    </ul><br/>
    <form:form id="inputForm" modelAttribute="comDocument" action="${ctx}/com/comDocument/save" method="post" class="form-horizontal">
        <form:hidden path="id"/>
        <sys:message content="${message}"/>        
        <div class="control-group">
            <label class="control-label">标题：</label>
            <div class="controls">
                <form:input path="title" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">内容：</label>
            <div class="controls">
	            <textarea id="content" name="content" htmlEscape="false" rows="8" class="input-xlarge " style="width:700px;" cssClass="required">
	                    ${comDocument.content}
	            </textarea>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">备注信息：</label>
            <div class="controls">
                <form:textarea path="remarks" htmlEscape="false" rows="2" maxlength="250" class="input-xxlarge "/>
            </div>
        </div>
        <div class="form-actions">
            <shiro:hasPermission name="com:comDocument:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
        </div>
    </form:form>
</body>
</html>