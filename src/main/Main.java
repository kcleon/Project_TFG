package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import util.EasyWord;
import util.Synonymous;

public class Main {
	private static Logger LOGGER = Logger.getLogger("InfoLogging");
	private static Scanner teclado;
	
	public static void main(String[] args) throws IOException {
		JFileChooser fileChooser = new JFileChooser("./src/source");
		fileChooser.setAcceptAllFileFilterUsed(false);
//      FileNameExtensionFilter filter = new FileNameExtensionFilter("Prolog file (.pro)", "pro");
//      fileChooser.addChoosableFileFilter(filter);
		fileChooser.showOpenDialog(null);
		File inFile = fileChooser.getSelectedFile();
		FileReader fr = new FileReader(inFile);
		BufferedReader bufReader = new BufferedReader (fr);
		String line ="", text = bufReader.readLine();
		
		while(line!=null){
			text+=line;
			line = bufReader.readLine();
		}
		String[] words = parseSummary(text);
		
		bufReader.close();
		Simplex simplex = new Simplex();
		simplex.simplex(words);
	}

	public static String[] parseSummary(String text){
		String delimitadores= "[ .,;?:)(!¡¿\'\"\\[\\]]+";
		return text.split(delimitadores);
	}	
	
	public static void isEasy(String word){
		Boolean easy = EasyWord.isEasy(word.split("\\s+")[0]);
		if(easy == false) System.out.println(" no"); 
		else if(easy == true) System.out.println(" yes");  
		else LOGGER.info("FALLO al conectar");
	}
	
	public static void getSynonymous(String word){
		String[] text = Synonymous.getSynonymous(word);
		if(text == null) LOGGER.info("FALLO al conectar");
		else System.out.println(text);
	}
	
	public static void getEasySynonymous(String word){
		String[] text = Synonymous.getSynonymous(word);
		if(text == null) LOGGER.info("FALLO al conectar");
		else System.out.println(text);
	}
	
	private static void menu(){
		System.out.println("easy(ew) | synonymous(sn) | easy syn(ews)");
		System.out.println("define(df) | translate(tr) | antonym(an) | exit");
		System.out.println("option word");
	}
	
	private static String input(){
//		System.out.println("'exit' para salir..gracias.");
		System.out.print(" > ");
		return teclado.nextLine();
	}
	
}