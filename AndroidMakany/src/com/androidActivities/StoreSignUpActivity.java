package com.androidActivities;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class StoreSignUpActivity extends Activity implements OnClickListener{
	
	EditText StoreNameEditText;
	EditText EmailEditText;
	EditText passwordEditText;
	EditText DistrictEditText;
	EditText categoryEditText;
	EditText DiscriptionEditText;
	EditText offerEditText;
	EditText reviewEditText;
	Button signupButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		
		StoreNameEditText = (EditText) findViewById(R.id.storeName);
		EmailEditText = (EditText) findViewById(R.id.email);
		passwordEditText = (EditText) findViewById(R.id.password);
		DistrictEditText = (EditText) findViewById(R.id.district);
		categoryEditText = (EditText) findViewById(R.id.category);
		DiscriptionEditText = (EditText) findViewById(R.id.Discription);
		offerEditText = (EditText) findViewById(R.id.offers);
		reviewEditText = (EditText) findViewById(R.id.reviews);
		
		signupButton = (Button) findViewById(R.id.StoreRegistration);
		signupButton.setOnClickListener(this);
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	

}
