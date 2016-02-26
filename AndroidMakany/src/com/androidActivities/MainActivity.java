package com.androidActivities;

import com.androidActivities.LoginActivity;
import com.androidActivities.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity {

	
	Button login;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	  super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
          login = (Button) findViewById(R.id.login);
          login.setOnClickListener(new OnClickListener() {
  			
  			@Override
  			public void onClick(View arg0) {
  				// TODO Auto-generated method stub
  				Intent loginIntent = new Intent(getApplicationContext(),LoginActivity.class);
  				startActivity(loginIntent);
  				
  			}
  		});
             }
    
    
}
