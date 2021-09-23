package Redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPool;

public class JedisUtil {
	private static JedisPool jedispool;
	private Jedis jedis;
	public JedisUtil() {
		 jedis = jedispool.getResource();
	}

	static {
		JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(10);
        config.setMaxTotal(100);
        config.setMaxWaitMillis(10000);
        config.setTestOnBorrow(true);
        jedispool = new JedisPool(config, "127.0.0.1", 6379);
//        jedis.auth("mypassword")
	}

	public synchronized String getDataBySpop(String keyset) {
		String key = jedis.spop(keyset);
		if(key == null)
			return null;
		// System.out.println(key);
		String data = jedis.get(key);
		// System.out.println(data);
		return data;
	}

	public synchronized String getDataBySrand(String keyset) {
		String key = jedis.srandmember(keyset);
		if(key == null)
			return null;
		// System.out.println(key);
		String data = jedis.get(key);
		// System.out.println(data);
		return data;
	}

	public synchronized List<String> getDataBySmembers(String keyset) {
		Set<String> keys = jedis.smembers(keyset);
		if(keys == null)
			return null;
		List<String> datalist = new ArrayList<String>();
		for(String key : keys) {
			String data = jedis.get(key);
			datalist.add(data);
		}
		return datalist;
	}

	public synchronized void setDataFrequency(String keyset,String word) {
		if(jedis.exists(word)) {
			String times = jedis.get(word);
			int t = Integer.valueOf(times);
			t++;
			jedis.set(word, String.valueOf(t));
			System.out.println(word+", "+t);
		}else {
			jedis.sadd(keyset, word);
			jedis.set(word, "1");
			System.out.println(word+", "+1);
		}
	}


}
