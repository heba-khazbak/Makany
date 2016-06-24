package com.eg.Makany.Models.UpdateProfile;

import java.util.Vector;

import com.eg.Makany.Models.District;

public class DynamicRecommender {
	public static String getMyDistrict(double longitude, double latitude){
		
		Vector<District> districts = District.getAllDistricts();
		
		double min=-1.0;
		boolean first=true;
		String ret="";
		for(District d:districts){
			double lat=Double.parseDouble(d.getLatitude());
			double lon=Double.parseDouble(d.getLongitude());
			
			double dist = calculateDistance(longitude,latitude,lon,lat);
			
			if(first || dist+1e-9<min){
				min=dist; 
				ret=d.getDistrictName();
				first=false;
			}
		}
		
		return ret;
	}
	
	private static double calculateDistance(double lon1,double lat1,double lon2,double lat2){
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);

		return dist * 60 * 1.1515;
	}
	
	private static double deg2rad(double deg) {
		return deg * Math.PI / 180.0;
	}
	
	private static double rad2deg(double rad) {
		return rad * 180 / Math.PI;
	}
}
