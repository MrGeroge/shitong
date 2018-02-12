<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="net.zypro.winter.video.bean.*"  %>
<%@page import="java.util.*" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <title>视通App后台登录</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="apple-mobile-web-app-capable" content="yes" />    
    
    <link href="./css/bootstrap.min.css" rel="stylesheet" />
    <link href="./css/bootstrap-responsive.min.css" rel="stylesheet" />
    <link href="./css/font-awesome.css" rel="stylesheet" />
    
    <link href="./css/adminia.css" rel="stylesheet" /> 
    <link href="./css/adminia-responsive.css" rel="stylesheet" /> 
    
    <link href="./css/pages/login.css" rel="stylesheet" /> 
	
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /></head>

<body>
	
<div class="navbar navbar-fixed-top">
	
	<div class="navbar-inner">
		
		<div class="container">
			
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span> 				
			</a>
			
			<a class="brand" href="#">视通App后台管理</a>
			
		</div> <!-- /container -->
		
	</div> <!-- /navbar-inner -->
	
</div> <!-- /navbar -->


<div id="login-container">
	
	
	<div id="login-header">
		
		<h3>用户设置</h3>
		
	</div> <!-- /login-header -->
	
	<div id="login-content" class="clearfix">
	<div>
	    <%=session.getAttribute("fielderror") %>
	</div>
	<form action="<%=request.getContextPath() %>/admin/update" >
				<fieldset>
					<div class="control-group">
						<label class="control-label" for="username">
						    用户名：<%String name=((Admin)session.getAttribute("admin")).getUsername();%>
							<%=name %>
							</label>
					</div>
					<div class="control-group">
						<label class="control-label" for="password">设置新用户名</label>
						<div class="controls">
							<input type="text" class="" name="newUsername" />
						</div>
					</div>
					
				</fieldset>
				
				<div class="pull-right">
					<button type="submit" class="btn btn-warning btn-large">
						确认
					</button>
				</div>
	</form>
			
		</div> <!-- /login-content -->
		
		
	
</div> <!-- /login-wrapper -->

    

<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="./js/jquery-1.7.2.min.js"></script>


<script src="./js/bootstrap.js"></script>

  </body>
</html>
