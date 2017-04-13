package util;

import java.io.BufferedReader;

import org.json.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Synonymous {
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

			try {
				JSONObject obj = new JSONObject(answer);
				answer = "";
				JSONArray results = obj.getJSONArray("sinonimos");
				for (int i = 0; i < results.length(); i++){
					if(i>0)answer+=", ";
					answer+= results.getJSONObject(i).getString("sinonimo");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			answer = null;
//			LOGGER.info("MalformedURLException: " + e.getClass());
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
			String line;
			while ( ((line = br.readLine()) != null)) {
				answer += line;
			}
		} catch (MalformedURLException e) {
			answer = null;
//			LOGGER.info("MalformedURLException: " + e.getClass());
		} catch (IOException e) {
			answer = null;
//			LOGGER.info("IOException: " + e.getClass());
		}
		return answer;
	}
}
