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
    
    <title>博客展示</title>
    
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
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/article/display-article.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/article/jquery.growl.css'/>" />
	
	<script src="<c:url value='/js/jquery-3.2.1.js'/>"></script>
	<script src="<c:url value='/js/bootstrap.js'/>"></script>
	<script src="<c:url value='/markdown/js/showdown.js'/>"></script>
	<script src="<c:url value='/js/article/display-article.js'/>"></script>
	<script src="<c:url value='/js/article/jquery.growl.js'/>"></script>
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
					<li class="spacing2"><span></span>博客</li>
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
		<div class="article">
			<div class="article-header">
				<h1 class="article-title">${article.atitle }</h1>
				<div class="desc-article clearfix">
					<div class="type-article">${article.type }</div>
					<div class="author">
						<span></span><a href="<c:url value='/ArticleServlet?method=findOtherArticlesByPage&curPage=1&uid=${article.author.uid }'/>">${article.author.username }</a>
					</div>
					<div class="time-article">
						<span></span>
						<p><fmt:formatDate type="both" value="${article.atime }" /></p>
					</div>
					<div class="reading">
						<span></span>
						<p>${article.readNum }</p>
					</div>
					<a class="praise clearfix" href="<c:url value='/PraiseRecordServlet?method=addPraiseRecord&aid=${article.aid }'/>">
						<span></span>
						<p>${article.praiseNum }</p>
					</a>
				</div>
			</div>
			<!-- 此处不能换行, 若换行${article.acontent }用showdown解析可能会出错 -->
			<div class="article-body" id="article-content">${article.acontent }</div>
		</div>
		<div class="bottom-body">
			
		<c:choose>
          	<c:when test="${!empty sessionScope.session_user }">
          	<!-- 用户登入时显示  -->
			<form action="<c:url value='/RemarkServlet'/>" method="post" class="remark clearfix" id="remark-form">
				
				<!-- 传递参数 -->
				<input type="hidden" name="method" value="addRemark" />
				<input type="hidden" name="article_aid" value="${article.aid }" />
				<input type="hidden" name="author_uid" value="${sessionScope.session_user.uid }" />
				
				
				
				<div class="remark-user">${sessionScope.session_user.username }</div>
				<textarea name="rcontent" class="remark-content" placeholder="发表你的评论"></textarea>
				<input type="button" id="remark-publish" onclick="checkRemarkComplete()" class="btn" value="发表评论"/>
			</form>
			</c:when>
          	<c:otherwise>
          	<!-- 未登入显示 -->
			<div class="hint-login">
				<div class="hint-txt-box">
					<span class="hint-txt">
						目前您尚未登录，请
						<a href="<c:url value='/jsp/user/login.jsp'/>">登入</a> 或
						<a href="<c:url value='/jsp/user/register.jsp'/>">注册</a>
						后进行评论和点赞!
					</span>				
				</div>
			</div>
          	</c:otherwise>
		</c:choose>
		<c:forEach items="${remarkList }" var="remark" varStatus="row">
			<div class="remarked clearfix">
				<div class="remarked-header clearfix">
					<div class="remarked-user clearfix">
						<span></span><a href="<c:url value='/ArticleServlet?method=findOtherArticlesByPage&curPage=1&uid=${remark.author.uid }'/>">${remark.author.username }</a>
					</div>
					<div class="time-remarked clearfix">
						<span></span>
						<p><fmt:formatDate type="both" value="${remark.rtime }" /></p>
					</div>
					<div class="remarked-order">${remarkList_size - row.index }楼</div>
				</div>
				<div class="remarked-content">${remark.rcontent }</div>
				<c:if test="${remark.author.username == sessionScope.session_user.username }">
				<a href="<c:url value='/RemarkServlet?method=deleteRemark&rid=${remark.rid }'/>" class="delete-remarked">删除</a>
				</c:if>
				
			</div>
			
		</c:forEach>
		</div>
	</div>
	
  </body>
</html>
