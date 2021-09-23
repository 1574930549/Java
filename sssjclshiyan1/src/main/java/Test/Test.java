package Test;


import Topology.TopologyFactory;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;

public class Test {
    public static void main(String[] args) throws Exception {

        StormTopology topology = TopologyFactory.factory();
        Config cfg = new Config();
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("WordCount",cfg,topology);
    }
}
