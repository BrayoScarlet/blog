package blog.article.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import blog.article.domain.Article;
import blog.utils.jdbc.JdbcUtils;
import blog.utils.jdbc.TxQueryRunner;

public class ArticleDao {
	private QueryRunner qr = new TxQueryRunner(JdbcUtils.getDataSource(), true);

	/**
	 * 向article表中新增记录
	 * @param article
	 */
	public void addArticle(Article article) {
		String sql = "insert into article values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = { article.getAid(), article.getAuthor().getUid(),
				article.getAtitle(), article.getType(), article.getAcontent(),
				article.getClassify(), article.getAbstractContent(),
				article.getPraiseNum(), article.getRemarkNum(), article.getReadNum(),
				new Timestamp(article.getAtime().getTime()) };
		try {
			qr.update(sql, params);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查询所有博客记录, 
	 * 将每一条记录放到Map中,
	 * 将所有的Map放到List中,
	 * 返回List<Map>
	 * @return
	 */
	public List<Map<String, Object>> findAllArticleRecords() {
		//使用别名处理数据库字段名与JavaBean属性名不一致的情况
		String sql = "select aid, author_uid as uid, atitle, `type`, acontent, classify, "
				+ "abstract as abstractContent, praise_num as praiseNum, "
				+ "remark_num as remarkNum, read_num as readNum, atime from article "
				+ "order by atime desc";
		try {
			return qr.query(sql, new MapListHandler());
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 按aid查询博客记录
	 * 将记录放到Map中,
	 * 返回Map
	 * @param aid
	 * @return
	 */
	public Map<String, Object> findArticleRecordByAid(String aid) {
		//使用别名处理数据库字段名与JavaBean属性名不一致的情况
		String sql = "select aid, author_uid as uid, atitle, `type`, acontent, classify, "
				+ "abstract as abstractContent, praise_num as praiseNum, "
				+ "remark_num as remarkNum, read_num as readNum, atime from article "
				+ "where aid=?";
		try {
			return qr.query(sql, new MapHandler(), aid);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 按uid查询所有博客记录, 
	 * 将每一条记录放到Map中,
	 * 将所有的Map放到List中,
	 * 返回List<Map>
	 * @return
	 */
	public List<Map<String, Object>> findArticleRecordsByUid(String uid) {
		//使用别名处理数据库字段名与JavaBean属性名不一致的情况
		String sql = "select aid, author_uid as uid, atitle, `type`, acontent, classify, "
				+ "abstract as abstractContent, praise_num as praiseNum, "
				+ "remark_num as remarkNum, read_num as readNum, atime from article "
				+ "where author_uid=? order by atime desc";
		try {
			return qr.query(sql, new MapListHandler(), uid);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 按限制查询文章记录,
	 * 将每一条记录放到Map中,
	 * 将所有的Map放到List中,
	 * 返回List<Map>
	 * @param begin
	 * @param length
	 * @return
	 */
	public List<Map<String, Object>> findArticleRecordsHasLimit(int begin, int length) {
		//使用别名处理数据库字段名与JavaBean属性名不一致的情况
		String sql = "select aid, author_uid as uid, atitle, `type`, acontent, classify, "
				+ "abstract as abstractContent, praise_num as praiseNum, "
				+ "remark_num as remarkNum, read_num as readNum, atime from article "
				+ "order by atime desc limit ?, ?";
		try {
			return qr.query(sql, new MapListHandler(), begin, length);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据aid更新文章记录中readNum的值自增1
	 * @param aid
	 */
	public void updateReadNumByAid(String aid) {
		String sql = "update article set read_num = read_num + 1 where aid = ?";
		try {
			qr.update(sql, aid);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据aid删除article中的记录
	 * @param aid
	 */
	public void deleteArticleByAid(String aid) {
		String sql = "delete from article where aid = ?";
		try {
			qr.update(sql, aid);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据article对象更新article表中的记录
	 * @param article
	 */
	public void updateArticle(Article article) {
		String sql = "update article set atitle=?, `type`=?, acontent=?, "
				+ "classify=?, abstract=? where aid=?";
		Object[] params = { article.getAtitle(), article.getType(), article.getAcontent(),
				article.getClassify(), article.getAbstractContent(), article.getAid() };
		try {
			qr.update(sql, params);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
}
