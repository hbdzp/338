package com.mstar.tv.tvplayer.ktc.factoryremote;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.kguan.mtvplay.tvapi.K_TvManager;
import com.mstar.tv.tvplayer.ktc.factoryremote.FactoryRemoter.UpgradeInfo;
import com.mstar.tv.tvplayer.ktc.factoryremote.FactoryRemoter.UpgradeMode;
import com.mstar.tv.tvplayer.ui.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;

public class WirteMacActivity extends Activity {
	private ProgressDialog progressDialog = null;
	public static final int MACADRR_DATA_LEN = 6;
	public static final int SYSTEM_BANK_SIZE = 0x10000;
	public static final int FLASH_BANK_ADRR = 0x1D;//0x0D;
	public static final int FLASH_MACADRR_ADRR_START = 0x00;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		USB_Write_MACaddr();
		super.onCreate(arg0);
	}
	
	public void USB_Write_MACaddr() {
		WritemacTask task = new WritemacTask();
		task.execute();
	}
	
	public  void Display(UpgradeInfo Info) {
		AlertDialog.Builder builder = new Builder(WirteMacActivity.this);
		builder.setTitle(getResources().getString(R.string.str_factory_writeMacAddress));
		builder.setPositiveButton(getResources().getString(R.string.str_root_alert_dialog_confirm), new OnClickListener() {
			
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				WirteMacActivity.this.finish();
			}
		});
		switch (Info) {
		case UPGRADE_OK:
			builder.setMessage(getResources().getString(R.string.str_factory_writeMacSuc));
			break;
		case UPGRADE_NO_FILE:
			builder.setMessage(getResources().getString(R.string.str_factory_noMacfile));
			break;
		case UPGRADE_READ_FILE_FAIL:
			builder.setMessage(getResources().getString(R.string.str_factory_readFileError));
			break;
		case UPGRADE_DATA_OVER:
			builder.setMessage(getResources().getString(R.string.str_factory_dataOverRetangle));
			break;
		}
		builder.create().show();

	}
	
	public  UpgradeInfo Write_MACaddr(UpgradeMode mode, byte[] data) {
		// System.out.println("Write_MACaddr!");
		byte buffer[] = new byte[MACADRR_DATA_LEN * 2];
		if (mode == UpgradeMode.UPGRADE_MODE_USB) {
			try {
				// Hisa 2015.09.01 usb升级识别MstarUpgrade.bin start
				FileInputStream finMacAddr = new FileInputStream(
						"/mnt/factoryusb/mac.bin");
				// Hisa 2015.09.01 usb升级识别MstarUpgrade.bin end
				int length = finMacAddr.available();
				if (length < MACADRR_DATA_LEN * 2) {
					finMacAddr.close();
					return UpgradeInfo.UPGRADE_READ_FILE_FAIL;
				}
				finMacAddr.read(buffer);
				/*
				 * System.out.printf("Mac Addr %02X:%02X:%02X:%02X:%02X:%02X\n",
				 * buffer[0], buffer[1], buffer[2], buffer[3], buffer[4],
				 * buffer[5]);
				 */
				long currentMacAddr = 0;
				long maxMacAddr = 0;
				int temp;
				byte bufferWrite[] = new byte[MACADRR_DATA_LEN * 2];
				for (int i = 0; i < MACADRR_DATA_LEN; i++) {
					currentMacAddr *= 0x100;
					temp = buffer[i];
					bufferWrite[i] = buffer[i];
					if (temp < 0) {
						temp += 0x100;
					}
					currentMacAddr += temp;
				}
				for (int i = MACADRR_DATA_LEN; i < (MACADRR_DATA_LEN * 2); i++) {
					maxMacAddr *= 0x100;
					temp = buffer[i];
					bufferWrite[i] = buffer[i];
					if (temp < 0) {
						temp += 0x100;
					}
					maxMacAddr += temp;
				}
				if (currentMacAddr > maxMacAddr) {
					finMacAddr.close();
					return UpgradeInfo.UPGRADE_DATA_OVER;
				}
				finMacAddr.close();
				currentMacAddr++;
				for (int i = (MACADRR_DATA_LEN - 1); i >= 0; i--) {
					bufferWrite[i] = (byte) (currentMacAddr % 0x100);
					currentMacAddr /= 0x100;
				}
				// Hisa 2015.09.01 usb升级识别MstarUpgrade.bin start
				FileOutputStream fout = new FileOutputStream(
						"/mnt/factoryusb/mac.bin");
				// Hisa 2015.09.01 usb升级识别MstarUpgrade.bin end
				fout.write(bufferWrite);
				fout.flush();
				fout.close();
				Runtime.getRuntime().exec("sync");
				try
				{
					
					Thread.sleep(3000);
				}catch(InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int count = 0;
				boolean status = true;
				while(true)
				{
					// Hisa 2015.09.01 usb升级识别MstarUpgrade.bin start
					finMacAddr = new FileInputStream("/mnt/factoryusb/mac.bin");
					// Hisa 2015.09.01 usb升级识别MstarUpgrade.bin end
					length = finMacAddr.available();
					if(length < MACADRR_DATA_LEN * 2)
					{
						status = false;
					}
					else
					{
						byte bufferSaved[] = new byte[length];
						finMacAddr.read(bufferSaved);
						for(int i = 0; i < MACADRR_DATA_LEN * 2; i++)
						{
							if(bufferSaved[i] != bufferWrite[i])
							{
								status = false;
								break;
							}
						}
					}
					finMacAddr.close();

					if(status == false)
					{
						count++;
						if(count > 5)
						{
							return UpgradeInfo.UPGRADE_READ_FILE_FAIL;
						}
						try
						{
							Thread.sleep(2000);
						}
						catch (InterruptedException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else
					{
						break;
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return UpgradeInfo.UPGRADE_NO_FILE;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return UpgradeInfo.UPGRADE_READ_FILE_FAIL;
			}

		} else if (mode == UpgradeMode.UPGRADE_MODE_NET) {

		}
			/*short bufferSpi[] = new short[SYSTEM_BANK_SIZE];
			bufferSpi = TvManager.getInstance().readFromSpiFlashByBank(FLASH_BANK_ADRR,
					SYSTEM_BANK_SIZE);
			for (int i = 0; i < MACADRR_DATA_LEN; i++) {
				data[i] = buffer[i];
				bufferSpi[FLASH_MACADRR_ADRR_START + i] = buffer[i];
			}
			TvManager.getInstance().writeToSpiFlashByBank(FLASH_BANK_ADRR, bufferSpi);*/
			try {
				String str = String.format("%02X:%02X:%02X:%02X:%02X:%02X",
						buffer[0], buffer[1], buffer[2], buffer[3], buffer[4],
						buffer[5]);
				// Log.i("setEnvironment", "ethaddr " + str);
				K_TvManager.getInstance().K_setEnvironment("ethaddr", str);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return UpgradeInfo.UPGRADE_RW_SPI_FAIL;
		}
		return UpgradeInfo.UPGRADE_OK;
	}
	class WritemacTask extends AsyncTask<Void, Void, UpgradeInfo> 
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			progressDialog = getProgressDialog();
			progressDialog.show();
			super.onPreExecute();
		}
		@Override
		protected UpgradeInfo doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			
			UpgradeInfo infoMacAddr = null;
			byte[] dataMacAddr = new byte[6];
			infoMacAddr = Write_MACaddr(UpgradeMode.UPGRADE_MODE_USB, dataMacAddr);
			return infoMacAddr;
		}
		@Override
		protected void onPostExecute(UpgradeInfo result) {
			// TODO Auto-generated method stub
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			Display(result);
			super.onPostExecute(result);
		}
	}
	
	private ProgressDialog getProgressDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog
					.setMessage(getResources().getString(R.string.str_factory_write_mac_waiting));
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(true);
		}
		return progressDialog;
	}

}
