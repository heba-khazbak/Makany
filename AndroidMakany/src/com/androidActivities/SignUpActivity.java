package com.androidActivities;

import java.util.ArrayList;

import com.controllers.AdminController;
import com.controllers.Application;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;

public class SignUpActivity extends Activity implements OnClickListener {

		EditText emailEditText;
		EditText usernameEditText;
		EditText passwordEditText;
		Button signupButton;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_signup);
			emailEditText = (EditText) findViewById(R.id.email);
			usernameEditText = (EditText) findViewById(R.id.name);
			passwordEditText = (EditText) findViewById(R.id.password);
			signupButton = (Button) findViewById(R.id.RegistrationButton);
			
			
			int Array_Count=0;
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

			for (int i = 0; i < Array_Count; i++) 
			{
			    TableRow row =new TableRow(this);
			    row.setId(i);
			    row.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			    CheckBox checkBox = new CheckBox(this);
			    checkBox.setOnCheckedChangeListener((OnCheckedChangeListener) this);
			    checkBox.setId(i);
			    checkBox.setText(Str_Array.get(i));
			    row.addView(checkBox);  
			    my_layout.addView(row);
			}
			
			
			
			signupButton.setOnClickListener(this);
		}
		
		public void addListenerOnSpinnerItemSelection() 
		{
				Spinner spinner1 = (Spinner) findViewById(R.id.genderSpinner);
				spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		}


		@Override
		public void onClick(View v) 
		{
			//UserController.Signup(emailEditText.getText().toString(), usernameEditText.getText().toString(), 
			//passwordEditText.getText().toString(), "", "", "", "", "", "");
		}


}
