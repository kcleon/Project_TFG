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
	
//	public static void main(String[] args) {
//		teclado = new Scanner(System.in);
//		String op = "", word ="computadora";
//		isEasy(word);
//		System.exit(0);
//		menu();
//		do{
//			op = input();
//			if(!"exit".contains(op.split("\\s+")[0]))
//				word = op.toLowerCase().trim().split("\\s+")[1].trim();
//			op = op.split("\\s+")[0].toLowerCase().trim();
//			switch(op){
//				case "ew":
//				case "easy": isEasy(word); break;
//				case "sn": 
//				case "synonymous": getSynonymous(word); break;
//				case "ews":
//				case "easy syn(ews)": ; break;
//				case "df":
//				case "define(df)": ; break;
//				case "tr":
//				case "translate(tr)": ; break;
//				case "an":
//				case "antonym": ; break;
//				case "exit":
//				default: LOGGER.info("Saliendo...");break;
//			}
//		}while(!"exit".equals(op));
//		
//		teclado.close();
//	}
	
	public static void main(String[] args) throws IOException {
		JFileChooser fileChooser = new JFileChooser("./src/source");
		fileChooser.setAcceptAllFileFilterUsed(false);
//      FileNameExtensionFilter filter = new FileNameExtensionFilter("Prolog file (.pro)", "pro");
//      fileChooser.addChoosableFileFilter(filter);
		fileChooser.showOpenDialog(null);
		File inFile = fileChooser.getSelectedFile();
		FileReader fr = new FileReader(inFile);
		BufferedReader bufReader = new BufferedReader (fr);
		String line = bufReader.readLine();
		
//		while(line!=null){
//			line += bufReader.readLine();
//		}
		parseSummary(line);
		
		bufReader.close();
	}

	public static ArrayList<String> parseSummary(String text){
		ArrayList<String> sentences = new ArrayList<String>();
		
		//Separamos párrafos
		char[] tmp = text.toCharArray();
		//TODO: leer caracter a caracter hasta encontrar el final de un palabra
		
		
		
		return sentences;
	}
	
	public static void isEasy(String word){
		int i = EasyWord.isEasy(word.split("\\s+")[0]);
		switch(i){
			case 0: System.out.println(" no"); break; 
			case 1: System.out.println(" yes");  break;
			default: LOGGER.info("FALLO al conectar");break;
		}
	}
	
	public static void getSynonymous(String word){
		String text = Synonymous.getSynonymous(word);
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