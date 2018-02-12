<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="net.zypro.winter.video.bean.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%User u=new User(1,"dog","dog");%>
<%session.setAttribute("user", u); %>
  <s:form action="/friend/delete">
   <s:token/>
   <s:textfield name="fri.username" label="ºÃÓÑÐÕÃû"/>
   <s:submit value="È¡Ïû¹Ø×¢"/>
   </s:form> 
</body>
</html>