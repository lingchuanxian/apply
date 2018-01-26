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
<body>
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
	<script type="text/javascript" src="static/js/organization-manage.js"></script>
</body>
</html>