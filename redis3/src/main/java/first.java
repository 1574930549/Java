import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.*;

/**
 * 测试Redis哨兵模式
 */
public class first {
    private JedisSentinelPool pool;

    private Jedis getConnection() {
        return pool.getResource();
    }

    public void insert(String key, String value) throws Exception {
        Jedis connection = getConnection();
        String value1 = connection.get(key);
        if(connection.get(key) != null) {
            connection.close();
            throw new Exception("插入失败，已经有名为"+key+"的键"+ "，value：" + value1);
        }
        else {
            connection.set(key, value);
            System.out.println("成功插入一组键值对" + "key：" + key + "，value：" + value);
        }
        connection.close();
    }

    public void delete(String key) throws Exception {
        Jedis connection = getConnection();
        String value = connection.get(key);
        if(connection.get(key) == null) {
            connection.close();
            throw new Exception("删除失败，不存在名为"+key+"的键"+ "，value：" + value);
        }
        else {
            System.out.println("成功删除一组键值对" + "key:" + key + "，value:" + value);
            connection.del(key);
        }
        connection.close();
    }

    public void update(String key, String value) throws Exception {
        Jedis connection = getConnection();
        if(connection.get(key) == null) {
            String value1 = connection.get(key);
            connection.close();
            throw new Exception("更新失败，不存在名为"+key+"的键"+ "，value：" + value1);
        }
        else {
            connection.set(key, value);
            System.out.println("成功更新一组键值对" + "key:" + key + "，value:" + value);
        }
        connection.close();
    }

    public String query(String key) {
        Jedis connection = getConnection();
        String value = connection.get(key);
        String result = null;
        if(value == null) {
            System.out.println("查询结果，不存在名为"+key+"的键");
        }
        else {
            result = value;
            System.out.println("成功查找一组键值对"+"key:"+key+"，value:"+value);
        }
        connection.close();
        return result;
    }


    @SuppressWarnings("resource")
    @Test
    public void first() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(8);//资源池中的最大连接数
        config.setMaxIdle(8); // 资源池允许的最大空闲连接数
        config.setMinIdle(0);//资源池中的最大连接数
        config.setBlockWhenExhausted(true);//当资源池用尽后，调用者是否要等待。只有当值为true时，下面的maxWaitMillis才会生效。
        config.setMaxWaitMillis(3000);//当资源池连接用尽后，调用者的最大等待时间（单位为毫秒）。
        config.setTestOnBorrow(false);//向资源池借用连接时是否做连接有效性检测（ping）。检测到的无效连接将会被移除。
        config.setTestOnReturn(false);//	向资源池归还连接时是否做连接有效性检测（ping）。检测到无效连接将会被移除。
        config.setJmxEnabled(true);//是否开启JMX监控
        HashSet<String> sentinels = new HashSet<>();
        sentinels.add("121.89.197.4:26379");
        sentinels.add("39.101.133.206:26380");
        sentinels.add("81.70.135.23:26381");
        pool = new JedisSentinelPool("mymaster",sentinels, config,"123");
        try {
            insert("name", "test1");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            update("name", "test3");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            query("name");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            delete("name");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
