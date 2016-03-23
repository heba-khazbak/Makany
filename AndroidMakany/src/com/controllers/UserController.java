package com.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.controllers.Application;
import com.androidActivities.HomeActivity;
import com.androidActivities.MainActivity;

import SimpleModels.SimpleUser;
import android.R.string;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;



public class UserController 
{

	private static UserController userController = new UserController();
	protected static SimpleUser simpleUser;
	protected static String email;
	
	public static UserController getInstance() 
	{
		if (userController == null)
			userController = new UserController();
		
		return userController;
	}

	private UserController() 
	{ }

	public void login(String email, String password) 
	{
		new Connection().execute( "http://makanyapp2.appspot.com/rest/LoginService", email, password, "LoginService");
		this.email=email;
	}
	
	public static void getUser(String email) 
	{
		new Connection().execute( "http://makanyapp2.appspot.com/rest/getUserService", email, "getUserService");
	}
	
	public void Signup(String name, String email, String password, String birthDate, 
						String district, String description, String gender, 
						String twitter, String foursquare, String interests) 
	{
		String uType="normal";
		String category="NONE";
		
		new Connection().execute( "http://makanyapp2.appspot.com/rest/signUpService", uType,
		name, email, password, birthDate, district, category, description, gender, twitter, 
		foursquare, interests, "signUpService");
	}
	
	//new by magie
	public void EditProfile(String email, String name, String password, String birthDate, 
			String district, String gender, String twitter, String foursquare, String interests) 
	{
		new Connection().execute( "http://makanyapp2.appspot.com/rest/editProfileService", 
		email, name, password,birthDate, district, gender, twitter, foursquare,
		interests, "editProfileService");
	}

	//"http://localhost:8888/rest/LoginService",
	
	
	static class Connection extends AsyncTask<String, String, String> 
	{

		String serviceType;

		@Override
		protected String doInBackground(String... params)
		{
			URL url;
			serviceType = params[params.length - 1];
			String urlParameters="";
			
			if (serviceType.equals("LoginService"))
				urlParameters = "email=" + params[1] + "&password=" + params[2];
			
			
			if (serviceType.equals("getUserService"))
				urlParameters = "email=" + params[1];
			
			
			else if(serviceType.equals("signUpService"))
				urlParameters ="uType=" + params[1] + "&name=" + params[2] + "&email=" + params[3] + 
							   "&password=" + params[4] + "&birthDate=" + params[5] + 
							   "&district=" + params[6] + "&category=" + params[7] +"&description=" 
							   + params[8] + "&gender=" + params[9] + "&twitter=" + params[10] 
							   + "&foursquare=" + params[11] + "&interests=" + params[12];
		
			//////////////////////
			else if(serviceType.equals("editProfileService"))
				urlParameters ="email=" + params[1] + "&username=" + params[2] + 
							   "&password=" + params[3] + "&birthDate=" + params[4] + 
							   "&district=" + params[5] + "&gender=" + params[6] + 
							   "&twitter=" + params[7] + "&foursquare=" + params[8] + 
							   "&interests=" + params[9]; 
				
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
				if (serviceType.equals("LoginService")) 
				{
					System.out.println("result " + result);
					
					/*JSONParser parser = new JSONParser();
					Object obj = parser.parse(result);
					JSONObject object = (JSONObject) obj;*/
					
					JSONObject object = new JSONObject(result);
					
					
					if(object== null || !object.has("Status"))
					{
						System.out.println("eroor" );
						Toast.makeText(Application.getAppContext(), "Error occured",
						Toast.LENGTH_LONG).show();
						return;
					}
					
					if(object.getString("Status").equals("Failed"))
					{
						Toast.makeText(Application.getAppContext(), "error: Retype your email",
						Toast.LENGTH_LONG).show();
						return;
					}
					
					if(object.getString("Status").equals("wrongPass"))
					{
						Toast.makeText(Application.getAppContext(), "Wrong Password",
						Toast.LENGTH_LONG).show();
						return;
					}
					
					//Logged in successfully 
					
					Intent homeIntent = new Intent(Application.getAppContext(),HomeActivity.class);
					homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					
					
					
					
					
					//String username = User
					//homeIntent.putExtra("id", user.getUserAccountId()+"");
					//homeIntent.putExtra("name", user.getUserFullName());
					
					
					//String email = simpleUser.get_email();
					
					////////////////////////////////////
					//getUser(email);
					///////////////////////////////////
					
					homeIntent.putExtra("email", email);
					//homeIntent.putExtra("username", simpleUser.name);
					
					//System.out.println(simpleUser.get_email());
					//System.out.println(simpleUser.name);
					
					
					Application.getAppContext().startActivity(homeIntent);
					
					
				}
			
				
				if (serviceType.equals("getUserService")) 
				{
					System.out.println("result " + result);
					
					/*JSONParser parser = new JSONParser();
					Object obj = parser.parse(result);
					JSONObject object = (JSONObject) obj;*/
					
					JSONObject object = new JSONObject(result);
					
					
					if(object== null || !object.has("Status"))
					{
						System.out.println("eroor" );
						Toast.makeText(Application.getAppContext(), "Error occured",
						Toast.LENGTH_LONG).show();
						return;
					}
					
					if(object.getString("Status").equals("Failed"))
					{
						Toast.makeText(Application.getAppContext(), "error: Retype your email",
						Toast.LENGTH_LONG).show();
						return;
					}
					
					JSONObject currentUser;
					
					try {
							currentUser = object;
							
							String interests = currentUser.getString("interests");
							String [] interestsArray = interests.split(";");
							
							Vector<String> interestsVector = new Vector<String>();
							
							for(int i=0; i<interestsArray.length; i++)
							{
								interestsVector.add(interestsArray[i]);
							}
							
							simpleUser = new SimpleUser(currentUser.getString("ID"),
									currentUser.getString("name"),currentUser.getString("email"),
									currentUser.getString("password"),currentUser.getString("birthDate"),
									currentUser.getString("district"),currentUser.getString("gender"),
									currentUser.getString("twitter"),currentUser.getString("foursquare"),
									Integer.parseInt(currentUser.getString("trust")), interestsVector);
							
					} 
					
					
					catch (JSONException e) 
					{
						e.printStackTrace();
					}
					
				
					
				}
			
					
				else if(serviceType.equals("signUpService"))
				{
					System.out.println("result " + result);
					
					JSONObject object = new JSONObject(result);
					
					if(object== null || !object.has("Status") 
					 ||object.getString("Status").equals("Failed"))
					{
						Toast.makeText(Application.getAppContext(), "Error occured",
						Toast.LENGTH_LONG).show();
						return;
					}
					
					//Signed Up successfully 
					Toast.makeText(Application.getAppContext(), "Success",
					Toast.LENGTH_LONG).show();
					
					Intent mainIntent = new Intent(Application.getAppContext(),MainActivity.class);
					mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					
					//AdminController adminController = new AdminController();
					///adminController.getInterests();	
					
					
					Application.getAppContext().startActivity(mainIntent);
				}
			
				else if(serviceType.equalsIgnoreCase("editProfileService"))
				{
					
					System.out.println("result" + result);
					
					JSONObject object=new JSONObject (result);
					
					if(object== null || !object.has("Status") 
					  ||object.getString("Status").equals("Failed"))
					{
						Toast.makeText(Application.getAppContext(), "Error occured",
						Toast.LENGTH_LONG).show();
						return;
					}
							
					//edit profile succeed
					Toast.makeText(Application.getAppContext(), "Success",
					Toast.LENGTH_LONG).show();
					
					Intent mainIntent = new Intent(Application.getAppContext(),MainActivity.class);
					mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					Application.getAppContext().startActivity(mainIntent);
					
					
				}


			} 
			catch (JSONException e) 
			{
				e.printStackTrace();
			}
			catch(Exception e)
			{}

		}

	}
	
	

}

	