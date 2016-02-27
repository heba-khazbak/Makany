package com.eg.Makany.Models;

import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Offer {
	private String id;
	private String description, storeMail, photo;
	private Vector<String> viewers,thumbsup,thumbsdown;
	
	public Offer(){
		this.id="";
		this.description="";
		this.storeMail="";
		this.photo="";
		this.viewers=new Vector<String>();
		this.thumbsup=new Vector<String>();
		this.thumbsdown=new Vector<String>();
	}
	
	public Offer(String id,String description,String storeMail,String photo){
		this.id=id;
		this.description=description;
		this.storeMail=storeMail;
		this.photo=photo;
	}
	
	public String getID(){return id;}
	public String getDescription(){return description;}
	public String getStoreMail(){return storeMail;}
	public String getPhoto(){return photo;}
	public int getNumViewers(){return viewers.size();}
	public int getNumThumbsUp(){return thumbsup.size();}
	public int getNumThumbsDown(){return thumbsdown.size();}
	public String getParsedViewers(){
		String ret="";
		for(int i=0;i<viewers.size();++i){
			if(i>0)ret+="_";
			ret+=viewers.get(i);
		}
		return ret;
	}
	public String getParsedThumbsup(){
		String ret="";
		for(int i=0;i<thumbsup.size();++i){
			if(i>0)ret+="_";
			ret+=thumbsup.get(i);
		}
		return ret;
	}
	public String getParsedThumbsDown(){
		String ret="";
		for(int i=0;i<thumbsdown.size();++i){
			if(i>0)ret+="_";
			ret+=thumbsdown.get(i);
		}
		return ret;
	}
	
	public boolean addOffer(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Entity offer=new Entity("offers");
		offer.setProperty("storeMail", this.storeMail);
		offer.setProperty("description", this.description);
		offer.setProperty("photo", this.photo);
		datastore.put(offer);
		
		return true;
	}
	
	public Offer getOfferByID(String offerID){
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("offers");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		
		this.id=offerID;
		for(Entity off:pq.asIterable()){
			if(String.valueOf(off.getKey().getId()).equals(offerID)){
				this.description=off.getProperty("description").toString();
				this.storeMail=off.getProperty("storeMail").toString();
				this.photo=off.getProperty("photo").toString();
				
				gaeQuery = new Query("offersViewers");
				pq = datastore.prepare(gaeQuery);
				for(Entity entity:pq.asIterable()){
					if(entity.getProperty("offerID").toString().equals(offerID))
						this.viewers.add(entity.getProperty("userEmail").toString());
					
				}
				
				gaeQuery = new Query("offersThumbsup");
				pq = datastore.prepare(gaeQuery);
				for(Entity entity:pq.asIterable()){
					if(entity.getProperty("offerID").toString().equals(offerID))
						this.thumbsup.add(entity.getProperty("userEmail").toString());
					
				}
				
				gaeQuery = new Query("offersThumbsDown");
				pq = datastore.prepare(gaeQuery);
				for(Entity entity:pq.asIterable()){
					if(entity.getProperty("offerID").toString().equals(offerID))
						this.thumbsdown.add(entity.getProperty("userEmail").toString());
					
				}
				break;
			}
			
		}
		return this;
	}
	
	public static Vector<Offer> getOffers(String email){
		Vector<Offer> ret=new Vector<Offer>();
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("offers");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("storeMail").toString().equals(email))
				ret.add(new Offer().
						getOfferByID(String.valueOf(entity.getKey().getId())));
			
		}
		return ret;
	}
	
	public static boolean removeOffer(String offerID){
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Key toDelete=null;
		Query gaeQuery = new Query("offers");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(String.valueOf(entity.getKey().getId()).equals(offerID)){
				toDelete=entity.getKey();
				break;
			}
			
		}

		if(toDelete!=null)
			datastore.delete(toDelete);
		
		return true;
	}
	
	public static boolean editOffer(String offerID,String newDescription,String newPhoto){
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query gaeQuery = new Query("offers");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(String.valueOf(entity.getKey().getId()).equals(offerID)){
				entity.setProperty("description", newDescription);
				entity.setProperty("photo", newPhoto);
				datastore.put(entity);
				return true;
			}
			
		}
		
		return false;
	}
	
	
	
	public static Offer viewOffer(String offerID,String userEmail){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Offer ret=new Offer().getOfferByID(offerID);
		
		for(String str:ret.viewers){
			if(str.equals(userEmail))
				return ret;
		}
		
		
		Entity offViewer=new Entity("offersViewers");
		
		offViewer.setProperty("offerID", offerID);
		offViewer.setProperty("userEmail", userEmail);
		datastore.put(offViewer);
		return ret;
	}
	
	public static int thumbsup(String offerID,String userEmail){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query gaeQuery = new Query("offersThumbsup");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("offerID").toString().equals(offerID)
					&& entity.getProperty("userEmail").toString().equals(userEmail)){
				
				return 2;
			}
			
		}
		
		Entity thumbup=new Entity("offersThumbsup");
		
		
		thumbup.setProperty("offerID", offerID);
		thumbup.setProperty("userEmail", userEmail);
		datastore.put(thumbup);
		return 1;
	}
	
	
	public static int thumbsDown(String offerID,String userEmail){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query gaeQuery = new Query("offersThumbsDown");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("offerID").toString().equals(offerID)
					&& entity.getProperty("userEmail").toString().equals(userEmail)){
				
				return 2;
			}
			
		}
		
		Entity thumbdown=new Entity("offersThumbsDown");
		
		
		thumbdown.setProperty("offerID", offerID);
		thumbdown.setProperty("userEmail", userEmail);
		datastore.put(thumbdown);
		return 1;
	}
	
}
