package com.kguan.mtvplay.tvapi.listener;

import com.mstar.android.tvapi.common.listener.OnTvEventListener;

public abstract class K_OnTvEventListener implements OnTvEventListener{

	@Override
	public boolean on4k2kHDMIDisableDualView(int arg0, int arg1, int arg2) {
		return K_on4k2kHDMIDisableDualView(arg0, arg1, arg2);
	}

	@Override
	public boolean on4k2kHDMIDisablePip(int arg0, int arg1, int arg2) {
		return K_on4k2kHDMIDisablePip(arg0, arg1, arg2);
	}

	@Override
	public boolean on4k2kHDMIDisablePop(int arg0, int arg1, int arg2) {
		return K_on4k2kHDMIDisablePop(arg0, arg1, arg2);
	}

	@Override
	public boolean on4k2kHDMIDisableTravelingMode(int arg0, int arg1, int arg2) {
		return K_on4k2kHDMIDisableTravelingMode(arg0, arg1, arg2);
	}

	@Override
	public boolean onAtscPopupDialog(int arg0, int arg1, int arg2) {
		return K_onAtscPopupDialog(arg0, arg1, arg2);
	}

	@Override
	public boolean onDeadthEvent(int arg0, int arg1, int arg2) {
		return K_onDeadthEvent(arg0, arg1, arg2);
	}

	@Override
	public boolean onDtvReadyPopupDialog(int arg0, int arg1, int arg2) {
		return K_onDtvReadyPopupDialog(arg0, arg1, arg2);
	}

	@Override
	public boolean onScartMuteOsdMode(int arg0) {
		return K_onScartMuteOsdMode(arg0);
	}

	@Override
	public boolean onScreenSaverMode(int arg0, int arg1, int arg2) {
		return K_onScreenSaverMode(arg0, arg1, arg2);
	}

	@Override
	public boolean onSignalLock(int arg0) {
		return K_onSignalLock(arg0);
	}

	@Override
	public boolean onSignalUnlock(int arg0) {
		return K_onSignalUnlock(arg0);
	}

	@Override
	public boolean onUnityEvent(int arg0, int arg1, int arg2) {
		return K_onUnityEvent(arg0, arg1, arg2);
	}
	//=======================================================================
	public abstract boolean K_on4k2kHDMIDisableDualView(int arg0, int arg1, int arg2);
	public abstract boolean K_on4k2kHDMIDisablePip(int arg0, int arg1, int arg2);
	public abstract boolean K_on4k2kHDMIDisablePop(int arg0, int arg1, int arg2);
	public abstract boolean K_on4k2kHDMIDisableTravelingMode(int arg0, int arg1, int arg2);
	public abstract boolean K_onAtscPopupDialog(int arg0, int arg1, int arg2);
	public abstract boolean K_onDeadthEvent(int arg0, int arg1, int arg2);
	public abstract boolean K_onDtvReadyPopupDialog(int arg0, int arg1, int arg2);
	public abstract boolean K_onScartMuteOsdMode(int arg0);
	public abstract boolean K_onScreenSaverMode(int arg0, int arg1, int arg2);
	public abstract boolean K_onSignalLock(int arg0);
	public abstract boolean K_onSignalUnlock(int arg0);
	public abstract boolean K_onUnityEvent(int arg0, int arg1, int arg2);

}
