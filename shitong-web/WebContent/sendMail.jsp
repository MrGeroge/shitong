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
String username=(String)request.getAttribute("username");     //�����ַ
String random=(String)session.getAttribute("random");
content.append("<a href='http://localhost:8080/video.demo/user/activated;jsessionid="+session.getId()+"?username="+username+"&random="+random+"'>����</a>");



Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider()); 

Properties props = new Properties();
props.put("mail.smtp.host", "smtp.qq.com"); //smtp
props.put("mail.smtp.auth", "true");
props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
props.setProperty("mail.smtp.socketFactory.fallback", "false"); 
props.setProperty("mail.smtp.port", "465"); 
props.setProperty("mail.smtp.socketFactory.port", "465"); 
//�������ʼ��Ự
Session mailSession = Session.getInstance(props);




//������Ϣ��
MimeMessage message = new MimeMessage(mailSession); 
//������ַ
Address address = new InternetAddress("whutosa@qq.com");
message.setFrom(address);
//�ռ���ַ
Address toAddress = new InternetAddress(username);
message.setRecipient(MimeMessage.RecipientType.TO, toAddress);
//����
sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();  
message.setSubject("=?GBK?B?"+enc.encode(new String("ע���û�������").getBytes("GBK"))+"?=");
//message.setSubject("");

message.setContent(new String(content.toString().getBytes("GBK"), "GBK"), "text/html;charset=GBK");
//����
//message.setText(content.toString());
        
message.saveChanges(); // implicit with send()
//Exception in thread "main" javax.mail.NoSuchProviderException: smtp
mailSession.setDebug(true);
Transport transport = mailSession.getTransport("smtp");
transport.connect("smtp.qq.com", "whutosa@qq.com","whutosapwd+");
//����
transport.sendMessage(message, message.getAllRecipients());
transport.close();
response.setContentType("application/json;charset=UTF-8");
JSONObject json=new JSONObject();
json.put("status", "success");
json.put("message","�û�ע��ɹ�");
response.getWriter().println(json.toString());

out.println("<script>alert('���ͳɹ���');window.location='../proxy.jsp#form';</script>");

%>