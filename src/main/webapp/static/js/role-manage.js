/**
 * 
 */

$(function(){
	var datagrid; //定义全局变量datagrid
	var editRow = undefined; //定义全局变量：当前编辑的行
	datagrid = $("#role-tb").datagrid({
		dnd: true,
		method:"GET",
		url:getRootPath() + "admin/role/selectAllOfRole",
		idField:'rlId',
		rownumbers: true,
		checkOnSelect : true,  
		width: $(window).width() - 20,
		height: $(window).height() - 10,
		striped: true, //行背景交换
		fitColumns:false,
		singleSelect:true,
		loadFilter: function(data){
			if (data.code == 200){
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
			field:'rlId',
			title:"角色编号",
			width:100,
			align:'center',
		},{
			field:'rlName',
			title:"角色名称",
			width:200,
			align:'center',
			editor: { type: 'validatebox', options: { required: true} }
		},{
			field:'rlCode',
			title:"角色代码",
			width:200,
			align:'center',
			editor: { 
				type: 'validatebox',
				options: { required: true}
			}
		},{
			field:'rlDetail',
			title:"角色描述",
			align:'center',
			width:600,
			editor: { type: 'validatebox'}
		}
		]],
		toolbar:'#toolbar',
		onBeforeLoad:function(){
			$("#save").hide();
			$("#cancle").hide();
		},
		onLoadSuccess: function(row){
			datagrid.datagrid("unselectAll");
		},
		onBeforeEdit:function(index,row){
			$("#save").show();
			$("#cancle").show();
		},
		onAfterEdit:function(index,row){
			$.ajax({
				url:getRootPath() + 'admin/role/AddOrUpdateRole',
				type:'post',
				dataType: 'json',
				data: {  
					"rlId" : row.rlId,
					"rlName":row.rlName,
					"rlCode":row.rlCode,
					"rlDetail":row.rlDetail,
				},
				success:function(data){
					if(data.code == 200){
						$("#save").hide();
						$("#cancle").hide();
						editRow = undefined;
						datagrid.datagrid("reload",editRow);
					}else{
						HandleException(data);
					}
				},
				error:function(e){
					$("#save").show();
					$("#cancle").show();
					$.messager.alert("提示消息", e.message,"error");
				}
			});
		},
	});
	
	$("#add").click(function(){
		//添加时先判断是否有开启编辑的行，如果有则把开户编辑的那行结束编辑
		if (editRow != undefined) {
			datagrid.datagrid("endEdit", editRow);
		}
		//添加时如果没有正在编辑的行，则在datagrid的最后追加
		if (editRow == undefined) {
			var index = datagrid.datagrid("appendRow", {
				muText: '',
				muIconcls: 'icon-node',
				muChecked:'false',
				muState:'closed',
				muUrl: ''
			}).datagrid('getRows').length-1;;
			//将新插入的那一行开户编辑状态
			datagrid.datagrid("beginEdit", index);
			datagrid.datagrid("checkRow",index);
			//给当前编辑的行赋值
			editRow = index;
		}
	});
	
	$("#edit").click(function(){
		//修改时要获取选择到的行
		var rows = datagrid.datagrid("getSelections");
		//如果只选择了一行则可以进行修改，否则不操作
		if (rows.length == 1) {
			//修改之前先关闭已经开启的编辑行，当调用endEdit该方法时会触发onAfterEdit事件
			if (editRow != undefined) {
				datagrid.datagrid("endEdit", editRow);
			}
			//当无编辑行时
			if (editRow == undefined) {
				//获取到当前选择行的下标
				var index = datagrid.datagrid("getRowIndex", rows[0]);
				//开启编辑
				datagrid.datagrid("beginEdit", index);
				//把当前开启编辑的行赋值给全局变量editRow
				editRow = index;
				//当开启了当前选择行的编辑状态之后，
				//应该取消当前列表的所有选择行，要不然双击之后无法再选择其他行进行编辑
				//datagrid.datagrid("unselectAll");
			}
		}else{
			$.messager.alert("编辑提示", "请选择要进行编辑的行");
		}
	});

	$("#save").click(function(){
		//保存时结束当前编辑的行，自动触发onAfterEdit事件如果要与后台交互可将数据通过Ajax提交后台
		datagrid.datagrid("endEdit", editRow);
		editRow = undefined;
	});
	
	$("#cancle").click(function(){
		datagrid.datagrid('cancelEdit',editRow)
		editRow = undefined;
		datagrid.datagrid("unselectAll");
		//添加时取消新增的一行还在，暂时用刷新页面的方法
		datagrid.datagrid("reload");
		$("#save").hide();
		$("#cancle").hide();
	});
	
	$("#delete").click(function(){
		doDelete();
	});
	
	$("#user-manage").click(function(){
		authorize();
	});
	
	$("#permission-manage").click(function(){
		alertPermissionManageDialog();
	});
	
	//删除数据
	function doDelete() {
		var selectRows =datagrid.treegrid("getSelections");
		if (selectRows.length < 1) {
			$.messager.alert("提示消息", "请选择要删除的菜单!");
			return;
		}
		//提醒用户是否是真的删除数据
		$.messager.confirm("确认消息", "您确定要删除角色【"+selectRows[0].rlName+"】吗？", function (r) {
			if (r) {
				MaskUtil.mask();
				$.ajax({
					url: getRootPath() + "admin/role/DeleteRoleById",
					type: "post",
					dataType: "json",
					data:{"id": selectRows[0].rlId},
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

	var user_datagrid; 
	var user_select_datagrid; 

	function authorize(){
		var selectRows =datagrid.treegrid("getSelections");
		if (selectRows.length < 1) {
			$.messager.alert("提示消息", "请选择要管理的角色!");
			return;
		}
		openUserDialogAndLoadData(selectRows[0].rlId,selectRows[0].rlName);
	}

	function openUserDialogAndLoadData(id,name){

		$('#role_menu').dialog({
			title: "角色【"+name+'】成员管理',
			width: 915,
			height: 680,
			closed: false,
			cache: false,
			modal: true,
			buttons:[{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function(){
					user_datagrid.datagrid("clearSelections");
					$('#role_menu').dialog("close");
				}
			}]
		});

		user_datagrid = $("#role-user-tb").datagrid({
			method:"GET",
			url:getRootPath() + "admin/userRole/SelectUserByRid?id="+id,
			idField:'urId',
			fit:true,
			checkOnSelect : true,  
			width:900,
			height: 600,
			rownumbers: true,
			fitColumns:true,
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
			columns:[[{
				field : 'ck',
				title:'编号',
				checkbox : true,
				align:'center',
			},
			{
				field:'usName',
				title:"用户名",
				width:20,
				align:'center',
				formatter:function(val,row,index){  
					return row.urUser.usName;
				},  
			},{
				field:'urOrg',
				title:"机构",
				width:30,
				align:'center',
				formatter:function(val,row,index){  
					return row.urUser.organization.orgName;
				},  
			},{
				field:'urDep',
				title:"部门",
				width:30,
				align:'center',
				formatter:function(val,row,index){  
					return row.urUser.department.depName;
				},  
			}
			]],
			toolbar:'#toolbar2',
		});

		$("#add2").click(function(){
			OpenSelectUserDialog(id);
		});
		
		$("#delete2").click(function(){
			RemoveRoleUser();
		});
		
	}

	function RemoveRoleUser(){
		var selectRows =user_datagrid.treegrid("getSelections");
		if (selectRows.length < 1) {
			$.messager.alert("提示消息", "请选择要移除的用户!");
			return;
		}
		var ids = '';
		var names ='';
		for(var i =0; i< selectRows.length;i++){  
			ids += selectRows[i].urId+",";
			names += selectRows[i].urUser.usName+",";
		} 
		
		//提醒用户是否是真的删除数据
		$.messager.confirm("确认消息", "您确定要移除用户【"+names.substr(0,names.length-1)+"】吗？", function (r) {
			if (r) {
				$.ajax({
					url: getRootPath() + "admin/userRole/RemoveUserOfRole",
					type: "post",
					dataType: "json",
					data:{"ids":ids},
					success: function (data) {
						if(data.code == 200){
							user_datagrid.datagrid("clearSelections");
							user_datagrid.datagrid("reload");
						}else{
							HandleException(data);
						}
					}
				});
			}
		});
	}

	function OpenSelectUserDialog(id){

		user_select_datagrid = $("#user-select-tb").datagrid({
			method:"GET",
			url:getRootPath() + "admin/user/GetUserListExpectRoleExist?rid="+id,
			idField:'usId',
			fit:true,
			checkOnSelect : true,  
			width:900,
			height: 600,
			rownumbers: true,
			fitColumns:true,
			pagination:true,//分页控件 
			pageSize: 20,//每页显示的记录条数，默认为10 
			pageList: [10,20,50,100],//可以设置每页记录条数的列表 
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
				field:'usName',
				title:"用户名",
				width:20,
				align:'center',
			},{
				field:'urOrg',
				title:"机构",
				width:30,
				align:'center',
				formatter:function(val,row,index){  
					return row.organization.orgName;
				},  
			},{
				field:'urDep',
				title:"部门",
				width:30,
				align:'center',
				formatter:function(val,row,index){  
					return row.department.depName;
				},  
			}
			]],
		});

		$('#user_select').dialog({
			title: '选择用户',
			width: 815,
			height: 680,
			closed: false,
			cache: false,
			modal: true,
			buttons:[{
				text:'确定添加',
				iconCls:'icon-ok',
				handler:function(){
					SubmitSelectedUser(id);
				}
			},{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function(){
					user_select_datagrid.datagrid("clearSelections");
					$('#user_select').dialog("close");
				}
			}]
		});
	}

	function SubmitSelectedUser(id){
		var selectRows =user_select_datagrid.treegrid("getSelections");
		if (selectRows.length < 1) {
			$.messager.alert("提示消息", "请选择要新增的用户!");
			return;
		}
		var uids = '';
		for(var i =0; i< selectRows.length;i++){  
			uids += selectRows[i].usId+",";
		} 
		$.ajax({
			url: getRootPath() + "admin/userRole/AddUserToRole",
			type: "post",
			dataType: "json",
			data:{"rid": id,"uids":uids},
			success: function (data) {
				if(data.code == 200){
					user_select_datagrid.datagrid("clearSelections");
					$('#user_select').dialog("close");
					user_datagrid.datagrid("reload");
				}else{
					HandleException(data);
				}
			}
		});
	}
	
	function alertPermissionManageDialog(){
		var selectRows =datagrid.treegrid("getSelections");
		if (selectRows.length < 1) {
			$.messager.alert("提示消息", "请选择要设置权限的角色!");
			return;
		}
		loadMenu(selectRows[0].rlId);
		permanage(selectRows[0].rlName,selectRows[0].rlId);
	}
	
	function permanage(role,id){
		$('#permission-manage-box').dialog({
			title: "角色["+role+']的权限管理',
			width: 300,
			height: 700,
			closed: true,
			cache: false,
			modal: true,
			buttons:[{
				text:'提交',
				iconCls:'icon-ok',
				handler:function(){
					var nodes = $("#menu-tree").tree('getChecked',['checked','indeterminate']);
					var ids = "";
					for(var i = 0;i<nodes.length;i++){
						ids +=nodes[i].id + ",";
					}
					savePermission(id,ids);
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#permission-manage-box').dialog("close");
				}
			}]
		});
	}
	
	function loadMenu(id){
		$('#menu-tree').tree({
			method:"GET",
		    url: getRootPath() + 'admin/menu/SelectMenuByRidWithChecked?rid='+id,
		    checkbox:true,
		    animate:true,
		    lines:true,
		    cascadeCheck:false,
		    onBeforeLoad:function(){
		    	MaskUtil.mask();
		    },
		    onLoadSuccess:function(){
		    	MaskUtil.unmask();
		    	$('#permission-manage-box').dialog("open");
		    },
		    loadFilter: function(data){
				if (data.code == 200){
					return data.data;
				} else {
					HandleException(data);
				}
		    }
		});
	}
	
	function savePermission(id,ids){
		$.ajax({
			url: getRootPath() + "admin/role/SaveRoleMenu",
			type: "post",
			dataType: "json",
			data:{"id":id,"ids":ids},
			beforeSend: function(){
				$.messager.progress({ 
					title:'请稍后', 
					msg:'正在保存...' 
					}); 
			},
			complete: function(){
				 $.messager.progress('close');
			},
			success: function (data) {
				if(data.code == 200){
					$('#permission-manage-box').dialog("close");
					$.messager.alert("提示", "保存成功");
				}else{
					HandleException(data);
				}
			}
		});
	}
	
});