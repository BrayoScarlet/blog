<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>我的博客</title>
    
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
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/article/my-article.css'/>" />
    
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
					<li class="currentPage"><span></span>我的博客</li>
				</a>
				<a href="<c:url value='/jsp/user/filter/personal-profile.jsp'/>">
					<li><span></span>个人资料</li>
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
		<div class="article-head">
			<h1 class="user-blog">${sessionScope.session_user.username }的博客</h1>
			<p class="ubrief">${sessionScope.session_user.ubrief }</p>
		</div>
		<div class="article-box clearfix">
			<form action="<c:url value='/ArticleServlet'/>" method="post" class="search">
				<input type="hidden" name="method" value="findMyArticlesByKeyWords" />
				<input type="text" class="key-word" autofocus name="key-word" value="${keyWord }" placeholder="请输入关键词" /><input type="submit" class="search-btn" value="搜索" />
			</form>
		<c:forEach items="${articleList }" var="article">
			<div class="breif-info">
				<a class="article-title" href="<c:url value='/ArticleServlet?method=findArticleByAid&aid=${article.aid }'/>">${article.atitle }</a>
				<div class="desc">
					${article.abstractContent }
				</div>
				<div class="bottom-father clearfix">
					<div class="type-article">${article.type }</div>
					<div class="author">
						<span></span><a href="<c:url value='/ArticleServlet?method=findArticlesByOtherUid&uid=${article.author.uid }'/>">${article.author.username }</a>
					</div>
					<div class="time">
						<span></span>
						<p><fmt:formatDate type="both" value="${article.atime }" /></p>
					</div>
					<div class="reading">
						<span></span><p>${article.readNum }</p>
					</div>
					<div class="remark">
						<span></span><p>${article.remarkNum }</p>
					</div>
					<a href="<c:url value='/ArticleServlet?method=deleteArticleByAid&aid=${article.aid }'/>" class="delete-article fr">删除</a>
					<a href="<c:url value='/ArticleServlet?method=editArticleByAid&aid=${article.aid }'/>" class="edit-article fr">编辑</a>
				</div>
			</div>
		</c:forEach>
		</div>
	</div>
  </body>
</html>
