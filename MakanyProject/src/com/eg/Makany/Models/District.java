package com.eg.Makany.Models;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class District {

	private static final String TABLENAME = "district";
	private String DistrictName;
	private String latitude;
	private String longitude;
	
	public District(String districtName, String latitude, String longitude) {
		super();
		DistrictName = districtName;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	
	public String getDistrictName() {
		return DistrictName;
	}

	public void setDistrictName(String districtName) {
		DistrictName = districtName;
	}
	
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public Boolean saveDistrict() {
		// should be unique 
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Entity district = new Entity(TABLENAME);

		district.setProperty("DistrictName", this.DistrictName);
		district.setProperty("latitude", this.latitude);
		district.setProperty("longitude", this.longitude);
		
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
			District temp = new District(entity.getProperty("DistrictName").toString(),
					entity.getProperty("latitude").toString(), entity.getProperty("longitude").toString());
			myDistricts.add(temp);
			}
		

		return myDistricts;
	}
	
	public static boolean editDistrict(String name, String newName , String newLatitude, String newLongitude) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query gaeQuery = new Query(TABLENAME);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("DistrictName").toString().equals(name))
			{
				entity.setProperty("DistrictName", newName);
				entity.setProperty("latitude", newLatitude);
				entity.setProperty("longitude", newLongitude);
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
	
	public static Vector<District> parseFromJson(String json) {
		
		Vector<District> myDistricts = new Vector<District>();
		JSONParser parser = new JSONParser();
		
		JSONArray array = null;
		try {
			array = (JSONArray) parser.parse(json);
			for (int i = 0 ; i < array.size() ; i++)
			{
				JSONObject obj = (JSONObject)array.get(i);
				District D = new District (obj.get("DistrictName").toString(),
						obj.get("latitude").toString(), obj.get("longitude").toString());
				myDistricts.add(D);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return myDistricts;

	}

	
	
}
