<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>推荐视频页面</title>
</head>
<body>
<s:form action="/video/recommend/list">
<s:submit value="推荐"/>
<s:textfield name="beginIndex" label="搜索的起点"/>
<s:textfield name="count" label="搜索的数量"/>
</s:form>
</body>
</html>