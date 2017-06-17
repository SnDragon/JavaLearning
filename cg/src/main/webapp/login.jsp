<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String path=request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>登录</h1>
		用户名:<input type="text" name="username" id="username" /><br/>
		密码:<input type="password" name="password" id="password" /> <br/>
		<button id="login">登录</button>
</body>
<input type="hidden" id="path" value="<%=path %>" />
<script type="text/javascript" src="<%=path %>/static/js/lib/jquery.min.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		$("#login").click(function(){
			var obj={"username":$("#username").val(),"password":$("#password").val()};
			$.ajax({
				url:$("#path").val()+"/login",
				type:"POST",
				contentType:"application/json;charset=UTF-8",
				data:JSON.stringify(obj),
				dataType:"text",
				success:function(data){
					alert(data);
				},
				error:function(XMLResponse){
					alert(XMLResponse.responseText);
				}
			});
		});
	});
</script>
</html>