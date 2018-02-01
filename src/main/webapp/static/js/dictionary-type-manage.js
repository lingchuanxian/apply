/**
 * 
 */

$(function(){
	var datagrid; //定义全局变量datagrid
	var editRow = undefined; //定义全局变量：当前编辑的行
	var falg = 0;//1--新增  2--更新
	datagrid = $("#data-table").datagrid({
		dnd: true,
		method:"POST",
		url:getRootPath() + "admin/dictionaryType/select",
		idField:'dtId',
		rownumbers: true,
		checkOnSelect : true,  
		width: $(window).width() - 20,
		height: $(window).height() - 10,
		striped: true, //行背景交换
		fitColumns:false,
		singleSelect:true,
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
		loadFilter: function(data){
			console.log(data);
			if (data.code == 200){
				return data.data;
			}else{
				HandleException(data);
			}
		},
		columns:[[
		{
			field:'dtCode',
			title:"字典类别编号",
			width:150,
			align:'center',
		},{
			field:'dtName',
			title:"字典类别名称",
			width:400,
			align:'center',
		},{
			field:'dtDescription',
			title:"描述",
			width:600,
			align:'center',
		}
		]],
		toolbar:'#toolbar',
		onLoadSuccess: function(row){
			datagrid.datagrid("unselectAll");
		}
	});

	//###########################	新增开始	############################
	$("#add").click(function(){
		$('#add-box').dialog("open");
		$("#add-form").form("disableValidation");
	});
	
	$('#add-box').dialog({
		title: '数据字典类别新增',
		width: 400,
		height: 400,
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
				$('#add-box').dialog("close");
			}
		}]
	});
	

	function formAddSubmit(){
		$('#add-form').form('submit', {
			url:getRootPath() + 'admin/dictionaryType/insert',
			onSubmit: function(){
				return $(this).form('enableValidation').form('validate');
			},
			success:function(data){
				$('#add-box').dialog("close");
				$('#add-form').form("clear");
				datagrid.datagrid("reload");
			}
		});
	}
	
	//###########################	新增结束	############################
	
	//###########################	编辑开始	############################

	$("#edit").click(function(){
		articleEdit();
	});
	
	function articleEdit(){
		var selectRows =datagrid.treegrid("getSelections");
		if (selectRows.length < 1) {
			$.messager.alert("提示消息", "请选择要编辑的字典类别!");
			return;
		}else if(selectRows.length > 1){
			$.messager.alert("提示消息", "只能选择一条的记录!");
			return;
		}else{
			$.ajax({
				url: getRootPath() + "admin/dictionaryType/select/"+selectRows[0].dtId,
				type: "post",
				dataType: "json",
				success: function (data) {
					if(data.code == 200){
						var dt = data.data;
						console.log(dt);
						$("#edit-dtId").val(dt.dtId);
						$("#edit-dtCode").textbox('setValue',dt.dtCode);
						$("#edit-dtName").textbox("setValue", dt.dtName);
						$("#edit-dtDescription").textbox("setValue", dt.dtDescription);
						$("#edit-form").form("disableValidation");
						$('#edit-box').dialog("open");
					}else{
						HandleException(data);
					}
				}
			});
			
		}
	}
	
	$('#edit-box').dialog({
		title: '字典类别编辑',
		width: 400,
		height: 400,
		closed: true,
		cache: false,
		modal: true,
		buttons:[{
			text:'提交',
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
	
	function formEditSubmit(){
		$('#edit-form').form('submit', {
			url:getRootPath() + 'admin/dictionaryType/update',
			onSubmit: function(){
				return $(this).form('enableValidation').form('validate');
			},
			success:function(data){
				$("#edit-form").form('clear');
				$('#edit-box').dialog("close");
				datagrid.datagrid("reload");
			},
			error:function(){
				alert("error");
			}
		});
	}
	
	//###########################	编辑结束	############################
	
	//###########################	删除开始	############################
	
	$("#delete").click(function(){
		doDelete();
	});
	
	//删除数据
	function doDelete() {
		var selectRows =datagrid.treegrid("getSelections");
		if (selectRows.length < 1) {
			$.messager.alert("提示消息", "请选择要删除的字典类别!");
			return;
		}
		//提醒用户是否是真的删除数据
		$.messager.confirm("确认消息", "您确定要删除字典类别【"+selectRows[0].dtName+"】吗？", function (r) {
			if (r) {
				MaskUtil.mask();
				$.ajax({
					url: getRootPath() + "admin/dictionaryType/delete/"+selectRows[0].dtId,
					type: "post",
					dataType: "json",
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
});