package com.androidActivities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
			signupButton.setOnClickListener(this);
		}
		public void addListenerOnSpinnerItemSelection() 
		{
				Spinner spinner1 = (Spinner) findViewById(R.id.gender);
				spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		}


		@Override
		public void onClick(View v) 
		{
			//UserController.Signup(emailEditText.getText().toString(), usernameEditText.getText().toString(), 
			//passwordEditText.getText().toString(), "", "", "", "", "", "");
		}


}
