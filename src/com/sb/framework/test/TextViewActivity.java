package com.sb.framework.test;

import com.sb.framework.R;
import com.sb.framework.R.layout;
import com.sb.framework.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TextViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text_view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_text_view, menu);
		return true;
	}

}
