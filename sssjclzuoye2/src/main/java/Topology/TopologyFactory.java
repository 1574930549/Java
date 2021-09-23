package Topology;

import Bolt.*;
import Spout.Spout1;
import Spout.Spout2;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;


public class TopologyFactory {
	public static StormTopology factory() {
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("001", new Spout1());
		builder.setSpout("002", new Spout2());

		builder.setBolt("011", new Bolt1()).shuffleGrouping("001").shuffleGrouping("002","jishustream");
		builder.setBolt("012", new Bolt2()).shuffleGrouping("002","oushustream");
		builder.setBolt("021", new Bolt3()).shuffleGrouping("011");
		builder.setBolt("022", new Bolt4()).shuffleGrouping("012","er");

		builder.setBolt("023", new Bolt5()).shuffleGrouping("012","si");;
		builder.setBolt("031", new Bolt6()).shuffleGrouping("021").shuffleGrouping("022").shuffleGrouping("023");

		return builder.createTopology();

	}
}

