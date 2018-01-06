package yang.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import yang.model.FeedBackInfo;

@Repository
public class FeedBackInfoDao {
	@Autowired
	private JdbcTemplate jdbctemplate=null;
	//根据对象类生产or mappring映射器
	private BeanPropertyRowMapper mapper=BeanPropertyRowMapper.newInstance(FeedBackInfo.class);
	
	//查看所有的反馈信息
	public FeedBackInfo[] findAllFeedBackInfos() {
		String sql = "select * from FeedBackInfo";
		List<FeedBackInfo> feeds = jdbctemplate.query(sql, mapper);
		
		return feeds.toArray(new FeedBackInfo[0]);
	}
	
	//新增一条反馈信息
	public boolean insertFeedBackInfo(FeedBackInfo feed) {
		String sql = "insert into FeedBackInfo(fbId ,fbTitle ,fbContent , "
				+ "fbContact ,userId ,fbTime) values(?,?,?,?,?,?)";
		int count  = jdbctemplate.update(sql,feed.getFbId() , feed.getFbTitle() ,
				feed.getFbContent(),feed.getFbContact(),feed.getUserId(), feed.getFbTime());
		return count>0?true:false;
	}
	
	//删除指定id的反馈信息
	public boolean deletefeedById(String fbId) {
		String sql = "delete from FeedBackInfo where fbId =?";
        Object[] paramValues = {fbId};
		int count = jdbctemplate.update(sql,paramValues );
		return count>0?true:false;
	}
	
}
