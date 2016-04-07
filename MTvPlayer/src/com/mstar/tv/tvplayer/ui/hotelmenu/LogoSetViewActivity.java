package com.mstar.tv.tvplayer.ui.hotelmenu;
import java.io.File;
import java.io.IOException;

import com.kguan.mtvplay.tvapi.K_TvManager;
import com.mstar.tv.tvplayer.ui.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
public class LogoSetViewActivity extends Activity 
implements View.OnClickListener 
{
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activety_logosetview);
		
		findViewById(R.id.linearlayout_logosetview).setOnClickListener(this);
		findViewById(R.id.linearlayout_logocheange).setOnClickListener(this);
		findViewById(R.id.linearlayout_logosetchacel).setOnClickListener(this);
	}
	
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.linearlayout_logosetview: 
				selectlogochangeactivity();
				break;
			case R.id.linearlayout_logocheange:
				if( restorelogo()){
					Toast.makeText(getApplicationContext(), getString(R.string.str_logo_restore_success), 2000).show();
					finish();
				}
				break;
			
			case R.id.linearlayout_logosetchacel:
				jumptomainmenu();
				break;
			default:break;
		}
	}
	
	
	
	private void selectlogochangeactivity()
	{
		Intent intent = new Intent();
		intent.setClass(LogoSetViewActivity.this,
				LogoResetActivity.class);
		
		
		startActivity(intent);
		finish();
	}
	private void jumptomainmenu()
	{
		Intent intent = new Intent();
		intent.setClass(LogoSetViewActivity.this,
				HotelMenuActivity.class);
		
		
		startActivity(intent);
		finish();
	}
	private boolean restorelogo()
	{
		String destPath = new String();
		String defaultFilePath = new String();
		File destFile;
		File defaultFile;
		destPath = "/tvconfig/boot0.jpg";
		defaultFilePath="/tvcustomer/Customer/boot.jpg";
		Log.i("OtherOption", "....1....destPath is " + destPath + "..........");
		destFile = new File(destPath);
		defaultFile = new File(defaultFilePath);
		if(!defaultFile.exists())
		{
			return false;
		}
		try {
			if (LogoResetActivity.copyFile(defaultFile, destFile)) {
			}
			LogoResetActivity.chmodFile(destFile);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		try{
			K_TvManager.getInstance().K_setEnvironment("db_table", "0");
			}
			catch (Exception e){
				return false;
			}
		return true;
	}
}
