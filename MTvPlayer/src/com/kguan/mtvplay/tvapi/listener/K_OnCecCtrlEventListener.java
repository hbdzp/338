package com.kguan.mtvplay.tvapi.listener;

import com.mstar.android.tv.TvCecManager.OnCecCtrlEventListener;

public abstract class K_OnCecCtrlEventListener implements OnCecCtrlEventListener{

	@Override
	public boolean onCecCtrlEvent(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}
	//===================================
	public abstract boolean K_onCecCtrlEvent(int arg0, int arg1, int arg2);
}
