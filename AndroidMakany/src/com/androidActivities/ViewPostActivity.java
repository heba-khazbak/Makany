package com.androidActivities;

import java.util.ArrayList;

import SimpleModels.FilteredPost;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ViewPostActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_post);
		
		
		ArrayList<FilteredPost> post_Array = null;
		
		
		LinearLayout my_layout = (LinearLayout)findViewById(R.id.postLayout);

		for (int i = 0; i < post_Array.size(); i++) 
		{
		    TableRow row =new TableRow(this);
		    row.setId(i);
		    row.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		    TextView post_content = new TextView(this);
		    post_content.setId(i);
		    post_content.setText(post_Array.get(i).content);
		    row.addView(post_content);  
		    my_layout.addView(row);
		}
		
	}
}
