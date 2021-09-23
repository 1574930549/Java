import com.alibaba.fastjson.JSON;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

import java.util.ArrayList;
import java.util.List;

public class SY4 implements UserDao {
    private ShardedJedis jedis;
    public SY4() {
        List<JedisShardInfo> list=new ArrayList<>();
        list.add(new JedisShardInfo("localhost", 6379));
        list.add(new JedisShardInfo("localhost", 6380));
        list.add(new JedisShardInfo("localhost", 6381));
        jedis= new ShardedJedis(list);

    }
    public UserVO queryById(String userid) {
//        jedis.auth("123");
        String key = userid;
        String str = jedis.get(key);
        UserVO temp = JSON.parseObject(str, UserVO.class);
        return temp;
    }

    public void insert(UserVO user) {
//        jedis.auth("123");
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

    public void update(UserVO user) {
//        jedis.auth("123");
        String key = user.getUserid();
        jedis.del(key);
        insert(user);
    }

    public void delete(UserVO user) {
//        jedis.auth("123");
        String key = user.getUserid();
        jedis.del(key);
    }

    public List<UserVO> queryByProductName(String productName) {
//        jedis.auth("123");
        List<UserVO> result = new ArrayList<UserVO>();
        String str = jedis.get("_" + productName);
        String[] idList = str.split(",");
        for(int i = 0; i < idList.length; i++) {
            result.add(queryById(idList[i]));
        }
        return result;
    }
}
