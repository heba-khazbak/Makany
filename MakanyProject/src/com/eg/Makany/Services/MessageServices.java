package com.eg.Makany.Services;

import java.util.Set;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.eg.Makany.Models.Item;
import com.eg.Makany.Models.Message;
import com.eg.Makany.Models.Post;


@Path("/")
@Produces("text/html")
public class MessageServices {

	@POST
	@Path("/getMsgNamesService")
	public String getMsgNamesService(@FormParam("usermail") String usermail) {

		JSONArray arr = new JSONArray();
		
		Set<Message> names = Message.getMsgNames(usermail);
		
		for(Message m:names){
			JSONObject object = new JSONObject();
			
			object.put("username", m.getSenderName());
			object.put("usermail", m.getSenderMail());
			
			arr.add(object);
		}
		
		return arr.toString();
		
	}
	
	@POST
	@Path("/getChatMessagesService")
	public String getChatMessagesService(@FormParam("usermail") String usermail,
			@FormParam("otherMail") String otherMail) {
		
		
		JSONArray arr = new JSONArray();
		
		Vector<Message> msgs = Message.getmsgsBetween(usermail, otherMail);
		
		for(Message msg:msgs){
			JSONObject object = new JSONObject();
			
			object.put("senderMail",msg.getSenderMail() );
			object.put("reciverMail", msg.getReciverMail());
			object.put("content", msg.getContent());
			object.put("senderName", msg.getSenderName());
			object.put("reciverName", msg.getReciverName());

			arr.add(object);
		}
		
		return arr.toString();
		
	}
	
	@POST
	@Path("/sendMessageService")
	public String sendMessageService(@FormParam("sender") String sender,
			@FormParam("reciver") String reciver, 
			@FormParam("content") String content) {
		
		JSONObject object = new JSONObject();
		
		Message msg=new Message(sender,reciver,content);
		
		if(msg.saveMessage())
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
		
		return object.toString();
		
	}
	
	
	
}
