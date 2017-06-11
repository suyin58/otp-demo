<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>登录界面</h1>
<form action="LoginServlet">
<span>请输入用户名</span><input type='text' name='username'></input>
<br/>
<span>请输入校验码</span><input type='text' name='otp'></input>
<br/>
<input type="submit" content="提交">
</form>

</body>
</html>