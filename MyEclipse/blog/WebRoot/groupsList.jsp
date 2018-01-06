<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="yang.model.*,yang.service.*,yang.util.*" %>
<%@ page import="org.springframework.beans.factory.BeanFactory" %>
<%
	/*
	功能：查询所有的学习小组
	*/
	
	//通过服务层获得数据
	BeanFactory factory=SpringUtils.getBeanFactory();
	GroupService service=factory.getBean(GroupService.class);
	StuGroupInfo[] groups=service.findAllGroups();
	
	//java对象(数组) 转换 json字符串
	String jsonStr=JsonUtils.objectToJson(groups);
	//json对象(数组) 转换 json字符串
	out.write(jsonStr);
%>