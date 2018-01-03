package blog.remark.domain;

import java.util.Date;

import blog.article.domain.Article;
import blog.user.domain.User;

public class Remark {
	private String rid;				//主键
	private User author;			//外键, 回复的人
	private Article article;		//外键, 将回复的文章
	private String rcontent;		//回复的内容
	private Date rtime;				//回复的时间

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public String getRcontent() {
		return rcontent;
	}

	public void setRcontent(String rcontent) {
		this.rcontent = rcontent;
	}

	public Date getRtime() {
		return rtime;
	}

	public void setRtime(Date rtime) {
		this.rtime = rtime;
	}

	@Override
	public String toString() {
		return "Remark [rid=" + rid + ", author=" + author + ", article=" + article
				+ ", rcontent=" + rcontent + ", rtime=" + rtime + "]";
	}

	public Remark(String rid, User author, Article article, String rcontent, Date rtime) {
		super();
		this.rid = rid;
		this.author = author;
		this.article = article;
		this.rcontent = rcontent;
		this.rtime = rtime;
	}

	public Remark() {
		super();
	}

}
