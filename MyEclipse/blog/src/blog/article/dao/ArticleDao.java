package blog.article.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

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
		String sql = "insert into article values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = { article.getAid(), article.getAuthor().getUid(),
				article.getAtitle(), article.getType(), article.getAcontent(),
				article.getClassify(), article.getAbstractContent(),
				article.getPraiseNum(), article.getRemarkNum(), article.getReadNum(),
				new Timestamp(article.getAtime().getTime()), article.getVerify() };
		try {
			qr.update(sql, params);
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
				+ "remark_num as remarkNum, read_num as readNum, atime, verify from article "
				+ "where verify != 2 and aid=?";
		try {
			return qr.query(sql, new MapHandler(), aid);
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
	public List<Map<String, Object>> findArticleRecordsHasLimit(long begin, long length) {
		//使用别名处理数据库字段名与JavaBean属性名不一致的情况
		String sql = "select aid, author_uid as uid, atitle, `type`, acontent, classify, "
				+ "abstract as abstractContent, praise_num as praiseNum, "
				+ "remark_num as remarkNum, read_num as readNum, atime, verify from article "
				+ "where verify != 2 order by atime desc limit ?, ?";
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
	 * 获取所有的博客记录数
	 * @return
	 */
	public Long findAllArticleCountRecord() {
		String sql = "select count(*) from article where verify != 2";
		try {
			return qr.query(sql, new ScalarHandler<Long>());
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 按uid获取所有的博客记录数
	 * @return
	 */
	public long findArticleCountRecordByUid(String uid) {
		String sql = "select count(*) from article where verify != 2 and author_uid = ?";
		try {
			return qr.query(sql, new ScalarHandler<Long>(), uid);
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
				+ "classify=?, abstract=?, verify=? where aid=?";
		Object[] params = { article.getAtitle(), article.getType(), article.getAcontent(),
				article.getClassify(), article.getAbstractContent(), article.getVerify(),
				article.getAid() };
		try {
			qr.update(sql, params);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 根据关键词数组匹配博客标题查询博客
	 * @param keyWords
	 * @return
	 */
	public List<Map<String, Object>>
			findArticleRecordsByKeyWordsMatchTitle(String[] keyWords) {
		//生成匹配字符串
		String matchString = generalMatchStringByKeyWords(keyWords);
		//使用别名处理数据库字段名与JavaBean属性名不一致的情况
		String sql = "select aid, author_uid as uid, atitle, `type`, acontent, classify, "
				+ "abstract as abstractContent, praise_num as praiseNum, "
				+ "remark_num as remarkNum, read_num as readNum, atime, verify from article "
				+ "where verify != 2 and atitle like ? order by atime desc";
		try {
			return qr.query(sql, new MapListHandler(), matchString);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 生成匹配字符串
	 * @param length
	 * @return
	 */
	private String generalMatchStringByKeyWords(String[] keyWords) {
		String matchString = "%";
		for (int i = 0; i < keyWords.length; i++) {
			matchString += (keyWords[i] + "%");
		}
		return matchString;
	}

	/**
	 * 根据关键词数组匹配博客分类查询博客
	 * @param keyWords
	 * @return
	 */
	public List<Map<String, Object>>
			findArticleRecordsByKeyWordsMatchClassify(String[] keyWords) {
		//生成匹配字符串
		String matchString = generalMatchStringByKeyWords(keyWords);
		//使用别名处理数据库字段名与JavaBean属性名不一致的情况
		String sql = "select aid, author_uid as uid, atitle, `type`, acontent, classify, "
				+ "abstract as abstractContent, praise_num as praiseNum, "
				+ "remark_num as remarkNum, read_num as readNum, atime, verify from article "
				+ "where verify != 2 and classify like ? order by atime desc";
		try {
			return qr.query(sql, new MapListHandler(), matchString);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据关键词数组匹配博客摘要查询博客
	 * @param keyWords
	 * @return
	 */
	public List<Map<String, Object>>
			findArticleRecordsByKeyWordsMatchAbstract(String[] keyWords) {
		//生成匹配字符串
		String matchString = generalMatchStringByKeyWords(keyWords);
		//使用别名处理数据库字段名与JavaBean属性名不一致的情况
		String sql = "select aid, author_uid as uid, atitle, `type`, acontent, classify, "
				+ "abstract as abstractContent, praise_num as praiseNum, "
				+ "remark_num as remarkNum, read_num as readNum, atime, verify from article "
				+ "where verify != 2 and abstract like ? order by atime desc";
		try {
			return qr.query(sql, new MapListHandler(), matchString);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据关键词数组匹配博客内容查询博客
	 * @param keyWords
	 * @return
	 */
	public List<Map<String, Object>>
			findArticleRecordsByKeyWordsMatchContent(String[] keyWords) {
		//生成匹配字符串
		String matchString = generalMatchStringByKeyWords(keyWords);
		//使用别名处理数据库字段名与JavaBean属性名不一致的情况
		String sql = "select aid, author_uid as uid, atitle, `type`, acontent, classify, "
				+ "abstract as abstractContent, praise_num as praiseNum, "
				+ "remark_num as remarkNum, read_num as readNum, atime, verify from article "
				+ "where verify != 2 and acontent like ? order by atime desc";
		try {
			return qr.query(sql, new MapListHandler(), matchString);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据uid和关键词数组匹配博客标题查询博客
	 * @param keyWords
	 * @return
	 */
	public List<Map<String, Object>>
			findArticleRecordsByUidAndKeyWordsMatchTitle(String uid, String[] keyWords) {
		//生成匹配字符串
		String matchString = generalMatchStringByKeyWords(keyWords);
		//使用别名处理数据库字段名与JavaBean属性名不一致的情况
		String sql = "select aid, author_uid as uid, atitle, `type`, acontent, classify, "
				+ "abstract as abstractContent, praise_num as praiseNum, "
				+ "remark_num as remarkNum, read_num as readNum, atime, verify from article "
				+ "where verify != 2 and author_uid=? and atitle like ? order by atime desc";
		try {
			return qr.query(sql, new MapListHandler(), uid, matchString);
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
	 * @param length 
	 * @param begin 
	 * @return
	 */
	public List<Map<String, Object>> findArticleRecordsByUidHasLimit(String uid,
			long begin, long length) {
		//使用别名处理数据库字段名与JavaBean属性名不一致的情况
		String sql = "select aid, author_uid as uid, atitle, `type`, acontent, classify, "
				+ "abstract as abstractContent, praise_num as praiseNum, "
				+ "remark_num as remarkNum, read_num as readNum, atime, verify from article "
				+ "where verify != 2 and author_uid=? order by atime desc limit ?, ?";
		try {
			return qr.query(sql, new MapListHandler(), uid, begin, length);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据uid和关键词数组匹配博客分类查询博客
	 * @param keyWords
	 * @return
	 */
	public List<Map<String, Object>> findArticleRecordsByUidAndKeyWordsMatchClassify(
			String uid, String[] keyWords) {
		//生成匹配字符串
		String matchString = generalMatchStringByKeyWords(keyWords);
		//使用别名处理数据库字段名与JavaBean属性名不一致的情况
		String sql = "select aid, author_uid as uid, atitle, `type`, acontent, classify, "
				+ "abstract as abstractContent, praise_num as praiseNum, "
				+ "remark_num as remarkNum, read_num as readNum, atime, verify from article "
				+ "where verify != 2 and author_uid=? and classify like ? order by atime desc";
		try {
			return qr.query(sql, new MapListHandler(), uid, matchString);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据uid和关键词数组匹配博客摘要查询博客
	 * @param keyWords
	 * @return
	 */
	public List<Map<String, Object>> findArticleRecordsByUidAndKeyWordsMatchAbstract(
			String uid, String[] keyWords) {
		//生成匹配字符串
		String matchString = generalMatchStringByKeyWords(keyWords);
		//使用别名处理数据库字段名与JavaBean属性名不一致的情况
		String sql = "select aid, author_uid as uid, atitle, `type`, acontent, classify, "
				+ "abstract as abstractContent, praise_num as praiseNum, "
				+ "remark_num as remarkNum, read_num as readNum, atime, verify from article "
				+ "where verify != 2 and author_uid=? and abstract like ? order by atime desc";
		try {
			return qr.query(sql, new MapListHandler(), uid, matchString);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据uid和关键词数组匹配博客内容查询博客
	 * @param keyWords
	 * @return
	 */
	public List<Map<String, Object>> findArticleRecordsByUidAndKeyWordsMatchContent(
			String uid, String[] keyWords) {
		//生成匹配字符串
		String matchString = generalMatchStringByKeyWords(keyWords);
		//使用别名处理数据库字段名与JavaBean属性名不一致的情况
		String sql = "select aid, author_uid as uid, atitle, `type`, acontent, classify, "
				+ "abstract as abstractContent, praise_num as praiseNum, "
				+ "remark_num as remarkNum, read_num as readNum, atime, verify from article "
				+ "where verify != 2 and author_uid=? and acontent like ? order by atime desc";
		try {
			return qr.query(sql, new MapListHandler(), uid, matchString);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查找未审核博客
	 */
	public List<Map<String, Object>> findNotVerifyArticleRecords() {
		//使用别名处理数据库字段名与JavaBean属性名不一致的情况
		String sql = "select aid, author_uid as uid, atitle, `type`, acontent, classify, "
				+ "abstract as abstractContent, praise_num as praiseNum, "
				+ "remark_num as remarkNum, read_num as readNum, atime, verify from article "
				+ "where verify = 0";
		try {
			return qr.query(sql, new MapListHandler());
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
