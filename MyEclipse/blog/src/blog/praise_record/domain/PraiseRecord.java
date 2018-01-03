package blog.praise_record.domain;

import blog.article.domain.Article;
import blog.user.domain.User;

public class PraiseRecord {
	private User praiseUser;		//点赞的人
	private Article praiseArticle;	//被点赞的文章

	public User getPraiseUser() {
		return praiseUser;
	}

	public void setPraiseUser(User praiseUser) {
		this.praiseUser = praiseUser;
	}

	public Article getPraiseArticle() {
		return praiseArticle;
	}

	public void setPraiseArticle(Article praiseArticle) {
		this.praiseArticle = praiseArticle;
	}

	@Override
	public String toString() {
		return "PraiseRecord [praiseUser=" + praiseUser + ", praiseArticle="
				+ praiseArticle + "]";
	}

	public PraiseRecord(User praiseUser, Article praiseArticle) {
		this.praiseUser = praiseUser;
		this.praiseArticle = praiseArticle;
	}

	public PraiseRecord() {
		super();
	}

}
