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
<title>填写反馈</title>  
    <meta charset="utf-8">
    <script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>

    <link href="bootstrap3.3.4/css/bootstrap.min.css" rel="stylesheet">   
    <link href="css/base.css" rel="stylesheet">
    <link href="css/navigation.css" rel="stylesheet">
    <link href="css/feedbackedit.css" rel="stylesheet">

    <script src="bootstrap3.3.4/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="layer/layer.js" type="text/javascript"></script>

<script type="text/javascript">

	//检查内容是否为空
	function checkandsubmit(){
		//标题
    	if($("#fbTitle").val()==null || $("#fbTitle").val()==""){
			layer.msg('标题输入为空');
        	$("#fbTitle").focus(); //焦点对准输入标题框 
        	return;
  	  	}
	
    	if($("#fbContent").val()==null || $("#fbContent").val()==""){
			layer.msg('内容为空');
        	$("#fbContent").focus(); 
        	return;
  	  	}
    	
    	if($("#fbContact").val()==null || $("#fbContact").val()==""){
			layer.msg('联系方式为空');
        	$("#fbContact").focus();  
        	return;
  	  	}
	
		//反馈标题
		var fbTitle = $("#fbTitle").val();
		//反馈内容
		var fbContent = $("#fbContent").val();
		//联系方式
		var fbContact = $("#fbContact").val();
		
		//把填入的数据发给addFeedBack.jsp处理
		$.ajax({
		    type: 'post',
		    url: 'addFeedBack.jsp',
		    data: {'fbTitle': fbTitle , 'fbContent':fbContent ,'fbContact':fbContact},
		    success: function (result) { //返回json结果
		    	var res = String($.trim(result)); //这个解析很关键
		    	if(res == "right"){
		    		layer.msg('提交反馈成功');
		    	}else{
		    		layer.msg('提交反馈失败，请重试');
		    	}
		    }
		});

	}

</script>

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
					<li class="currentPage"><span></span>用户反馈</li>
				</a>
				<a href="<c:url value='/UserServlet?method=quit'/>">
					<li class="spacing2"><span></span>退出</li>
				</a>
			</ul>
		</div>
	</div>
    <div class="middle-body fl">
		<form action="#" class="form-horizontal">
			<div class="form-group">
				<label class="col-sm-2 control-label">标题</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" 
					id="fbTitle" placeholder="主题..."/>			
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">内容</label>
				<div class="col-sm-10">
					<textarea  class="form-control" cols="20" rows="6"
					id="fbContent" placeholder="写下您的具体问题..."></textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">联系方式</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" 
					id="fbContact" placeholder="留下您的联系方式（手机号或者其他）"/>			
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-2"></div>
				<div class="col-sm-10">
					<input type="button" class="btn btn-info" onclick="checkandsubmit();" value="提交"/>
				</div>
			</div>
		</form>
	</div>
  </body>
</html>
