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
import org.json.simple.JSONObject;

import com.eg.Makany.Controller.Connector;




@Path("/")
@Produces("text/html")
public class AdminController {
	

	@GET
	@Path("/LoginPage")
	public Response login() {
		return Response.ok(new Viewable("/jsp/AdminPanel/Login")).build();
	}
	
	@GET
	@Path("/CreateAdminPage")
	public Response signup() {
		return Response.ok(new Viewable("/jsp/AdminPanel/CreateAdmin")).build();
	}
	
	@GET
	@Path("/HomePage")
	public Response homePage() {
		return Response.ok(new Viewable("/jsp/AdminPanel/AdminHomePage")).build();
	}

	


	@POST
	@Path("/login")
	@Produces("text/html")
	public Response login(@Context HttpServletRequest request,@FormParam("username") String username,
			@FormParam("password") String password) {
			
			String serviceUrl = "http://localhost:8889/rest/loginAdmin";

			String urlParameters = "username=" + username + "&password=" + password;
			
			JSONObject object = Connector.callService(serviceUrl ,urlParameters );
			if (object.get("Status").equals("OK"))
			{
				HttpSession session = request.getSession(true);
				
				session.setAttribute("username", username);
				return Response.ok(new Viewable("/jsp/AdminPanel/AdminHomePage")).build();
			}
				
			
			return null;
	
	}
	
	@POST
	@Path("/CreateAdmin")
	@Produces("text/html")
	public String CreateAdmin(@FormParam("username") String username, @FormParam("password") String password) {
			
		String serviceUrl = "http://localhost:8889/rest/CreateAdmin";

			String urlParameters = "username=" + username + "&password=" + password;
			
			JSONObject object = Connector.callService(serviceUrl ,urlParameters );
			if (object.get("Status").equals("OK"))
				return "Registered Successfully";
			
			return "Failed";
	
	}

}