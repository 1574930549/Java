

import java.util.Iterator;
import java.util.List;

/**
 * @author spondere
 * @version 1.8
 */
public class Test2 {
    public static void main(String[] args) {
            UserVO t2 = new UserVO();
            t2.setUserName("test2");
            t2.setPassword("888999");
            t2.setUserId("1814010130");
            t2.setBirthday("2021-04-10");
            t2.setUserico("C:\\Users\\zlh\\Desktop\\图片\\Snipaste_2021-04-06_17-22-17.png");
            UserDao userDao = new UserDaoRedisImpl();
            userDao.insertByString(t2); //插入数据
            List<UserVO> q = userDao.queryByName("test2");
            Iterator<UserVO> it = q.iterator();
            while (it.hasNext()) {
                UserVO v = it.next();
                System.out.println(v.getUserId());
                System.out.println(v.getPassword());
                System.out.println(v.getUserName());
                System.out.println(v.getBirthday());
                System.out.println(v.getUserico());
            }
    }
}
