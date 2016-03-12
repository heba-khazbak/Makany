package com.eg.Makany.Services;

import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.simple.JSONObject;

import com.eg.Makany.Models.Offer;

@Path("/")
@Produces("text/html")
public class OffersServices {
	@POST
	@Path("/addOfferService")
	public String addOfferService( 
			@FormParam("storeMail") String storeMail,
			@FormParam("description") String description,
			@FormParam("photo") String photo) {
		
		
		JSONObject object = new JSONObject();
		
		if(new Offer(null,description,storeMail,photo).addOffer())
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();

	}
	
	@POST
	@Path("/editOfferService")
	public String editOfferService( 
			@FormParam("offerID") String offerID,
			@FormParam("description") String description,
			@FormParam("photo") String photo) {
		
		
		JSONObject object = new JSONObject();
		
		if(Offer.editOffer(offerID, description, photo))
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();

	}
	
	@POST
	@Path("/removeOfferService")
	public String removeOfferService(@FormParam("offerID") String offerID) {

		
		JSONObject object = new JSONObject();
		
		if(Offer.removeOffer(offerID))
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();

	}
	
	
	@POST
	@Path("/viewOfferService")
	public String viewOfferService(@FormParam("offerID") String offerID,
			@FormParam("userEmail") String userEmail) {
		
		JSONObject object = new JSONObject();
		
		Offer offer=Offer.viewOffer(offerID, userEmail);
		
		object.put("ID", offer.getID());
		object.put("storeMail", offer.getStoreMail());
		object.put("description", offer.getDescription());
		object.put("photo", offer.getPhoto());

		
		object.put("numViewers", String.valueOf(offer.getNumViewers()));
		object.put("viewersMails", offer.getParsedViewers());
		
		object.put("numThumbsup", String.valueOf(offer.getNumThumbsUp()));
		object.put("thumbsupMails", offer.getParsedThumbsup());
		
		object.put("numThumbsDown", String.valueOf(offer.getNumThumbsDown()));
		object.put("thumbsdownMails", offer.getParsedThumbsDown());
		
		return object.toString();
		
	}
	
	
	@POST
	@Path("/thumbsUpOfferService")
	public String thumbsUpOfferService(@FormParam("offerID") String offerID,
			@FormParam("userEmail") String userEmail) {
		
		JSONObject object = new JSONObject();
		
		int res=Offer.thumbsup(offerID, userEmail);
		
		if(res==1)
			object.put("Status", "OK");
		else if(res==2)
			object.put("Status", "ThumbsUpBefore");
		else
			object.put("Status", "Failed");
		
		return object.toString();
		
	}
	
	@POST
	@Path("/thumbsDownOfferService")
	public String thumbsDownOfferService(@FormParam("offerID") String offerID,
			@FormParam("userEmail") String userEmail) {
		
		JSONObject object = new JSONObject();
		
		int res=Offer.thumbsDown(offerID, userEmail);
		
		if(res==1)
			object.put("Status", "OK");
		else if(res==2)
			object.put("Status", "ThumbsDownBefore");
		else
			object.put("Status", "Failed");
		
		return object.toString();
		
	}
}
