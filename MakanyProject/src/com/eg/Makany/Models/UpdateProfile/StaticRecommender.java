package com.eg.Makany.Models.UpdateProfile;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

public class StaticRecommender {
	public static boolean recommend(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("userLovedCategories").addSort("score", SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		Map<String, Integer> userCategoryCnt = new HashMap<String,Integer>();
		Map<String, Vector<String> > map = new HashMap<String, Vector<String> >();
		for(Entity entity:pq.asIterable()){
			String user = entity.getProperty("userEmail").toString();
			String category = entity.getProperty("category").toString();
			Vector<String> tmp = new Vector<String>();
			
			int cnt = 0;
			if(userCategoryCnt.containsKey(user))
				cnt = userCategoryCnt.get(user);
			
			if(cnt == 2)continue;
			
			userCategoryCnt.put(user, cnt+1);
			
			if(!map.containsKey(category)){
				tmp.add(user);
				map.put(category, tmp);
			}
			else{	
				tmp = map.get(category);
				map.put(category, tmp);
			}
		}
		
		
		
		// recommend places
		gaeQuery = new Query("stores");
		pq = datastore.prepare(gaeQuery);
		
		for(Entity entity:pq.asIterable()){
			String category = entity.getProperty("category").toString();
			
			if(!map.containsKey(category))continue;
			// if rand()&1 continue;
			
			for(String user:map.get(category)){
				// notify user by message from heba's part
			}
		}
		
		
		
		// recommend events
		gaeQuery = new Query("events");
		pq = datastore.prepare(gaeQuery);
		
		for(Entity entity:pq.asIterable()){
			String category = entity.getProperty("category").toString();
			
			if(!map.containsKey(category))continue;
			// if rand()&1 continue;
			
			for(String user:map.get(category)){
				// notify user by message from heba's part
			}
		}
		return true;
	}
}
