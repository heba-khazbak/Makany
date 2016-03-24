package com.eg.Makany.Models;

import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Store {
	private String id;
	private String name, email, password, district, category, description;
	private Vector<Offer> offers;
	private Vector<Review> reviews;
	
	public Store(String id, String name,String email,String password,String district,
			String category, String description,
			Vector<Offer> offers,Vector<Review> reviews){
		this.id=id;
		this.name=name;
		this.email=email;
		this.password=password;
		this.district=district;
		this.category=category;
		this.description=description;
		this.offers=offers;
		this.reviews=reviews;
	}
	
	public String getID(){return id;}
	public String getName(){return name;}
	public String getEmail(){return email;}
	public String getPassword(){return password;}
	public String getDistrict(){return district;}
	public String getCategory(){return category;}
	public String getDescription(){return description;}
	
	public boolean saveStore(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity store = new Entity("stores");
		
		if(this.name==null)this.name="";
		if(this.email==null)this.email="";
		if(this.password==null)this.password="";
		if(this.district==null)this.district="";
		if(this.category==null)this.category="";
		if(this.description==null)this.description="";

		store.setProperty("name", this.name);
		store.setProperty("email", this.email);
		store.setProperty("password", this.password);
		store.setProperty("district", this.district);
		store.setProperty("category", this.category);
		store.setProperty("description", this.description);
		datastore.put(store);

		return true;
	}
	
	public boolean editStore(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query gaeQuery = new Query("stores");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		if(this.name==null)this.name="";
		if(this.email==null)this.email="";
		if(this.password==null)this.password="";
		if(this.district==null)this.district="";
		if(this.category==null)this.category="";
		if(this.description==null)this.description="";
		
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("email").toString().equals(this.email)){
				entity.setProperty("name", this.name);
				entity.setProperty("password", this.password);
				entity.setProperty("district", this.district);
				entity.setProperty("category", this.category);
				entity.setProperty("description", this.description);
				datastore.put(entity);
				return true;
			}
		}

		return false;
	}
	
	public static Vector<Store> getAllStores(String specificCategory,String specificDistrict){
		Vector<Store> ret=new Vector<Store>();
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("stores");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(specificDistrict!=null && !specificDistrict.isEmpty() &&
					!specificDistrict.equals(entity.getProperty("district").toString()))
				continue;
			
			if(specificCategory!=null && !specificCategory.isEmpty() &&
					!specificCategory.equals(entity.getProperty("category").toString()))
				continue;
			
			String storeMail=entity.getProperty("email").toString();
			ret.add(new Store(String.valueOf(entity.getKey().getId()),
					entity.getProperty("name").toString(),
					storeMail,
					entity.getProperty("password").toString(),
					entity.getProperty("district").toString(),
					entity.getProperty("category").toString(),
					entity.getProperty("description").toString(),
					Offer.getOffers(storeMail),
					Review.getReviews(storeMail)));
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
