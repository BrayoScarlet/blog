package blog.user.web.servlet;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blog.user.domain.User;
import blog.user.service.UserException;
import blog.user.service.UserService;
import blog.utils.commons.CommonUtils;
import blog.utils.encryption.Encryption;
import blog.utils.mail.Mail;
import blog.utils.mail.MailUtils;
import blog.utils.servlet.BaseServlet;

@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {

	private UserService userService = new UserService();

	/**
	 * 管理员登录验证
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String adminLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1, 获取表单数据,
		 * 2, 调用service方法完成验证
		 */
		User admin = CommonUtils.toBean(request.getParameterMap(), User.class);
		admin.setPassword(Encryption.getMD5(admin.getPassword()));
		try {
			User user = userService.adminLogin(admin);
			request.getSession().setAttribute("session_admin", user);
			return "r:/manageFeed.jsp";
		}
		catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			return "f:/jsp/admin/adminLogin.jsp";
		}
	}

	/**
	 * 注册功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1, 将form表单封装成bean对象
		 * 	* 验证用户名
		 * 	* 验证密码
		 * 	* 验证邮箱
		 * 	* 验证验证码
		 * 2, 判断是否有错误信息(回显)
		 * 		有, 保存错误信息到request域,转发到register.jsp
		 * 3, 补全Uid, code
		 * 4, 调用service方法完成注册
		 * 5, 发邮件
		 * 6, 保存成功信息到 msg.jsp
		 */
		//建立一个Map用于记录错误信息
		Map<String, String> errors = new HashMap<String, String>();

		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		//验证用户名
		String username = form.getUsername();
		if (username == null || username.trim().isEmpty()) {
			errors.put("username", "用户名不能为空!");
		}
		else if (username.length() < 3 || username.length() > 10) {
			errors.put("username", "用户名长度必须在3~10之间!");
		}

		//验证密码
		String password = form.getPassword();
		if (password == null || password.trim().isEmpty()) {
			errors.put("password", "密码不能空!");
		}
		else if (password.length() < 9 || password.length() > 20) {
			errors.put("password", "密码长度必须在9~20之间!");
		}
		String verifyPassword = request.getParameter("re-password");
		if (!password.equals(verifyPassword)) {
			errors.put("verifyPassword", "两次密码不一致!");
		}

		//验证邮箱
		String email = form.getEmail();
		if (email == null || email.trim().isEmpty()) {
			errors.put("email", "email不能空!");
		}
		else if (email.length() < 3 || email.length() > 20) {
			errors.put("email", "密码长度必须在3~20之间!");
		}
		else if (!email.matches("\\w+@\\w+.\\w+")) {
			errors.put("email", "email格式错误!");
		}

		//验证验证码
		String verifyValue = request.getParameter("verify-value");
		String verifyCode = request.getParameter("verify-code");
		if (!verifyCode.equalsIgnoreCase(verifyValue)) {
			errors.put("verifyCode", "验证码错误!");
		}

		if (errors.size() > 0) {
			request.setAttribute("errors", errors);
			request.setAttribute("form", form);
			return "f:/jsp/user/register.jsp";
		}

		//补全uid和token
		form.setUid(CommonUtils.uuid());
		form.setToken(CommonUtils.uuid() + CommonUtils.uuid());

		try {
			//使用md5算法加密密码
			form.setPassword(Encryption.getMD5(form.getPassword()));
			userService.register(form);
		}
		catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			return "f:/jsp/user/register.jsp";
		}

		//发激活邮件
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader()
				.getResourceAsStream("register_email.properties"));
		String host = props.getProperty("host");//获取服务器主机
		String uname = props.getProperty("uname");//获取用户名
		String pwd = props.getProperty("pwd");//获取密码
		String from = props.getProperty("from");//获取发件人
		String to = form.getEmail();//获取收件人
		String subject = props.getProperty("subject");//获取主题
		String content = props.getProperty("content");//获取邮件内容
		content = MessageFormat.format(content, form.getToken());//替换{0}

		Session session = MailUtils.createSession(host, uname, pwd);//得到session
		Mail mail = new Mail(from, to, subject, content);//创建邮件对象
		try {
			MailUtils.send(session, mail);//发邮件！
		}
		catch (MessagingException e) {}

		request.setAttribute("msg", "恭喜，注册成功！请马上到邮箱激活!");
		return "f:/jsp/msg.jsp";
	}

	/**
	 * 邮箱激活激活功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String active(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1，获取参数激活码
		 * 2，调用service方法完成激活
		 * 	》保存异常信息到request域，转发到msg.jsp
		 * 3，保存成功信息到request域，转发到msg.jsp
		 */
		String token = request.getParameter("token");
		try {
			userService.active(token);
			request.setAttribute("msg", "恭喜，您激活成功了！请马上登入!");
		}
		catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
		}
		return "f:/jsp/msg.jsp";
	}

	/**
	 * 登入功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1, 封装表单数据到bean中
		 * 2, 调用service方法完成登入
		 */
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		//对密码进行md5加密, 以便与数据库中的密码进行匹配
		form.setPassword(Encryption.getMD5(form.getPassword()));
		try {
			User user = userService.login(form);
			request.getSession().setAttribute("session_user", user);
			return "r:/index.jsp";
		}
		catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			return "f:/jsp/user/login.jsp";
		}
	}

	/**
	 * 发送激活账户邮件功能邮件功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String sendActiveEmail(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 1, 从request域中获取邮箱
		 * 2, 验证邮箱是否已注册
		 * 		未注册, 保存未注册信息和邮箱到request域, 转发到active.jsp页面
		 * 3, 验证邮箱是否以激活
		 * 		已激活, 保存已激活信息和邮箱到request域, 转发到active.jsp页面
		 * 4, 发送激活账户邮件
		 * 5, 用户在邮件中点击链接进行激活
		 */
		String email = request.getParameter("email");
		User user = userService.findByEmail(email);
		if (user == null) {
			request.setAttribute("msg", email + "未注册!");
			request.setAttribute("email", email);
			return "f:/jsp/user/active.jsp";
		}
		if (user.isState()) {
			request.setAttribute("msg", email + "已激活!");
			request.setAttribute("email", email);
			return "f:/jsp/user/active.jsp";
		}

		//发激活邮件
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader()
				.getResourceAsStream("register_email.properties"));
		String host = props.getProperty("host");//获取服务器主机
		String uname = props.getProperty("uname");//获取用户名
		String pwd = props.getProperty("pwd");//获取密码
		String from = props.getProperty("from");//获取发件人
		String to = user.getEmail();//获取收件人
		String subject = props.getProperty("subject");//获取主题
		String content = props.getProperty("content");//获取邮件内容
		content = MessageFormat.format(content, user.getToken());//替换{0}

		Session session = MailUtils.createSession(host, uname, pwd);//得到session
		Mail mail = new Mail(from, to, subject, content);//创建邮件对象
		try {
			MailUtils.send(session, mail);//发邮件！
		}
		catch (MessagingException e) {}

		request.setAttribute("msg", "邮件已发送,请马上到邮箱激活!");
		return "f:/jsp/msg.jsp";
	}

	/**
	 * 发送重置密码邮件功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String sendResetPasswordEmail(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 1, 从request域中获取邮箱
		 * 2, 验证邮箱是否已注册
		 * 		未注册, 保存未注册信息到request域, 转发到forgot.jsp页面
		 * 3, 发送重置密码邮件
		 * 4, 用户在邮件中点击链接进行密码修改
		 */

		String email = request.getParameter("email");
		User user = userService.findByEmail(email);
		if (user == null) {
			request.setAttribute("msg", email + "未注册!");
			return "f:/jsp/user/forgot.jsp";
		}

		Properties props = new Properties();
		props.load(this.getClass().getClassLoader()
				.getResourceAsStream("reset_email.properties"));
		String host = props.getProperty("host");//获取服务器主机
		String uname = props.getProperty("uname");//获取用户名
		String pwd = props.getProperty("pwd");//获取密码
		String from = props.getProperty("from");//获取发件人
		String to = email;//获取收件人
		String subject = props.getProperty("subject");//获取主题
		String content = props.getProperty("content");//获取邮件内容
		content = MessageFormat.format(content, user.getUid());//替换{0}

		Session session = MailUtils.createSession(host, uname, pwd);//得到session
		Mail mail = new Mail(from, to, subject, content);//创建邮件对象
		try {
			MailUtils.send(session, mail);//发邮件！
		}
		catch (MessagingException e) {}

		request.setAttribute("msg", "请到邮箱进行重置密码!");
		return "f:/jsp/msg.jsp";
	}

	/**
	 * 重置密码
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String resetPassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1, 获取uid, 并验证uid是否存在
		 * 		不存在, 说明用户不是通过邮箱来重置密码的, 保存警告信息到request域, 重定向到msg.jsp页面
		 * 2, 获取两个密码,判断其是否满足要求
		 * 		不满足, 保存错误信息到request域, 转发到resetPassword.jsp
		 * 3, 加密密码
		 * 4, service方法完成更改密码
		 * 5, 转发到login.jsp
		 */
		String uid = request.getParameter("uid");
		if (uid == null || uid.equals("")) {
			request.setAttribute("msg", "请通过正常渠道重置密码! *_!");
			return "f:/jsp/msg.jsp";
		}
		User user = userService.findByUid(uid);
		if (user == null) {
			request.setAttribute("msg", "请通过正常渠道重置密码! *_!");
			return "f:/jsp/msg.jsp";
		}

		//当密码发生错误时, 需要转发到重置页面, 为了避免uid丢失, 先保存uid
		request.setAttribute("uid", uid);
		String password = request.getParameter("password");
		if (password == null || password.trim().isEmpty()) {
			request.setAttribute("msg", "密码不能空!");
			return "f:/jsp/user/resetPassword.jsp";
		}
		else if (password.length() < 9 || password.length() > 20) {
			request.setAttribute("msg", "密码长度必须在9~20之间!");
			return "f:/jsp/user/resetPassword.jsp";
		}
		String verifyPassword = request.getParameter("re-password");
		if (!password.equals(verifyPassword)) {
			request.setAttribute("msg", "两次密码不一致!");
			return "f:/jsp/user/resetPassword.jsp";
		}

		//加密密码
		password = Encryption.getMD5(password);
		//更新密码
		userService.updatePasswordByUid(uid, password);

		//保存成功信息
		request.setAttribute("msg", "重置密码成功!");
		return "f:/jsp/msg.jsp";
	}

	/**
	 * 编辑个人资料功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1, 从session中获取user
		 * 		不存在, 保存错误信息到request域, 转发到msg.jsp页面
		 * 2, 获取表单中的参数,封装到javabean对象form中
		 * 3, 用form更新user
		 * 4, 通过userService更新user的资料
		 * 5, 保存user到session域中
		 * 6, 转发到personal-profile.jsp
		 */

		User user = (User) request.getSession().getAttribute("session_user");
		if (user == null) {
			request.setAttribute("msg", "请您先登入!");
			return "f:/jsp/msg.jsp";
		}
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		updateUser(user, form);
		userService.updateUser(user);
		return "f:/jsp/user/filter/personal-profile.jsp";
	}

	/**
	 * 更新user资料
	 * @param user
	 * @param form
	 */
	private void updateUser(User user, User form) {
		user.setSchool(form.getSchool());
		user.setQualification(form.getQualification());
		user.setGraduationYear(form.getGraduationYear());
		user.setSpecialisations(form.getSpecialisations());
		user.setSex(form.getSex());
		user.setUbrief(form.getUbrief());
		user.setDomicile(form.getDomicile());
	}

	/**
	 * 修改密码功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String changePassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1, 从session中获取user
		 * 		不存在, 保存错误信息到request域, 转发到msg.jsp页面
		 * 2, 判断原密码是否正确
		 * 3, 判断新密码时候合法
		 * 4, 判读两次密码是否一致
		 * 5, 删除session域中的user, 表示退出
		 * 6, 保存成功信息, 转发到msg.jsp
		 */

		User user = (User) request.getSession().getAttribute("session_user");
		if (user == null) {
			request.setAttribute("msg", "请您先登入!");
			return "f:/jsp/msg.jsp";
		}

		//用一个map保存错误信息
		Map<String, String> errors = new HashMap<String, String>();

		//验证原密码
		String oldPassword = request.getParameter("oldPassword");
		//md5加密
		oldPassword = Encryption.getMD5(oldPassword);
		if (!oldPassword.equals(user.getPassword())) {
			errors.put("oldPassword", "原密码错误!");
		}

		//验证新密码
		String newPassword = request.getParameter("newPassword");
		if (newPassword == null || newPassword.trim().isEmpty()) {
			errors.put("newPassword", "密码不能空!");
		}
		else if (newPassword.length() < 9 || newPassword.length() > 20) {
			errors.put("newPassword", "密码长度必须在9~20之间!");
		}
		String verifyNewPassword = request.getParameter("verifyNewPassword");
		if (!newPassword.equals(verifyNewPassword)) {
			errors.put("verifyNewPassword", "两次密码不一致!");
		}

		//如果有错误! 转发至原界面
		if (errors.size() > 0) {
			request.setAttribute("errors", errors);
			return "f:/jsp/user/filter/change-password.jsp";
		}

		//将新密码md5加密
		newPassword = Encryption.getMD5(newPassword);
		userService.updatePasswordByUid(user.getUid(), newPassword);

		//删除session域中的user
		request.getSession().removeAttribute("session_user");

		//保存成功信息
		request.setAttribute("msg", "恭喜! 修改密码成功, 请重新登入!");
		return "f:/jsp/msg.jsp";
	}

	public String adminQuit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().invalidate();
		return "r:/manageFeed.jsp";
	}

	/**
	 * 退出功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String quit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1, 初始化session域
		 * 2, 重定向到index.jsp
		 */
		request.getSession().invalidate();
		return "r:/index.jsp";
	}

	/**
	 * 验证用户是否已登入
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String checkLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//验证是否已登入
		User user = (User) request.getSession().getAttribute("session_user");
		String url = request.getParameter("url");
		if (user == null) {
			request.setAttribute("msg", "请您先登入");
			return "f:/jsp/msg.jsp";
		}
		return "r:" + url;
	}
}
