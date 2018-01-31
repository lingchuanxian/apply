$(function(){
	document.onkeydown = function(e){ 
		var ev = document.all ? window.event : e;
		if(ev.keyCode==13) {
			login();
		}  
	}

	$("#login").click(function(){
		login();
	});

	function login(){
		var name = $("#name").val();
		var pwd = $("#pwd").val();
		if(name == ''){
			layer.msg('请输入登录帐号');
			$("#name").focus();
			$("#name").css("background-color","#FFFFCC");
			return;
		}
		if(pwd == ''){
			layer.msg('请输入密码');
			$("#pwd").focus();
			$("#pwd").css("background-color","#FFFFCC");
			return;
		}
		$.ajax({
			url: "loginAction",
			type: "POST",
			dataType: "json",
			data:{"username": name,"password":pwd},
			beforeSend: function () {
				layer.msg('正在登录。。。', {
					icon: 16,
					shade: 0.2,
					time:5000
				});
			},
			success: function (data) {
				layer.close();
				if(data.code == 200){
					location.href = "admin/index";
				}else{
					layer.alert(data.message, {icon: 2});
				}
			},
			error:function(e){
				layer.alert('未知异常', {icon: 2});
			}
		});
	}

});  