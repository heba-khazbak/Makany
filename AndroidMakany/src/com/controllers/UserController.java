package com.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;

import android.app.Application;
import android.widget.Toast;


public class UserController 
{
	@POST
	public int login(String username,String password) 
	{
			
			String serviceUrl = "http://localhost:8888/rest/LoginService";

			String urlParameters = "username=" + username + "&password=" + password;
			
			JSONObject object = Connector.callService(serviceUrl ,urlParameters );
			if (object.get("Status").equals("OK"))
			{
				return 1;
			}
				
				return 0;
	
	}




}
