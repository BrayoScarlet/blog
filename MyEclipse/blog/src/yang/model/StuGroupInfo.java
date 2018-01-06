package yang.model;

import java.util.Date;

public class StuGroupInfo {
	private String stugroupId;
	private int memberNum;//人数
	private String stugroupName;//小组名称
	private String stugroupIntro;//小组简介
	private String leadId;//创建者的用户id
	private String qqNumber;//QQ号
	private Date buildDate;//创建日期
	private String imageUrl;//图片链接
	
	public StuGroupInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public StuGroupInfo(String stugroupId, int memberNum, String stugroupName, String stugroupIntro, String leadId,
			String qqNumber, Date buildDate, String imageUrl) {
		super();
		this.stugroupId = stugroupId;
		this.memberNum = memberNum;
		this.stugroupName = stugroupName;
		this.stugroupIntro = stugroupIntro;
		this.leadId = leadId;
		this.qqNumber = qqNumber;
		this.buildDate = buildDate;
		this.imageUrl = imageUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getStugroupId() {
		return stugroupId;
	}

	public void setStugroupId(String stugroupId) {
		this.stugroupId = stugroupId;
	}

	public int getMemberNum() {
		return memberNum;
	}


	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}

	public String getStugroupName() {
		return stugroupName;
	}

	public void setStugroupName(String stugroupName) {
		this.stugroupName = stugroupName;
	}

	public String getStugroupIntro() {
		return stugroupIntro;
	}

	public void setStugroupIntro(String stugroupIntro) {
		this.stugroupIntro = stugroupIntro;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public String getQqNumber() {
		return qqNumber;
	}

	public void setQqNumber(String qqNumber) {
		this.qqNumber = qqNumber;
	}

	public Date getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}

	@Override
	public boolean equals(Object other){
		if(other instanceof StuGroupInfo == false) {
			return false;
		}
		StuGroupInfo othergroup=(StuGroupInfo) other;
		return this.stugroupId.equalsIgnoreCase(othergroup.getStugroupId());
		
	}
}
