package com.eg.Makany.Services;

import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.simple.JSONObject;

import com.eg.Makany.Models.Item;
import com.eg.Makany.Models.Post;
import com.google.appengine.api.datastore.Key;


@Path("/")
@Produces("text/html")
public class itemServices {

	
	@POST
	@Path("/loanItemService")
	public String loanItemService(@FormParam("name") String name,
			@FormParam("description") String description, 
			@FormParam("userEmail") String userEmail,
			@FormParam("categories") Vector<String> categories) {
		
		JSONObject object = new JSONObject();
		
		Item item=new Item(null,name,description,userEmail,categories);
		
		if(item.saveItem(true))
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();
		
	}
	
	@POST
	@Path("/requestItemService")
	public String requestItemService(@FormParam("name") String name,
			@FormParam("description") String description, 
			@FormParam("userEmail") String userEmail,
			@FormParam("categories") Vector<String> categories) {
		
		JSONObject object = new JSONObject();
		
		Item item=new Item(null,name,description,userEmail,categories);
		
		if(item.saveItem(false))
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();
		
	}
	
	@POST
	@Path("/deleteItemService")
	public String deleteItemService(@FormParam("itemID") Key itemID,
			@FormParam("userEmail") String userEmail) {
		
		JSONObject object = new JSONObject();
		
		int res=Item.deleteItem(itemID, userEmail);
		
		if(res==1)
			object.put("Status", "OK");
		else if(res==2)
			object.put("Status","notYourItem");
		else
			object.put("Status", "Failed");
		
		return object.toString();
		
	}
	
}
