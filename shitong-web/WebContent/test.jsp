<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ page import="org.json.JSONObject" %>
<%
response.setContentType("application/json;charset=UTF-8");
JSONObject json=new JSONObject();
json.put("status", "success");
json.put("message","用户注册成功");
response.getWriter().println(json.toString());
%>
