package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;
import java.util.Scanner;

import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.json.JSONException;
import org.json.JSONObject;


public class EasyWord {
	private static Logger LOGGER = Logger.getLogger("InfoLogging");
	private static final String JSON_KEY_OBJECT = "palabraSencilla";
	
	protected static Scanner teclado;
	public static Boolean isEasy(String word){
//		int easy = -1;
//		if((easy = isEasyJSON(word))== -1)
//			easy = isEasyXML(word);
		return isEasyJSON(word);
	}
	
	private static Boolean isEasyJSON(String word){
//		int easy = -1;
		Boolean easy = null;
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
			answer = obj.get(JSON_KEY_OBJECT);
			easy = (Boolean)answer;
			
		} catch (MalformedURLException e) {
			easy = isEasyXML(word);
			LOGGER.info("MalformedURLException: " + e.getClass());
		} catch (JSONException e) {
			easy = isEasyXML(word);
			LOGGER.info("MalformedURLException: " + word);
			e.printStackTrace();
		} catch (IOException e) {
			isEasyXML(word);
			LOGGER.info("IOException: " + e.getClass());
		}
		return easy;
	}
	
	private static Boolean isEasyXML(String word){
		Boolean easy = null;
		try {
			// Se abre la conexión
			URL url = new URL("http://sesat.fdi.ucm.es:8080/servicios/rest/palabras/xml/"+word);
			URLConnection conexion = url.openConnection();
			conexion.connect();

			// Lectura
			InputStream is = conexion.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String leido = br.readLine();

			org.jdom.input.SAXBuilder saxBuilder = new SAXBuilder();
		    org.jdom.Document doc = saxBuilder.build(new StringReader(leido));
		    String answer = doc.getRootElement().getText();
			easy = "true".equals(answer);
		} catch (MalformedURLException e) {
			LOGGER.info("MalformedURLException: " + e.getClass());
		} catch (JDOMException e) {
			LOGGER.info("JDOMException: " + e.getClass());
		} catch (IOException e) {
			LOGGER.info("IOException: " + e.getClass());
		}
		
		return easy;
	}
}

