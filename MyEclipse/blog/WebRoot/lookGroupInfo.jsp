<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.util.*,yang.util.*,yang.service.*,yang.model.*"%>
<%@ page import="blog.user.domain.*"%>
<%@ page import="org.springframework.beans.factory.BeanFactory" %>
<%
	/*
	功能：用来查询某一个小组的信息，并把这个信息返回给服务器
	*/
	String stugroupId= request.getParameter("stugroupId");
	System.out.println("lookGoupInfo.jsp works");
	System.out.println("stugroupId:"+stugroupId);
	//传过来的小组id为空
	if(stugroupId ==null || stugroupId.equals("") ){
		out.print("");
		return ;
	}
	
	BeanFactory factory = SpringUtils.getBeanFactory();
	GroupService service=factory.getBean(GroupService.class);
	/*
	判断该用户是否属于学习小组，是，直接显示需要信息；不是，显示加入按钮
	从session中取出用户user对象,获得其id
	*/
	User user = (User) session.getAttribute("session_user");
	//验证是否已登入
	if(user == null) {
		user = new User();
	}
	
	boolean isofMember = service.isofMember(stugroupId, user);
	
	//为了把是否属于这个消息写入json而创立的对象model
	IsMember is = new IsMember();
	if(isofMember==true){
		is.setFlag("right");
	}
	else{
		is.setFlag("wrong");
	}
	//根据Id查看学习小组
	StuGroupInfo stugroupInfo = service.findGroupById(stugroupId);
	is.setStugroupInfo(stugroupInfo);
	
	//java对象(数组) 转换 json字符串
	String jsonStr=JsonUtils.objectToJson(is);
	//json对象(数组) 转换 json字符串
	out.write(jsonStr);
%>