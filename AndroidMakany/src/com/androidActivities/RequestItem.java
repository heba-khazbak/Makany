package com.androidActivities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RequestItem extends Activity implements OnClickListener{

	EditText itemnameEditText;
	EditText discriptionEditText;
	EditText usermailEditText;
	EditText categoryEditText;
	Button request;
	

	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request_item);
		
		itemnameEditText = (EditText) findViewById(R.id.itemname);
		discriptionEditText=(EditText) findViewById(R.id.itemDiscription);
		usermailEditText = (EditText) findViewById(R.id.useremail);
		categoryEditText = (EditText) findViewById(R.id.category);
		request= (Button) findViewById(R.id.request);
		request.setOnClickListener(this);

	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		
		
	}

}
