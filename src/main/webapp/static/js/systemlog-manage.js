/**
 * 
 */

$(function(){
	var datagrid; //定义全局变量datagrid
	var editRow = undefined; //定义全局变量：当前编辑的行
	datagrid = $("#systemlog-tb").datagrid({
		dnd: true,
		method:"POST",
		url:getRootPath() + "admin/systemlog/selectSystemLogOfAll",
		idField:'lgId',
		rownumbers: true,
		checkOnSelect : true, 
		singleSelect:true,
		width: $(window).width() - 6,
		height: $(window).height() - 110,
		striped: true, //行背景交换
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
		},{
			field:'lgDescription',
			title:"操作描述",
			width:80,
			align:'center',
		},{
			field:'lgMethod',
			title:"操作请求方法",
			width:140,
			align:'center',
		},{
			field:'user',
			title:"操作者",
			width:100,
			align:'center',
			formatter:function(val){  
				return val.usName+" [ "+val.organization.orgName+"，"+val.department.depName+" ] ";  
			},  
		},{
			field:'lgCreateDate',
			title:"操作时间",
			width:50,
			align:'center',
			formatter:function(val){  
				return jsonTimeStamp(val);  
			},  
		}
		]],
		toolbar:'#toolbar',	
		onLoadSuccess: function(row){
			datagrid.datagrid("unselectAll");
		},
	});

	//设置分页控件 
	var p = datagrid.datagrid('getPager'); 
	$(p).pagination({ 
		beforePageText: '第',//页数文本框前显示的汉字 
		afterPageText: '页    共 {pages} 页', 
		displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
	}); 


	$("#search-type").combobox({
		onChange: function (n,o) {
			if($(this).val() == 0){
				$(".td_description").show();
				$(".td_method").hide();
				$(".td_datetime").hide();
			}else if($(this).val() == 1){
				$(".td_description").hide();
				$(".td_datetime").hide();
				$(".td_method").show();
			}else if($(this).val() == 2){
				$(".td_description").hide();
				$(".td_datetime").show();
				$(".td_method").hide();
			}
		}
	});
	
	$("#btnSearch").click(function(){
		doSearch();
	});

	function doSearch(){
		if($("#search-type").val() == 0){
			$('#systemlog-tb').datagrid('load',{
				stype: 0,
				skey:  $('#search-description').val()
			});
		}else if($("#search-type").val() == 1){
			$('#systemlog-tb').datagrid('load',{
				stype: 1,
				skey: $('#search-method').val()
			});
		}else{
			$('#systemlog-tb').datagrid('load',{
				stype: 2,
				skey:  $('#search-date-start').datetimebox('getValue')+","+$('#search-date-end').datetimebox('getValue')
			});
		}
	}
	
	$("#show").click(function(){
		showLogDetail();
	});
	
	$('#systemlog-detail-box').dialog({
		title: '日志详情',
		width: 1000,
		height: 600,
		closed: true,
		cache: false,
		modal: true,
		buttons:[{
			text:'关闭',
			iconCls:'icon-cancel',
			handler:function(){
				$('#systemlog-detail-box').dialog("close");
			}
		}]
	});
	
	function showLogDetail(){
		var selectRows =datagrid.treegrid("getSelections");
		if (selectRows.length < 1) {
			$.messager.alert("提示消息", "请选择要查看的文章!");
			return;
		}else{
			$.ajax({
				url: getRootPath() + "admin/systemlog/SelectSystemLogById",
				type: "get",
				dataType: "json",
				data:{"id": selectRows[0].lgId},
				success: function (data) {
					console.log(data);
					if(data.code == 200){
						var log = data.data;
						$(".lg_description").html(log.lgDescription);
						$(".lg_method").html(log.lgMethod);
						$(".lg_params").html(log.lgParams);
						$(".lg_create_user").html(log.user.usName);
						$(".lg_create_ip").html(log.lgRequestIp);
						$(".lg_create_date").html(jsonTimeStamp(log.lgCreateDate));
						$('#systemlog-detail-box').dialog("open");
					}else{
						HandleException(data);
					}
				}
			});
		}
	}
	
	$('#article-detail-box').dialog({
		title: '文章详情',
		width: 1000,
		height: 800,
		closed: true,
		cache: false,
		modal: true,
		buttons:[{
			text:'关闭',
			iconCls:'icon-cancel',
			handler:function(){
				$('#article-detail-box').dialog("close");
			}
		}]
	});
	
	
});