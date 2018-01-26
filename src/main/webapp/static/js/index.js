$(function(){

	$.ajax({  
		type : 'GET',  
		dataType : "json",  
		url : 'admin/GetMenuParent',  
		data:{
			"id":0
		},
		beforeSend: function () {
			MaskUtil.mask();
		},
		onComplete:function(){
			MaskUtil.unmask();
		},
		success : function(data) {  
			if(data.code == 200){
				$.each(data.data, function(i, item) {//加载父类节点即一级菜单  
					var id = item.muId;
					var title = item.muText;
					if (i == 0) {//显示第一个一级菜单下的二级菜单  
						$('#layout_west_accordion').accordion('add', {  
							title : item.muText,  
							iconCls : item.muIconcls,  
							selected : true,  
							content : '<div style="margin-top:8px;"><ul id="tree_'+id+'" name="'+item.text+'"></ul></div>',  
						});  
					}else{
						$('#layout_west_accordion').accordion('add', {  
							title : item.muText,  
							iconCls : item.muIconcls,  
							selected : false,  
							content : '<div style="margin-top:8px;"><ul id="tree_'+id+'" name="'+item.text+'"></ul></div>',  
						});  
					}
					getChild(id,title);
				});  
			}else{
				HandleException(data);
			}
		}  
	});  

	function getChild(id,title){
		$("#tree_"+id).tree({
			method : 'GET',
			url : 'admin/GetMenuChildren?id='+id,
			animate:true,
			loadFilter: function(data){
				console.log(data);
				if (data.code == 200){
					return data.data;
				}else{
					HandleException(data);
				}
			},
			error : function() {
				alert('系统异常，请稍候再试');
			},
			onBeforeLoad:function(){
				MaskUtil.mask();
			},
			onLoadSuccess:function(){
				MaskUtil.unmask();
			},
			onClick: function(node){
				if($("#tree"+id).tree('isLeaf',node.target)){  
					addTab(node.text,node.url,node.iconCls);
				}
			}
		});
	}

	function addTab(title, url,iconCls){
		if ($('#tabs').tabs('exists', title)){
			$('#tabs').tabs('select', title);//选中并刷新
			var currTab = $('#tabs').tabs('getSelected');
			var url = $(currTab.panel('options').content).attr('src');
			if(url != undefined && currTab.panel('options').title != 'Home') {
				$('#tabs').tabs('update',{
					tab:currTab,
					options:{
						content:createFrame(url)
					}
				})
			}
		} else {
			var content = createFrame(url);
			$('#tabs').tabs('add',{
				title:title,
				content:content,
				iconCls:iconCls,
				fit:true,
				closable:true
			});
		}
		tabClose();
	}

	function createFrame(url) {
		var s = '<iframe scrolling="no" frameborder="0"  src="'+url+'" style="width:100%;height:99%;"></iframe>';
		return s;
	}

	$(".modify-password").click(function(){
		$('#modify-password-dlg').dialog('open');
	});

	$('#modify-password-dlg').dialog({
		title: '修改密码',
		width: 400,
		height: 300,
		closed: true,
		cache: false,
		modal: true,
		buttons:[{
			text:'提交',
			iconCls:'icon-ok',
			handler:function(){
				formAddSubmit();
			}
		},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				$('#modify-password-dlg').dialog("close");
			}
		}]
	});

	function formAddSubmit(){
		$('#modify-password-form').form('submit', {
			url:'admin/user/ModifyUserPassword',
			onSubmit: function(){
				return $(this).form('enableValidation').form('validate');
			},
			success:function(data){
				console.log(data);
				var data = eval('(' + data + ')');
				if(data.code == 200){
					$.messager.alert("修改提示", "密码修改成功");
					$('#modify-password-dlg').dialog("close");
					$('#modify-password-form').form("clear");
				}else{
					HandleException(data);
				}
			},
			error:function(){
				alert("error");
			}
		});
	}

	$(".logout").click(function(){
		//提醒用户是否是真的删除数据
		$.messager.confirm("提示消息", "您确定要退出本系统吗？", function (r) {
			if (r) {
				window.location.href="logout";
			}
		});
	});
	
	$.ajax({
		url: "admin/organization/selectOrganizationOfAll",
		type: "get",
		dataType: "json",
		success: function (data) {
			console.log("Organization-list:"+data.code);
		}
	});
});