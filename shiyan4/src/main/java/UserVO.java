import java.util.List;


public class UserVO {
    private String username;
    private String password;
    private String userid;
    private String birthday;
    private String userico;
    private List<Product> product;


    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passWord) {
        this.password = passWord;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userId) {
        this.userid = userId;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String Birthday) {
        this.birthday = Birthday;
    }

    public String getUserico() {
        return userico;
    }

    public void setUserico(String userIco) {
        this.userico = userIco;
    }

    public void setproduct(List<Product> productList) {
        this.product = productList;
    }

    public List<Product> getproduct() {
        return product;
    }

    @Override
    public String toString() {
        return "UserVO2{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userid='" + userid + '\'' +
                ", birthday='" + birthday + '\'' +
                ", userico='" + userico + '\'' +
                ", product=" + product +
                '}';
    }
}

