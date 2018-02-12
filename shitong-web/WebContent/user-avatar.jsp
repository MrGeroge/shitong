<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户头像上传</title>
</head>
<body>
<s:form action="upload/image" enctype="multipart/form-data">
<s:file name="image" label="选择上传头像"></s:file>
<s:submit value="上传"/>
</s:form>

</body>
</html>