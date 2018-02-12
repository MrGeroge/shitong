<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户激活页面</title>
</head>
<body>
<form action="user/activated" method="post">
用户名：<input type="text" name="username"/><br/>
随机数：<input type="text" name="random"/><br/>
<input type="submit" value="激活"/>
</form>
</body>
</html>