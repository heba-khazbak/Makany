package com.androidActivities;

import java.util.ArrayList;
import java.util.Vector;

import com.controllers.AdminController;
import com.controllers.Application;
import com.controllers.PostController;
import com.controllers.UserController;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class TestActivity extends Activity implements OnClickListener  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
		Button B = (Button)findViewById(R.id.testButton);
		B.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) 
	{
		PostController controllers = new PostController();
		
		controllers.getPost("art", "maadi", "");
		
		//Toast.makeText(getApplicationContext(),interests.get(0), Toast.LENGTH_LONG).show();
				
	}


}
