package com.eg.Makany.Services.AdminServices;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.eg.Makany.Models.Category;


@Path("/")
@Produces("text/html")
public class CategoryServices {


	@POST
	@Path("/AddCategoryService")
	public String AddInterest(@FormParam("categoryValue") String categoryValue) {
		JSONObject object = new JSONObject();
		Category category = new Category(categoryValue);
		
		if(category.saveCategory())
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();

	}
	
	@POST
	@Path("/ShowAllCategoryService")
	public String ShowAllInterests() {
		JSONArray CategoryArray = new JSONArray();
		
		for (Category I : Category.getAllCategories() )
		{
			JSONObject object = new JSONObject();
			object.put("categoryValue",I.getCategoryValue());
			CategoryArray.add(object);
		}
		
		
		return CategoryArray.toString();

	}
	
	@POST
	@Path("/EditCategoryService")
	public String editInterest(@FormParam("categoryValue") String categoryValue , @FormParam("newName") String newName) {
		JSONObject object = new JSONObject();
		
		if(Category.editCategory(categoryValue, newName))
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();

	}
	
	@POST
	@Path("/DeleteCategoryService")
	public String deleteInterest(@FormParam("categoryValue") String categoryValue) {
		JSONObject object = new JSONObject();
		
		if(Category.deleteCategory(categoryValue))
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();

	}

}