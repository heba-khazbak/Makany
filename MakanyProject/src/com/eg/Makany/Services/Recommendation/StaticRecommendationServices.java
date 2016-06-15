package com.eg.Makany.Services.Recommendation;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.simple.JSONObject;

import com.eg.Makany.Models.UpdateProfile.StaticRecommender;

@Path("/")
@Produces("text/html")
public class StaticRecommendationServices {

	
	@POST
	@Path("/staticRecommendService")
	public String staticRecommendService(){
		JSONObject object = new JSONObject();
		
		if(StaticRecommender.recommend())
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();
	}
}
