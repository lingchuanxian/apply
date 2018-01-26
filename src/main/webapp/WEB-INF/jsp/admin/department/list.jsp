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
	<div data-options="region:'west',split:true,title:'机构模块'"
		style="width: 300px;">
		<div class="easyui-panel" style="height:100%;">
			<ul id="tt" class="easyui-tree" style="padding-left:20px;">
			</ul>
		</div>
	</div>
	<div data-options="region:'center',fit:true,scroll:'no'">
		<table id="DepManage"  class="easyui-datagrid"></table>
	</div>
	<script type="text/javascript" src="static/js/department-manage.js"></script>
</body>
</html>