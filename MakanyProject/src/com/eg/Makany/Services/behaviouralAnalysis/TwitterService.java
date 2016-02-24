package com.eg.Makany.Services.behaviouralAnalysis;

import java.util.Vector;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.simple.JSONObject;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

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
		
		   Twitter twitter = new TwitterFactory().getInstance();
		   twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
		   twitter.setOAuthAccessToken(new AccessToken(TWITTER_TOKEN,TWITTER_TOKEN_SECRET));
		 
		JSONObject object = new JSONObject();
		
		Vector <User> allUsers = User.getAllUsers();
		
		for (User user : allUsers)
		{
			String screenName = user.getTwitter();
			System.out.println("screenName " + screenName);
				try {
	        	
	        	ResponseList<Status> a = twitter.getUserTimeline(screenName,new Paging(1,5));
	        	
	        	for(Status b: a) {
	        		System.out.println(b.getText() + " " + b.getCreatedAt());
	        		}

	        }catch(Exception e ){
	        	System.out.println("Error");
	        }
			
			
			System.out.println("-------------");
		}
		
		object.put("Status", "OK");
		
		return object.toString();

	}
	
	


}