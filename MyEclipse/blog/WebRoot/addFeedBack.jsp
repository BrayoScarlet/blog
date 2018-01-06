<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.util.*,yang.util.*,yang.service.*,yang.model.*"%>
<%@ page import="blog.user.domain.*"%>
<%@ page import="org.springframework.beans.factory.BeanFactory" %>
<% 
	/*
	功能：提交反馈，插入数据到FeedBackInfo
	*/
	//获得传来数据
	String fbTitle = request.getParameter("fbTitle");
	String fbContent = request.getParameter("fbContent");
	String fbContact = request.getParameter("fbContact");
	
	//从session中取出用户user对象,获得其id//////////////////////////
	User user = (User) request.getSession().getAttribute("session_user");
	
	//获取用户Id
	String userId = user.getUid();
	///////////////////////////////////////////////////////////////////	

	//随机生成32位的fbId
	String fbId = CommonUtils.uuid();
	//获得当前时间
	Date fbTime = new Date();
	
	//创建FeedBackInfo
	FeedBackInfo feed = new FeedBackInfo();
	feed.setFbId(fbId);
	feed.setFbTitle(fbTitle);
	feed.setFbContent(fbContent);
	feed.setFbContact(fbContact);
	feed.setUserId(userId);
	feed.setFbTime(fbTime);
	
	//向FeedBackInfo里插入数据
	BeanFactory factory=SpringUtils.getBeanFactory();
	FeedBackService feedservice = factory.getBean(FeedBackService.class);
	
	boolean flag = feedservice.insertFeedBackInfo(feed);
	
	if(flag == true ){//操作成功
		out.write("right");
	}else{
		out.write("wrong");
	}
%>