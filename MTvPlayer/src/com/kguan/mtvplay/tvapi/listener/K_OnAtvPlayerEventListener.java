package com.kguan.mtvplay.tvapi.listener;

import com.kguan.mtvplay.tvapi.vo.atv.K_AtvEventScan;
import com.mstar.android.tvapi.atv.listener.OnAtvPlayerEventListener;
import com.mstar.android.tvapi.atv.vo.AtvEventScan;

public abstract class K_OnAtvPlayerEventListener implements OnAtvPlayerEventListener{

	@Override
	public boolean onAtvAutoTuningScanInfo(int arg0, AtvEventScan atvEventScan) {
		K_AtvEventScan k_AtvEventScan = new K_AtvEventScan();
		k_AtvEventScan.setAtvEventScan(atvEventScan);
		boolean val = K_onAtvAutoTuningScanInfo(arg0, k_AtvEventScan);
		return val;
	}

	@Override
	public boolean onAtvManualTuningScanInfo(int arg0, AtvEventScan atvEventScan) {
		K_AtvEventScan k_AtvEventScan = new K_AtvEventScan();
		k_AtvEventScan.setAtvEventScan(atvEventScan);
		boolean val = K_onAtvManualTuningScanInfo(arg0, k_AtvEventScan);
		return val;
	}

	@Override
	public boolean onAtvProgramInfoReady(int arg0) {
		boolean val = K_onAtvProgramInfoReady(arg0);
		return val;
	}

	@Override
	public boolean onSignalLock(int arg0) {
		boolean val = K_onSignalLock(arg0);
		return val;
	}

	@Override
	public boolean onSignalUnLock(int arg0) {
		boolean val = K_onSignalUnLock(arg0);
		return val;
	}
	
	public abstract boolean K_onAtvAutoTuningScanInfo(int arg0, K_AtvEventScan arg1);
	public abstract boolean K_onAtvManualTuningScanInfo(int arg0, K_AtvEventScan arg1);
	public abstract boolean K_onAtvProgramInfoReady(int arg0);
	public abstract boolean K_onSignalLock(int arg0);
	public abstract boolean K_onSignalUnLock(int arg0);
}
