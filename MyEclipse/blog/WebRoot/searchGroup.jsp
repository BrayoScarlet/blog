<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.util.*,yang.util.*,yang.service.*,yang.model.*"%>
<%@ page import="org.springframework.beans.factory.BeanFactory" %>
<%
	/*
	功能：根据传来的小组名字，简介模糊查询
						
	*/
	String keywords = request.getParameter("keywords");
	
	BeanFactory factory = SpringUtils.getBeanFactory();
	GroupService service=factory.getBean(GroupService.class);
	StuGroupInfo[] groups = service.searchGroupByKey(keywords);
	
	//java对象(数组) 转换 json字符串
	String jsonStr=JsonUtils.objectToJson(groups);
	//json对象(数组) 转换 json字符串
	out.write(jsonStr);
%>