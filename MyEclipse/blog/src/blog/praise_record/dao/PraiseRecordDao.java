package blog.praise_record.dao;

import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;

import blog.praise_record.domain.PraiseRecord;
import blog.utils.jdbc.JdbcUtils;
import blog.utils.jdbc.TxQueryRunner;

public class PraiseRecordDao {
	private QueryRunner qr = new TxQueryRunner(JdbcUtils.getDataSource(), true);

	/**
	 * 向praise_record表中添加记录
	 * @param praiseRecord
	 */
	public void addPraiseRecord(PraiseRecord praiseRecord) {
		String sql = "insert into praise_record values(?,?)";
		Object[] params = { praiseRecord.getPraiseUser().getUid(),
				praiseRecord.getPraiseArticle().getAid() };
		try {
			qr.update(sql, params);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 按praiseRecord查询阅读记录
	 * 将记录放到Map中,
	 * 返回Map
	 * @param praiseRecord
	 * @return
	 */
	public Map<String, Object>
			findPraiseRecordMapByPraiseRecord(PraiseRecord praiseRecord) {
		String sql = "select * from praise_record where praise_uid=? and praise_aid=?";
		Object[] params = { praiseRecord.getPraiseUser().getUid(),
				praiseRecord.getPraiseArticle().getAid() };
		try {
			return qr.query(sql, new MapHandler(), params);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据aid删除praise_record中的记录
	 * @param aid
	 */
	public void deletePraiseRecordsByAid(String aid) {
		String sql = "delete from praise_record where praise_aid = ?";
		try {
			qr.update(sql, aid);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
