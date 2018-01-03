package test.praise_record;

import org.junit.Test;

import blog.article.domain.Article;
import blog.praise_record.domain.PraiseRecord;
import blog.praise_record.service.PraiseRecordService;
import blog.user.domain.User;

public class PraiseRecordServiceTest {
	private PraiseRecordService praiseRecordService = new PraiseRecordService();

	@Test
	public void testAddPraiseRecord() {
		Article article = new Article("E337017D0BAC4E289EBF9B576280FAA8", null, null,
				null, null, null, null, null, null, null, null, 0);
		User user = new User("37BAA2FF5E1945B385B61AE9C9AB0A04", null, null, null, null,
				null, null, null, null, null, null, null, false, 0);
		PraiseRecord praiseRecord = new PraiseRecord(user, article);
		praiseRecordService.addPraiseRecord(praiseRecord);
	}

}
