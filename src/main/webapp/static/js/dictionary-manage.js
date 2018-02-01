/**
 * 
 */

$(function(){
	loadType($('#search-type-combox'),1);
	var datagrid; //定义全局变量datagrid
	var editRow = undefined; //定义全局变量：当前编辑的行
	datagrid = $("#data-table").datagrid({
		dnd: true,
		method:"POST",
		url:getRootPath() + "admin/dictionary/select",
		idField:'dictId',
		rownumbers: true,
		checkOnSelect : true,  
		width: $(window).width() - 6,
		height: $(window).height() - 110,
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
				field:'dictType',
				title:"类型",
				width:400,
				align:'center',
				formatter:function(val){
					return val.dtName;
				}
			},{
				field:'dictCode',
				title:"字典编号",
				width:150,
				align:'center',
			},{
				field:'dictName',
				title:"字典名称",
				width:400,
				align:'center',
			},{
				field:'dictDescription',
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
	
	//############################	搜索开始	###############################
	$("#search-type").combobox({
		onChange: function (n,o) {
			if($(this).val() == 0){
				$(".td_code").show();
				$(".td_name").hide();
				$(".td_type").hide();
			}else if($(this).val() == 1){
				$(".td_code").hide();
				$(".td_name").show();
				$(".td_type").hide();
			}else if($(this).val() == 2){
				$(".td_code").hide();
				$(".td_name").hide();
				$(".td_type").show();
			}
		}
	});
	
	$("#btnSearch").click(function(){
		doSearch();
	});

	function doSearch(){
		var type = $("#search-type").val();
		if(type == 0){
			$('#data-table').datagrid('load',{
				stype: 0,
				skey: $('#search-code').val()
			});
		}else if(type == 1){
			$('#data-table').datagrid('load',{
				stype: 1,
				skey: $('#search-name').val()
			});
		}else if(type == 2){
			$('#data-table').datagrid('load',{
				stype: 2,
				skey: $('#search-type-combox').val()
			});
		}
	}
	
	//############################	搜索结束	###############################

	//###########################	新增开始	############################
	$("#add").click(function(){
		loadType($('#add-type-combox'),1);
		$('#add-box').dialog("open");
		$("#add-form").form("disableValidation");
	});

	$('#add-box').dialog({
		title: '数据字典新增',
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
			url:getRootPath() + 'admin/dictionary/insert',
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
			$.messager.alert("提示消息", "请选择要编辑的字典!");
			return;
		}else if(selectRows.length > 1){
			$.messager.alert("提示消息", "只能选择一条的记录!");
			return;
		}else{
			$.ajax({
				url: getRootPath() + "admin/dictionary/select/"+selectRows[0].dictId,
				type: "post",
				dataType: "json",
				success: function (data) {
					if(data.code == 200){
						var dict = data.data;
						console.log(dict);
						loadType($('#edit-type-combox'),2);
						$("#edit-dictId").val(dict.dictId);
						$("#edit-dictCode").textbox('setValue',dict.dictCode);
						$("#edit-dictName").textbox("setValue", dict.dictName);
						$("#edit-type-combox").combobox("select", dict.dictTypeId);
						$("#edit-dictDescription").textbox("setValue", dict.dictDescription);
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
		title: '字典编辑',
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
			url:getRootPath() + 'admin/dictionary/update',
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
			$.messager.alert("提示消息", "请选择要删除的字典!");
			return;
		}
		//提醒用户是否是真的删除数据
		$.messager.confirm("确认消息", "您确定要删除字典【"+selectRows[0].dictName+"】吗？", function (r) {
			if (r) {
				MaskUtil.mask();
				$.ajax({
					url: getRootPath() + "admin/dictionary/delete/"+selectRows[0].dictId,
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

	//###########################	加载类别开始	############################

	function loadType(combobox,type){
		combobox.combobox({  
			method:"POST",
			url:getRootPath() + 'admin/dictionaryType/selectAll',  
			valueField:'dtId',  
			textField:'dtName',
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
				if(type == 1){
					if(data.length > 0){
						$(this).combobox("select", data[0].dtId);
					}
				}
			}
		});  
	}
	//###########################	加载类别结束	############################
});