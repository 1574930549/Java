package Test;

import Topology.TopologyFactory;
import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.generated.StormTopology;


public class Test {
	public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
		 StormTopology topology = TopologyFactory.factory();
		StormSubmitter.submitTopology(args[0],new Config(), topology);

	}
}
