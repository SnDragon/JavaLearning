<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%String basePath=request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>用户登录</title>	   
     
  </head>
  
  <body>
  
    <form action="<%=basePath %>/user/login" method="post" id="myform">
    	<table align="center">        
    		<tr>
    			<td><p>用户账户：<input type="text" id="name" name="name" style="width: 150px" /></p></td>
    		</tr>
    		<tr>
    			<td><p>用户密码：<input type="password" id="password" name="password" style="width: 150px"/></p></td>
    		</tr>
    		
    	
    		<tr>
    			<td><input type="submit" value="提交" id="login"/></td>
    		</tr>
    	</table> 	
    </form>
  </body>
</html>
