/**
 * 
 */

$(function(){
	var treegrid; //定义全局变量datagrid
	var editRow = undefined; //定义全局变量：当前编辑的行
	treegrid = $("#organization-tb").treegrid({
		dnd: true,
		method:"GET",
		url:"admin/organization/selectOrganizationOfAll",
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
		toolbar:[{
			text:'新增',
			iconCls:'icon-image-add',
			handler:function(){
				loadCombotree();
				$('#organization-box').dialog("open");
				$("#organization-form").form("disableValidation");
			}
		},{
			text:'删除',
			iconCls:'icon-layout-delete',
			handler:function(){
				doDelete();
			}
		}],
		onBeforeLoad:function(){
		},
		onLoadSuccess: function(row){
			treegrid.datagrid("unselectAll");
		}
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


	function loadCombotree(){
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
				var row = treegrid.treegrid('getSelections');
				if(row.length == 0){
					$("#org-combox").combotree('setValue', { id: data[0].id, text: data[0].text }); 
				}else{
					$("#org-combox").combotree('setValue', { id: row[0].orgId, text: row[0].orgName }); 
				}
			}
		});  
	}

	function formAddSubmit(){
		$('#organization-form').form('submit', {
			url:'admin/organization/insertOrUpdateOrganization',
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
					url: "admin/organization/DeleteOrganizationById",
					type: "post",
					dataType: "json",
					data:{"id": selectRows[0].orgId},
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

});