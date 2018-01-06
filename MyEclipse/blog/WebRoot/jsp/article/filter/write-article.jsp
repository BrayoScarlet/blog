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
    
    <title>发表博客</title>
    
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
	<link rel="stylesheet" type="text/css" href="<c:url value='/markdown/css/bootstrap-markdown.min.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/markdown/font-awesome/css/font-awesome.min.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/base.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/navigation.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/article/jquery.growl.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/article/write-article.css'/>" />
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
				<a href="<c:url value='/jsp/article/filter/write-article.jsp'/>">
					<li class="currentPage"><span></span>发表博客</li>
				</a>
				<a href="<c:url value='/ArticleServlet?method=findMyArticlesByPage&curPage=1'/>">
					<li><span></span>我的博客</li>
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
		<h1 class="right-body-title">发表博客</h1>
		<form id="articleForm" action="<c:url value='/ArticleServlet'/>" method="post">
			<input type="hidden" name="method" value="addArticle" />
			<!-- 用一个隐含字段来保存aid, 用于辨别用户是在发表文章还是修改文章 -->
			<input type="hidden" name="aid" value="${article.aid }" />
			<div class="article-title">
				<div class="desc-title">文章标题</div>
				<div class="title-father">
					<select class="article-type" name="type" id="type-id">
						<option>请选择</option>
						<option>原创</option>
						<option>转载</option>
						<option>翻译</option>
					</select>
					<input type="text" name="atitle" value="${article.atitle }" class="content-title" autofocus placeholder="输入文章标题" />
				</div>
			</div>
			<div class="article-content">
				<div class="desc-content">文章内容</div>
				<textarea data-provide="markdown" name="acontent" data-iconlibrary="fa" rows="10" id="editor">${article.acontent }</textarea>
			</div>
			<div class="article-classify">
				<div class="desc-classify">文章分类</div>
				<select class="content-classify" name="classify" id="classify-id">
					<option>选择分类</option>
					<option>Java高级开发</option>
					<option>数据库</option>
					<option>软件工程</option>
					<option>算法与数据结构</option>
					<option>C语言</option>
					<option>C++</option>
				</select>
			</div>
			<div class="article-abstract">
				<div class="desc-abstract">摘要:(默认自动提取您文章的前两百字显示在博客首页作为文章摘要,您也可以在这里自行编辑)</div>
				<textarea class="content-abstract" name="abstractContent" >${article.abstractContent }</textarea>
			</div>
			<p class="hint-p">提示：请不要发布任何推广、广告（包括招聘）、政治、低俗等方面的内容，不要把博客当作SEO工具，否则可能会影响到您的使用。</p>
			<button type="button" class="btn btn-primary" id="btn-publish" onclick="checkArticleComplete()" >发表文章</button>
		</form>
	</div>
	
	<script src="<c:url value='/js/jquery-3.2.1.js'/>"></script>
	<script src="<c:url value='/js/bootstrap.js'/>"></script>
	<script src="<c:url value='/markdown/js/markdown.js'/>"></script>
	<script src="<c:url value='/markdown/js/to-markdown.js'/>"></script>
	<script src="<c:url value='/markdown/js/bootstrap-markdown.js'/>"></script>
	<script src="<c:url value='/markdown/js/bootstrap-markdown.fr.js'/>"></script>
	<script src="<c:url value='/markdown/js/hint-style.js'/>"></script>
	<script src="<c:url value='/markdown/js/showdown.js'/>"></script>
	<script src="<c:url value='/js/article/jquery.growl.js'/>"></script>
	<script src="<c:url value='/js/article/write-article.js'/>"></script>
	<script type="text/javascript">
		//用户编辑博客时, 要显示原来的博客信息
		var articleType = "${article.type}" == "" ? "请选择" : "${article.type}";
		var articleClassify = "${article.classify}" == "" ? "选择分类" : "${article.classify}";
		document.getElementById("type-id").value = articleType;
		document.getElementById("classify-id").value = articleClassify;
	</script>
  </body>
</html>
