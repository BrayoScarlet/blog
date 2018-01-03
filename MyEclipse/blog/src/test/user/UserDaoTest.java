package test.user;

import org.junit.Test;

import blog.user.dao.UserDao;
import blog.user.domain.User;

public class UserDaoTest {

	@Test
	public void test() {
		User user = new UserDao().findByUsername("zhangSan");
		System.out.println(user);
	}
	/*
	 * User [uid=00FB3F2662454FCEA1C2A73BA316162D, email=825580813@qq.com, username=zhangSan, password=9fab6755cd2e8817d3e73b0978ca54a6, school=阿萨德发asdfa, qualification=硕士, graduationYear=null, specialisations=花费大概asdfa, sex=女, ubrief=hgedhgr, domicile=官方态度asdf, token=3E5428C2200245258D374CAABFC2E266692ACE9874364DE59141A1AEA5D86F91, state=true, admin=0]
	   User [uid=00FB3F2662454FCEA1C2A73BA316162D, email=825580813@qq.com, username=zhangSan, password=9fab6755cd2e8817d3e73b0978ca54a6, school=阿萨德发asdfa, qualification=硕士, graduationYear=2019, specialisations=花费大概asdfa, sex=女, ubrief=hgedhgr, domicile=官方态度asdf, token=3E5428C2200245258D374CAABFC2E266692ACE9874364DE59141A1AEA5D86F91, state=true, admin=0]
	
	 */

}
