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
import com.eg.Makany.Models.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

public class StaticRecommender {
	
	private static Map<String,Integer> userLoved;
	
	public static Set<String> getLovedCategories(String email){
		DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
		Query gaeQuery = new Query("userLovedCategories").addSort("numloved", SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		
		int cnt = 0;
		Set<String> ret = new HashSet<String>();
		userLoved = new HashMap<String,Integer>();
		
		
		// get from own loved categories
		for(Entity entity:pq.asIterable()){
			if(!email.equals(entity.getProperty("userEmail").toString()))
				continue;
			
			String category = entity.getProperty("category").toString();
			
			if(cnt<3)
				ret.add(category);
			
			userLoved.put(category, Integer.parseInt(entity.getProperty("numloved").toString()));
			
			++cnt;
		}
		
		
		
		// get from neighbors loved categories using cosine similarity
		
		Vector<String> userMails = User.getOtherUserMails(email);
		
		for(String mail:userMails){
			Map<String,Integer> other = getAllLovedCategories(mail);
			
			Vector<Integer> v1 = new Vector<Integer>();
			Vector<Integer> v2 = new Vector<Integer>();
			
			for(Map.Entry<String, Integer> entry:userLoved.entrySet()){
				String category = entry.getKey();
				v1.add(entry.getValue());
				
				if(other.containsKey(category))
					v2.add(other.get(category));
				
				else
					v2.add(0);
			}
			
			for(Map.Entry<String, Integer> entry:other.entrySet()){
				String category = entry.getKey();
				
				
				if(!userLoved.containsKey(category)){
					v1.add(0);
					v2.add(entry.getValue());
				}
			}
			
			
			if(cosineSimilarity(v1,v2) - 1e-9 > 0.65){
				for(Map.Entry<String, Integer> entry:other.entrySet())
					ret.add(entry.getKey());
			}
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
			
			Date date = (Date)entity.getProperty("to");
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
					Double.parseDouble(entity.getProperty("latitude").toString()),
					Double.parseDouble(entity.getProperty("longitude").toString()),
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
	
	
	
	
	
	private static Map<String,Integer> getAllLovedCategories(String email){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("userLovedCategories");
		PreparedQuery pq = datastore.prepare(gaeQuery);
			
		Map<String,Integer> ret = new HashMap<String,Integer>();
		for(Entity entity:pq.asIterable()){
			if(email.equals(entity.getProperty("userEmail").toString()))
				ret.put(entity.getProperty("category").toString(),
						Integer.parseInt(entity.getProperty("numloved").toString()));
		}
		return ret;
	}
	
	
	
	private static double cosineSimilarity(Vector<Integer> v1, Vector<Integer> v2){
		double magnitude1 = Math.sqrt(dotProduct(v1,v1));
		double magnitude2 = Math.sqrt(dotProduct(v2,v2));

		return dotProduct(v1,v2) / (magnitude1 * magnitude2);
	}
	
	private static double dotProduct(Vector<Integer> v1,Vector<Integer> v2){
		double dotproduct = 0.0;
		for(int i=0;i<v1.size();++i){
			double x=v1.get(i), y=v2.get(i);
			dotproduct += (x*y);
		}
		return dotproduct;
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
