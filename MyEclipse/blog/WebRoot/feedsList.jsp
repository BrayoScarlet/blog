<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="yang.model.*,yang.service.*,yang.util.*" %>
<%@ page import="org.springframework.beans.factory.BeanFactory" %>
<% 
	/*
	功能：查询所有的反馈的信息
	*/
	BeanFactory factory=SpringUtils.getBeanFactory();
	FeedBackService service = factory.getBean(FeedBackService.class);
	FeedBackInfo[] feeds = service.findAllFeedBackInfos();
	
	//java对象(数组) 转换 json字符串
	String jsonStr=JsonUtils.objectToJson(feeds);
	//json对象(数组) 转换 json字符串
	out.write(jsonStr);
%>