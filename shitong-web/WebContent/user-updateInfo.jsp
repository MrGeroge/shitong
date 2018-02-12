<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑用户基本资料的界面</title>
</head>
<body>
<s:form action="/user/updateInfo">
<s:textfield name="userInfo.nick_name" label="用户呢称"/>
<s:textfield name="userInfo.sex" label="用户性别"/>
<s:textfield name="userInfo.age" label="用户年龄"/>
<s:textfield name="userInfo.qq" label="用户qq号"/>
<s:textfield name="userInfo.address" label="用户所在地"/>
<s:textfield name="userInfo.desc" label="用户简介"/>
<s:textfield name="userInfo.avatar_url" label="用户头像"/>
<s:submit value="编辑"/>
<s:reset value="重置"/>
</s:form>
</body>
</html>
