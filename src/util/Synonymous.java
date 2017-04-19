package util;

import java.io.BufferedReader;

import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.json.*;
import org.jdom.Element;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.logging.Logger;

public class Synonymous {
	private static Logger LOGGER = Logger.getLogger("InfoLogging");
	private static final String JSON_KEY_ARRAY = "sinonimos";
	private static final String JSON_KEY_OBJECT = "sinonimo";
	public static String[] getSynonymous(String word){
		String[] text = getSynonymousJSON(word);
		if(text == null) text = getSynonymousXML(word);
		return text;
	}
	
	private static String[] getSynonymousJSON(String word){
		String[] answer = null;
		try {
			// Se abre la conexión
			URL url = new URL("http://sesat.fdi.ucm.es:8080/servicios/rest/sinonimos/json/"+word);
			URLConnection conexion = url.openConnection();
			conexion.connect();

			// Lectura
			InputStream is = conexion.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String webContent = br.readLine();

			JSONObject obj = new JSONObject(webContent);
			//TODO: ver cómo hacer parse cuando no hay sinonimos. ejemplo: el (determinante)
			
			JSONArray results = null;
			if(obj.has(JSON_KEY_ARRAY) && obj.get(JSON_KEY_ARRAY).toString().length()>2){//cuando es vacio, equivale a "{}" con length 2
				results = obj.getJSONArray(JSON_KEY_ARRAY);
				answer = new String[results.length()];
				for (int i = 0; i < results.length(); i++){
					answer[i] = results.getJSONObject(i).getString(JSON_KEY_OBJECT);
				}
			}
		} catch (MalformedURLException e) {
			answer = null;
			LOGGER.info("MalformedURLException: " + word);
		}catch (JSONException e) {
			answer = null;
			LOGGER.info("JSONException: " + word);
			e.printStackTrace();
		} catch (IOException e) {
			answer = null;
			LOGGER.info("IOException: " + word);
		}
		return answer;
	}
	
	private static String[] getSynonymousXML(String word){
		String[] answer = null;
		try {
			// Se abre la conexión
			URL url = new URL("http://sesat.fdi.ucm.es:8080/servicios/rest/sinonimos/xml/"+word);
			URLConnection conexion = url.openConnection();
			conexion.connect();

			// Lectura
			InputStream is = conexion.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String leido = br.readLine();

			org.jdom.input.SAXBuilder saxBuilder = new SAXBuilder();
		    org.jdom.Document doc = saxBuilder.build(new StringReader(leido));
		    @SuppressWarnings("rawtypes")
			List results = doc.getRootElement().getChildren();
		    if(results.size()>0){
		    	answer = new String[results.size()];
		    	for (int i = 0; i < results.size(); i++){
		    		answer[i] += ((Element)results.get(i)).getText();
		    	}
		    }
		} catch (MalformedURLException e) {
			answer = null;
			LOGGER.info("MalformedURLException: " + e.getClass());
		} catch (JDOMException e) {
			answer = null;
			LOGGER.info("JDOMException: " + e.getClass());
		}  catch (IOException e) {
			answer = null;
			LOGGER.info("IOException: " + e.getClass());
		}
		return answer;
	}
}
