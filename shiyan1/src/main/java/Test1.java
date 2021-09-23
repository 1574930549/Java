

/**
 * @author spondere
 * @version 1.8
 */
public class Test1 {
    public static void main(String[] args) {
            UserVO t = new UserVO();
            t.setUserName("test");
            t.setPassword("123");
            t.setUserId("1814010130");
            t.setBirthday("2021-04-01");
            t.setUserico("C:\\Users\\zlh\\Desktop\\图片\\Snipaste_2021-04-06_17-22-17.png");
            UserDao userDao = new UserDaoRedisImpl();
            userDao.insertByString(t); //插入数据
            UserVO v = new UserVO();
            v = userDao.queryById("1814010130"); //按 id 查找
            System.out.println(v.getPassword());
            System.out.println(v.getUserName());
            System.out.println(v.getBirthday());
            System.out.println(v.getUserico());
    }
}

