package blog.article.domain;

import java.util.Date;

import blog.user.domain.User;

public class Article {
	private String aid;				//主键
	private User author;			//外键, 表示博客属于谁(博主)
	private String atitle;			//文章标题
	private String type;			//文章类型(原创, 转发, 翻译)
	private String acontent;		//文章内容
	private String classify;		//文章分类
	private String abstractContent;	//文章摘要
	private Long praiseNum = 0L;	//点赞数
	private Long remarkNum = 0L;	//回复数
	private Long readNum = 0L;		//阅读量
	private Date atime;				//发表时间
	private Integer verify = 0;		//审核字段, 0: 未审核 1: 通过 2: 未通过

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getAtitle() {
		return atitle;
	}

	public void setAtitle(String atitle) {
		this.atitle = atitle;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAcontent() {
		return acontent;
	}

	public void setAcontent(String acontent) {
		this.acontent = acontent;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public String getAbstractContent() {
		return abstractContent;
	}

	public void setAbstractContent(String abstractContent) {
		this.abstractContent = abstractContent;
	}

	public Long getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(Long praiseNum) {
		this.praiseNum = praiseNum;
	}

	public Long getRemarkNum() {
		return remarkNum;
	}

	public void setRemarkNum(Long remarkNum) {
		this.remarkNum = remarkNum;
	}

	public Long getReadNum() {
		return readNum;
	}

	public void setReadNum(Long readNum) {
		this.readNum = readNum;
	}

	public Date getAtime() {
		return atime;
	}

	public void setAtime(Date atime) {
		this.atime = atime;
	}

	public Integer getVerify() {
		return verify;
	}

	public void setVerify(Integer verify) {
		this.verify = verify;
	}

	@Override
	public String toString() {
		return "Article [aid=" + aid + ", author=" + author + ", atitle=" + atitle
				+ ", type=" + type + ", acontent=" + acontent + ", classify=" + classify
				+ ", abstractContent=" + abstractContent + ", praiseNum=" + praiseNum
				+ ", remarkNum=" + remarkNum + ", readNum=" + readNum + ", atime=" + atime
				+ ", verify=" + verify + "]";
	}

	public Article(String aid, User author, String atitle, String type, String acontent,
			String classify, String abstractContent, Long praiseNum, Long remarkNum,
			Long readNum, Date atime, Integer verify) {
		this.aid = aid;
		this.author = author;
		this.atitle = atitle;
		this.type = type;
		this.acontent = acontent;
		this.classify = classify;
		this.abstractContent = abstractContent;
		this.praiseNum = praiseNum;
		this.remarkNum = remarkNum;
		this.readNum = readNum;
		this.atime = atime;
		this.verify = verify;
	}

	public Article() {
		super();
	}

}
