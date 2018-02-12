<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收藏视频页面</title>
</head>
<body>
<s:form action="/collect/edit">
<s:textfield name="video.id" label="收藏记录的视频id"/>
<s:submit value="收藏"></s:submit>
<s:reset value="重置"></s:reset>
</s:form>

</body>
</html>