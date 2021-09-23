package Bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Bolt2 extends BaseRichBolt {

    Map<String,Integer> map=new HashMap<String,Integer>();

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {

    }

    public void execute(Tuple tuple) {

        String words = tuple.getStringByField("words");
        Integer count = map.get(words);

        if(count==null){
            count=0;
        }
        count++;

        map.put(words,count);

        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Set<Map.Entry<String, Integer>> set = map.entrySet();

        for (Map.Entry<String, Integer> entry:set){
            System.out.println(entry);
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
