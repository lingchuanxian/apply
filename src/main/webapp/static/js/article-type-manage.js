/**
 * 
 */

$(function(){
	var datagrid; //定义全局变量datagrid
	var editRow = undefined; //定义全局变量：当前编辑的行
	datagrid = $("#art-tb").datagrid({
		dnd: true,
		method:"GET",
		url:"admin/articleType/selectArticleTypeOfAll",
		idField:'atId',
		rownumbers: true,
		checkOnSelect : true,  
		width: $(window).width() - 20,
		height: $(window).height() - 10,
		striped: true, //行背景交换
		fitColumns:false,
		singleSelect:true,
		loadFilter: function(data){
			console.log(data);
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
			field:'atId',
			title:"类别编号",
			width:150,
			align:'center',
		},{
			field:'atName',
			title:"类别名称",
			width:400,
			align:'center',
			editor: { type: 'validatebox', options: { required: true} }
		}
		]],
		toolbar:'#toolbar'
			/*[{
			text:'新增',
			iconCls:'icon-application-form-add',
			handler:function(){
				//添加时先判断是否有开启编辑的行，如果有则把开户编辑的那行结束编辑
				if (editRow != undefined) {
					datagrid.datagrid("endEdit", editRow);
				}
				//添加时如果没有正在编辑的行，则在datagrid的最后追加
				if (editRow == undefined) {
					var index = datagrid.datagrid("appendRow", {
						atName: '',
					}).datagrid('getRows').length-1;;
					//将新插入的那一行开户编辑状态
					datagrid.datagrid("beginEdit", index);
					datagrid.datagrid("checkRow",index);
					//给当前编辑的行赋值
					editRow = index;
				}
			}
		},'-',{
			text:'编辑',
			iconCls:'icon-application-form-edit',
			handler:function(){
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
			}
		},
		{ id : 'save', text: '保存', iconCls: 'icon-disk', handler: function () {
			
		}
		},
		{ id : 'cancle', text: '取消编辑', iconCls: 'icon-undo', handler: function () {
			datagrid.datagrid('cancelEdit',editRow)
			editRow = undefined;
			datagrid.datagrid("unselectAll");
			//添加时取消新增的一行还在，暂时用刷新页面的方法
			datagrid.datagrid("reload");
			$("#save").hide();
			$("#cancle").hide();
		}
		},'-',{
			text:'删除',
			iconCls:'icon-application-form-delete',
			handler:function(){
				doDelete(datagrid);
			}
		}]*/,
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
				url:'admin/articleType/AddOrUpdateArticleType',
				type:'post',
				dataType: 'json',
				data: {  
					"atId" : row.atId,
					"atName":row.atName,
				},
				success:function(data){
					$("#save").hide();
					$("#cancle").hide();
					editRow = undefined;
					datagrid.datagrid("reload",editRow);
				},
				error:function(){
					$("#save").show();
					$("#cancle").show();
				}
			});
		},
	});

	//###########################	新增开始	############################
	$("#add").click(function(){
		//添加时先判断是否有开启编辑的行，如果有则把开户编辑的那行结束编辑
		if (editRow != undefined) {
			datagrid.datagrid("endEdit", editRow);
		}
		//添加时如果没有正在编辑的行，则在datagrid的最后追加
		if (editRow == undefined) {
			var index = datagrid.datagrid("appendRow", {
				atName: '',
			}).datagrid('getRows').length-1;;
			//将新插入的那一行开户编辑状态
			datagrid.datagrid("beginEdit", index);
			datagrid.datagrid("checkRow",index);
			//给当前编辑的行赋值
			editRow = index;
		}
	});
	
	//###########################	新增结束	############################
	
	//###########################	编辑开始	############################
	
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
	
	//###########################	编辑结束	############################
	
	//###########################	保存开始	############################
	
	$("#save").click(function(){
		//保存时结束当前编辑的行，自动触发onAfterEdit事件如果要与后台交互可将数据通过Ajax提交后台
		datagrid.datagrid("endEdit", editRow);
		editRow = undefined;
	});
	
	//###########################	保存结束	############################
	
	//###########################	取消编辑开始	############################
	
	$("#cancle").click(function(){
		datagrid.datagrid('cancelEdit',editRow)
		editRow = undefined;
		datagrid.datagrid("unselectAll");
		//添加时取消新增的一行还在，暂时用刷新页面的方法
		datagrid.datagrid("reload");
		$("#save").hide();
		$("#cancle").hide();
	});
	
	//###########################	取消编辑结束	############################
	
	//###########################	删除开始	############################
	
	$("#delete").click(function(){
		doDelete();
	});
	
	//删除数据
	function doDelete() {
		var selectRows =datagrid.treegrid("getSelections");
		if (selectRows.length < 1) {
			$.messager.alert("提示消息", "请选择要删除的菜单!");
			return;
		}
		//提醒用户是否是真的删除数据
		$.messager.confirm("确认消息", "您确定要删除类别【"+selectRows[0].atName+"】吗？", function (r) {
			if (r) {
				MaskUtil.mask();
				$.ajax({
					url: "admin/articleType/DeleteArtTypeById",
					type: "post",
					dataType: "json",
					data:{"id": selectRows[0].atId},
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

	//###########################	删除结束	############################
	
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

});