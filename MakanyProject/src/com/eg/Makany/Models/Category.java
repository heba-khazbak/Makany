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

public class Category {
	private static final String TABLENAME = "category";
	private String CategoryValue;
	
	public Category (String CategoryValue)
	{
		this.CategoryValue = CategoryValue;
	}
	
	public String getCategoryValue() {
		return CategoryValue;
	}

	public void setCategoryValue(String CategoryValue) {
		this.CategoryValue = CategoryValue;
	}
	
	public Boolean saveCategory() {
		// should be unique 
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Entity category = new Entity(TABLENAME);

		category.setProperty("CategoryValue", this.CategoryValue);
		
		datastore.put(category);

		return true;

	}
	
	public static Vector<Category> getAllCategories() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Vector<Category> myCategories = new Vector<Category>();
		
		Query gaeQuery = new Query(TABLENAME);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			Category temp = new Category(entity.getProperty("CategoryValue").toString());
			myCategories.add(temp);
			}
		

		return myCategories;
	}
	

	public static boolean editCategory(String name, String newName) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query gaeQuery = new Query(TABLENAME);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("CategoryValue").toString().equals(name))
			{
				entity.setProperty("CategoryValue", newName);
				datastore.put(entity);
				return true;
			}
		}
		

		return false;
	}
	
	public static boolean deleteCategory(String name) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query gaeQuery = new Query(TABLENAME);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("CategoryValue").toString().equals(name))
			{
				datastore.delete(entity.getKey());
				return true;
			}
		}
		

		return false;
	}
	
	public static Vector<Category> parseFromJson(String json) {
		
		Vector<Category> myCategories = new Vector<Category>();
		JSONParser parser = new JSONParser();
		
		JSONArray array = null;
		try {
			array = (JSONArray) parser.parse(json);
			for (int i = 0 ; i < array.size() ; i++)
			{
				JSONObject obj = (JSONObject)array.get(i);
				Category D = new Category (obj.get("CategoryValue").toString());
				myCategories.add(D);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return myCategories;

	}

}
