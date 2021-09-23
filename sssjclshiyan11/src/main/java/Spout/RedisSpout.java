package Spout;

import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import Redis.JedisUtil;

public class RedisSpout extends BaseRichSpout{
	private SpoutOutputCollector collector;
	private JedisUtil jedis;
	
	@Override
	public void nextTuple() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String JsonString = jedis.getDataBySpop("urls");
		if(JsonString!=null) {
			JSONObject jo = JSON.parseObject(JsonString);
			// System.out.println("Title:"+jo.getString("Title")+" Author:"+jo.getString("Author")+" Date:"+jo.getString("Date"));
			Values title = new Values(jo.getString("Title"));
			Values author = new Values(jo.getString("Author"));
			Values date = new Values(jo.getString("Date"));
			collector.emit("title",title);
			collector.emit("author",author);
			collector.emit("date",date);
		}
	}

	@Override
	public void open(Map arg0, TopologyContext arg1, SpoutOutputCollector arg2) {
		// TODO Auto-generated method stub
		collector = arg2;
		jedis = new JedisUtil();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// TODO Auto-generated method stub
		arg0.declareStream("title",new Fields("Title"));
		arg0.declareStream("author",new Fields("Author"));
		arg0.declareStream("date",new Fields("Date"));
	}
	
}
