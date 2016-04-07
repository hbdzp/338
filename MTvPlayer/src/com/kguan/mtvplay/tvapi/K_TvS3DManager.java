package com.kguan.mtvplay.tvapi;

import com.mstar.android.tv.TvS3DManager;

public class K_TvS3DManager {
	public static K_TvS3DManager k_TS3dManager = null;
	public TvS3DManager tvS3DManager = null;

	private K_TvS3DManager() {
		tvS3DManager = TvS3DManager.getInstance();
	}

	public static K_TvS3DManager getInstance() {
		if (k_TS3dManager == null) {
			k_TS3dManager = new K_TvS3DManager();
		}
		return k_TS3dManager;
	}

	public void K_setDisplayFormatForUI(int threeDimensionsDisplayFormatNone) {
		if(tvS3DManager != null)
			tvS3DManager.setDisplayFormatForUI(threeDimensionsDisplayFormatNone);
	}

	
}
