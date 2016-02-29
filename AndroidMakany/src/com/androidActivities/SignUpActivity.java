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

		@Override
		public void onClick(View v) 
		{
			//UserController.Signup(emailEditText.getText().toString(), usernameEditText.getText().toString(), 
			//passwordEditText.getText().toString(), "", "", "", "", "", "");
		}


}
