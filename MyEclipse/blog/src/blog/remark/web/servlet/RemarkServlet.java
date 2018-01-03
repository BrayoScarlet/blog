package blog.remark.web.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blog.article.domain.Article;
import blog.article.service.ArticleService;
import blog.remark.domain.Remark;
import blog.remark.service.RemarkService;
import blog.user.domain.User;
import blog.user.service.UserService;
import blog.utils.commons.CommonUtils;
import blog.utils.servlet.BaseServlet;

@WebServlet("/RemarkServlet")
public class RemarkServlet extends BaseServlet {
	private RemarkService remarkService = new RemarkService();
	private UserService userService = new UserService();
	private ArticleService articleService = new ArticleService();

	/**
	 * 添加回复功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addRemark(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * 添加回复信息
		 * 1, 获取请求参数
		 * 2, 创建Remark对象实例
		 * 3, 根据article_aid查询article对象, 并赋给remark
		 * 4, 根据author_uid查询user对象, 并赋给remark(回复的人, 与article中的author不一定一样)
		 * 5, 补全rid和rtime
		 * 6, 调用remarkService方法完成remark的添加
		 * 
		 * 查询博客信息及所有回复信息用于display-article.jsp页面显示
		 * 7, 保存article信息到request域 
		 * 8, 根据article_aid查询所有回复信息List<Reamrk>(按时间从近到远排序)
		 * 9, 转发到display-article.jsp
		 */

		//获取请求参数
		String articleAid = request.getParameter("article_aid");
		String authorUid = request.getParameter("author_uid");
		String rcontent = request.getParameter("rcontent");

		Remark remark = new Remark();
		//设置rid
		remark.setRid(CommonUtils.uuid());
		//获取并设置author
		User author = userService.findByUid(authorUid);
		remark.setAuthor(author);
		//获取并设置article
		Article article = articleService.findArticleByAid(articleAid);
		remark.setArticle(article);
		//设置rcontent
		remark.setRcontent(rcontent);
		//设置rtime
		remark.setRtime(new Date());

		//调用remarkService方法完成remark的添加
		remarkService.addRemark(remark);

		//保存article信息到request域 
		request.setAttribute("article", article);

		List<Remark> remarkList = remarkService.findRemarksByArticle(article);
		request.setAttribute("remarkList", remarkList);
		request.setAttribute("remarkList_size", remarkList.size());

		return "f:/jsp/article/filter/display-article.jsp";
	}

	/**
	 * 删除回复功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteRemark(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1, 从请求参数中获取rid
		 * 2, 根据rid调用remarkService方法获取aid
		 * 3, 根据rid调用remarkService方法删除回复
		 * 
		 * 查询博客信息及所有回复信息用于display-article.jsp页面显示
		 * 4, 根据aid获取article对象并保存到request域 
		 * 4, 根据article_aid查询所有回复信息List<Reamrk>并保存到request域 (按时间从近到远排序)
		 * 5, 转发到display-article.jsp
		 */
		String rid = request.getParameter("rid");
		String aid = remarkService.findAidByRid(rid);
		remarkService.deleteRemarkByRid(rid);

		Article article = articleService.findArticleByAid(aid);
		request.setAttribute("article", article);

		List<Remark> remarkList = remarkService.findRemarksByArticle(article);
		request.setAttribute("remarkList", remarkList);
		request.setAttribute("remarkList_size", remarkList.size());

		return "f:/jsp/article/filter/display-article.jsp";
	}

}
