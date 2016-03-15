package com.eg.Makany.Models.BA;

import java.util.Date;
import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class TwitterTweets {
	

	private static final String TABLENAME = "TwitterTweets";
	private String UserEmail;
	private long TweetID;
	private String content;
	private Date createdAt;
	private String lang;
	private Vector<TweetTopic> Topics;
	
	public TwitterTweets(String userEmail, long tweetID, String content,
			Date createdAt, String lang, Vector<TweetTopic> topics) {
		super();
		UserEmail = userEmail;
		TweetID = tweetID;
		this.content = content;
		this.createdAt = createdAt;
		this.lang = lang;
		Topics = topics;
	}

	
	public TwitterTweets() {
		// TODO Auto-generated constructor stub
	}

	public String getUserEmail() {
		return UserEmail;
	}
	public void setUserEmail(String userEmail) {
		UserEmail = userEmail;
	}
	public long getTweetID() {
		return TweetID;
	}
	public void setTweetID(long tweetID) {
		TweetID = tweetID;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}

	public Vector<TweetTopic> getTopics() {
		return Topics;
	}


	public void setTopics(Vector<TweetTopic> topics) {
		Topics = topics;
	}
	
	public Boolean saveAnalyzedTweet() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Entity tweet = new Entity(TABLENAME);

		tweet.setProperty("UserEmail", this.UserEmail);
		tweet.setProperty("TweetID", this.TweetID);
		tweet.setProperty("content", this.content);
		tweet.setProperty("createdAt", this.createdAt);
		tweet.setProperty("lang", this.lang);
		
		datastore.put(tweet);
		
		for (TweetTopic T : this.Topics)
			T.saveTweetTopic();

		return true;

	}
	
	public static Vector<TwitterTweets> getUserTweets(String email) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Vector<TwitterTweets> myTweets = new Vector<TwitterTweets>();
		
		Query gaeQuery = new Query(TABLENAME);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("UserEmail").toString().equals(email))
			{
				TwitterTweets temp = new TwitterTweets();
				temp.setUserEmail(entity.getProperty("UserEmail").toString());
				temp.setTweetID((long) entity.getProperty("TweetID"));
				temp.setContent(entity.getProperty("content").toString());
				temp.setCreatedAt((Date) entity.getProperty("createdAt"));
				temp.setLang(entity.getProperty("lang").toString());
				temp.Topics = TweetTopic.getTweetTopics(temp.getTweetID());
				
				myTweets.add(temp);
				
			}
			
		}
		
		return myTweets;
	}
	
	public static long getMaxTweetID (String email)
	{
		Vector<TwitterTweets> myTweets = getUserTweets(email);
		if (myTweets == null)
			return 0;
		
		long max = 0;
		for (TwitterTweets T : myTweets)
		{
			if (T.getTweetID() > max)
				max = T.getTweetID();
		}
		
		return max;
	}


	
	


}
