package com.eg.Makany.Models;

import java.util.List;
import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Interest {
	
	private String InterestValue;
	
	public Interest (String InterestValue)
	{
		this.InterestValue = InterestValue;
	}
	
	public String getInterestValue() {
		return InterestValue;
	}

	public void setInterestValue(String InterestValue) {
		this.InterestValue = InterestValue;
	}
	
	public Boolean saveInterest() {
		// should be unique 
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("interest");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity interest = new Entity("interest");

		interest.setProperty("InterestValue", this.InterestValue);
		
		datastore.put(interest);

		return true;

	}
	
	public static Vector<District> getAllDistricts() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Vector<District> myDistricts = new Vector<District>();
		
		Query gaeQuery = new Query("district");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			District temp = new District(entity.getProperty("DistrictName").toString());
			myDistricts.add(temp);
			}
		

		return myDistricts;
	}

}
