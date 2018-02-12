<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ page import="java.util.Properties" %>
<%@ page import="javax.mail.*" %>
<%@ page import="javax.mail.internet.*" %>
<%@ page import="java.security.Security" %>
<%@ page import="org.json.JSONObject" %>
<%
request.setCharacterEncoding("GBK");
StringBuilder content=new StringBuilder();
String username=(String)request.getAttribute("username");     //邮箱地址
String random=(String)session.getAttribute("random");
content.append("<a href='http://localhost:8080/video.demo/user/activated;jsessionid="+session.getId()+"?username="+username+"&random="+random+"'>激活</a>");



Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider()); 

Properties props = new Properties();
props.put("mail.smtp.host", "smtp.qq.com"); //smtp
props.put("mail.smtp.auth", "true");
props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
props.setProperty("mail.smtp.socketFactory.fallback", "false"); 
props.setProperty("mail.smtp.port", "465"); 
props.setProperty("mail.smtp.socketFactory.port", "465"); 
//基本的邮件会话
Session mailSession = Session.getInstance(props);




//构造信息体
MimeMessage message = new MimeMessage(mailSession); 
//发件地址
Address address = new InternetAddress("whutosa@qq.com");
message.setFrom(address);
//收件地址
Address toAddress = new InternetAddress(username);
message.setRecipient(MimeMessage.RecipientType.TO, toAddress);
//主题
sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();  
message.setSubject("=?GBK?B?"+enc.encode(new String("注册用户名激活").getBytes("GBK"))+"?=");
//message.setSubject("");

message.setContent(new String(content.toString().getBytes("GBK"), "GBK"), "text/html;charset=GBK");
//正文
//message.setText(content.toString());
        
message.saveChanges(); // implicit with send()
//Exception in thread "main" javax.mail.NoSuchProviderException: smtp
mailSession.setDebug(true);
Transport transport = mailSession.getTransport("smtp");
transport.connect("smtp.qq.com", "whutosa@qq.com","whutosapwd+");
//发送
transport.sendMessage(message, message.getAllRecipients());
transport.close();
response.setContentType("application/json;charset=UTF-8");
JSONObject json=new JSONObject();
json.put("status", "success");
json.put("message","用户注册成功");
response.getWriter().println(json.toString());

out.println("<script>alert('发送成功！');window.location='../proxy.jsp#form';</script>");

%>