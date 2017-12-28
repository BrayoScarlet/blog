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
    
    <title>登入</title>
    
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
			<div class="col-md-4 col-md-offset-4">
				<!-- Start -->
				<form action="<c:url value='/UserServlet'/>" method="post" class="fh5co-form animate-box" data-animate-effect="fadeIn">
					<!-- 用隐藏的input传递希望在UserServlet中调用的方法 -->
					<input type="hidden" name="method" value="login" />
					<h2>账号登录</h2>
					<!-- 提示错误信息 -->
					<div class="hint">${msg }</div>
					<div class="form-group">
						<label for="username" class="sr-only">Username</label>
						<!--autocomplete 属性规定输入字段是否应该启用自动完成功能。-->
						<input type="text" name="username" class="form-control" id="username" placeholder="输入用户名" autocomplete="off">
					</div>
					<div class="form-group">
						<label for="password" class="sr-only">Password</label>
						<input type="password" name="password" class="form-control" id="password" placeholder="输入密码" autocomplete="off">
					</div>
					<div class="form-group">
						<p>没有注册? <a href="<c:url value='/jsp/user/register.jsp'/>">注册</a> | <a href="<c:url value='/jsp/user/forgot.jsp'/>">忘记密码?</a></p>
					</div>
					<div class="form-group">
						<input type="submit" value="登录" class="btn btn-primary">
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
