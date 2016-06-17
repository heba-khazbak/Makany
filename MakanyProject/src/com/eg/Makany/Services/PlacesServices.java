package com.eg.Makany.Services;

import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.eg.Makany.Models.Offer;
import com.eg.Makany.Models.Store;
import com.eg.Makany.Models.Review;


@Path("/")
@Produces("text/html")
public class PlacesServices {
	
	@POST
	@Path("/reviewStoreService")
	public String reviewStoreService(@FormParam("userMail") String uMail,
			@FormParam("storeMail") String storeMail,
			@FormParam("review") String review,
			@FormParam("rating") String rating){
		
		JSONObject object = new JSONObject();
		
		Review sreview=new Review(null,uMail,storeMail,review,"",Integer.parseInt(rating));
		
		if(sreview.saveReview())
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();
	}
	
	
	@POST
	@Path("/getFilteredStoresService")
	public String getFilteredStoresService(@FormParam("category") String category,
			@FormParam("district") String district,
			@FormParam("maxStoreID") String maxStoreID){
		
		JSONArray arr = new JSONArray();
		
		Vector<Store> stores=Store.getAllStores(category, district,maxStoreID);
		
		for(Store store:stores){
			JSONObject object = new JSONObject();
			
			if(store!=null){
				object.put("ID", store.getID());
				object.put("name", store.getName());
				object.put("email", store.getEmail());
				object.put("password", store.getPassword());
				object.put("district", store.getDistrict());
				object.put("category", store.getCategory());
				object.put("description", store.getDescription());
				object.put("date", store.getDate());
			}
			
			arr.add(object);
		}
		
		return arr.toString();
	}
	
	@POST
	@Path("/getStoreOffersService")
	public String getStoreOffersService(@FormParam("storeMail") String storeMail){
		
		JSONArray arr = new JSONArray();
		
		Vector<Offer> offers=Offer.getOffers(storeMail);
		
		for(Offer offer:offers){
			JSONObject object = new JSONObject();
			
			if(offer!=null){
				object.put("ID", offer.getID());
				object.put("description", offer.getDescription());
				object.put("photo", offer.getPhoto());
				object.put("date", offer.getDate());
				
				object.put("numViewers", String.valueOf(offer.getNumViewers()));
				object.put("viewersMails", offer.getParsedViewers());
				
				object.put("numThumbsup", String.valueOf(offer.getNumThumbsUp()));
				object.put("thumbsupMails", offer.getParsedThumbsup());
				
				object.put("numThumbsDown", String.valueOf(offer.getNumThumbsDown()));
				object.put("thumbsdownMails", offer.getParsedThumbsDown());
			}
			
			arr.add(object);
		}
		
		return arr.toString();
	}
	
	@POST
	@Path("/getStoreReviewsService")
	public String getStoreReviewsService(@FormParam("storeMail") String storeMail){
		
		JSONArray arr = new JSONArray();
		
		Vector<Review> sreviews=Review.getReviews(storeMail);
		
		for(Review sreview:sreviews){
			JSONObject object = new JSONObject();
			
			if(sreview!=null){
				object.put("ID", sreview.getID());
				object.put("review", sreview.getReview());
				object.put("reviewerMail", sreview.getReviewerMail());
				object.put("date", sreview.getDate());
				object.put("rating", String.valueOf(sreview.getRating()));
			}
			
			arr.add(object);
		}
		
		return arr.toString();
	}
	
}
