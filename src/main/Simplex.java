package main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.EasySynonymous;
import util.EasyWord;

public class Simplex {
	private final Pattern p = Pattern.compile("[a-zA-Z]*");
	private Matcher m;

	public void simplex(String[] text){
		String[] word;
		next: for(int i = 0; i < text.length; i++){
			m = p.matcher(text[i]);
			if(!m.matches()) continue next;
			if(!EasyWord.isEasy(text[i])){
				if((word = EasySynonymous.getSynonymous(text[i]))[0] != null){
					text[i] = word[0];
				}
			}
		}

		String summay = "";
		for(String s: text){
			summay += p.matcher(s).matches() ? " "+s+" " : " "+s+" ";
		}
		System.out.println(summay);
	}
}