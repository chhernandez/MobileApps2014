package team.kyb.extragames;

import java.util.ArrayList;
import java.util.Random;

public class FillInTheBlankGame {

	Random rd = new Random();
	private String mainString; 
	private String missingString;
	private String word1, word2, word3, word4;
	private ArrayList<WordOrder> words = new ArrayList<WordOrder>();
	private String[] wordSeq = new String[4];
	private int[] orderSeq = new int[4];
	

	public FillInTheBlankGame(String input) {
		mainString = input;
		missingString = input;
		
		word1 = getWord(missingString, 1);
		WordOrder a = new WordOrder(word1, 1);
		words.add(a);
		
		word2 = getWord(missingString, 2);
		WordOrder b = new WordOrder(word2, 2);
		words.add(b);
		
		word3 = getWord(missingString, 3);
		WordOrder c = new WordOrder(word3, 3);
		words.add(c);
		
		word4 = getWord(missingString, 4);
		WordOrder d = new WordOrder(word4, 4);
		words.add(d);
		
		int i = 0;
		while (!words.isEmpty()) {
			int rdNumb = rd.nextInt(words.size());
			WordOrder temp = words.get(rdNumb);
			wordSeq[i] = temp.word;
			orderSeq[i] = temp.order;
			i++;
			words.remove(rdNumb);
		}
	}

	public String getWord(String passageFull, int order) {
		int posOfWord = -1;
		String randomMissingWord = " ";
		while (randomMissingWord.length() < 4) {
			int rdint = rd.nextInt(passageFull.length());
			posOfWord = passageFull.indexOf(" ", rdint);
			posOfWord++;
			
			if (passageFull.charAt(posOfWord) == '-') {
				continue;
			}

			// Normal case
			int endPos = passageFull.indexOf(" ", posOfWord);

			// Case words at the end
			if (endPos < 0) {
				endPos = passageFull.indexOf(".", posOfWord);
			}

			// Case word follow by a comma
			if (passageFull.charAt(endPos - 1) == ',') {
				endPos--;
			}

			randomMissingWord = passageFull.substring(posOfWord, endPos);
		}
		String dashes = "-(" + order + ")-";

		this.missingString = passageFull.replaceFirst(randomMissingWord, dashes);


		return randomMissingWord;
	}
	
	public String getMissingString() {
		return missingString;
	}
	
	public String getCorrectString() {
		return mainString;
	}
	
	public String[] getWords() {
		return wordSeq;
	}
	
	public int[] getOrder() {
		return orderSeq;
	}
	
	public boolean rightAnswer(int[] answers) {
		return orderSeq.equals(answers);
	}
	
	private class WordOrder {
		String word; 
		int order;
		
		public WordOrder(String word, int order) {
			this.word = word;
			this.order = order;
		}
	}

}
