package blog.user.domain;

public class User {
	private String uid;
	private String email;
	private String username;
	private String password;
	private String school;
	private String qualification;	//最高学历
	private String graduationYear;	//毕业年份
	private String specialisations;	//感兴趣的职业方向
	private String sex = "男";
	private String ubrief;			//个人简介
	private String domicile;		//居住地
	private String token;			//激活码
	private boolean state = false;	//激活状态
	private int admin = 0;			//用户权限

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getGraduationYear() {
		return graduationYear;
	}

	public void setGraduationYear(String graduationYear) {
		this.graduationYear = graduationYear;
	}

	public String getSpecialisations() {
		return specialisations;
	}

	public void setSpecialisations(String specialisations) {
		this.specialisations = specialisations;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUbrief() {
		return ubrief;
	}

	public void setUbrief(String ubrief) {
		this.ubrief = ubrief;
	}

	public String getDomicile() {
		return domicile;
	}

	public void setDomicile(String domicile) {
		this.domicile = domicile;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", email=" + email + ", username=" + username
				+ ", password=" + password + ", school=" + school + ", qualification="
				+ qualification + ", graduationYear=" + graduationYear
				+ ", specialisations=" + specialisations + ", sex=" + sex + ", ubrief="
				+ ubrief + ", domicile=" + domicile + ", token=" + token + ", state="
				+ state + ", admin=" + admin + "]";
	}

	public User(String uid, String email, String username, String password, String school,
			String qualification, String graduationYear, String specialisations,
			String sex, String ubrief, String domicile, String token, boolean state,
			int admin) {
		super();
		this.uid = uid;
		this.email = email;
		this.username = username;
		this.password = password;
		this.school = school;
		this.qualification = qualification;
		this.graduationYear = graduationYear;
		this.specialisations = specialisations;
		this.sex = sex;
		this.ubrief = ubrief;
		this.domicile = domicile;
		this.token = token;
		this.state = state;
		this.admin = admin;
	}

	public User() {
		super();
	}

}
