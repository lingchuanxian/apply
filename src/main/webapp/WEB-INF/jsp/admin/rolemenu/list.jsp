<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.contextPath }/">
<meta charset="UTF-8" />
<title>数字办公系统</title>
<jsp:include page="../include_header.jsp"></jsp:include>
</head>
<body class="easyui-layout" data-options="fit:true">
	<div data-options="region:'west',split:true,title:'系统用户角色'"
		style="width: 35%;">
		<div class="easyui-panel" style="height: 100%;">
			<table id="role-tb" class="easyui-datagrid"></table>
		</div>
	</div>
	<div data-options="region:'center',fit:false,scroll:'no'">
			<table id="menu-tt" class="easyui-treegrid"></table>
	</div>
	<script type="text/javascript" src="static/easyui/treegridExtend.js"></script>
	<script type="text/javascript" src="static/js/rolemenu-manage.js"></script>
</body>
</html>