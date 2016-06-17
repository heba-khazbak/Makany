package com.eg.Makany.Services.AdminServices;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.eg.Makany.Models.Category;
import com.eg.Makany.Models.Interest;

@Path("/")
@Produces("text/html")
public class InterestServices {


	@POST
	@Path("/AddInterestService")
	public String AddInterest(@FormParam("interestValue") String interestValue) {
		/*JSONObject object = new JSONObject();
		Interest interest = new Interest(interestValue);
		
		if(interest.saveInterest())
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();*/
		
		JSONObject object = new JSONObject();
		Category category = new Category(interestValue);
		
		if(category.saveCategory())
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();

	}
	
	@POST
	@Path("/ShowAllInterestsService")
	public String ShowAllInterests() {
		JSONArray InterestArray = new JSONArray();
		
		for (Category I : Category.getAllCategories() )
		{
			JSONObject object = new JSONObject();
			object.put("InterestValue",I.getCategoryValue());
			InterestArray.add(object);
		}
		
		
		return InterestArray.toString();

	}
	
	@POST
	@Path("/EditInterestService")
	public String editInterest(@FormParam("interestValue") String interestValue , @FormParam("newName") String newName) {
		JSONObject object = new JSONObject();
		
		if(Interest.editInterest(interestValue, newName))
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();

	}
	
	@POST
	@Path("/DeleteInterestService")
	public String deleteInterest(@FormParam("interestValue") String interestValue) {
		JSONObject object = new JSONObject();
		
		if(Interest.deleteInterest(interestValue))
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();

	}

}