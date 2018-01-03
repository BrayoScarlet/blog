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
    
    <title>mool-all博客</title>
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
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/index.css'/>" />

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
					<li class="currentPage"><span></span>网站首页</li>
				</a>
				<a href="<c:url value='/ArticleServlet?method=findAllArticles'/>">
					<li class="spacing2"><span></span>博客</li>
				</a>
				<a href="#">
					<li><span></span>学习小组</li>
				</a>
				<a href="#">
					<li><span></span>mooc检索</li>
				</a>
				<a href="#">
					<li><span></span>关于我们</li>
				</a>
				<a href="<c:url value='/UserServlet?method=quit'/>">
					<li class="spacing2"><span></span>退出</li>
				</a>
			</ul>
		</div>
	</div>
	<div class="middle-body fl clearfix">
		<div id="myCarousel" class="carousel slide advertisement">
			<!-- 轮播（Carousel）指标 -->
			<ol class="carousel-indicators">
				<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				<li data-target="#myCarousel" data-slide-to="1"></li>
				<li data-target="#myCarousel" data-slide-to="2"></li>
			</ol>
			<!-- 轮播（Carousel）项目 -->
			<div class="carousel-inner">
				<div class="item active">
					<img src="<c:url value='/images/ad/first.jpg'/>" alt="First slide">
				</div>
				<div class="item">
					<img src="<c:url value='/images/ad/second.jpg'/>" alt="Second slide">
				</div>
				<div class="item">
					<img src="<c:url value='/images/ad/third.jpg'/>" alt="Third slide">
				</div>
			</div>
			<!-- 轮播（Carousel）导航 -->
			<a class="carousel-control left" href="#myCarousel"
			   data-slide="prev">&lsaquo;
			</a>
			<a class="carousel-control right" href="#myCarousel"
			   data-slide="next">&rsaquo;
			</a>
		</div>
		<div class="latest-article">最新文章</div>
		<div class="article clearfix">
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
				</div>
			</div>
		</c:forEach>
		</div>
	</div>
	<div class="right-body fr">
		<div class="daily">
			<div class="name">每日一句</div>
			<h2>2017年11月11日 星期六</h2>
			<p>你是我人生中唯一的主角，我却只能是你故事中的一晃而过得路人甲。</p>
		</div>
		<div class="hot-group">
			热门学习小组
		</div>
		<ul>
			<a href="#">
				<li>
					<div class="msg">
                        <span>
                            个人技术博客技术博客的SHORTCUT和ICON图标
                        </span>
						<span>
                            阅读(2165)
                        </span>
					</div>
					<div class="icon">
						<img src="images/icon.png" />
					</div>
				</li>
			</a>
			<a href="#">
				<li>
					<div class="msg">
                        <span>
                            个人技术博客技术博客的SHORTCUT和ICON图标
                        </span>
						<span>
                            阅读(2165)
                        </span>
					</div>
					<div class="icon">
						<img src="images/icon.png" />
					</div>
				</li>
			</a>
			<a href="#">
				<li>
					<div class="msg">
                        <span>
                            个人技术博客技术博客的SHORTCUT和ICON图标
                        </span>
						<span>
                            阅读(2165)
                        </span>
					</div>
					<div class="icon">
						<img src="images/icon.png" />
					</div>
				</li>
			</a>
		</ul>
	</div>
  </body>
</html>
