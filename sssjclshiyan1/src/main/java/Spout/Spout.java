package Spout;

import org.apache.storm.shade.org.apache.commons.io.FileUtils;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Spout extends BaseRichSpout {

    private SpoutOutputCollector collector;


    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {

        collector  = spoutOutputCollector;
    }

    public void nextTuple() {


        File filename = new File("F:\\代码\\Java\\sssjclshiyan1\\src\\test.txt");
        List<String> lines = null;
        try {
            lines = FileUtils.readLines(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(String line:lines){
                    //逐行发送
                    collector.emit(new Values(line));
                }
        }


    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

        outputFieldsDeclarer.declare(new Fields("lines"));
    }
}
