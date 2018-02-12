<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@page import="net.zypro.winter.video.bean.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%User user=new User(1,"dog","dog"); %>
<%session.setAttribute("user", user); %>
<%response.sendRedirect("friend/getFollowers.action"); %>
</body>
</html>