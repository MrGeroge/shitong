<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通过视频关键字查找</title>
</head>
<body>
<s:form action="/video/find">
<s:textfield name="keyword" label="请输入搜索关键字"/>
<s:textfield name="beginIndex" label="搜索的起点"/>
<s:textfield name="count" label="搜索的数量"/>
<s:submit value="搜索"/>
<s:reset value="重置"/>
</s:form>
</body>
</html>