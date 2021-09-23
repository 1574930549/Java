package Bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

public class Bolt1 extends BaseRichBolt {

    private OutputCollector collector;

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {

        collector = outputCollector;
    }

    public void execute(Tuple tuple) {

        String lines = tuple.getStringByField("lines");
        String[] split = lines.split(" ");

        for (String word : split) {
            collector.emit(new Values(word));
        }
    }
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

        outputFieldsDeclarer.declare(new Fields("words"));
    }
}
