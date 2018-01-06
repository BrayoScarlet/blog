package yang.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import blog.user.domain.User;
import yang.model.MemberInfo;
import yang.model.StuGroupInfo;

@Repository
public class MemberInfoDao {
	@Autowired
	private JdbcTemplate jdbctemplate = null;
	//根据对象类生产or mappring映射器
	private BeanPropertyRowMapper mapper =
			BeanPropertyRowMapper.newInstance(MemberInfo.class);

	//内部类构成映射器
	public class MemberInfoMapper implements RowMapper<MemberInfo> {
		public MemberInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			MemberInfo memberInfo = new MemberInfo();
			StuGroupInfo group = new StuGroupInfo();
			group.setStugroupId(rs.getString("stugroupId"));

			User user = new User();
			user.setUid(rs.getString("userId"));

			memberInfo.setStugroup(group);
			memberInfo.setUser(user);
			return memberInfo;
		}
	}

	//查询所有的学习小组和其中成员
	//暂时没有用到
	public MemberInfo[] findAllMemebers() {
		String sql = "select * from MemberInfo";
		List<MemberInfo> members = jdbctemplate.query(sql, mapper);

		System.out.println("MemberInfoDao works");
		for (MemberInfo index : members) {
			System.out.println("stugName:" + index.getStugroup().getStugroupName()
					+ " userName:" + index.getUser().getUsername());
		}
		return members.toArray(new MemberInfo[0]);
	}

	//判断小组是否包含特定用户
	public boolean isofMember(String stugroupId, User user) {
		String sql = "select * from MemberInfo where stugroupId=?";
		Object[] paramValues = { stugroupId };
		List<MemberInfo> members =
				jdbctemplate.query(sql, paramValues, new MemberInfoMapper());

		for (MemberInfo index : members) {
			//该小组包含这个用户
			if (index.getUser().getUid().equals(user.getUid())) {
				System.out.println("ture");
				System.out.println("uid:" + user.getUid());
				return true;
			}
		}
		return false;
	}

	//插入一条信息
	public boolean insertMemberInfo(StuGroupInfo group, User user) {
		String sql = "insert into MemberInfo(stugroupId , userId) values(?,?)";
		int count = jdbctemplate.update(sql, group.getStugroupId(), user.getUid());
		return count > 0 ? true : false;
	}
}
