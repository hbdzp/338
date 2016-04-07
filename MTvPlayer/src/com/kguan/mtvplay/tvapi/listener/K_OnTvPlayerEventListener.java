package com.kguan.mtvplay.tvapi.listener;

import com.kguan.mtvplay.tvapi.vo.common.K_HbbtvEventInfo;
import com.mstar.android.tvapi.common.listener.OnTvPlayerEventListener;
import com.mstar.android.tvapi.common.vo.HbbtvEventInfo;

public abstract class K_OnTvPlayerEventListener implements OnTvPlayerEventListener{

	@Override
	public boolean on4k2kHDMIDisableDualView(int arg0, int arg1, int arg2) {
		boolean val = K_on4k2kHDMIDisableDualView(arg0, arg1, arg2);
		return val;
	}

	@Override
	public boolean on4k2kHDMIDisablePip(int arg0, int arg1, int arg2) {
		boolean val = K_on4k2kHDMIDisablePip(arg0, arg1, arg2);
		return val;
	}

	@Override
	public boolean on4k2kHDMIDisablePop(int arg0, int arg1, int arg2) {
		boolean val = K_on4k2kHDMIDisablePop(arg0, arg1, arg2);
		return val;
	}

	@Override
	public boolean on4k2kHDMIDisableTravelingMode(int arg0, int arg1, int arg2) {
		boolean val = K_on4k2kHDMIDisableTravelingMode(arg0, arg1, arg2);
		return val;
	}

	@Override
	public boolean onDtvChannelInfoUpdate(int arg0, int arg1, int arg2) {
		boolean val = K_onDtvChannelInfoUpdate(arg0, arg1, arg2);
		return val;
	}

	@Override
	public boolean onDtvPsipTsUpdate(int arg0, int arg1, int arg2) {
		boolean val = K_onDtvPsipTsUpdate(arg0, arg1, arg2);
		return val;
	}

	@Override
	public boolean onEmerencyAlert(int arg0, int arg1, int arg2) {
		boolean val = K_onEmerencyAlert(arg0, arg1, arg2);
		return val;
	}

	@Override
	public boolean onEpgUpdateList(int arg0, int arg1) {
		boolean val = K_onEpgUpdateList(arg0, arg1);
		return val;
	}

	@Override
	public boolean onHbbtvUiEvent(int arg0, HbbtvEventInfo arg1) {
		K_HbbtvEventInfo k_HbbtvEventInfo = new K_HbbtvEventInfo();
		k_HbbtvEventInfo.setHbbtvEventInfo(arg1);
		boolean val = K_onHbbtvUiEvent(arg0, k_HbbtvEventInfo);
		return val;
	}

	@Override
	public boolean onPopupDialog(int arg0, int arg1, int arg2) {
		boolean val = K_onPopupDialog(arg0, arg1, arg2);
		return val;
	}

	@Override
	public boolean onPvrNotifyAlwaysTimeShiftProgramNotReady(int arg0) {
		boolean val = K_onPvrNotifyAlwaysTimeShiftProgramNotReady(arg0);
		return val;
	}

	@Override
	public boolean onPvrNotifyAlwaysTimeShiftProgramReady(int arg0) {
		boolean val = K_onPvrNotifyAlwaysTimeShiftProgramReady(arg0);
		return val;
	}

	@Override
	public boolean onPvrNotifyCiPlusProtection(int arg0) {
		boolean val = K_onPvrNotifyCiPlusProtection(arg0);
		return val;
	}

	@Override
	public boolean onPvrNotifyCiPlusRetentionLimitUpdate(int arg0, int arg1) {
		boolean val = K_onPvrNotifyCiPlusRetentionLimitUpdate(arg0, arg1);
		return val;
	}

	@Override
	public boolean onPvrNotifyOverRun(int arg0) {
		boolean val = K_onPvrNotifyOverRun(arg0);
		return val;
	}

	@Override
	public boolean onPvrNotifyParentalControl(int arg0, int arg1) {
		boolean val = K_onPvrNotifyParentalControl(arg0, arg1);
		return val;
	}

	@Override
	public boolean onPvrNotifyPlaybackBegin(int arg0) {
		boolean val = K_onPvrNotifyPlaybackBegin(arg0);
		return val;
	}

	@Override
	public boolean onPvrNotifyPlaybackSpeedChange(int arg0) {
		boolean val = K_onPvrNotifyPlaybackSpeedChange(arg0);
		return val;
	}

	@Override
	public boolean onPvrNotifyPlaybackStop(int arg0) {
		boolean val = K_onPvrNotifyPlaybackStop(arg0);
		return val;
	}

	@Override
	public boolean onPvrNotifyPlaybackTime(int arg0, int arg1) {
		boolean val = K_onPvrNotifyPlaybackTime(arg0, arg1);
		return val;
	}

	@Override
	public boolean onPvrNotifyRecordSize(int arg0, int arg1) {
		boolean val = K_onPvrNotifyRecordSize(arg0, arg1);
		return val;
	}

	@Override
	public boolean onPvrNotifyRecordStop(int arg0) {
		boolean val = K_onPvrNotifyRecordStop(arg0);
		return val;
	}

	@Override
	public boolean onPvrNotifyRecordTime(int arg0, int arg1) {
		boolean val = K_onPvrNotifyRecordTime(arg0, arg1);
		return val;
	}

	@Override
	public boolean onPvrNotifyTimeShiftOverwritesAfter(int arg0, int arg1) {
		boolean val = K_onPvrNotifyTimeShiftOverwritesAfter(arg0, arg1);
		return val;
	}

	@Override
	public boolean onPvrNotifyTimeShiftOverwritesBefore(int arg0, int arg1) {
		boolean val = K_onPvrNotifyTimeShiftOverwritesBefore(arg0, arg1);
		return val;
	}

	@Override
	public boolean onPvrNotifyUsbRemoved(int arg0, int arg1) {
		boolean val = K_onPvrNotifyUsbRemoved(arg0, arg1);
		return val;
	}

	@Override
	public boolean onScreenSaverMode(int arg0, int arg1) {
		boolean val = K_onScreenSaverMode(arg0, arg1);
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

	@Override
	public boolean onTvProgramInfoReady(int arg0) {
		boolean val = K_onTvProgramInfoReady(arg0);
		return val;
	}
	
	
	//============================================
	public abstract boolean K_on4k2kHDMIDisableDualView(int arg0, int arg1, int arg2);
	public abstract boolean K_on4k2kHDMIDisablePip(int arg0, int arg1, int arg2);
	public abstract boolean K_on4k2kHDMIDisablePop(int arg0, int arg1, int arg2);
	public abstract boolean K_on4k2kHDMIDisableTravelingMode(int arg0, int arg1, int arg2);
	public abstract boolean K_onDtvChannelInfoUpdate(int arg0, int arg1, int arg2);
	public abstract boolean K_onDtvPsipTsUpdate(int arg0, int arg1, int arg2);
	public abstract boolean K_onEmerencyAlert(int arg0, int arg1, int arg2);
	public abstract boolean K_onEpgUpdateList(int arg0, int arg1);
	public abstract boolean K_onHbbtvUiEvent(int arg0, K_HbbtvEventInfo arg1);
	public abstract boolean K_onPopupDialog(int arg0, int arg1, int arg2);
	public abstract boolean K_onPvrNotifyAlwaysTimeShiftProgramNotReady(int arg0);
	public abstract boolean K_onPvrNotifyAlwaysTimeShiftProgramReady(int arg0);
	public abstract boolean K_onPvrNotifyCiPlusProtection(int arg0);
	public abstract boolean K_onPvrNotifyCiPlusRetentionLimitUpdate(int arg0, int arg1);
	public abstract boolean K_onPvrNotifyOverRun(int arg0);
	public abstract boolean K_onPvrNotifyParentalControl(int arg0, int arg1);
	public abstract boolean K_onPvrNotifyPlaybackBegin(int arg0);
	public abstract boolean K_onPvrNotifyPlaybackSpeedChange(int arg0);
	public abstract boolean K_onPvrNotifyPlaybackStop(int arg0);
	public abstract boolean K_onPvrNotifyPlaybackTime(int arg0, int arg1);
	public abstract boolean K_onPvrNotifyRecordSize(int arg0, int arg1);
	public abstract boolean K_onPvrNotifyRecordStop(int arg0);
	public abstract boolean K_onPvrNotifyRecordTime(int arg0, int arg1);
	public abstract boolean K_onPvrNotifyTimeShiftOverwritesAfter(int arg0, int arg1);
	public abstract boolean K_onPvrNotifyTimeShiftOverwritesBefore(int arg0, int arg1);
	public abstract boolean K_onPvrNotifyUsbRemoved(int arg0, int arg1);
	public abstract boolean K_onScreenSaverMode(int arg0, int arg1);
	public abstract boolean K_onSignalLock(int arg0);
	public abstract boolean K_onSignalUnLock(int arg0);
	public abstract boolean K_onTvProgramInfoReady(int arg0);


}
