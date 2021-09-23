import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public class Dao implements UserDao{
    private Jedis jedis = new Jedis("121.89.197.4",6379);
    public UserVO queryById(String userid){
        jedis.auth("123");
        String sid = userid;
        String skey = jedis.get(sid);
        UserVO svo = JSON.parseObject(skey, UserVO.class);
        return svo;
    }
    @Override
    public void insert(UserVO user) {
        jedis.auth("123");
        String key = user.getUserId();
        jedis.set(key, JSON.toJSONString(user));
        List<Product> pd = user.getproduct();
        for(int i = 0;i<pd.size();i++){
            Product p = pd.get(i);
            String productName = p.getproductName();
            String productAmount = p.getamount();
            String namevalue = jedis.get(productName);
            if(namevalue == null){
                jedis.set(productName, productAmount);
            }
            else{
                namevalue += ("," + productAmount);
                jedis.set(productName,namevalue);
            }
            String product = "_" + productName;
            String value = jedis.get(product);
            if(value == null){
                jedis.set(product, key);
            }
            else{
                value += (","+key);
                jedis.set(product,value);
            }
        }
    }
    public void update(UserVO user) {
        jedis.auth("123");
        String key = user.getUserId();
        jedis.del(key);
        insert(user);
    }

    public void delete(UserVO user) {
        jedis.auth("123");
        String key = user.getUserId();
        jedis.del(key);
    }

    public List<UserVO> queryByProductName(String productName) {
        jedis.auth("123");
        List<UserVO> result = new ArrayList<UserVO>();
        String str = jedis.get("_" + productName);
        String[] idList = str.split(",");
        for(int i = 0; i < idList.length; i++) {
            result.add(queryById(idList[i]));
        }
        return result;
    }
}
