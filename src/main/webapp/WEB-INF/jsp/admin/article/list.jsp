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
<link rel="stylesheet"
	href="static/kindeditor/themes/default/default.css" />
<link rel="stylesheet"
	href="static/kindeditor/plugins/code/prettify.css" />
<script src="static/kindeditor/kindeditor.js"></script>
<script src="static/kindeditor/kindeditor-all-min.js"></script>
<script charset="UTF-8" src="static/kindeditor/kindeditor-min.js"></script>
<script charset="UTF-8" src="static/kindeditor/lang/zh_CN.js"></script>
<script charset="UTF-8" src="static/kindeditor/plugins/code/prettify.js"></script>
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
								<option value="0">标题</option>
								<option value="1">分类</option>
						</select></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td class="td_title">文章标题：<input class="easyui-textbox"
							type="text" id="search-title" style="width: 200px" /></td>
						<td class="td_type" style="display: none;">文章类别： <input
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
		<shiro:hasPermission name="system:article:insert">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-application-form-add" plain="true" id="add">新增</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="system:article:update">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-application-form-edit" plain="true" id="edit">编辑</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="system:article:delete">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-application-form-delete" plain="true" id="delete">删除</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="system:article:select">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-application-form-magnify" plain="true" id="show">查看</a>
		</shiro:hasPermission>
	</div>

	<div data-options="region:'center',split:false">
		<table id="article-tb"></table>
	</div>

	<div class="box" id="article-add-box" style="display: none;">
		<form id="article-form" method="post">
			<table class="rb-add-user" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="td_key">文章标题：</td>
					<td class="td_val" colspan="3"><input class="easyui-textbox"
						id="artTitle" type="text" name="artTitle" style="width: 588px;"
						data-options="required:true" missingMessage="请输入文章标题" /></td>
				</tr>
				<tr>
					<td class="td_key">文章类别：</td>
					<td class="td_val"><input class="easyui-combobox"
						editable="false" id="type-combox" style="width: 588px;"
						name="artType" /></td>
				</tr>

				<tr>
					<td class="td_key">文章内容：</td>
					<td class="td_val" colspan="3"><textarea rows="8"
							id="artContent" style="width: 100%; height: 400px;"
							name="artContent"></textarea></td>
				</tr>
				<tr>
					<td class="td_key">文章置顶：</td>
					<td class="td_val"><select class="easyui-combobox"
						name="artTop" editable="false" style="width: 588px;">
							<option value=0>不置顶</option>
							<option value=1>置顶</option>
					</select></td>
				</tr>
				<tr>
					<td class="td_key">附件：</td>
					<td class="td_val"></td>
				</tr>
			</table>
		</form>
	</div>

	<div class="box" id="article-edit-box" style="display: none;">
		<form id="article-edit-form" method="post">
			<table class="rb-add-user" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="td_key">文章标题：</td>
					<td class="td_val" colspan="3"><input type="hidden"
						name="artId" id="editId"> <input class="easyui-textbox"
						id="editTitle" type="text" name="artTitle" style="width: 588px;" data-options="required:true" missingMessage="请输入文章标题" /></td>
				</tr>
				<tr>
					<td class="td_key">文章类别：</td>
					<td class="td_val"><input class="easyui-combobox"
						editable="false" id="type-combox2" style="width: 588px;"
						name="artType" /></td>
				</tr>

				<tr>
					<td class="td_key">文章内容：</td>
					<td class="td_val" colspan="3"><textarea rows="8"
							id="editContent" style="width: 100%; height: 400px;"
							name="artContents"></textarea></td>
				</tr>
				<tr>
					<td class="td_key">文章置顶：</td>
					<td class="td_val"><select class="easyui-combobox"
						name="artTop" id="top" editable="false" style="width: 588px;">
							<option value=0>不置顶</option>
							<option value=1>置顶</option>
					</select></td>
				</tr>
			</table>
		</form>
	</div>


	<div class="box" id="article-detail-box" style="display: none;">
		<table border="0" cellspacing="0" cellpadding="0"
			style="width: 950px;">
			<tr>
				<td class="title" style="font-size:20px;font-weight:bold;padding-top:25px;padding-bottom:20px;"></td>
			</tr>
			<tr>
				<td class="sec"></td>
			</tr>
			<tr>
				<td class="content"></td>
			</tr>
		</table>
	</div>

	<script type="text/javascript" src="static/js/article-manage.js"></script>
</body>
</html>