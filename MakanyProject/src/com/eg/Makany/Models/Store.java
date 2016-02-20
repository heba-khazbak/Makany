package com.eg.Makany.Models;

import java.util.List;
import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Store {
	private Key id;
	private String name, email, password, district;
	private Vector<Offer> offers;
	
	public Store(Key id, String name,String email,String password,String district,Vector<Offer> offers){
		this.id=id;
		this.name=name;
		this.email=email;
		this.password=password;
		this.district=district;
		this.offers=offers;
	}
	
	public boolean saveStore(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity store = new Entity("stores");

		store.setProperty("name", this.name);
		store.setProperty("email", this.email);
		store.setProperty("password", this.password);
		store.setProperty("district", this.district);
		datastore.put(store);

		return true;
	}
	
	public static Vector<Store> getAllStores(){
		Vector<Store> ret=new Vector<Store>();
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("stores");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			String storeMail=entity.getProperty("email").toString();
			ret.add(new Store(entity.getKey(),
					entity.getProperty("name").toString(),
					storeMail,
					entity.getProperty("password").toString(),
					entity.getProperty("district").toString(),
					Offer.getOffers(storeMail)));
		}
		
		return ret;
	}
	
	public static int checkStore(String email, String password){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("stores");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("email").toString().equals(email)){
				if(entity.getProperty("password").toString().equals(password))
					return 1;
				else
					return 2;
			}
		}
		return 0;
	}
}
