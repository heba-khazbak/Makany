package com.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.controllers.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.androidActivities.HomeActivity;


public class UserController 
{

	private static UserController userController = new UserController();
	
	
	public static UserController getInstance() 
	{
		if (userController == null)
			userController = new UserController();
		
		return userController;
	}

	private UserController() 
	{ }

	public  void login(String email, String password) 
	{
		new Connection().execute( "http://makanyapp.appspot.com/rest/LoginService", email, password, "LoginService");
	}
	
	public void Signup(String email, String username, String password, String birthDate, 
			String district, String gender, String twitter, String foursquare, String interests) 
	{
		new Connection().execute( "http://makanyapp.appspot.com/rest/signUpService", 
		email, username, password,birthDate, district, gender, twitter, foursquare,
		interests, "signUpService");
	}

	//"http://localhost:8888/rest/LoginService", email,
	
	
	/*public void signUp(String userName, String email, String password) 
	{
		new Connection().execute("", userName, email, password, "RegistrationService");
	}*/
	
	
	static private class Connection extends AsyncTask<String, String, String> 
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
			else if(serviceType.equals("RegistrationService"))
				urlParameters = "uname=" + params[1] + "&email=" + params[2]
						+ "&password=" + params[3];
			

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
					
					Toast.makeText(Application.getAppContext(), "Loginnnnnnnn",
					Toast.LENGTH_LONG).show();
						
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
					
					if(object.getString("Status").equals("WrongPass"))
					{
						Toast.makeText(Application.getAppContext(), "Wrong Password",
						Toast.LENGTH_LONG).show();
						return;
					}
					
					//Logged in successfully 
					Intent homeIntent = new Intent(Application.getAppContext(),HomeActivity.class);
					homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					Application.getAppContext().startActivity(homeIntent);
					System.out.println("okkkkkk " );
					
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
					Intent homeIntent = new Intent(Application.getAppContext(),HomeActivity.class);
					homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					Application.getAppContext().startActivity(homeIntent);
				}
			

				//Do the same for other services
				//else if(serviceType.equals("RegistrationService"))
				//{}
	
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

	