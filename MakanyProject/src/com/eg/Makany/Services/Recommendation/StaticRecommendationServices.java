package com.eg.Makany.Services.Recommendation;

import java.util.Collections;
import java.util.Set;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.eg.Makany.Models.Event;
import com.eg.Makany.Models.Item;
import com.eg.Makany.Models.Offer;
import com.eg.Makany.Models.Post;
import com.eg.Makany.Models.Store;
import com.eg.Makany.Models.User;
import com.eg.Makany.Models.UpdateProfile.StaticRecommender;

@Path("/")
@Produces("text/html")
public class StaticRecommendationServices {

	
	@POST
	@Path("/staticRecommendService")
	public String staticRecommendService(@FormParam("email") String email){
		
		Set<String> categories = StaticRecommender.getLovedCategories(email);
		String district = User.getUserDistrict(email);
		
		Vector<Event> events = StaticRecommender.recommendEvents(district, categories);
		Vector<Store> stores = StaticRecommender.recommendPlaces(district, categories);
		Vector<Post> posts = StaticRecommender.recommendPosts(district, categories);
		Vector<Item> loanItems = StaticRecommender.recommendItems(true, district, categories);
		Vector<Item> requestItems = StaticRecommender.recommendItems(false, district, categories);
		
		
		JSONArray arr = new JSONArray();
		
		for(Event event:events){
			JSONObject object = new JSONObject();
			
			if(event!=null){
				object.put("type", "Event");
				
				object.put("username", User.getUserName(event.getOwnerMail()));
				object.put("id", event.getID());
				object.put("name", event.getName());
				object.put("category", event.getCategory());
				object.put("description", event.getDescription());
				object.put("latitude", event.getLatitude());
				object.put("longitude", event.getLongitude());
				object.put("ownerMail", event.getOwnerMail());
				object.put("district", event.getDistrict());
				object.put("date", event.getDate());
				object.put("goingMails", event.getParsedGoingMails());
				object.put("postIDs", event.getParsedPostIDs());
			}
			
			arr.add(object);
		}
		
		
		
		for(Store store:stores){
			JSONObject object = new JSONObject();
			
			if(store==null)continue;
			
			
			String storeMail = store.getEmail();
			Vector<Offer> offers=Offer.getOffers(storeMail);
			
			if(offers==null || offers.isEmpty()){
				object.put("type", "Store");
				
				object.put("ID", store.getID());
				object.put("name", store.getName());
				object.put("email", store.getEmail());
				object.put("password", store.getPassword());
				object.put("district", store.getDistrict());
				object.put("category", store.getCategory());
				object.put("description", store.getDescription());
				object.put("date", store.getDate());
				
				arr.add(object);
			}
			
			for(Offer offer:offers){
				object = new JSONObject();
				
				if(offer!=null){
					object.put("type", "Offer");
					
					object.put("category", store.getCategory());
					object.put("storeName", store.getName());
					object.put("ID", offer.getID());
					object.put("description", offer.getDescription());
					object.put("photo", offer.getPhoto());
					
					object.put("numViewers", String.valueOf(offer.getNumViewers()));
					object.put("viewersMails", offer.getParsedViewers());
					
					object.put("numThumbsup", String.valueOf(offer.getNumThumbsUp()));
					object.put("thumbsupMails", offer.getParsedThumbsup());
					
					object.put("numThumbsDown", String.valueOf(offer.getNumThumbsDown()));
					object.put("thumbsdownMails", offer.getParsedThumbsDown());
				}
				
				arr.add(object);
			}
		}
		
		
		
		for(Post post:posts){
			JSONObject object = new JSONObject();
			
			if(post!=null){
				object.put("type", "Post");
				
				object.put("username", User.getUserName(post.getUserEmail()));
				object.put("categories", post.getParsedCategories());
				
				object.put("ID", post.getID());
				object.put("postType", post.getPostType());
				object.put("content", post.getContent());
				object.put("photo", post.getPhoto());
				object.put("userEmail", post.getUserEmail());
				object.put("district", post.getDistrict());
				object.put("onEventID", post.getOnEventID());
				object.put("date", post.getDate());
				object.put("score", post.getScore());
				
				object.put("numApprovals", String.valueOf(post.getNumApprovals()));
				object.put("approvalMails", post.getParsedApprovals());
				
				object.put("numDisApprovals", String.valueOf(post.getNumDisApprovals()));
				object.put("disapprovalMails", post.getParsedDisApprovals());
				
				object.put("numReports", String.valueOf(post.getNumReports()));
				object.put("reportMails", post.getParsedReports());
			}
			
			arr.add(object);
		}
		
		
		
		for(Item item:loanItems){
			JSONObject object = new JSONObject();
			
			if(item!=null){
				object.put("type", "LoanItem");
				object.put("username", User.getUserName(item.getUserEmail()));
				
				object.put("id", item.getID());
				object.put("name", item.getName());
				object.put("description", item.getDescription());
				object.put("userEmail", item.getUserEmail());
				object.put("district", item.getDistrict());
				object.put("photo", item.getPhoto());
				object.put("state", item.getState());
				object.put("date", item.getDate());
				object.put("categories", item.getParsedCategories());
			}
			
			arr.add(object);
		}
		
		
		
		for(Item item:requestItems){
			JSONObject object = new JSONObject();
			
			if(item!=null){
				object.put("type", "RequestItem");
				object.put("username", User.getUserName(item.getUserEmail()));
				
				object.put("id", item.getID());
				object.put("name", item.getName());
				object.put("description", item.getDescription());
				object.put("userEmail", item.getUserEmail());
				object.put("district", item.getDistrict());
				object.put("photo", item.getPhoto());
				object.put("state", item.getState());
				object.put("date", item.getDate());
				object.put("categories", item.getParsedCategories());
			}
			
			arr.add(object);
		}
		
		
		Collections.shuffle(arr);
		return arr.toString();
	}
}
