package com.eg.Makany.Controller.behaviouralAnalysis;



import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.eg.Makany.Controller.Connector;




@Path("/")
@Produces("text/html")
public class CommunityController {
	

	
	@GET
	@Path("/analyzePosts")
	@Produces("text/html")
	public String analyzePosts() {
			
			String serviceUrl = "http://makanyapp2.appspot.com/rest/AnalyzePosts";

			String urlParameters = "";
			
			JSONObject object = Connector.callService(serviceUrl ,urlParameters);
			
			
			return object.toString();
	
	}
	
	@GET
	@Path("/analyzePlaces")
	@Produces("text/html")
	public String analyzePlaces() {
			
			String serviceUrl = "http://makanyapp2.appspot.com/rest/AnalyzePlaces";

			String urlParameters = "";
			
			JSONObject object = Connector.callService(serviceUrl ,urlParameters);
			
			
			return object.toString();
	
	}
	
	@GET
	@Path("/analyzeEvents")
	@Produces("text/html")
	public String analyzeEvents() {
			
			String serviceUrl = "http://makanyapp2.appspot.com/rest/AnalyzeEvents";

			String urlParameters = "";
			
			JSONObject object = Connector.callService(serviceUrl ,urlParameters);
			
			
			return object.toString();
	
	}
	
	
}