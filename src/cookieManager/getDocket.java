package cookieManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.commons.logging.LogFactory;

class getdocket{
	
@SuppressWarnings("deprecation")
public static JSONArray docketSearch(String ownerName) throws IOException{
	JSONObject theReturnJson = new JSONObject();
	
	String[] elements = ownerName.split(" ");
	String fName =null;
	String lName=elements[0];
	if (elements.length>1){
	fName = elements[1];
	}
//System.out.println(jsonBody);
	CloseableHttpClient client = HttpClients.createDefault();
        String url = "https://fjdefile.phila.gov/efsfjd/zk_fjd_public_qry_02.zp_judgment_rslt_idx";
		 HttpPost post = new HttpPost(url);
		 
		 List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		 params.add(new BasicNameValuePair("last_name", lName));
		 params.add(new BasicNameValuePair("first_name", fName));
		 params.add(new BasicNameValuePair("uid", "!YnTNooNzIolqsSYDxam"));
		 params.add(new BasicNameValuePair("o", "F1t6GYfeTzqzIea"));
		 post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		 CloseableHttpResponse response = client.execute(post);
		 
		 HttpEntity entity = response.getEntity();
		 String responseString = EntityUtils.toString(entity, "UTF-8");
		 
		    //assertThat(response.getStatusLine().getStatusCode(), equals(200));
		 Document doc = Jsoup.parse(responseString);

		 ArrayList<String> downServers = new ArrayList<>();
		    Element table = doc.select("table").get(7); //select the first table.
		    //System.out.println(table);
		    
		    Elements rows = table.select("tr");
            JSONArray theCases = new JSONArray();
		    for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
		        JSONObject theCase = new JSONObject();
		    	
		    	Element row = rows.get(i);
		        Elements cols = row.select("td");

		       theCase.put("name",cols.get(1).text());
		       theCase.put("docket_type", cols.get(2).text());
		       //theCase.put("docket_address", cols.get(3).text().replace(":", " "));
		       theCase.put("amount", cols.get(4).text());
		       theCase.put("judgement_date", cols.get(5).text());
		       
		       theCases.put(theCase);
		            
		       //System.out.println(theCase);   
		         
		    } 
		 
		 
		 
		 client.close();
		 //theReturnJson.put(theCases.toString());
		 //System.out.println("******RETURNJSON*****: "+theReturnJson); 
		return theCases; 
		 
		 
		 
		 
		//return null;

		
	}
}