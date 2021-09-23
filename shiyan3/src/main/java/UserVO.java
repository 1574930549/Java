/**
 * @author spondere
 * @version 1.8
 */
public class UserVO {
    private String userName;
    private String password;
    private String userId;
    private String birthday;
    private String userico;

    public String getUserico() {
        return userico;
    }

    public void setUserico(String userico) {
        this.userico = userico;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

}

