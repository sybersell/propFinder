package cookieManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class csvJson
{
	public static void main(String args[]) throws Exception {
String csvFile= args[0];

try (InputStream in = new FileInputStream(csvFile);) {
    csvParser csv = new csvParser(true, ',', in );
    
    
    JSONArray thePropsArr = new JSONArray();
    List < String > fieldNames = null;
    if (csv.hasNext()) fieldNames = new ArrayList < > (csv.next());
    List < Map < String, String >> list = new ArrayList < > ();
    while (csv.hasNext()) {
    	JSONObject theProp = new JSONObject();
        List < String > x = csv.next();
        //System.out.println(fieldNames.size());
        Map < String, String > obj = new LinkedHashMap < > ();
        for (int i = 0; i < fieldNames.size()-1; i++) {
        	
        	
            theProp.put(fieldNames.get(i), x.get(i));
        	
        	
        	//System.out.println(fieldNames.get(i)+":"+x.get(i));
            obj.put(fieldNames.get(i), x.get(i));
            if (fieldNames.get(i).equals("the_geom_webmercator")){
            propLiSearch liSearch = new propLiSearch();
           JSONObject liViolations = liSearch.readJsonFromUrl("https://data.phila.gov/carto/api/v2/sql?q=SELECT%20*%20FROM%20LI_VIOLATIONS%20WHERE%20the_geom_webmercator=%27"+x.get(i)+"%27");
           theProp.put("violations", liViolations.get("rows"));
            }
            
            if (fieldNames.get(i).equals("owner_1")){
            	getdocket getdock = new getdocket();
            	JSONArray theDockets = getdocket.docketSearch(x.get(i));
            	
            	//ystem.out.println("########: "+theDockets); 
            	
            	theProp.put("dockets",theDockets);
            }

        }
        //thePropsArr.put(theProp);
        System.out.println(theProp.toString(4));
        postToES(theProp.toString());
    }

}
	}
	
	public static void postToES(String bodyText) throws IOException{
		DefaultHttpClient client = new DefaultHttpClient();
        String url = "https://es5.feithapps.com/moms/data";
		 HttpPost post = new HttpPost("https://es5.feithapps.com/props/property");

		 StringEntity input = new StringEntity(bodyText);

		 post.setEntity(input);

		 HttpResponse response = client.execute(post);

		 BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		String line = "";

		 while ((line = rd.readLine()) != null) {

		System.out.println(line);
        //StringBuilder result = new StringBuilder();
        //try {
        //    HttpPut putRequest = new HttpPut(url);
        //    putRequest.addHeader("Content-Type", "application/json");
        //    putRequest.addHeader("Accept", "application/json");
        //    JSONObject keyArg = new JSONObject();
        //    StringEntity input = new StringEntity(bodyText);
            

        //    putRequest.setEntity(input);
        //    HttpResponse response = client.execute(putRequest);
        //    if (response.getStatusLine().getStatusCode() != 200) {
        //        throw new RuntimeException("Failed : HTTP error code : "
        //                + response.getStatusLine().getStatusCode());
        //    }
        //    BufferedReader br = new BufferedReader(new InputStreamReader(
        //            (response.getEntity().getContent())));
        //    String output;
        //    while ((output = br.readLine()) != null) {
        //        result.append(output);
       //     }
       // } catch (ClientProtocolException e) {
       //     e.printStackTrace();
       // } catch (IOException e) {
       //     e.printStackTrace();
       // }
        //return result.toString();
    }
	}
}