package com.eg.Makany.Models;

import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class User {
	private String id;
	private String name, email, password, birthDate, district, gender;
	private String twitter, foursquare;
	private Vector<String> interests;
	
	public User(String id, String name,String email,String password,String birthDate,
			String district, String gender, String twitter, String foursquare,
			Vector<String> interests){
		this.id=id;
		this.name=name;
		this.email=email;
		this.password=password;
		this.birthDate=birthDate;
		this.district=district;
		this.gender=gender;
		this.twitter=twitter;
		this.foursquare=foursquare;
		this.interests=interests;
	}
	
	public String getID(){return id;}
	public String getName(){return name;}
	public String getMail(){return email;}
	public String getPassword(){return password;}
	public String getBirthDate(){return birthDate;}
	public String getDistrict(){return district;}
	public String getGender(){return gender;}
	public String getTwitter(){return twitter;}
	public String getFoursquare(){return foursquare;}
	public String getParsedInterests(){
		String ret="";
		for(int i=0;i<interests.size();++i){
			if(i>0)ret+=";";
			ret+=interests.get(i);
		}
		return ret;
	}
	
	
	public boolean saveUser(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Entity user = new Entity("users");

		user.setProperty("name", this.name);
		user.setProperty("email", this.email);
		user.setProperty("password", this.password);
		user.setProperty("birthDate", this.birthDate);
		user.setProperty("district", this.district);
		user.setProperty("gender", this.gender);
		user.setProperty("twitter", this.twitter);
		user.setProperty("foursquare", this.foursquare);
		datastore.put(user);
		
		
		for(String str:this.interests){
			Entity interest=new Entity("interests");
			interest.setProperty("userEmail", this.email);
			interest.setProperty("name", str);
			datastore.put(interest);
		}

		return true;
	}
	
	public static boolean removeUser(String email){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Vector<Key> keysToDelete=new Vector<Key>();
		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("email").toString().equals(email)){
				keysToDelete.add(entity.getKey());
				break;
			}
		}
		
		gaeQuery = new Query("interests");
		pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("userEmail").toString().equals(email))
				keysToDelete.add(entity.getKey());
			
		}
		
		for(Key k:keysToDelete)
			datastore.delete(k);
		
		return true;
	}
	
	public static Vector<String> getInterests(String userEmail){
		Vector<String> ret=new Vector<String>();
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("interests");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("userEmail").toString().equals(userEmail))
				ret.add(entity.getProperty("name").toString());
			
		}
		return ret;
	}
	
	public static User getUser(String email){
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			String userEmail=entity.getProperty("email").toString();
			if(userEmail.equals(email)){
				return new User(String.valueOf(entity.getKey().getId()),
						entity.getProperty("name").toString(),
						userEmail,
						entity.getProperty("password").toString(),
						entity.getProperty("birthDate").toString(),
						entity.getProperty("district").toString(),
						entity.getProperty("gender").toString(),
						entity.getProperty("twitter").toString(),
						entity.getProperty("foursquare").toString(),
						getInterests(userEmail));
			}
		}
		
		return null;
	}
	
	public static Vector<User> getAllUsers(){
		Vector<User> ret=new Vector<User>();
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			String userEmail=entity.getProperty("email").toString();
			ret.add(new User(String.valueOf(entity.getKey().getId()),
					entity.getProperty("name").toString(),
					userEmail,
					entity.getProperty("password").toString(),
					entity.getProperty("birthDate").toString(),
					entity.getProperty("district").toString(),
					entity.getProperty("gender").toString(),
					entity.getProperty("twitter").toString(),
					entity.getProperty("foursquare").toString(),
					getInterests(userEmail)));
		}
		
		return ret;
	}
	
	public static int checkUser(String email, String password){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("email").toString().equals(email)){
				if(entity.getProperty("password").toString().equals(password))
					return 1;
				else
					return 2;
			}
		}
		return 0;
	}
	
	public static String getUserName(String email){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		String ret=null;
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("email").toString().equals(email)){
				ret=entity.getProperty("name").toString();
				break;
			}
		}
		return ret;
	}
}
