package com.eg.Makany.Models;

import java.util.Date;
import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

public class Message {
	
	private static final String TABLENAME = "messages";
	private String senderMail, reciverMail,content;
	private String senderName, reciverName;
	
	public Message(){
		this.senderMail="";
		this.reciverMail="";
		this.content="";
		this.setSenderName("");
		this.setReciverName("");

	}
	
	public Message(String senderMail, String reciverMail, String content) {
		super();
		this.senderMail = senderMail;
		this.reciverMail = reciverMail;
		this.content = content;
	}
	
	public Message(String senderMail, String senderName) {
		super();
		this.senderMail = senderMail;
		this.senderName = senderName;
	}
	
	public String getSenderMail() {
		return senderMail;
	}

	public void setSenderMail(String senderMail) {
		this.senderMail = senderMail;
	}

	public String getReciverMail() {
		return reciverMail;
	}

	public void setReciverMail(String reciverMail) {
		this.reciverMail = reciverMail;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getReciverName() {
		return reciverName;
	}

	public void setReciverName(String reciverName) {
		this.reciverName = reciverName;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	

	
	
	public boolean saveMessage(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity messages = new Entity(TABLENAME);
		
		if(this.senderMail==null)this.senderMail="";
		if(this.reciverMail==null)this.reciverMail="";
		if(this.content==null)this.content="";

		messages.setProperty("date", new Date());
		messages.setProperty("senderMail", this.senderMail);
		messages.setProperty("reciverMail", this.reciverMail);
		messages.setProperty("content", this.content);
		

		datastore.put(messages);

		return true;
	}
	
	

	
	public static Vector<Message> getmsgsBetween(String emailOne,String emailTwo){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Vector<Message> ret =new Vector<Message>();
		//Query gaeQuery = new Query(TABLENAME);
		Query gaeQuery = new Query(TABLENAME).addSort("date", SortDirection.ASCENDING);
		
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for(Entity entity:pq.asIterable()){
			if ((entity.getProperty("senderMail").toString().equals(emailOne)
					&& entity.getProperty("reciverMail").toString().equals(emailTwo))
					|| (entity.getProperty("senderMail").toString().equals(emailTwo)
							&& entity.getProperty("reciverMail").toString().equals(emailOne)) )
			{
				Message msg = new Message(entity.getProperty("senderMail").toString(),
						entity.getProperty("reciverMail").toString(), entity.getProperty("content").toString());
				
				msg.setSenderName(User.getUserName(entity.getProperty("senderMail").toString()));
				msg.setReciverName(User.getUserName(entity.getProperty("reciverMail").toString()));
				
				ret.add(msg);
			}
			
		}

		return ret;
	}
	
	
	public static Vector<Message> getMsgNames(String usermail){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Vector<Message> ret =new Vector<Message>();
		Query gaeQuery = new Query(TABLENAME);
		
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for(Entity entity:pq.asIterable()){
			if (entity.getProperty("senderMail").toString().equals(usermail))
			{
				String name = User.getUserName(entity.getProperty("reciverMail").toString());
				Message M = new Message(entity.getProperty("reciverMail").toString(),name);
				
				boolean flag = true;
				for (Message X : ret)
				{
					if (X.getSenderMail().equals(M.getSenderMail()))
					{
						flag = false;
						break;
					}
				}
				
				if (flag)
					ret.add(M);
			}
			else if (entity.getProperty("reciverMail").toString().equals(usermail))	
			{
				String name = User.getUserName(entity.getProperty("senderMail").toString());
				Message M = new Message(entity.getProperty("senderMail").toString(),name);
				
				boolean flag = true;
				for (Message X : ret)
				{
					if (X.getSenderMail().equals(M.getSenderMail()))
					{
						flag = false;
						break;
					}
				}
				
				if (flag)
					ret.add(M);
				
			}
			
		}
		
		
		return ret;
	}
	
	


}
