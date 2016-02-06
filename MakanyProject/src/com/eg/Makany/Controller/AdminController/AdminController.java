package com.eg.Makany.Controller.AdminController;


import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;


import com.eg.Makany.Controller.Connector;




@Path("/")
@Produces("text/html")
public class AdminController {
	
	@GET
	@Path("/LoginPage")
	public Response createPost() {
		return Response.ok(new Viewable("/jsp/AdminPanel/Login")).build();
	}

	


	@POST
	@Path("/login")
	@Produces("text/html")
	public Response response(@FormParam("username") String username, @FormParam("password") String password) {
			
		String serviceUrl = "http://localhost:8889/rest/loginAdmin";

			String urlParameters = "username=" + username + "&password=" + password;
			
			JSONObject object = Connector.callService(serviceUrl ,urlParameters );
			if (object.get("Status").equals("OK"))
				return Response.ok(new Viewable("/jsp/AdminPanel/AdminHomePage")).build();
			
			return null;
	
	}

}