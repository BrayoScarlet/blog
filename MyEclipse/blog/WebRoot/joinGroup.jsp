<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.util.*,yang.util.*,yang.service.*,yang.model.*"%>
<%@ page import="blog.user.domain.*"%>
<%@ page import="org.springframework.beans.factory.BeanFactory" %>
<%
	/*
	功能：用户点击“加入”按钮参加小组
	*/
	String stugroupId = request.getParameter("stugroupId");
	StuGroupInfo group = new StuGroupInfo();
	group.setStugroupId(stugroupId);

	//从session中取出用户user对象,获得其id//////////////////////////
	User user = (User) request.getSession().getAttribute("session_user");
	
	//学习小组成员表MemberInfo里插入数据
	BeanFactory factory=SpringUtils.getBeanFactory();
	GroupService service=factory.getBean(GroupService.class);
	boolean flag1 = service.insertMemberInfo(group, user);
	
	//StuGroupInfo表人数增加1
	boolean flag2 = false;
	if(flag1){
		flag2 = service.plusMemberNum(stugroupId);
	}
	
	if(flag1 == true && flag2 == true){//操作成功
		out.write("right");
	}else{
		out.write("wrong");
	}
			
%>