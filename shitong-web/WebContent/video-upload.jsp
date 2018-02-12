<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>视频上传界面</title>
</head>
<body>
<s:form action="upload/video" enctype="multipart/form-data">
<s:file name="video" label="请选择上传视频"/>
<s:submit value="上传"/>
</s:form>
</body>
</html>