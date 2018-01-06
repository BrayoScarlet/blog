package test.article;

import java.util.List;

import org.junit.Test;

import blog.article.domain.Article;
import blog.article.service.ArticleService;

public class ArticleServiceTest {
	private ArticleService articleService = new ArticleService();

	@Test
	public void testFindArticlesHasLimit() {
		List<Article> articles = articleService.findArticlesHasLimit(0, 3);
		for (Article article : articles) {
			System.out.println(article);
		}
	}

}
