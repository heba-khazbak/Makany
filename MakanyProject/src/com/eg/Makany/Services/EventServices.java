package com.eg.Makany.Services;

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
import com.eg.Makany.Models.Review;


@Path("/")
@Produces("text/html")
public class EventServices {

	@POST
	@Path("/createEventService")
	public String createEventService(@FormParam("name") String name,
			@FormParam("category") String category,
			@FormParam("description") String description,
			@FormParam("latitude") String latitude, 
			@FormParam("longitude") String longitude, 
			@FormParam("ownerMail") String ownerMail) {
		
		JSONObject object = new JSONObject();

		
		if(new Event(null,name,category,description,
				Double.parseDouble(latitude),Double.parseDouble(longitude),
				ownerMail,null,null).saveEvent())
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();
		
	}
	
	@POST
	@Path("/editEventService")
	public String editEventService( 
			@FormParam("eventID") String eventID,
			@FormParam("category") String category,
			@FormParam("description") String description,
			@FormParam("latitude") String latitude,
			@FormParam("longitude") String longitude) {
		
		
		JSONObject object = new JSONObject();
		
		if(Event.editEvent(eventID, category, description, Double.parseDouble(latitude), Double.parseDouble(longitude)))
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();

	}
	
	@POST
	@Path("/joinEventService")
	public String joinEventService(@FormParam("eventID") String eventID,
			@FormParam("userMail") String userMail) {
		
		
		JSONObject object = new JSONObject();
		
		if(Event.addGoingUser(eventID, userMail))
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();

	}
	
	
	@POST
	@Path("/postOnEventService")
	public String postOnEventService(@FormParam("eventID") String eventID,
			@FormParam("postType") String postType,
			@FormParam("content") String content, 
			@FormParam("photo") String photo,
			@FormParam("district") String district,
			@FormParam("onEventID") String onEventID,
			@FormParam("userEmail") String userEmail,
			@FormParam("categories") String strCategories) {
		
		Vector<String> categories=new Vector<String>();
		if(strCategories!=null && !strCategories.isEmpty()){
			String tmp[]=strCategories.split(";");
			for(int i=0;i<tmp.length;++i)categories.add(tmp[i]);
		}
		
		JSONObject object = new JSONObject();
		
		Post post=new Post(null,postType,content,photo,userEmail,district,onEventID,0,categories);
		
		if(post.savePost()){
			if(Event.addPost(eventID, post.getID()))
				object.put("Status", "OK");
			else
				object.put("Status", "Failed");
		}
		else
			object.put("Status", "Failed");
		
		return object.toString();
		
	}
	
	@POST
	@Path("/reviewEventService")
	public String reviewEventService(@FormParam("userMail") String uMail,
			@FormParam("eventID") String eventID,
			@FormParam("review") String review,
			@FormParam("rating") String rating){
		
		JSONObject object = new JSONObject();
		
		Review sreview=new Review(null,uMail,eventID,review,Integer.parseInt(rating));
		
		if(sreview.saveReview())
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();
	}
	
	@POST
	@Path("/getEventGoingService")
	public String getEventGoingService(@FormParam("eventID") String eventID){
		
		JSONArray arr = new JSONArray();
		
		for(String str:Event.getEventGoing(eventID)){
			JSONObject object = new JSONObject();
			object.put("email", str);
			arr.add(object);
		}
		
		
		return arr.toString();
	}
	
	@POST
	@Path("/getGoingEventsService")
	public String getGoingEventsService(@FormParam("userEmail") String userEmail,
			@FormParam("maxEventID") String maxEventID){
		
		JSONArray arr = new JSONArray();
		
		for(Event event:Event.getGoingEvents(userEmail,maxEventID)){
			JSONObject object = new JSONObject();
			
			if(event!=null){
				object.put("id", event.getID());
				object.put("name", event.getName());
				object.put("category", event.getCategory());
				object.put("description", event.getDescription());
				object.put("latitude", event.getLatitude());
				object.put("longitude", event.getLongitude());
				object.put("ownerMail", event.getOwnerMail());
				object.put("goingMails", event.getParsedGoingMails());
				object.put("postIDs", event.getParsedPostIDs());
			}
			arr.add(object);
		}
		
		
		return arr.toString();
	}
	
	@POST
	@Path("/getEventByIDService")
	public String getEventByIDService(@FormParam("eventID") String eventID) {
		
		JSONObject object = new JSONObject();
		
		Event event=new Event().getEventByID(eventID);
		
		if(event!=null){
			object.put("id", event.getID());
			object.put("name", event.getName());
			object.put("category", event.getCategory());
			object.put("description", event.getDescription());
			object.put("latitude", event.getLatitude());
			object.put("longitude", event.getLongitude());
			object.put("ownerMail", event.getOwnerMail());
			object.put("goingMails", event.getParsedGoingMails());
			object.put("postIDs", event.getParsedPostIDs());
		}
		
		return object.toString();
		
	}
}
