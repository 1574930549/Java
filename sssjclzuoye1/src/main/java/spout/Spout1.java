package spout;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;
import java.util.Random;


//小整数
public class Spout1 extends BaseRichSpout {

	private Random rd = new Random();
	private SpoutOutputCollector collector;


	public void nextTuple() {
		int zhengshu = rd.nextInt(100);
		Values tuple= new Values(zhengshu,"SmallRange");
		collector.emit(tuple);
	}

	public void open(Map arg0, TopologyContext arg1, SpoutOutputCollector arg2) {
		collector = arg2;
	}

	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		arg0.declare(new Fields("shuzhi","source"));
	}
}
