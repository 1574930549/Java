package bolt;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.apache.storm.tuple.Fields;

public  class ParticipleBolt extends BaseRichBolt {
	OutputCollector collector;

	public void execute(Tuple arg0) {
		String content = arg0.getStringByField("content");

		if("".equals(content)) {
			collector.emit(new Values(""));
			return;
		}

		try(Analyzer analyzer = new IKAnalyzer(true);) {
			TokenStream stream = analyzer.tokenStream("content", new StringReader(content));
			CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);
			stream.reset();
			while (stream.incrementToken()) {
	            collector.emit(new Values(cta.toString()));
	        }
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void prepare(Map arg0, TopologyContext arg1, OutputCollector arg2) {
		collector = arg2;
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}

}
