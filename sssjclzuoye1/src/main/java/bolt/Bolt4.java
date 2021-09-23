package bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;



public class Bolt4 extends BaseRichBolt {
	private OutputCollector collector;
	public void execute(Tuple input) {

		int v = input.getIntegerByField("weishu");
		collector.emit(new Values(v));

	}
	public void prepare(Map stormConf, TopologyContext context, OutputCollector arg2) {
		collector = arg2;
	}

	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		arg0.declare(new Fields("shuzi"));
	}

}
