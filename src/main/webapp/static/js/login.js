$(function(){
	$("#login").dialog({
		title:"登录",
		width:350,
		height:200,
		modal:true,
		collapsible:false, //设置可折叠  
		minimizable:false,  
		maximizable:false,  
		resizable:true,  
		buttons:[{ 
			text:'登录',  
			iconCls:'icon-ok',  
			handler:function(){  
				login();
			}
		},{  
			text:'重置',
			iconCls:'icon-delete3',
			handler:function(){
				$('#login-form').form('clear');
			}
		}],
		iconCls:'icon-key'
	});
	
	function login(){
		$('#login-form').form('submit', {   
			url:"loginAction", 
			type:"POST",
			onSubmit: function(){   
				return $(this).form('enableValidation').form('validate');
			},   
			success:function(data){ 
				var data = eval('(' + data + ')');
				if(data.code == 200){
					location.href=getRootPath() + "admin/index";
				}else{
					HandleException(data);
				}
			}   
		});  
	}  
});