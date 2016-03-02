package com.androidActivities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends Activity implements OnClickListener 
{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		Button buttonHome = (Button) findViewById(R.id.homeButton);
		buttonHome.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) 
	{
	
		Intent currentIntent = getIntent();
		String currentEmail = currentIntent.getStringExtra("email");
		Toast.makeText(getApplicationContext(),
		"Welocome User!\nYour Email is: " + currentEmail, Toast.LENGTH_LONG).show();
		
	}

}
