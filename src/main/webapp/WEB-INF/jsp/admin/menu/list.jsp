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
<body class="easyui-layout" data-options="fit:false,scroll:'no'">
	<div data-options="region:'west',split:true,title:'主菜单模块'"
		style="width: 230px;">
		<div class="easyui-panel" style="height:100%;">
			<ul id="tt" class="easyui-tree" style="padding-left:20px;">
			</ul>
		</div>
	</div>
	<div data-options="region:'center',fit:false,scroll:'no'">
		<table id="FMenuManage"  class="easyui-datagrid"></table>
	</div>
	<script type="text/javascript" src="static/js/menu-manage.js"></script>
</body>
</html>