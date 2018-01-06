package yang.model;

import java.util.Date;

public class FeedBackInfo {
	String fbId;
	String fbTitle;//反馈标题
	String fbContent;//反馈内容
	String fbContact;//联系方式
	
	String userId;//外键，用户id
	Date fbTime;//反馈发表时间
	public String getFbId() {
		return fbId;
	}
	public void setFbId(String fbId) {
		this.fbId = fbId;
	}
	public String getFbTitle() {
		return fbTitle;
	}
	public void setFbTitle(String fbTitle) {
		this.fbTitle = fbTitle;
	}
	public String getFbContent() {
		return fbContent;
	}
	public void setFbContent(String fbContent) {
		this.fbContent = fbContent;
	}
	public String getFbContact() {
		return fbContact;
	}
	public void setFbContact(String fbContact) {
		this.fbContact = fbContact;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getFbTime() {
		return fbTime;
	}
	public void setFbTime(Date fbTime) {
		this.fbTime = fbTime;
	}
	public FeedBackInfo(String fbId, String fbTitle, String fbContent, String fbContact, String userId, Date fbTime) {
		super();
		this.fbId = fbId;
		this.fbTitle = fbTitle;
		this.fbContent = fbContent;
		this.fbContact = fbContact;
		this.userId = userId;
		this.fbTime = fbTime;
	}
	public FeedBackInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
