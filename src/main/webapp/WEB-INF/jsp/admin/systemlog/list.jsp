<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.contextPath }/">
<meta charset="UTF-8" />
<title>数字办公系统</title>
<jsp:include page="../include_header.jsp"></jsp:include>

</head>
<body class="easyui-layout" fit="true" border="false">
	<div data-options="region:'north'" style="height: 100px;">
		<fieldset style="border-radius: 10px; border: 1px solid #C3C3C3;">
			<legend style="font-size: 14px;">信息检索</legend>
			<form id="article-search-form" method="post">
				<table cellspacing="0" cellpadding="0">
					<tr>
						<td>搜索类型：</td>
						<td><select class="easyui-combobox" name="artTop"
							id="search-type" editable="false" style="width: 88px;">
								<option value="0">描述</option>
								<option value="1">请求方法</option>
								<option value="2">请求时间</option>
						</select></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td class="td_description">描述：<input class="easyui-textbox"
							type="text" id="search-description" style="width: 200px" /></td>
						<td class="td_method" style="display:none;">方法名称：<input class="easyui-textbox"
							type="text" id="search-method" style="width: 200px" /></td>
						<td class="td_datetime" style="display: none;">
						开始时间：<input type="text" class="easyui-datetimebox" id="search-date-start" style="width: 170px"
							required="required"> 结束时间：<input type="text" id="search-date-end" style="width: 170px"
							class="easyui-datetimebox" required="required">
						</td>
						<td colspan="2" style="padding-left: 20px;"><a
							href="javascript:;" class="easyui-linkbutton"
							data-options="iconCls:'icon-search'" id="btnSearch">查询</a></td>
					</tr>
				</table>
			</form>
		</fieldset>
	</div>

	<div data-options="region:'center',split:false">
		<table id="systemlog-tb"></table>
	</div>

	<div class="box" id="systemlog-detail-box" style="display: none;">
			<table class="rb-add-user" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="td_key">操作描述：</td>
					<td class="td_val lg_description"></td>
				</tr>
				<tr>
					<td class="td_key">操作请求方法：</td>
					<td class="td_val lg_method"></td>
				</tr>

				<tr>
					<td class="td_key">请求参数：</td>
					<td class="td_val lg_params"></td>
				</tr>
				<tr>
					<td class="td_key">操作者：</td>
					<td class="td_val lg_create_user"></td>
				</tr>
				<tr>
					<td class="td_key">操作IP地址：</td>
					<td class="td_valv lg_create_ip"></td>
				</tr>
				<tr>
					<td class="td_key">操作时间：</td>
					<td class="td_val lg_create_date"></td>
				</tr>
			</table>
	</div>

	<script type="text/javascript" src="static/js/systemlog-manage.js"></script>
</body>
</html>