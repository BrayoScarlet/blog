<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="yang.model.*,yang.service.*,yang.util.*" %>
<%@ page import="org.springframework.beans.factory.BeanFactory" %>
<%
	String fbId = request.getParameter("fbId");
	
	BeanFactory factory= SpringUtils.getBeanFactory();
	FeedBackService service = factory.getBean(FeedBackService.class);
	boolean flag = service.deletefeedById(fbId);
	
	if(flag == true){//成功删除
		out.write("right");
	}else{
		out.write("wrong");	
	}
%>