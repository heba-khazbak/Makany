package com.eg.Makany.Models;

import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Offer {
	private Key id;
	private String description, storeMail;
	
	public Offer(Key id,String description,String storeMail){
		this.id=id;
		this.description=description;
		this.storeMail=storeMail;
	}
	
	public static boolean addOffers(String email, Vector<String> offers){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		for(String str:offers){
			Entity offer=new Entity("offers");
			offer.setProperty("storeMail", email);
			offer.setProperty("description", str);
			datastore.put(offer);
		}
		return true;
	}
	
	public static Vector<Offer> getOffers(String email){
		Vector<Offer> ret=new Vector<Offer>();
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("offers");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("storeMail").toString().equals(email))
				ret.add(new Offer(entity.getKey(),
						entity.getProperty("description").toString(),
						entity.getProperty("storeMail").toString()));
			
		}
		return ret;
	}
	
	public static boolean removeOffers(Vector<Key> offerIDs){
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		for(Key id:offerIDs)
			datastore.delete(id);
		return true;
	}
}
