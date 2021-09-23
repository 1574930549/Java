package Bolt;

import java.util.List;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import Lucene.LuceneUtil;
import Redis.JedisUtil;

public class TitleBolt extends BaseRichBolt{
	private JedisUtil jedis;
	
	@Override
	public void execute(Tuple arg0) {
		// TODO Auto-generated method stub
		String title = arg0.getStringByField("Title");
		List<String> wordlist = LuceneUtil.getSmartChineseAnalyzer(title);
		if(wordlist!=null) {
			for(String word : wordlist) {
				jedis.setDataFrequency("words",word);
			}
		}
		//System.out.println("Title:"+title);
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
