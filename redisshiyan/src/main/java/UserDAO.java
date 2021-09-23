
import java.util.List;
import java.util.Map;
import redis.clients.jedis.Jedis;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class UserDAO implements DAO {
	Jedis jedis;
	int AutoId;
	public UserDAO() {
		jedis = new Jedis();
		AutoId = Integer.parseInt(jedis.get("AutoId"));
	}
	@Override
	public List<UserVO> queryAll() throws DaoException {
		List<UserVO> l = new ArrayList<>();
		List<String> keyList = jedis.sort("UserList");
		if(keyList.size() == 0) {
			throw new DaoException("数据库中没有任何用户信息");
		}
		for(Iterator<String> i = keyList.iterator(); i.hasNext(); ) {
			String tempId = (String)i.next();
			UserVO tempU = new UserVO();
			tempU.setUserId(tempId);
			tempU.setUserName(jedis.hmget("User-" + tempId, "userName").get(0));
			tempU.setPassWord(jedis.hmget("User-" + tempId, "passWord").get(0));
			l.add(tempU);
		}
		return l;
	}
	@Override
	public List<UserVO> queryByName(String name) throws DaoException {
		List<UserVO> l = new ArrayList<>();
		List<String> keyList = jedis.sort("Name-" + name);
		if(keyList.size() == 0) {
			throw new DaoException("数据库中没有任何名为" + name + "的用户信息");
		}
		for(Iterator<String> i = keyList.iterator(); i.hasNext(); ) {
			String tempId = (String)i.next();
			UserVO tempU = new UserVO();
			tempU.setUserId(tempId);
			tempU.setUserName(jedis.hmget("User-" + tempId, "userName").get(0));
			tempU.setPassWord(jedis.hmget("User-" + tempId, "passWord").get(0));
			l.add(tempU);
		}
		return l;
	}
	@Override
	public UserVO queryById(String userId) throws DaoException {
		if(jedis.keys("User-" + userId).size() == 0) {
			throw new DaoException("数据库里没有userId为" + userId + "的用户信息");
		}
		UserVO tempU = new UserVO();
		tempU.setUserId(userId);
		tempU.setUserName(jedis.hmget("User-" + userId, "userName").get(0));
		tempU.setPassWord(jedis.hmget("User-" + userId, "passWord").get(0));
		return tempU;
	}
	@Override
	public void insert(UserVO user) throws DaoException {
		if(jedis.keys("User-" + user.getUserId()).size() != 0) {
			throw new DaoException("插入失败，数据库里已经有userId为" + user.getUserId() + "的用户信息了");
		}
		Map<String, String> u = new HashMap<>();
		u.put("userName", user.getUserName());
		u.put("passWord", user.getPassWord());
		if(user.getUserId().equals("")) {
			while(jedis.keys("User-" + AutoId).size() != 0) {
				AutoId++;
			}
			jedis.hmset("User-" + AutoId, u);
			jedis.lpush("Name-" + user.getUserName(), ((Integer)AutoId).toString());
			jedis.lpush("UserList", ((Integer)AutoId).toString());
			AutoId++;
			jedis.set("AutoId", ((Integer)AutoId).toString());
		}
		else {
			jedis.hmset("User-" + user.getUserId(), u);
			jedis.lpush("Name-" + user.getUserName(), user.getUserId());
			jedis.lpush("UserList", user.getUserId());
		}
	}
	@Override
	public void update(UserVO user) throws DaoException {
		if(jedis.keys("User-" + user.getUserId()).size() == 0) {
			throw new DaoException("更新失败，数据库里没有userId为" + user.getUserId() + "的用户信息");
		}
		jedis.lrem("Name-" + jedis.hget("User-" + user.getUserId(), "userName"), 1, user.getUserId());
		Map<String, String> u = new HashMap<>();
		u.put("userName", user.getUserName());
		u.put("passWord", user.getPassWord());
		jedis.hmset("User-" + user.getUserId(), u);
		jedis.lpush("Name-" + user.getUserName(), user.getUserId());
	}
	@Override
	public void delete(UserVO user) throws DaoException {
		if(jedis.keys("User-" + user.getUserId()).size() == 0) {
			throw new DaoException("删除失败，数据库里没有userId为" + user.getUserId() + "的用户信息");
		}
		jedis.lrem("Name-" + jedis.hget("User-" + user.getUserId(), "userName"), 1, user.getUserId());
		jedis.lrem("UserList", 1, user.getUserId());
		jedis.del("User-" + user.getUserId());
	}
	public static void main(String[] args) {
		UserDAO dao = new UserDAO();
		List<UserVO> l = null;
		UserVO u = null;

		/*查询全部*/
		try {
			l = dao.queryAll();
		}
		catch(DaoException e) {
			e.printStackTrace();
		}
		System.out.println(l);

		/*按名字查询*/
//		try {
//			l = dao.queryByName("zhenghaoran");
//		}
//		catch(DaoException e) {
//			e.printStackTrace();
//		}
//		System.out.println(l);

		/*按id查询*/
//		try {
//			u = dao.queryById("2");
//		}
//		catch(DaoException e) {
//			e.printStackTrace();
//		}
//		System.out.println(u);

		/*插入操作*/
//		try {
//			dao.insert(new UserVO("", "zhenghaoran", "123456"));
//		}
//		catch(DaoException e) {
//			e.printStackTrace();
//		}

		/*更新操作*/
//		try {
//			dao.update(new UserVO("4", "huoweijia", "123456"));
//		}
//		catch(DaoException e) {
//			e.printStackTrace();
//		}

		/*删除操作*/
//		try {
//			dao.delete(new UserVO("4", "", ""));
//		}
//		catch(DaoException e) {
//			e.printStackTrace();
//		}
	}
}


