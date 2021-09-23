package bean;

import java.util.Date;

public class BiWordFreq {
	private String word1;
	private String word2;
	private Date date;
	private int freq;

	public void setWord1(String word1) {
		this.word1 = word1;
	}

	public String getWord1() {
		return word1;
	}

	public void setWord2(String word2) {
		this.word2 = word2;
	}

	public String getWord2() {
		return word2;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public int getFreq() {
		return freq;
	}

}
