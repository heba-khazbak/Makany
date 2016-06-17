package com.eg.Makany.Models;


import java.util.Date;
import java.util.Vector;

import com.eg.Makany.Models.BA.PostTopic;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class User {
	private String id;
	private String name, email, password, birthDate, district, gender;
	private String twitter, foursquare, date;
	private int trust;
	private Vector<String> interests;
	
	public User(){
		this.id="";
		this.name="";
		this.email="";
		this.password="";
		this.birthDate="";
		this.district="";
		this.gender="";
		this.twitter="";
		this.foursquare="";
		this.date="";
		this.trust=0;
		this.interests=new Vector<String>();
	}
	
	public User(String id, String name,String email,String password,String birthDate,
			String district, String gender, String twitter, String foursquare,
			String date,int trust, Vector<String> interests){
		this.id=id;
		this.name=name;
		this.email=email;
		this.password=password;
		this.birthDate=birthDate;
		this.district=district;
		this.gender=gender;
		this.twitter=twitter;
		this.foursquare=foursquare;
		this.date=date;
		this.trust=trust;
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
	public String getDate(){return date;}
	public int getTrust(){return trust;}
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
		
		if(this.name==null)this.name="";
		if(this.email==null)this.email="";
		if(this.password==null)this.password="";
		if(this.birthDate==null)this.birthDate="";
		if(this.district==null)this.district="";
		if(this.gender==null)this.gender="";
		if(this.twitter==null)this.twitter="";
		if(this.foursquare==null)this.foursquare="";
		if(this.interests==null)this.interests=new Vector<String>();

		user.setProperty("name", this.name);
		user.setProperty("email", this.email);
		user.setProperty("password", this.password);
		user.setProperty("birthDate", this.birthDate);
		user.setProperty("district", this.district);
		user.setProperty("gender", this.gender);
		user.setProperty("twitter", this.twitter);
		user.setProperty("foursquare", this.foursquare);
		user.setProperty("date", new Date());
		user.setProperty("trust", this.trust);
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
	
	public User getUser(String userMail){
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		
		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			String userEmail=entity.getProperty("email").toString();
			if(userEmail.equals(userMail)){
				this.id=String.valueOf(entity.getKey().getId());
this.name=entity.getProperty("name").toString();
				this.email=userEmail;
				this.password=entity.getProperty("password").toString();
				this.birthDate=entity.getProperty("birthDate").toString();
				this.district=entity.getProperty("district").toString();
				this.gender=entity.getProperty("gender").toString();
				this.twitter=entity.getProperty("twitter").toString();
				this.foursquare=entity.getProperty("foursquare").toString();
				this.date=entity.getProperty("date").toString();
				this.trust=Integer.parseInt(entity.getProperty("trust").toString());
				this.interests=getInterests(userEmail);
				break;
			}
		}
		
		return this;
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
					entity.getProperty("date").toString(),
					Integer.parseInt(entity.getProperty("trust").toString()),
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
	
	public static String getUserDistrict(String email){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		String ret="";
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("email").toString().equals(email)){
				ret=entity.getProperty("district").toString();
				break;
			}
		}
		return ret;
	}
	
	public static int getTrust(String email){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		

		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("email").toString().equals(email))
				return Integer.parseInt(entity.getProperty("trust").toString());
			
		}
		assert(true);
		return 0;
	}
	
	public static boolean updateTrust(String email,int newTrust){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		

		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("email").toString().equals(email)){
				entity.setProperty("trust", newTrust);
				datastore.put(entity);
				return true;
			}
			
		}

		return false;
	}
	
}
