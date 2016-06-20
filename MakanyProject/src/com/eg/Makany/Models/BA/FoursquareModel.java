package com.eg.Makany.Models.BA;

import java.util.Vector;

public class FoursquareModel {
	

	private String name , address;
	private String rating , phone;
	private String distance; // with meters
	private Vector<String> category;
	private String latitude, longitude;
	
	public FoursquareModel(String name, String address, String rating,
			String phone, String distance,
			String latitude, String longitude) {
		super();
		if (name == null)
			this.name = "";
		else
			this.name = name;
		if (address == null)
			this.address = "";
		else
			this.address = address;
		
		if (rating == null)
			this.rating = "";
		else
			this.rating = rating;
		
		if (phone == null)
			this.phone = "";
		else
			this.phone = phone;
		
		if (distance == null)
			this.distance = "";
		else
			this.distance = distance;
		
		if (latitude == null)
			this.latitude = "";
		else
			this.latitude = latitude;
		
		if (longitude == null)
			this.longitude = "";
		else
			this.longitude = longitude;
		
		this.category = new Vector<String>();
	
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public Vector<String> getCategory() {
		return category;
	}
	public void setCategory(Vector<String> category) {
		this.category = category;
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
	
	public void addCategory(String c)
	{
		this.category.add(c);
	}


	@Override
	public String toString() {
		return "FoursquareModel [name=" + name + ", address=" + address
				+ ", rating=" + rating + ", phone=" + phone + ", distance="
				+ distance + ", category=" + category + ", latitude="
				+ latitude + ", longitude=" + longitude + "]";
	}

	

}
