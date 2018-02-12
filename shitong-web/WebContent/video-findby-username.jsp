<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通过用户名来查找视频</title>
</head>
<body>
<s:form action="/video/user/list">
<s:textfield name="username" label="请输入用户名"/>
<s:textfield name="beginIndex" label="搜索的起点"/>
<s:textfield name="count" label="搜索的数量"/>
<s:submit value="查找"/>
<s:reset value="重置"/>
</s:form>
</body>
</html>