package test.article;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import blog.article.dao.ArticleDao;
import blog.article.domain.Article;
import blog.user.domain.User;
import blog.utils.commons.CommonUtils;

public class ArticleDaoTest {
	private ArticleDao articleDao = new ArticleDao();

	@Test
	public void testAdd() {
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
			int verify = 0;

			Article article = new Article(aid, author, atitle, type, acontent, classify,
					abstractContent, apraiseNum, remarkNum, readNum, atime, verify);
			articleDao.addArticle(article);
		}
	}

	@Test
	public void testMapEquals() {
		String[] keyWords1 = { "张三", "博客" };
		String[] keyWords4 = { "算法" };
		List<Map<String, Object>> listMap =
				articleDao.findArticleRecordsByKeyWordsMatchTitle(keyWords1);
		Map<String, Object> map =
				articleDao.findArticleRecordsByKeyWordsMatchClassify(keyWords4).get(0);
		System.out.println(listMap.contains(map));
	}

	@Test
	public void testFindArticleRecordsByUidAndKeyWordsMatchTitle() {
		String[] keyWords = { "张三", "博客", "2" };
		String[] keyWords2 = { "" };
		String uid = "601D6287BFC041CD8E36D7C7FDDCD8D1";
		List<Map<String, Object>> listMap =
				articleDao.findArticleRecordsByUidAndKeyWordsMatchTitle(uid, keyWords2);
		for (Map<String, Object> map : listMap) {
			System.out.println(map);
		}

	}
}
