package com.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;



public class UserController {

	private static UserController userController;

	public static UserController getInstance() {
		if (userController == null)
			userController = new UserController();
		return userController;
	}

	private UserController() {

	}

	public void login(String email, String password) {
		
		new Connection().execute(
				"http://localhost:8889/rest/LoginService", email,
				password, "LoginService");
	}

	public void signUp(String userName, String email, String password) {
		new Connection().execute(
				"", userName,
				email, password, "RegistrationService");
	}
	
	
	
	

	static private class Connection extends AsyncTask<String, String, String> {

		String serviceType;

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
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

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {
				
				
				
				if (serviceType.equals("LoginService")) {
					JSONObject object = new JSONObject(result);
					
					if(!object.has("Status") || object.getString("Status").equals("Failed")){
						Toast.makeText(Application.getAppContext(), "Error occured", Toast.LENGTH_LONG).show();
						return;
					}
					else if(object.getString("Status").equals("OK"))
						Toast.makeText(Application.getAppContext(), "Yees", Toast.LENGTH_LONG).show();
					else
						Toast.makeText(Application.getAppContext(), "No", Toast.LENGTH_LONG).show();
						
				}
				else if(serviceType.equals("RegistrationService")){
					
				}
				

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

}
