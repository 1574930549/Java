import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class test3 {
    public static void main(String[] args) {
        UserVO t=new UserVO();
        t.setUserName("wang666");
        t.setPassword("789789");
        t.setUserId("1814010130");
        t.setBirthday("2021-04-20");
        t.setUserico("");

        System.out.println(t.getUserId());
        UserDao userDao = new SY3();
        userDao.insertByString(t); //插入数据
        UserVO v;
        v= userDao.queryById("1814010130"); //按 id 查找
        System.out.println(v.getUserId());
        System.out.println(v.getPassword());
        System.out.println(v.getUserName());
        System.out.println(v.getBirthday());
        System.out.println(v.getUserico());
    }
}
