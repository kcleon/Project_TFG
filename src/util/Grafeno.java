package util;

import java.util.Scanner;

public abstract class Grafeno {
	protected static Scanner teclado;
	protected String word ="";
	
	public String input(){
		System.out.println("'exit' para salir.");
		String word = "";
		System.out.print(" > ");
		word = teclado.nextLine();
		return word;
	}
}
