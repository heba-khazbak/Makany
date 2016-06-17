package com.eg.Makany.Models;

import java.util.Date;
import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class StoreReview {
	String id, reviewerMail, storeMail, review, date;
	int rating;
	
	public StoreReview(String id, String reviewerMail, String storeMail, 
			String review,String date,int rating){
		this.id=id;
		this.reviewerMail=reviewerMail;
		this.storeMail=storeMail;
		this.review=review;
		this.date=date;
		this.rating=rating;
	}
	
	public String getID(){return id;}
	public String getReviewerMail(){return reviewerMail;}
	public String getStoreMail(){return storeMail;}
	public String getReview(){return review;}
	public String getDate(){return date;}
	public int getRating(){return rating;}
	
	public boolean saveReview(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity sreview = new Entity("storeReviews");

		sreview.setProperty("reviewerMail", this.reviewerMail);
		sreview.setProperty("storeMail", this.storeMail);
		sreview.setProperty("review", this.review);
		sreview.setProperty("date", new Date());
		sreview.setProperty("rating", this.rating);
		datastore.put(sreview);

		return true;
	}
	
	public static Vector<StoreReview> getReviews(String email){
		Vector<StoreReview> ret=new Vector<StoreReview>();
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("storeReviews");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("storeMail").toString().equals(email))
				ret.add(new StoreReview(String.valueOf(entity.getKey().getId()),
						entity.getProperty("reviewerMail").toString(),
						entity.getProperty("storeMail").toString(),
						entity.getProperty("review").toString(),
						entity.getProperty("date").toString(),
						Integer.parseInt(entity.getProperty("rating").toString())));
			
		}
		return ret;
	}
	
	public static Vector<StoreReview> getGoodReviewsByUser(String userEmail){
		Vector<StoreReview> ret=new Vector<StoreReview>();
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("storeReviews");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		

		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("reviewerMail").toString().equals(userEmail)
					&& Integer.parseInt(entity.getProperty("rating").toString())>=4)
				ret.add(new StoreReview(String.valueOf(entity.getKey().getId()),
						entity.getProperty("reviewerMail").toString(),
						entity.getProperty("storeMail").toString(),
						entity.getProperty("review").toString(),
						entity.getProperty("date").toString(),
						Integer.parseInt(entity.getProperty("rating").toString())));
			
		}
		return ret;
	}
}
