package com.kguan.mtvplay.tvapi.listener;

import com.mstar.android.tvapi.common.listener.OnMhlEventListener;

public abstract class K_OnMhlEventListener implements OnMhlEventListener{

	@Override
	public boolean onAutoSwitch(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return K_onAutoSwitch(arg0, arg1, arg2);
	}

	@Override
	public boolean onKeyInfo(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return K_onKeyInfo(arg0, arg1, arg2);
	}
	//=======================================================
	public abstract boolean K_onAutoSwitch(int arg0, int arg1, int arg2);
	public abstract boolean K_onKeyInfo(int arg0, int arg1, int arg2);
}
