package com.eg.Makany.Services.behaviouralAnalysis;


import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.Category;
import fi.foyt.foursquare.api.entities.Recommendation;
import fi.foyt.foursquare.api.entities.Recommended;




@Path("/")
@Produces("text/html")
public class FoursquareService {

	@POST
	@Path("/GetNearByPlaces")
	public String getNearByPlaces(@FormParam("latitude") String latitude, @FormParam("longitude") String longitude) throws FoursquareApiException {
		
		JSONArray arr = new JSONArray();
		
		
		String CLIENT_ID = "CMK22P15BVQF2DVECGOOGBLUEBZGDVFCG3MZPUC2DLE2Y3TJ";
		String CLIENT_SECTET = "1CLOSPRBXZDAEWGNL5NNJJOH43YWOO5FVCH1O0EGG0MZLWLK";
		String CALLBACK = "GFWZS3H0TIIAAMEEDO4T0FAFDEUHCEB2IKJKGUAU2OT2HQRR";
	    FoursquareApi foursquareApi = new FoursquareApi(CLIENT_ID, CLIENT_SECTET, CALLBACK);
	    
	    
	    Result<Recommended> result = foursquareApi.venuesExplore(latitude + "," + longitude);
	    
	    if (result.getMeta().getCode() == 200) {
		     Recommendation[] myRecomended = result.getResult().getGroups()[0].getItems();
		     
		     
		      for (int i = 0 ; i < myRecomended.length ; i++) {
		    	 JSONObject object = new JSONObject();
		        // TODO: Do something we the data
		        System.out.println("Name "  + myRecomended[i].getVenue().getName());
		        System.out.println("Rating " + myRecomended[i].getVenue().getRating());
		        System.out.println("Distance " + myRecomended[i].getVenue().getLocation().getDistance());
		        
		        object.put("Name", myRecomended[i].getVenue().getName());
				object.put("Rating", myRecomended[i].getVenue().getRating());
				object.put("Distance", myRecomended[i].getVenue().getLocation().getDistance());
				

				/* System.out.println("Hours "+ myRecomended[i].getVenue().getHours().getStatus());
				 System.out.println("phone "+ myRecomended[i].getVenue().getContact().getPhone());
				 System.out.println("Address "+ myRecomended[i].getVenue().getLocation().getAddress());
				*/
		        System.out.println("Category");
		        JSONArray catArr = new JSONArray();
		        for (Category c : myRecomended[i].getVenue().getCategories())
		        {
		        	System.out.println(c.getName());
		        	catArr.add(c.getName());
		        	/*JSONObject catObj = new JSONObject();
		        	catObj.put("category", c.getName());
		        	catArr.add(catObj);*/
		        }
		        object.put("Category",catArr);
		        arr.add(object);
		        System.out.println("-------------");
		      }
		      
		      
		      
		    } else {
		      // TODO: Proper error handling
		      JSONObject object = new JSONObject();
		      object.put("Status", "Failed");
		      arr.add(object);
		      System.out.println("Error occured: ");
		      System.out.println("  code: " + result.getMeta().getCode());
		      System.out.println("  type: " + result.getMeta().getErrorType());
		      System.out.println("  detail: " + result.getMeta().getErrorDetail()); 
		    }
	    
	    return arr.toString();
		
		

	}
	
	void parse(String result)
	{
		
	}
	

	


}