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
import org.json.JSONException;
import org.json.JSONObject;

import sun.net.www.protocol.http.HttpURLConnection;

public class EasyWord {
//	private static Logger LOGGER = Logger.getLogger("InfoLogging");
	private static final String JSON_KEY_OBJECT = "palabraSencilla";
	
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
			URL url = new URL("http://sesat.fdi.ucm.es:8080/servicios/rest/palabras/json/"+word);
			 URLConnection urlConn=url.openConnection();
			  if (urlConn instanceof HttpURLConnection) {
			    HttpURLConnection httpConn=(HttpURLConnection)urlConn;
			    httpConn.setRequestMethod("POST");
			  }
			  urlConn.setDoInput(true);
			  urlConn.setDoOutput(true);
			  urlConn.setUseCaches(false);
			  urlConn.setDefaultUseCaches(false);
			 // return urlConn;
			
			
			
			
			
			// Lectura
			InputStream is = urlConn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			Object answer = br.readLine();
			
			JSONObject obj = new JSONObject(answer.toString());
			answer = obj.get(JSON_KEY_OBJECT);
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
			InputStream is = conexion.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String leido = br.readLine();

			org.jdom.input.SAXBuilder saxBuilder = new SAXBuilder();
		    org.jdom.Document doc = saxBuilder.build(new StringReader(leido));
		    String answer = doc.getRootElement().getText();
		    easy = ("true".equals(answer) ? 1 : 0);
		} catch (MalformedURLException e) {
			easy = -1;
//			LOGGER.info("MalformedURLException: " + e.getClass());
		} catch (JDOMException e) {
		    // handle JDOMException
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

