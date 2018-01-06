<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,yang.util.*,yang.service.*,yang.model.*"%>
<%@ page import="org.springframework.beans.factory.BeanFactory" %>
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
    <title>浏览学习小组</title>  
    <script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>

    <link href="bootstrap3.3.4/css/bootstrap.min.css" rel="stylesheet">   
    <link href="css/base.css" rel="stylesheet">
    <link href="css/navigation.css" rel="stylesheet">
    <link href="css/stugroup.css" rel="stylesheet">

    <script src="bootstrap3.3.4/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="layer/layer.js" type="text/javascript"></script>
	
	<script type="text/javascript">
		var request=new XMLHttpRequest();
		
		/*
		功能：展示所有的学习小组
		*/
		function showAllGroups(){
//			alert('1');
			$.post("groupsList.jsp" ,function(result){
				var groups=JSON.parse(result);
//				alert(groups);
				var groupsList=document.getElementById("groupsList");//展示小组的区域
				//AJAX传过来数据
				for(var index in groups){
					var stugroupId=groups[index].stugroupId;//学习小组的Id
//					alert(groups[index].stugroupId);		
					var groupNode=createGroup(groups[index]);//新增小组并把它显示到groupsList
					groupsList.appendChild(groupNode);
				}
			});	
		}

		/*
		参数：dataObj 从json解读的小组单位
		功能：创建小组，返回值新增小组结点
		*/
		function createGroup(dataObj){
			var groupLi=document.createElement("li");//每个小组
			var groupA=document.createElement("a");
			//groupA.setAttribute("href","lookGroup");//不能写成死链接javascript:void(0);
			//点击该小组执行事件，会弹出layer框
			groupA.setAttribute("onclick","seeGroup(\""+dataObj.stugroupId+"\") ");
			
//			var form = JSON.stringify(dataObj);			
			var wrapHtml;
			wrapHtml ="<img class='logo' src=\""+dataObj.imageUrl+"\" >";
			wrapHtml +="<div class='infoarea'>";
			wrapHtml +="<h1 class='tt'> 名称："+dataObj.stugroupName+" </h1>";
			wrapHtml +="<h2 class='writer'> 简介："+dataObj.stugroupIntro+" </h2>";
			wrapHtml +="<p class='pnum'> "+dataObj.memberNum+" </p>";
			wrapHtml +="</div>";
//			wrapHtml = wrapHtml.format(form);

			groupA.innerHTML = wrapHtml;
			groupLi.appendChild(groupA);			
			return groupLi;
		} 
		/*
		功能：比较粗暴清除所有的小组，用来刷新
		*/
		function clearAllGroups(){
			$('#groupsList li').each(function(){
			    $(this).remove();
			}); 
		}
		//使用全局变量标识弹出层
		var layerIndex=-1;
		/*
		参数：stugroupId 学习小组id（用户点击，传来id）
		功能：查看详细的小组信息
		*/
		function seeGroup(stugroupId){
//			alert(stugroupId);
			layerIndex=layer.open({		//使用layer.js弹出lookGroup.html
				type : 2,				//以iframe的形式弹出lookGroup.html
				title : '查看小组详细信息',		//弹出层(对话框)标题
				shadeClose : true, 			//点击遮罩关闭层
				area : [ '500px', '500px' ],//窗口大小
				content : 'lookGroup.html?stugroupId=' +stugroupId,//弹出内容URL
				end : function(e) { //弹出层关闭后回调函数,可能涉及到异步问题
					//刷新页面重新查询
					clearAllGroups();
					showAllGroups();
				}
			});
		}
		/*
		功能：关闭查看小组页面
		*/
		function closeBookEdit(){
			layer.close(layerIndex);
		}
			
		//点击搜索按钮，对小组名字和简介采用模糊查询
		function searchGroupsByName(){
			var keywords = $("#groupname").val();
			var url = "searchGroup.jsp";
			$.post(url ,{'keywords':keywords},function(result){
				var groups=JSON.parse(result);
				//先把原来界面清空
				clearAllGroups();
				var groupsList=document.getElementById("groupsList");//展示小组的区域
				//AJAX传过来数据
				for(var index in groups){
					var stugroupId=groups[index].stugroupId;//学习小组的Id
//					alert(groups[index].stugroupId);		
					var groupNode=createGroup(groups[index]);//新增小组并把它显示到groupsList
					groupsList.appendChild(groupNode);
				}

			});	
			
		}
	</script>	

  </head>
<body onload="showAllGroups();">
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
				<a href="<c:url value='/ArticleServlet?method=findAllArticles'/>">
					<li class="spacing2"><span></span>博客</li>
				</a>
				<a href="<c:url value='/studygroup.jsp'/>">
					<li class="currentPage"><span></span>学习小组</li>
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
	<div class="middle-body fl">
        <div class="search">
            <div class="input-group">
    		  <input type="text" class="form-control" id="groupname"  placeholder="输入小组关键词...">
   			   <span class="input-group-btn">
     		   <button class="btn btn-primary" type="button" id="btnQuery"
     		   onclick="searchGroupsByName();" >搜索</button>
                   <a href="<c:url value='/UserServlet?method=checkLogin&url=/buildGroup.html'/>" id="btnQuery" class="btn btn-danger" >创建学习小组</a>
      			</span>
   			 </div>
        </div>
        <div>
            <ul id="groupsList">
        	</ul>
        </div>
	</div>
</body>
</html>
