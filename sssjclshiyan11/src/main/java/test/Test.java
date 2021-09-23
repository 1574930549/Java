package test;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;

import Topology.TopologyFactory;

public class Test {
	public static void main(String[] args) {
		
		StormTopology topology = TopologyFactory.redisFactory();
		
		Config config = new Config();
		
		LocalCluster cluster = new LocalCluster();
		
		cluster.submitTopology("topology", config, topology);
		
	}
}
