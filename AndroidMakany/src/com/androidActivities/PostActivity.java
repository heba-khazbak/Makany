package com.androidActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.controllers.Application;
import com.controllers.PostController;
import com.controllers.UserController;


public class PostActivity extends Activity implements OnClickListener {

	EditText postEditText;
	Button postButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		postEditText = (EditText) findViewById(R.id.postEditText);
		postButton = (Button) findViewById(R.id.postButton);
		postButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		UserController controller = Application.getUserController();
		if (controller == null)
		{
			Toast.makeText(getApplicationContext(), "null! ", Toast.LENGTH_LONG).show();
		}
		else
		{
			Intent currentIntent = getIntent();
			String currentEmail = currentIntent.getStringExtra("email");
			System.out.println("curentEmail on post screen " + currentEmail);
			
			PostController postController = new PostController();
			postController.addPost("normal", postEditText.getText().toString(), "no-pic", 
			"maadi",currentEmail, "none");
		}		
	
	}
}
