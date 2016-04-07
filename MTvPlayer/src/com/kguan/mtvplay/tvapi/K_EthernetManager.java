package com.kguan.mtvplay.tvapi;

import com.kguan.mtvplay.tvapi.vo.K_VOClasses.K_EthernetDevInfo;
import com.mstar.android.ethernet.EthernetDevInfo;
import com.mstar.android.ethernet.EthernetManager;

public class K_EthernetManager {
	public EthernetManager tvEthernetManager = null;
	public static K_EthernetManager kEthernetManager = null;

	private K_EthernetManager() {
		tvEthernetManager = EthernetManager.getInstance();
	}

	public static K_EthernetManager getInstance() {
		if (kEthernetManager == null) {
			kEthernetManager = new K_EthernetManager();
		}
		return kEthernetManager;
	}

	public K_EthernetDevInfo K_getSavedConfig() {
		// TODO Auto-generated method stub
		return (K_EthernetDevInfo) tvEthernetManager.getSavedConfig();
	}


}
