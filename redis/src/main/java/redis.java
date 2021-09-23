import redis.clients.jedis.Jedis;
public class redis {
    public static void main(String[] args) {

        Jedis jedis = new Jedis("39.101.133.206",6379);
        jedis.auth("123");
        System.out.println(jedis.ping());
    }
}
