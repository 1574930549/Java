import java.util.List;

/**
 * @author spondere
 * @version 1.8
 */
public interface UserDao {
    public List<UserVO> queryAll () throws Exception;
    public List<UserVO> queryByName (String name) throws Exception;
    public UserVO queryById (String userId) throws Exception;
    public void insert(UserVO user) throws Exception;
    public void update(UserVO user) throws Exception;
    public void delete(UserVO user) throws Exception;
}
