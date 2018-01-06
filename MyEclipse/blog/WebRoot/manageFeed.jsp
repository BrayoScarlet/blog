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
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 <meta charset="utf-8">
    <title>管理反馈</title>  
    <script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>

    <link href="bootstrap3.3.4/css/bootstrap.min.css" rel="stylesheet">   
    <link href="css/base.css" rel="stylesheet">
    <link href="css/navigation.css" rel="stylesheet">

    <link href="css/article/my-article.css" rel="stylesheet">
    <link href="css/manageFeed.css" rel="stylesheet">

    <script src="bootstrap3.3.4/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="layer/layer.js" type="text/javascript"></script>
	
<script type="text/javascript">
	/*
	功能：显示所有的反馈信息
	*/
	function showAllFeeds(){

		$.post("feedsList.jsp" ,function(result){
			var feeds=JSON.parse(result);
			//展示反馈信息的区域
			var feedsbox=document.getElementById("feedsbox");
			//AJAX传过来数据,
			for(var index in feeds){
				var fbId= feeds[index].fbId;//反馈的Id

				//新增反馈结点并把它显示到feedsbox
				var userName = findUserNameById( feeds[index].userId );
				var feedBack = createFeedBack( feeds[index] ,userName);
				
				feedsbox.appendChild(feedBack);
			}
		});	
	}
	/*
	功能：界面删除所有的的反馈信息
	*/
	function clearAllFeeds(){
		$("#feedsbox").children("div").remove();
		$("#feedsbox").children("a").remove();
	}
	/*
		参数：userId:用户id
		功能：返回一个user的名字
		、、、、、、、、、、、、、、、、、、、、、还没测试
	*/
	function findUserNameById(userId){
		var user ;
		var userName;
		/*
		问题：在ajax回调函数中return返回退出的仅仅是回调函数本身，
		而并非外层的整个函数，所以造成外层函数无返回值，而返回undefined。
		*/
		$.ajax({
		    type: 'post',
		    url: 'findUserById.jsp',
		    async:  false, //关闭异步
		    data: {'userId':userId} ,
		    success: function (result) { //返回json结果
		    	user = JSON.parse(result);
		    	userName = user.username;
		    }
		});
		return userName;
	}
	
	/*
		参数：dataObj 从json解读的反馈单位
		功能：创建反馈节点，返回值反馈节点
	*/
	function createFeedBack(dataObj , userName){
		//整个反馈的div
		var FeedDiv = document.createElement('div');
		FeedDiv.setAttribute('class' ,'breif-info');
		var wrapHtml;
		wrapHtml = "<h2 class='article-title'>"+dataObj.fbTitle+" </h2>";
		wrapHtml += "<div>"+dataObj.fbContent+"</div>";
		wrapHtml += "<div class='bottom-father clearfix'>";
		//显示用户
		wrapHtml += "<div class='author'>";
		//wrapHtml += "<span>"++"</span>";
		wrapHtml += "<p>用户："+userName+"</p>"
		wrapHtml += "</div>";
		//显示时间
		wrapHtml += "<div class='time'>";
		//wrapHtml += "<span>"++"</span>";
		wrapHtml += "<p> 时间："+dataObj.fbTime+"</p>"
		wrapHtml += "</div>";
		//显示联系方式
		wrapHtml += "<div class='time'>";
		//wrapHtml += "<span>"++"</span>";
		wrapHtml += "<p>"+'联系方式：'+dataObj.fbContact+"</p>"
		wrapHtml += "</div>";
		//删除
		wrapHtml += "<button type='button' class='btn delete-article fr' onclick='deleteFeed(\""
 	   + dataObj.fbId + "\");' >删除</button>";
 	   //关闭标签，对应class='bottom-father clearfix'
		wrapHtml += "</div>";
	
		FeedDiv.innerHTML = wrapHtml;
		return FeedDiv;
	}
	
	/*
	功能：删除fbId的反馈
	*/
	function deleteFeed(fbId){
		$.post("deleteFeed.jsp",{'fbId':fbId} ,function(result){
	    	var res = String($.trim(result)); //这个解析很关键
	    	if(res == "right"){
	    		layer.msg('成功删除反馈');

	    	}else{
	    		layer.msg('请重试');
	    	}
	    	//刷新界面
	    	clearAllFeeds();
	    	showAllFeeds();
		});
	}
</script>
  </head>
 
<body onload="showAllFeeds();">
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
	
	<div class="right-body fl">
		<div class="article-box clearfix" id="feedsbox">	
			
		</div>
	</div>
</body>
</html>
