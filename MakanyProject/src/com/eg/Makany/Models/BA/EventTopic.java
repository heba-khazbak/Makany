package com.eg.Makany.Models.BA;

import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class EventTopic {
	private static final String TABLENAME = "EventTopics";
	private String userEmail;
	private String eventID;
	private String topic;
	private double score;
	
	public EventTopic(String userEmail , String eventID, String topic, double score) {
		this.userEmail = userEmail;
		this.eventID = eventID;
		this.topic = topic;
		this.score = score;
	}
	
	public EventTopic(){
		this.userEmail="";
		this.eventID="";
		this.topic="";
		this.score=0;
	}
	
	public boolean saveEventTopic(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query gaeQuery = new Query(TABLENAME);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("userEmail").toString().equals(this.userEmail)
					&& entity.getProperty("eventID").toString().equals(this.eventID)){
				return false;
			}
		}
		
		if(topic==null)return true;
		
		Entity topic=new Entity(TABLENAME);
		topic.setProperty("userEmail", this.userEmail);
		topic.setProperty("eventID", this.eventID);
		topic.setProperty("topic", this.topic);
		topic.setProperty("score", this.score);
		
		datastore.put(topic);
		return true;
	}
	
	public static Vector<EventTopic> getEventTopics(String userEmail){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Vector<EventTopic> ret=new Vector<EventTopic>();
		Query gaeQuery = new Query(TABLENAME);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("userEmail").toString().equals(userEmail)){
				ret.add(new EventTopic(
						userEmail,
						entity.getProperty("eventID").toString(),
						entity.getProperty("topic").toString(),
						Double.parseDouble(entity.getProperty("score").toString())));
			}
			
		}
		return ret;
	}
	
}
