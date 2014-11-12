package com.sb.framework.view;

import android.app.Activity;
import android.widget.Toast;

public class SBActivity extends Activity{

	protected void toastLong(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
	protected void toastShort(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
}
