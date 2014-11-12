package com.sb.framework.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.sb.framework.C;
import com.sb.framework.utils.MD5Utils;

public class DiskCacheUseFile implements DiskCacheStrategy{

	File cacheDir = null;
	public DiskCacheUseFile(String cacheDirPath){
		cacheDir = new File(cacheDirPath);
		if(!cacheDir.exists()){
			cacheDir.mkdirs();
		}
	}
	public DiskCacheUseFile(){
		this(C.SD_ROOT + "requestCache/");
	}
	
	
	@Override
	public void save(String url, String content) {
		String filename = MD5Utils.digest(url);
		File out = new File(cacheDir, filename);
		try {
			FileOutputStream fos = new FileOutputStream(out);
			fos.write(content.getBytes("utf-8"));
			fos.flush();
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public String get(String url) {
		String filename = MD5Utils.digest(url);
		File f = new File(cacheDir, filename);
		if(!f.exists()){
			return "";
		}
		try {
			byte[] buffer = new byte[(int) f.length()];
			FileInputStream fos = new FileInputStream(f);
			fos.read(buffer);
			fos.close();
			return new String(buffer, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
}
