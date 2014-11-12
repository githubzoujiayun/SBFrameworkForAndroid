package com.sb.framework.test;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import com.sb.framework.R;
import com.sb.framework.rubbish.SBObjectCache;
import com.sb.framework.utils.SimpleAsyncTask;
import com.sb.framework.view.SBQuery;

public class RubbishOOMActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rubbish_oom);
		
		SBQuery q = new SBQuery(this);
		q.id(R.id.btn1).clicked(this);
		q.id(R.id.btn2).clicked(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_rubbish_oom, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(id == R.id.btn1){
			new SimpleAsyncTask() {
				
				@Override
				protected void onRunning() {
					tryOOM();
				}
			}.go();
			
		}
		
		if(id == R.id.btn2){
			new SimpleAsyncTask() {
				
				@Override
				protected void onRunning() {
					tryNotOOM();
				}
			}.go();
		}
	}
	
	private int loop = 10000;
	private void tryOOM(){
		List<Bitmap> list = new ArrayList<Bitmap>();
		for(int i = 0; i < loop; i++){
			//String str = System.nanoTime()+"";
			Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.oom);
			list.add(b);
		}
	}
	
	private void tryNotOOM(){
		SBObjectCache rubbish = new SBObjectCache();
		for(int i = 0; i < loop; i++){
			Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.oom);
			rubbish.recycle(b);
		}
	}

}
