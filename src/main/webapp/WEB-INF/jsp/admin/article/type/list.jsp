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
<jsp:include page="../../include_header.jsp"></jsp:include>
</head>
<body>
	<div id="toolbar">
		<shiro:hasPermission name="system:article:type:insert">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-application-form-add" plain="true" id="add">新增</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="system:article:type:update">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-application-form-edit" plain="true" id="edit">编辑</a>
		</shiro:hasPermission>
		<a href="javascript:;" class="easyui-linkbutton"
			iconCls="icon-disk" plain="true" id="save" style="display:none;">保存</a>
		<a href="javascript:;" class="easyui-linkbutton"
			iconCls="icon-undo" plain="true" id="cancle" style="display:none;">取消编辑</a>
		<shiro:hasPermission name="system:article:type:delete">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-application-form-delete" plain="true" id="delete">删除</a>
		</shiro:hasPermission>
	</div>
	<table id="art-tb"></table>
	<script type="text/javascript" src="static/js/article-type-manage.js"></script>
</body>
</html>