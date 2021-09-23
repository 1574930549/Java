
public class UserVO {
	private String userName;
	private String passWord;
	private String userId;
	public UserVO() {

	}
	public UserVO(String id, String un, String pw) {
		userName = un;
		passWord = pw;
		userId = id;
	}
	public void setUserName(String un) {
		userName = un;
	}
	public String getUserName() {
		return userName;
	}
	public void setPassWord(String pw) {
		passWord = pw;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setUserId(String id) {
		userId = id;
	}
	public String getUserId() {
		return userId;
	}
	public String toString() {
		return "\tuserId = " + userId + "\t\tuserName = " + userName + "\t\tpassWord = " + passWord + "\n";
	}
}





