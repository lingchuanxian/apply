/**
 * 
 */

$(function(){
	var editor1,editor2 ;
	KindEditor.ready(function(K) {
		editor1 = K
				.create(
						'textarea[name="artContent"]',
						{
							cssPath : getRootPath() + 'static/kindeditor/plugins/code/prettify.css',
							uploadJson : getRootPath() + 'static/kindeditor/jsp/upload_json.jsp',
							fileManagerJson : getRootPath() + 'static/kindeditor/jsp/file_manager_json.jsp',
							allowFileManager : true,
							afterCreate : function() {
								var self = this;
								K.ctrl(document, 13, function() {
									self.sync();
								});
								K.ctrl(self.edit.doc, 13, function() {
									self.sync();
								});
							},
							afterBlur : function() {
								this.sync();
							}
						});
		prettyPrint();
	});
	
	KindEditor.ready(function(K) {
		editor2 = K
				.create(
						'textarea[name="artContents"]',
						{
							cssPath : getRootPath() + 'static/kindeditor/plugins/code/prettify.css',
							uploadJson : getRootPath() + 'static/kindeditor/jsp/upload_json.jsp',
							fileManagerJson :getRootPath() +  'static/kindeditor/jsp/file_manager_json.jsp',
							allowFileManager : true,
							afterCreate : function() {
								var self = this;
								K.ctrl(document, 13, function() {
									self.sync();
								});
								K.ctrl(self.edit.doc, 13, function() {
									self.sync();
								});
							},
							afterBlur : function() {
								this.sync();
							}
						});
		prettyPrint();
	});
	
	var datagrid; //定义全局变量datagrid
	var editRow = undefined; //定义全局变量：当前编辑的行
	getArticleType($('#search-type-combox'),1);
	datagrid = $("#article-tb").datagrid({
		dnd: true,
		method:"GET",
		url:getRootPath() + "admin/article/select",
		idField:'artId',
		rownumbers: true,
		checkOnSelect : true,  
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
			field:'artTitle',
			title:"文章标题",
			width:400,
			align:'center',
			formatter:function(val, row, index){  
				if(row.artTop == 1){
					return "<img src='static/images/top.png' class='top'/>"+val;
				}else{
					return val;
				}
			}, 
		},{
			field:'articleType',
			title:"类别",
			width:80,
			align:'center',
			formatter:function(val){  
				return val.dictName;
			}, 
		},{
			field:'artDate',
			title:"发布时间",
			width:80,
			align:'center',
			formatter:function(val){  
				return jsonTimeStamp(val);  
			},  
		},{
			field:'user',
			title:"发布者",
			width:60,
			align:'center',
			formatter:function(val){  
				if(val){  
					return val.usName;  
				}  
			},  
		},{
			field:'artTimes',
			title:"浏览次数",
			width:60,
			align:'center',
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

	//###########################  新增 开始    ##############################
	$("#add").click(function(){
		getArticleType($('#type-combox'),1);
		editor1.html("");
		$("#article-form").form("disableValidation");
		$('#article-add-box').dialog("open");
	});
	
	$('#article-add-box').dialog({
		title: '信息新增',
		width: 1000,
		height: 800,
		closed: true,
		cache: false,
		modal: true,
		buttons:[{
			text:'提交',
			iconCls:'icon-ok',
			handler:function(){
				formAddSubmit();
			}
		},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				$('#article-add-box').dialog("close");
			}
		}]
	});

	function getArticleType(combobox,type){
		combobox.combobox({  
			method:"POST",
			url:getRootPath() + 'admin/selectType/ArticleType',  
			valueField:'dictId',  
			textField:'dictName',
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
						$(this).combobox("select", data[0].dictId);
					}
				}
			}
		});  
	}

	function formAddSubmit(){
		$('#article-form').form('submit', {
			url:getRootPath() + 'admin/article/insert',
			onSubmit: function(){
				return $(this).form('enableValidation').form('validate');
			},
			success:function(data){
				console.log(data);
				var dataResult = eval('(' + data + ')');
				if(dataResult.code == 200){
					$("#artTitle").textbox('setValue',"");
					editor1.html("");
					$("#article-form").form('clear');
					$('#article-add-box').dialog("close");
					datagrid.datagrid("reload");
				}else{
					console.log("else");
					HandleException(data);
				}
			},
			error:function(e){
				$.messager.alert("提示消息", e.message,"error");
			}
		});
	}
	
	//############################	新增结束	###############################
	
	//############################	编辑开始	###############################
	
	$("#edit").click(function(){
		articleEdit();
	});
	
	function articleEdit(){
		var selectRows =datagrid.treegrid("getSelections");
		if (selectRows.length < 1) {
			$.messager.alert("提示消息", "请选择要编辑的文章!");
			return;
		}else if(selectRows.length > 1){
			$.messager.alert("提示消息", "只能选择一条的记录!");
			return;
		}else{
			$.ajax({
				url:getRootPath() + "admin/article/select/"+selectRows[0].artId,
				type: "post",
				dataType: "json",
				success: function (data) {
					if(data.code == 200){
						var article = data.data;
						console.log(article);
						getArticleType($('#type-combox2'),2);
						$("#editId").val(article.artId);
						$("#editTitle").textbox('setValue',article.artTitle);
						try{
							editor2.html(article.artContent);
						}catch(error){
							$.messager.alert("提示消息", error,"error");
							return;
						}
						$("#type-combox2").combobox("select", article.articleType.dictId);
						$("#top").combobox("select", article.artTop);
						$("#article-edit-form").form("disableValidation");
						$('#article-edit-box').dialog("open");
					}else{
						HandleException(data);
					}
				}
			});
			
		}
	}
	
	$('#article-edit-box').dialog({
		title: '文章编辑',
		width: 1000,
		height: 800,
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
				$('#article-edit-box').dialog("close");
			}
		}]
	});
	
	function formEditSubmit(){
		$('#article-edit-form').form('submit', {
			url:getRootPath() + 'admin/article/update',
			onSubmit: function(){
				return $(this).form('enableValidation').form('validate');
			},
			success:function(data){
				$("#editTitle").textbox('setValue',"");
				editor2.html("");
				$("#article-form").form('clear');
				$('#article-edit-box').dialog("close");
				datagrid.datagrid("reload");
			},
			error:function(){
				alert("error");
			}
		});
	}
	
	//############################	编辑结束	###############################
	
	//############################	搜索开始	###############################
	$("#search-type").combobox({
		onChange: function (n,o) {
			if($(this).val() == 0){
				$(".td_title").show();
				$(".td_type").hide();
			}else if($(this).val() == 1){
				$(".td_title").hide();
				$(".td_type").show();
			}
		}
	});
	
	$("#btnSearch").click(function(){
		doSearch();
	});

	function doSearch(){
		if($("#search-type").val() == 0){
			$('#article-tb').datagrid('load',{
				stype: 0,
				skey: $('#search-title').val()
			});
		}else{
			$('#article-tb').datagrid('load',{
				stype: 1,
				skey: $('#search-type-combox').val()
			});
		}
	}
	
	//############################	搜索结束	###############################
	
	//############################	删除开始	###############################
	
	$("#delete").click(function(){
		doDelete();
	});
	
	//删除数据
	function doDelete() {
		var selectRows =datagrid.treegrid("getSelections");
		if (selectRows.length < 1) {
			$.messager.alert("提示消息", "请选择要删除的文章!");
			return;
		}
		var ids = "";
		for(var i = 0;i <selectRows.length;i++ ){
			ids += selectRows[i].artId+",";
		}
		//提醒用户是否是真的删除数据
		$.messager.confirm("确认消息", "您确定要删除选中文章吗？", function (r) {
			if (r) {
				MaskUtil.mask();
				$.ajax({
					url: getRootPath() + "admin/article/delete",
					type: "post",
					dataType: "json",
					data:{"ids": ids},
					success: function (data) {
						MaskUtil.unmask();
						if(data.code == 200){
							datagrid.datagrid("reload");
							datagrid.datagrid("clearSelections");
						}else{
							HandleException(data);
						}
					},
					error:function(e){
						$.messager.alert("提示消息", e.message,"error");
					}
				});
			}
		});
	}
	
	//############################	删除结束	###############################
	
	//############################	查看开始	###############################
	
	$("#show").click(function(){
		showArticleDetail();
	});
	
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
	
	function showArticleDetail(){
		var selectRows =datagrid.treegrid("getSelections");
		if (selectRows.length < 1) {
			$.messager.alert("提示消息", "请选择要查看的文章!");
			return;
		}else if(selectRows.length > 1){
			$.messager.alert("提示消息", "只能选择一条的记录!");
			return;
		}else{
			$.ajax({
				url: getRootPath() + "admin/article/select/"+selectRows[0].artId,
				type: "post",
				dataType: "json",
				success: function (data) {
					console.log(data);
					if(data.code == 200){
						var article = data.data;
						$(".title").html(article.artTitle);
						$(".sec").html("发布时间："+jsonTimeStamp(article.artDate)+"&nbsp;&nbsp;&nbsp;发布者:"+article.user.usName+"&nbsp;&nbsp;&nbsp;浏览次数:"+article.artTimes);
						$(".content").html(article.artContent);
						$('#article-detail-box').dialog("open");
					}else{
						HandleException(data);
					}
				},
				error:function(e){
					$.messager.alert("提示消息", e.message,"error");
				}
			});
		}
	}
	
	//############################	查看结束	###############################
	
});