package com.kguan.mtvplay.tvapi.listener;

import com.mstar.android.tv.TvCiManager.OnCiStatusChangeEventListener;

public abstract class K_OnCiStatusChangeEventListener implements OnCiStatusChangeEventListener{

	@Override
	public boolean onCiStatusChanged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return K_onCiStatusChanged(arg0, arg1, arg2);
	}
	//=============================================================
	public abstract boolean K_onCiStatusChanged(int arg0, int arg1, int arg2);
}
