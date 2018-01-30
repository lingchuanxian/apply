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
<body>
	
	<div id="toolbar">
		<shiro:hasPermission name="system:menu:insert">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-lock-add" plain="true" id="add">新增</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="system:menu:update">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-lock-edit" plain="true" id="edit">编辑</a>
		</shiro:hasPermission>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-disk"
			plain="true" id="save" style="display: none;">保存</a> <a
			href="javascript:;" class="easyui-linkbutton" iconCls="icon-undo"
			plain="true" id="cancle" style="display: none;">取消编辑</a>
		<shiro:hasPermission name="system:menu:delete">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-lock-delete" plain="true" id="delete">删除</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="system:userRole:select">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-user-group" plain="true" id="user-manage">成员管理</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="system:role:updatepermission">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-lock-key" plain="true" id="permission-manage">权限管理</a>
		</shiro:hasPermission>
	</div>

	<table id="role-tb"></table>

	<div id="role_menu" style="display: none;">
		<div id="role-user-tb"></div>
	</div>
	
	<div id="toolbar2">
		<shiro:hasPermission name="system:userRole:insert">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-lock-add" plain="true" id="add2">新增</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="system:userRole:delete">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-lock-edit" plain="true" id="delete2">移除</a>
		</shiro:hasPermission>
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