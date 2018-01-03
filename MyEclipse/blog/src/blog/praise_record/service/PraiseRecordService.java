package blog.praise_record.service;

import java.util.Map;

import blog.praise_record.dao.PraiseRecordDao;
import blog.praise_record.domain.PraiseRecord;

public class PraiseRecordService {
	private PraiseRecordDao praiseRecordDao = new PraiseRecordDao();

	/**
	 * 添加praiseRecord功能, 返回是否添加成功
	 * @param praiseRecord
	 */
	public boolean addPraiseRecord(PraiseRecord praiseRecord) {
		/*
		 * 1, 查询点赞记录表中是否已存在即将插入的记录,
		 * 		* 若存在, 则直接返回
		 * 		* 否则, 向下执行
		 * 2, 向点赞记录表中插入一条记录
		 * 3, 数据库中的触发器会自动更新对应文章记录中praiseNum的值自增1
		 * 
		 * (为了避免有人使用脚本来刷阅读量, 所以每个人对每篇文章只有增加一次阅读记录的权利)
		 */
		Map<String, Object> map =
				praiseRecordDao.findPraiseRecordMapByPraiseRecord(praiseRecord);
		if (map != null) {
			return false;
		}
		praiseRecordDao.addPraiseRecord(praiseRecord);
		return true;
	}
}
