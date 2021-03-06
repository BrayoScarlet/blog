package blog.article.web.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blog.article.domain.Article;
import blog.article.service.ArticleService;
import blog.read_record.domain.ReadRecord;
import blog.read_record.service.ReadRecordService;
import blog.remark.domain.Remark;
import blog.remark.service.RemarkService;
import blog.user.domain.User;
import blog.user.service.UserService;
import blog.utils.commons.CommonUtils;
import blog.utils.pagination.Pagination;
import blog.utils.servlet.BaseServlet;

public class ArticleServlet extends BaseServlet {
	private ArticleService articleService = new ArticleService();
	private RemarkService remarkService = new RemarkService();
	private UserService userService = new UserService();
	private ReadRecordService readRecordService = new ReadRecordService();

	/**
	 * 按登入者的uid查询博客, 即查询我的博客功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findMyArticlesByPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 1, 从请求参数中获取当前页的值
		 * 2, 从session域中获取user对象
		 * 3, 按uid获取博客的记录总数
		 * 4, 根据uid, 通过articleServive方法查询博客
		 * 5, 将分页信息和查询结果List<Article> 保存到request域
		 * 6, 转发到my-article.jsp
		 */
		long curPage = Long.parseLong(request.getParameter("curPage"));
		User user = (User) request.getSession().getAttribute("session_user");
		long countRecord = articleService.findArticleCountRecordByUid(user.getUid());
		Pagination pagination = new Pagination(countRecord, curPage);
		List<Article> articleList = articleService.findArticlesByUidHasLimit(
				user.getUid(), pagination.getQueryBegin(), pagination.getQueryLength());
		request.setAttribute("articleList", articleList);
		request.setAttribute("pagination", pagination);
		return "f:/jsp/article/filter/my-article.jsp";
	}

	/**
	 *  按关键字搜索他人博客功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findOtherArticlesByKeyWordAndPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		long curPage = Long.parseLong(request.getParameter("curPage"));
		String uid = request.getParameter("uid");
		User author = userService.findByUid(uid);
		String keyWord = request.getParameter("key-word");
		Pagination[] pags = { new Pagination(curPage, curPage) };

		List<Article> articleList =
				articleService.findArticlesByUidAndKeyWordAndPage(uid, keyWord, pags);
		request.setAttribute("articleList", articleList);
		request.setAttribute("author", author);
		request.setAttribute("keyWord", keyWord);
		request.setAttribute("pagination", pags[0]);
		return "f:/jsp/article/other-article.jsp";
	}

	/**
	 * 按请求参数中的uid查询博客功能, 及查看他人博客
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findOtherArticlesByPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 1, 从请求参数中获取uid
		 * 2, 调用userService方法查询user对象
		 * 3, 保存到request域
		 * 4, 调用articleService方法查询所有博客
		 * 5, 将查询结果List<Article> 保存到request域
		 * 6, 获取登入者user对象
		 * 7, 判断参数中的uid与登入者的uid是否相同
		 * 	 * 相同, 转发到my-article.jsp
		 * 	 * 不同, 转发到other-article.jsp
		 */

		long curPage = Long.parseLong(request.getParameter("curPage"));
		String uid = request.getParameter("uid");
		long countRecord = articleService.findArticleCountRecordByUid(uid);

		Pagination pagination = new Pagination(countRecord, curPage);

		User author = userService.findByUid(uid);
		request.setAttribute("author", author);

		List<Article> articleList = articleService.findArticlesByUidHasLimit(uid,
				pagination.getQueryBegin(), pagination.getQueryLength());

		request.setAttribute("articleList", articleList);
		request.setAttribute("pagination", pagination);
		User user = (User) request.getSession().getAttribute("session_user");
		if (user != null && uid.equals(user.getUid())) {
			return "f:/jsp/article/filter/my-article.jsp";
		}
		return "f:/jsp/article/other-article.jsp";
	}

	/**
	 * 按关键词查找博客功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findArticlesByKeyWordAndPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 1, 从请求参数中获取关键词参数和当前页数
		 * 2, 构造分页类数组保存一个分页类(假的)
		 * 3, 调用articleService方法, 查询相关的博客List<Article>, 掉包分页类(真的)
		 * 4, 保存List<Article>, keyWord, 分页信息, 到request域, 用于回显
		 * 5, 转发到all-article.jsp
		 */
		long curPage = Long.parseLong(request.getParameter("curPage"));
		String keyWord = request.getParameter("key-word");
		Pagination[] pags = { new Pagination(curPage, curPage) };

		List<Article> articleList =
				articleService.findArticlesByKeyWordAndPage(keyWord, pags);
		request.setAttribute("articleList", articleList);
		request.setAttribute("keyWord", keyWord);
		request.setAttribute("pagination", pags[0]);
		return "f:/jsp/article/all-article.jsp";
	}

	/**
	 * 删除博客功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteArticleByAid(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 1, 从请求参数中获取aid
		 * 2, 根据aid, 调用articleService方法删除article
		 * 3, 从调用findArticlesByMyUid()方法, 并将其结果返回
		 */
		String aid = request.getParameter("aid");
		articleService.deleteArticleByAid(aid);
		return findMyArticlesByPage(request, response);
	}

	/**
	 * 添加文章功能(对于用户来说,就是发表文章) 和 编辑文章功能(对用户来说,就是修改文章)
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addArticle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1, 获取表单数据封装到JavaBean对象article中
		 * 2, 从session中获取user对象, 补全到article
		 * 3, 判断abstractContent是否为null/""
		 * 		*若为空,从acontent中截取前200字符,补全
		 * 4, 判断article.aid是否为""(空字符串)
		 * 		* 如果为"", 说明用户在发表文章
		 * 			(1) 补全aid
		 * 			(2) 补全atime
		 * 			(3) 调用articleService 添加  方法完成添加
		 * 			(4) 保存发表成功信息到request域, 
		 * 		* 如果不为"", 说明用户在修改文章
		 * 			(1) 调用articleService 更新 方法完成修改
		 * 			(2) 保存修改成功信息到request域, 
		 * 5, 转发到msg.jsp页面
		 */
		Article article = CommonUtils.toBean(request.getParameterMap(), Article.class);
		User user = (User) request.getSession().getAttribute("session_user");
		article.setAuthor(user);

		String abstractContent = article.getAbstractContent();
		if (abstractContent == null || abstractContent.equals("")) {
			String acontent = article.getAcontent();
			int endIndex = acontent.length() > 200 ? 200 : acontent.length();
			abstractContent = acontent.substring(0, endIndex);
			article.setAbstractContent(abstractContent);
		}

		if (article.getAid().equals("")) {
			article.setAid(CommonUtils.uuid());
			article.setAtime(new Date());
			articleService.addArticle(article);
			request.setAttribute("msg", "文章发布成功!");
		}
		else {
			articleService.updateArticle(article);
			request.setAttribute("msg", "文章修改成功!");
		}

		return "f:/jsp/msg.jsp";
	}

	/**
	 * 通过页数获取博客功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAllArticlesByPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 1, 获取博客的总记录数和当前页数
		 * 2, 构造分页类
		 * 3, 通过service方法获取需要的博客内容
		 * 4, 保存博客信息和分页信息到request域, 转发到all-article.jsp
		 */
		long countRecord = articleService.findAllArticleCountRecord();
		long curPage = Long.parseLong(request.getParameter("curPage"));
		Pagination pagination = new Pagination(countRecord, curPage);
		List<Article> articleList = articleService.findArticlesHasLimit(
				pagination.getQueryBegin(), pagination.getQueryLength());
		request.setAttribute("articleList", articleList);
		request.setAttribute("pagination", pagination);
		return "f:/jsp/article/all-article.jsp";
	}

	/**
	 * 按aid查询博客即所有回复信息功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findArticleByAid(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 1, 从请求参数中获取aid
		 * 2, 通过service方法查询博客
		 * 3, 保存博客信息Article到request域, 
		 * 4, 按aid查询所有回复信息List<Remrk>, 保存List及其大小到request域,
		 * 
		 * 5, 既然页面将转发到display-article.jsp, 说明文章产生了阅读操作
		 * 6, 所以, 调用readRecordService向阅读记录表中插入一个记录
		 * 7, 但是, 能产生阅读操作的前提, 必须是已登入用户
		 * 
		 * 8, 转发到display-article.jsp
		 */
		String aid = request.getParameter("aid");
		Article article = articleService.findArticleByAid(aid);
		/*
		 * 向阅读记录表中插入记录
		 * 	1, 从session中获取user对象
		 * 	2, 判断user是否为null
		 * 		* 如果为null, 说明用户没有登入, 不会产生阅读操作, 直接转发到display-article.jsp
		 * 		* 如果不为空, 则产生阅读操作, 调用readRecordService向阅读记录表中插入一个记录
		 *  3, 转发到display-article.jsp
		 */
		User user = (User) request.getSession().getAttribute("session_user");
		if (user != null) {
			ReadRecord readRecord = new ReadRecord(user, article);
			boolean success = readRecordService.addReadRecord(readRecord);
			//更新已获取的文章阅读量, 先判断是否添加成功
			if (success) {
				article.setReadNum(article.getReadNum() + 1);
			}
		}
		request.setAttribute("article", article);
		List<Remark> remarkList = remarkService.findRemarksByArticle(article);
		request.setAttribute("remarkList", remarkList);
		request.setAttribute("remarkList_size", remarkList.size());

		return "f:/jsp/article/filter/display-article.jsp";
	}

	/**
	 * 按关键字搜索我的博客功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findMyArticlesByKeyWordAndPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 1, 从session中获取user对象
		 * 2, 从请求参数中获取关键词参数
		 * 3, 将请求参数按空格分割
		 * 4, 根据uid和关键词数组, 调用articleService方法, 查询相关的博客List<Article>
		 * 5, 保存List<Article>到request域中, 保存keyWord到request域, 用于回显
		 * 6, 转发到my-article.jsp
		 */

		long curPage = Long.parseLong(request.getParameter("curPage"));
		User user = (User) request.getSession().getAttribute("session_user");
		String keyWord = request.getParameter("key-word");
		Pagination[] pags = { new Pagination(curPage, curPage) };

		List<Article> articleList = articleService
				.findArticlesByUidAndKeyWordAndPage(user.getUid(), keyWord, pags);
		request.setAttribute("articleList", articleList);
		request.setAttribute("keyWord", keyWord);
		request.setAttribute("pagination", pags[0]);
		return "f:/jsp/article/filter/my-article.jsp";
	}

	/**
	 * 初始化主页功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String initialiseIndexPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*
		 *1, 查询3篇最新文章
		 *2, 保存到request域
		 *3, 转发到main.jsp
		 */
		List<Article> articleList = articleService.findArticlesHasLimit(0, 3);
		request.setAttribute("articleList", articleList);
		return "f:/jsp/main.jsp";
	}

	/**
	 * 显示用户想要编辑的博客信息功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editArticleByAid(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 1, 从请求参数中获取aid
		 * 2, 根据aid, 调用articleService方法获取 article对象
		 * 3, 保存article对象到request域
		 * 4, 转发到write-article.jsp页面
		 */
		String aid = request.getParameter("aid");
		Article article = articleService.findArticleByAid(aid);
		request.setAttribute("article", article);
		return "f:/jsp/article/filter/write-article.jsp";
	}
}
