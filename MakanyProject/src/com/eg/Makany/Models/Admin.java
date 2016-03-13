package com.eg.Makany.Models;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Admin {
	private static final String TABLENAME = "admin";
	private String username;
	private String password;
	
	public Admin(String username, String password) {
		this.username = username;
		this.password = password;
		
	}
	
	public static Admin getAdmin(String username, String pass) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query(TABLENAME);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("username").toString().equals(username)
					&& entity.getProperty("password").toString().equals(pass)) {
				Admin returnedUser = new Admin(entity.getProperty(
						"username").toString(), entity.getProperty("password").toString());
				return returnedUser;
			}
		}

		return null;
	}
	
	public Boolean saveUser() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Entity user = new Entity(TABLENAME);

		user.setProperty("username", this.username);
		user.setProperty("password", this.password);
		
		datastore.put(user);

		return true;

	}

}
