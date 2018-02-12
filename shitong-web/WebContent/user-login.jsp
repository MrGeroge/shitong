<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登陆界面</title>
</head>
<body>
<s:fielderror/>
注册用户请点击<a href="user-register.jsp">这里</a>
<s:form action="/user/login" validate="true" >
<s:textfield name="user.username" label="用户名"/>
<s:password name="user.password" label="密码"/>
<s:submit value="登陆"/>
<s:reset value="重置"/>
</s:form>
</body>
</html>