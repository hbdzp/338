package com.mstar.tv.tvplayer.ktc.factoryremote;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.kguan.mtvplay.tvapi.K_Constants;
import com.kguan.mtvplay.tvapi.K_EthernetManager;
import com.kguan.mtvplay.tvapi.K_TvFactoryManager;
import com.kguan.mtvplay.tvapi.K_TvManager;
import com.kguan.mtvplay.tvapi.K_TvPictureManager;
import com.kguan.mtvplay.tvapi.vo.K_VOClasses.K_EthernetDevInfo;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.util.PropHelp;
//nathan.liao 2015.03.19 add for reset channel ANR error start
//nathan.liao 2015.03.19 add for reset channel ANR error end

public class FactoryRemoter {

	private static Activity mActivity;

	public FactoryRemoter() {
		// TODO Auto-generated constructor stub
	}

	public static enum UpgradeInfo {
		UPGRADE_OK, UPGRADE_DATA_OVER, UPGRADE_NO_FILE, UPGRADE_READ_FILE_FAIL, UPGRADE_RW_SPI_FAIL,
	}

	public static enum UpgradeMode {
		UPGRADE_MODE_NET, UPGRADE_MODE_USB,
	}

	public static boolean gotoShowMACAddress(Activity from, int keyCode) {
		if (KeyEvent.KEYCODE_KTC_MAC == keyCode) {
			K_EthernetManager mEtherneManager = K_EthernetManager.getInstance();
			K_EthernetDevInfo devInfo = mEtherneManager.K_getSavedConfig();
			String str_mac = "";
			 if (devInfo == null) {
				 str_mac =  "";
		        } else {
		        	str_mac =  devInfo.getMacAddress(devInfo.getIfName());
		        }
			new AlertDialog.Builder(from).setTitle(from.getResources().getString(R.string.str_factory_wireMac))
					.setMessage(str_mac).setNegativeButton(from.getResources().getString(R.string.str_factory_wireMac_close), null).show();
			return true;
		}
		return false;
	}

	public static boolean gotoFactoryMenu(Activity from, int keyCode) {
		if (KeyEvent.KEYCODE_KTC_FAC == keyCode) {
			Intent intent = new Intent(
					"ktc.intent.action.softinfomenu");
			from.startActivity(intent);
			return true;
		}
		return false;
	}

	public static boolean writeMacAddress(Activity from, int keyCode) {
		if (KeyEvent.KEYCODE_KTC_WMAC == keyCode) {
			Intent intent = new Intent(
					"com.mstar.tvsetting.ui.intent.action.WirteMacActivity");
			from.startActivity(intent);
			return true;
		}
		return false;
	}

	public static boolean switchAgeMode(Activity from, int keyCode) {
		if (KeyEvent.KEYCODE_KTC_BI == keyCode) {
			if (getagemode(from) == 0) {
				K_TvFactoryManager.getInstance().K_setPowerOnMode(K_Constants.E_ACON_POWERON_DIRECT);
				setAgemode(from,(short) 1);
				Toast.makeText(from, from.getResources().getString(R.string.str_factory_agemode_open), Toast.LENGTH_LONG).show();
			} else {
				setAgemode(from,(short) 0);
				Toast.makeText(from, from.getResources().getString(R.string.str_factory_agemode_close), Toast.LENGTH_LONG).show();
			}

			return true;
		}
		return false;
	}

	public static boolean adcAutoAdjust(Activity from, int keyCode) {
		if (KeyEvent.KEYCODE_KTC_ADC == keyCode) {
			Intent intent = new Intent(
					"mstar.tvsetting.factory.intent.action.ADCAdjustActivity");
			from.startActivity(intent);
			return true;
		}
		return false;
	}
	public static boolean colorTempAdjust(Activity from,int keyCode)
	{
		if(KeyEvent.KEYCODE_KTC_COLOR == keyCode)
		{
			int curIndex = K_TvPictureManager.getInstance().K_getColorTemprature();
			if(curIndex == 2)
			{
				curIndex = 0;
			}else
			{
				curIndex++;
			}
			K_TvPictureManager.getInstance().K_setColorTemprature(curIndex);
			Toast.makeText(from, (from.getResources().getStringArray(R.array.str_arr_pic_colortemperature_vals))[curIndex], Toast.LENGTH_SHORT).show();
			return true;
		}
		return false;
	}
	public static boolean factoryReset(Activity from,int keyCode)
	{
		if(KeyEvent.KEYCODE_KTC_REST3 == keyCode)
		{
			try {
                Intent intent = new Intent("ktc.tvsetting.factory.factoryMenu"); //mstar.tvsetting.factory.intent.action.MainmenuActivity
                intent.putExtra("ktc.factoryremote.flag", true);
                from.startActivity(intent);
                from.finish();
            } catch (ActivityNotFoundException e){
            }
			return true;
		}
		return false;
	}
	public static boolean channelPreset(Activity from,int keyCode)
	{
		if(KeyEvent.KEYCODE_KTC_REST2 == keyCode)
		{
			mActivity = from;
			new ResetChannelTask().execute();
			//nathan.liao 2015.03.19 add for reset channel ANR error end
			return true;
		}
		return false;
	}
	//nathan.liao 2015.03.19 add for reset channel ANR error start  
	static class ResetChannelTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub		
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
				if(PropHelp.newInstance().hasDtmb())
				{
					K_TvManager.getInstance().K_setTvosCommonCommand("SetResetATVDTVChannel");
				}
				else
				{
					K_TvManager.getInstance().K_setTvosCommonCommand("SetResetATVChannel");
				}
				//Toast.makeText(RootActivity.this, RootActivity.this.getResources().getString(R.string.str_factory_channelpreset_suc), Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			Toast.makeText(mActivity, mActivity.getResources().getString(R.string.str_factory_channelpreset_suc), Toast.LENGTH_LONG).show();
			super.onPostExecute(result);
		}
	}

	//nathan.liao 2015.03.19 add for reset channel ANR error end	
/*	public static boolean setWireIpAddress(Activity from,int keyCode)
	{
		if(KeyEvent.KEYCODE_MEDIA_STOP == keyCode)
		{
			Intent intent = new Intent("mstar.tvsetting.ui.intent.action.FactoryConnectWire");
			from.startActivity(intent);
			return true;
		}
		return false;
	}*/
	public static boolean showVGADDCInfo(Activity from,int keyCode)
	{
		if(KeyEvent.KEYCODE_KTC_DDC == keyCode)
		{
			Intent intent = new Intent("mstar.tvsetting.factory.intent.action.ShowVGADDCActivity");
			from.startActivity(intent);
			return true;
		}
		return false;
	}
	
	//====================age mode start
	public static short getagemode(Activity mActivity)  {
		short bAgeModeFlag = 0;
		Cursor cursor = mActivity.getContentResolver().query(
				Uri.parse("content://mstar.tv.usersetting/systemsetting"),
				null, null, null, null);
		if (cursor.moveToFirst()) {
			bAgeModeFlag = (short)cursor.getInt(cursor.getColumnIndex("bAgeModeFlag"));
		}
		cursor.close();
		return bAgeModeFlag;
	}
	

	public static void setAgemode(Activity mActivity,short bAgeModeFlag) {
		int ret = -1;
		ContentValues vals = new ContentValues();
		vals.put("bAgeModeFlag", bAgeModeFlag);
		try {
			ret = mActivity.getContentResolver().update(
					Uri.parse("content://mstar.tv.usersetting/systemsetting"),
					vals, null, null);
		} catch (SQLException e) {
		}
		if (ret == -1) {
			System.out.println("update tbl_SystemSetting ignored");
		}
		
			K_TvManager.getInstance().K_setDatabaseDirtyByApplication((short)0x19);
		
		try 
		{
			K_TvManager.getInstance().K_setTvosCommonCommand("SetFactoryEnergyEfficiency");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//====================age mode end
    /**
     * Copy data from a source stream to destFile.
     * Return true if succeed, return false if failed.
     */
    private static  boolean copyToFile(InputStream inputStream, File destFile) {
        try {
            if (destFile.exists()) {
                destFile.delete();
            }
            FileOutputStream out = new FileOutputStream(destFile);
            try {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) >= 0) {
                    Log.d(" out.write(buffer, 0, bytesRead);", " out.write(buffer, 0, bytesRead);");
                    out.write(buffer, 0, bytesRead);
                }
            } finally {
                out.flush();
                try {
                    out.getFD().sync();
                } catch (IOException e) {
                }
                out.close();
            }
            return true;
        } catch (IOException e) {
            Log.d("copyToFile(InputStream inputStream, File destFile)", e.getMessage());
            return false;
        }
    }
    // copy a file from srcFile to destFile, return true if succeed, return
    // false if fail
    private static  boolean copyFile(File srcFile, File destFile) {
        boolean result = false;
        try {
            InputStream in = new FileInputStream(srcFile);
            try {
                result = copyToFile(in, destFile);
            } finally  {
                in.close();
            }
        } catch (IOException e) {
            Log.d("copyFile(File srcFile, File destFile)", e.getMessage());
            result = false;
        }
        chmodFile(destFile);
        return result;
    }
    private static void chmodFile(File destFile)
    {
        try {
            String command = "chmod 666 " + destFile.getAbsolutePath();
            Log.i("zyl", "command = " + command);
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec(command);
           } catch (IOException e) {
            Log.i("zyl","chmod fail!!!!");
            e.printStackTrace();
           }
    }
}
