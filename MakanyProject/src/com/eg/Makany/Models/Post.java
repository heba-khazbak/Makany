package com.eg.Makany.Models;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Post {
	private String id;
	private String type, content, photo, userEmail;
	private Vector<String> categories;
	private Vector<String> approvals,disapprovals;
	private Vector<Comment> comments;
	private Vector<Report> reports;
	
	public Post() {
		this.id=null;
		this.type=null;
		this.content = null;
		this.photo=null;
		this.userEmail=null;
		this.categories=new Vector<String>();
		this.comments=new Vector<Comment>();
		this.reports=new Vector<Report>();
		this.approvals=new Vector<String>();
		this.disapprovals=new Vector<String>();
	}

	public Post(String id,String type,String content,String photo,String userEmail,Vector<String> categories) {
		this.id=id;
		this.type=type;
		this.content = content;
		this.photo=photo;
		this.userEmail=userEmail;
		this.categories=categories;
		this.comments=new Vector<Comment>();
		this.reports=new Vector<Report>();
		this.approvals=new Vector<String>();
		this.disapprovals=new Vector<String>();
	}


	public boolean savePost() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Entity post = new Entity("posts");
		
		post.setProperty("type", this.type);
		post.setProperty("content", this.content);
		post.setProperty("photo", this.photo);
		post.setProperty("userEmail", this.userEmail);
		datastore.put(post);
		
		this.id=post.getKey().toString();
		for(String str:this.categories){
			Entity category=new Entity("categories");
			category.setProperty("postID", this.id);
			category.setProperty("name", str);
			datastore.put(category);
		}

		return true;
	}
	
	public static int deletePost(String postID,String userEmail){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Vector<Key> keysToDelete=new Vector<Key>();
		
		Query gaeQuery = new Query("posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getKey().toString().equals(postID)){
				if(!entity.getProperty("userEmail").toString().equals(userEmail))
					return 2;
				
				keysToDelete.add(entity.getKey());
				break;
			}
			
		}

		
		gaeQuery = new Query("categories");
		pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("postID").toString().equals(postID))
				keysToDelete.add(entity.getKey());
			
		}
		
		gaeQuery = new Query("comments");
		pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("postID").toString().equals(postID))
				keysToDelete.add(entity.getKey());
			
		}
		
		gaeQuery = new Query("reports");
		pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("postID").toString().equals(postID))
				keysToDelete.add(entity.getKey());
			
		}
		
		gaeQuery = new Query("approvals");
		pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("postID").toString().equals(postID))
				keysToDelete.add(entity.getKey());
			
		}
		
		gaeQuery = new Query("disapprovals");
		pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("postID").toString().equals(postID))
				keysToDelete.add(entity.getKey());
			
		}
		
		for(Key k:keysToDelete)
			datastore.delete(k);
		
		return 1;
	}
	
	public static boolean addComment(String content,String postID,String userEmail){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Entity comment=new Entity("comments");
		comment.setProperty("content", content);
		comment.setProperty("postID", postID);
		comment.setProperty("userEmail", userEmail);
		datastore.put(comment);
		return true;
	}
	
	public static boolean report(String reason,String postID,String userEmail){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Entity comment=new Entity("reports");
		comment.setProperty("reason", reason);
		comment.setProperty("postID", postID);
		comment.setProperty("userEmail", userEmail);
		datastore.put(comment);
		return true;
	}
	
	public static int approve(String postID,String userEmail){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query gaeQuery = new Query("approvals");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("postID").toString().equals(postID)
					&& entity.getProperty("userEmail").toString().equals(userEmail)){
				
				return 2;
			}
			
		}
		
		Entity approval=new Entity("approvals");
		
		
		approval.setProperty("postID", postID);
		approval.setProperty("userEmail", userEmail);
		datastore.put(approval);
		return 1;
	}
	
	
	public static int disapprove(String postID,String userEmail){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query gaeQuery = new Query("disapprovals");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("postID").toString().equals(postID)
					&& entity.getProperty("userEmail").toString().equals(userEmail)){
				
				return 2;
			}
			
		}
		
		Entity disapproval=new Entity("disapprovals");
		
		
		disapproval.setProperty("postID", postID);
		disapproval.setProperty("userEmail", userEmail);
		datastore.put(disapproval);
		return 1;
	}
	
	
	public Post getPost(Entity post){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		String postID=post.getKey().toString();
		
		this.content=post.getProperty("content").toString();
		this.id=postID;
		this.photo=post.getProperty("photo").toString();
		this.type=post.getProperty("type").toString();
		this.userEmail=post.getProperty("userEmail").toString();
		
		Query gaeQuery = new Query("categories");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("postID").toString().equals(postID))
				this.categories.add(entity.getProperty("name").toString());
			
		}
		
		gaeQuery = new Query("comments");
		pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("postID").toString().equals(postID)){
				String mail=entity.getProperty("userEmail").toString();
				this.comments.add(new Comment(mail,User.getUserName(mail),
						entity.getProperty("content").toString()));
			}
			
		}
		
		gaeQuery = new Query("reports");
		pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("postID").toString().equals(postID))
				this.reports.add(new Report(entity.getProperty("userEmail").toString(),
						entity.getProperty("reason").toString()));
			
		}
		
		gaeQuery = new Query("approvals");
		pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("postID").toString().equals(postID))
				this.approvals.add(entity.getProperty("userEmail").toString());
			
		}
		
		gaeQuery = new Query("disapprovals");
		pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("postID").toString().equals(postID))
				this.disapprovals.add(entity.getProperty("userEmail").toString());
			
		}
		
		return this;
	}
	
	
	public Vector<Post> getAllPosts(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Vector<Post> ret=new Vector<Post>();
		
		Query gaeQuery = new Query("posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			ret.add(new Post().getPost(entity));
		}
		
		return ret;
	}
	
}
