package tryTwitter;
// javaDoc http://twitter4j.org/oldjavadocs/4.0.4/index.html

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Try {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		final String CONSUMER_KEY = "4SEqXIbESzwiPeIC2PGxuhYYe";
		final String CONSUMER_KEY_SECRET = "p8EMzuPoOBXl0GuGTKDPLjdJdEauzOtT7SEaVw1m5bVMB4ULcs";
		final String TWITTER_TOKEN = "4515806056-aCEvacSt5qayd7kWDepS9EZphLJsUfmO1UnB6UJ";
		final String TWITTER_TOKEN_SECRET = "8aPMo6hh6okssoRunKeQsNHvYmxkYbWfcIwYXRovjCksH";
		
		    Twitter twitter = new TwitterFactory().getInstance();
		    twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
		    twitter.setOAuthAccessToken(new AccessToken(TWITTER_TOKEN,TWITTER_TOKEN_SECRET));
		    
		    try {
	        	
	        	ResponseList<Status> a = twitter.getUserTimeline("HebaKhazbak",new Paging(1,5));
	        	
	        	for(Status b: a) {
	        		System.out.println(b.getText());
	        	}

	        }catch(Exception e ){
	        	System.out.println("Error");
	        }
	}

}
