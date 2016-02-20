package com.eg.Makany.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.eg.Makany.Models.Offer;
import com.eg.Makany.Models.Post;
import com.eg.Makany.Models.Store;
import com.eg.Makany.Models.User;
import com.google.appengine.api.datastore.Key;

@Path("/")
@Produces("text/html")
public class UserServices {

	// works for user and store
	@POST
	@Path("/signUpService")
	public String signUpService(@FormParam("type") String type,
			@FormParam("name") String name, 
			@FormParam("email") String email,
			@FormParam("password") String password,
			@FormParam("birthDate") String birthDate,
			@FormParam("district") String district,
			@FormParam("gender") String gender,
			@FormParam("twitter") String twitter,
			@FormParam("foursquare") String foursquare,
			@FormParam("interests") Vector<String> interests) {
		
		JSONObject object = new JSONObject();
		if(type.equals("store")){
			if(Store.checkStore(email, password)>0){
				object.put("Status", "emailAlreadyExists");
				return object.toString();
			}
			Store store = new Store(null,name,email,password,district,null);
			if(store.saveStore())
				object.put("Status", "OK");
			else
				object.put("Status", "Failed");
		}
		else{
			if(User.checkUser(email, password)>0){
				object.put("Status", "emailAlreadyExists");
				return object.toString();
			}
			User user = new User(null,name,email,password,birthDate,district,gender,twitter,foursquare,interests);
			if(user.saveUser())
				object.put("Status", "OK");
			else
				object.put("Status", "Failed");
		}
		
		return object.toString();

	}
	
	
	// works for user and store
	@POST
	@Path("/LoginService")
	public String LoginService(@FormParam("email") String email, @FormParam("password") String password) {
		int check=User.checkUser(email, password);
		JSONObject object = new JSONObject();
		if(check==1)
			object.put("Status", "OK");
		else if(check==2)
			object.put("Status", "wrongPass");
		else{
			check=Store.checkStore(email, password);
			if(check==1)
				object.put("Status", "OK");
			else if(check==2)
				object.put("Status", "wrongPass");
			else
				object.put("Status", "Failed");
		}
		
		return object.toString();

	}
	
	
	// works for user type only
	@POST
	@Path("/editProfileService")
	public String editProfileService(
			@FormParam("name") String name, 
			@FormParam("email") String email,
			@FormParam("password") String password,
			@FormParam("birthDate") String birthDate,
			@FormParam("district") String district,
			@FormParam("gender") String gender,
			@FormParam("twitter") String twitter,
			@FormParam("foursquare") String foursquare,
			@FormParam("interests") Vector<String> interests) {
		
		JSONObject object = new JSONObject();
		
		if(!User.removeUser(email)){
			object.put("Status", "Failed");
			return object.toString();
		}
		
		User user = new User(null,name,email,password,birthDate,district,gender,twitter,foursquare,interests);
		if(user.saveUser())
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();

	}
	
	
	//works for store only
	@POST
	@Path("/addOffersService")
	public String addOffersService( 
			@FormParam("email") String email,
			@FormParam("offers") Vector<String> offers) {
		
		JSONObject object = new JSONObject();
		
		if(Offer.addOffers(email, offers))
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();

	}
	
	//works for store only
	@POST
	@Path("/removeOffersService")
	public String removeOffersService(@FormParam("offerIDs") Vector<Key> offerIDs) {
		
		JSONObject object = new JSONObject();
		
		if(Offer.removeOffers(offerIDs))
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();

	}

}