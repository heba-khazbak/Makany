package com.eg.Makany.Services.AdminServices;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.json.simple.JSONObject;
import com.eg.Makany.Models.Admin;


@Path("/")
@Produces("text/html")
public class AdminServices {

	@POST
	@Path("/loginAdmin")
	public String loginAdmin(@FormParam("username") String username, @FormParam("password") String password) {
		JSONObject object = new JSONObject();
		if(Admin.getAdmin(username,password) != null)
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();

	}
	
	@POST
	@Path("/CreateAdmin")
	public String createAdmin(@FormParam("username") String username, @FormParam("password") String password) {
		JSONObject object = new JSONObject();
		Admin admin = new Admin(username,password);
		
		if(admin.saveUser())
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();

	}
	


}