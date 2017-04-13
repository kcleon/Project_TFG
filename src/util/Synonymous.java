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

public class Synonymous {
	private static final String JSON_KEY_ARRAY = "sinonimos";
	private static final String JSON_KEY_OBJECT = "sinonimo";
	public static String getSynonymous(String word){
		String text = getSynonymousJSON(word);
		if(text == null) text = getSynonymousXML(word);
		return text;
	}
	
	private static String getSynonymousJSON(String word){
		String answer = "";
		try {
			// Se abre la conexión
			URL url = new URL("http://sesat.fdi.ucm.es:8080/servicios/rest/sinonimos/json/"+word);
			URLConnection conexion = url.openConnection();
			conexion.connect();

			// Lectura
			InputStream is = conexion.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			answer = br.readLine();

			JSONObject obj = new JSONObject(answer);
			answer = "";
			JSONArray results = obj.getJSONArray(JSON_KEY_ARRAY);
			for (int i = 0; i < results.length(); i++){
				if(i>0)answer+=", ";
				answer+= results.getJSONObject(i).getString(JSON_KEY_OBJECT);
			}
		} catch (MalformedURLException e) {
			answer = null;
//			LOGGER.info("MalformedURLException: " + e.getClass());
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			answer = null;
//			LOGGER.info("IOException: " + e.getClass());
		}
		return answer;
	}
	
	private static String getSynonymousXML(String word){
		String answer = "";
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
			List syns = doc.getRootElement().getChildren();

		    for (int i = 0; i < syns.size(); i++){
				if(i>0)answer+=", ";
		    	answer += ((Element)syns.get(i)).getText();
		    }
		} catch (MalformedURLException e) {
			answer = null;
//			LOGGER.info("MalformedURLException: " + e.getClass());
		} catch (JDOMException e) {
		    // handle JDOMException
		}  catch (IOException e) {
			answer = null;
//			LOGGER.info("IOException: " + e.getClass());
		}
		return answer;
	}
}
