/**
 * 
 */

$(function(){
	var datagrid; //定义全局变量datagrid
	var editRow = undefined; //定义全局变量：当前编辑的行
	datagrid = $("#user-tb").datagrid({
		title:"用户列表",
		method:"GET",
		url:getRootPath() + "admin/user/GetUserList",
		idField:'usId',
		rownumbers: true,
		singleSelect:true,
		width: $(window).width() - 6,
		height: $(window).height() - 110,
		striped: true, //行背景交换
		fitColumns:false,
		pagination:true,//分页控件 
		pageSize: 20,//每页显示的记录条数，默认为10 
		pageList: [10,20,50,100],//可以设置每页记录条数的列表 
		loadFilter: function(data){
			if (data.code == 200){
				return data.data;
			}else{
				HandleException(data);
			}
		},
		columns:[[
			{
				field:'usId',
				title:"用户编号",
				width:100,
				align:'center',
			},{
				field:'usName',
				title:"用户名",
				width:220,
				align:'center',
			},{
				field:'usLoginname',
				title:"登录账号",
				width:220,
				align:'center',
			},{
				field:'organization',
				title:"机构",
				width:220,
				align:'center',
				formatter:function(val){  
					if(val){  
						return  val.orgName;  
					}  
				},  
			},{
				field:'department',
				title:"部门",
				width:220,
				align:'center',
				formatter:function(val){  
					if(val){  
						return  val.depName;  
					}  
				},  
			},{
				field:'usState',
				title:"是否启用",
				width:180,
				align:'center',
				formatter: function(value,row,index){
					if(value == 0){
						return '<a class="state-yes" href="javascript:void(0)">是</a>';
					}else{
						return '<a class="state-no" href="javascript:void(0)">否</a>';
					}
				}
			}
			]],
			toolbar:'#toolbar',
			onBeforeLoad:function(){
				$("#enable").hide();
				$("#disable").hide();
			},
			onLoadSuccess: function(row){
				$(".state-yes").linkbutton({ text: '是', plain: true, iconCls: 'icon-ok' });
				$(".state-no").linkbutton({ text: '否', plain: true, iconCls: 'icon-stop' });
			},
			onSelect:function(rowIndex, rowData){  
				if(rowData.usState == 0){
					$("#disable").show();
					$("#enable").hide();
				}else{
					$("#enable").show();
					$("#disable").hide();
				}
			},
	});
	//设置分页控件 
	var p = datagrid.datagrid('getPager'); 
	$(p).pagination({ 
		beforePageText: '第',//页数文本框前显示的汉字 
		afterPageText: '页    共 {pages} 页', 
		displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
	}); 
	
//#################################  搜索开始	################################
	
	$("#btnSearch").click(function(){
		doSearch();
	});

	function doSearch(){
		$('#user-tb').datagrid('load',{
			name: $('#name').val(),
			loginName: $('#loginName').val()
		});
	}
	
	//#################################  搜索结束	################################

	//#################################  新增开始	############################################
	
	$("#add").click(function(){
		loadCombotreeOfOrg($("#org-combox"),$("#dep-combox"),1);
		getRoleType($('#role-combox'));
		$("#user-form").form("disableValidation");
		$('#user-box').dialog("open");
	});
	
	$('#user-box').dialog({
		title: '用户新增',
		width: 800,
		height: 500,
		closed: true,
		cache: false,
		modal: true,
		buttons:[{
			text:'保存',
			iconCls:'icon-ok',
			handler:function(){
				formAddSubmit();
			}
		},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				$('#user-box').dialog("close");
			}
		}]
	});
	
	function formAddSubmit(){
		$('#user-form').form('submit', {
			url:getRootPath() + 'admin/user/AddUser',
			onSubmit: function(){
				return $(this).form('enableValidation').form('validate');
			},
			success:function(data){
				$('#user-box').dialog("close");
				$('#user-form').form("clear");
				datagrid.datagrid("reload");
			}
		});
	}
	
	//#################################  新增结束	################################
	
	//#################################  编辑开始	################################
	
	$("#edit").click(function(){
		MaskUtil.mask();
		loadCombotreeOfOrg($("#edit-org-combox"),$("#edit-dep-combox"),2);
		showEditData();
		$("#edit-form").form("disableValidation");
		$('#edit-box').dialog("open");
	});
	
	$('#edit-box').dialog({
		title: '用户编辑',
		width: 800,
		height: 500,
		closed: true,
		cache: false,
		modal: true,
		buttons:[{
			text:'保存',
			iconCls:'icon-ok',
			handler:function(){
				formEditSubmit();
			}
		},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				$('#edit-box').dialog("close");
			}
		}]
	});
	
	function showEditData(){
		var selectRows =datagrid.treegrid("getSelections");
		if (selectRows.length < 1) {
			$.messager.alert("提示消息", "请选择要编辑的用户!");
			return;
		}
		$.ajax({
			url: getRootPath() + "admin/user/SelectUserById/"+selectRows[0].usId,
			type: "post",
			dataType: "json",
			success: function (data) {
				MaskUtil.unmask();
				if(data.code == 200){
					var user = data.data;
					console.log(user);
					$("#edit-usId").val(user.usId);
					$("#edit-usName").textbox('setValue',user.usName);
					$("#edit-usLoginname").html(user.usLoginname);
					$("#edit-usPhone").textbox('setValue',user.usPhone);
					$("#edit-usAddress").textbox('setValue',user.usAddress);
					$("#edit-usMail").textbox('setValue',user.usMail);
					$("#edit-org-combox").combotree("setValue",{ id: user.organization.orgId,text: user.organization.orgName});
					$("#edit-dep-combox").combotree("setValue", { id: user.department.depId,text: user.department.depName});
					$('#edit-box').dialog("open");
					$("#edit-form").form("disableValidation");
				}else{
					HandleException(data);
				}
			}
		});
		
	}
	
	function formEditSubmit(){
		$('#edit-form').form('submit', {
			url:getRootPath() + 'admin/user/AddUser',
			onSubmit: function(){
				return $(this).form('enableValidation').form('validate');
			},
			success:function(data){
				$('#edit-box').dialog("close");
				$('#edit-form').form("clear");
				datagrid.datagrid("reload");
			}
		});
	}
	
	//#################################  编辑结束	################################
	
	//#################################  删除数据开始	################################
	
	$("#delete").click(function(){
		doDelete();
	});
	
	//删除数据
	function doDelete() {
		var selectRows =datagrid.treegrid("getSelections");
		if (selectRows.length < 1) {
			$.messager.alert("提示消息", "请选择要删除的用户!");
			return;
		}
		//提醒用户是否是真的删除数据
		$.messager.confirm("确认消息", "您确定要删除用户【"+selectRows[0].usName+"】吗？", function (r) {
			if (r) {
				MaskUtil.mask();
				$.ajax({
					url: getRootPath() + "admin/user/DeleteUserById",
					type: "post",
					dataType: "json",
					data:{"id": selectRows[0].usId},
					success: function (data) {
						MaskUtil.unmask();
						if(data.code == 200){
							datagrid.datagrid("reload");
							datagrid.datagrid("clearSelections");
						}else{
							HandleException(data);
						}
					}
				});
			}
		});
	}

	//#################################  删除数据结束	################################
	
	//#################################  重置密码开始	################################
	
	$("#resetpwd").click(function(){
		resetPassword();
	});
	
	function resetPassword(){
		var selectRows =datagrid.treegrid("getSelections");
		if (selectRows.length < 1) {
			$.messager.alert("提示消息", "请选择要重置密码的用户!");
			return;
		}
		//提醒用户是否是真的删除数据
		$.messager.confirm("确认消息", "您确定要重置用户【"+selectRows[0].usName+"】的密码吗？", function (r) {
			if (r) {
				MaskUtil.mask();
				$.ajax({
					url: getRootPath() + "admin/user/ResetUserPassword",
					type: "post",
					dataType: "json",
					data:{"id": selectRows[0].usId},
					success: function (data) {
						MaskUtil.unmask();
						if(data.code == 200){
							$.messager.alert("提示消息", data.message,"info");
						}else{
							HandleException(data);
						}
					}
				});
			}
		});
	}

	//#################################  重置密码结束	################################
	
	//#################################  查看详情开始	################################

	$("#show").click(function(){
		showDetail();
	});
	
	$('#user-detail-box').dialog({
		title: '用户详情',
		width: 800,
		height: 500,
		closed: true,
		cache: false,
		modal: true,
		buttons:[{
			text:'关闭',
			iconCls:'icon-cancel',
			handler:function(){
				$('#user-detail-box').dialog("close");
			}
		}]
	});
	
	function showDetail(){
		var selectRows =datagrid.treegrid("getSelections");
		if (selectRows.length < 1) {
			$.messager.alert("提示消息", "请选择要查看的用户!");
			return;
		}else{
			$.ajax({
				url: getRootPath() + "admin/user/SelectUserById",
				type: "post",
				dataType: "json",
				data:{"id": selectRows[0].usId},
				success: function (data) {
					if(data.code == 200){
						var user = data.data;
						$(".name").html(user.usName);
						$(".loginName").html(user.usLoginname);
						$(".sex").html(user.usSex==0 ? "男":"女");
						$(".phone").html(user.usPhone);
						$(".email").html(user.usMail);
						$(".state").html(user.usState==0? "是":"否");
						$(".org").html(user.organization.orgName);
						$(".dep").html(user.department.depName);
						$(".address").html(user.usAddress);
						$(".addDate").html(jsonYearMonthDay(user.usRegistdate));
						$(".loginDate").html(jsonTimeStamp(user.usLastlogindate));
						$('#user-detail-box').dialog("open");
					}else{
						HandleException(data);
					}
				}
			});
		}
	}
	
	//#################################  查看详情结束	################################
	
	//#################################  查看角色开始	################################
	
	$("#show-role").click(function(){
		showRole();
	});
	
	function showRole() {
		var selectRows =datagrid.datagrid("getSelections");
		if (selectRows.length < 1) {
			$.messager.alert("提示消息", "请选择要查看的用户!");
			return;
		}
		showUserRoleDialog(selectRows[0].usId,selectRows[0].usName);
	}

	function showUserRoleDialog(id,name){
		$("#user-role-tb").datagrid({
			method:"POST",
			url:getRootPath() + "admin/userRole/SelectUserRoleByUid",
			queryParams: {          
				id: id            
			} ,
			idField:'urId',
			fit:true,
			checkOnSelect : true,  
			width:700,
			height: 500,
			fitColumns:true,
			loadFilter: function(data){
				if (data.code == 200){
					console.log(data.data);
					return data.data;
				}else{
					HandleException(data);
				}
			},
			columns:[[{
				field : 'ck',
				title:'编号',
				checkbox : true,
				align:'center',
			},
			{
				field:'rlName',
				title:"角色名",
				width:20,
				align:'center',
				formatter:function(val,row,index){ 
					if(row.urRole){
						return row.urRole.rlName;
					}
				},  
			},{
				field:'rlCode',
				title:"角色代码",
				width:30,
				align:'center',
				formatter:function(val,row,index){ 
					if(row.urRole){
						return row.urRole.rlCode;
					}
				},  
			},{
				field:'rlDetail',
				title:"角色描述",
				width:30,
				align:'center',
				formatter:function(val,row,index){ 
					if(row.urRole){
						return row.urRole.rlDetail;
					}
				},  
			}
			]],
			toolbar:'#toolbar2',
			onLoadSuccess: function(data){
				if (data.total == 0) { 
					//添加一个新数据行，第一列的值为你需要的提示信息，然后将其他列合并到第一列来，注意修改colspan参数为你columns配置的总列数 
					$(this).datagrid('appendRow', { rlName: '<div style="color:red">没有相关记录！</div>' }).datagrid('mergeCells', { index: 0, field: 'rlName', colspan: 2 }); 
				} 
			},
		});
		
		$("#add2").click(function(){
			RemoveRoleUser($("#user-role-tb"));
		});

		$('#user_role').dialog({
			title: '用户【'+name+'】拥有的角色',
			width: 615,
			height: 580,
			closed: false,
			cache: false,
			modal: true,
			buttons:[{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function(){
					$('#user-role-tb').datagrid("clearSelections");
					$('#user_role').dialog("close");
				}
			}]
		});
	}
	
	function RemoveRoleUser(datagrid){
		var selectRows =datagrid.treegrid("getSelections");
		if (selectRows.length < 1) {
			$.messager.alert("提示消息", "请选择要移除的角色!");
			return;
		}
		var ids = '';
		var names ='';
		for(var i =0; i< selectRows.length;i++){  
			ids += selectRows[i].urId+",";
			names += selectRows[i].urRole.rlName+",";
		} 
		
		//提醒用户是否是真的移除
		$.messager.confirm("确认消息", "您确定要移除角色【"+names.substr(0,names.length-1)+"】吗？", function (r) {
			if (r) {
				$.ajax({
					url: getRootPath() + "admin/userRole/RemoveUserOfRole",
					type: "post",
					dataType: "json",
					data:{"ids":ids},
					success: function (data) {
						if(data.code == 200){
							datagrid.datagrid("clearSelections");
							datagrid.datagrid("reload");
						}else{
							HandleException(data);
						}
					}
				});
			}
		});
	}
	
	//#################################  查看角色结束	################################
	
	//################################	启用、禁用开始	######################################
	
	$("#enable").click(function(){
		changeState(0);
	});
	
	$("#disable").click(function(){
		changeState(1);
	});

	function changeState(state){
		var selectRows =datagrid.treegrid("getSelections");
		var str = "";
		if(state == 0){
			str = "启用";
		}else{
			str = "停用";
		}
		$.messager.confirm("确认消息", "您确定要"+str+"帐号【"+selectRows[0].usName+"】吗？", function (r) {
			if (r) {
				$.ajax({
					url:getRootPath() + 'admin/user/UpdateUserState',
					type:'post',
					dataType: 'json',
					data: {  
						"id" : selectRows[0].usId,
						"state":state,
					},
					success:function(data){
						if(data.code== 200){
							datagrid.datagrid("reload");
							datagrid.datagrid("clearSelections");
						}else{
							HandleException(data);
						}
					},
					error:function(e){
						alert(e.message);
					}
				});
			}
		});
	}
	
	//################################	启用、禁用结束	######################################

	//加载机构
	function loadCombotreeOfOrg(combotree,combotree2,flag){
		combotree.combotree({  
			method:"GET",
			url:getRootPath() + 'admin/organization/selectOrganizationForSelect',  
			editable:false,
			loadFilter: function(data){
				if (data.code == 200){
					return data.data;
				}else{
					HandleException(data);
				}
			},
			onLoadSuccess: function (node,data) { 
				if(flag == 1){
					combotree.combotree('setValue', { id: data[0].id, text: data[0].text }); 
				}
				loadCombotreeOfDep(combotree2,data[0].id,flag);
			},
			onSelect:function(node) {
				loadCombotreeOfDep(combotree2,node.id,flag);
			}
		});  
	}
	
	//加载部门
	function loadCombotreeOfDep(combotree,id,flag){
		combotree.combotree({  
			method:"GET",
			url:getRootPath() + 'admin/department/selectDepForSelect?id='+id,  
			editable:false,
			loadFilter: function(data){
				if (data.code == 200){
					return data.data;
				}else{
					HandleException(data);
				}
			},
			onLoadSuccess: function (node,data) { 
				if(flag == 1){
					combotree.combotree('setValue', { id: data[0].id, text: data[0].text }); 
				}
			}
		});  
	}

	//加载角色类型
	function getRoleType(combobox){
		combobox.combobox({  
			method:"GET",
			url:getRootPath() + 'admin/role/selectAllOfRole',  
			valueField:'rlId',  
			textField:'rlName',
			editable:false,
			loadFilter: function(data){
				if (data.code == 200){
					return data.data;
				}else{
					HandleException(data);
				}
			},
			onLoadSuccess: function () { 
				var data = $(this).combobox("getData");
				if(data.length > 0){
					$(this).combobox("select", data[0].rlId);
				}
			}
		});  
	}
	
});