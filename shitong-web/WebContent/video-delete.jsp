<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>视频删除界面</title>
</head>
<body>
<s:form action="/video/delete">
<s:textfield name="video1.id" label="删除视频id"/>
<s:submit value="删除"/>
</s:form>
</body>
</html>