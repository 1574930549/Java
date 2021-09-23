import java.util.List;

public class UserVO {
    private String userName;
    private String passWord;
    private String userId;
    private String birthday;
    private String userico;
    private List<Product> product;
    public void setUserName(String uname) {
        userName = uname;
    }
    public String getUserName() {
        return userName;
    }
    public void setPassWord(String pword) {
        passWord = pword;
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
    public void setBirthday(String bday){
        birthday = bday;
    }
    public String getBirthday(){
        return birthday;
    }
    public void setUserico(String uico){
        userico = uico;
    }
    public String getUserico(){
        return userico;
    }
    public void setProduct(List<Product> productList){
        this.product = productList;
    }
    public List<Product> getproduct(){
        return product;
    }
    public String toString() {
        return userName;
    }
}
