package com.kguan.mtvplay.tvapi;

import com.mstar.android.tv.TvMhlManager;

public class K_TvMhlManager {
	public static K_TvMhlManager k_TvMhlManager = null;
	public TvMhlManager tvMhlManager = null;

	private K_TvMhlManager() {
		tvMhlManager = TvMhlManager.getInstance();
	}

	public static K_TvMhlManager getInstance() {
		if (k_TvMhlManager == null) {
			k_TvMhlManager = new K_TvMhlManager();
		}
		return k_TvMhlManager;
	}

	public boolean K_CbusStatus() {
		// TODO Auto-generated method stub
		return tvMhlManager.CbusStatus();
	}

	public boolean K_IsMhlPortInUse() {
		// TODO Auto-generated method stub
		return tvMhlManager.IsMhlPortInUse();
	}

	public boolean K_IRKeyProcess(int keyCode, boolean bIsRelease) {
		// TODO Auto-generated method stub
		return tvMhlManager.IRKeyProcess(keyCode, bIsRelease);
	}
	/**
	 * 
	 * @Title: K_getAutoSwitch
	 * @Description: 判断TvMhlManager是否是自动选择
	 * @return
	 * @return: boolean
	 */
	public boolean K_getAutoSwitch() {
		return tvMhlManager.getAutoSwitch();
	}
	/**
	 * 
	 * @Title: K_setAutoSwitch
	 * @Description: 设置为自动切换
	 * @param tf
	 * @return: void
	 */
	public void K_setAutoSwitch(boolean tf) {
		if (tvMhlManager != null)
			tvMhlManager.setAutoSwitch(tf);
	}

	
}
