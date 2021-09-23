package test;

import topology.TopologyFactory;
import util.JedisUtil;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class zlh {

	public static void main(String[] args) throws MalformedURLException, IOException {

		Document doc = Jsoup.parse(new URL("https://blog.csdn.net/weixin_45267419"), 50000);
		Elements titles = doc.select("h4");
		StringBuffer sb = new StringBuffer();

		for(Element title: titles) {
			sb.append(title.html() + "\n");
		}
		sb.delete(sb.length() - 1, sb.length());

		JedisUtil.getConnection().set("!", sb.toString());

		StormTopology topology = TopologyFactory.factory();

		LocalCluster cluster = new LocalCluster();

		Config config = new Config();

		cluster.submitTopology("1814010130", config, topology);

	}
}

