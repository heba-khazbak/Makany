package com.eg.Makany.Models.BA;

import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class TweetTopic {
	

	private static final String TABLENAME = "TweetTopics";
	private long tweetID;
	private String topic;
	private double score;
	
	public TweetTopic(long tweetID, String topic, double score) {
		this.tweetID = tweetID;
		this.topic = topic;
		this.score = score;
	}
	
	public TweetTopic() {
		// TODO Auto-generated constructor stub
	}

	public boolean saveTweetTopic(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Entity topic=new Entity(TABLENAME);
		topic.setProperty("tweetID", this.tweetID);
		topic.setProperty("topic", this.topic);
		topic.setProperty("score", this.score);
		
		datastore.put(topic);
		return true;
	}
	
	public static Vector<TweetTopic> getTweetTopics(long tweetID){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Vector<TweetTopic> ret=new Vector<TweetTopic>();
		Query gaeQuery = new Query("TABLENAME");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("tweetID").toString().equals(tweetID)){
				TweetTopic temp = new TweetTopic();
				temp.tweetID = (long) entity.getProperty("userEmail");
				temp.topic = entity.getProperty("topic").toString();
				temp.score = (double) entity.getProperty("score");
				ret.add(temp);
			}
			
		}
		return ret;
	}
}
