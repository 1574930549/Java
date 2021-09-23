

import java.util.List;

public interface DAO {
	public List<UserVO> queryAll() throws DaoException;
	public List<UserVO> queryByName(String name) throws DaoException;
	public UserVO queryById(String userId) throws DaoException;
	public void insert(UserVO user) throws DaoException;
	public void update(UserVO user) throws DaoException;
	public void delete(UserVO user) throws DaoException;
}
