package Bolt;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import Redis.JedisUtil;

public class AuthorBolt extends BaseRichBolt{
	private JedisUtil jedis;
	
	@Override
	public void execute(Tuple arg0) {
		// TODO Auto-generated method stub
		String author = arg0.getStringByField("Author");
		jedis.setDataFrequency("authors", author);
		//System.out.println("Author:"+author);
	}

	@Override
	public void prepare(Map arg0, TopologyContext arg1, OutputCollector arg2) {
		// TODO Auto-generated method stub
		jedis = new JedisUtil();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// TODO Auto-generated method stub
		
	}

}
