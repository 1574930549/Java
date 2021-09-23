package bean;

import java.util.Date;

public class WordFreq {
	private String word;
	private int freq;
	private Date date;

	public void setWord(String word) {
		this.word = word;
	}

	public String getWord() {
		return word;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public int getFreq() {
		return freq;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

}
