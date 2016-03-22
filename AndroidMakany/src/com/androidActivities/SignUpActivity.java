package com.androidActivities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.controllers.Application;
import com.controllers.UserController;

public class SignUpActivity extends Activity implements OnClickListener {

		EditText emailEditText;
		EditText usernameEditText;
		EditText passwordEditText;
		//spinner district
		EditText birthdateEditText;
		EditText descriptionEditText;
		//spinner genre
		EditText twitterAccountEditText;
		EditText foursquareAccountEditText;
		//checkbox interests
		
		Set<String> interestsSet = new HashSet<String>();

		
		
		Button signupButton;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_signup);
			emailEditText = (EditText) findViewById(R.id.email);
			usernameEditText = (EditText) findViewById(R.id.name);
			passwordEditText = (EditText) findViewById(R.id.password);
			
			birthdateEditText =(EditText) findViewById(R.id.birthDate);
			descriptionEditText = (EditText) findViewById(R.id.description);
			twitterAccountEditText = (EditText) findViewById(R.id.twitterAccount);
			foursquareAccountEditText = (EditText) findViewById(R.id.foursquareAccount);
			
			
			signupButton = (Button) findViewById(R.id.RegistrationButton);
			
			
			
			ArrayList<String> Str_Array = null;
			
			Intent currentIntent = getIntent();
			Str_Array = currentIntent.getStringArrayListExtra("interests");
			
			
			Toast.makeText(getApplicationContext(),
			"interst 1 is: " + Str_Array.get(0), Toast.LENGTH_LONG).show();
			
			
			//AdminController adminController = new AdminController();
			
			///Str_Array= adminController.getInterests();
			//Array_Count=Str_Array.size();

			//Toast.makeText(Application.getAppContext(), Integer.toString(Array_Count), Toast.LENGTH_LONG).show();
					
		LinearLayout my_layout = (LinearLayout)findViewById(R.id.interestLayout);

			for (int i = 0; i < Str_Array.size(); i++) 
			{
			    TableRow row =new TableRow(this);
			    row.setId(i);
			    row.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			    CheckBox checkBox = new CheckBox(this);
			    //checkBox.setOnClickListener(this);
			    checkBox.setTag(Str_Array);
			    checkBox.setId(i);
			    checkBox.setText(Str_Array.get(i));
			    row.addView(checkBox);  
			    my_layout.addView(row);
			}
		
			signupButton.setOnClickListener(this);
		}
		
		/*public void addListenerOnSpinnerItemSelection() 
		{
				Spinner spinner1 = (Spinner) findViewById(R.id.genderSpinner);
				spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		}*/


		@Override
		public void onClick(View v) 
		{
			UserController userController = Application.getUserController();
			if (userController == null)
			{
				Toast.makeText(getApplicationContext(), "null! ", Toast.LENGTH_LONG).show();
			}
			else
			{
			
				userController.Signup(usernameEditText.getText().toString(), 
				  emailEditText.getText().toString(), passwordEditText.getText().toString(), 
				  birthdateEditText.getText().toString(), "maadi",
				  descriptionEditText.getText().toString(),
				  "female", twitterAccountEditText.getText().toString(),
				  foursquareAccountEditText.getText().toString(), "food_sport");

			}				
					
		}

	
}
