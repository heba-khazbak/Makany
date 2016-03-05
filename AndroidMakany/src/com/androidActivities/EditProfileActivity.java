package com.androidActivities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.controllers.UserController;

public class EditProfileActivity extends Activity implements OnClickListener {
	EditText emailEditText;
	EditText usernameEditText;
	EditText passwordEditText;
	EditText birthdateEditText;
	EditText districtEditText;
	EditText genderEditText;
	EditText twitterEditText;
	EditText foursquareEditText;
	EditText intrestsEditText;
	Button  editProfileButton;
	
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editprofile);
		emailEditText = (EditText) findViewById(R.id.email);
		usernameEditText=(EditText) findViewById(R.id.name);
		passwordEditText = (EditText) findViewById(R.id.password);
		birthdateEditText = (EditText) findViewById(R.id.birthdate);
		districtEditText= (EditText) findViewById(R.id.district);
		genderEditText= (EditText) findViewById(R.id.gender);
		twitterEditText = (EditText) findViewById(R.id.twitter);
		foursquareEditText= (EditText) findViewById(R.id.foursqaure);
		intrestsEditText = (EditText) findViewById(R.id.intrests);
		editProfileButton = (Button) findViewById(R.id.Edit);
		
		editProfileButton.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View arg0) 
	{
		//here we should update the user's info 
	}

}
