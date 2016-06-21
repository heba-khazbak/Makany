package com.eg.Makany.Models;

import java.util.Date;
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
	private String ownerMail, district, date, from, to;
	private Vector<String> goingMails, postIDs;
	
	public Event(){
		
		this.id="";
		this.name="";
		this.category="";
		this.description="";
		this.latitude=0;
		this.longitude=0;
		this.ownerMail="";
		this.district="";
		this.date="";
		this.from="";
		this.to="";
		this.goingMails=new Vector<String>();
		this.postIDs=new Vector<String>();
	}
	
	public Event(String id, String name, String category, String description,
			double latitude, double longitude, String ownerMail, String district, String date,
			String from, String to, Vector<String> goingMails, Vector<String> postIDs){
		
		this.id=id;
		this.name=name;
		this.category=category;
		this.description=description;
		this.latitude=latitude;
		this.longitude=longitude;
		this.ownerMail=ownerMail;
		this.district=district;
		this.date=date;
		this.from=from;
		this.to=to;
		this.goingMails=goingMails;
		this.postIDs=postIDs;
	}
	
	public String getID(){return id;}
	public String getName(){return name;}
	public String getCategory(){return category;}
	public String getDescription(){return description;}
	public double getLatitude(){return latitude;}
	public double getLongitude(){return longitude;}
	public String getOwnerMail(){return ownerMail;}
	public String getDistrict(){return district;}
	public String getDate(){return date;}
	public String getFrom(){return from;}
	public String getTo(){return to;}
	public String getParsedGoingMails(){
		String ret="";
		for(int i=0;i<goingMails.size();++i){
			if(i>0)ret+=";";
			ret+=goingMails.get(i);
		}
		return ret;
	}
	public String getParsedPostIDs(){
		String ret="";
		for(int i=0;i<postIDs.size();++i){
			if(i>0)ret+=";";
			ret+=postIDs.get(i);
		}
		return ret;
	}
	
	public boolean saveEvent(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity event = new Entity("events");
		
		if(this.name==null)this.name="";
		if(this.category==null)this.category="";
		if(this.description==null)this.description="";
		if(this.ownerMail==null)this.ownerMail="";
		if(this.district==null)this.district="";
		if(this.from==null)this.from="";
		if(this.to==null)this.to="";

		event.setProperty("name", this.name);
		event.setProperty("category", this.category);
		event.setProperty("description", this.description);
		event.setProperty("latitude", this.latitude);
		event.setProperty("longitude", this.longitude);
		event.setProperty("ownerMail", this.ownerMail);
		event.setProperty("district", this.district);
		event.setProperty("date", new Date());
		event.setProperty("from", this.from);
		event.setProperty("to", this.to);
		
		datastore.put(event);

		return Event.addGoingUser(String.valueOf(event.getKey().getId()), this.ownerMail) > 0;
	}
	
	public static boolean editEvent(String eventID,
			String newCategory,String newDescription,String newDistrict,
			double newLatitude,double newLongitude,
			String newFrom, String newTo){
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		if(newCategory==null)newCategory="";
		if(newDescription==null)newDescription="";
		if(newDistrict==null)newDistrict="";
		if(newFrom==null)newFrom="";
		if(newTo==null)newTo="";
		
		Query gaeQuery = new Query("events");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(String.valueOf(entity.getKey().getId()).equals(eventID)){
				entity.setProperty("category", newCategory);
				entity.setProperty("description", newDescription);
				entity.setProperty("district", newDistrict);
				entity.setProperty("date", new Date());
				entity.setProperty("latitude", newLatitude);
				entity.setProperty("longitude", newLongitude);
				entity.setProperty("from", newFrom);
				entity.setProperty("to", newTo);
				datastore.put(entity);
				return true;
			}
			
		}
		
		return false;
	}
	
	public static int addGoingUser(String eventID,String userMail){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query gaeQuery = new Query("eventGoing");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("eventID").toString().equals(eventID)
					&& entity.getProperty("userMail").toString().equals(userMail))
				return 2;
			
		}
		
		Entity eventGo = new Entity("eventGoing");
		
		eventGo.setProperty("eventID", eventID);
		eventGo.setProperty("userMail", userMail);
		datastore.put(eventGo);
		return 1;
	}
	
	public static int cancelGoing(String eventID,String userMail){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Key keyToDelete=null;
		
		Query gaeQuery = new Query("eventGoing");
		PreparedQuery pq = datastore.prepare(gaeQuery);

		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("eventID").toString().equals(eventID)
					&& entity.getProperty("userMail").toString().equals(userMail)){
				keyToDelete=entity.getKey();
				break;
			}
			
		}

		if(keyToDelete==null)
			return 2;

		datastore.delete(keyToDelete);
		return 1;
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
	
	public static Vector<Event> getGoingEvents(String userEmail,String specificID){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Vector<Event> ret=new Vector<Event>();
		
		Query gaeQuery = new Query("eventGoing");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		long eid=-1;
		if(specificID!=null && !specificID.isEmpty())eid=Long.parseLong(specificID);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("userMail").toString().equals(userEmail)){
				if(Long.parseLong(entity.getProperty("eventID").toString())<=eid)
					continue;
				ret.add(new Event().getEventByID(entity.getProperty("eventID").toString()));
			}
				
			
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
	
	
	public Event getEventByID(String eventID){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		this.id=eventID;
		Query gaeQuery = new Query("events");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(String.valueOf(entity.getKey().getId()).equals(eventID)){
				this.name=entity.getProperty("name").toString();
				this.category=entity.getProperty("category").toString();
				this.description=entity.getProperty("description").toString();
				this.latitude=Double.parseDouble(entity.getProperty("latitude").toString());
				this.longitude=Double.parseDouble(entity.getProperty("longitude").toString());
				this.ownerMail=entity.getProperty("ownerMail").toString();
				this.district=entity.getProperty("district").toString();
				this.date=entity.getProperty("date").toString();
				this.from=entity.getProperty("from").toString();
				this.to=entity.getProperty("to").toString();
				break;
			}
			
		}
		
		gaeQuery = new Query("eventPosts");
		pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("eventID").toString().equals(eventID))
				this.postIDs.add(entity.getProperty("postID").toString());
			
			
		}
		
		
		gaeQuery = new Query("eventGoing");
		pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("eventID").toString().equals(eventID))
				this.goingMails.add(entity.getProperty("userMail").toString());
			
		}
		
		return this;
	}
	
	public static Vector<Event> getFilteredEvents(String specificCategory,String specificDistrict){
		Vector<Event> ret=new Vector<Event>();
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("events");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		

		for(Entity entity:pq.asIterable()){
			
			if(specificDistrict!=null && !specificDistrict.isEmpty() &&
					!specificDistrict.equals(entity.getProperty("district").toString()))
				continue;
			
			if(specificCategory!=null && !specificCategory.isEmpty() &&
					!specificCategory.equals(entity.getProperty("category").toString()))
				continue;
			
			ret.add(new Event().getEventByID(String.valueOf(entity.getKey().getId())));
		}
		
		return ret;
	}
	
}
