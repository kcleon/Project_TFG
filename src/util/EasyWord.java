package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
//import java.util.logging.Logger;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EasyWord {
//	private static Logger LOGGER = Logger.getLogger("InfoLogging");
	protected static Scanner teclado;
	public static int isEasy(String word){
		int easy = -1;
		if((easy = isEasyJSON(word))== -1)
			easy = isEasyXML(word);
		return easy;
	}
	
	private static int isEasyJSON(String word){
		int easy = -1;
		try {
			// Se abre la conexión
			URL url = new URL("http://sesat.fdi.ucm.es:8080/servicios/rest/palabras/json/"+word);
			URLConnection conexion = url.openConnection();
			conexion.connect();

			// Lectura
			InputStream is = conexion.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			Object answer = br.readLine();
			try {
				if(true)throw new IOException();
				@SuppressWarnings("unused")
				JSONObject obj = new JSONObject(answer);
				answer = obj.getString("palabraSencilla");
				easy = ("true".equals(answer) ? 1 : 0);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (MalformedURLException e) {
//			LOGGER.info("MalformedURLException: " + e.getClass());
		} catch (IOException e) {
//			LOGGER.info("IOException: " + e.getClass());
		}
		return easy;
	}
	
	private static int isEasyXML(String word){
		int easy = -1;
		try {
			// Se abre la conexión
			URL url = new URL("http://sesat.fdi.ucm.es:8080/servicios/rest/palabras/xml/"+word);
			URLConnection conexion = url.openConnection();
			conexion.connect();

			// Lectura
			InputStream is = conexion.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			char[] buffer = new char[1000];
			int leido;
			while ((leido = br.read(buffer)) > 0) {
				String answer = new String(buffer, 0, leido).split(">")[1];
				answer = answer.substring(0, answer.indexOf("</"));
				easy = ("true".equals(answer) ? 1 : 0);
			}
		} catch (MalformedURLException e) {
			easy = -1;
//			LOGGER.info("MalformedURLException: " + e.getClass());
		} catch (IOException e) {
			easy = -1;
//			LOGGER.info("IOException: " + e.getClass());
		}
		
		return easy;
	}

	public String input(){
		System.out.println("'exit' para salir.");
		String word = "";
		do{
			System.out.print(" > ");
			word = teclado.nextLine();
			isEasy(word.split("\\s+")[0]);
		}while(!"exit".equalsIgnoreCase(word.trim()));
		return word;
	}
}
