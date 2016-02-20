package com.eg.Makany.Models;

import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Interest {
	private static final String TABLENAME = "interest";
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
		Query gaeQuery = new Query(TABLENAME);
		PreparedQuery pq = datastore.prepare(gaeQuery);

		Entity interest = new Entity(TABLENAME);

		interest.setProperty("InterestValue", this.InterestValue);
		
		datastore.put(interest);

		return true;

	}
	
	public static Vector<Interest> getAllInterests() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Vector<Interest> myInterests = new Vector<Interest>();
		
		Query gaeQuery = new Query(TABLENAME);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			Interest temp = new Interest(entity.getProperty("InterestValue").toString());
			myInterests.add(temp);
			}
		

		return myInterests;
	}
	

	public static boolean editInterest(String name, String newName) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query gaeQuery = new Query(TABLENAME);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("InterestValue").toString().equals(name))
			{
				entity.setProperty("InterestValue", newName);
				datastore.put(entity);
				return true;
			}
			}
		

		return false;
	}
	
	public static boolean deleteInterest(String name) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query gaeQuery = new Query(TABLENAME);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("InterestValue").toString().equals(name))
			{
				datastore.delete(entity.getKey());
				return true;
			}
			}
		

		return false;
	}
	
	public static Vector<Interest> parseFromJson(String json) {
		
		Vector<Interest> myInterests = new Vector<Interest>();
		JSONParser parser = new JSONParser();
		
		JSONArray array = null;
		try {
			array = (JSONArray) parser.parse(json);
			for (int i = 0 ; i < array.size() ; i++)
			{
				JSONObject obj = (JSONObject)array.get(i);
				Interest D = new Interest (obj.get("InterestValue").toString());
				myInterests.add(D);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return myInterests;

	}

}
