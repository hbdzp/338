package com.mstar.tv.tvplayer.ui.hotelmenu;

import android.app.Activity;
import android.view.View;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.kguan.mtvplay.tvapi.K_TvManager;
import com.mstar.tv.tvplayer.ui.R;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

public class LogoResetActivity extends Activity 
implements View.OnClickListener
	 {
	protected TextView logosetyes;
	protected TextView logosetno;
	boolean lagerflag = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_logo);
		TextView  logosetyes =(TextView)findViewById(R.id.textview_logo_reset_yes);
		TextView  logosetno =(TextView)findViewById(R.id.textview_logo_reset_no);
		
       
	    logosetyes.setOnClickListener(this);
	    logosetno.setOnClickListener(this);
		
	
	}
	        public void onClick(View v) {
	            switch (v.getId()) {
			 case R.id.textview_logo_reset_yes:
			  updateLogo();
			  finish();
				 break;
			case R.id.textview_logo_reset_no:
				Intent intent = new Intent();
				intent.setClass(LogoResetActivity.this,
						LogoSetViewActivity.class);
				
				
				startActivity(intent);
				finish();
				break;
			default:break;
		}
	}
	public void updateLogo() {
		boolean ret = false;
		String destPath = new String();
		String defaultFilePath = new String();
		File srcFile;
		File destFile;
		File defaultFile;
		destPath = "/tvconfig/boot0.jpg";
		defaultFilePath="/tvcustomer/Customer/boot.jpg";
		Log.i("OtherOption", "....1....destPath is " + destPath + "..........");
		srcFile = new File("/mnt/usb/sda1/boot0.jpg");
		destFile = new File(destPath);
		defaultFile = new File(defaultFilePath);
		if(srcFile.length()>100*1024)
		{
			lagerflag=true;
			Log.i("OtherOption", "~src file not dddd~");
		}
		else
		{
			lagerflag=false;
			Log.i("OtherOption", "~src file not 66666~");
		}
		if(lagerflag==false)
		{
		try {
			if(!defaultFile.exists())
			copyFile(destFile, defaultFile);
			if (copyFile(srcFile, destFile)) {
				ret = true;
			}
			 chmodFile(destFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		else
		{
		ret = false;
		}
		if (ret)
		
		{
			Toast.makeText(getBaseContext(), R.string.str_logo_reset_result_info, Toast.LENGTH_SHORT).show();
			try{
				K_TvManager.getInstance().K_setEnvironment("db_table", "0");
			}
			catch (Exception e){
				
		                                  }
		}
		else
		{
			if(lagerflag==true)
			{
				lagerflag=false;
			Toast.makeText(getBaseContext(), R.string.str_logo_reset_mistake_info_limit, Toast.LENGTH_SHORT).show();
			}
			else
				Toast.makeText(getBaseContext(), R.string.str_logo_reset_mistake_info_no_file, Toast.LENGTH_SHORT).show();
		}
	}
	public static void chmodFile(File destFile) {
		              try {
		               String command = "chmod 644 " + destFile.getAbsolutePath();
	                   Log.i("zyl", "command = " + command);
	                   Runtime runtime = Runtime.getRuntime();
                    Process proc = runtime.exec(command);
	            } catch (IOException e) {
	                   Log.i("zyl", "chmod fail!!!!");
	                   e.printStackTrace();
	              }
	      }
	public static  boolean copyFile(File srcFile, File destFile) throws IOException {
		if (!srcFile.exists()) {
			Log.i("OtherOption", "~src file not exits~");
			return false;
		}
		
		if (!srcFile.isFile()) {
			Log.i("OtherOption", "~src file is not a file~");
			return false;
		}
		if (!srcFile.canRead()) {
			Log.i("OtherOption", "~src file can  not read~");
			return false;
		}

		if (!destFile.exists()) {
			Log.i("OtherOption", "~dest file not exits~");
			if (!destFile.getParentFile().exists()) {
				destFile.getParentFile().mkdirs();
			}
			destFile.createNewFile();
		}
		if (!destFile.canRead()) {
			Log.i("OtherOption", "~dest file can  not read~");
			return false;
		}

		Log.i("OtherOption", "~src file OK~");
		FileInputStream input = new FileInputStream(srcFile);
		BufferedInputStream inBuff = new BufferedInputStream(input);
		FileOutputStream output = new FileOutputStream(destFile);
		BufferedOutputStream outBuff = new BufferedOutputStream(output);
		byte[] b = new byte[1024 * 2000];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		outBuff.flush();
		inBuff.close();
		outBuff.close();
		output.close();
		input.close();
		// chmodFile(destFile);
		Log.i("OtherOption", "~chmod dest file OK~");
		return true;
	}
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		 }
	

	
}
