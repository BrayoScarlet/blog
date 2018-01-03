package blog.read_record.service;

import java.sql.SQLException;
import java.util.Map;

import blog.article.dao.ArticleDao;
import blog.read_record.dao.ReadRecordDao;
import blog.read_record.domain.ReadRecord;
import blog.utils.jdbc.JdbcUtils;

public class ReadRecordService {
	private ReadRecordDao readRecordDao = new ReadRecordDao();
	private ArticleDao articleDao = new ArticleDao();

	/**
	 * 插入阅读记录业务, 返回是否添加成功
	 * @param readRecord
	 */
	public boolean addReadRecord(ReadRecord readRecord) {
		/*
		 * 由于插入阅读记录要操作两张表, 所以需要开启事务
		 * 1, 查询阅读记录表中是否已存在即将插入的记录,
		 * 		* 若存在, 则直接返回
		 * 		* 否则, 向下执行
		 * 2, 向阅读记录表中插入一条记录
		 * 3, 更新对应文章记录中readNum的值自增1
		 * 
		 * 2,3只要有一个出现错误, 就会回滚事务
		 * (为了避免有人使用脚本来刷阅读量, 所以每个人对每篇文章只有增加一次阅读记录的权利)
		 */

		Map<String, Object> map = readRecordDao.findReadRecordMapByReadRecord(readRecord);
		if (map != null) {
			return false;
		}

		try {
			//开启事务
			JdbcUtils.beginTransaction();

			readRecordDao.addReadRecord(readRecord);	//插入阅读记录
			//更新对应文章记录中readNum的值自增1
			articleDao.updateReadNumByAid(readRecord.getArticle().getAid());

			//提交事务
			JdbcUtils.commitTransaction();
			return true;
		}
		catch (SQLException e1) {
			try {
				//回滚事务
				JdbcUtils.rollbackTransaction();
			}
			catch (SQLException e2) {
				throw new RuntimeException(e2);
			}
			throw new RuntimeException(e1);
		}
	}
}
