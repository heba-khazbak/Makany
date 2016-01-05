package com.eg.Makany.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.eg.Makany.Models.Post;

@Path("/")
@Produces("text/html")
public class Service {

	@POST
	@Path("/createPostService")
	public String createPostService(@FormParam("content") String content) {
		JSONObject object = new JSONObject();
		Post post = new Post(content);
		if(post.savePost())
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();

	}

}