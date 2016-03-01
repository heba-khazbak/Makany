package com.eg.Makany.Services.behaviouralAnalysis;

import java.io.StringWriter;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.XML;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import com.alchemyapi.api.AlchemyAPI;
import com.eg.Makany.Models.User;

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
		JSONObject object = new JSONObject();
		
		String CLIENT_ID = "CMK22P15BVQF2DVECGOOGBLUEBZGDVFCG3MZPUC2DLE2Y3TJ";
		String CLIENT_SECTET = "1CLOSPRBXZDAEWGNL5NNJJOH43YWOO5FVCH1O0EGG0MZLWLK";
		String CALLBACK = "GFWZS3H0TIIAAMEEDO4T0FAFDEUHCEB2IKJKGUAU2OT2HQRR";
	    FoursquareApi foursquareApi = new FoursquareApi(CLIENT_ID, CLIENT_SECTET, CALLBACK);
	    
	    
	    Result<Recommended> result = foursquareApi.venuesExplore(latitude + "," + longitude);
	    
	    if (result.getMeta().getCode() == 200) {
		     Recommendation[] myRecomended = result.getResult().getGroups()[0].getItems();
		     
		     
		      for (int i = 0 ; i < myRecomended.length ; i++) {
		        // TODO: Do something we the data
		        System.out.println("Name "  + myRecomended[i].getVenue().getName());
		        System.out.println("Rating " + myRecomended[i].getVenue().getRating());
		        System.out.println("Distance " + myRecomended[i].getVenue().getLocation().getDistance());
		        
		        System.out.println("Category");
		        for (Category c : myRecomended[i].getVenue().getCategories())
		        {
		        	System.out.println(c.getName());
		        }
		        System.out.println("-------------");
		      }
		      
		      object.put("Status", "OK");
		      
		    } else {
		      // TODO: Proper error handling
		      object.put("Status", "Failed");
		      
		      System.out.println("Error occured: ");
		      System.out.println("  code: " + result.getMeta().getCode());
		      System.out.println("  type: " + result.getMeta().getErrorType());
		      System.out.println("  detail: " + result.getMeta().getErrorDetail()); 
		    }
	    
	    return object.toString();
		
		

	}
	

	


}