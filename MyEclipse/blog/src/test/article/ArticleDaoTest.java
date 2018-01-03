package test.article;

import java.util.Date;

import org.junit.Test;

import blog.article.dao.ArticleDao;
import blog.article.domain.Article;
import blog.user.domain.User;
import blog.utils.commons.CommonUtils;

public class ArticleDaoTest {

	@Test
	public void testAdd() {
		ArticleDao articleDao = new ArticleDao();
		int i = 0;
		while (i++ != 10) {
			String aid = CommonUtils.uuid();
			User author = new User("C86D29E2F8C84B9EA3F5EF383FF612D8", aid, aid, aid, aid,
					aid, aid, aid, aid, aid, aid, aid, false, i);			//外键, 表示博客属于谁(博主)
			String atitle = "title" + i;			//文章标题
			String type = "原创";			//文章类型(原创, 转发, 翻译)
			String acontent = "content" + i;		//文章内容
			String classify = "Java";		//文章分类
			String abstractContent = "abstract" + i;	//文章摘要
			Long apraiseNum = 0L;	//点赞数
			Long remarkNum = 0L;	//回复数
			Long readNum = 0L;		//阅读量
			Date atime = new Date();				//发表时间

			Article article = new Article(aid, author, atitle, type, acontent, classify,
					abstractContent, apraiseNum, remarkNum, readNum, atime);
			articleDao.addArticle(article);
		}
	}

	@Test
	public void testFindAll() {
		ArticleDao articleDao = new ArticleDao();
		articleDao.findAllArticleRecords();
	}

}
