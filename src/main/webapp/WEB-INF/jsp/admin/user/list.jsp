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
			<form id="ffSearch" method="post">
				<table cellspacing="0" cellpadding="0">
					<tr>
						<td>用户名：</td>
						<td><input type="text" id="name" style="width: 200px" /></td>
					</tr>
					<tr>
						<td>登录帐号：</td>
						<td><input type="text" id="loginName" style="width: 200px" /></td>
						<td colspan="2" style="padding-left: 20px;"><a
							href="javascript:;" class="easyui-linkbutton"
							data-options="iconCls:'icon-search'" id="btnSearch">查询</a></td>
					</tr>
				</table>
			</form>
		</fieldset>
	</div>
	<div id="toolbar">
		<shiro:hasPermission name="system:user:insert">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-user-add" plain="true" id="add">新增</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="system:user:update">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-user-edit" plain="true" id="edit">编辑</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="system:user:delete">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-user-delete" plain="true" id="delete">删除</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="system:user:resetpwd">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-key-go" plain="true" id="resetpwd">重置密码</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="system:user:select">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-user-magnify" plain="true" id="show">查看</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="system:userRole:selectbyuser">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-lock-key" plain="true" id="show-role">查看角色</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="system:user:updatestate">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-user-accept16" plain="true" id="enable" style="display:none;">启用</a>
				<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-user-reject16" plain="true" id="disable" style="display:none;">停用</a>
		</shiro:hasPermission>
	</div>
	
	<div data-options="region:'center',split:false">
		<table id="user-tb"></table>
	</div>
	
	<div class="box" id="user-box" style="display: none;">
		<form id="user-form" method="post">
			<table class="rb-add-user" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="td_key">用户名：</td>
					<td class="td_val"><input class="easyui-textbox" type="text"
						name="usName" data-options="required:true" missingMessage="请填写用户名" /></td>
					<td class="td_key">登陆账号：</td>
					<td class="td_val"><input class="easyui-textbox" type="text"
						name="usLoginname"
						data-options="required:true,validType:'isLoginNameExist'"
						missingMessage="请填写登录帐号" /></td>
				</tr>
				<tr>
					<td class="td_key">机构：</td>
					<td class="td_val"><input class="easyui-combobox"
						id="org-combox" style="width: 204px;" name="usOrgid" /></td>
					<td class="td_key">部门：</td>
					<td class="td_val"><input class="easyui-combobox"
						id="dep-combox" style="width: 204px;" name="usDepid" /></td>
				</tr>
				<tr>
					<td class="td_key">密码：</td>
					<td class="td_val"><input id="pwd" class="easyui-textbox"
						type="password" name="usPwd"
						data-options="required:true,validType:'minLength[6]'"
						missingMessage="请填写登录密码" /></td>
					<td class="td_key">重复密码：</td>
					<td class="td_val"><input class="easyui-textbox"
						type="password" name="reusPwd" data-options="required:true"
						validType="equals['#pwd']" missingMessage="请再次填写登录密码" /></td>
				</tr>
				<tr>
					<td class="td_key">性别：</td>
					<td class="td_val"><select class="easyui-combobox"
						style="width: 204px;" name="usSex" editable="false">
							<option value="0">男</option>
							<option value="1">女</option>
					</select></td>
					<td class="td_key">手机号：</td>
					<td class="td_val"><input class="easyui-textbox" type="text"
						name="usPhone" data-options="required:true,validType:'mobile'"
						missingMessage="请填写手机号" /></td>
				</tr>
				<tr>
					<td class="td_key">邮箱：</td>
					<td class="td_val"><input class="easyui-textbox" type="text"
						name="usMail" data-options="required:true,validType:'email'"
						missingMessage="请填写邮箱地址" /></td>
					<td class="td_key">是否启用：</td>
					<td class="td_val"><select class="easyui-combobox"
						style="width: 204px;" name="usState" editable="false">
							<option value="0">是</option>
							<option value="1">否</option>
					</select></td>
				</tr>
				<tr>
					<td class="td_key">联系地址：</td>
					<td class="td_val" colspan="3"><input class="easyui-textbox"
						type="text" name="usAddress" style="width:588px;"/></td>
				</tr>
			</table>
		</form>
	</div>
	
		<div class="box" id="edit-box" style="display: none;">
		<form id="edit-form" method="post">
			<table class="rb-add-user" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="td_key">用户名：</td>
					<td class="td_val"><input class="easyui-textbox" type="text"
						name="usName" data-options="required:true" missingMessage="请填写用户名" id="edit-usName"/></td>
					<td class="td_key">登陆账号：</td>
					<td class="td_val"><span id="edit-usLoginname"></span></td>
				</tr>
				<tr>
					<td class="td_key">机构：</td>
					<td class="td_val"><input class="easyui-combobox"
						id="edit-org-combox" style="width: 204px;" name="usOrgid"/></td>
					<td class="td_key">部门：</td>
					<td class="td_val"><input class="easyui-combobox"
						id="edit-dep-combox" style="width: 204px;" name="usDepid"/></td>
				</tr>
				<tr>
					<td class="td_key">性别：</td>
					<td class="td_val"><select class="easyui-combobox"
						style="width: 204px;" name="usSex" editable="false" id="edit-usSex">
							<option value="0">男</option>
							<option value="1">女</option>
					</select></td>
					<td class="td_key">手机号：</td>
					<td class="td_val"><input class="easyui-textbox" type="text"
						name="usPhone" data-options="required:true,validType:'mobile'"
						missingMessage="请填写手机号" id="edit-usPhone"/></td>
				</tr>
				<tr>
					<td class="td_key">邮箱：</td>
					<td class="td_val"><input class="easyui-textbox" type="text"
						name="usMail" data-options="required:true,validType:'email'"
						missingMessage="请填写邮箱地址" id="edit-usMail"/></td>
					<td class="td_key">是否启用：</td>
					<td class="td_val"><select class="easyui-combobox"
						style="width: 204px;" name="usState" editable="false" id="edit-usState">
							<option value="0">是</option>
							<option value="1">否</option>
					</select></td>
				</tr>
				<tr>
					<td class="td_key">联系地址：</td>
					<td class="td_val" colspan="3"><input class="easyui-textbox"
						type="text" name="usAddress" style="width:588px;" id="edit-usAddress"/></td>
				</tr>
			</table>
		</form>
	</div>

	<div class="box" id="user-detail-box" style="display: none;">
		<table class="rb-add-user" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="td_key">用户名：</td>
				<td class="td_val name"></td>
				<td class="td_key">登陆账号：</td>
				<td class="td_val loginName"></td>
			</tr>
			<tr>
				<td class="td_key">性别：</td>
				<td class="td_val sex"></td>
				<td class="td_key">手机号：</td>
				<td class="td_val phone"></td>
			</tr>
			<tr>
				<td class="td_key">机构：</td>
				<td class="td_val org"></td>
				<td class="td_key">部门：</td>
				<td class="td_val dep"></td>
			</tr>
			<tr>
				<td class="td_key">联系地址：</td>
				<td class="td_val address" colspan="3"></td>
			</tr>
			<tr>
				<td class="td_key">添加时间：</td>
				<td class="td_val addDate"></td>
				<td class="td_key">上一次登录时间：</td>
				<td class="td_val loginDate"></td>
			</tr>
			<tr>
				<td class="td_key">邮箱：</td>
				<td class="td_val email"></td>
				<td class="td_key">是否启用：</td>
				<td class="td_val state"></td>
			</tr>
		</table>
	</div>
	<div id="toolbar2">
		<shiro:hasPermission name="system:userRole:selectbyuser">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-user-add" plain="true" id="add2">新增</a>
		</shiro:hasPermission>
	</div>
	<div id="user_role" style="display: none;">
		<div id="user-role-tb"></div>
	</div>
	<script type="text/javascript" src="static/js/user-manage.js"></script>
</body>
</html>