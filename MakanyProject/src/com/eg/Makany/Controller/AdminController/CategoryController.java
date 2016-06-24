package com.eg.Makany.Controller.AdminController;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.eg.Makany.Controller.Connector;


@Path("/")
@Produces("text/html")
public class CategoryController {
	

	

	@POST
	@Path("/AddCategory")
	@Produces("text/html")
	public String addCategory(@FormParam("categoryValue") String interestValue) {
			
		String serviceUrl = "http://makanyapp2.appspot.com/rest/AddCategoryService";

			String urlParameters = "categoryValue=" + interestValue;
			
			JSONObject object = Connector.callService(serviceUrl ,urlParameters );
			if (object.get("Status").equals("OK"))
				return "Added successfully";
				
			
			return "Failed";
	
	}
	
	@GET
	@Path("/ShowAllCategories")
	@Produces("text/html")
	public Response getAllCategories(@Context HttpServletRequest request) {
			
		HttpSession session = request.getSession(true);
		String serviceUrl = "http://makanyapp2.appspot.com/rest/ShowAllCategoryService";

			String urlParameters = "";
			
			JSONArray array = Connector.callServiceArray(serviceUrl ,urlParameters);

			session.setAttribute("myCategories", array.toString());
			return Response.ok(new Viewable("/jsp/AdminPanel/ViewCategories")).build();

	}
	
	@POST
	@Path("/EditCategory")
	@Produces("text/html")
	public String editCategory(@FormParam("categoryValue") String interestValue, @FormParam("newName") String newName) {
			
		String serviceUrl = "http://makanyapp2.appspot.com/rest/EditCategoryService";

			String urlParameters = "categoryValue=" + interestValue + "&newName=" + newName;
			
			JSONObject object = Connector.callService(serviceUrl ,urlParameters );
			if (object.get("Status").equals("OK"))
				return "Edited successfully";
				
			
			return "Failed";
	
	}
	
	@POST
	@Path("/DeleteCategory")
	@Produces("text/html")
	public String deleteCategory(@FormParam("categoryValue") String interestValue) {
			
		String serviceUrl = "http://makanyapp2.appspot.com/rest/DeleteCategoryService";

			String urlParameters = "categoryValue=" + interestValue;
			
			JSONObject object = Connector.callService(serviceUrl ,urlParameters );
			if (object.get("Status").equals("OK"))
				return "Deleted successfully";
				
			
			return "Failed";
	
	}
	

}