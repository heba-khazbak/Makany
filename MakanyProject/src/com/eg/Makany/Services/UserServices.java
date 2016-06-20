package com.eg.Makany.Services;


import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;



import org.json.simple.JSONArray;
import org.json.simple.JSONObject;



import com.eg.Makany.Models.Store;
import com.eg.Makany.Models.User;

@Path("/")
@Produces("text/html")
public class UserServices {

	// works for user and store
	@POST
	@Path("/signUpService")
	public String signUpService(@FormParam("uType") String uType,
			@FormParam("name") String name, 
			@FormParam("email") String email,
			@FormParam("password") String password,
			@FormParam("birthDate") String birthDate,
			@FormParam("district") String district,
			@FormParam("category") String category,
			@FormParam("description") String description,
			@FormParam("latitude") String latitude,
			@FormParam("longitude") String longitude,
			@FormParam("gender") String gender,
			@FormParam("twitter") String twitter,
			@FormParam("foursquare") String foursquare,
			@FormParam("interests") String strInterests) {
		
		Vector<String> interests=new Vector<String>();
		if(strInterests!=null && !strInterests.isEmpty()){
			String tmp[]=strInterests.split(";");
			for(int i=0;i<tmp.length;++i)interests.add(tmp[i]);
		}
		
		JSONObject object = new JSONObject();
		if(uType.equals("store")){
			if(Store.checkStore(email, password)>0){
				object.put("Status", "emailAlreadyExists");
				return object.toString();
			}
			
			double lat=0.0,longt=0.0;
			if(latitude!=null && !latitude.isEmpty())lat=Double.parseDouble(latitude);
			if(longitude!=null && !longitude.isEmpty())longt=Double.parseDouble(longitude);
			
			Store store = new Store(null,name,email,password,district,category,description,"",lat,longt,null,null);
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
			User user = new User(null,name,email,password,birthDate,district,gender,twitter,foursquare,"",0,interests);
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
		if(check==1){
			object.put("Status", "OK");
			object.put("type","User");
			object.put("username", User.getUserName(email));
		}
		else if(check==2)
			object.put("Status", "wrongPass");
		else{
			check=Store.checkStore(email, password);
			if(check==1){
				object.put("Status", "OK");
				object.put("type","Store");
				object.put("username", Store.getStoreName(email));
			}
			else if(check==2)
				object.put("Status", "wrongPass");
			else
				object.put("Status", "Failed");
		}
		
		return object.toString();

	}
	
	
	// works for user and store
	@POST
	@Path("/editProfileService")
	public String editProfileService(@FormParam("uType") String uType,
			@FormParam("name") String name, 
			@FormParam("email") String email,
			@FormParam("password") String password,
			@FormParam("category") String category,
			@FormParam("description") String description,
			@FormParam("latitude") String latitude,
			@FormParam("longitude") String longitude,
			@FormParam("birthDate") String birthDate,
			@FormParam("district") String district,
			@FormParam("gender") String gender,
			@FormParam("twitter") String twitter,
			@FormParam("foursquare") String foursquare,
			@FormParam("interests") String strInterests) {
		
		
		JSONObject object = new JSONObject();
		
		if(uType.equals("store")){
			
			double lat=0.0,longt=0.0;
			if(latitude!=null && !latitude.isEmpty())lat=Double.parseDouble(latitude);
			if(longitude!=null && !longitude.isEmpty())longt=Double.parseDouble(longitude);
			
			if(new Store(null,name,email,password,district,category,description,"",lat,longt,null,null).editStore())
				object.put("Status", "OK");
			else
				object.put("Status", "Failed");
			
			return object.toString();
		}
		
		
		if(!User.removeUser(email)){
			object.put("Status", "Failed");
			return object.toString();
		}
		
		Vector<String> interests=new Vector<String>();
		if(strInterests!=null && !strInterests.isEmpty()){
			String tmp[]=strInterests.split(";");
			for(int i=0;i<tmp.length;++i)interests.add(tmp[i]);
		}
		
		User user = new User(null,name,email,password,birthDate,district,gender,twitter,foursquare,"",0,interests);
		if(user.saveUser())
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();

	}
	
	
	@POST
	@Path("/getUserService")
	public String getUserService(@FormParam("email") String email){
		
		
		JSONObject object = new JSONObject();
		
		User user=new User().getUser(email);
		
		if(user!=null){
			object.put("Status", "OK");
			object.put("ID", user.getID());
			object.put("name", user.getName());
			object.put("email", user.getMail());
			object.put("password", user.getPassword());
			object.put("birthDate", user.getBirthDate());
			object.put("district", user.getDistrict());
			object.put("gender", user.getGender());
			object.put("twitter", user.getTwitter());
			object.put("foursquare", user.getFoursquare());
			object.put("date", user.getDate());
			object.put("trust", user.getTrust());
			object.put("interests", user.getParsedInterests());
		}
		else
			object.put("Status", "Failed");
		
		return object.toString();
	}
	
	
	@POST
	@Path("/getAllUsersService")
	public String getAllUsersService(){
		
		JSONArray arr = new JSONArray();
		
		for(User user:User.getAllUsers()){
			JSONObject object = new JSONObject();
			
			if(user!=null){
				object.put("ID", user.getID());
				object.put("name", user.getName());
				object.put("email", user.getMail());
				object.put("password", user.getPassword());
				object.put("birthDate", user.getBirthDate());
				object.put("district", user.getDistrict());
				object.put("gender", user.getGender());
				object.put("twitter", user.getTwitter());
				object.put("foursquare", user.getFoursquare());
				object.put("date", user.getDate());
				object.put("trust", user.getTrust());
				object.put("interests", user.getParsedInterests());
			}
			
			arr.add(object);
		}
		
		return arr.toString();
	}

}