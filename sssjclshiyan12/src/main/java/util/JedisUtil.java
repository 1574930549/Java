package util;

import redis.clients.jedis.Jedis;

public class JedisUtil {

	public static Jedis getConnection() {
//		return new Jedis("121.89.197.4", 6379);
		Jedis jedis= new Jedis("121.89.197.4", 6379);
		jedis.auth("root");
		return jedis;
	}

}
