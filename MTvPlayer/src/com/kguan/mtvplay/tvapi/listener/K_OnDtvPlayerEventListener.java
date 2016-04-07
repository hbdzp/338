package com.kguan.mtvplay.tvapi.listener;

import com.kguan.mtvplay.tvapi.vo.dtv.K_DtvEventScan;
import com.mstar.android.tvapi.dtv.listener.OnDtvPlayerEventListener;
import com.mstar.android.tvapi.dtv.vo.DtvEventScan;

public abstract class K_OnDtvPlayerEventListener implements OnDtvPlayerEventListener{

	@Override
	public boolean onAudioModeChange(int arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return K_onAudioModeChange(arg0, arg1);
	}

	@Override
	public boolean onChangeTtxStatus(int arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return K_onChangeTtxStatus(arg0, arg1);
	}

	@Override
	public boolean onCiLoadCredentialFail(int arg0) {
		// TODO Auto-generated method stub
		return K_onCiLoadCredentialFail(arg0);
	}

	@Override
	public boolean onDtvAutoTuningScanInfo(int arg0, DtvEventScan arg1) {
		K_DtvEventScan k_DtvEventScan = new K_DtvEventScan();
		k_DtvEventScan.setDvEventScan(arg1);
		return K_onDtvAutoTuningScanInfo(arg0, k_DtvEventScan);
	}

	@Override
	public boolean onDtvAutoUpdateScan(int arg0) {
		// TODO Auto-generated method stub
		return K_onDtvAutoUpdateScan(arg0);
	}

	@Override
	public boolean onDtvChannelNameReady(int arg0) {
		// TODO Auto-generated method stub
		return K_onDtvChannelNameReady(arg0);
	}

	@Override
	public boolean onDtvPriComponentMissing(int arg0) {
		// TODO Auto-generated method stub
		return K_onDtvPriComponentMissing(arg0);
	}

	@Override
	public boolean onDtvProgramInfoReady(int arg0) {
		// TODO Auto-generated method stub
		return K_onDtvProgramInfoReady(arg0);
	}

	@Override
	public boolean onEpgTimerSimulcast(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return K_onEpgTimerSimulcast(arg0, arg1);
	}

	@Override
	public boolean onGingaStatusMode(int arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return K_onGingaStatusMode(arg0, arg1);
	}

	@Override
	public boolean onHbbtvStatusMode(int arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return K_onHbbtvStatusMode(arg0, arg1);
	}

	@Override
	public boolean onMheg5EventHandler(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return K_onMheg5EventHandler(arg0, arg1);
	}

	@Override
	public boolean onMheg5ReturnKey(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return K_onMheg5ReturnKey(arg0, arg1);
	}

	@Override
	public boolean onMheg5StatusMode(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return K_onMheg5StatusMode(arg0, arg1);
	}

	@Override
	public boolean onOadDownload(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return K_onOadDownload(arg0, arg1);
	}

	@Override
	public boolean onOadHandler(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return K_onOadHandler(arg0, arg1,arg2);
	}

	@Override
	public boolean onOadTimeout(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return K_onOadTimeout(arg0, arg1);
	}

	@Override
	public boolean onPopupScanDialogFrequencyChange(int arg0) {
		// TODO Auto-generated method stub
		return K_onPopupScanDialogFrequencyChange(arg0);
	}

	@Override
	public boolean onPopupScanDialogLossSignal(int arg0) {
		// TODO Auto-generated method stub
		return K_onPopupScanDialogLossSignal(arg0);
	}

	@Override
	public boolean onPopupScanDialogNewMultiplex(int arg0) {
		// TODO Auto-generated method stub
		return K_onPopupScanDialogNewMultiplex(arg0);
	}

	@Override
	public boolean onRctPresence(int arg0) {
		// TODO Auto-generated method stub
		return K_onRctPresence(arg0);
	}

	@Override
	public boolean onSignalLock(int arg0) {
		// TODO Auto-generated method stub
		return K_onSignalLock(arg0);
	}

	@Override
	public boolean onSignalUnLock(int arg0) {
		// TODO Auto-generated method stub
		return K_onSignalUnLock(arg0);
	}

	@Override
	public boolean onTsChange(int arg0) {
		// TODO Auto-generated method stub
		return K_onTsChange(arg0);
	}

	@Override
	public boolean onUiOPExitServiceList(int arg0) {
		// TODO Auto-generated method stub
		return K_onUiOPExitServiceList(arg0);
	}

	@Override
	public boolean onUiOPRefreshQuery(int arg0) {
		// TODO Auto-generated method stub
		return K_onUiOPRefreshQuery(arg0);
	}

	@Override
	public boolean onUiOPServiceList(int arg0) {
		// TODO Auto-generated method stub
		return K_onUiOPServiceList(arg0);
	}
	//=================================================
	public abstract boolean K_onAudioModeChange(int arg0, boolean arg1);
	public abstract boolean K_onChangeTtxStatus(int arg0, boolean arg1);
	public abstract boolean K_onCiLoadCredentialFail(int arg0);
	public abstract boolean K_onDtvAutoTuningScanInfo(int arg0, K_DtvEventScan arg1);
	public abstract boolean K_onDtvAutoUpdateScan(int arg0);
	public abstract boolean K_onDtvChannelNameReady(int arg0);
	public abstract boolean K_onDtvPriComponentMissing(int arg0);
	public abstract boolean K_onDtvProgramInfoReady(int arg0);
	public abstract boolean K_onEpgTimerSimulcast(int arg0, int arg1);
	public abstract boolean K_onGingaStatusMode(int arg0, boolean arg1);
	public abstract boolean K_onHbbtvStatusMode(int arg0, boolean arg1);
	public abstract boolean K_onMheg5EventHandler(int arg0, int arg1);
	public abstract boolean K_onMheg5ReturnKey(int arg0, int arg1);
	public abstract boolean K_onMheg5StatusMode(int arg0, int arg1);
	public abstract boolean K_onOadDownload(int arg0, int arg1);
	public abstract boolean K_onOadHandler(int arg0, int arg1, int arg2);
	public abstract boolean K_onOadTimeout(int arg0, int arg1);
	public abstract boolean K_onPopupScanDialogFrequencyChange(int arg0);
	public abstract boolean K_onPopupScanDialogLossSignal(int arg0);
	public abstract boolean K_onPopupScanDialogNewMultiplex(int arg0);
	public abstract boolean K_onRctPresence(int arg0);
	public abstract boolean K_onSignalLock(int arg0);
	public abstract boolean K_onSignalUnLock(int arg0);
	public abstract boolean K_onTsChange(int arg0);
	public abstract boolean K_onUiOPExitServiceList(int arg0);
	public abstract boolean K_onUiOPRefreshQuery(int arg0);
	public abstract boolean K_onUiOPServiceList(int arg0);
}
