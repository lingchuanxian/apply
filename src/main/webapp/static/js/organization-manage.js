/**
 * 
 */

$(function(){
	var treegrid; //定义全局变量datagrid
	var editRow = undefined; //定义全局变量：当前编辑的行
	treegrid = $("#organization-tb").treegrid({
		dnd: true,
		method:"GET",
		url:getRootPath() + "admin/organization/select",
		idField:'orgId',
		treeField:'orgName',
		rownumbers: true,
		animate : true,
		width: $(window).width() - 20,
		height: $(window).height() - 10,
		fitColumns:true,
		singleSelect:true,
		loadFilter: function(data){
			if (data.code == 200){
				return data.data;
			}else{
				HandleException(data);
			}
		},
		columns:[[{
			field:'orgName',
			title:"机构名称",
			width:70,
		},{
			field:'orgCode',
			title:"机构代码",
			width:70,
			align:'center',
		},{
			field:'orgDetail',
			title:"机构描述",
			align:'center',
			width:200
		}
		]],
		toolbar:'#toolbar',
		onBeforeLoad:function(){
		},
		onLoadSuccess: function(row){
			treegrid.datagrid("unselectAll");
		}
	});

	//################################	新增开始	##################################
	
	$("#add").click(function(){
		loadCombotree($("#org-combox"),null);
		$('#organization-box').dialog("open");
		$("#organization-form").form("disableValidation");
	});
	
	$('#organization-box').dialog({
		title: '机构新增',
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
				$('#organization-box').dialog("close");
			}
		}]
	});
	

	function formAddSubmit(){
		$('#organization-form').form('submit', {
			url:getRootPath() + 'admin/organization/insert',
			onSubmit: function(){
				return $(this).form('enableValidation').form('validate');
			},
			success:function(data){
				$('#organization-box').dialog("close");
				$('#organization-form').form("clear");
				treegrid.treegrid("reload");
			}
		});
	}
	//################################	新增结束	##################################

	//################################	编辑开始	##################################
	
	$("#edit").click(function(){
		var selectRows =treegrid.treegrid("getSelections");
		if (selectRows.length < 1) {
			$.messager.alert("提示消息", "请选择要编辑的机构!");
			return;
		}
		MaskUtil.mask();
		
		$.ajax({
			url: getRootPath() + "admin/organization/select/"+selectRows[0].orgId,
			type: "post",
			dataType: "json",
			success: function (data) {
				MaskUtil.unmask();
				if(data.code == 200){
					var org = data.data;
					console.log(org);
					$("#orgId").val(org.orgId);
					$("#edit-orgName").textbox('setValue',org.orgName);
					$("#edit-orgCode").textbox('setValue',org.orgCode);
					$("#edit-orgDetail").textbox('setValue',org.orgDetail);
					loadCombotree($("#org-combox2"),org.parent);
					$('#organization-edit-box').dialog("open");
					$("#organization-edit-form").form("disableValidation");
				}else{
					HandleException(data);
				}
			}
		});
		
	});
	
	$('#organization-edit-box').dialog({
		title: '机构编辑',
		width: 400,
		height: 400,
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
				$('#organization-edit-box').dialog("close");
			}
		}]
	});
	
	function formEditSubmit(){
		$('#organization-edit-form').form('submit', {
			url:getRootPath() + 'admin/organization/update',
			onSubmit: function(){
				return $(this).form('enableValidation').form('validate');
			},
			success:function(data){
				$('#organization-edit-box').dialog("close");
				$('#organization-edit-form').form("clear");
				treegrid.treegrid("reload");
			}
		});
	}
	
	//################################	编辑结束	##################################

	function loadCombotree(combotree,parent){
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
				if(parent != null){
					combotree.combotree('setValue', { id: parent.orgId, text: parent.orgName });
				}else{
					var row = treegrid.treegrid('getSelections');
					if(row.length == 0){
						combotree.combotree('setValue', { id: data[0].id, text: data[0].text }); 
					}else{
						combotree.combotree('setValue', { id: row[0].orgId, text: row[0].orgName }); 
					}
				}
			}
		});  
	}

	//################################	删除开始	##################################

	$("#delete").click(function(){
		doDelete();
	});
	
	//删除数据
	function doDelete() {
		var selectRows =treegrid.treegrid("getSelections");
		if (selectRows.length < 1) {
			$.messager.alert("提示消息", "请选择要删除的机构!");
			return;
		}
		//提醒用户是否是真的删除数据
		$.messager.confirm("确认消息", "您确定要删除机构【"+selectRows[0].orgName+"】吗？", function (r) {
			if (r) {
				MaskUtil.mask();
				$.ajax({
					url: getRootPath() + "admin/organization/delete/"+selectRows[0].orgId,
					type: "post",
					dataType: "json",
					success: function (data) {
						MaskUtil.unmask();
						if(data.code == 200){
							treegrid.treegrid("reload");
							treegrid.treegrid("clearSelections");
						}else{
							HandleException(data);
						}
					}
				});
			}
		});
	}

	//################################	删除结束	##################################
	
});