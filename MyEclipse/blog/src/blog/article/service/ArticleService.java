package blog.article.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import blog.article.dao.ArticleDao;
import blog.article.domain.Article;
import blog.praise_record.dao.PraiseRecordDao;
import blog.read_record.dao.ReadRecordDao;
import blog.remark.dao.RemarkDao;
import blog.user.dao.UserDao;
import blog.user.domain.User;
import blog.utils.commons.CommonUtils;
import blog.utils.jdbc.JdbcUtils;

public class ArticleService {
	private ArticleDao articleDao = new ArticleDao();
	private UserDao userDao = new UserDao();
	private PraiseRecordDao praiseRecordDao = new PraiseRecordDao();
	private RemarkDao remarkDao = new RemarkDao();
	private ReadRecordDao readRecordDao = new ReadRecordDao();

	/**
	 * 添加文章业务
	 * @param article
	 */
	public void addArticle(Article article) {
		articleDao.addArticle(article);
	}

	/**
	 * 获取所有文章业务
	 * @return
	 */
	public List<Article> findAllArticles() {
		/*
		 * 1, 通过articleDao方法获取所有的文章记录, 并封装到List<Map>里返回
		 * 2, 通过CommonUtils.toBean方法, 构造Article对象
		 * 3, 根据map里的uid, 通过userDao方法, 获取对应的user对象
		 * 4, 将user对象赋给对应的article对象
		 * 5, 将所有的article对象放到list容器中, 返回
		 */
		List<Article> articleList = new ArrayList<Article>();
		List<Map<String, Object>> listMap = articleDao.findAllArticleRecords();
		for (Map<String, Object> eleMap : listMap) {
			Article article = CommonUtils.toBean(eleMap, Article.class);
			User author = userDao.findByUid(eleMap.get("uid") + "");
			article.setAuthor(author);
			articleList.add(article);
		}
		return articleList;
	}

	/**
	 * 按aid查询博客业务
	 * @param aid
	 * @return
	 */
	public Article findArticleByAid(String aid) {
		/*
		 * 1, 按aid通过articleDao方法查询出博客记录Map
		 * 2, 通过CommonUtils.toBean方法, 构造Article对象
		 * 3, 根据map里的uid, 通过userDao方法, 获取对应的user对象
		 * 4, 将user对象赋给article对象
		 * 5, 返回article对象
		 */
		Map<String, Object> map = articleDao.findArticleRecordByAid(aid);
		Article article = CommonUtils.toBean(map, Article.class);
		User author = userDao.findByUid(map.get("uid") + "");
		article.setAuthor(author);
		return article;
	}

	/**
	 * 按uid查询博客的业务
	 * @param uid
	 * @return
	 */
	public List<Article> findArticlesByUid(String uid) {
		/*
		 * 1, 按uid通过articleDao方法查询出所有的博客记录Map
		 * 2, 通过CommonUtils.toBean方法, 构造Article对象
		 * 3, 根据uid, 通过userDao方法, 获取对应的user对象
		 * 4, 将user对象赋给article对象
		 * 5, 将所有的article对象放到list容器中, 返回
		 */
		List<Map<String, Object>> listMap = articleDao.findArticleRecordsByUid(uid);
		List<Article> articleList = new ArrayList<Article>();
		for (Map<String, Object> eleMap : listMap) {
			Article article = CommonUtils.toBean(eleMap, Article.class);
			User author = userDao.findByUid(uid);
			article.setAuthor(author);
			articleList.add(article);
		}
		return articleList;
	}

	/**
	 * 按限制查询博客业务
	 * @return
	 */
	public List<Article> findArticlesHasLimit(int begin, int length) {
		List<Map<String, Object>> listMap =
				articleDao.findArticleRecordsHasLimit(begin, length);
		List<Article> articleList = new ArrayList<Article>();
		for (Map<String, Object> eleMap : listMap) {
			Article article = CommonUtils.toBean(eleMap, Article.class);
			User author = userDao.findByUid(eleMap.get("uid") + "");
			article.setAuthor(author);
			articleList.add(article);
		}
		return articleList;
	}

	/**
	 * 删除博客业务
	 * @param aid
	 */
	public void deleteArticleByAid(String aid) {
		/*
		 * 由于article表受到praise_record, remark, read_record表的外键约束,
		 * 所以应该先删除praise_record, remark, read_record表中对应的记录, 在删除article的记录
		 * 因此需要用到事务.
		 * 1, 根据aid删除praise_record中的记录
		 * 2, 根据aid删除remark中的记录
		 * 3, 根据aid删除read_record中的记录
		 * 4, 根据aid删除article中的记录
		 */
		try {
			JdbcUtils.beginTransaction();	//开启事务

			praiseRecordDao.deletePraiseRecordsByAid(aid);
			remarkDao.deleteRemarksByAid(aid);
			readRecordDao.deleteReadRecordsByAid(aid);

			articleDao.deleteArticleByAid(aid);

			JdbcUtils.commitTransaction();	//提交事务
		}
		catch (SQLException e1) {
			try {
				JdbcUtils.rollbackTransaction();	//回滚事务
			}
			catch (SQLException e2) {
				throw new RuntimeException(e2);
			}
			throw new RuntimeException(e1);
		}
	}

	/**
	 * 修改博客记录业务
	 * @param article
	 */
	public void updateArticle(Article article) {
		articleDao.updateArticle(article);
	}

}
