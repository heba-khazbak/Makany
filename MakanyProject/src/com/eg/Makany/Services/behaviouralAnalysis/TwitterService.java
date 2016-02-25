package com.eg.Makany.Services.behaviouralAnalysis;

import java.io.StringWriter;
import java.util.Vector;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.XML;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import com.alchemyapi.api.AlchemyAPI;
import com.eg.Makany.Models.User;




@Path("/")
@Produces("text/html")
public class TwitterService {

	@POST
	@Path("/AnalyzeTwitter")
	public String analyzeTwitter() {
		
		final String CONSUMER_KEY = "4SEqXIbESzwiPeIC2PGxuhYYe";
		final String CONSUMER_KEY_SECRET = "p8EMzuPoOBXl0GuGTKDPLjdJdEauzOtT7SEaVw1m5bVMB4ULcs";
		final String TWITTER_TOKEN = "4515806056-aCEvacSt5qayd7kWDepS9EZphLJsUfmO1UnB6UJ";
		final String TWITTER_TOKEN_SECRET = "8aPMo6hh6okssoRunKeQsNHvYmxkYbWfcIwYXRovjCksH";
		
		final String ALCHEMY_KEY = "2b4fc971215f193b42ebb0ca7e1052164dddc5f7";
		
		// Create an AlchemyAPI object.
    	AlchemyAPI alchemyObj= AlchemyAPI.GetInstanceFromString(ALCHEMY_KEY);
		
    	Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
		twitter.setOAuthAccessToken(new AccessToken(TWITTER_TOKEN,TWITTER_TOKEN_SECRET));
		
		JSONArray ReturnedArray = new JSONArray();
		
		
		Vector <User> allUsers = User.getAllUsers();
		
		for (User user : allUsers)
		{
			JSONObject object = new JSONObject();

			String screenName = user.getTwitter();
			object.put("user", screenName);
			
			System.out.println("screenName " + screenName);
				try {
	        	
				ResponseList<Status> myTweets = twitter.getUserTimeline(screenName,new Paging(1,5));
					
				// get 20 tweets 	
	        	//ResponseList<Status> myTweets = twitter.getUserTimeline(screenName);
	        	String allText = "";
	        	for(Status s: myTweets) {
	        		System.out.println(s.getText() + " " + s.getCreatedAt());
	        		Document doc = alchemyObj.TextGetTaxonomy(s.getText());
	        		String theXmlResult = getStringFromDocument(doc);
	        		org.json.JSONObject xmlJSONObj = XML.toJSONObject(theXmlResult);
	        		
	        		/* json contains results , elements ... elements contains array each has 
	        		label and score and may be some things else 
	        		score is value from 0-1 and label is seperated with /  */
	        		
	        		//String label = xmlJSONObj.getString("label");
	        		//String score = xmlJSONObj.getString("score");
	        		
	        		String x = xmlJSONObj.getJSONObject("results").getJSONObject("taxonomy").toString();
	        		System.out.println(x);
	        		//System.out.println("element " + element.toString());
	        		//System.out.println("label" + label + " Score" + score);
	                //System.out.println(xmlJSONObj.toString());
	        		
	        		}
	        	ReturnedArray.add(object);

	        }catch(Exception e ){
	        	System.out.println("Error");
	        }
			
			
			System.out.println("-------------");
		}
		
	
		
		return ReturnedArray.toString();

	}
	
	private static String getStringFromDocument(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);

            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }
	
	


}