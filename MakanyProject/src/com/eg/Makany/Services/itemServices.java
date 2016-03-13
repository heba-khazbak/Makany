package com.eg.Makany.Services;

import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.eg.Makany.Models.Item;



@Path("/")
@Produces("text/html")
public class ItemServices {

	
	@POST
	@Path("/loanItemService")
	public String loanItemService(@FormParam("name") String name,
			@FormParam("description") String description, 
			@FormParam("userEmail") String userEmail,
			@FormParam("categories") String strCategories) {
		
		Vector<String> categories=new Vector<String>();
		String tmp[]=strCategories.split("_");
		for(int i=0;i<tmp.length;++i)categories.add(tmp[i]);
		
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
			@FormParam("categories") String strCategories) {
		
		Vector<String> categories=new Vector<String>();
		String tmp[]=strCategories.split("_");
		for(int i=0;i<tmp.length;++i)categories.add(tmp[i]);
		
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
	public String deleteItemService(@FormParam("itemID") String itemID,
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
	
	
	@POST
	@Path("/viewItemService")
	public String deleteItemService(@FormParam("itemID") String itemID) {
		
		JSONObject object = new JSONObject();
		
		Item item=new Item().getItemByID(itemID);
		
		if(item!=null){
			object.put("id", item.getID());
			object.put("name", item.getName());
			object.put("description", item.getDescription());
			object.put("userEmail", item.getUserEmail());
			object.put("categories", item.getParsedCategories());
		}
		
		return object.toString();
		
	}
	
	@POST
	@Path("/getALLLoanItemsService")
	public String getALLLoanItemsService() {
		
		JSONArray arr = new JSONArray();
		
		Vector<Item> items=Item.getAllItems(true);
		
		for(Item item:items){
			JSONObject object = new JSONObject();
			
			if(item!=null){
				object.put("id", item.getID());
				object.put("name", item.getName());
				object.put("description", item.getDescription());
				object.put("userEmail", item.getUserEmail());
				object.put("categories", item.getParsedCategories());
			}
			
			arr.add(object);
		}
		
		return arr.toString();
		
	}
	
	@POST
	@Path("/getALLRequestItemsService")
	public String getALLRequestItemsService() {
		
		JSONArray arr = new JSONArray();
		
		Vector<Item> items=Item.getAllItems(false);
		
		for(Item item:items){
			JSONObject object = new JSONObject();
			
			if(item!=null){
				object.put("id", item.getID());
				object.put("name", item.getName());
				object.put("description", item.getDescription());
				object.put("userEmail", item.getUserEmail());
				object.put("categories", item.getParsedCategories());
			}
			
			arr.add(object);
		}
		
		return arr.toString();
		
	}
	
}
