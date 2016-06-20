package com.eg.Makany.Services.behaviouralAnalysis;

import java.io.IOException;
import java.util.Vector;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.xml.sax.SAXException;

import com.eg.Makany.Models.Event;
import com.eg.Makany.Models.User;
import com.eg.Makany.Models.BA.EventTopic;
import com.eg.Makany.Models.BA.MakanyAlchemy;
import com.eg.Makany.Models.UpdateProfile.UserProfileUpdate;

@Path("/")
@Produces("text/html")
public class EventAnalysisService {
	@POST
	@Path("/AnalyzeEvents")
	public String AnalyzeEvents() throws XPathExpressionException, JSONException, IOException, SAXException, ParserConfigurationException {
		JSONObject object = new JSONObject();
		
		Vector <User> allUsers = User.getAllUsers();
		
		for (User user : allUsers)
		{
			
			Vector <Event> myEvents = Event.getGoingEvents(user.getMail(),null);
			for(Event event : myEvents)
			{
				if(!new EventTopic(user.getMail(),event.getID(),null,0).saveEventTopic())
					continue;
				
				Vector<String> topics = MakanyAlchemy.getFromAlchemy(event.getDescription());
				if (topics != null)
				{
					int cnt=0;
					for (String A : topics)
					{
						String temp[]=A.split(";");
						double score = Double.parseDouble(temp[1]);
						EventTopic e = new EventTopic (user.getMail() , event.getID() , temp[0] , score);
						e.saveEventTopic();
						if(++cnt<3)
							UserProfileUpdate.saveLovedTopics(user.getMail(), temp[0], 1);
					}
				}
				UserProfileUpdate.saveLovedEvents(user.getMail(), event.getCategory(), event.getID());
			}
		}
		
	    return object.toString();
	}
}
