<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="yang.util.*" %>
<%@ page import="blog.article.domain.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<meta charset="utf-8">
    <title>管理待审核博客</title>  
    <script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>

    <link href="bootstrap3.3.4/css/bootstrap.min.css" rel="stylesheet">   
    <link href="css/base.css" rel="stylesheet">
    <link href="css/navigation.css" rel="stylesheet">
    <link href="css/manageBlog.css" rel="stylesheet">

    <script src="bootstrap3.3.4/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="layer/layer.js" type="text/javascript"></script>
	
<script type="text/javascript">
	/*
		功能:显示所有的待审核的博客
	*/
	function showAllBlogs(){
			
		$.ajax({
			dataType : 'json',
			url : 'blogsList.jsp',
			type : 'post',
			success : function(result){
				//存放博客的盒子
				var blogsbox = document.getElementById("blogsbox");
				for(i in result ){
					var blog = createBlogNode( result[i] );
					blogsbox.appendChild(blog);
				}
	        }
		});
	}
	
	/*
	参数：dataObj 根据json解析出来的单个blog对象
	功能：生成每个显示blog的结点
	*/
	function createBlogNode(dataObj){
		//blog结点的div
		var blogDiv = document.createElement('div');
		blogDiv.setAttribute('class' ,'breif-info');
		
		var wrapHtml;
		//博客标题是超链接，点击转到blogDisplay.html并传入博客id
		wrapHtml = "<a class='article-title' href='blogDisplay.jsp?aid="+dataObj.aid+"'>"+dataObj.atitle+" </a>";
		wrapHtml += "<div class='desc'>"+dataObj.acontent+"</div>";
		wrapHtml += "<div class='bottom-father clearfix'>";

		//显示类型
		wrapHtml += "<div class='type-article'>"+dataObj.type+"</div>";	
		//显示用户
		wrapHtml += "<div class='author'>";
		wrapHtml += "<p href='#'>用户："+dataObj.author.userName+"</p>"
		wrapHtml += "</div>";
		//显示时间
		wrapHtml += "<div class='time'>";
		wrapHtml += "<p> 时间："+dataObj.atime+"</p>"
		wrapHtml += "</div>";
		
	 	//关闭标签，对应class='bottom-father clearfix'
		wrapHtml += "</div>";
		
		blogDiv.innerHTML = wrapHtml;
		return blogDiv;
	}
	
	/*
	功能：界面删除所有的的待审核的博客信息
	*/
	function clearAllFeeds(){
		$("#blogsbox").children("div").remove();
		$("#blogsbox").children("a").remove();
	}
</script>
  </head>
  
  <body onload="showAllBlogs();">
  	<div class="left-body fl">
		<div class="logo"></div>
		<div class="user-info">
		<c:choose>
          	  <c:when test="${empty sessionScope.session_admin }">
	            <div class="notLogin">
	                <a href="<c:url value='/jsp/admin/adminLogin.jsp'/>" class="sign-in">登入</a>
	            </div>
          	  </c:when>
          	  <c:otherwise>
	            <div class="hasLogin">
		    		<div class="admin fl">${sessionScope.session_admin.username }</div>
	            </div>
          	  </c:otherwise>
           </c:choose>
		</div>
		<div class="navigation">
			<ul>
				<a href="<c:url value='/manageFeed.jsp'/>">
					<li>反馈信息</li>
				</a>
				<a href="<c:url value='/manageBlog.jsp'/>">
					<li class="currentPage">博客审核</li>
				</a>
				<a href="<c:url value='/UserServlet?method=adminQuit'/>">
					<li class="spacing2">退出</li>
				</a>
			</ul>
		</div>
	</div>
    <div class="right-body fl">
		<div class="article-box clearfix" id="blogsbox">	
		
		</div>
	</div>
  </body>
</html>
