<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑video</title>
</head>
<body>
<s:form action="/video/add">
<s:textfield name="video1.url" label="URL"></s:textfield>
<s:textfield name="video1.cover_url" label="封面URL"></s:textfield>
<s:textfield name="video1.tag" label="标签一"/>

<s:textfield name="video1.desc" label="描述"/>
<s:submit value="提交"/>
</s:form>
</body>
</html>