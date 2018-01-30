/**
 * 
 */

$(function(){
	var datagrid; //定义全局变量datagrid
	var editRow = undefined; //定义全局变量：当前编辑的行
	datagrid = $("#user-tb").datagrid({
		title:"用户列表",
		method:"GET",
		url:"admin/user/GetUserList",
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
				/*[{
				text:'新增',
				iconCls:'icon-user-add',
				handler:function(){
					loadCombotreeOfOrg();
					getRoleType($('#role-combox'));
					$("#user-form").form("disableValidation");
					$('#user-box').dialog("open");
				}
			},'-',{
				text:'查看',
				iconCls:'icon-user-magnify',
				handler:function(){
					showDetail();
				}
			},'-',{
				text:'查看角色',
				iconCls:'icon-lock-key',
				handler:function(){
					showRole(datagrid);
				}
			},'-',{
				text:'编辑',
				iconCls:'icon-user-edit',
				handler:function(){

				}
			},'-',{
				text:'重置密码',
				iconCls:'icon-key-go',
				handler:function(){
					resetPassword();
				}
			},'-',{
				text:'删除',
				iconCls:'icon-user-delete',
				handler:function(){
					doDelete(datagrid);
				}
			},'-',{
				id:'start',
				text:'启用',
				iconCls:'icon-user-accept16',
				handler:function(){
					changeState(0);
				}
			},{
				id:'stop',
				text:'停用',
				iconCls:'icon-user-reject16',
				handler:function(){
					changeState(1);
				}
			}],*/
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

	$("#add").click(function(){
		loadCombotreeOfOrg();
		getRoleType($('#role-combox'));
		$("#user-form").form("disableValidation");
		$('#user-box').dialog("open");
	});
	
	$("#resetpwd").click(function(){
		resetPassword();
	});
	
	$("#show").click(function(){
		showDetail();
	});
	
	$("#delete").click(function(){
		doDelete();
	});
	
	$("#show-role").click(function(){
		showRole();
	});
	
	$("#enable").click(function(){
		changeState(0);
	});
	
	$("#disable").click(function(){
		changeState(1);
	});
	
	//查看角色
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
			url:"admin/userRole/SelectUserRoleByUid",
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
					url: "admin/userRole/RemoveUserOfRole",
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
					url: "admin/user/ResetUserPassword",
					type: "post",
					dataType: "json",
					data:{"id": selectRows[0].usId},
					success: function (data) {
						MaskUtil.unmask();
						console.log(data);
						if(data.code == 200){
							//成功
						}else{
							HandleException(data);
						}
					}
				});
			}
		});
	}

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
					url: "admin/user/DeleteUserById",
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

	/*getRoleType($('#role-combox-search'));*/
	$("#btnSearch").click(function(){
		doSearch();
	});

	function doSearch(){
		$('#user-tb').datagrid('load',{
			name: $('#name').val(),
			loginName: $('#loginName').val()
		});
	}

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


	function loadCombotreeOfOrg(){
		$("#org-combox").combotree({  
			method:"GET",
			url:'admin/organization/selectOrganizationForSelect',  
			editable:false,
			loadFilter: function(data){
				if (data.code == 200){
					return data.data;
				}else{
					HandleException(data);
				}
			},
			onLoadSuccess: function (node,data) { 
				$("#org-combox").combotree('setValue', { id: data[0].id, text: data[0].text }); 
				loadCombotreeOfDep(data[0].id);
			},
			onSelect:function(node) {
				loadCombotreeOfDep(node.id);
			}
		});  
	}

	function loadCombotreeOfDep(id){
		$("#dep-combox").combotree({  
			method:"GET",
			url:'admin/department/selectDepForSelect?id='+id,  
			editable:false,
			loadFilter: function(data){
				if (data.code == 200){
					return data.data;
				}else{
					HandleException(data);
				}
			},
			onLoadSuccess: function (node,data) { 
				$("#dep-combox").combotree('setValue', { id: data[0].id, text: data[0].text }); 
			}
		});  
	}


	function getRoleType(combobox){
		combobox.combobox({  
			method:"GET",
			url:'admin/role/selectAllOfRole',  
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

	function formAddSubmit(){
		$('#user-form').form('submit', {
			url:'admin/user/AddUser',
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

	function showDetail(){
		var selectRows =datagrid.treegrid("getSelections");
		if (selectRows.length < 1) {
			$.messager.alert("提示消息", "请选择要查看的用户!");
			return;
		}else{
			$.ajax({
				url: "admin/user/SelectUserById",
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
					url:'admin/user/UpdateUserState',
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
});