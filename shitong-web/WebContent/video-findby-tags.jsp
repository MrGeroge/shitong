<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>标签搜索界面</title>
</head>
<body>
<s:form action="/video/tag/find">
<s:textfield name="video1.tag" label="标签搜索相关的标签"/>
<s:textfield name="beginIndex" label="搜索的起点"/>
<s:textfield name="count" label="搜索的数量"/>
<s:submit value="确认"/>
<s:reset value="重置"/>
</s:form>
</body>
</html>