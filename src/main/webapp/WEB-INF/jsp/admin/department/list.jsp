<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@	taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
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
	<div id="toolbar">
		<shiro:hasPermission name="system:department:insert">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-application-form-add" plain="true" id="add">新增</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="system:department:edit">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-application-form-edit" plain="true" id="edit">编辑</a>
		</shiro:hasPermission>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-disk"
			plain="true" id="save" style="display: none;">保存</a> <a
			href="javascript:;" class="easyui-linkbutton" iconCls="icon-undo"
			plain="true" id="cancle" style="display: none;">取消编辑</a>
		<shiro:hasPermission name="system:department:delete">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-application-form-delete" plain="true" id="delete">删除</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="system:department:exchange">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-arrow-up" plain="true" id="up">上移</a>
				<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-arrow-down" plain="true" id="down">下移</a>
		</shiro:hasPermission>
	</div>
	<div data-options="region:'center',fit:true,scroll:'no'">
		<table id="DepManage"  class="easyui-datagrid"></table>
	</div>
	<script type="text/javascript" src="static/js/department-manage.js"></script>
</body>
</html>