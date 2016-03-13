package com.eg.Makany.Models;

import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Item {
	private String id;
	private String name, description,userEmail, district, photo, state;
	private Vector<String> categories;
	
	public Item(){
		this.id="";
		this.name="";
		this.description="";
		this.userEmail="";
		this.district="";
		this.photo="";
		this.state="";
		this.categories=new Vector<String>();
	}
	
	public Item(String id,String name,String description,String userEmail,
			String district,String photo,String state,
			Vector<String> categories){
		this.id=id;
		this.name=name;
		this.description=description;
		this.userEmail=userEmail;
		this.district=district;
		this.photo=photo;
		this.state=state;
		this.categories=categories;
	}
	
	public String getID(){return id;}
	public String getName(){return name;}
	public String getDescription(){return description;}
	public String getUserEmail(){return userEmail;}
	public String getDistrict(){return district;}
	public String getPhoto(){return photo;}
	public String getState(){return state;}
	public String getParsedCategories(){
		String ret="";
		for(int i=0;i<categories.size();++i){
			if(i>0)ret+=";";
			ret+=categories.get(i);
		}
		return ret;
	}
	
	
	public boolean saveItem(boolean loan){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		String tableName="loanItem";
		if(!loan)tableName="requestItem";
		
		Entity item;
		if(this.id==null)item=new Entity(tableName);
		else item=new Entity(tableName,Long.parseLong(this.id));
		
		item.setProperty("name", name);
		item.setProperty("description", description);
		item.setProperty("userEmail", userEmail);
		item.setProperty("district", district);
		item.setProperty("photo", photo);
		item.setProperty("state", state);
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

		int ret=0;
		Vector<Key> keysToDelete=new Vector<Key>();
		Query gaeQuery = new Query("loanItem");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(String.valueOf(entity.getKey().getId()).equals(itemID)){
				if(!entity.getProperty("userEmail").toString().equals(userEmail))
					return 3;
				
				keysToDelete.add(entity.getKey());
				ret=1;
				break;
			}
			
		}
		
		gaeQuery = new Query("requestItem");
		pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(String.valueOf(entity.getKey().getId()).equals(itemID)){
				if(!entity.getProperty("userEmail").toString().equals(userEmail))
					return 3;
				
				keysToDelete.add(entity.getKey());
				ret=2;
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
		
		return ret;
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
				this.district=entity.getProperty("district").toString();
				this.photo=entity.getProperty("photo").toString();
				this.state=entity.getProperty("state").toString();
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
	
	
	public static Vector<Item> getAllItems(boolean loan,
			String specificDistrict,String specificState){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Vector<Item> ret=new Vector<Item>();
		Query gaeQuery;
		if(loan)gaeQuery = new Query("loanItem");
		else gaeQuery = new Query("requestItem");
		
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(specificDistrict!=null &&
					!specificDistrict.equals(entity.getProperty("district").toString()))
				continue;
			if(specificState!=null &&
					!specificState.equals(entity.getProperty("state").toString()))
				continue;
			
			ret.add(new Item().getItemByID(String.valueOf(entity.getKey().getId())));
		}
		
		
		return ret;
	}
}
