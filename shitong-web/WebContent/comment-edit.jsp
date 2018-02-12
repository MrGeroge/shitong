<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>视频评论页面</title>
</head>
<body>
<s:form action="/comment/edit">
<s:textfield name="video.id" label="评论的视频id"/>
<s:textarea name="comment.content" label="视频评论"/>
<s:submit value="提交"/>
<s:reset value="重置"/>
</s:form>
</body>
</html>