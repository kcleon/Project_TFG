package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EasySynonymous {
	private static Logger LOGGER = Logger.getLogger("InfoLogging");
	private static final String JSON_KEY_ARRAY = "conversion";
	private static final String JSON_KEY_OBJECT = "conversion";
	
	public static String[] getSynonymous(String word){
		String[] text = getSynonymousJSON(word);
//		if(text == null) text = getSynonymousXML(word);	
		return text;
	}
	
	private static String[] getSynonymousJSON(String word){
		String[] answer = null;
//		try {
//			// Se abre la conexión
//			URL url = new URL("http://sesat.fdi.ucm.es:8080/servicios/rest/conversion/json/"+word);
//			URLConnection conexion = url.openConnection();
//			conexion.connect();
//
//			// Lectura
//			InputStream is = conexion.getInputStream();
//			BufferedReader br = new BufferedReader(new InputStreamReader(is));
//			String webContent = br.readLine();
//
//			JSONObject obj = new JSONObject(webContent);
//			//TODO: ver cómo hacer parse cuando no hay sinonimos. ejemplo: el (determinante)
//			
//			answer = new String[1];
//			if(obj.has(JSON_KEY_OBJECT) && obj.get(JSON_KEY_OBJECT).toString().length()>2)
//			answer[0] = obj.getString(JSON_KEY_OBJECT);
//		} catch (MalformedURLException e) {
//			answer = null;
//			LOGGER.info("MalformedURLException: " + word);
//		}catch (JSONException e) {
//			answer = null;
//			LOGGER.info("JSONException: " + word);
//			e.printStackTrace();
//		} catch (IOException e) {
//			answer = null;
//			LOGGER.info("IOException: " + word);
//		}
		
		URL url = null;
	    BufferedReader reader = null;
	    StringBuilder stringBuilder;

	    try
	    {
	      // create the HttpURLConnection
//	      url = new URL("http://sesat.fdi.ucm.es:8080/servicios/rest/conversion/json/edificio");
	    	url = new URL("http://services.groupkt.com/country/get/iso2code/IN");
	      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	      
	      // just want to do an HTTP GET here
	      connection.setRequestMethod("GET");
	      
	      // uncomment this if you want to write output to this url
	      //connection.setDoOutput(true);
	      
	      // give it 15 seconds to respond
	      connection.setReadTimeout(15*1000);
	      connection.connect();

	      // read the output from the server
	      reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	      stringBuilder = new StringBuilder();

	      String line = null;
	      while ((line = reader.readLine()) != null)
	      {
	        stringBuilder.append(line + "\n");
	      }
	      stringBuilder.toString();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    finally
	    {
	      // close the reader; this can throw an exception too, so
	      // wrap it in another try/catch block.
	      if (reader != null)
	      {
	        try
	        {
	          reader.close();
	        }
	        catch (IOException ioe)
	        {
	          ioe.printStackTrace();
	        }
	      }
	    }
		return answer;
	}


}
