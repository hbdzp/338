package com.kguan.mtvplay.tvapi;

import com.kguan.mtvplay.tvapi.vo.dtv.K_SatelliteInfo;
import com.mstar.android.tv.TvDvbChannelManager;
import com.mstar.android.tvapi.dtv.dvb.dvbs.vo.SatelliteInfo;

public class K_TvDvbChannelManager {
	public static K_TvDvbChannelManager kTvDvbChannelManager = null;
	public TvDvbChannelManager tvDvbChannelManager = null;

	private K_TvDvbChannelManager() {
		tvDvbChannelManager = TvDvbChannelManager.getInstance();
	}

	public static K_TvDvbChannelManager getInstance() {
		if (kTvDvbChannelManager == null) {
			kTvDvbChannelManager = new K_TvDvbChannelManager();
		}
		return kTvDvbChannelManager;
	}

	public int K_getCurrentSatelliteCount() {
		// TODO Auto-generated method stub
		return tvDvbChannelManager.getCurrentSatelliteCount();
	}

	public K_SatelliteInfo K_getSatelliteInfo(int index) {
		K_SatelliteInfo k_SatelliteInfo = new K_SatelliteInfo();
		k_SatelliteInfo.setSatelliteInfo(tvDvbChannelManager.getSatelliteInfo(index));
		return k_SatelliteInfo;
	}

	public int K_getCurrentSatelliteNumber() {
		// TODO Auto-generated method stub
		return tvDvbChannelManager.getCurrentSatelliteNumber();
	}

}
