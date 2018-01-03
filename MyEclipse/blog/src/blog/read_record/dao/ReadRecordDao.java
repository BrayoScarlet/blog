package blog.read_record.dao;

import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;

import blog.read_record.domain.ReadRecord;
import blog.utils.jdbc.JdbcUtils;
import blog.utils.jdbc.TxQueryRunner;

public class ReadRecordDao {
	private QueryRunner qr = new TxQueryRunner(JdbcUtils.getDataSource(), true);

	/**
	 * 向read_record表中插入记录
	 * @param readRecord
	 */
	public void addReadRecord(ReadRecord readRecord) {
		String sql = "insert into read_record values(?,?)";
		Object[] params =
				{ readRecord.getReader().getUid(), readRecord.getArticle().getAid() };
		try {
			qr.update(sql, params);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 按readRecord查询阅读记录
	 * 将记录放到Map中,
	 * 返回Map
	 * @param readRecord
	 * @return
	 */
	public Map<String, Object> findReadRecordMapByReadRecord(ReadRecord readRecord) {
		String sql = "select * from read_record where read_uid=? and read_aid=?";
		Object[] params =
				{ readRecord.getReader().getUid(), readRecord.getArticle().getAid() };
		try {
			return qr.query(sql, new MapHandler(), params);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据aid删除read_record中的记录
	 * @param aid
	 */
	public void deleteReadRecordsByAid(String aid) {
		String sql = "delete from read_record where read_aid = ?";
		try {
			qr.update(sql, aid);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
