package com.eg.Makany.Models;


import java.util.Set;
import java.util.Vector;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Post {
	private String id;
	private String postType, content, photo, userEmail, district, onEventID;
	private long score;
	private Vector<String> categories;
	private Vector<String> approvals,disapprovals;
	private Vector<Comment> comments;
	private Vector<Report> reports;
	
	public Post() {
		this.id=null;
		this.postType=null;
		this.content = null;
		this.photo=null;
		this.userEmail=null;
		this.district=null;
		this.onEventID=null;
		this.score=0;
		this.categories=new Vector<String>();
		this.comments=new Vector<Comment>();
		this.reports=new Vector<Report>();
		this.approvals=new Vector<String>();
		this.disapprovals=new Vector<String>();
	}

	public Post(String id,String type,String content,String photo,String userEmail,String district,String onEventID,long score,Vector<String> categories) {
		this.id=id;
		this.postType=type;
		this.content = content;
		this.photo=photo;
		this.userEmail=userEmail;
		this.district=district;
		this.onEventID=onEventID;
		this.score=score;
		this.categories=categories;
		this.comments=new Vector<Comment>();
		this.reports=new Vector<Report>();
		this.approvals=new Vector<String>();
		this.disapprovals=new Vector<String>();
	}
	public String getID(){return id;}
	public String getPostType(){return postType;}
	public String getContent(){return content;}
	public String getPhoto(){return photo;}
	public String getUserEmail(){return userEmail;}
	public String getDistrict(){return district;}
	public String getOnEventID(){return onEventID;}
	public long getScore(){return score;}
	public int getNumApprovals(){return approvals.size();}
	public int getNumDisApprovals(){return disapprovals.size();}
	public int getNumReports(){return reports.size();}
	
	public String getParsedCategories(){
		String ret="";
		for(int i=0;i<categories.size();++i){
			if(i>0)ret+=";";
			ret+=categories.get(i);
		}
		return ret;
	}
	public String getParsedApprovals(){
		String ret="";
		for(int i=0;i<approvals.size();++i){
			if(i>0)ret+=";";
			ret+=approvals.get(i);
		}
		return ret;
	}
	public String getParsedDisApprovals(){
		String ret="";
		for(int i=0;i<disapprovals.size();++i){
			if(i>0)ret+=";";
			ret+=disapprovals.get(i);
		}
		return ret;
	}
	public String getParsedReports(){
		String ret="";
		for(int i=0;i<reports.size();++i){
			if(i>0)ret+=";";
			ret+=reports.get(i);
		}
		return ret;
	}


	public boolean savePost() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Entity post = new Entity("posts");
		
		if(this.postType==null)this.postType="";
		if(this.content==null)this.content="";
		if(this.photo==null)this.photo="";
		if(this.userEmail==null)this.userEmail="";
		if(this.district==null)this.district="";
		if(this.onEventID==null)this.onEventID="";
		
		post.setProperty("postType", this.postType);
		post.setProperty("content", this.content);
		post.setProperty("photo", this.photo);
		post.setProperty("userEmail", this.userEmail);
		post.setProperty("district", this.district);
		post.setProperty("onEventID", this.onEventID);
		post.setProperty("score", this.score);
		datastore.put(post);
		
		this.id=String.valueOf(post.getKey().getId());
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
			if(String.valueOf(entity.getKey().getId()).equals(postID)){
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
		
		gaeQuery = new Query("eventPosts");
		pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("postID").toString().equals(postID)){
				keysToDelete.add(entity.getKey());
				break;
			}
			
		}
		
		for(Key k:keysToDelete)
			datastore.delete(k);
		
		return 1;
	}
	
	public static boolean report(String reason,String postID,String userEmail){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Entity rep=new Entity("reports");
		if(reason==null)reason="";
		rep.setProperty("reason", reason);
		rep.setProperty("postID", postID);
		rep.setProperty("userEmail", userEmail);
		datastore.put(rep);
		return true;
	}
	
	public static boolean updateScore(String postID, int upd){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query gaeQuery = new Query("posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		int newUserTrust=0;
		String userEmail=null;
		for(Entity entity:pq.asIterable()){
			if(String.valueOf(entity.getKey().getId()).equals(postID)){
				long cur=Long.parseLong(entity.getProperty("score").toString());
				entity.setProperty("score", cur+upd);
				datastore.put(entity);
				userEmail=entity.getProperty("userEmail").toString();
			}
			if(Long.parseLong(entity.getProperty("score").toString())>0)
				++newUserTrust;
		}
		
		if(userEmail==null)return false;
		
		return User.updateTrust(userEmail, newUserTrust);
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
		
		if(!updateScore(postID, User.getTrust(userEmail)))return 0;
		
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
		
		if(!updateScore(postID, -User.getTrust(userEmail)))return 0;
		
		return 1;
	}
	
	
	public Post getPost(Entity post){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		String postID=String.valueOf(post.getKey().getId());
		
		this.content=post.getProperty("content").toString();
		this.id=postID;
		this.photo=post.getProperty("photo").toString();
		this.postType=post.getProperty("postType").toString();
		this.userEmail=post.getProperty("userEmail").toString();
		this.district=post.getProperty("district").toString();
		this.onEventID=post.getProperty("onEventID").toString();
		this.score=Long.parseLong(post.getProperty("score").toString());
		
		Query gaeQuery = new Query("categories");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("postID").toString().equals(postID))
				this.categories.add(entity.getProperty("name").toString());
			
		}
		
		this.comments=Comment.getFilteredComments(postID,null,null);
		
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
	
	
	public static Vector<Post> getFilteredPosts(String specificUser,
			String specificEvent,
			String specificDistrict,
			Set<String> specificCategories, 
			String postID){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Vector<Post> ret=new Vector<Post>();
		
		Query gaeQuery = new Query("posts");
		long pid=-1;
		if(postID!=null && !postID.isEmpty())pid=Long.parseLong(postID);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getKey().getId()<=pid)
				continue;
			
			if(specificUser!=null && !specificUser.isEmpty() && 
					!entity.getProperty("userEmail").toString().equals(specificUser))
				continue;
			
			if(specificEvent!=null && !specificEvent.isEmpty() && 
					!entity.getProperty("onEventID").toString().equals(specificEvent))
				continue;
			
			if(specificDistrict!=null && !specificDistrict.isEmpty() && 
					!entity.getProperty("district").toString().equals(specificDistrict))
				continue;
			
			Post p=new Post().getPost(entity);
			
			boolean ok=true;
			if(!specificCategories.isEmpty()){
				ok=false;
				for(int i=0;i<p.categories.size();++i)
					if(specificCategories.contains(p.categories.get(i))){
						ok=true;
						break;
					}
			}
			
			if(ok)ret.add(p);
		}
		
		return ret;
	}
	
}
