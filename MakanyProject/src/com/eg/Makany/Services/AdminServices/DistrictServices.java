package com.eg.Makany.Services.AdminServices;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.eg.Makany.Models.District;

@Path("/")
@Produces("text/html")
public class DistrictServices {


	@POST
	@Path("/AddDistrictService")
	public String AddDistrict(@FormParam("districtName") String districtName) {
		JSONObject object = new JSONObject();
		District district = new District(districtName);
		
		if(district.saveDistrict())
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();

	}
	
	@POST
	@Path("/ShowAllDistrictsService")
	public String ShowAllDistricts() {
		JSONArray DistrictArray = new JSONArray();
		
		for (District D : District.getAllDistricts() )
		{
			JSONObject object = new JSONObject();
			object.put("DistrictName",D.getDistrictName());
			DistrictArray.add(object);
		}
		
		
		return DistrictArray.toString();

	}
	
	@POST
	@Path("/EditDistrictService")
	public String editDistrict(@FormParam("districtName") String districtName , @FormParam("newName") String newName) {
		JSONObject object = new JSONObject();
		
		if(District.editDistrict(districtName, newName))
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();

	}
	
	@POST
	@Path("/DeleteDistrictService")
	public String deleteDistrict(@FormParam("districtName") String districtName) {
		JSONObject object = new JSONObject();
		
		if(District.deleteDistrict(districtName))
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();

	}

}