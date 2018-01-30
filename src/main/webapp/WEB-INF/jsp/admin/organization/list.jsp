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
		<shiro:hasPermission name="system:organization:insert">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-application-form-add" plain="true" id="add">新增</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="system:organization:update">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-application-form-edit" plain="true" id="edit">编辑</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="system:organization:delete">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-application-form-delete" plain="true" id="delete">删除</a>
		</shiro:hasPermission>
	</div>
	
	<table id="organization-tb"></table>
	
	<div class="box" id="organization-box" style="display: none;">
		<form id="organization-form" method="post">
			<table class="rb-org" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="td_key">机构名称：</td>
					<td class="td_val"><input class="easyui-textbox" type="text"
						name="orgName" data-options="required:true"
						missingMessage="请填写机构名称" /></td>
				</tr>
				<tr>
					<td class="td_key">机构代码：</td>
					<td class="td_val"><input class="easyui-textbox" type="text"
						name="orgCode" data-options="required:true"
						missingMessage="请填写机构代码" /></td>
				</tr>
				<tr>
					<td class="td_key">上级机构：</td>
					<td class="td_val"><input id="org-combox" type="text"
						name="orgPid" /></td>
				</tr>
				<tr>
					<td class="td_key">机构描述：</td>
					<td class="td_val"><input class="easyui-textbox"
						data-options="multiline:true" type="text" name="orgDetail"
						style="width: 200px; height: 100px" /></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div class="box" id="organization-edit-box" style="display: none;">
		<form id="organization-edit-form" method="post">
			<table class="rb-org" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="td_key">机构名称：</td>
					<td class="td_val">
					<input type="hidden" name="orgId" id="orgId">
					<input class="easyui-textbox" type="text"
						name="orgName" data-options="required:true"
						missingMessage="请填写机构名称" id="edit-orgName"/></td>
				</tr>
				<tr>
					<td class="td_key">机构代码：</td>
					<td class="td_val"><input class="easyui-textbox" type="text"
						name="orgCode" data-options="required:true"
						missingMessage="请填写机构代码" id="edit-orgCode"/></td>
				</tr>
				<tr>
					<td class="td_key">上级机构：</td>
					<td class="td_val"><input id="org-combox2" type="text"
						name="orgPid"/></td>
				</tr>
				<tr>
					<td class="td_key">机构描述：</td>
					<td class="td_val"><input class="easyui-textbox"
						data-options="multiline:true" type="text" name="orgDetail"
						style="width: 200px; height: 100px" id="edit-orgDetail"/></td>
				</tr>
			</table>
		</form>
	</div>
	
	<script type="text/javascript" src="static/js/organization-manage.js"></script>
</body>
</html>