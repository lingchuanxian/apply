$(function(){
	$('#tt').tree({
		method : 'GET',
		url : 'admin/organization/selectOrganizationForSelect',
		animate:true,
		loadFilter: function(data){
			if (data.code == 200){
				return data.data;
			}else{
				HandleException(data);
			}
		},
		error : function(e) {
			$.messager.alert("提示消息", e.message,"error");
		},
		onClick: function(node){
			loadMenu(node.id,"["+node.text+"]—下属部门");
		},
		onLoadSuccess:function(node, data){
			loadMenu(data[0].id,"["+data[0].text+"]—下属部门");
		}
	});
	
	function loadMenu(nodeId,text){
		var datagrid; //定义全局变量datagrid
		var editRow = undefined; //定义全局变量：当前编辑的行
		datagrid = $("#DepManage").datagrid({
			title:text,
			method:"POST",
			url:"admin/department/GetDepartmentByOrgId",
			idField:'depId',
			rownumbers: true,
			striped: true, //行背景交换
			singleSelect:true,
			queryParams: {          
				id: nodeId            
			},  
			loadFilter: function(data){
				if (data.code == 200){
					return data.data;
				}else{
					HandleException(data);
				}
			},
			columns:[[
			{
				field:'depId',
				title:"部门编号",
				width:100,
				align:'center',
			},{
				field:'depName',
				title:"部门名称",
				width:300,
				align:'center',
				editor: { type: 'validatebox', options: { required: true} }
			},{
				field:'depDetail',
				title:"部门描述",
				width:400,
				align:'center',
				editor: { 
					type: 'validatebox',
				}
			}]],
			toolbar:[{
				text:'新增',
				iconCls:'icon-image-add',
				handler:function(){
					//添加时先判断是否有开启编辑的行，如果有则把开户编辑的那行结束编辑
					if (editRow != undefined) {
						datagrid.datagrid("endEdit", editRow);
					}
					//添加时如果没有正在编辑的行，则在datagrid的最后追加
					if (editRow == undefined) {
						var index = datagrid.datagrid("appendRow", {
							depName: '',
							depDetail: '',
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
				iconCls:'icon-layout-edit',
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
				//保存时结束当前编辑的行，自动触发onAfterEdit事件如果要与后台交互可将数据通过Ajax提交后台
				datagrid.datagrid("endEdit", editRow);
				editRow = undefined;
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
				iconCls:'icon-layout-delete',
				handler:function(){
					doDelete(datagrid);
				}
			},{
				text:'上移',
				iconCls:'icon-arrow-up',
				handler:function(){
					MoveUp(datagrid);
				}
			},
			{
				text:'下移',
				iconCls:'icon-arrow-down',
				handler:function(){
					MoveDown(datagrid);
				}
			}],
			onBeforeLoad:function(){
				$("#save").hide();
				$("#cancle").hide();
			},
			onLoadSuccess: function(data){
				datagrid.datagrid("unselectAll");
				if (data.total == 0) { 
					//添加一个新数据行，第一列的值为你需要的提示信息，然后将其他列合并到第一列来，注意修改colspan参数为你columns配置的总列数 
					$(this).datagrid('appendRow', { depId: '<div style="color:red">暂无下属部门！</div>' }).datagrid('mergeCells', { index: 0, field: 'depId', colspan: 3 }) 
				} 
			},
			onBeforeEdit:function(index,row){
				$("#save").show();
				$("#cancle").show();
			},
			onAfterEdit:function(index,row){
				$.ajax({
					url:'admin/department/AddOrUpdateDep',
					type:'post',
					dataType: 'json',
					data: {  
						"depId" : row.depId,
						"depName":row.depName,
						"depDetail":row.depDetail,
						"depPid":0,
						"depOrgid":nodeId
					},
					success:function(data){
						if(data.code == 200){
							$("#save").hide();
							$("#cancle").hide();
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
				editRow = undefined;
				datagrid.datagrid("unselectAll");
			},
		});
	}

	//上移
	function MoveUp(datagrid) {
		var row = datagrid.datagrid("getSelected");
		if (row != null) {
			var index =datagrid.datagrid('getRowIndex', row);
			mysort(index, 'up', datagrid);
		}else{
			$.messager.alert("移动提示", "请选择要进行移动的行");
		}
	}
	//下移
	function MoveDown(datagrid) {
		var row = datagrid.datagrid("getSelected");
		if (row != null) {
			var index = datagrid.datagrid('getRowIndex', row);
			mysort(index, 'down',datagrid);
		}else{
			$.messager.alert("移动提示", "请选择要进行移动的行");
		}

	}


	function mysort(index, type, datagrid) {
		if ("up" == type) {
			if (index != 0) {
				var toup = datagrid.datagrid('getData').rows[index];
				var todown = datagrid.datagrid('getData').rows[index - 1];

				datagrid.datagrid('getData').rows[index] = todown;
				datagrid.datagrid('getData').rows[index - 1] = toup;
				exchange(toup.depId,todown.depId);
				datagrid.datagrid('refreshRow', index);
				datagrid.datagrid('refreshRow', index - 1);
				datagrid.datagrid('selectRow', index - 1);
			}
		} else if ("down" == type) {
			var rows = datagrid.datagrid('getRows').length;
			if (index != rows - 1) {
				var todown = datagrid.datagrid('getData').rows[index];
				var toup = datagrid.datagrid('getData').rows[index + 1];
				datagrid.datagrid('getData').rows[index + 1] = todown;
				datagrid.datagrid('getData').rows[index] = toup;
				exchange(toup.depId,todown.depId);
				datagrid.datagrid('refreshRow', index);
				datagrid.datagrid('refreshRow', index + 1);
				datagrid.datagrid('selectRow', index + 1);
			}
		}
	}

	function exchange(position1,position2){
		$.ajax({
			url:'admin/department/ExchangeDepPosition',
			type:'post',
			dataType: 'json',
			data: {  
				"id1" : position1,
				"id2" : position2,
			},
			success:function(data){
				console.log("exchange:"+data);
			},
			error:function(){
				$.messager.alert("提示消息", e.message,"error");
			}
		});
	}


	//删除数据
	function doDelete(datagrid) {
		var selectRows =datagrid.treegrid("getSelections");
		if (selectRows.length < 1) {
			$.messager.alert("提示消息", "请选择要删除的菜单!");
			return;
		}
		//提醒用户是否是真的删除数据
		$.messager.confirm("确认消息", "您确定要删除部门【"+selectRows[0].depName+"】吗？", function (r) {
			if (r) {
				MaskUtil.mask();
				$.ajax({
					url: "admin/department/DeleteDepById",
					type: "post",
					dataType: "json",
					data:{"id": selectRows[0].depId},
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

});