package com.eg.Makany.Controller.behaviouralAnalysis;



import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.json.simple.JSONArray;
import com.eg.Makany.Controller.Connector;




@Path("/")
@Produces("text/html")
public class TwitterController {
	

	
	@GET
	@Path("/analyzeTwitter")
	@Produces("text/html")
	public String analyzeTwitter() {
			
			String serviceUrl = "http://localhost:8889/rest/AnalyzeTwitter";

			String urlParameters = "";
			
			JSONArray array = Connector.callServiceArray(serviceUrl ,urlParameters);
			
			
			return array.toString();
	
	}
	
	
}