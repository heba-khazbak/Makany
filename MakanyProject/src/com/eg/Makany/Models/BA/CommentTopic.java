package com.eg.Makany.Models.BA;

import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class CommentTopic {
	

	private static final String TABLENAME = "CommentTopics";
	private String userEmail;
	private String postID;
	private String commentID;
	private String topic;
	private double score;
	
	public CommentTopic(String userEmail , String postID, String commentID, String topic, double score) {
		this.userEmail = userEmail;
		this.postID = postID;
		this.commentID = commentID;
		this.topic = topic;
		this.score = score;
		
	}
	
	public CommentTopic() {
		// TODO Auto-generated constructor stub
	}

	public boolean saveCommentTopic(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Entity topic=new Entity(TABLENAME);
		topic.setProperty("userEmail", this.userEmail);
		topic.setProperty("postID", this.postID);
		topic.setProperty("commentID", this.commentID);
		topic.setProperty("topic", this.topic);
		topic.setProperty("score", this.score);

		datastore.put(topic);
		return true;
	}
	
	public static Vector<CommentTopic> getCommentsTopics(String userEmail){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Vector<CommentTopic> ret=new Vector<CommentTopic>();
		Query gaeQuery = new Query("TABLENAME");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("userEmail").toString().equals(userEmail)){
				CommentTopic temp = new CommentTopic();
				temp.userEmail = entity.getProperty("userEmail").toString();
				temp.postID = entity.getProperty("postID").toString();
				temp.commentID = entity.getProperty("commentID").toString();
				temp.topic = entity.getProperty("topic").toString();
				temp.score = (double) entity.getProperty("score");
				
				ret.add(temp);
			}
			
		}
		return ret;
	}
	
	public static long getMaxCommentID (String email)
	{
		Vector<CommentTopic> myComments = getCommentsTopics(email);
		if (myComments == null)
			return 0;
		
		long max = 0;
		for (CommentTopic T : myComments)
		{
			if (Long.parseLong(T.commentID) > max)
				max = Long.parseLong(T.commentID);
		}
		
		return max;
	}
}
