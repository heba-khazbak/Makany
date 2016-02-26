package com.androidActivities;


import com.controllers.UserController;
import com.controllers.Application;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity implements OnClickListener {

	EditText emailEditText;
	EditText passwordEditText;
	Button loginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		emailEditText = (EditText) findViewById(R.id.email);
		passwordEditText = (EditText) findViewById(R.id.password);
		loginButton = (Button) findViewById(R.id.loginButton);
		loginButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		UserController controller = Application.getUserController();
		controller.login(emailEditText.getText().toString(), passwordEditText
						.getText().toString());
		
		/*Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		CharSequence text ="!!!";
		
		UserController controller = new UserController();
		int successfulLogin = controller.login(emailEditText.getText().toString(), passwordEditText.getText().toString());
		
		if(successfulLogin == 1)
		{
			text = "Success";
		}
		else
		{
			text = "NO";
		}

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();*/
		
	}

}