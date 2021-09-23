package bolt;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;

import bean.WordFreq;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import dao.WordFreqDAO;

public class FrequencyBolt extends BaseRichBolt {
	private Map<String, Integer> wordFreq;

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		// TODO Auto-generated method stub
		wordFreq = new HashMap<String, Integer>();
	}

	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		String word = input.getStringByField("word");
		if("".equals(word)) {
			System.out.println("词频：");
			System.out.println(wordFreq);
			System.out.println();

			Set<String> set = wordFreq.keySet();
			for(String s: set) {
				WordFreq wf = new WordFreq();

				wf.setWord(s);
				wf.setFreq(wordFreq.get(s));
				wf.setDate(new Date());

				new WordFreqDAO().saveWordFreq(wf);
			}
			return;
		}

		if(!wordFreq.containsKey(word)) {
			wordFreq.put(word, 1);
		}
		else {
			wordFreq.replace(word, wordFreq.get(word) + 1);
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub

	}

}
