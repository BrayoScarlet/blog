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
    
    <title>注册</title>
    
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
	<link rel="stylesheet" href="<c:url value='/css/user/register.css'/>" />


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
	<script src="<c:url value='/js/user/register.js'/>"></script>
  </head>
  
  <body>
    <div class="container">
		<div class="row">
			<!--col-md-offset-4 类将 .col-md-4 元素向右侧偏移了4个列（column）的宽度-->
			<div class="col-md-6 col-md-offset-3">
				<!-- Start  -->
				<form action="<c:url value='/UserServlet'/>" method="post" class="fh5co-form animate-box" data-animate-effect="fadeIn">
					<!-- 用隐藏的input传递希望在UserServlet中调用的方法 -->
					<input type="hidden" name="method" value="register" />
					<h2>账号注册</h2>
					<!-- 提示用户名错误 -->
					<div class="hint">${msg }</div>
					<div class="form-group">
						<!--for 属性规定 label 与哪个表单元素绑定。sr-only是 除了屏幕阅读器外，其他设备上隐藏该元素，-->
						<label for="name" class="sr-only">Name</label>
						<!--autocomplete 属性规定输入字段是否应该启用自动完成功能。-->
						<input type="text" name="username" value="${form.username }" class="form-control" id="name" placeholder="用户名" autocomplete="off">
						<!-- 提示用户名错误 -->
						<div class="hint">${errors.username }</div>
					</div>
					<div class="form-group">
						<label for="email" class="sr-only">Email</label>
						<input type="email" name="email" value="${form.email }" class="form-control" id="email" placeholder="邮箱" autocomplete="off">
						<!-- 提示邮箱错误错误 -->
						<div class="hint">${errors.email }</div>
					</div>
					<div class="form-group">
						<label for="password" class="sr-only">Password</label>
						<input type="password" name="password" class="form-control" id="password" placeholder="密码" autocomplete="off">
						<!-- 提示密码错误 -->
						<div class="hint">${errors.password }</div>
					</div>
					<div class="form-group">
						<label for="re-password" class="sr-only">Re-type Password</label>
						<input type="password" name="re-password" class="form-control" id="re-password" placeholder="确认密码" autocomplete="off">
						<!-- 提示确认密码错误 -->
						<div class="hint">${errors.verifyPassword }</div>
					</div>
					<div class="form-group clearfix">
						<label for="verify-code" class="sr-only">Verify Code</label>
						<button type="button" id="verifyImg" onclick="generateVerifyCode()"></button>
						<input type="hidden" name="verify-value" id="verify-value" />
						<input type="text" name="verify-code" class="form-control" id="verify-code" placeholder="验证码" autocomplete="off" onblur="checkVerifyCode()" />
						<!-- 提示验证码错误 -->
						<div class="hint" id="verify-hint">${errors.verifyCode }</div>
					</div>
					<div class="form-group">
						<p>已经注册了? <a href="<c:url value='/jsp/user/login.jsp'/>">登录</a></p>
					</div>
					<div class="form-group">
						<input type="submit" value="注册" class="btn btn-primary" />
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
