package util;

import java.util.List;
import java.util.ArrayList;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.JedisShardInfo;

public class JedisUtil {

	public static Jedis getConnection() {
		Jedis jedis=new Jedis("121.89.197.4",6379);
		jedis.auth("root");
		return jedis;
	}

	public static ShardedJedis section() {
		List<JedisShardInfo> list = new ArrayList<>();
		JedisShardInfo jedisShardInfo1 = new JedisShardInfo("121.89.197.4", 6379);
		jedisShardInfo1.setPassword("root");
		list.add(jedisShardInfo1);
		JedisShardInfo jedisShardInfo2 = new JedisShardInfo("121.89.197.4", 6380);
		jedisShardInfo2.setPassword("root");
		list.add(jedisShardInfo2);
		JedisShardInfo jedisShardInfo3 = new JedisShardInfo("121.89.197.4", 6381);
		jedisShardInfo3.setPassword("root");
		list.add(jedisShardInfo3);

		return new ShardedJedis(list);
	}

}
