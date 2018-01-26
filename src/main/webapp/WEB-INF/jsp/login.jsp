<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.contextPath }/">
<meta charset="UTF-8" />
<title>数字办公系统</title>
<link rel="stylesheet" id="easyuiTheme" type="text/css"
	href="static/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="static/css/icon.css" />
<link rel="stylesheet" type="text/css" href="static/css/style.css" />
</head>
<body>
	<div id="login" >
		<form id="login-form" class="easyui-form" method="post" data-options="novalidate:true">
			<p>
				帐号：<input type="text" id="username" class="easyui-textbox textbox"
					name="username" data-options="required:true"/>
			</p>
			<p>
				密码：<input type="password" id="password"
					class="easyui-textbox" name="password" data-options="required:true"/>
			</p>
		</form>
	</div>

	<script type="text/javascript" src="static/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="static/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="static/easyui/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="static/easyui/validateExtend.js"></script>
	<script type="text/javascript" src="static/js/easyui-common.js"></script>
	<script type="text/javascript" src="static/js/loading.js"></script>
	<script type="text/javascript" src="static/js/common-utils.js"></script>
	<script type="text/javascript" src="static/js/login.js"></script>
</body>
</html>