package com.eg.Makany.Models;

import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Comment {
	private String id,userEmail, username, content, postID;
	public Comment(String id,String userEmail,String username, String content,String postID){
		this.id=id;
		this.userEmail=userEmail;
		this.username=username;
		this.content=content;
		this.postID=postID;
	}
	public String getID(){return id;}
	public String getUserEmail(){return userEmail;}
	public String getUserName(){return username;}
	public String getContent(){return content;}
	public String getPostID(){return postID;}
	
	
	public boolean addComment(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Entity comment=new Entity("comments");
		if(this.content==null)this.content="";
		comment.setProperty("content", this.content);
		comment.setProperty("postID", this.postID);
		comment.setProperty("userEmail", this.userEmail);
		datastore.put(comment);
		return true;
	}
	
	public static Vector<Comment> getFilteredComments(String specificPostID,
			String specificUser,String commID){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Vector<Comment> ret=new Vector<Comment>();
		Query gaeQuery = new Query("comments");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		long cid=-1;
		if(commID!=null && !commID.isEmpty())cid=Long.parseLong(commID);
		for(Entity entity:pq.asIterable()){
			if(entity.getKey().getId()<=cid)
				continue;
			
			if(specificPostID!=null && !specificPostID.isEmpty() 
					&& !specificPostID.equals(entity.getProperty("postID").toString()))
				continue;
			
			String mail=entity.getProperty("userEmail").toString();
			
			if(specificUser!=null && !specificUser.isEmpty() 
					&& !specificUser.equals(mail))
				continue;
			
			
			ret.add(new Comment(String.valueOf(entity.getKey().getId()),
					mail,User.getUserName(mail),
					entity.getProperty("content").toString(),
					entity.getProperty("postID").toString()));
			
		}
		return ret;
	}
}
