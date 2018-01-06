package yang.model;


/*该类是为了方便lookGroup.jsp把信息返回给lookGroup.html而创建的
 * 
 * */
public class IsMember {
	//flag 是用户是否存在在这个学习小组里
	private String flag = null;
	private StuGroupInfo stugroupInfo = null;
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public StuGroupInfo getStugroupInfo() {
		return stugroupInfo;
	}
	public void setStugroupInfo(StuGroupInfo stugroupInfo) {
		this.stugroupInfo = stugroupInfo;
	}
	public IsMember(String flag, StuGroupInfo stugroupInfo) {
		super();
		this.flag = flag;
		this.stugroupInfo = stugroupInfo;
	}
	public IsMember() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
