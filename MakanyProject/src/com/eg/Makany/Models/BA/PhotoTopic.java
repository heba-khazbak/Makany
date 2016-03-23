package com.eg.Makany.Models.BA;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;


public class PhotoTopic {
	

	private static final String TABLENAME = "PhotoTopics";
	private String userEmail;
	private String postID;
	private String topic;
	private double score;
	
	public PhotoTopic(String userEmail , String postID, String topic, double score) {
		this.userEmail = userEmail;
		this.postID = postID;
		this.topic = topic;
		this.score = score;
		
	}
	
	public PhotoTopic() {
		// TODO Auto-generated constructor stub
	}

	public boolean savePhotoTopic(){
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
	
	
}
