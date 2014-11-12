package com.sb.framework.view;

import java.io.File;
import java.lang.reflect.Method;

import com.sb.framework.http.image.ImageMgmr;
import com.sb.framework.http.image.OnImageDownloadCallback;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SBQuery {
	private View view;
	private boolean useCache;//使用缓存适用于多次使用id(R.id.xx)、id(view)方法的地方(例如adaper)，不使用缓存适用于使用id(R.id.xx)、id(view)一次或不多的的情况
	private static SparseArray<SBQuery> mqCache;//mq变量缓存
	
	///--------------------------构造函数------------------------------------//
	public SBQuery(){
		//需要配合下面几个函数使用
	}
	public SBQuery acticity(Activity activity){
		this.view = activity.getWindow().getDecorView();
		return this;
	}
	public SBQuery acticity(Activity activity, boolean useCache){
		this.useCache = useCache;
		this.view = activity.getWindow().getDecorView();
		return this;
	}
	public SBQuery view(View view){
		this.view = view;
		return this;
	}
	public SBQuery view(View view,boolean usecache){
		this.useCache = usecache;
		this.view = view;
		return this;
	}
	
	public SBQuery (Activity activity) {
		this.view = activity.getWindow().getDecorView();
	}
	
	public SBQuery (View view) {
		this.view = view;
	}
	
	public SBQuery (Activity activity,boolean usecache) {
		this.useCache = usecache;
		this.view = activity.getWindow().getDecorView();
	}
	
	public SBQuery (View view,boolean usecache) {
		this.useCache = usecache;
		this.view = view;
	}
	
	///--------------------------------findViewById------------------------//
	public SBQuery id (int id) {
		if (useCache) {
			//10W次调用测试耗时:2316ms 2323ms 2348ms
			if (mqCache == null) {
				mqCache = new SparseArray<SBQuery>();
			}
			SBQuery mq = mqCache.get(view.hashCode() + id);
			if (mq==null) {
				mq = new SBQuery(view.findViewById(id));
				mqCache.put(view.hashCode() +id, mq);
			}
			return mq;
		} else {
			//10W次调用测试用时:5154ms 4808ms 5270ms
			return id(view.findViewById(id));
		}
	}
	
	public SBQuery id (View view) {
		if (useCache) {
			//10W次调用测试用时:2882ms 3142秒 3024秒
			if (mqCache == null) {
				mqCache = new SparseArray<SBQuery>();
			}
			SBQuery mq = mqCache.get(view.hashCode());
			if (mq==null) {
				mq = new SBQuery(view);
				mqCache.put(view.hashCode(), mq);
			}
			return mq;
		} else {
			//10W次调用测试用时:6467ms 6091ms 6001ms7
			return new SBQuery(view);			
		}
	}
	
	public View getView(){
		return view;
	}
	
	///-------------------------文本控件操作---------------------------///
	public String text() {
		CharSequence result = null;
		if(view instanceof TextView){
			result = ((TextView) view).getText();
		}
		return result==null?"":result.toString();
	}
	
	public String textTrim () {
		String result = text();
		return result==null?"":result.trim();
	}
	
	public SBQuery text (String str) {
		if(view instanceof TextView){
			((TextView) view).setText(str);
		}
		return this;
	}
	
	public SBQuery text (int res) {
		if(view instanceof TextView){
			((TextView) view).setText(res);
		}
		return this;
	}
	
	public SBQuery textColor(int color){
		if(view instanceof TextView){			
			TextView tv = (TextView) view;
			tv.setTextColor(color);
		}
		return this;
	}
	
	///------------------------可见性--------------------------------------///
	public SBQuery visibility(int visibility){
		if(view != null && view.getVisibility() != visibility){
			view.setVisibility(visibility);
		}
		return this;
	}
	
	public int visibility(){
		int vi = View.GONE;
		if(view != null){
			vi =view.getVisibility();
		}
		return vi;
	}
	
	///---------------------------复选，单选等CompoundButton的选中控制---------------------------///
	public SBQuery checked(boolean checked){
		if(view instanceof CompoundButton){
			CompoundButton cb = (CompoundButton) view;
			cb.setChecked(checked);
		}
		return this;
	}
	
	public boolean checked(){
		boolean checked = false;
		if(view instanceof CompoundButton){
			CompoundButton cb = (CompoundButton) view;
			checked = cb.isChecked();
		}
		return checked;
	}
	
	///---------------------------可点击，使能，可聚焦---------------------------///
	public SBQuery clickable(boolean clickable){
		if(view != null){
			view.setClickable(clickable);
		}
		return this;
	}
	
	public SBQuery enabled(boolean enabled){
		if(view != null){
			view.setEnabled(enabled);
		}
		return this;
	}
	
	public SBQuery focusable(boolean focusable){
		if(view != null){
			view.setFocusable(focusable);
		}
		return this;
	}
	
	
	///---------------------------点击事件---------------------------///
	public SBQuery clicked(View.OnClickListener listener){
		if(view != null){						
			view.setOnClickListener(listener);
		}
		return this;
	}
	
	/**
	 * clicked(this, "btn_back");
	 * 会调用this.btn_back();
	 * public void btn_back(){
	 * 
	 * }
	 * @param obj
	 * @param callbackName
	 * @return
	 */
	public SBQuery clicked(final Object obj, final String callbackName){
		if(view != null){						
			view.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					try {
						Class<?> c = obj.getClass();
						Method m = c.getDeclaredMethod(callbackName);
						m.invoke(obj);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
		}
		return this;
	}
	
	public SBQuery click(){
		if(view != null){
			view.performClick();
		}
		return this;
	}
	
	///---------------------------长按事件---------------------------///
	public SBQuery longClick(){
		if(view != null){
			view.performLongClick();
		}
		return this;
	}
	/**
	 * longClick(this, "btn_popup");
	 * 会调用：this.btn_popup();
	 * public boolean btn_popup{
	 * 	return true;
	 * }
	 * @param obj
	 * @param callbackName
	 * @return
	 */
	public SBQuery longClicked(final Object obj, final String callbackName){
		if (view != null) {
			view.setOnLongClickListener(new View.OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					try {
						Class<?> c = obj.getClass();
						Method m = c.getDeclaredMethod(callbackName);
						return (Boolean) m.invoke(obj);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return true;
				}
			});
		}
		return this;
		
	}
	
	public SBQuery longClicked(View.OnLongClickListener listener) {
		if (view != null) {
			view.setOnLongClickListener(listener);
		}
		return this;
	}
	
	///---------------------------ListView的ItemClick事件---------------------------///
	public SBQuery itemClicked(OnItemClickListener listener){
		if(view instanceof AdapterView){
			AdapterView<?> alv = (AdapterView<?>) view;
			alv.setOnItemClickListener(listener);
		}
		return  this;
	}	
	
	@SuppressWarnings({"unchecked", "rawtypes" })
	public SBQuery adapter(Adapter adapter){
		if(view instanceof AdapterView){
			AdapterView av = (AdapterView) view;
			av.setAdapter(adapter);
		}
		return this;
	}
	
	public SBQuery adapter(ExpandableListAdapter adapter){
		if(view instanceof ExpandableListView){
			ExpandableListView av = (ExpandableListView) view;
			av.setAdapter(adapter);
		}
		return  this;
	}
	
	public Adapter adapter(){
		if(view instanceof AdapterView){
			AdapterView av = (AdapterView) view;
			return av.getAdapter();
		}
		return null;
	}
	
	public SBQuery setSelection(int position){
		if(view instanceof AdapterView){		
			AdapterView<?> alv = (AdapterView<?>) view;
			alv.setSelection(position);		
		}
		return this;
	}
	
	public int selection(){
		if(view instanceof AdapterView){		
			AdapterView<?> alv = (AdapterView<?>) view;
			return alv.getSelectedItemPosition();	
		}
		return 0;
	}
	
	///---------------------------点击事件---------------------------///
	
	///---------------------------点击事件---------------------------///
	
	
	///---------------------------复选，单选等CompoundButton的选中控制---------------------------///
	
	
	///---------------------------背景设置---------------------------///
	public SBQuery background(int id){
		if(view != null){
			if(id != 0){
				view.setBackgroundResource(id);
			}else{
				view.setBackgroundDrawable(null);
			}
		}
		return this;
	}
	
	public SBQuery background(Drawable draw){
		if(view != null){
			view.setBackgroundDrawable(draw);
		}
		return this;
	}
	
	///--------------------------图片资源操作---------------------------------///
	public SBQuery image (int resid) {
		if(view instanceof ImageView){
			ImageView iv = (ImageView)view;
			if(resid == 0){
				iv.setImageBitmap(null);
			}else{				
				iv.setImageResource(resid);
			}
		}
		return this;
	}
	
	public SBQuery image(Drawable drawable){
		if(view instanceof ImageView){
			ImageView iv = (ImageView) view;
			iv.setImageDrawable(drawable);
		}
		return this;
	}
	
	public SBQuery image(Bitmap bm){
		if(view instanceof ImageView){
			ImageView iv = (ImageView) view;
			iv.setImageBitmap(bm);
		}
		return this;
	}
	
	public SBQuery image(File file){
		Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
		return image(bm);
	}
	
	/**
	 * 获取view的bitmap数据 new mquery(activity).getViewbitmap >>截图
	 * 
	 * @author ping
	 * @create 2014-4-23 下午2:16:48
	 * @return
	 */
	public Bitmap getViewbitmap() {
		Bitmap bitmap = null;
		try {
			int width = view.getWidth();
			int height = view.getHeight();
			if (width != 0 && height != 0) {
				bitmap = Bitmap.createBitmap(width, height,Bitmap.Config.ARGB_8888);
				Canvas canvas = new Canvas(bitmap);
				view.layout(0, 0, width, height);
				view.draw(canvas);
			}
		} catch (Exception e) {
			bitmap = null;
			e.getStackTrace();
		}
		return bitmap;
	}
	
	
	
	
	///--------------------------网络图片操作---------------------------------///
//	public SBQuery image (String url) {
//		if(view instanceof ImageView){
//			//NetAccess.image((ImageView)view, url);
//			ImageMgmr.showImage((ImageView)view, 
//					url, 
//					0, 
//					0, 
//					null, 
//					0, 
//					0, 
//					true, //是否使用sd卡缓存
//					true);//是否使用内存缓存
//		}
//		return this;
//	}
//	
//	public SBQuery image (String url,int loadingimg,int errorimg) {
//		if(view instanceof ImageView){
//			//NetAccess.image((ImageView)view, url, loadingimg, errorimg);
//			ImageMgmr.showImage((ImageView)view, 
//					url, 
//					loadingimg, 
//					errorimg, 
//					null, 
//					0, 
//					0, 
//					true, //是否使用sd卡缓存
//					true);//是否使用内存缓存
//		}
//		return this;
//	}
//	
//	public SBQuery image (String url,int loadingimg,int errorimg,int width, int height) {
//		if(view instanceof ImageView){
//			ImageMgmr.showImage((ImageView)view, 
//					url, 
//					loadingimg, 
//					errorimg, 
//					null, 
//					width, 
//					height, 
//					true, //是否使用sd卡缓存
//					true);//是否使用内存缓存
//		}
//		return this;
//	}
//	
//	public SBQuery image (String url,int loadingimg,int errorimg,int width, int height, OnImageDownloadCallback callback) {
//		if(view instanceof ImageView){
//			ImageMgmr.showImage((ImageView)view, 
//					url, 
//					loadingimg, 
//					errorimg, 
//					callback, 
//					width, 
//					height, 
//					true, //是否使用sd卡缓存
//					true);//是否使用内存缓存
//		}
//		return this;
//	}
	
	
	//----------------------------创建控件------------------------//
	
}
