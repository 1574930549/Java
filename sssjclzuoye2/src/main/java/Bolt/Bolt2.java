package Bolt;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;


public class Bolt2 extends BaseRichBolt {
	private OutputCollector collector;
	public void execute(Tuple arg0) {

		int v = arg0.getIntegerByField("shuzhi");
		if(v % 10 == 2){
			collector.emit("er",new Values(v));
		}
		if(v % 10 == 4){
			collector.emit("si",new Values(v));
		}
	}

	public void prepare(Map arg0, TopologyContext arg1, OutputCollector arg2) {
		collector = arg2;
	}

	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		arg0.declareStream("er", new Fields("weishu"));
		arg0.declareStream("si", new Fields("weishu"));
	}
}


