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
<body>
	<table id="role-tb"></table>

	<div id="role_menu" style="display: none;">
		<div id="role-user-tb"></div>
	</div>
	<div id="user_select" style="display: none;">
		<div id="user-select-tb"></div>
	</div>
	<div id="permission-manage-box" style="display: none;padding-left:20px;">
		<ul class="easyui-tree" id="menu-tree"></ul>
	</div>
	<script type="text/javascript" src="static/js/role-manage.js"></script>
</body>
</html>