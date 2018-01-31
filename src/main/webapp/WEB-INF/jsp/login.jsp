<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.contextPath }/">
<meta charset="UTF-8" />
<title>登录-后台管理系统</title>
<link href="static/login/css/login.css" type="text/css" rel="stylesheet">
</head>
<body>

	<div class="login">
		<div class="message">办公系统-管理登录</div>
		<div id="darkbannerwrap"></div>
		<form method="post">
			<input name="username" placeholder="用户名" required="required" type="text" id="name">
			<hr class="hr15">
			<input name="password" placeholder="密码" required="required" type="password" id="pwd">
			<hr class="hr15">
			<input value="登录" style="width: 100%;" type="button" id="login">
			<hr class="hr20">
			 <a onClick="alert('请联系管理员')">忘记密码</a> 
		</form>


	</div>

	<div class="copyright">
		© 2016品牌名称 by <a href="http://www.mycodes.net/" target="_blank">源码之家</a>
	</div>
	<script type="text/javascript" src="static/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="static/layer/layer.js"></script>
	<script type="text/javascript" src="static/js/login.js"></script>
</body>
</html>