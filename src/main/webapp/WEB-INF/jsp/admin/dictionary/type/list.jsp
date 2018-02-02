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
		<shiro:hasPermission name="system:dictionary:type:insert">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-application-form-add" plain="true" id="add">新增</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="system:dictionary:type:update">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-application-form-edit" plain="true" id="edit">编辑</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="system:dictionary:type:delete">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-application-form-delete" plain="true" id="delete">删除</a>
		</shiro:hasPermission>
	</div>
	<table id="data-table"></table>
	
	<div class="box" id="add-box" style="display: none;">
		<form id="add-form" method="post">
			<table class="rb-org" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="td_key">编码：</td>
					<td class="td_val"><input class="easyui-textbox" type="text"
						name="dtCode" data-options="required:true,validType:'isDictionaryTypeCodeExist'"
						missingMessage="请填写编码" /></td>
				</tr>
				<tr>
					<td class="td_key">名称：</td>
					<td class="td_val"><input class="easyui-textbox" type="text"
						name="dtName" data-options="required:true"
						missingMessage="请填写名称" /></td>
				</tr>
				<tr>
					<td class="td_key">描述：</td>
					<td class="td_val"><input class="easyui-textbox"
						data-options="multiline:true" type="text" name="dtDescription"
						style="width: 200px; height: 100px" /></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div class="box" id="edit-box" style="display: none;">
		<form id="edit-form" method="post">
			<table class="rb-org" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="td_key">编码：</td>
					<td class="td_val">
					<input type="hidden" id="edit-dtId" name="dtId">
					<input class="easyui-textbox" type="text"
						name="dtCode" data-options="required:true"
						missingMessage="请填写编码" id="edit-dtCode"/></td>
				</tr>
				<tr>
					<td class="td_key">名称：</td>
					<td class="td_val"><input class="easyui-textbox" type="text"
						name="dtName" data-options="required:true"
						missingMessage="请填写名称" id="edit-dtName"/></td>
				</tr>
				<tr>
					<td class="td_key">描述：</td>
					<td class="td_val"><input class="easyui-textbox"
						data-options="multiline:true" type="text" name="dtDescription"
						style="width: 200px; height: 100px" id="edit-dtDescription"/></td>
				</tr>
			</table>
		</form>
	</div>
	
	<script type="text/javascript" src="static/js/dictionary-type-manage.js"></script>
</body>
</html>