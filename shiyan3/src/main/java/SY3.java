import redis.clients.jedis.Jedis;
import com.alibaba.fastjson.JSON;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.*;

public class SY3 implements UserDao{
    //    private Jedis jedis = new Jedis("121.89.197.4",6379);
    private Jedis jedis;
    public SY3() {
        Set<String> IPS=new HashSet<>();
//        IPS.add("127.0.0.1:26379");
//        IPS.add("127.0.0.1:26380");
//        IPS.add("127.0.0.1:26381");
        IPS.add("121.89.197.4:26379");
        IPS.add("121.89.197.4:26380");
        IPS.add("121.89.197.4:26381");

        JedisSentinelPool pool=new JedisSentinelPool("mymaster",IPS);
        jedis=pool.getResource();
    }
    @Override
    public List<UserVO> queryAll() {
//        jedis.auth("123");
        String userlist = jedis.get(UserVO.class.getName());
        String[] userarr = userlist.split(",");
        List<UserVO> userList = new ArrayList<UserVO>();
        for (int i = 0;i<userarr.length;i++){
            UserVO v = new UserVO();
            String key = userarr[i];
            String value = jedis.get(key);
            String[] info = value.split(",");
            v.setUserId(key);
            v.setUserName(info[0]);
            v.setPassword(info[1]);
            v.setBirthday(info[2]);
            v.setUserico(info[3]);
            userList.add(v);
        }
        return userList;

    }

    @Override
    public List<UserVO> queryByName(String name) {
//        jedis.auth("123");
        List<UserVO> user_list = new ArrayList<UserVO>();
        String keystr = jedis.get(name);
        String[] keyarr = keystr.split(",");
        UserVO v = new UserVO();
        for (int i=0; i<keyarr.length; i++){
            String key = keyarr[i];
            String value = jedis.get(key);
            if (value.equals(" ") == false){
                String[] info = value.split(",");
                v.setUserId(key);
                v.setUserName(info[0]);
                v.setPassword(info[1]);
                v.setBirthday(info[2]);
                v.setUserico(info[3]);
                user_list.add(v);
            }
        }
        return user_list;
    }


    @Override
    public UserVO queryById(String userId) {
//        jedis.auth("123");
        String user_string_temp = jedis.get(userId);
        if (user_string_temp == null){
            return null;
        }else{
            String[] strarr = user_string_temp.split(",");
            UserVO v = new UserVO();
            v.setUserId(userId);
            v.setUserName(strarr[0]);
            v.setPassword(strarr[1]);
            v.setBirthday(strarr[2]);
            v.setUserico(strarr[3]);
            return v;
        }

    }

    @Override
    public void insertByHash(UserVO user) {
//        jedis.auth("123");
        String key = user.getUserId();
        Map<String, String> userMap = new HashMap();
        userMap.put("username", user.getUserName());
        userMap.put("password", user.getPassword());
        userMap.put("birthday", user.getBirthday());
        userMap.put("userico", user.getUserico());
        jedis.hmset(key, userMap);
        String nameid = user.getUserName();
        String namevalue = jedis.get(nameid);
        if (namevalue == null){
            jedis.set(nameid, key);
        }else{
            namevalue = namevalue + "," + key;
            jedis.set(nameid, namevalue);
        }
        String allid = jedis.get(UserVO.class.getName());
        if (allid == null){
            jedis.set(UserVO.class.getName(), user.getUserId());
        }else{
            allid = allid + "," + user.getUserId() ;
            jedis.set(UserVO.class.getName(), allid);
        }
    }


    @Override
    public void insertByList(UserVO user) {
//        jedis.auth("123");
        String key = user.getUserId();
        jedis.rpush(key,
                user.getUserName(),
                user.getPassword(),
                user.getBirthday(),
                user.getUserico());
        String nameid = user.getUserName();
        String namevalue = jedis.get(nameid);
        if (namevalue == null){
            jedis.set(nameid, key);
        }else{
            namevalue = namevalue + "," + key;
            jedis.set(nameid, namevalue);
        }
        String allid = jedis.get(UserVO.class.getName());
        if (allid == null){
            jedis.set(UserVO.class.getName(), user.getUserId());
        }else{
            allid = allid + "," + user.getUserId() ;
            jedis.set(UserVO.class.getName(), allid);
        }

    }

    @Override
    public void insertByString(UserVO user) {
//        jedis.auth("123");
        String key = user.getUserId();
        jedis.set(key,JSON.toJSONString(user));
        String nameid = user.getUserName();
        String namevalue = jedis.get(nameid);
        if (namevalue == null){
            jedis.set(nameid, key);
        }else{
            namevalue = namevalue + "," + key;
            jedis.set(nameid, namevalue);
        }
        String allid = jedis.get(UserVO.class.getName());
        if (allid == null){
            jedis.set(UserVO.class.getName(), user.getUserId());
        }else{
            allid = allid + "," + user.getUserId() ;
            jedis.set(UserVO.class.getName(), allid);
        }
    }


    @Override
    public void updateByHash(UserVO user) {
//        jedis.auth("123");
        Map<String, String> userMap = new HashMap();
        userMap.put("username", user.getUserName());
        userMap.put("password", user.getPassword());
        userMap.put("birthday", user.getBirthday());
        userMap.put("userico", user.getUserico());
        jedis.hmset(user.getUserId(), userMap);
    }

    @Override
    public void updateByList(UserVO user) {
//        jedis.auth("123");
        String key = user.getUserId();
        jedis.del(key);
        jedis.rpush(key,
                user.getUserName(),
                user.getPassword(),
                user.getBirthday(),
                user.getUserico());
    }

    @Override
    public void delete(UserVO user) {
//        jedis.auth("123");
        jedis.del(user.getUserId());
    }
}
