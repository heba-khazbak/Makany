package com.eg.Makany.Services.behaviouralAnalysis;


import java.util.Set;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.eg.Makany.Models.BA.FoursquareModel;

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
	    
	    Vector<FoursquareModel> myPlaces = new Vector<FoursquareModel>();
	    
	    if (result.getMeta().getCode() == 200) {
		     Recommendation[] myRecomended = result.getResult().getGroups()[0].getItems();
		     
		     
		      for (int i = 0 ; i < myRecomended.length ; i++) {
		    	 JSONObject object = new JSONObject();
		        // TODO: Do something we the data
		       /* System.out.println("Name "  + myRecomended[i].getVenue().getName());
		        System.out.println("Rating " + myRecomended[i].getVenue().getRating());
		        System.out.println("Distance " + myRecomended[i].getVenue().getLocation().getDistance());
		        System.out.println("Address "+ myRecomended[i].getVenue().getLocation().getAddress());
				System.out.println("latitude "+ myRecomended[i].getVenue().getLocation().getLat());
				System.out.println("Longitude "+ myRecomended[i].getVenue().getLocation().getLng());
				System.out.println("Phone "+ myRecomended[i].getVenue().getContact().getFormattedPhone());
				System.out.println("url "+ myRecomended[i].getVenue().getUrl());*/
				
		        
				
				FoursquareModel place = new FoursquareModel(myRecomended[i].getVenue().getName(), myRecomended[i].getVenue().getLocation().getAddress(),
						myRecomended[i].getVenue().getRating().toString(), myRecomended[i].getVenue().getContact().getFormattedPhone(),
						myRecomended[i].getVenue().getLocation().getDistance().toString(),
						myRecomended[i].getVenue().getLocation().getLat().toString(), myRecomended[i].getVenue().getLocation().getLng().toString());
				
				object.put("name", place.getName());
				object.put("rating", place.getRating());
				object.put("distance", place.getDistance());
				object.put("address", place.getAddress());
				object.put("phone", place.getPhone());
				object.put("latitude", place.getLatitude());
				object.put("longitude", place.getLongitude());
				
		        System.out.println("Category");
		        JSONArray catArr = new JSONArray();
		        for (Category c : myRecomended[i].getVenue().getCategories())
		        {
		        	System.out.println(c.getName());
		        	//c.getParents().length
		        	catArr.add(c.getName());
		        	place.addCategory(c.getName());
		        }
		        object.put("category",catArr);
		        arr.add(object);
		        //System.out.println("-------------");
		        
		        myPlaces.add(place);
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
	    
	    
	    for (FoursquareModel F : myPlaces)
	    	System.out.println(F.toString());
	    
	   return arr.toString();
	    
		
		

	}
	
	
	public static Vector<FoursquareModel> getTheNearByPlaces(String latitude, String longitude,Set<String> categories) throws FoursquareApiException {
		
		
		
		String CLIENT_ID = "CMK22P15BVQF2DVECGOOGBLUEBZGDVFCG3MZPUC2DLE2Y3TJ";
		String CLIENT_SECTET = "1CLOSPRBXZDAEWGNL5NNJJOH43YWOO5FVCH1O0EGG0MZLWLK";
		String CALLBACK = "GFWZS3H0TIIAAMEEDO4T0FAFDEUHCEB2IKJKGUAU2OT2HQRR";
	    FoursquareApi foursquareApi = new FoursquareApi(CLIENT_ID, CLIENT_SECTET, CALLBACK);
	    
	    
	    Result<Recommended> result = foursquareApi.venuesExplore(latitude + "," + longitude);
	    
	    Vector<FoursquareModel> myPlaces = new Vector<FoursquareModel>();
	    
	    if (result.getMeta().getCode() == 200) {
		     Recommendation[] myRecomended = result.getResult().getGroups()[0].getItems();
		     
		     
		      for (int i = 0 ; i < myRecomended.length ; i++) {
				
		        
				
				FoursquareModel place = new FoursquareModel(myRecomended[i].getVenue().getName(), myRecomended[i].getVenue().getLocation().getAddress(),
						myRecomended[i].getVenue().getRating().toString(), myRecomended[i].getVenue().getContact().getFormattedPhone(),
						myRecomended[i].getVenue().getLocation().getDistance().toString(),
						myRecomended[i].getVenue().getLocation().getLat().toString(), myRecomended[i].getVenue().getLocation().getLng().toString());
				
				

				boolean ok=false;
		        for (Category c : myRecomended[i].getVenue().getCategories())
		        {
		        	if(categories.contains(c.getName()))ok=true;
		        	place.addCategory(c.getName());
		        }
		        
		        if(ok)
		        	myPlaces.add(place);
		      }
		      
		      
		      
		    } else {
		      System.out.println("Error occured: ");
		      System.out.println("  code: " + result.getMeta().getCode());
		      System.out.println("  type: " + result.getMeta().getErrorType());
		      System.out.println("  detail: " + result.getMeta().getErrorDetail()); 
		    }

	    
	   return myPlaces;

	}

	

	


}