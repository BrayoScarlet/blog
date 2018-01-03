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

	@Test
	public void testFindArticlesByKeyWords() {
		String[] keyWords1 = { "张三", "博客" };
		String[] keyWords2 = { "张三", "博客", "2" };
		String[] keyWords3 = { "" };
		String[] keyWords4 = { "算法" };
		String[] keyWords5 = { "Java" };
		String[] keyWords6 = { "粗体" };
		String[] keyWords7 = { "啥" };
		String[] keyWords8 = { "数据库" };
		List<Article> articles = articleService.findArticlesByKeyWords(keyWords8);
		for (Article article : articles) {
			System.out.println(article);
		}
	}

	@Test
	public void testFindArticlesByUidAndKeyWords() {
		String uid = "601D6287BFC041CD8E36D7C7FDDCD8D1";
		String[] keyWords1 = { "张三", "博客" };
		String[] keyWords2 = { "张三", "博客", "2" };
		String[] keyWords3 = { "" };
		String[] keyWords4 = { "算法" };
		String[] keyWords5 = { "Java" };
		String[] keyWords6 = { "粗体" };
		String[] keyWords7 = { "啥" };
		String[] keyWords8 = { "数据" };
		String[] keyWords9 = { "数据库" };
		List<Article> articles =
				articleService.findArticlesByUidAndKeyWords(uid, keyWords9);
		for (Article article : articles) {
			System.out.println(article);
			System.out.println("============================");
		}
	}
}
