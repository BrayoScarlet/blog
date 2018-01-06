<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.util.*,yang.util.*,yang.service.*,yang.model.*, blog.user.domain.*"%>
<%@ page import="org.springframework.beans.factory.BeanFactory" %>
<%
	/*
	功能：创建学习小组，插入数据到StuGroupInfo，MemberInfo
	*/
	System.out.println("buildGroup.jsp  work");
	//从buildGroup.html传过来数据
	String stugroupName = request.getParameter("stugroupName");
	String qqNumber = request.getParameter("qqNumber");
	String stugroupIntro = request.getParameter("stugroupIntro");
	String imageUrl = request.getParameter("imageUrl");
	
	System.out.println("stugroupName:" +stugroupName);
	//可以对传过来数据做检查
	
	//从session中取出用户user对象,获得其id//////////////////////////
	User user = (User) request.getSession().getAttribute("session_user");
	String leadId = user.getUid();

	//随机生成32位的stugroupId
	String stugroupId = CommonUtils.uuid();
	//初始人数只有1人
	int memberNum = 1;
	//获得当前时间
	Date buildDate = new Date();
	//建立StuGroupInfo对象
	StuGroupInfo group = new StuGroupInfo();
	group.setStugroupId(stugroupId);
	group.setMemberNum(memberNum);
	group.setLeadId(leadId);
	group.setBuildDate(buildDate);
	group.setStugroupName(stugroupName);
	group.setQqNumber(qqNumber);
	group.setStugroupIntro(stugroupIntro);
	group.setImageUrl(imageUrl);
	
	//要在两张表StuGroupInfo,和学习小组成员表MemberInfo里插入数据
	BeanFactory factory=SpringUtils.getBeanFactory();
	GroupService service=factory.getBean(GroupService.class);
	//向表StuGroupInfo插入数据
	boolean istoStuGroupInfo = service.insertGroup(group);
	//向表MemberInfo里插入数据
	boolean istoMemberInfo = service.insertMemberInfo(group, user);
	
	if(istoStuGroupInfo == true && istoMemberInfo ==true){//操作成功
		out.write("right");
	}else{
		out.write("wrong");
	}
%>