package cookieManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class cookieManager {

	/** The Constant URL_STRING. */

	//private final static String URL_STRING = "http://www.nejm.org/doi/pdf/10.1056/NEJMp1514202";

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String args[]) throws Exception {
		Map store;
		store = new HashMap();
		CookieManager cookieManager = new CookieManager();
		
		CookieHandler.setDefault(cookieManager);
		
		URL url = new URL(args[0]);
		//URL url = tocURL;
		//URLConnection connection = url.openConnection();
		
		//connection.getContent();
		
		CookieStore cookieStore = cookieManager.getCookieStore();
		
		List<HttpCookie> cookieList = cookieStore.getCookies();
		
		
		
		//Validate.isTrue(args.length == 1, "usage: supply url to fetch");
        
        //System.out.println("Fetching %s...", url);
        
        Connection.Response response = Jsoup.connect(url.toString())
                .method(Connection.Method.GET)
                .execute();
        
        response = Jsoup.connect(url.toString())
                .cookies(response.cookies())
             //   .method(Connection.Method.POST)
                .execute();
        
        Document doc = Jsoup.connect(url.toString()).cookies(response.cookies()).get();
        //print("\nCookies: ",response.cookies());
        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");

        //print("\nMedia: (%d)", media.size());
        //for (Element src : media) {
        //    if (src.tagName().equals("img"))
         //       print(" * %s: <%s> %sx%s (%s)",
         //               src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
         //               trim(src.attr("alt"), 20));
         //   else
                //print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
        //}

        //print("\nImports: (%d)", imports.size());
        for (Element link : imports) {
            //print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
        }

        //print("\nLinks: (%d)", links.size());
        for (Element link : links) {
        	
        	String theLink = link.attr("abs:href");
        	String theLinkID = link.attr("abs:id");
        	String issueIndex = link.html().trim();
        	System.out.println("the link: " + theLink);
        	System.out.println("Issue: " + issueIndex);
    		//String lastIndex = theLink.substring(index-2);
    		String pattern = "http://www.nejm.org/medical-archives/*";
    		Pattern r = Pattern.compile(pattern);
    		Matcher m = r.matcher(theLink);
    		if (m.find()){
    		//print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
    		System.out.println("toc: " + theLink);
    		String tocLink = theLink;
    		System.out.println("toc link: " + tocLink);
        	
    		
    		StringBuffer cookieStringBuffer = new StringBuffer();

    		//CookieManager cm = new CookieManager();
        	try {
        		getTOC(tocLink);
        		//URL tocurl = new URL(tocLink);
        		//URL url = new URL("http://www.hccp.org/test/cookieTest.jsp");
        	    //connection = tocurl.openConnection();
        	    //connection.setRequestProperty(cookieStore.getCookies().toString());
        	    //connection.connect();
        	    
        	    //System.out.println(cm);
        	    //InputStream in = connection.getInputStream();
        	    //System.out.println(in);
        	    //Files.copy(in, Paths.get(lastIndex+".pdf"), StandardCopyOption.REPLACE_EXISTING);
        		//in.close();
        		
        	} catch (IOException ioe) {
        	    ioe.printStackTrace();
        	}
  
    		//URL pdfurl = new URL(pdfLink);
    		//InputStream in = pdfurl.openStream();
    		//Files.copy(in, Paths.get(lastIndex+".pdf"), StandardCopyOption.REPLACE_EXISTING);
    		//in.close();
    		
    		}
        }
		
		
	}
	
	public static void getTOC(String tocURL) throws Exception {
		TimeUnit.SECONDS.sleep(3);
		int index = tocURL.lastIndexOf("/");
		String tocYear = tocURL.substring(index+1);
		
		Map store;
		store = new HashMap();
		CookieManager cookieManager = new CookieManager();
		
		CookieHandler.setDefault(cookieManager);
		
		URL url = new URL(tocURL);
		//URL url = tocURL;
		//URLConnection connection = url.openConnection();
		
		//connection.getContent();
		
		CookieStore cookieStore = cookieManager.getCookieStore();
		
		List<HttpCookie> cookieList = cookieStore.getCookies();
		
		
		
		//Validate.isTrue(args.length == 1, "usage: supply url to fetch");
        
        //System.out.println("Fetching %s...", url);
        
        Connection.Response response = Jsoup.connect(url.toString())
                .method(Connection.Method.GET)
                .execute();
        
        response = Jsoup.connect(url.toString())
                .cookies(response.cookies())
             //   .method(Connection.Method.POST)
                .execute();
        
        Document doc = Jsoup.connect(url.toString()).cookies(response.cookies()).get();
        //print("\nCookies: ",response.cookies());
        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");

        //print("\nMedia: (%d)", media.size());
        //for (Element src : media) {
        //    if (src.tagName().equals("img"))
         //       print(" * %s: <%s> %sx%s (%s)",
         //               src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
         //               trim(src.attr("alt"), 20));
         //   else
                //print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
        //}

        //print("\nImports: (%d)", imports.size());
        for (Element link : imports) {
            //print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
        }

        //print("\nLinks: (%d)", links.size());
        for (Element link : links) {
        	
        	String theLink = link.attr("abs:href");
        	String theLinkID = link.attr("abs:id");
        	String issueIndex = link.select("div").html();
        	
        	System.out.println("the link: " + theLink);
        	System.out.println("Issue: " + issueIndex);
        	System.out.println("the link: " + theLink);
        	//int index = theLink.lastIndexOf("/");
    		//String lastIndex = theLink.substring(index-2);
    		String pattern = "http://www.nejm.org/toc/nejm/*";
    		Pattern r = Pattern.compile(pattern);
    		Matcher m = r.matcher(theLink);
    		if (m.find()){
    		//print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
    		System.out.println("toc: " + theLink);
    		String tocLink = theLink;
    		System.out.println("toc link: " + tocLink);
    		
    		
    		StringBuffer cookieStringBuffer = new StringBuffer();

    		//CookieManager cm = new CookieManager();
        	try {
        		getPDFs(tocLink,tocYear,issueIndex);
        		//URL tocurl = new URL(tocLink);
        		//URL url = new URL("http://www.hccp.org/test/cookieTest.jsp");
        	    //connection = tocurl.openConnection();
        	    //connection.setRequestProperty(cookieStore.getCookies().toString());
        	    //connection.connect();
        	    
        	    //System.out.println(cm);
        	    //InputStream in = connection.getInputStream();
        	    //System.out.println(in);
        	    //Files.copy(in, Paths.get(lastIndex+".pdf"), StandardCopyOption.REPLACE_EXISTING);
        		//in.close();
        		
        	} catch (IOException ioe) {
        	    ioe.printStackTrace();
        	}
  
    		//URL pdfurl = new URL(pdfLink);
    		//InputStream in = pdfurl.openStream();
    		//Files.copy(in, Paths.get(lastIndex+".pdf"), StandardCopyOption.REPLACE_EXISTING);
    		//in.close();
    		
    		}
            
        }
    
		
		
		
		
		
		// iterate HttpCookie object
		

	}
		
		
		
		
		public static void fdMakeDir (String tocYear){
			String insertURL = "https://developer.feithapps.com/rest/v1/api/developer/sql/fdMkDir";
			String fdDirName = "/NEJM/"+tocYear;
			try {
				Connection.Response response = Jsoup.connect(insertURL)
				.data("dirName",fdDirName)
				.data("volID","17")
				.method(Method.GET)
				.ignoreContentType(true)
				.execute();
				String contentType = response.body();
				System.out.println("MkDir Response: "+contentType);
				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		public static void applyPropertySet (String fdName,String srcURL,String DOIid,String subjectTitle, String jName,String authors, String tocYear, String issueDate){
			String insertURL = "https://developer.feithapps.com/rest/v1/api/developer/sql/getDocIdbyName";
			
			try {
				Connection.Response response = Jsoup.connect(insertURL)
				.data("fileName",fdName)
				.data("sourceurl",srcURL)
				.data("subjecttitle",subjectTitle)
				.data("doiid",DOIid)
				.data("journalname",jName)
				.data("authors",authors)
				.data("fddYear",tocYear)
				.data("issueIndex",issueDate)
				.method(Method.GET)
				.ignoreContentType(true)
				.execute();
				String contentType = response.body();
				System.out.println("PSet Response: "+contentType);
				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public static void saveToFeithDrive (File fileName,String tocYear){
			String insertURL = "https://developer.feithapps.com/rest/v1/api/developer/sql/getDirID";
			JSONParser jsonParser = new JSONParser();

			
			String fileURL = "https://demo.feithapps.com/files";
			
			try {

				
				Connection.Response dirresponse = Jsoup.connect(insertURL)
				.data("parentName","NEJM")
				.data("dirYear",tocYear)
				.method(Method.GET)
				.ignoreContentType(true)
				.execute();
				System.out.println("get dir: "+dirresponse.body());
				
				//Object obj = parser.parse(dirresponse.body());

				JSONObject jsonObject = (JSONObject) jsonParser.parse(dirresponse.body());
				//JSONObject structure = (JSONObject) jsonObject.get("resultSet");
				
				
				//String targetid = structure.get("idx_id").toString();
				
				JSONArray names = (JSONArray) jsonObject.get("resultSet");
				

			    for (Object c : names)
			    {
			      System.out.println(c+"");
			    }
			  
				
				
				
				
				

				           Iterator i = names.iterator();
	
				            while (i.hasNext()) {
				            	
				                JSONObject slide = (JSONObject) i.next();
				                String targetid = (String)slide.get("idx_id");
				                System.out.println(targetid);
				            	
				            	
				            		//System.out.println(i.next());
				            		
				            		//JSONObject innerObj = (JSONObject) i.next();
				                  // System.out.println(""+innerObj.get("idx_id"));

				            		
				            		
				            		//jsonParser.parse((String) i.next());
				            		//String targetid = (String) innerObj.get("idx_id");
				            		// System.out.println(targetid);
				            		
				            		Connection.Response response = Jsoup.connect(fileURL)
				            				.data("username","charlie")
				            				.data("password","feith123")
				            				.data("targetId",targetid)
				            				.data("file",fileName.getName(), new FileInputStream(fileName))
				            				.method(Method.POST)
				            				.execute();
				            				System.out.println("FeithDrive Response: "+response.statusMessage());
				            }

				
				
				//String targetid = (String) jsonObject.get("idx_id");
				//String targetid = "2208";
				  
				
		
			
			
			
			

				
				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException p) {
				// TODO Auto-generated catch block
				p.printStackTrace();
			}
		
		}
		
		public static String checkDOI (String doiID) throws ParseException{
			String insertURL = "https://developer.feithapps.com/rest/v1/api/developer/sql/checkDOI";
			JSONParser jsonParser = new JSONParser();
			String doiCount = "0";
			
			try {
				System.out.println("*********DOI ID: "+doiID);
				Connection.Response dirresponse;
				
					dirresponse = Jsoup.connect(insertURL)
					.data("doi",doiID)
					.method(Method.GET)
					.ignoreContentType(true)
					.execute();
				
				System.out.println("check DOI: "+dirresponse.body());
				
				//Object obj = parser.parse(dirresponse.body());

				JSONObject jsonObject;
			
					jsonObject = (JSONObject) jsonParser.parse(dirresponse.body());
				
				//JSONObject structure = (JSONObject) jsonObject.get("resultSet");
				//System.out.println("Into job structure, name: " + structure.get("idx_id"));
				
				//String targetid = structure.get("idx_id").toString();
				
				JSONArray names = (JSONArray) jsonObject.get("resultSet");
				
			    for (Object c : names)
			    {
			      System.out.println(c+"");
			    }

				           Iterator i = names.iterator();
	
				            while (i.hasNext()) {
				            	
				                JSONObject slide = (JSONObject) i.next();
				                doiCount = (String)slide.get("doicount");
				                System.out.println("*****DOI COUNT:"+doiCount);
				               
				            }
				            
			} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			return doiCount;
			 
		}
	
	public static void getPDFs(String tocURL,String tocYear, String issueIndex) throws Exception 
	{
		Path dir = Paths.get("c:/workspace/cookiemanager/nejm/"+tocYear);
		if (!Files.exists(dir)) {
		Files.createDirectory(dir);
		}
		fdMakeDir(tocYear);
		Map store;
		store = new HashMap();
		CookieManager cookieManager = new CookieManager();
		
		CookieHandler.setDefault(cookieManager);
		TimeUnit.SECONDS.sleep(1);
		URL url = new URL(tocURL);
		//URL url = args[0];
		URLConnection connection = url.openConnection();
		
		connection.getContent();
		
		CookieStore cookieStore = cookieManager.getCookieStore();
		
		List<HttpCookie> cookieList = cookieStore.getCookies();
		
		
		
		//Validate.isTrue(args.length == 1, "usage: supply url to fetch");
        
        //System.out.println("Fetching %s...", url);
		TimeUnit.SECONDS.sleep(3);
        Connection.Response response = Jsoup.connect(url.toString())
                .method(Connection.Method.GET)
                .execute();
        
        response = Jsoup.connect(url.toString())
                .cookies(response.cookies())
             //   .method(Connection.Method.POST)
                .execute();
        
        Document doc = Jsoup.connect(url.toString()).cookies(response.cookies()).get();
        //print("\nCookies: ",response.cookies());
        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");

        //print("\nMedia: (%d)", media.size());
        //for (Element src : media) {
        //    if (src.tagName().equals("img"))
         //       print(" * %s: <%s> %sx%s (%s)",
         //               src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
         //               trim(src.attr("alt"), 20));
         //   else
                //print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
        //}

        //print("\nImports: (%d)", imports.size());
        for (Element link : imports) {
            //print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
        }

        //print("\nLinks: (%d)", links.size());
        for (Element link : links) {
        	
        	String theLink = link.attr("abs:href");
        	int index = theLink.lastIndexOf("/");
    		String lastIndex = theLink.substring(index+1);
    		String pattern = "NEJM*";
    		Pattern r = Pattern.compile(pattern);
    		Matcher m = r.matcher(lastIndex);
    		if (m.find()){
    		//print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
    		System.out.println("Article ID: " + lastIndex);
    		String pdfLink = theLink.replaceAll("full", "pdf");
    		System.out.println("pdf link: " + pdfLink);
    		String doiIndex = theLink.substring(index-7);
    		String doiID = doiIndex;
    		System.out.println("DOI ID: " + doiID);
    		String subHTML = link.html();
    		System.out.println("Sub HTML: " + subHTML);
    		Document subDoc = Jsoup.parse(subHTML);
    		Elements linkTitle = subDoc.select("h2.a-title");
    		Elements linkAuthor = subDoc.select("div.a-author");
    		System.out.println("Title: "+linkTitle.text());
    		System.out.println("Author: "+linkAuthor.text());
    		StringBuffer cookieStringBuffer = new StringBuffer();
    		
    		String doiCount = checkDOI(doiID);
    		
    		System.out.println("XXXFile DOI CountXXX: "+doiCount);
    		
    		if (doiCount.equals("0")){
    		//CookieManager cm = new CookieManager();
    			System.out.println("++++RUNNING FILE SEQUENCE++++");
    			try {
    				TimeUnit.SECONDS.sleep(2);
        		URL pdfurl = new URL(pdfLink);
        		//URL url = new URL("http://www.hccp.org/test/cookieTest.jsp");
        	    connection = pdfurl.openConnection();
        	    //connection.setRequestProperty(cookieStore.getCookies().toString());
        	    connection.connect();
        	    
        	    //System.out.println(cm);
        	    InputStream in = connection.getInputStream();
        	    System.out.println(in);
        	    Files.copy(in, Paths.get("c:/workspace/cookiemanager/nejm/"+tocYear+"/"+lastIndex+".pdf"), StandardCopyOption.REPLACE_EXISTING);
        		in.close();
            	Path myPath = Paths.get("c:/workspace/cookiemanager/nejm/"+tocYear+"/"+lastIndex+".pdf");
        		File fileName = myPath.toFile();
            	Map fSize = Files.readAttributes(Paths.get("c:/workspace/cookiemanager/nejm/"+tocYear+"/"+lastIndex+".pdf"),"size");
        		Object[] theList = fSize.values().toArray();
        		Long theFileSize = (Long) theList[0];
        		if (theFileSize < 54500) {
        			
        			//Path dir = Paths.get("c:/workspace/cookiemanager/nejm/"+tocYear+"/"+lastIndex+".pdf").getParent();        
        		    String newNameString = Paths.get("c:/workspace/cookiemanager/nejm/"+tocYear+"/"+lastIndex+".pdf").toString().replaceAll("pdf", "html");
        		    
        		    Path fn = Paths.get("c:/workspace/cookiemanager/nejm/"+tocYear+"/"+lastIndex+".pdf").getFileSystem().getPath(newNameString);
        		    Path target = (dir == null) ? fn : dir.resolve(fn);
        		    
        		    try {
        				Files.move(Paths.get("c:/workspace/cookiemanager/nejm/"+tocYear+"/"+lastIndex+".pdf"), target,StandardCopyOption.REPLACE_EXISTING);
        				fileName = target.toFile();
        			//saveToFeithDrive(myFile);
        			
        		    } catch (IOException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        			
        			
        			//renameSmallFiles(Paths.get("c:/workspace/cookiemanager/nejm/2015/"+lastIndex+".pdf"));
        		} 
        			saveToFeithDrive(fileName,tocYear);
        			String fdName = fileName.getName().toString();
    				System.out.println("FeithDrive Name: "+fdName);
    				applyPropertySet (fdName,pdfLink,doiID,linkTitle.text(),"NEJM",linkAuthor.text(),tocYear,issueIndex);
        		
        		
        		System.out.println("FILE SIZE: "+theList[0]);
        		
        	} catch (IOException ioe) {
        	    ioe.printStackTrace();
        	}

    		//URL pdfurl = new URL(pdfLink);
    		//InputStream in = pdfurl.openStream();
    		//Files.copy(in, Paths.get(lastIndex+".pdf"), StandardCopyOption.REPLACE_EXISTING);
    		//in.close();
    		
    		}
    		}
            
        }
    
		
		
		
		
		
		// iterate HttpCookie object
		

	}
}