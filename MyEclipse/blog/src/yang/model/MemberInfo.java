package yang.model;

import blog.user.domain.User;

//学习小组包含哪些用户
public class MemberInfo {
	private StuGroupInfo stugroup;//学习小组
	private User user;//用户

	public StuGroupInfo getStugroup() {
		return stugroup;
	}

	public void setStugroup(StuGroupInfo stugroup) {
		this.stugroup = stugroup;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public MemberInfo(StuGroupInfo stugroup, User user) {
		super();
		this.stugroup = stugroup;
		this.user = user;
	}

	public MemberInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

}
