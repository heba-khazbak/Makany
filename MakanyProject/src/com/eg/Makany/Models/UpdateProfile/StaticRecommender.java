package com.eg.Makany.Models.UpdateProfile;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.eg.Makany.Models.Event;
import com.eg.Makany.Models.Item;
import com.eg.Makany.Models.Offer;
import com.eg.Makany.Models.Post;
import com.eg.Makany.Models.Review;
import com.eg.Makany.Models.Store;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

public class StaticRecommender {
	
	public static Set<String> getLovedCategories(String email){
		DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
		Query gaeQuery = new Query("userLovedCategories").addSort("score", SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		
		int cnt = 0;
		Set<String> ret = new HashSet<String>();
		
		for(Entity entity:pq.asIterable()){
			if(!email.equals(entity.getProperty("userEmail").toString()))
				continue;
			
			ret.add(entity.getProperty("category").toString());
			
			++cnt;
			if(cnt==3)break;
		}
		
		return ret;
	}

	public static Vector<Event> recommendEvents(String district, 
			Set<String> categories){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("events");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		Date curDate = new Date();
		Vector<Event> ret = new Vector<Event>();
		for(Entity entity:pq.asIterable()){
			if( (!district.isEmpty() && !district.equals(entity.getProperty("district").toString()))
					|| !categories.contains(entity.getProperty("category").toString()) )
				continue;
			
			Date date = (Date)entity.getProperty("date");
			if(date.before(curDate))
				continue;
			
			
			ret.add(new Event().getEventByID(String.valueOf(entity.getKey().getId())));
		}
		
		
		return ret;
	}
	
	public static Vector<Store> recommendPlaces(String district, 
			Set<String> categories){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("stores");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		Vector<Store> ret = new Vector<Store>();
		for(Entity entity:pq.asIterable()){
			if( (!district.isEmpty() && !district.equals(entity.getProperty("district").toString()))
					|| !categories.contains(entity.getProperty("category").toString()) )
				continue;
			
			String storeMail=entity.getProperty("email").toString();
			ret.add(new Store(String.valueOf(entity.getKey().getId()),
					entity.getProperty("name").toString(),
					storeMail,
					entity.getProperty("password").toString(),
					entity.getProperty("district").toString(),
					entity.getProperty("category").toString(),
					entity.getProperty("description").toString(),
					entity.getProperty("date").toString(),
					Offer.getOffers(storeMail),
					Review.getReviews(storeMail)));
		}
		
		return ret;
	}
	
	
	public static Vector<Post> recommendPosts(String district, 
			Set<String> categories){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		

		Vector<Post> ret = new Vector<Post>();
		for(Entity entity:pq.asIterable()){
			if( (!district.isEmpty() && !district.equals(entity.getProperty("district").toString()))
					|| Integer.parseInt(entity.getProperty("score").toString()) < 0)
				continue;
			
			
			Post p=new Post().getPost(entity);
			
			boolean ok=true;
			if(!categories.isEmpty()){
				ok=false;
				Vector<String> postCategories = p.getCategories();
				for(int i=0;i<postCategories.size();++i)
					if(categories.contains(postCategories.get(i))){
						ok=true;
						break;
					}
			}
			
			if(ok)ret.add(p);
		}
		
		return ret;
	}
	
	public static Vector<Item> recommendItems(boolean loan,
			String district, 
			Set<String> categories){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery;
		if(loan)gaeQuery = new Query("loanItem");
		else gaeQuery = new Query("requestItem");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		Vector<Item> ret = new Vector<Item>();
		for(Entity entity:pq.asIterable()){
			if( (!district.isEmpty() && !district.equals(entity.getProperty("district").toString()))
					|| !entity.getProperty("state").toString().equals("Open"))
				continue;
			
			
			Item item = new Item().getItemByID(String.valueOf(entity.getKey().getId()));
			
			boolean ok=true;
			if(!categories.isEmpty()){
				ok=false;
				Vector<String> itemCategories = item.getCategories();
				for(int i=0;i<itemCategories.size();++i)
					if(categories.contains(itemCategories.get(i))){
						ok=true;
						break;
					}
			}
			
			if(ok)ret.add(item);

		}
		
		return ret;
	}
	
//	public static boolean recommend(){
//		DatastoreService datastore = DatastoreServiceFactory
//				.getDatastoreService();
//		Query gaeQuery = new Query("userLovedCategories").addSort("score", SortDirection.DESCENDING);
//		PreparedQuery pq = datastore.prepare(gaeQuery);
//		
//		Map<String, Integer> userCategoryCnt = new HashMap<String,Integer>();
//		Map<String, Vector<String> > map = new HashMap<String, Vector<String> >();
//		for(Entity entity:pq.asIterable()){
//			String user = entity.getProperty("userEmail").toString();
//			String category = entity.getProperty("category").toString();
//			Vector<String> tmp = new Vector<String>();
//			
//			int cnt = 0;
//			if(userCategoryCnt.containsKey(user))
//				cnt = userCategoryCnt.get(user);
//			
//			if(cnt == 2)continue;
//			
//			userCategoryCnt.put(user, cnt+1);
//			
//			if(!map.containsKey(category)){
//				tmp.add(user);
//				map.put(category, tmp);
//			}
//			else{	
//				tmp = map.get(category);
//				map.put(category, tmp);
//			}
//		}
//		
//		
//		
//		// recommend places
//		gaeQuery = new Query("stores");
//		pq = datastore.prepare(gaeQuery);
//		
//		for(Entity entity:pq.asIterable()){
//			String category = entity.getProperty("category").toString();
//			
//			if(!map.containsKey(category))continue;
//			// if rand()&1 continue;
//			
//			for(String user:map.get(category)){
//				// notify user by message from heba's part
//			}
//		}
//		
//		
//		
//		// recommend events
//		gaeQuery = new Query("events");
//		pq = datastore.prepare(gaeQuery);
//		
//		for(Entity entity:pq.asIterable()){
//			String category = entity.getProperty("category").toString();
//			
//			if(!map.containsKey(category))continue;
//			// if rand()&1 continue;
//			
//			for(String user:map.get(category)){
//				// notify user by message from heba's part
//			}
//		}
//		return true;
//	}
}
