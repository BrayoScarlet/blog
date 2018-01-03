package blog.remark.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import blog.remark.domain.Remark;
import blog.utils.jdbc.JdbcUtils;
import blog.utils.jdbc.TxQueryRunner;

public class RemarkDao {
	private QueryRunner qr = new TxQueryRunner(JdbcUtils.getDataSource(), true);

	/**
	 * 向 回复表 中插入回复记录
	 * @param remark  
	 */
	public void addRemark(Remark remark) {
		String sql = "insert into remark values(?,?,?,?,?)";
		Object[] params = { remark.getRid(), remark.getAuthor().getUid(),
				remark.getArticle().getAid(), remark.getRcontent(),
				new Timestamp(remark.getRtime().getTime()) };
		try {
			qr.update(sql, params);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 按aid查询回复记录
	 * 将每一条记录放到Map中,
	 * 将所有的Map放到List中,
	 * 返回List<Map>
	 * @param articleAid
	 * @return
	 */
	public List<Map<String, Object>> findRemarkRecordsByAid(String articleAid) {
		//使用别名处理数据库字段名与JavaBean属性名不一致的情况
		String sql = "select rid, author_uid as uid, article_returned_aid as aid, "
				+ "rcontent, rtime from remark "
				+ "where article_returned_aid=? order by rtime desc";
		try {
			return qr.query(sql, new MapListHandler(), articleAid);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 按rid从remark表中查询aid
	 * @param rid 
	 * @return
	 */
	public String findAidByRid(String rid) {
		String sql = "select article_returned_aid as aid from remark where rid=?";
		try {
			return qr.query(sql, new ScalarHandler<String>(), rid);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据rid删除对应的记录
	 * @param rid
	 */
	public void deleteRemarkByRid(String rid) {
		String sql = "delete from remark where rid=?";
		try {
			qr.update(sql, rid);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据aid删除remark中的记录
	 * @param aid
	 */
	public void deleteRemarksByAid(String aid) {
		String sql = "delete from remark where article_returned_aid = ?";
		try {
			qr.update(sql, aid);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
