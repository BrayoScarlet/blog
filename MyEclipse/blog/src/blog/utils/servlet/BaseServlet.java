package blog.utils.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * BaseServlet用来做其他类的父类
 * 	一个类多个请求处理方法, 每个请求原型与service相同!
 * 	原型 = 返回值类型 + 方法名称 + 参数列表
 * @author SolitaryEagle
 *
 */
public class BaseServlet extends HttpServlet {

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//设置请求编码
		request.setCharacterEncoding("utf-8");
		//设置响应编码
		response.setContentType("text/html;charset=utf-8");

		/*
		 * 1, 获取method参数, 它是用户想要调用的方法
		 * 2, 把方法名称变成Method类的实例对象
		 * 3, 通过invoke()来调用这个方法
		 */
		String methodName = request.getParameter("method");
		Method method = null;
		try {
			method = this.getClass().getMethod(methodName, HttpServletRequest.class,
					HttpServletResponse.class);
		}
		catch (Exception e) {
			throw new RuntimeException("您要调用的方法" + methodName + "不存在!", e);
		}
		try {
			String result = (String) method.invoke(this, request, response);
			//如果请求处理方法,返回不为空, 并清空左右两端的空格
			if (result != null && !(result = result.trim()).isEmpty()) {
				int index = result.indexOf(":");	//获取第一个冒号的位置
				if (index == -1) {	//如果没有冒号,默认使用转发
					request.getRequestDispatcher(result).forward(request, response);
				}
				else {	//如果有冒号
					String start = result.substring(0, index); //分隔出前缀
					String path = result.substring(index + 1);	//分隔出路径
					if (start.equalsIgnoreCase("f")) {	//前缀为f,使用转发
						request.getRequestDispatcher(path).forward(request, response);
					}
					else if (start.equalsIgnoreCase("r")) {	//前缀为r,使用重定向,需要项目路径
						response.sendRedirect(request.getContextPath() + path);
					}
				}
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
