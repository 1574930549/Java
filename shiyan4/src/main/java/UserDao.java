
public interface UserDao {
    public UserVO queryById(String userId);
    public void insert(UserVO user);
    public void update(UserVO user);
    public void delete(UserVO user);
}
