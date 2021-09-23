import redis.clients.jedis.Jedis;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author spondere
 * @version 1.8
 */
public class UserDaoRedisImpl implements UserDao {
    private Jedis openConnection()throws Exception{
        Jedis jedis = new Jedis("121.89.197.4",6379);
//        jedis.auth("123");
        return jedis;
    }


    private String generateId(){
        return System.currentTimeMillis()+"";
    }

    @Override
    public List<UserVO> queryAll() throws Exception{
        // TODO Auto-generated method stub
        Jedis jedis = openConnection();

        String globalKey = UserVO.class.getName();
        long length = jedis.llen(globalKey);
        List<String> idlist = jedis.lrange(globalKey, 0, length - 1);

        List<UserVO> userlist = new ArrayList<>();
        for(String id : idlist){
            UserVO vo = queryById0(jedis,id);
            userlist.add(vo);
        }
        return userlist;
    }

    @Override
    public List<UserVO> queryByName(String name) throws Exception{
        // TODO Auto-generated method stub

        Jedis jedis = openConnection();

        long length = jedis.llen(name);
        List<String> idlist = jedis.lrange(name, 0, length - 1);

        List<UserVO> userlist = new ArrayList<>();
        for(String id : idlist){
            UserVO vo = queryById0(jedis,id);
            userlist.add(vo);
        }
        return userlist;
    }

    @Override
    public UserVO queryById(String userId) throws Exception{
        // TODO Auto-generated method stub
        Jedis jedis = openConnection();

        UserVO vo = queryById0(jedis,userId);

        jedis.close();

        return vo;
    }

    private UserVO queryById0(Jedis jedis,String userId){
        // TODO Auto-generated method stub
        Map<String,String> mapValue =jedis.hgetAll(userId);
        UserVO vo = mapToVO(mapValue);
        vo.setUserId(userId);
        return vo;
    }

    private UserVO mapToVO(Map<String,String> mapValue){
        UserVO vo = new UserVO();
        vo.setUserName(mapValue.get("userName"));
        vo.setPassword(mapValue.get("password"));
        vo.setVocation(mapValue.get("vocation"));
        return vo;

    }

    @Override
    public void insert(UserVO user) throws Exception{
        // TODO Auto-generated method stub
        user.setUserId("USER:"+generateId());

        Jedis jedis = openConnection();
        jedis.hset(user.getUserId(), "userName", user.getUserName());
        jedis.hset(user.getUserId(), "password", user.getPassword());
        jedis.hset(user.getUserId(), "vocation", user.getVocation());

        String globalKey = UserVO.class.getName();
        jedis.lpush(globalKey, user.getUserId());

        String nameKey = user.getUserName();
        jedis.lpush(nameKey, user.getUserId());

        jedis.close();
    }

    @Override
    public void update(UserVO user)throws Exception {
        // TODO Auto-generated method stub
        Jedis jedis = openConnection();
        jedis.hset(user.getUserId(), "password", user.getPassword());
        jedis.hset(user.getUserId(), "vocation", user.getVocation());

        String oldname = jedis.hget(user.getUserId(), "userName");
        String newname = user.getUserName();
        if(newname.equals(oldname)){
            jedis.lrem(oldname, 1, user.getUserId());
            jedis.lpush(newname, user.getUserId());
        }

        jedis.close();

    }

    @Override
    public void delete(UserVO user) throws Exception{
        // TODO Auto-generated method stub
        Jedis jedis = openConnection();

        String username = jedis.hget(user.getUserId(), "userName");
        jedis.lrem(username, 1, user.getUserId());

        String globalKey = UserVO.class.getName();
        jedis.lrem(globalKey,1 , user.getUserId());

        jedis.del(user.getUserId());

        jedis.close();

    }
}
