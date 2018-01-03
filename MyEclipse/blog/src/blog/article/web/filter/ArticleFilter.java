package blog.article.web.filter;

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

/**
 * 文章展示页面拦截..
 * @author SolitaryEagle
 *
 */
@WebFilter(dispatcherTypes = { DispatcherType.REQUEST },
		urlPatterns = { "/jsp/article/filter/display-article.jsp" })
public class ArticleFilter implements Filter {

	public void destroy() {}

	/**
	 * 如果用户通过请求方式到达display-article.jsp页面,则直接拦截,并转发到msg.jsp页面.
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		httpRequest.setAttribute("msg", "您来错地方啦^_^!");
		httpRequest.getRequestDispatcher("/jsp/msg.jsp").forward(httpRequest, response);;
	}

	public void init(FilterConfig fConfig) throws ServletException {}

}
