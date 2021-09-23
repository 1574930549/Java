package bolt;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.wltea.analyzer.lucene.IKAnalyzer;

import bean.BiWordFreq;
import dao.BiWordFreqDAO;

public class RelationBolt extends BaseRichBolt {
	private Map<String, Integer> wordFreq;

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		// TODO Auto-generated method stub
		wordFreq = new HashMap<String, Integer>();
	}

	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		String content = input.getStringByField("content");
		List<String> wordList = new ArrayList<String>();

		if("".equals(content)) {
			System.out.println("关联词频：");
			System.out.println(wordFreq);
			System.out.println();

			Set<String> set = wordFreq.keySet();
			for(String s: set) {
				BiWordFreq wf = new BiWordFreq();
				String[] words = s.split("-");

				wf.setWord1(words[0]);
				wf.setWord2(words[1]);
				wf.setFreq(wordFreq.get(s));
				wf.setDate(new Date());

				new BiWordFreqDAO().saveBiWordFreq(wf);
			}
			return;
		}

		try(Analyzer analyzer = new IKAnalyzer(true);) {
			TokenStream stream = analyzer.tokenStream("content", new StringReader(content));
			CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);
			stream.reset();
			while (stream.incrementToken()) {
				wordList.add(cta.toString());
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}

		for(int i = 0; i < wordList.size(); i++) {
			for(int j = i + 1; j < wordList.size(); j++) {
				String str1 = wordList.get(i);
				String str2 = wordList.get(j);
				String key = null;

				if(str1.compareTo(str2) == 0) {
					continue;
				}
				else if(str1.compareTo(str2) > 0) {
					key = str2 + "-" + str1;
				}
				else {
					key = str1 + "-" + str2;
				}

				if(!wordFreq.containsKey(key)) {
					wordFreq.put(key, 1);
				}
				else {
					wordFreq.replace(key, wordFreq.get(key) + 1);
				}
			}
		}

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub

	}

}
