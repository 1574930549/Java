import java.util.List;


public interface UserDao {
    public List<UserVO> queryAll ();
    public List<UserVO> queryByName (String name);
    public UserVO queryById (String userId);

    public void insertByHash(UserVO user);
    public void insertByList(UserVO user);
    public void insertByString(UserVO user);
    public void updateByHash(UserVO user);
    public void updateByList(UserVO user);

    public void delete(UserVO user);

}
