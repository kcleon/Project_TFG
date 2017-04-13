package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
//import java.util.logging.Logger;
import java.util.Scanner;

import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
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
			JSONObject obj = new JSONObject(answer.toString());
			answer = obj.get("palabraSencilla");
			easy = ((Boolean)answer ? 1 : 0);
			
		} catch (MalformedURLException e) {
//			LOGGER.info("MalformedURLException: " + e.getClass());
		} catch (JSONException e) {
			// TODO personalizar excepcion
			e.printStackTrace();
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
//			InputStream is = conexion.getInputStream();
//			BufferedReader br = new BufferedReader(new InputStreamReader(is));
//			char[] buffer = new char[1000];
//			int leido;
//			while ((leido = br.read(buffer)) > 0) {
//				String answer = new String(buffer, 0, leido).split(">")[1];
//				answer = answer.substring(0, answer.indexOf("</"));
//				easy = ("true".equals(answer) ? 1 : 0);
//			}

			String xml = "<message>HELLO!</message>";
			org.jdom.input.SAXBuilder saxBuilder = new SAXBuilder();
			try {
			    org.jdom.Document doc = saxBuilder.build(new StringReader(xml));
			    String message = doc.getRootElement().getText();
			    System.out.println(message);
			} catch (JDOMException e) {
			    // handle JDOMException
			} catch (IOException e) {
			    // handle IOException
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

