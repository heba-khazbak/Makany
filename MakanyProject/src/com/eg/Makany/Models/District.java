package com.eg.Makany.Models;

import java.util.List;
import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class District {
	private static final String TABLENAME = "district";
	private String DistrictName;
	
	public District (String DistrictName)
	{
		this.DistrictName = DistrictName;
	}
	
	public String getDistrictName() {
		return DistrictName;
	}

	public void setDistrictName(String districtName) {
		DistrictName = districtName;
	}
	
	public Boolean saveDistrict() {
		// should be unique 
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query(TABLENAME);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity district = new Entity(TABLENAME);

		district.setProperty("DistrictName", this.DistrictName);
		
		datastore.put(district);

		return true;

	}
	
	public static Vector<District> getAllDistricts() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Vector<District> myDistricts = new Vector<District>();
		
		Query gaeQuery = new Query(TABLENAME);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			District temp = new District(entity.getProperty("DistrictName").toString());
			myDistricts.add(temp);
			}
		

		return null;
	}
	
	public static boolean editDistrict(String name, String newName) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query gaeQuery = new Query(TABLENAME);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("DistrictName").toString().equals(name))
			{
				entity.setProperty("DistrictName", newName);
				datastore.put(entity);
				return true;
			}
			}
		

		return false;
	}
	
	public static boolean deleteDistrict(String name) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query gaeQuery = new Query(TABLENAME);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("DistrictName").toString().equals(name))
			{
				datastore.delete(entity.getKey());
				return true;
			}
			}
		

		return false;
	}
}
