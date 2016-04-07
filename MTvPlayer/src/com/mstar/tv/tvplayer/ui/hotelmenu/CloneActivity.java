package com.mstar.tv.tvplayer.ui.hotelmenu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.kguan.mtvplay.tvapi.K_TvCommonManager;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.util.Command;
import com.mstar.util.PropHelp;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class CloneActivity extends Activity 
		implements View.OnClickListener {
	
	private static final int IO_READ_DATA = 0;
	private static final int IO_WRITE_DATA = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clone);
		
		findViewById(R.id.linearlayout_backup).setOnClickListener(this);
		findViewById(R.id.linearlayout_restore).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.linearlayout_backup: 
				backup();
				break;
			case R.id.linearlayout_restore:
				restore();
				break;
			default:break;
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	private void backup() {
		if (IO(IO_WRITE_DATA)) {
			Toast.makeText(this, R.string.backup_successed, Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, R.string.backup_failed, Toast.LENGTH_SHORT).show();
		}
	}
	
	private void restore() {
		if (IO(IO_READ_DATA)) {
			Toast.makeText(this, R.string.restore_successed, Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, R.string.restore_failed, Toast.LENGTH_SHORT).show();
		}
	}
	
	private boolean IO(int state) {
		int i = 0;
		File USBFile[] = new File[3];
		File TVFile[] = new File[3];
		// Hisa 2015.09.01 usb升级识别MstarUpgrade.bin start
		USBFile[0] = new File("/mnt/factoryusb/atv_cmdb.bin");
		USBFile[1] = new File("/mnt/factoryusb/user_setting.db");
		USBFile[2] = new File("/mnt/factoryusb/customer.db");
		// Hisa 2015.09.01 usb升级识别MstarUpgrade.bin end
		TVFile[0] = new File("/tvdatabase/Database/atv_cmdb.bin");
		TVFile[1] = new File("/tvdatabase/Database/user_setting.db");
		TVFile[2] = new File("/tvdatabase/Database/customer.db");
		
/*		File USBFile_DTMB[] = new File[6];
		File TVFile_DTMB[] = new File[6];*/
		File USBFile_DTMB[] = new File[4];
		File TVFile_DTMB[] = new File[4];
		// Hisa 2015.09.01 usb升级识别MstarUpgrade.bin start
		USBFile_DTMB[0] = new File("/mnt/factoryusb/atv_cmdb.bin");
		USBFile_DTMB[1] = new File("/mnt/factoryusb/user_setting.db");
		USBFile_DTMB[2] = new File("/mnt/factoryusb/customer.db");
		USBFile_DTMB[3] = new File("/mnt/factoryusb/dtv_cmdb_0.bin");
		// Hisa 2015.09.01 usb升级识别MstarUpgrade.bin end
/*		USBFile_DTMB[4] = new File("/mnt/usb/sda1/dtv_cmdb_1.bin");
		USBFile_DTMB[5] = new File("/mnt/usb/sda1/dtv_cmdb_2.bin");*/
		TVFile_DTMB[0] = new File("/tvdatabase/Database/atv_cmdb.bin");
		TVFile_DTMB[1] = new File("/tvdatabase/Database/user_setting.db");
		TVFile_DTMB[2] = new File("/tvdatabase/Database/customer.db");
		TVFile_DTMB[3] = new File("/tvdatabase/Database/dtv_cmdb_0.bin");
/*		TVFile_DTMB[4] = new File("/tvdatabase/Database/dtv_cmdb_1.bin");
		TVFile_DTMB[5] = new File("/tvdatabase/Database/dtv_cmdb_2.bin");*/
		// Uдļ
		if (state == IO_WRITE_DATA) {
//			if (null == usbState.getUsbPath()) {
//				return false;
//			}
			if (PropHelp.newInstance().hasDtmb()) {
				for (i = 0; i < 4; i++) {
					Command.cp(TVFile_DTMB[i].getAbsolutePath(),
							USBFile_DTMB[i].getAbsolutePath());
					Command.sync();
				}

				for (i = 0; i < 4; i++) {
					if (!USBFile_DTMB[i].exists()) {
						System.out.println("wen jian bu cun zai !!!!!");
						return false;
					}
				}
			} else {
			for (i = 0; i < 3; i++) {
				Command.cp(TVFile[i].getAbsolutePath(),
						USBFile[i].getAbsolutePath());
				Command.sync();
			}
			
			for (i = 0; i < 3; i++) {
				if (!USBFile[i].exists()) {
					System.out.println("wen jian bu cun zai !!!!!");
					return false;
				}
			}
			}
		// U̶ļ
		} else if (state == IO_READ_DATA) {
			if(PropHelp.newInstance().hasDtmb())
			{
				for (i = 0; i < 4; i++) {
					if (!USBFile_DTMB[i].exists()) {
						System.out.println("wen jian bu cun zai !!!!!");
						return false;
					}
				}
				try {
					for (i = 0; i < 4; i++) {
						FileOutputStream fos = new FileOutputStream(TVFile_DTMB[i]);
						FileInputStream fis = new FileInputStream(USBFile_DTMB[i]);
						byte[] bt = new byte[fis.available()];
						fis.read(bt);
						fos.write(bt);
						fos.close();
						fis.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
			else
			{
			for (i = 0; i < 3; i++) {
				if (!USBFile[i].exists()) {
					System.out.println("wen jian bu cun zai !!!!!");
					return false;
				}
			}
			try {
				for (i = 0; i < 3; i++) {
					FileOutputStream fos = new FileOutputStream(TVFile[i]);
					FileInputStream fis = new FileInputStream(USBFile[i]);
					byte[] bt = new byte[fis.available()];
					fis.read(bt);
					fos.write(bt);
					fos.close();
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			}

			K_TvCommonManager.getInstance().K_rebootSystem("reboot");
		}
		return true;
	}
}
