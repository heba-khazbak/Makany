package com.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.ConnectionClosedException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.androidActivities.SignUpActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

public class AdminController 
{
	protected ArrayList<String> interestsList = new ArrayList<String>();

	protected static ArrayList<String> districtsList = new ArrayList<String>();

	
	
	public void getInterests() 
	{
		Connection connectionClass = new Connection();
		
		connectionClass.execute( "http://makanyapp2.appspot.com/rest/ShowAllInterestsService",
		"ShowAllInterestsService");
		
		return;
	}
	
	public static void getDistricts() 
	{
		Connection connectionClass = new Connection();
		
		connectionClass.execute( "http://makanyapp2.appspot.com/rest/ShowAllDistrictsService",
		"ShowAllDistrictsService");
		
		//return;
	}
	
		
	
		
	static class Connection extends AsyncTask<String, String, String> 
	{

		String serviceType;
		
		/*private static ArrayList<String> interestsList = new ArrayList<String>();
		
		public ArrayList<String> get_InterestList() 
		{
			return interestsList;
		}
		*/
		
		@Override
		protected String doInBackground(String... params)
		{
			URL url;
			
			/////////////////////////////////
			serviceType = params[params.length - 1];
			
			String urlParameters="";
			/*if (serviceType.equals("addPostService"))
				urlParameters = "postType="+ params[1] +"&content="+ params[2] +"&photo="
						+ params[3] +"&district=" + params[4] +"&userEmail=" 
						+ params[5] +"&categories="+ params[6];*/
						

			HttpURLConnection connection;
			try {
				url = new URL(params[0]);

				connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setInstanceFollowRedirects(false);
				connection.setRequestMethod("POST");
				connection.setConnectTimeout(60000); // 60 Seconds
				connection.setReadTimeout(60000); // 60 Seconds

				connection.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded;charset=UTF-8");
				System.out.println("urlParameters " + urlParameters);
				System.out.println("URL " + params[0]);
				OutputStreamWriter writer = new OutputStreamWriter(
						connection.getOutputStream());
				writer.write(urlParameters);
				writer.flush();
				
				String line, retJson = "";
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(connection.getInputStream()));

				while ((line = reader.readLine()) != null) {
					retJson += line;
				}
				
				return retJson;

			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(String result) 
		{

			super.onPostExecute(result);
			
			try 
			
			{
				if (serviceType.equals("ShowAllInterestsService")) 
				{
					System.out.println("result " + result);
					
					//String temp="";
					//interestsList = result;
					
					ArrayList<String> interestsList = new ArrayList<String>();
					JSONArray requestArray;
					
					try {
							requestArray = new JSONArray(result);
							for(int i=0;i<requestArray.length();i++)
							
							{
								JSONObject object=new JSONObject();
								object = (JSONObject)requestArray.get(i);
								String x = object.getString("InterestValue");
								interestsList.add(x);
								//temp+= x+ ",";
							}
					
					} 
					
					
					catch (JSONException e) 
					{
						e.printStackTrace();
					}


					//getDistricts();
					//Intent signUpIntent = new Intent(Application.getAppContext(),SignUpActivity.class);
					//signUpIntent.putExtra("interests", interestsList);
					//signUpIntent.putExtra("districts", districtsList);
					//System.out.println("testtttttt" + interestsList.get(0));	
					//signUpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					//Application.getAppContext().startActivity(signUpIntent);

					
					//Toast.makeText(Application.getAppContext(),this.interestsList.get(0), Toast.LENGTH_LONG).show();
					//return;
			
				}
				
				
				if (serviceType.equals("ShowAllDistrictsService")) 
				{
					System.out.println("result " + result);
					
					
					
					JSONArray requestArray;
					
					try {
							requestArray = new JSONArray(result);
							for(int i=0;i<requestArray.length();i++)
							
							{
								JSONObject object=new JSONObject();
								object = (JSONObject)requestArray.get(i);
								String x = object.getString("districtName");
								districtsList.add(x);
							}
					
					} 
					
					
					catch (JSONException e) 
					{
						e.printStackTrace();
					}
					
				
				}
				
				
				//Do the same for other services
				//else if(serviceType.equals(""))
				//{}

			}
			catch(Exception e)
			{}

		}

	}

}

