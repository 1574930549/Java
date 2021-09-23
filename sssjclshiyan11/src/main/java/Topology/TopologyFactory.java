package Topology;

import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;

import Bolt.AuthorBolt;
import Bolt.DateBolt;
import Bolt.TitleBolt;
import Spout.RedisSpout;

public class TopologyFactory {
	public static StormTopology redisFactory() {
		TopologyBuilder builder = new TopologyBuilder();
		
		builder.setSpout("redisspout", new RedisSpout());
		
		builder.setBolt("titlebolt", new TitleBolt()).shuffleGrouping("redisspout","title");
		builder.setBolt("authorebolt", new AuthorBolt()).shuffleGrouping("redisspout","author");
		builder.setBolt("datebolt", new DateBolt()).shuffleGrouping("redisspout","date");
		
		return builder.createTopology();
	}
	
}
