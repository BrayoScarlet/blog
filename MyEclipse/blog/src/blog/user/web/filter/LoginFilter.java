package blog.user.web.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import blog.user.domain.User;

@WebFilter(dispatcherTypes = { DispatcherType.REQUEST },
		urlPatterns = { "/jsp/user/filter/*" })
public class LoginFilter implements Filter {

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		/*
		 * 1, 从session中获取user信息
		 * 2, 判断如果session中存在user信息, 放行!
		 * 3, 否则,保存错误信息,转发到msg.jsp显示
		 */
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		User user = (User) httpRequest.getSession().getAttribute("session_user");
		if (user != null) {
			chain.doFilter(request, response);
		}
		else {
			httpRequest.setAttribute("msg", "您还没有登入!");
			httpRequest.getRequestDispatcher("/jsp/msg.jsp").forward(httpRequest,
					response);;
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {}

}
