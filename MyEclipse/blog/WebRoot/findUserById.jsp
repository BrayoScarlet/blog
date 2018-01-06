<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="yang.util.*" %>
<%@ page import="blog.user.domain.*, blog.user.service.*" %>
<% 
	/*
	功能：根据用户传来的id找到user对象
	*/
	String uid = request.getParameter("userId");
	UserService service = new UserService();
	User user = service.findByUid(uid);
	
	//java对象(数组) 转换 json字符串
	String jsonStr = JsonUtils.objectToJson(user);
	//json对象(数组) 转换 json字符串
	out.write(jsonStr);
%>