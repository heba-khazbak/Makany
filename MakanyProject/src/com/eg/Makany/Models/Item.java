package com.eg.Makany.Models;

import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Item {
	private String id;
	private String name, description,userEmail;
	private Vector<String> categories;
	
	public Item(){
		this.id="";
		this.name="";
		this.description="";
		this.userEmail="";
		this.categories=new Vector<String>();
	}
	
	public Item(String id,String name,String description,String userEmail,Vector<String> categories){
		this.id=id;
		this.name=name;
		this.description=description;
		this.userEmail=userEmail;
		this.categories=categories;
	}
	
	public String getID(){return id;}
	public String getName(){return name;}
	public String getDescription(){return description;}
	public String getUserEmail(){return userEmail;}
	public String getParsedCategories(){
		String ret="";
		for(int i=0;i<categories.size();++i){
			if(i>0)ret+="_";
			ret+=categories.get(i);
		}
		return ret;
	}
	
	
	public boolean saveItem(boolean loan){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Entity item;
		if(loan)item=new Entity("loanItem");
		else item=new Entity("requestItem");
		
		item.setProperty("name", name);
		item.setProperty("description", description);
		item.setProperty("userEmail", userEmail);
		datastore.put(item);
		
		this.id=String.valueOf(item.getKey().getId());
		for(String str:this.categories){
			Entity category=new Entity("itemCategories");
			category.setProperty("itemID", this.id);
			category.setProperty("name", str);
			datastore.put(category);
		}
		
		return true;
	}
	
	public static int deleteItem(String itemID,String userEmail){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Vector<Key> keysToDelete=new Vector<Key>();
		Query gaeQuery = new Query("loanItem");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(String.valueOf(entity.getKey().getId()).equals(itemID)){
				if(!entity.getProperty("userEmail").toString().equals(userEmail))
					return 2;
				
				keysToDelete.add(entity.getKey());
				break;
			}
			
		}
		
		gaeQuery = new Query("requestItem");
		pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(String.valueOf(entity.getKey().getId()).equals(itemID)){
				if(!entity.getProperty("userEmail").toString().equals(userEmail))
					return 2;
				
				keysToDelete.add(entity.getKey());
				break;
			}
			
		}
		
		
		gaeQuery = new Query("itemCategories");
		pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("itemID").toString().equals(itemID))
				keysToDelete.add(entity.getKey());
			
		}
		
		for(Key k:keysToDelete)
			datastore.delete(k);
		
		return 1;
	}
	
	
	public Item getItemByID(String itemID){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		this.id=itemID;
		Query gaeQuery = new Query("loanItem");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(String.valueOf(entity.getKey().getId()).equals(itemID)){
				this.name=entity.getProperty("name").toString();
				this.description=entity.getProperty("description").toString();
				this.userEmail=entity.getProperty("userEmail").toString();
				break;
			}
			
		}
		
		gaeQuery = new Query("requestItem");
		pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(String.valueOf(entity.getKey().getId()).equals(itemID)){
				this.name=entity.getProperty("name").toString();
				this.description=entity.getProperty("description").toString();
				this.userEmail=entity.getProperty("userEmail").toString();
				break;
			}
			
		}
		
		
		gaeQuery = new Query("itemCategories");
		pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("itemID").toString().equals(itemID))
				this.categories.add(entity.getProperty("name").toString());
			
		}
		
		return this;
	}
	
	
	public static Vector<Item> getAllItems(boolean loan){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Vector<Item> ret=new Vector<Item>();
		Query gaeQuery;
		if(loan)gaeQuery = new Query("loanItem");
		else gaeQuery = new Query("requestItem");
		
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable())
			ret.add(new Item().getItemByID(String.valueOf(entity.getKey().getId())));
		
		
		return ret;
	}
}
