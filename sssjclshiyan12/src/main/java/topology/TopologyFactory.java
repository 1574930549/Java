package topology;

import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.generated.StormTopology;

import spout.*;
import bolt.*;

public class TopologyFactory {

	public static StormTopology factory() {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("s1", new Spout());
		builder.setBolt("b1", new ParticipleBolt()).shuffleGrouping("s1");
		builder.setBolt("b2", new FrequencyBolt()).shuffleGrouping("b1");
		builder.setBolt("b3", new RelationBolt()).shuffleGrouping("s1");
		return builder.createTopology();
	}

}
