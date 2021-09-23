package Topology;

import Bolt.Bolt1;
import Bolt.Bolt2;
import Spout.Spout;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;

public class TopologyFactory {

    public static StormTopology factory(){
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("Spout",new Spout());//文件读取
        builder.setBolt("B1",new Bolt1()).shuffleGrouping("Spout");//分割
        builder.setBolt("B2",new Bolt2()).shuffleGrouping("B1");//统计

        return builder.createTopology();
    }
}
