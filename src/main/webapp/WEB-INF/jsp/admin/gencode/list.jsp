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
<body>
	<form id="gencode-form" method="post">
			<table class="rb-add-user" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="td_key" style="text-align:center;">表名：</td>
					<td class="td_val" style="text-align:center;"><input class="easyui-textbox" type="text"
						name="tablename" data-options="required:true" missingMessage="请填写表名" /></td>
				</tr>
				<tr>
					<td class="td_key" style="text-align:center;">类名：</td>
					<td class="td_val" style="text-align:center;"><input class="easyui-textbox"
						style="width: 204px;" name="classname" /></td>
				</tr>
				<tr>
					<td class="td_key" style="text-align:center;" colspan="2">
						<a href="javascript:void(0);" id="gencode" class="easyui-linkbutton" style="width:120px">确认生成</a>
					</td>
				</tr>
			</table>
		</form>
	<script type="text/javascript">
		$("#gencode").click(function(){
			$('#gencode-form').form('submit', {
				url:'GenCode',
				onSubmit: function(){
					return $(this).form('enableValidation').form('validate');
				},
				success:function(data){
					$.messager.alert("生成提示", data.message);
				}
			});
		});
	</script>
</body>
</html>