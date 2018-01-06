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
    
    <title>博客</title>
    
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
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/article/all-article.css'/>" />
  
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
	            	<a href="<c:url value='/ArticleServlet?method=findMyArticlesByPage&curPage=1'/>">
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
				<a href="<c:url value='/ArticleServlet?method=findAllArticlesByPage&curPage=1'/>">
					<li class="spacing2 currentPage"><span></span>博客</li>
				</a>
				<a href="<c:url value='/studygroup.jsp'/>">
					<li><span></span>学习小组</li>
				</a>
				<a href="#">
					<li><span></span>mooc检索</li>
				</a>
				<a href="<c:url value='/UserServlet?method=checkLogin&url=/feedbackedit.jsp'/>">
					<li><span></span>用户反馈</li>
				</a>
				<a href="<c:url value='/UserServlet?method=quit'/>">
					<li class="spacing2"><span></span>退出</li>
				</a>
			</ul>
		</div>
	</div>
	<div class="right-body fr">
		<div class="article-head">
			<h1 class="user-blog">博客</h1>
			<p class="ubrief">过去属于死神，未来属于自己。—— 雪莱</p>
		</div>
		<div class="article-box clearfix">
			<form action="<c:url value='/ArticleServlet'/>" method="post" class="search">
				<input type="hidden" name="method" value="findArticlesByKeyWordAndPage" />
				<input type="hidden" name="curPage" value="1" />
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
						<span></span><a href="<c:url value='/ArticleServlet?method=findOtherArticlesByPage&curPage=1&uid=${article.author.uid }'/>">${article.author.username }</a>
					</div>
					<div class="time">
						<span></span><p><fmt:formatDate type="both" value="${article.atime }" /></p>
					</div>
					<div class="reading">
						<span></span><p>${article.readNum }</p>
					</div>
					<div class="remark">
						<span></span><p>${article.remarkNum }</p>
					</div>
				</div>
			</div>
		</c:forEach>
		</div>
		
		
		
		
		
		
		
		
		<div class="article-pagination">
			<ul class="pagination">
			    <li><a href="<c:url value='/ArticleServlet?method=findArticlesByKeyWordAndPage&curPage=1&key-word=${keyWord }'/>">首页</a></li>
			    <li <c:if test="${1 == pagination.curPage}">class="disabled"</c:if> ><a href="<c:url value='/ArticleServlet?method=findArticlesByKeyWordAndPage&curPage=${pagination.curPage - 1 }&key-word=${keyWord }'/>">&laquo;</a></li>
			    <c:forEach var="cur" begin="${pagination.beginPage }" end="${pagination.endPage }">
			    <li <c:if test="${cur == pagination.curPage}">class="disabled" id="cur-page"</c:if> ><a href="<c:url value='/ArticleServlet?method=findArticlesByKeyWordAndPage&curPage=${cur }&key-word=${keyWord }'/>">${cur }</a></li>
			    </c:forEach>
			    <li <c:if test="${pagination.lastPage == pagination.curPage}">class="disabled"</c:if> ><a href="<c:url value='/ArticleServlet?method=findArticlesByKeyWordAndPage&curPage=${pagination.curPage + 1 }&key-word=${keyWord }'/>">&raquo;</a></li>
			    <li><a href="<c:url value='/ArticleServlet?method=findArticlesByKeyWordAndPage&curPage=${pagination.lastPage }&key-word=${keyWord }'/>">尾页</a></li>
			</ul>
		</div>
		
		
		
		
		
		
		
		
		
		
		
	</div>
  </body>
</html>
