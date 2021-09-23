/**
 * @author spondere
 * @version 1.8
 */
public class UserVO {
    private String userName;
    private String password;
    private String userId;
    private String vocation;

    public String getVocation() {
        return vocation;
    }

    public void setVocation(String vocation) {
        this.vocation = vocation;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userId='" + userId + '\'' +
                ", vocation='" + vocation + '\'' +
                '}';
    }
}

