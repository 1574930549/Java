package Lucene;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class LuceneUtil {
	
	public LuceneUtil() {
		
	}
	
	public static List<String> getIKAnalyzer(String str) {
		Analyzer analyzer = new IKAnalyzer(true);
		try {	
			return stringToWordList(analyzer, str);
		}
		catch(IOException e) {
			analyzer.close();
			return null;
		}
	}
	
	public static List<String> getSmartChineseAnalyzer(String str) {
		Analyzer analyzer = new SmartChineseAnalyzer();
		try {	
			return stringToWordList(analyzer, str);
		}
		catch(IOException e) {
			analyzer.close();
			return null;
		}
	}
	
	public static List<String> stringToWordList(Analyzer analyzer,String str) throws IOException {
		StringReader reader = new StringReader(str);
		TokenStream toStream = analyzer.tokenStream("content",reader);
		toStream.reset();
		CharTermAttribute teAttribute = toStream.getAttribute(CharTermAttribute.class);
		List<String> wordlist = new ArrayList<String>();
		while(toStream.incrementToken()){
			String word = teAttribute.toString();
			if(word.length() > 1 && !isInteger(word)) {
				wordlist.add(word);
			}
		}
		analyzer.close();
		return wordlist;
	}
	
	private static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile(".?([\\d]+.)*[\\d]+.*");
		return pattern.matcher(str).matches();
	}
	
//	public static void main(String[] args) {
//		String str = "hadoop1.2.3+hbase2.1.3 大数据你好世界 222 12.77.88 2--- _66_1 6-4w8.1p";
////		List<String> wordlist = getIKAnalyzer(str);
//		List<String> wordlist = getSmartChineseAnalyzer(str);
//		for(String word : wordlist) {
//			System.out.print(word+"  ");
//		}
//	}

}
