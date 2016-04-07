package com.kguan.mtvplay.tvapi;

import com.kguan.mtvplay.tvapi.vo.common.K_PresentFollowingEventInfo;
import com.kguan.mtvplay.tvapi.vo.dtv.K_DtvEitInfo;
import com.mstar.android.tv.TvEpgManager;

public class K_TvEpgManager {
	public static K_TvEpgManager kTvEpgManager = null;
	public TvEpgManager tvEpgManager = null;

	private K_TvEpgManager() {
		tvEpgManager = TvEpgManager.getInstance();
	}

	public static K_TvEpgManager getInstance() {
		if (kTvEpgManager == null) {
			kTvEpgManager = new K_TvEpgManager();
		}
		return kTvEpgManager;
	}

	public K_DtvEitInfo K_getEitInfo(boolean bPresent) {
		K_DtvEitInfo k_DtvEitInfo = new K_DtvEitInfo();
		k_DtvEitInfo.setDtvEitInfo(tvEpgManager.getEitInfo(bPresent));
		return k_DtvEitInfo;
	}

	public K_PresentFollowingEventInfo K_getEpgPresentFollowingEventInfo(short serviceType, int serviceNo, boolean present,
			int descriptionType) {
		// TODO Auto-generated method stub
		K_PresentFollowingEventInfo k_PresentFollowingEventInfo = new K_PresentFollowingEventInfo();
		k_PresentFollowingEventInfo.setPresentFollowingEventInfo(tvEpgManager.getEpgPresentFollowingEventInfo(serviceType, serviceNo, present, descriptionType));
		return k_PresentFollowingEventInfo;
	}

	
}
