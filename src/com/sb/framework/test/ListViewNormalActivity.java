package com.sb.framework.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.sb.framework.R;
import com.sb.framework.utils.SimpleAsyncTask;
import com.sb.framework.view.SBActivity;
import com.sb.framework.view.SBQuery;
import com.sb.framework.view.SBSimpleAdapter;

public class ListViewNormalActivity extends SBActivity {
	
	List<Bean> list;
	TestAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adapter_view);
		
		SBQuery q = new SBQuery(this, true);
		
		adapter = new TestAdapter(this, list);
		q.id(R.id.listview).adapter(adapter);
		q.id(R.id.listview).itemClicked(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				toastShort("点击：" + arg2 + ", " + arg3);
			}
		});
		
		new SimpleAsyncTask() {
			
			@Override
			protected void onRunning() {
				list = new ArrayList<Bean>();
				list.add(new Bean());
				list.add(new Bean());
				list.add(new Bean());
				list.add(new Bean());
				list.add(new Bean());
				list.add(new Bean());
				handler.sendEmptyMessage(1);
			}
		}.go();
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			adapter.notifyDataSetChanged(list);
		}
	};

	class TestAdapter extends SBSimpleAdapter<Bean>{

		public TestAdapter(Activity context, List<Bean> list) {
			super(context, list);
		}
		
		@Override
		protected int getLayoutId() {
			return android.R.layout.simple_list_item_1;
		}

		@Override
		public boolean isConvertViewUseable(View convertView) {
			return true;
		}

		@Override
		public void fillHolder(com.sb.framework.view.SBSimpleAdapter.ViewHolder holder,
				View convertView, Bean bean, int position) {
			TextView text1 = (TextView) holder.findViewByID(android.R.id.text1, convertView);
			text1.setText(bean.name);
		}
		
	}

}

class Bean{
	public String name = System.currentTimeMillis()+"";
	public int age;
	public Date birthday;
}
