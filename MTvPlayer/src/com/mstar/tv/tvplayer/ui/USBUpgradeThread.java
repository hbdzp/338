package com.mstar.tv.tvplayer.ui;

import com.mstar.tv.tvplayer.ui.LocalUpdateSoftwareActivity.EnumUpgradeStatus;


public class USBUpgradeThread {
	static int UPGRATE_END_FAIL = 0;
	static int UPGRATE_END_SUCCESS = 1;
	static int UPGRATE_END_FILE_NOT_FOUND = 2;
	static int UPGRATE_END_SUCCESS_MAIN = 3;
	static int UPGRATE_START = 4;


	public static boolean UpgradeMain() {
		new Thread(new Runnable() {
			@Override
			public void run() {

				if (LocalUpdateSoftwareActivity.getHandler() != null) {
					int upgrate_status;
					LocalUpdateSoftwareActivity.getHandler().sendEmptyMessage(UPGRATE_START);

					upgrate_status = LocalUpdateSoftwareActivity.UpgradeMainFun();

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (upgrate_status == EnumUpgradeStatus.E_UPGRADE_SUCCESS
							.ordinal()) {
						LocalUpdateSoftwareActivity.getHandler().sendEmptyMessage(
								UPGRATE_END_SUCCESS_MAIN);
					} else if (upgrate_status == EnumUpgradeStatus.E_UPGRADE_FILE_NOT_FOUND
							.ordinal()) {
						LocalUpdateSoftwareActivity.getHandler().sendEmptyMessage(
								UPGRATE_END_FILE_NOT_FOUND);
					} else {
						LocalUpdateSoftwareActivity.getHandler().sendEmptyMessage(
								UPGRATE_END_FAIL);
					}
				}

			}

		}).start();
		return true;
	}
}
