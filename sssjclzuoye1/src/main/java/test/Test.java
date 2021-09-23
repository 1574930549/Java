package test;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;


import org.apache.storm.generated.StormTopology;
import topology.TopologyFactory;

public class Test {
	public static void main(String[] args) {
		 StormTopology topology = TopologyFactory.factory();
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("lhc",new Config(), topology);

	}
}
