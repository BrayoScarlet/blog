<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.util.*,yang.util.*,yang.service.*,yang.model.*"%>
<%@ page import="blog.article.service.*, blog.article.domain.*"%>
<%@ page import="org.springframework.beans.factory.BeanFactory" %>
<% 
	/*
	功能：根据传来的aid查询博客信息并返回
	*/
	String aid = request.getParameter("aid");

	ArticleService service = new ArticleService(); 
	Article blog = service.findArticleByAid(aid);
	
	//java对象(数组) 转换 json字符串
	String jsonStr=JsonUtils.objectToJson(blog);
	//json对象(数组) 转换 json字符串
	out.write(jsonStr);
%>