<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户修改密码界面</title>
</head>
<body>
<s:form action="/user/changePass">
<s:password name="password" label="新密码"/>
<s:submit value="修改"/>
<s:reset value="重置"/>
</s:form>

</body>
</html>