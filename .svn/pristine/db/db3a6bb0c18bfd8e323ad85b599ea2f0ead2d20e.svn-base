package com.sb.framework.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SBViewCompat {
	/**
	 * 设置listview的高度为固定值
	 * 
	 * @param listView
	 */
	public static int setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return -100;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			if (null == listItem) {
				continue;
			}
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();

		}
		

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + 
				(listView.getDividerHeight() * (listAdapter.getCount() - 1)) + 400;
		listView.setLayoutParams(params);
		return totalHeight;
	}
}
