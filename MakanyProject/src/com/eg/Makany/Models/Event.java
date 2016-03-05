package com.eg.Makany.Models;

import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Event {
	private String id, name, category, description;
	private double latitude, longitude;
	private String ownerMail;
	private Vector<String> goingMails, postIDs;
	
	
	public Event(String id, String name, String category, String description,
			double latitude, double longitude, String ownerMail,
			Vector<String> goingMails, Vector<String> postIDs){
		
		this.id=id;
		this.name=name;
		this.category=category;
		this.description=description;
		this.latitude=latitude;
		this.longitude=longitude;
		this.ownerMail=ownerMail;
		this.goingMails=goingMails;
		this.postIDs=postIDs;
	}
	
	public boolean saveEvent(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity event = new Entity("events");

		event.setProperty("name", this.name);
		event.setProperty("category", this.category);
		event.setProperty("description", this.description);
		event.setProperty("latitude", this.latitude);
		event.setProperty("longitude", this.longitude);
		event.setProperty("ownerMail", this.ownerMail);
		
		datastore.put(event);

		return true;
	}
	
	public static boolean editEvent(String eventID,
			String newCategory,String newDescription,double newLatitude,double newLongitude){
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query gaeQuery = new Query("events");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(String.valueOf(entity.getKey().getId()).equals(eventID)){
				entity.setProperty("category", newCategory);
				entity.setProperty("description", newDescription);
				entity.setProperty("latitude", newLatitude);
				entity.setProperty("longitude", newLongitude);
				datastore.put(entity);
				return true;
			}
			
		}
		
		return false;
	}
	
	public static boolean addGoingUser(String eventID,String userMail){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Entity eventGo = new Entity("eventGoing");
		
		eventGo.setProperty("eventID", eventID);
		eventGo.setProperty("userMail", userMail);
		datastore.put(eventGo);
		return true;
	}
	
	public static Vector<String> getEventGoing(String eventID){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Vector<String> ret=new Vector<String>();
		
		Query gaeQuery = new Query("eventGoing");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("eventID").toString().equals(eventID))
				ret.add(entity.getProperty("userMail").toString());
			
		}
		return ret;
	}
	
	public static boolean addPost(String eventID,String postID){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Entity eventPost = new Entity("eventPosts");
		
		eventPost.setProperty("eventID", eventID);
		eventPost.setProperty("postID", postID);
		datastore.put(eventPost);
		return true;
	}
	
	public static int deleteEvent(String eventID,String ownerEmail){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Vector<Key> keysToDelete=new Vector<Key>();
		
		Query gaeQuery = new Query("events");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(String.valueOf(entity.getKey().getId()).equals(eventID)){
				if(!entity.getProperty("ownerMail").toString().equals(ownerEmail))
					return 2;
				
				keysToDelete.add(entity.getKey());
				break;
			}
			
		}

		
		gaeQuery = new Query("eventGoing");
		pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("eventID").toString().equals(eventID))
				keysToDelete.add(entity.getKey());
			
		}
		
		gaeQuery = new Query("eventPosts");
		pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("eventID").toString().equals(eventID))
				keysToDelete.add(entity.getKey());
			
		}

		
		for(Key k:keysToDelete)
			datastore.delete(k);
		
		return 1;
	}
}
