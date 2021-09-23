import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import java.util.HashSet;
import java.util.Set;

public class third {
    private Set<HostAndPort> set;
    private JedisCluster getConnection() {
        return new JedisCluster(set);
    }
    public void insert(String key, String value) throws Exception {
        JedisCluster connection = getConnection();
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
        JedisCluster connection = getConnection();
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
        JedisCluster connection = getConnection();
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
        JedisCluster connection = getConnection();
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
    public void third() {

        Set<HostAndPort> port = new HashSet<>();
        port.add(new HostAndPort("localhost", 6383));
        port.add(new HostAndPort("localhost", 6384));
        port.add(new HostAndPort("localhost", 6385));
        this.set = port;
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
