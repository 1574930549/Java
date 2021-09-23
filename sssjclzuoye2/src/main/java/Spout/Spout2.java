package Spout;

import java.util.Map;
import java.util.Random;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

//大整数
public class Spout2 extends BaseRichSpout {



	private Random rd = new Random();
	private SpoutOutputCollector collector;

	public void nextTuple() {
		int zhengshu = rd.nextInt(1000)+10000;
		if(zhengshu % 2 == 0){
			collector.emit("oushustream",new Values(zhengshu));
		}
		else{
			collector.emit("jishustream",new Values(zhengshu));
		}
	}


	public void open(Map arg0, TopologyContext arg1, SpoutOutputCollector arg2) {
		collector = arg2;
	}

	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		arg0.declareStream("oushustream", new Fields("shuzhi"));
		arg0.declareStream("jishustream", new Fields("shuzhi"));

	}
}
