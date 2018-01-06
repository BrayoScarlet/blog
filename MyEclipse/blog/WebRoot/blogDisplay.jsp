<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%
	String aid = request.getParameter("aid");
	request.setAttribute("aid", aid);
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
<meta charset="UTF-8">
	<title>博客展示</title>

	<link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
	<link rel="stylesheet" type="text/css" href="css/base.css"/>
	<link rel="stylesheet" type="text/css" href="css/navigation.css"/>
	<link rel="stylesheet" type="text/css" href="css/displayblog.css"/>

	<script src="js/jquery-3.2.1.js"></script>
	<script src="js/bootstrap.js"></script>
	<script src="layer/layer.js"></script>
	
<script type="text/javascript">
	function loadBlogs(){
		if (window.location.search != null && window.location.search != ""){
			var strs = window.location.search.split('=');
			if (strs.length == 2){
				var aid = strs[1]; //获得传来的aid博客
				
				$.ajax({
					dataType : 'json',
					url : 'lookBlogInfo.jsp',
					data : {'aid': aid},
				    success: function (result) { //返回json结果
				    	var blog = result;
				    	//显示内容
				    	$("#atitle").html( blog.atitle);
				    	$("#atype").html( blog.type);
				    	$("#author").html( blog.author.username );		    	
				    	$("#atime").html( blog.atime);
				    	$("#acontent").html( blog.acontent);
				    }   
				});
			}
		}
	}
	
	/*
		管理员点击‘通过’按钮
	*/
	function pass(aid){
		$.ajax({
			url : 'handleBlog.jsp',
			type : 'post',
			data : {'aid': aid ,'action' : 'pass'} ,
			success : function (result) { //返回json结果
		    	var res = String($.trim(result)); //这个解析很关键
		    	if(res == "right"){
		    		layer.msg('操作博客成功');
		    		window.location.href="manageBlog.jsp";
		    	}else{
		    		layer.msg('操作博客失败，请重试');
		    	}
		    }  
		});
	}
	
	/*
	管理员点击‘不通过’按钮
	*/
	function notpass(aid){
		$.ajax({
			url : 'handleBlog.jsp',
			data : {'aid': aid ,'action' : 'notpass'} ,
		    success: function (result) { //返回json结果
		    	var res = String($.trim(result)); //这个解析很关键
		    	if(res == "right"){
		    		layer.msg('操作博客成功');
		    		window.location.href="manageBlog.jsp";
		    	}else{
		    		layer.msg('操作博客失败，请重试');
		    	}
		    }   
		});
	}
	
</script>

  </head>
  
 <body class="clearfix" onload="loadBlogs();">
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
					<li class="currentPage">反馈信息</li>
				</a>
				<a href="<c:url value='/manageBlog.jsp'/>">
					<li>博客审核</li>
				</a>
				<a href="<c:url value='/UserServlet?method=adminQuit'/>">
					<li class="spacing2">退出</li>
				</a>
			</ul>
		</div>
	</div>
	<div class="right-body fr">
		<div class="article">
			<div class="article-header">
				<h1 class="article-title" id="atitle"></h1>
				<div class="desc-article clearfix">
					<div class="type-article" id="atype"></div>
					<div class="author">
						<span></span><p id="author"></p>
					</div>
					<div class="time-article">
						<span></span>
						<p id="atime"></p>
					</div>
				</div>
			</div>
			<div class="article-body" id="acontent"></div>
			<div >
				<button type="button" class="btn btn-primary fl" onclick="pass('${aid}');">通过</button>
			</div>
			<div >
				<button type="button" class="btn btn-danger fr" onclick="notpass('${aid}');">不通过</button>
			</div>
		</div>

	</div>
</body>
</html>
