package com.eg.Makany.Models;

import java.util.Date;
import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Review {
	private String id, reviewerMail, reviewedID, review, date;
	private int rating;
	
	public Review(String id, String reviewerMail, String reviewedID, 
			String review,String date,int rating){
		this.id=id;
		this.reviewerMail=reviewerMail;
		this.reviewedID=reviewedID;
		this.review=review;
		this.date=date;
		this.rating=rating;
	}
	
	public String getID(){return id;}
	public String getReviewerMail(){return reviewerMail;}
	public String getReviewedID(){return reviewedID;}
	public String getReview(){return review;}
	public String getDate(){return date;}
	public int getRating(){return rating;}
	
	public boolean saveReview(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity sreview = new Entity("reviews");
		
		if(this.reviewerMail==null)this.reviewerMail="";
		if(this.reviewedID==null)this.reviewedID="";
		if(this.review==null)this.review="";

		sreview.setProperty("reviewerMail", this.reviewerMail);
		sreview.setProperty("reviewedID", this.reviewedID);
		sreview.setProperty("review", this.review);
		sreview.setProperty("date", new Date());
		sreview.setProperty("rating", this.rating);
		datastore.put(sreview);

		return true;
	}
	
	public static Vector<Review> getReviews(String ID){
		Vector<Review> ret=new Vector<Review>();
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("reviews");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("reviewedID").toString().equals(ID))
				ret.add(new Review(String.valueOf(entity.getKey().getId()),
						entity.getProperty("reviewerMail").toString(),
						entity.getProperty("storeMail").toString(),
						entity.getProperty("review").toString(),
						entity.getProperty("date").toString(),
						Integer.parseInt(entity.getProperty("rating").toString())));
			
		}
		return ret;
	}
}
