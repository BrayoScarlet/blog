package blog.praise_record.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blog.article.domain.Article;
import blog.article.service.ArticleService;
import blog.praise_record.domain.PraiseRecord;
import blog.praise_record.service.PraiseRecordService;
import blog.remark.domain.Remark;
import blog.remark.service.RemarkService;
import blog.user.domain.User;
import blog.utils.servlet.BaseServlet;

@WebServlet("/PraiseRecordServlet")
public class PraiseRecordServlet extends BaseServlet {
	private PraiseRecordService praiseRecordService = new PraiseRecordService();
	private ArticleService articleService = new ArticleService();
	private RemarkService remarkService = new RemarkService();

	/**
	 * 添加点赞记录功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addPraiseRecord(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 1, 从请求参数中获取aid
		 * 2, 根据aid, 调用articleService方法查询出article对象
		 * 3, 从session中获取user对象(能产生点赞操作的前提, 必须是已登入用户)
		 * 4, 判断user是否为null
		 * 		* 如果为null, 说明用户没有登入, 不会产生点赞操作, 
		 * 		* 如果不为空, 则产生点赞操作, 调用praiseRecordService向点赞记录表中插入一个记录
		 * 			(1)用article和user创建PraiseRecord对象
		 * 			(2)调用praiseService方法添加点赞记录
		 * 
		 * 保存博客信息及所有回复信息用于display-article.jsp页面显示
		 * 5, 保存article信息到request域 
		 * 6, 根据article_aid查询所有回复信息List<Reamrk>(按时间从近到远排序)
		 * 7, 转发到display-article.jsp
		 */
		String aid = request.getParameter("aid");
		Article praiseArticle = articleService.findArticleByAid(aid);
		User praiseUser = (User) request.getSession().getAttribute("session_user");
		if (praiseUser != null) {
			PraiseRecord praiseRecord = new PraiseRecord(praiseUser, praiseArticle);
			boolean success = praiseRecordService.addPraiseRecord(praiseRecord);
			//更新已获取的博客的点赞量, 先判断是否添加成功
			if (success) {
				praiseArticle.setPraiseNum(praiseArticle.getPraiseNum() + 1);
			}
		}

		request.setAttribute("article", praiseArticle);
		List<Remark> remarkList = remarkService.findRemarksByArticle(praiseArticle);
		request.setAttribute("remarkList", remarkList);
		request.setAttribute("remarkList_size", remarkList.size());

		return "f:/jsp/article/filter/display-article.jsp";
	}

}
