package blog.read_record.domain;

import blog.article.domain.Article;
import blog.user.domain.User;

public class ReadRecord {
	private User reader;		//读者
	private Article article;	//被阅读的文章

	public User getReader() {
		return reader;
	}

	public void setReader(User reader) {
		this.reader = reader;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	@Override
	public String toString() {
		return "ReadRecord [reader=" + reader + ", article=" + article + "]";
	}

	public ReadRecord(User reader, Article article) {
		super();
		this.reader = reader;
		this.article = article;
	}

	public ReadRecord() {
		super();
	}

}
