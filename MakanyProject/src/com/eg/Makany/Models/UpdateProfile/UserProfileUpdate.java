package com.eg.Makany.Models.UpdateProfile;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class UserProfileUpdate {
	
	
	public static void saveLovedPlaces(String userEmail,String placeCategory,String placeID){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("userLovedPlaces");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("userEmail").toString().equals(userEmail)
					&& entity.getProperty("placeID").toString().equals(placeID)){
				return;
			}
		}
		
		gaeQuery = new Query("userLovedCategories");
		pq = datastore.prepare(gaeQuery);
		

		boolean found=false;
		int tot=0;
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("userEmail").toString().equals(userEmail)
					&& entity.getProperty("placeCategory").toString().equals(placeCategory)){
				int numloved=Integer.parseInt(entity.getProperty("numloved").toString());
				entity.setProperty("numloved", numloved+1);
				datastore.put(entity);
				found=true;
			}
			
			if(entity.getProperty("userEmail").toString().equals(userEmail))
				tot+=Integer.parseInt(entity.getProperty("numloved").toString());
		}
		if(!found){
			Entity entity=new Entity("userLovedCategories");
			entity.setProperty("userEmail", userEmail);
			entity.setProperty("placeCategory", placeCategory);
			entity.setProperty("numloved", 1);
			++tot;
			entity.setProperty("score", 1.0/(double)tot);
		}
		
		for(Entity entity:pq.asIterable()){			
			if(entity.getProperty("userEmail").toString().equals(userEmail)){
				int numloved=Integer.parseInt(entity.getProperty("numloved").toString());
				double score=numloved/(double)tot;
				entity.setProperty("score", score);
			}
		}

		Entity entity2=new Entity("userLovedPlaces");
		entity2.setProperty("userEmail", userEmail);
		entity2.setProperty("placeID", placeID);

	}
	
	
	
	
	
	public static void saveLovedEvents(String userEmail,String eventCategory,String eventID){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("userLovedEvents");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("userEmail").toString().equals(userEmail)
					&& entity.getProperty("eventID").toString().equals(eventID)){
				return;
			}
		}
		
		
		gaeQuery = new Query("userLovedCategories");
		pq = datastore.prepare(gaeQuery);
		

		boolean found=false;
		int tot=0;
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("userEmail").toString().equals(userEmail)
					&& entity.getProperty("eventCategory").toString().equals(eventCategory)){
				int numloved=Integer.parseInt(entity.getProperty("numloved").toString());
				entity.setProperty("numloved", numloved+1);
				datastore.put(entity);
				found=true;
			}
			
			if(entity.getProperty("userEmail").toString().equals(userEmail))
				tot+=Integer.parseInt(entity.getProperty("numloved").toString());
		}
		
		if(!found){
			Entity entity=new Entity("userLovedCategories");
			entity.setProperty("userEmail", userEmail);
			entity.setProperty("eventCategory", eventCategory);
			entity.setProperty("numloved", 1);
			++tot;
			entity.setProperty("score", 1.0/(double)tot);
		}
		
		for(Entity entity:pq.asIterable()){			
			if(entity.getProperty("userEmail").toString().equals(userEmail)){
				int numloved=Integer.parseInt(entity.getProperty("numloved").toString());
				double score=numloved/(double)tot;
				entity.setProperty("score", score);
			}
		}
		
		Entity entity2=new Entity("userLovedEvents");
		entity2.setProperty("userEmail", userEmail);
		entity2.setProperty("eventID", eventID);

	}
	
	
	
	public static void saveLovedTopics(String userEmail,String topic){
		int ind=topic.substring(1).indexOf('/');
		if(ind==-1)topic=topic.substring(1);
		else topic=topic.substring(1, ind);
		
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();		
		
		Query gaeQuery = new Query("userLovedTopics");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		

		boolean found=false;
		int tot=0;
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("userEmail").toString().equals(userEmail)
					&& entity.getProperty("topic").toString().equals(topic)){
				int numloved=Integer.parseInt(entity.getProperty("numloved").toString());
				entity.setProperty("numloved", numloved+1);
				datastore.put(entity);
				found=true;
			}
			
			if(entity.getProperty("userEmail").toString().equals(userEmail))
				tot+=Integer.parseInt(entity.getProperty("numloved").toString());
		}
		
		if(!found){
			Entity entity=new Entity("userLovedTopics");
			entity.setProperty("userEmail", userEmail);
			entity.setProperty("topic", topic);
			entity.setProperty("numloved", 1);
			++tot;
			entity.setProperty("score", 1.0/(double)tot);
		}
		
		for(Entity entity:pq.asIterable()){			
			if(entity.getProperty("userEmail").toString().equals(userEmail)){
				int numloved=Integer.parseInt(entity.getProperty("numloved").toString());
				double score=numloved/(double)tot;
				entity.setProperty("score", score);
			}
		}

	}
}
