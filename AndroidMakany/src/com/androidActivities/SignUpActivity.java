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
		EditText passwordEditText;
		Button signupButton;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_signup);
			emailEditText = (EditText) findViewById(R.id.email);
			passwordEditText = (EditText) findViewById(R.id.password);
			signupButton = (Button) findViewById(R.id.RegistrationButton);
			signupButton.setOnClickListener(this);

		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		//	UserController controller = Application.getUserController();
			//controller.login(emailEditText.getText().toString(), passwordEditText
				//			.getText().toString());
			
			Context context = getApplicationContext();
			int duration = Toast.LENGTH_SHORT;
			CharSequence text ="!!!";
			
			/*UserController controller = new UserController();
			int successfulLogin = controller.login(emailEditText.getText().toString(), passwordEditText.getText().toString());
			
			if(successfulLogin == 1)
			{
				text = "Success";
			}
			else
			{
				text = "NO";
			}*/

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			
		}


}
