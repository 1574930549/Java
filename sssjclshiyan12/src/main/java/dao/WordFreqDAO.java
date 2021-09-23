package dao;

import bean.WordFreq;
import util.JedisUtil;

import redis.clients.jedis.Jedis;

public class WordFreqDAO {

	public void saveWordFreq(WordFreq wf) {
		try(Jedis jedis = JedisUtil.getConnection()) {
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
