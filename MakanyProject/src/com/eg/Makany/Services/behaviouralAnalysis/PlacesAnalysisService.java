package com.eg.Makany.Services.behaviouralAnalysis;


import java.util.Vector;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.simple.JSONObject;

import com.eg.Makany.Models.Store;
import com.eg.Makany.Models.StoreReview;
import com.eg.Makany.Models.User;
import com.eg.Makany.Models.UpdateProfile.UserProfileUpdate;

@Path("/")
@Produces("text/html")
public class PlacesAnalysisService {
	@POST
	@Path("/AnalyzePlaces")
	public String AnalyzePlaces() {
		JSONObject object = new JSONObject();
		
		Vector <User> allUsers = User.getAllUsers();
		
		for (User user : allUsers)
		{
			Vector <StoreReview> lovedPlaces = StoreReview.getGoodReviewsByUser(user.getMail());
			
			for(StoreReview sreview:lovedPlaces){
				UserProfileUpdate.saveLovedPlaces(user.getMail(), Store.getStoreByID(sreview.getStoreMail()).getCategory(), sreview.getStoreMail());
			}
		}
		
		object.put("Status", "OK");
	    return object.toString();
}
	
}
