package com.eg.Makany.Services.behaviouralAnalysis;

import java.util.Vector;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import com.eg.Makany.Models.User;
import com.eg.Makany.Models.BA.MakanyAlchemy;
import com.eg.Makany.Models.BA.TweetTopic;
import com.eg.Makany.Models.BA.TwitterTweets;





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
		
		JSONArray ReturnedArray = new JSONArray();
		
		
		Vector <User> allUsers = User.getAllUsers();
		
		for (User user : allUsers)
		{
			JSONObject object = new JSONObject();
			
			String screenName = user.getTwitter();
			object.put("user", screenName);
			
			System.out.println("screenName " + screenName);
				try {
				//Paging page = new Paging(1, 200, TwitterTweets.getMaxTweetID(user.getMail()));
				Paging page = new Paging(1, 50);
				
				ResponseList<Status> myTweets = twitter.getUserTimeline(screenName,page);
				
				System.out.println("Size " + myTweets.size() );
				
				long oldMaxID = TwitterTweets.getMaxTweetID(user.getMail());
				
	        	for(Status s: myTweets) {
	        		if (s.getId() <= oldMaxID)
	        			break;
	        		TwitterTweets T = new TwitterTweets();
	        		T.setUserEmail(user.getMail());
					T.setTweetID(s.getId());
					T.setContent(s.getText());
					
					if (s.getCreatedAt() == null)
						T.setCreatedAt("");
					else
						T.setCreatedAt(s.getCreatedAt().toString());
					
					if (s.getLang() == null)
						T.setLang("");
					else
						T.setLang(s.getLang());
					
	        		/*s.getId();
	        		//Date x = new Date(year,month,day);
	        		Date y = new Date(2015,2,15);
	        		s.getCreatedAt().before(y);
	        		s.getLang();*/
					
	        		System.out.println(T.getContent() + " " + T.getCreatedAt() +" " +  T.getLang());

	        		
	        		Vector <TweetTopic> myTopics = new Vector<TweetTopic>();
	        		
	        		Vector<String> topics = MakanyAlchemy.getFromAlchemy(s.getText());
					if (topics != null)
					{
						for (String A : topics)
						{
							String temp[]=A.split(";");
							double score = Double.parseDouble(temp[1]);
							TweetTopic topic = new TweetTopic (user.getMail(),T.getTweetID(),temp[0] , score);
		        			myTopics.add(topic);
						}
					}
	        		T.setTopics(myTopics);
	        		
	        		T.saveAnalyzedTweet();
	        		}
	        	ReturnedArray.add(object);

	        }catch(Exception e ){
	        	System.out.println("Error " + e.getMessage());
	        	object.put("Status", "ERROR");
	        	ReturnedArray.add(object);
	        }
			
			
			System.out.println("-------------");
		}
		
	
		
		return ReturnedArray.toString();

	}
	
	
	


}