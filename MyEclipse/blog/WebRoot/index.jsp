<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- 向servlet发送请求, 获取主页main的初始化信息 -->
<jsp:forward page="/ArticleServlet?method=initialiseIndexPage"></jsp:forward>