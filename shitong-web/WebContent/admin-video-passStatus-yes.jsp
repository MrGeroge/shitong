<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page import="net.zypro.winter.video.bean.*"  %>
<%@page import="java.util.*" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <meta charset="utf-8" />
    <title>视通App后台审核</title>   
    <link href="./css/bootstrap.min.css" rel="stylesheet" />
    <link href="./css/bootstrap-responsive.min.css" rel="stylesheet" />
    <link href="./css/font-awesome.css" rel="stylesheet" />    
    <link href="./css/adminia.css" rel="stylesheet" /> 
    <link href="./css/adminia-responsive.css" rel="stylesheet" />     
    <link href="./css/pages/dashboard.css" rel="stylesheet" />     
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /></head>
<body>
	
<div class="navbar navbar-fixed-top">
	
	<div class="navbar-inner">
		
		<div class="container">
			
			<span class="brand" >后台审核</span>
			
			<div class="nav-collapse">
			
				<ul class="nav pull-right">
				
					<li class="divider-vertical"></li>
					
					<li class="dropdown">
						
						<a data-toggle="dropdown" class="dropdown-toggle " href="#">
							<%String name=((Admin)session.getAttribute("admin")).getUsername();%>
							<%=name %>
							<b class="caret"></b>							
						</a>
						
						<ul class="dropdown-menu">
							<li>
								<a href="<%=request.getContextPath() %>/admin-information-update.jsp"><i class="icon-user"></i> 用户设置</a>
							</li>
							
							<li>
								<a href="<%=request.getContextPath() %>/admin-changePassword.jsp"><i class="icon-lock"></i> 修改密码</a>
							</li>
							
							<li class="divider"></li>
							
							<li>
								<a href="<%=request.getContextPath() %>/admin-login.jsp"><i class="icon-off"></i>退出登录</a>
							</li>
						</ul>
					</li>
				</ul>
				
			</div> <!-- /nav-collapse -->
			
		</div> <!-- /container -->
		
	</div> <!-- /navbar-inner -->
	
</div> <!-- /navbar -->
<div id="content">	
	<div class="container">
		<div class="row">
            <div class="span2">								
				<ul id="main-nav" class="nav nav-tabs nav-stacked">					
					<li class="active">
						<a href="#">
							<i class="icon-home"></i>
							导航	
						</a>
					</li>					
					<li>
						<a href="<%=request.getContextPath() %>/adminVideo/change-yes-no?to=passStatus-no">
							<i class="icon-remove"></i>
							未审核
						</a>
					</li>	
                         <li>
						<a href="#">
							<i class="icon-ok"></i>
							已审核
						
						</a>
						
					</li>	
                      		
				</ul>							
			</div> <!-- /span3 -->		
		    	<div class="span10">
				
				<h1 class="page-title">
					<i class="icon-ok"></i>
					已审核视频					
				</h1>
				
				  <div class="widget widget-table">
										
					<div class="widget-header">
						<i class="icon-th-list"></i>
						<h3>视频审核列表</h3>
					</div> <!-- /widget-header -->
					
					<div class="widget-content">
					
						<table class="table table-striped table-bordered">
						
								<tr>
									<th>用户名</th>
									<th>用户账号</th>
									<th>视频URL</th>
                                    <th>视频内容</th>
                                    <th>发布时间</th>
                                    <th>状态</th>									
									<th>操作</th>
								</tr>							
					<%  List<Video> videosNoStatusList=(List<Video>)session.getAttribute("videosNoStatusList");
				    for(Video video:videosNoStatusList){
					out.println("<tr>");
					out.println("<td>");
					out.println(video.getUser().getUsername());
					out.println("</td>");
					out.println("<td>");
					out.println(video.getUser().getId());
					out.println("</td>");
					out.println("<td>");
					out.println(video.getUrl());
					out.println("</td>");
					out.println("<td>");
					out.println(video.getDesc());
					out.println("</td>");
					out.println("<td>");
					out.println(video.getTime());
					out.println("</td>");
					out.println("<td>");
					out.println("审核通过");
					out.println("</td>");
					out.println("<td>");
					
					%>
									<button class="btn btn-small btn-info" data-toggle="modal" data-target="#myModal">查看视频</button>														
									  <!-- Modal -->
                                    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                    <div class="modal-content">
                                    <div class="modal-header">
									<!--弹出框开始-->
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                      <h4 id="myModalLabel" class="page-title">查看视频</h4>
                                     </div>
                                 <div class="modal-body">
                                <form role="form" class="form-horizontal tasi-form">										
								
								<video width="320" height="240" controls="controls">
                                 <source src="<%=video.getUrl() %>"  type="video/mp4" />
                                 <source src="<%=video.getUrl() %>"  type="video/ogg" />
								 </video>
																							                    							 
										<div class="control-group">											
											<label class="control-label">视频内容简介</label>
											<div class="controls">
												<input type="text" class="input-medium " name="" /></div> <!-- /controls -->				
										</div> <!-- /control-group -->
								  </form>
                             </div>
                             <div class="modal-footer">
							 <button type="button" class="btn btn-primary">保存</button>
                          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>    
                          </div>
                              </div><!-- /.modal-content -->
                              </div><!-- /.modal-dialog -->
                             </div><!--弹出框结束-->
							            	
					    <div class="btn-group">
                            
                            <a href="<%=request.getContextPath() %>/adminVideo/cancelStatus?videoId=<%=video.getId()%>" class="btn btn-small btn-success">撤销</a>
                            <a href="<%=request.getContextPath() %>/adminVideo/delete?videoId=<%=video.getId()%>&&to=passStatus-yes" class="btn btn-small btn-danger">删除</a>
                        </div>                   
				                 
				<%
				    out.println("</td>");
					out.println("</tr>");
				}
               								
				%>		
				
						</table>					
					</div> <!-- /widget-content -->							
								</div><!-- /widget -->												
			   </div> <!-- /span12 -->
			
			
		       </div> <!-- /row -->
		
            	</div> <!-- /container -->
	
               </div> <!-- /content -->
					
	



    

<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="./js/jquery-1.7.2.min.js"></script>
<script src="./js/excanvas.min.js"></script>
<script src="./js/jquery.flot.js"></script>
<script src="./js/jquery.flot.pie.js"></script>
<script src="./js/jquery.flot.orderBars.js"></script>
<script src="./js/jquery.flot.resize.js"></script>


<script src="./js/bootstrap.js"></script>
<script src="./js/charts/bar.js"></script>

  </body>
</html>
