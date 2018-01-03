package test.remark;

import java.util.Date;

import org.junit.Test;

import blog.article.domain.Article;
import blog.remark.dao.RemarkDao;
import blog.remark.domain.Remark;
import blog.user.domain.User;
import blog.utils.commons.CommonUtils;

public class RemarkDaoTest {

	@Test
	public void testAdd() {
		RemarkDao remarkDao = new RemarkDao();
		for (int i = 1; i < 11; i++) {
			String rid = CommonUtils.uuid();
			User author = new User("C86D29E2F8C84B9EA3F5EF383FF612D8", rid, rid, rid, rid,
					rid, rid, rid, rid, rid, rid, rid, false, i);
			Article article = new Article("111ADC3E36A74516A340E1E30B2C5D9D", author, rid,
					rid, rid, rid, rid, null, null, null, null);
			String rcontent = "rcontent" + i;
			Date rtime = new Date(1514014996000L);
			Remark remark = new Remark(rid, author, article, rcontent, rtime);
			remarkDao.addRemark(remark);
		}
	}

	@Test
	public void fun1() {
		//System.out.println(System.currentTimeMillis());
		System.out.println(new Date(1514014996000L));
	}

}
