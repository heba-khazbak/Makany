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
	private Key id;
	private String name, description,userEmail;
	Vector<String> categories;
	
	public Item(Key id,String name,String description,String userEmail,Vector<String> categories){
		this.id=id;
		this.name=name;
		this.description=description;
		this.userEmail=userEmail;
		this.categories=categories;
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
		
		this.id=item.getKey();
		for(String str:this.categories){
			Entity category=new Entity("itemCategories");
			category.setProperty("itemID", this.id);
			category.setProperty("name", str);
			datastore.put(category);
		}
		
		return true;
	}
	
	public static int deleteItem(Key itemID,String userEmail){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Vector<Key> keysToDelete=new Vector<Key>();
		try {
			Entity item = datastore.get(itemID);
			if(!item.getProperty("userEmail").toString().equals(userEmail))
				return 2;
		} catch (EntityNotFoundException e) {
			return 0;
		}
		keysToDelete.add(itemID);
		
		Query gaeQuery = new Query("itemCategories");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("itemID").equals(itemID))
				keysToDelete.add(entity.getKey());
			
		}
		
		for(Key k:keysToDelete)
			datastore.delete(k);
		
		return 1;
	}
}
