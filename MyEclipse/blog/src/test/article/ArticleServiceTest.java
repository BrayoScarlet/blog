package test.article;

import java.util.List;

import org.junit.Test;

import blog.article.domain.Article;
import blog.article.service.ArticleService;

public class ArticleServiceTest {
	private ArticleService articleService = new ArticleService();

	@Test
	public void testFindAllArticles() {
		List<Article> articles = articleService.findAllArticles();
		for (Article article : articles) {
			System.out.println(article);
		}
	}

	@Test
	public void testFindArticlesByUid() {
		List<Article> articles =
				articleService.findArticlesByUid("C86D29E2F8C84B9EA3F5EF383FF612D8");
		for (Article article : articles) {
			System.out.println(article);
		}
	}

	@Test
	public void testFindArticlesHasLimit() {
		List<Article> articles = articleService.findArticlesHasLimit(0, 3);
		for (Article article : articles) {
			System.out.println(article);
		}
	}

}
