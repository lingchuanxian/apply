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
<body class="easyui-layout" fit="true" border="false">

	<div data-options="region:'north'" style="height: 100px;">
		<fieldset style="border-radius: 10px; border: 1px solid #C3C3C3;">
			<legend style="font-size: 14px;">信息检索</legend>
			<form id="article-search-form" method="post">
				<table cellspacing="0" cellpadding="0">
					<tr>
						<td>搜索类型：</td>
						<td><select class="easyui-combobox" name="artTop"
							id="search-type" editable="false" style="width: 88px;">
								<option value="0">编号</option>
								<option value="1">名称</option>
								<option value="2">类别</option>
						</select></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td class="td_code">编号：<input class="easyui-textbox"
							type="text" id="search-code" style="width: 200px" /></td>
						<td class="td_name" style="display: none;">名称：<input class="easyui-textbox"
							type="text" id="search-name" style="width: 200px" /></td>
						<td class="td_type" style="display: none;">类别： <input
							class="easyui-combobox" editable="false" id="search-type-combox"
							style="width: 128px;" name="artType" />
						</td>
						<td colspan="2" style="padding-left: 20px;"><a
							href="javascript:;" class="easyui-linkbutton"
							data-options="iconCls:'icon-search'" id="btnSearch">查询</a></td>
					</tr>
				</table>
			</form>
		</fieldset>
	</div>

	<div id="toolbar">
		<shiro:hasPermission name="system:dictionary:insert">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-application-form-add" plain="true" id="add">新增</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="system:dictionary:update">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-application-form-edit" plain="true" id="edit">编辑</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="system:dictionary:delete">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-application-form-delete" plain="true" id="delete">删除</a>
		</shiro:hasPermission>
	</div>
	<div data-options="region:'center',split:false">
		<table id="data-table"></table>
	</div>
	<div class="box" id="add-box" style="display: none;">
		<form id="add-form" method="post">
			<table class="rb-org" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="td_key">编码：</td>
					<td class="td_val"><input class="easyui-textbox" type="text"
						name="dictCode" data-options="required:true"
						missingMessage="请填写编码" /></td>
				</tr>
				<tr>
					<td class="td_key">名称：</td>
					<td class="td_val"><input class="easyui-textbox" type="text"
						name="dictName" data-options="required:true"
						missingMessage="请填写名称" /></td>
				</tr>
				<tr>
					<td class="td_key">类型：</td>
					<td class="td_val"><input class="easyui-combobox"
						editable="false" id="add-type-combox" style="width: 200px;"
						name="dictTypeId" /></td>
				</tr>
				<tr>
					<td class="td_key">描述：</td>
					<td class="td_val"><input class="easyui-textbox"
						data-options="multiline:true" type="text" name="dictDescription"
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
					<td class="td_val"><input type="hidden" id="edit-dictId"
						name="dictId"> <input class="easyui-textbox" type="text"
						name="dictCode" data-options="required:true"
						missingMessage="请填写编码" id="edit-dictCode" /></td>
				</tr>
				<tr>
					<td class="td_key">名称：</td>
					<td class="td_val"><input class="easyui-textbox" type="text"
						name="dictName" data-options="required:true"
						missingMessage="请填写名称" id="edit-dictName" /></td>
				</tr>
				<tr>
					<td class="td_key">类型：</td>
					<td class="td_val"><input class="easyui-combobox"
						editable="false" id="edit-type-combox" style="width: 200px;"
						name="dictTypeId" /></td>
				</tr>
				<tr>
					<td class="td_key">描述：</td>
					<td class="td_val"><input class="easyui-textbox"
						data-options="multiline:true" type="text" name="dictDescription"
						style="width: 200px; height: 100px" id="edit-dictDescription" /></td>
				</tr>
			</table>
		</form>
	</div>

	<script type="text/javascript" src="static/js/dictionary-manage.js"></script>
</body>
</html>