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
public class DistrictController {
	
	@GET
	@Path("/AddDistrictPage")
	public Response addDistrictView() {
		return Response.ok(new Viewable("/jsp/AdminPanel/AddDistrict")).build();
	}
	

	@POST
	@Path("/AddDistrict")
	@Produces("text/html")
	public String addDistrict(@FormParam("districtName") String districtName,
			@FormParam("latitude") String latitude,@FormParam("longitude") String longitude) {
			
		String serviceUrl = "http://localhost:8889/rest/AddDistrictService";

			String urlParameters = "districtName=" + districtName + "&latitude=" + latitude
					+ "&longitude=" + longitude;
			
			JSONObject object = Connector.callService(serviceUrl ,urlParameters );
			if (object.get("Status").equals("OK"))
				return "Added successfully";
				
			
			return "Failed";
	
	}
	
	@GET
	@Path("/ShowAllDistricts")
	@Produces("text/html")
	public Response getAllDistricts(@Context HttpServletRequest request) {
			
		HttpSession session = request.getSession(true);
		String serviceUrl = "http://localhost:8889/rest/ShowAllDistrictsService";

			String urlParameters = "";
			
			JSONArray array = Connector.callServiceArray(serviceUrl ,urlParameters);

			session.setAttribute("myDistricts", array.toString());
			session.setAttribute("latitude", array.toString());
			session.setAttribute("longitude", array.toString());
			return Response.ok(new Viewable("/jsp/AdminPanel/ViewDistricts")).build();

	}
	
	@POST
	@Path("/EditDistrict")
	@Produces("text/html")
	public String editDistrict(@FormParam("districtName") String districtName, @FormParam("newName") String newName,
			@FormParam("latitude") String latitude,@FormParam("longitude") String longitude) {
			
		String serviceUrl = "http://localhost:8889/rest/EditDistrictService";

			String urlParameters = "districtName=" + districtName + "&newName=" + newName
					+ "&latitude=" + latitude + "&longitude=" + longitude;
			
			JSONObject object = Connector.callService(serviceUrl ,urlParameters );
			if (object.get("Status").equals("OK"))
				return "Edited successfully";
				
			
			return "Failed";
	
	}
	
	@POST
	@Path("/DeleteDistrict")
	@Produces("text/html")
	public String deleteDistrict(@FormParam("districtName") String districtName) {
			
		String serviceUrl = "http://localhost:8889/rest/DeleteDistrictService";

			String urlParameters = "districtName=" + districtName;
			
			JSONObject object = Connector.callService(serviceUrl ,urlParameters );
			if (object.get("Status").equals("OK"))
				return "Deleted successfully";
				
			
			return "Failed";
	
	}
	

}