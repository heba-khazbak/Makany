package SimpleModels;

import java.util.Vector;

public class SimpleEvent 
{

	private String id, name, category, description;
	private double latitude, longitude;
	private String ownerMail;
	private Vector<String> goingMails, postIDs;
	
	
	public SimpleEvent(String id, String name, String category, String description,
			double latitude, double longitude, String ownerMail,
			Vector<String> goingMails, Vector<String> postIDs)
	{
		this.id=id;
		this.name=name;
		this.category=category;
		this.description=description;
		this.latitude=latitude;
		this.longitude=longitude;
		this.ownerMail=ownerMail;
		this.goingMails=goingMails;
		this.postIDs=postIDs;
	}
	

}
