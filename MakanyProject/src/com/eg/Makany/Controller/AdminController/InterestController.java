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
public class InterestController {
	
	@GET
	@Path("/AddInterestPage")
	public Response addDistrictView() {
		return Response.ok(new Viewable("/jsp/AdminPanel/AddInterest")).build();
	}
	

	@POST
	@Path("/AddInterest")
	@Produces("text/html")
	public String addInterest(@FormParam("interestValue") String interestValue) {
			
		String serviceUrl = "http://localhost:8889/rest/AddInterestService";

			String urlParameters = "interestValue=" + interestValue;
			
			JSONObject object = Connector.callService(serviceUrl ,urlParameters );
			if (object.get("Status").equals("OK"))
				return "Added successfully";
				
			
			return "Failed";
	
	}
	
	@GET
	@Path("/ShowAllInterests")
	@Produces("text/html")
	public Response getAllInterests(@Context HttpServletRequest request) {
			
		HttpSession session = request.getSession(true);
		String serviceUrl = "http://localhost:8889/rest/ShowAllInterestsService";

			String urlParameters = "";
			
			JSONArray array = Connector.callServiceArray(serviceUrl ,urlParameters);

			session.setAttribute("myInterests", array.toString());
			return Response.ok(new Viewable("/jsp/AdminPanel/ViewInterests")).build();

	}
	
	@POST
	@Path("/EditInterest")
	@Produces("text/html")
	public String editInterest(@FormParam("interestValue") String interestValue, @FormParam("newName") String newName) {
			
		String serviceUrl = "http://localhost:8889/rest/EditInterestService";

			String urlParameters = "interestValue=" + interestValue + "&newName=" + newName;
			
			JSONObject object = Connector.callService(serviceUrl ,urlParameters );
			if (object.get("Status").equals("OK"))
				return "Edited successfully";
				
			
			return "Failed";
	
	}
	
	@POST
	@Path("/DeleteInterest")
	@Produces("text/html")
	public String deleteInterest(@FormParam("interestValue") String interestValue) {
			
		String serviceUrl = "http://localhost:8889/rest/DeleteInterestService";

			String urlParameters = "interestValue=" + interestValue;
			
			JSONObject object = Connector.callService(serviceUrl ,urlParameters );
			if (object.get("Status").equals("OK"))
				return "Deleted successfully";
				
			
			return "Failed";
	
	}
	

}