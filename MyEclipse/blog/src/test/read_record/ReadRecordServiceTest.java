package test.read_record;

import org.junit.Test;

import blog.article.domain.Article;
import blog.read_record.domain.ReadRecord;
import blog.read_record.service.ReadRecordService;
import blog.user.domain.User;

public class ReadRecordServiceTest {
	private ReadRecordService readRecordService = new ReadRecordService();

	@Test
	public void testAddReadRecord() {
		Article article = new Article("F97CC7B9B9634F6085EC43687A91D7DD", null, null,
				null, null, null, null, null, null, null, null, 0);
		User user = new User("C86D29E2F8C84B9EA3F5EF383FF612D8", null, null, null, null,
				null, null, null, null, null, null, null, false, 0);
		ReadRecord readRecord = new ReadRecord(user, article);
		readRecordService.addReadRecord(readRecord);
	}

}
