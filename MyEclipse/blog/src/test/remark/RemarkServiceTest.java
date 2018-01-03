package test.remark;

import java.util.Date;

import org.junit.Test;

import blog.article.domain.Article;
import blog.remark.domain.Remark;
import blog.remark.service.RemarkService;
import blog.user.domain.User;
import blog.utils.commons.CommonUtils;

public class RemarkServiceTest {
	private RemarkService remarkService = new RemarkService();

	@Test
	public void testFindRemarkByArticle() {
		Article article = new Article("111ADC3E36A74516A340E1E30B2C5D9D", null, null,
				null, null, null, null, null, null, null, null);
		remarkService.findRemarksByArticle(article);
	}

	@Test
	public void testFindAidByRid() {
		System.out
				.println(remarkService.findAidByRid("1726D78D28E84540962253140E09B89E"));
	}

	@Test
	public void testDeleteRemarkByRid() {
		remarkService.deleteRemarkByRid("18590A819FE0494DB14EABDBD9DAA687");
	}

	@Test
	public void testAddRemark() {
		for (int i = 1; i < 11; i++) {
			String rid = CommonUtils.uuid();
			User author = new User("601D6287BFC041CD8E36D7C7FDDCD8D1", rid, rid, rid, rid,
					rid, rid, rid, rid, rid, rid, rid, false, i);
			Article article = new Article("F97CC7B9B9634F6085EC43687A91D7DD", null, rid,
					rid, rid, rid, rid, null, null, null, null);
			String rcontent = "我是回复" + i;
			Date rtime = new Date();
			Remark remark = new Remark(rid, author, article, rcontent, rtime);
			remarkService.addRemark(remark);
		}
	}

}
