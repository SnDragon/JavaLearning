<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String path=request.getContextPath(); %>
<%String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册</title>
<script type="text/javascript">
	function reloadCode(){
		var time=new Date().getTime();
		document.getElementById("checkImage").setAttribute("src", "<%=request.getContextPath() %>/servlet/ImageServlet?d="+time);
	}
</script>
</head>
<body>
	<form method="post" action="<%=basePath %>register.action">
		用户名：<input type="text" name="userName"/>
		<hr/>
		邮箱：<input type="text" name="email" />
		<hr/>
		手机：<input type="text" name="telephone"/>
		<hr/>
		密码：<input type="password" name="password1"/>
		<hr/>
		确认密码：<input type="password" name="password2"/>
		<hr/>
		验证码：<input type="text" name="checkCode" /> 
		<img id="checkImage" alt="验证码" src="<%=request.getContextPath() %>/servlet/ImageServlet" />
		<a href="javascript:reloadCode();">看不清楚</a><br/>
		<input type="submit" value="注册"/>
	</form>
</body>
</html>