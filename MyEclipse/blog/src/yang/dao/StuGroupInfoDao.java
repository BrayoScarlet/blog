package yang.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import yang.model.StuGroupInfo;

@Repository
public class StuGroupInfoDao {
	@Autowired
	private JdbcTemplate jdbctemplate;
	//根据对象类生产or mappring映射器
	private BeanPropertyRowMapper mapper =
			BeanPropertyRowMapper.newInstance(StuGroupInfo.class);

	//查看所有的学习小组
	public StuGroupInfo[] findAllGroups() {
		String sql = "select * from StuGroupInfo";
		List<StuGroupInfo> groups = jdbctemplate.query(sql, mapper);

		/*System.out.println("StuGroupInfoDao works");
		for(StuGroupInfo index:groups) {
			System.out.println("stugId:"+index.getStugroupId()+" 名字："
						+index.getStugroupName());
		}*/
		return groups.toArray(new StuGroupInfo[0]);
	}

	//根据Id来查看学习小组
	public StuGroupInfo findGroupById(String stugroupId) {
		//定义查询参数
		Object[] paramValues = { stugroupId };
		//查询
		String sql = "select * from StuGroupInfo where stugroupId=?";
		List<StuGroupInfo> objs = jdbctemplate.query(sql, paramValues, mapper);
		if (objs.size() >= 1) return objs.get(0);
		else return null;
	}

	//新增一个学习小组
	public boolean insertGroup(StuGroupInfo group) {
		String sql = "insert into StuGroupInfo(stugroupId, memberNum ,stugroupName ,"
				+ "stugroupIntro, leadId, qqNumber,buildDate ,imageUrl) values(?,?,?,?,?,?,?,?)";
		int count = jdbctemplate.update(sql, group.getStugroupId(), group.getMemberNum(),
				group.getStugroupName(), group.getStugroupIntro(), group.getLeadId(),
				group.getQqNumber(), group.getBuildDate(), group.getImageUrl());
		return count > 0 ? true : false;
	}

	//删除学习小组
	public boolean deleteGroupById(String stugroupId) {
		String sql = "delete from StuGroupInfo where stugroupId =?";
		int count = jdbctemplate.update(sql, stugroupId);
		return count > 0 ? true : false;
	}

	//根据名字模糊查询小组信息
	/*
	 * 	public StuGroupInfo[] searchGroupByName(String stugroupname) {
		String sql = "select * from StuGroupInfo where stugroupName like ?";
		String name = "%"+stugroupname+"%";
		Object[] paramValues = {name};
		List<StuGroupInfo> objs=jdbctemplate.query(sql, paramValues,mapper);
		return objs.toArray(new StuGroupInfo[0]);
	}
	 * */

	//根据关键词模糊查询小组名字和简介
	public StuGroupInfo[] searchGroupByKey(String keywords) {
		String sql =
				"select * from StuGroupInfo where stugroupName like ? or stugroupIntro like ?";
		String name = "%" + keywords + "%";
		String intro = "%" + keywords + "%";
		Object[] paramValues = { name, intro };
		List<StuGroupInfo> objs = jdbctemplate.query(sql, paramValues, mapper);
		return objs.toArray(new StuGroupInfo[0]);
	}

	//当有用户加入小组时，人数加1
	public boolean plusMemberNum(String stugroupId) {
		//根据id找到该记录
		StuGroupInfo group = findGroupById(stugroupId);
		int num = group.getMemberNum();
		String sql = "update StuGroupInfo set memberNum =? where stugroupId =?";
		num += 1;
		Object[] paramValues = { num, stugroupId };
		int count = jdbctemplate.update(sql, paramValues);
		return count > 0 ? true : false;
	}
}
