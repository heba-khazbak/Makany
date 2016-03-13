package com.eg.Makany.Services;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.eg.Makany.Models.Comment;
import com.eg.Makany.Models.Post;



@Path("/")
@Produces("text/html")
public class PostServices {
	
	
	@POST
	@Path("/addPostService")
	public String addPostService(@FormParam("postType") String postType,
			@FormParam("content") String content, 
			@FormParam("photo") String photo,
			@FormParam("district") String district,
			@FormParam("onEventID") String onEventID,
			@FormParam("userEmail") String userEmail,
			@FormParam("categories") String strCategories) {
		
		Vector<String> categories=new Vector<String>();
		String tmp[]=strCategories.split("_");
		for(int i=0;i<tmp.length;++i)categories.add(tmp[i]);
		
		JSONObject object = new JSONObject();
		
		Post post=new Post(null,postType,content,photo,userEmail,district,onEventID,categories);
		
		if(post.savePost())
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();
		
	}
	
	@POST
	@Path("/deletePostService")
	public String deletePostService(@FormParam("postID") String postID,
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
			@FormParam("postID") String postID,
			@FormParam("userEmail") String userEmail) {
		
		JSONObject object = new JSONObject();
		
		
		if(new Comment(null,userEmail,null,content,postID).addComment())
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();
		
	}
	
	@POST
	@Path("/reportPostService")
	public String reportPostService(@FormParam("reason") String reason, 
			@FormParam("postID") String postID,
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
	public String approvePostService(@FormParam("postID") String postID,
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
	public String disapprovePostService(@FormParam("postID") String postID,
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
	
	
	@POST
	@Path("/getFilteredPostsService")
	public String getFilteredPostsService(@FormParam("category") String strCategories,
			@FormParam("district") String district,
			@FormParam("onEventID") String onEventID){
		
		Set<String> categories=new HashSet<String>();
		if(strCategories!=null){
			String tmp[]=strCategories.split("_");
			for(int i=0;i<tmp.length;++i)categories.add(tmp[i]);
		}
		
		JSONArray arr = new JSONArray();
		
		Vector<Post> posts=Post.getFilteredPosts(onEventID, district, categories);
		
		for(Post post:posts){
			JSONObject object = new JSONObject();
			
			if(post!=null){
				object.put("ID", post.getID());
				object.put("postType", post.getPostType());
				object.put("content", post.getContent());
				object.put("photo", post.getPhoto());
				object.put("userEmail", post.getUserEmail());
				object.put("district", post.getDistrict());
				
				object.put("numApprovals", String.valueOf(post.getNumApprovals()));
				object.put("approvalMails", post.getParsedApprovals());
				
				object.put("numDisApprovals", String.valueOf(post.getNumDisApprovals()));
				object.put("disapprovalMails", post.getParsedDisApprovals());
				
				object.put("numReports", String.valueOf(post.getNumReports()));
				object.put("reportMails", post.getParsedReports());
			}
			
			arr.add(object);
		}
		
		return arr.toString();
	}
	
	
	@POST
	@Path("/getPostCommentsService")
	public String getStoreReviewsService(@FormParam("postID") String postID){
		
		JSONArray arr = new JSONArray();
		
		Vector<Comment> comments=Comment.getComments(postID);
		
		for(Comment comment:comments){
			JSONObject object = new JSONObject();
			
			if(comment!=null){
				object.put("ID", comment.getID());
				object.put("userEmail", comment.getUserEmail());
				object.put("username", comment.getUserName());
				object.put("content", comment.getContent());
			}
			
			arr.add(object);
		}
		
		return arr.toString();
	}
}
