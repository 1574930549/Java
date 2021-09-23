package spout;

import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Values;
import org.apache.storm.tuple.Fields;

import redis.clients.jedis.Jedis;

import util.JedisUtil;

public class Spout extends BaseRichSpout  {
	private SpoutOutputCollector collector;

	public void nextTuple() {
		String content = JedisUtil.getConnection().get("!");
		String[] strs = content.split("\n");

		for(String s: strs) {
			collector.emit(new Values(s));
		}
		collector.emit(new Values(""));
		try {
			Thread.sleep(Long.MAX_VALUE);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void open(Map arg0, TopologyContext arg1, SpoutOutputCollector arg2) {
		collector = arg2;
	}

	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		arg0.declare(new Fields("content"));
	}

}
