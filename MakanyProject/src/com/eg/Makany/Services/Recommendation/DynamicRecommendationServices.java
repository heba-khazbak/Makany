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
import com.eg.Makany.Models.Post;
import com.eg.Makany.Models.User;
import com.eg.Makany.Models.BA.FoursquareModel;
import com.eg.Makany.Models.UpdateProfile.DynamicRecommender;
import com.eg.Makany.Models.UpdateProfile.StaticRecommender;
import com.eg.Makany.Services.behaviouralAnalysis.FoursquareService;

import fi.foyt.foursquare.api.FoursquareApiException;

@Path("/")
@Produces("text/html")
public class DynamicRecommendationServices {

	
	@POST
	@Path("/DynamicRecommendService")
	public String DynamicRecommendService(@FormParam("email") String email,
			@FormParam("longitude") String longitude, 
			@FormParam("latitude") String latitude) {
		
		String district = 
				DynamicRecommender.getMyDistrict(Double.parseDouble(longitude), 
						Double.parseDouble(latitude));
		
		Set<String> categories = StaticRecommender.getLovedCategories(email,district);
		
		Vector<FoursquareModel> places = new Vector<FoursquareModel>() ;
		try {
			places = FoursquareService.getTheNearByPlaces(latitude, longitude, categories);
		} catch (FoursquareApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Vector<Event> events = StaticRecommender.recommendEvents(district, categories);
		Vector<Post> posts = StaticRecommender.recommendPosts(district, categories,true);
		Vector<Item> loanItems = StaticRecommender.recommendItems(true, district, categories,true);
		Vector<Item> requestItems = StaticRecommender.recommendItems(false, district, categories,true);
		
		
		
		
		JSONArray arr = new JSONArray();
		
		
		
		for(FoursquareModel place:places){
			JSONObject object = new JSONObject();
			
			object.put("type", "Foursquare");
			object.put("name", place.getName());
			object.put("rating", place.getRating());
			object.put("distance", place.getDistance());
			object.put("address", place.getAddress());
			object.put("phone", place.getPhone());
			object.put("latitude", place.getLatitude());
			object.put("longitude", place.getLongitude());
			
			
			JSONArray catArr = new JSONArray();
			for(String cat:place.getCategory())
				catArr.add(cat);
			
			object.put("category",catArr);
			arr.add(object);
		}
		
		
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
