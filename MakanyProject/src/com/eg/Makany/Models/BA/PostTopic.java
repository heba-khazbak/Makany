package com.eg.Makany.Models.BA;

import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class PostTopic {

	private static final String TABLENAME = "PostTopics";
	private String userEmail;
	private String postID;
	private String topic;
	private double score;
	
	public PostTopic(String userEmail , String postID, String topic, double score) {
		this.userEmail = userEmail;
		this.postID = postID;
		this.topic = topic;
		this.score = score;
	}
	
	public PostTopic() {
		// TODO Auto-generated constructor stub
	}

	public boolean savePostTopic(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Entity topic=new Entity(TABLENAME);
		topic.setProperty("userEmail", this.userEmail);
		topic.setProperty("postID", this.postID);
		topic.setProperty("topic", this.topic);
		topic.setProperty("score", this.score);
		
		datastore.put(topic);
		return true;
	}
	
	public static Vector<PostTopic> getPostsTopics(String userEmail){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Vector<PostTopic> ret=new Vector<PostTopic>();
		Query gaeQuery = new Query("TABLENAME");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("userEmail").toString().equals(userEmail)){
				PostTopic temp = new PostTopic();
				temp.userEmail = entity.getProperty("userEmail").toString();
				temp.topic = entity.getProperty("topic").toString();
				temp.score = (double) entity.getProperty("score");
				temp.postID = entity.getProperty("postID").toString();
				ret.add(temp);
			}
			
		}
		return ret;
	}
	
	public static long getMaxPostID (String email)
	{
		Vector<PostTopic> myPosts = getPostsTopics(email);
		if (myPosts == null)
			return 0;
		
		long max = 0;
		for (PostTopic T : myPosts)
		{
			if (Long.parseLong(T.postID) > max)
				max = Long.parseLong(T.postID);
		}
		
		return max;
	}
}
