<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>重置密码</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<!--name 属性提供了名称/值对中的名称。-->
	<!--让viewport的宽度等于物理设备上的真实分辨率，不允许用户缩放。-->
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!--description网站内容描述-->
	<meta name="description" content="Mooc-all" />
	<!--"keywords" 为文档定义了一组关键字。某些搜索引擎在遇到这些关键字时，会用这些关键字对文档进行分类。-->
	<meta name="keywords" content="Mooc" />

	<link rel="stylesheet" href="<c:url value='/css/bootstrap.css'/>" />
	<link rel="stylesheet" href="<c:url value='/css/user/animate.css'/>" />
	<link rel="stylesheet" href="<c:url value='/css/user/style.css'/>" />

	<script src="<c:url value='/js/modernizr-2.6.2.min.js'/>"></script>
	<!-- jQuery -->
	<script src="<c:url value='/js/jquery-3.2.1.js'/>"></script>
	<!-- Bootstrap -->
	<script src="<c:url value='/js/bootstrap.js'/>"></script>
	<!-- Placeholder占位符 -->
	<script src="<c:url value='/js/jquery.placeholder.min.js'/>"></script>
	<!-- Waypoints 滚动插件-->
	<script src="<c:url value='/js/jquery.waypoints.min.js'/>"></script>
	<!-- Main JS -->
	<script src="<c:url value='/js/main.js'/>"></script>
	
  </head>
  
  <body>
    <div class="container">
		<div class="row">
			<!--col-md-offset-4 类将 .col-md-4 元素向右侧偏移了4个列（column）的宽度-->
			<div class="col-md-6 col-md-offset-3">
				<!-- Start  -->
				<form action="<c:url value='/UserServlet'/>" method="post" class="fh5co-form animate-box" data-animate-effect="fadeIn">
					<!-- 传递参数 -->
					<input type="hidden" name="method" value="resetPassword" />
					<input type="hidden" name="uid" value="<%=request.getParameter("uid")%>" />
					<h2>重置密码</h2>
					<!-- 提示错误信息 -->
					<div class="hint">${msg }</div>
					<div class="form-group">
						<label for="password" class="sr-only">Password</label>
						<input type="password" class="form-control" name="password" id="password" placeholder="新密码" autocomplete="off">
					</div>
					<div class="form-group">
						<label for="re-password" class="sr-only">Re-type Password</label>
						<input type="password" class="form-control" name="re-password" id="re-password" placeholder="确认密码" autocomplete="off">
					</div>
					<div class="form-group">
						<input type="submit" value="重置" class="btn btn-primary">
					</div>
				</form>
				<!-- END  -->
			</div>
		</div>
		<div class="row" style="padding-top: 60px; clear: both;">
			<div class="col-md-12 text-center"><p><small>&copy; All Rights Reserved. </small></p></div>
		</div>
	</div>
  </body>
</html>
