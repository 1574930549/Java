import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * @author spondere
 * @version 1.8
 */
public class UserDaoImpl implements UserDao2 {
    private Jedis jedis = new Jedis("121.89.197.4",6379);

    public UserVO2 queryById(String userid) {
        jedis.auth("123");
        String key = userid;
        String str = jedis.get(key);
        UserVO2 temp = JSON.parseObject(str, UserVO2.class);
        return temp;
    }

    public void insert(UserVO2 user) {
        jedis.auth("123");
        String key = user.getUserid();
        jedis.set(key, JSON.toJSONString(user));
        List<Product> ps = user.getproduct();
        for(int i = 0; i < ps.size(); i++) {
            Product p = ps.get(i);
            String productname = p.getProductname();
            String amount = p.getAmount();
            String namevalue = jedis.get(productname);
            if(namevalue == null) {
                jedis.set(productname, amount);
            }
            else {
                namevalue += ("," + amount);
                jedis.set(productname, namevalue);
            }
            //
            String product = "_" + productname;
            String value = jedis.get(product);
            if(value == null) {
                jedis.set(product, key);
            }
            else {
                value += ("," + key);
                jedis.set(product, value);
            }
        }
    }

    public void update(UserVO2 user) {
        jedis.auth("123");
        String key = user.getUserid();
        jedis.del(key);
        insert(user);
    }

    public void delete(UserVO2 user) {
        jedis.auth("123");
        String key = user.getUserid();
        jedis.del(key);
    }

    public List<UserVO2> queryByProductName(String productName) {
        jedis.auth("123");
        List<UserVO2> result = new ArrayList<UserVO2>();
        String str = jedis.get("_" + productName);
        String[] idList = str.split(",");
        for(int i = 0; i < idList.length; i++) {
            result.add(queryById(idList[i]));
        }
        return result;
    }
}
