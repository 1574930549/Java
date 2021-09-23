package dao;

import bean.WordFreq;
import util.JedisUtil;

import redis.clients.jedis.ShardedJedis;

public class WordFreqDAO {

	public void saveWordFreq(WordFreq wf) {
		try(ShardedJedis jedis = JedisUtil.section()) {
			String key = wf.getWord();
			int value = wf.getFreq();

			if(jedis.exists(key)) {
				jedis.set(key, String.valueOf(Integer.parseInt(jedis.get(key)) + value));
			}
			else {
				jedis.set(key, String.valueOf(value));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
