<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="yang.util.*" %>
<%@ page import="java.util.*" %>
<%@ page import="blog.article.service.*, blog.article.domain.*" %>
<% 
	/*
	功能：显示待审核的博客
	*/

	//显示所有博客
	ArticleService service = new ArticleService();
	//返回给服务器的blogs
	List<Article> blogs = service.findNotVerifyArticles();
	
	//将list变成数组
	Article[] objs = blogs.toArray(new Article[blogs.size()]);
	
	//java对象(数组) 转换 json字符串
	String jsonStr = JsonUtils.objectToJson(objs);
	
	//json对象(数组) 转换 json字符串
	out.write(jsonStr);
%>