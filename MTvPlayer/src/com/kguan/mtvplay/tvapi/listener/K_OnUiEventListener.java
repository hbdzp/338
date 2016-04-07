package com.kguan.mtvplay.tvapi.listener;

import com.mstar.android.tv.TvCiManager.OnUiEventListener;

public abstract class K_OnUiEventListener implements OnUiEventListener{

	@Override
	public boolean onUiEvent(int arg0) {
		// TODO Auto-generated method stub
		return K_onUiEvent(arg0);
	}
	//==============================
	public abstract boolean K_onUiEvent(int arg0);
}
