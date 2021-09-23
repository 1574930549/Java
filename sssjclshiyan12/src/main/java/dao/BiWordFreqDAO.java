package dao;

import bean.BiWordFreq;
import util.JedisUtil;

import redis.clients.jedis.Jedis;

public class BiWordFreqDAO {

	public void saveBiWordFreq(BiWordFreq wf) {
		try(Jedis jedis = JedisUtil.getConnection()) {
			String key = wf.getWord1() + "-" + wf.getWord2();
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
