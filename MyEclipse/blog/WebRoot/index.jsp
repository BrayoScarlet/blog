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
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/bootstrap.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/base.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/index.css'/>">

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
	                <a href="<c:url value='/jsps/user/login.jsp'/>" class="sign-in">登入</a>
	                <a href="<c:url value='/jsps/user/register.jsp'/>" class="sign-up">注册</a>
	            </div>
          	  </c:when>
          	  <c:otherwise>
	            <div class="hasLogin">
	            	<a href="#">
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
				<a href="#">
					<li><span></span>学习小组</li>
				</a>
				<a href="#">
					<li><span></span>mooc检索</li>
				</a>
				<a href="#">
					<li><span></span>关于我们</li>
				</a>
			</ul>
		</div>
		<div class=""></div>
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
					<img src="images/ad/first.jpg" alt="First slide">
				</div>
				<div class="item">
					<img src="images/ad/second.jpg" alt="Second slide">
				</div>
				<div class="item">
					<img src="images/ad/third.jpg" alt="Third slide">
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
		<div class="latest-article">最新文章
		</div>
		<div class="article1">
			<div class="breif-info">
				<h2>MOOC-ALL博客正式上线!</h2>
				<div class="desc">
					欢迎来到个人技术博客技术博客，在这里可以看到网站前端和后端的技术，还有CMS内容管理系统，包括但不限于这些还有CMS内容管理系统，包括但不限于这些。
				</div>
				<div class="author">
					<span></span><a href="#">MOOC-ALL技术博客</a>
				</div>
				<div class="time">
					<span></span>
					<p>2017-12-2</p>
				</div>
			</div>
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
