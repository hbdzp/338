package com.mstar.tv.tvplayer.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.TextView;

import com.kguan.mtvplay.tvapi.K_Constants;
import com.kguan.mtvplay.tvapi.K_TvCommonManager;
import com.kguan.mtvplay.tvapi.K_TvManager;

public class LocalUpdateSoftwareActivity extends Activity {
	private static TextView butt_start;
	private TextView butt_cancel;
	private static TextView text_update_info;
	private boolean bUSBUpgradeFlag;
	private static Context mContext;
	private static  String[] upgradeStatus = {};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updatesoftware_dialog);
		mContext = this;
		bUSBUpgradeFlag = true;
		text_update_info = (TextView)findViewById(R.id.textview_cha_update_info);
		butt_start = (TextView)findViewById(R.id.textview_menu_update_yes);
		butt_cancel = (TextView)findViewById(R.id.textview_menu_update_no);
		upgradeStatus = this.getResources().getStringArray(
				R.array.str_arr_update_status);
		butt_start.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				// TODO Auto-generated method stub
				if (arg2.getAction() == KeyEvent.ACTION_DOWN) {
					switch (arg1) {
					case KeyEvent.KEYCODE_TV_INPUT:
						ok_update();
						return true;
					case KeyEvent.KEYCODE_VOLUME_DOWN:
					case KeyEvent.KEYCODE_VOLUME_UP:
						butt_cancel.requestFocus();
						return true;
					default:
						break;
					}
				}
				return false;
					}
		});
		butt_cancel.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				if (arg2.getAction() == KeyEvent.ACTION_DOWN) {
					switch (arg1) {
					case KeyEvent.KEYCODE_TV_INPUT:
						cancel_update();
						return true;
					case KeyEvent.KEYCODE_VOLUME_DOWN:
					case KeyEvent.KEYCODE_VOLUME_UP:
						butt_start.requestFocus();
						return true;
					default:
						break;
					}
				}
				return false;
			}
		});
		butt_start.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ok_update();
			}

		});
		butt_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				cancel_update();
			}
		});

	}
	public enum EnumUpgradeStatus {
		// status fail
		E_UPGRADE_FAIL,
		// status success
		E_UPGRADE_SUCCESS,
		// file not found
		E_UPGRADE_FILE_NOT_FOUND,
	}

	public static Handler getHandler() {
		return handler;
	}

	private static ProgressDialog progressDialog = null;
	protected static Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == USBUpgradeThread.UPGRATE_START) {
				progressDialog = getProgressDialog();
				progressDialog.show();
			} else if (msg.what == USBUpgradeThread.UPGRATE_END_SUCCESS) {
				progressDialog.dismiss();
				text_update_info.setText(upgradeStatus[1]);
			} else if (msg.what == USBUpgradeThread.UPGRATE_END_SUCCESS_MAIN) {
				progressDialog.dismiss();
				text_update_info.setText(mContext.getResources().getString(
                            R.string.str_reboot_wait));
				//升级时将上电模式修改为上电开机 Nathan.liao 20131104
					K_TvManager.getInstance()
					.K_setEnvironmentPowerMode(K_Constants.E_ACON_POWERON_DIRECT);
				K_TvCommonManager.getInstance().K_rebootSystem("reboot");
			} else if (msg.what == USBUpgradeThread.UPGRATE_END_FILE_NOT_FOUND) {
				progressDialog.dismiss();
				text_update_info.setText(upgradeStatus[2]);
				butt_start.setVisibility(View.INVISIBLE);
			} else {
				progressDialog.dismiss();
				text_update_info.setText(upgradeStatus[0]);
			}
		};
	};
	private static String port;
	private static String exhub;

	private static ProgressDialog getProgressDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(mContext);
			progressDialog.setMessage(mContext.getResources().getString(
					R.string.str_update_wait));
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(true);
		}
		return progressDialog;
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mContext = null;
		progressDialog = null;
	}
	static int UpgradeMainFun() {
		int ret = 0;
		// TODO UpgradeMain function
		String mainpath;
		String bootpath;
		mainpath = FindFileOnUSB("MstarUpgrade.bin");// should not change this
		bootpath = FindFileOnUSB("MbootUpgrade.bin");// file name
		Log.v("UpgradeMainFun", "mainpath=" + mainpath + "==bootpath="
				+ bootpath);
		if ("".equals(mainpath) && "" .equals(bootpath)) {
			ret = EnumUpgradeStatus.E_UPGRADE_FILE_NOT_FOUND.ordinal();
		} else {
				if (K_TvManager.getInstance().K_setEnvironment("upgrade_mode",
						"usb")) {
					String path = ("".equals(mainpath)?bootpath:mainpath);
					if (!changeToPhyPath(path)) {
						Log.d("UpgradeMainFun:", "changeToPhyPath Failed!");
						ret = EnumUpgradeStatus.E_UPGRADE_FAIL.ordinal();
					}

					ret = EnumUpgradeStatus.E_UPGRADE_SUCCESS.ordinal();
				} else {
					Log.d("UpgradeMainFun:", "setEnvironment Failed!");
					ret = EnumUpgradeStatus.E_UPGRADE_FAIL.ordinal();
				}
		}

		return ret;
	}

	private static String FindFileOnUSB(String filename) {
		// TODO Find File On USB function
		String filepath = "";
		// Hisa 2015.09.01 usb升级识别MstarUpgrade.bin start
		//File usbroot = new File("/mnt/usb/");
		File usbroot = new File("/mnt/factoryusb/");
		
		File targetfile;
		if (usbroot != null && usbroot.exists()) {
			File[] usbitems = usbroot.listFiles();
			int sdx = 0;
			for (; sdx < usbitems.length; sdx++) {
				/*
				if (usbitems[sdx].isDirectory()) {
					targetfile = new File(usbitems[sdx].getPath() + "/"
							+ filename);
					if (targetfile != null && targetfile.exists()) {
						filepath = usbitems[sdx].getPath() + "/" + filename;
						break;
					}
				}
				*/
				
				targetfile = new File("/mnt/factoryusb/"+ filename);
				if (targetfile != null && targetfile.exists()) {
					filepath = "/mnt/factoryusb/" + filename;
					break;
				}
			}
		}
		// Hisa 2015.09.01 usb升级识别MstarUpgrade.bin end
		return filepath;
	}

	static boolean changeToPhyPath(String filepath) {
		boolean ret = false;
		Log.d("changeToPhyPort:", "filepath = " + filepath);
		String sdindex;
		// Hisa 2015.09.01 usb升级识别MstarUpgrade.bin start
		sdindex = filepath.substring(5, 15);// /mnt/factoryusb/ ==>"factoryusb"
		//sdindex = filepath.substring(9, 13); // "/mnt/usb/sdx1" ==> "sdx1"
		// Hisa 2015.09.01 usb升级识别MstarUpgrade.bin end
		Log.d("changeToPhyPort:", "sdindex = " + sdindex);
		ret = getPhyPortInfo(sdindex);
		Log.d("changeToPhyPort:", "port = " + port);
		Log.d("changeToPhyPort:", "exhub = " + exhub);
		if (ret) {
				ret = K_TvManager.getInstance().K_setEnvironment(
						"usb_upgrade_port", port);
				ret = K_TvManager.getInstance().K_setEnvironment(
						"usb_upgrade_exhub_port ", exhub);
			if (!ret) {
				Log.d("changeToPhyPort:", "setEnvironment failed!");
			}
		} else {
			Log.d("changeToPhyPort:", "getPhyPortInfo failed!");
		}

		return ret;
	}

	static boolean getPhyPortInfo(String sdx) {
		boolean ret = false;
		FileReader filereader = null;
		try {
			filereader = new FileReader("/proc/partitions");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (filereader == null) {
			Log.d("getPhyPortInfo:", "Can't find the file /proc/partitions");
		}
		BufferedReader bufferedreader = new BufferedReader(filereader);
		String lineString = bufferedreader.toString();
		while (lineString != null) {
			try {
				lineString = bufferedreader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Log.d("sdx = ", sdx);
			if (lineString == null) {
				Log.d("getPhyPortInfo:", "Can't find " + sdx + "on partitions");
				break;
			}

			if (lineString.indexOf(sdx) >= 0) {
				if (lineString.indexOf("s=") == -1
						|| lineString.indexOf("p=") == -1
						|| lineString.indexOf("b=") == -1) {
					Log.d("getPhyPortInfo:",
							"Can't get the right infomation of " + sdx);
					break;
				}

				char sss = lineString.charAt(lineString.indexOf("s=") + 2);
				char ppp = lineString.charAt(lineString.indexOf("p=") + 2);
				char bbb = lineString.charAt(lineString.indexOf("b=") + 2);
				port = "" + bbb;
				exhub = "" + ppp;
				if (sss == '3') // not hub
				{
					exhub = "0";
					Log.d("getPhyPortInfo:", "No hub on port" + port);
				}
				ret = true;
				break;
			}
		}
		return ret;
	}
	private boolean CheckUsbIsExist() {
		// TODO Auto-generated method stub
		boolean ret = false;
		UsbManager usbManager = (UsbManager) LocalUpdateSoftwareActivity.this
				.getSystemService(Context.USB_SERVICE);
		if (usbManager.getDeviceList().isEmpty() == false) {
			ret = true;
		}
		return ret;
	}

	public void ok_update() {
		if (bUSBUpgradeFlag) {
			if (CheckUsbIsExist() == true) {
				USBUpgradeThread.UpgradeMain();
				bUSBUpgradeFlag = false;
			} else {
				text_update_info.setText(upgradeStatus[3]);
			}
		} else {
			Intent intent = new Intent();
			intent.setClass(LocalUpdateSoftwareActivity.this,
					MainMenuActivity.class);
			intent.putExtra("currentPage", 3);
			MainMenuActivity.getInstance().setSettingSelectStatus(0x11000000);
			startActivity(intent);
			finish();
		}
	}
	public void cancel_update() {
		Intent intent = new Intent();
		intent.setClass(LocalUpdateSoftwareActivity.this,
				MainMenuActivity.class);
		intent.putExtra("currentPage", 3);
		MainMenuActivity.getInstance().setSettingSelectStatus(0x11000000);
		startActivity(intent);
		finish();
	}
}
