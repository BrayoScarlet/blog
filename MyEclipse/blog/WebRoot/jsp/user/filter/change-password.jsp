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
    
    <title>修改密码</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/bootstrap.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/base.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/navigation.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/user/style.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/user/change-password.css'/>" />

	<script src="<c:url value='/js/jquery-3.2.1.js'/>"></script>
	<script src="<c:url value='/js/bootstrap.js'/>"></script>
  </head>
  
  <body>
    <div class="left-body fl">
		<div class="logo"></div>
		<div class="user-info">
		<c:choose>
          	  <c:when test="${empty sessionScope.session_user }">
	            <div class="notLogin">
	                <a href="<c:url value='/jsp/user/login.jsp'/>" class="sign-in">登入/注册</a>
	            </div>
          	  </c:when>
          	  <c:otherwise>
	            <div class="hasLogin">
	            	<a href="<c:url value='/jsp/user/filter/personal-profile.jsp'/>">
		    			<img src="images/head_portrait.jpg" />
		    			<div class="username fl">${sessionScope.session_user.username }</div>
	            	</a>
	            </div>
          	  </c:otherwise>
           </c:choose>
		</div>
		<div class="navigation">
			<ul>
				<a href="index.html">
					<li><span></span>网站首页</li>
				</a>
				<a href="htmls/article/writer.html">
					<li><span></span>发表博文</li>
				</a>
				<a href="htmls/article/show.html">
					<li><span></span>我的博文</li>
				</a>
				<a href="<c:url value='/jsp/user/filter/personal-profile.jsp'/>">
					<li><span></span>个人资料</li>
				</a>
				<a href="<c:url value='/jsp/user/filter/change-password.jsp'/>">
					<li><span></span>修改密码</li>
				</a>
				<a href="<c:url value='/UserServlet?method=quit'/>">
					<li><span></span>退出</li>
				</a>
			</ul>
		</div>
		<div class=""></div>
	</div>
	<div class="right-body fr">
		<h2>修改密码</h2>
		<hr/>
		<form action="<c:url value='/UserServlet'/>" method="post">
			<input type="hidden" name="method" value="changePassword" />
			<div class="change-password" id="change">
				<div class="change-group clearfix">
					<p class="key">原密码</p>
					<input type="password" class="value" name="oldPassword" />
					<!-- 提示原密码错误 -->
					<div class="hint">${errors.oldPassword }</div>
				</div>
				<div class="change-group clearfix">
					<p class="key">新密码</p>
					<input type="password" class="value" name="newPassword" />
					<!-- 提示新密码错误 -->
					<div class="hint">${errors.newPassword }</div>
				</div>
				<div class="change-group clearfix">
					<p class="key">重输新密码</p>
					<input type="password" class="value" name="verifyNewPassword" />
					<!-- 提示重输新密码错误 -->
					<div class="hint">${errors.verifyNewPassword }</div>
				</div>
				<input type="submit" class="btn btn-primary save" value="保存" />
			</div>
		</form>
	</div>
  </body>
</html>
