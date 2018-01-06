<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.util.*,yang.util.*,yang.service.*,yang.model.*"%>
<%@ page import="blog.article.service.*, blog.article.domain.*"%>
<%@ page import="org.springframework.beans.factory.BeanFactory" %>
<% 
	/*
	功能：管理员点击‘通过’按钮，设置博客审核通过
		管理员点击‘不通过’按钮，设置博客审核通过			
	*/
	String action = request.getParameter("action");
	String aid = request.getParameter("aid");

	ArticleService service = new ArticleService();
	Article blog = service.findArticleByAid(aid);

	//管理员点击‘通过’按钮
	if(action.equals("pass")){
		blog.setVerify(1);
		service.updateArticle(blog);
	}else{
		//管理员点击‘不通过’按钮
		blog.setVerify(2);
		service.updateArticle(blog);
	}
	//写信息回服务器
	out.write("right");
%>