package com.eg.Makany.Services;

import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.simple.JSONObject;

import com.eg.Makany.Models.Post;
import com.google.appengine.api.datastore.Key;


@Path("/")
@Produces("text/html")
public class PostServices {
	
	
	@POST
	@Path("/addPostService")
	public String addPostService(@FormParam("type") String type,
			@FormParam("content") String content, 
			@FormParam("photo") String photo,
			@FormParam("userEmail") String userEmail,
			@FormParam("categories") Vector<String> categories) {
		
		JSONObject object = new JSONObject();
		
		Post post=new Post(null,type,content,photo,userEmail,categories);
		
		if(post.savePost())
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();
		
	}
	
	@POST
	@Path("/deletePostService")
	public String deletePostService(@FormParam("postID") Key postID,
			@FormParam("userEmail") String userEmail) {
		
		JSONObject object = new JSONObject();

		int res=Post.deletePost(postID, userEmail);
		
		if(res==1)
			object.put("Status", "OK");
		else if(res==2)
			object.put("Status","notYourPost");
		else
			object.put("Status", "Failed");
		
		return object.toString();
		
	}
	
	
	@POST
	@Path("/addCommentService")
	public String addCommentService(@FormParam("content") String content, 
			@FormParam("postID") Key postID,
			@FormParam("userEmail") String userEmail) {
		
		JSONObject object = new JSONObject();
		
		
		if(Post.addComment(content, postID, userEmail))
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();
		
	}
	
	@POST
	@Path("/reportPostService")
	public String reportPostService(@FormParam("reason") String reason, 
			@FormParam("postID") Key postID,
			@FormParam("userEmail") String userEmail) {
		
		JSONObject object = new JSONObject();
		
		
		if(Post.report(reason, postID, userEmail))
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();
		
	}
	
	@POST
	@Path("/approvePostService")
	public String approvePostService(@FormParam("postID") Key postID,
			@FormParam("userEmail") String userEmail) {
		
		JSONObject object = new JSONObject();
		
		int res=Post.approve(postID, userEmail);
		
		if(res==1)
			object.put("Status", "OK");
		else if(res==2)
			object.put("Status", "alreadyApproved");
		else
			object.put("Status", "Failed");
		
		return object.toString();
		
	}
	
	
	@POST
	@Path("/disapprovePostService")
	public String disapprovePostService(@FormParam("postID") Key postID,
			@FormParam("userEmail") String userEmail) {
		
		JSONObject object = new JSONObject();
		
		int res=Post.disapprove(postID, userEmail);
		
		if(res==1)
			object.put("Status", "OK");
		else if(res==2)
			object.put("Status", "alreadyDisapproved");
		else
			object.put("Status", "Failed");
		
		return object.toString();
		
	}
}
