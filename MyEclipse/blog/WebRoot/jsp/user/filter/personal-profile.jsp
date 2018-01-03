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
    
    <title>个人资料</title>
    
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
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/user/personal-profile.css'/>" />

	<script src="<c:url value='/js/jquery-3.2.1.js'/>"></script>
	<script src="<c:url value='/js/bootstrap.js'/>"></script>
	<script src="<c:url value='/js/user/personal-profile.js'/>"></script>
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
	            	<!-- 点击用户名进入个人博客页面 -->
	            	<a href="<c:url value='/ArticleServlet?method=findArticlesByMyUid'/>">
		    			<img src="images/head_portrait.jpg" />
		    			<div class="username fl">${sessionScope.session_user.username }</div>
	            	</a>
	            </div>
          	  </c:otherwise>
           </c:choose>
		</div>
		<div class="navigation">
			<ul>
				<a href="<c:url value='/index.jsp'/>">
					<li><span></span>网站首页</li>
				</a>
				<a href="<c:url value='/jsp/article/filter/write-article.jsp'/>">
					<li><span></span>发表博客</li>
				</a>
				<a href="<c:url value='/ArticleServlet?method=findArticlesByMyUid'/>">
					<li><span></span>我的博客</li>
				</a>
				<a href="<c:url value='/jsp/user/filter/personal-profile.jsp'/>">
					<li class="currentPage"><span></span>个人资料</li>
				</a>
				<a href="<c:url value='/jsp/user/filter/change-password.jsp'/>">
					<li class="spacing2"><span></span>设置</li>
				</a>
				<a href="<c:url value='/UserServlet?method=quit'/>">
					<li class="spacing2"><span></span>退出</li>
				</a>
			</ul>
		</div>
	</div>
	<div class="right-body fr">
		<h2>个人资料</h2>
		<hr/>

		<!-- 展示部分 -->

		<div class="person-info" id="display">
			<div class="btnfather">
				<button class="btn btn-primary fr" onclick="editPersonInfo()">编辑</button>
			</div>
			<div class="info-group clearfix">
				<p class="key">昵称</p>
				<p class="value">${session_user.username }</p>
			</div>
			<div class="info-group clearfix">
				<p class="key">性别</p>
				<p class="value">${session_user.sex }</p>
			</div>
			<div class="info-group clearfix">
				<p class="key">简介</p>
				<p class="value">${session_user.ubrief }</p>
			</div>
			<div class="info-group clearfix">
				<p class="key">居住地</p>
				<p class="value">${session_user.domicile }</p>
			</div>
			<div class="info-group clearfix">
				<p class="key">毕业年份</p>
				<p class="value">${session_user.graduationYear }</p>
			</div>
			<div class="info-group clearfix">
				<p class="key">学历</p>
				<p class="value">${session_user.qualification }</p>
			</div>
			<div class="info-group clearfix">
				<p class="key">学校</p>
				<p class="value">${session_user.school }</p>
			</div>
			<div class="info-group clearfix">
				<p class="key">感兴趣的工作</p>
				<p class="value">${session_user.specialisations }</p>
			</div>
		</div>

		<!-- 修改部分, 原先是隐藏的 -->
		<form action="<c:url value='/UserServlet'/>" id="editForm" method="post">
			<input type="hidden" name="method" value="edit" />
			<div class="person-info" id="edit" hidden="hidden">
				<div class="btnfather">
					<button class="btn btn-primary fr save" onclick="savePersonInfo()">保存</button>
					<button class="btn fr cancel" onclick="cancelEdit()">取消</button>
				</div>
				<div class="info-group clearfix">
					<p class="key">性别</p>
					<select class="value sex" name="sex">
						<option>男</option>
						<option>女</option>
					</select>
				</div>
				<div class="info-group clearfix">
					<p class="key">简介</p>
					<input type="text" class="value" name="ubrief" />
				</div>
				<div class="info-group clearfix">
					<p class="key">居住地</p>
					<input type="text" class="value" name="domicile" />
				</div>
				<div class="info-group clearfix">
					<p class="key">毕业年份</p>
					<select class="value year" name="graduationYear">
						<option>2025</option>
						<option>2024</option>
						<option>2023</option>
						<option>2022</option>
						<option>2021</option>
						<option>2020</option>
						<option>2019</option>
						<option>2018</option>
						<option>2017</option>
						<option>2016</option>
						<option>2015</option>
						<option>2014</option>
						<option>2013</option>
						<option>2012</option>
						<option>2011</option>
						<option>2010</option>
					</select>
				</div>
				<div class="info-group clearfix">
					<p class="key">学历</p>
					<select class="value" name="qualification">
						<option>博士后</option>
						<option>博士</option>
						<option>硕士</option>
						<option>本科</option>
						<option>专科</option>
						<option>高中</option>
						<option>初中</option>
						<option>小学</option>
					</select>
				</div>
				<div class="info-group clearfix">
					<p class="key">学校</p>
					<input type="text" class="value" name="school" />
				</div>
				<div class="info-group clearfix">
					<p class="key">感兴趣的工作</p>
					<input type="text" class="value" name="specialisations"/>
				</div>
			</div>
		</form>
	</div>
  </body>
</html>
