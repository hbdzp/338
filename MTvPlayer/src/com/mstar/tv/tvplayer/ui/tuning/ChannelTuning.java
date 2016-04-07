//<MStar Software>
//******************************************************************************
// MStar Software
// Copyright (c) 2010 - 2013 MStar Semiconductor, Inc. All rights reserved.
// All software, firmware and related documentation herein ("MStar Software") are
// intellectual property of MStar Semiconductor, Inc. ("MStar") and protected by
// law, including, but not limited to, copyright law and international treaties.
// Any use, modification, reproduction, retransmission, or republication of all
// or part of MStar Software is expressly prohibited, unless prior written
// permission has been granted by MStar.
//
// By accessing, browsing and/or using MStar Software, you acknowledge that you
// have read, understood, and agree, to be bound by below terms ("Terms") and to
// comply with all applicable laws and regulations:
//
// 1. MStar shall retain any and all right, ownership and interest to MStar
//    Software and any modification/derivatives thereof.
//    No right, ownership, or interest to MStar Software and any
//    modification/derivatives thereof is transferred to you under Terms.
//
// 2. You understand that MStar Software might include, incorporate or be
//    supplied together with third party's software and the use of MStar
//    Software may require additional licenses from third parties.
//    Therefore, you hereby agree it is your sole responsibility to separately
//    obtain any and all third party right and license necessary for your use of
//    such third party's software.
//
// 3. MStar Software and any modification/derivatives thereof shall be deemed as
//    MStar's confidential information and you agree to keep MStar's
//    confidential information in strictest confidence and not disclose to any
//    third party.
//
// 4. MStar Software is provided on an "AS IS" basis without warranties of any
//    kind. Any warranties are hereby expressly disclaimed by MStar, including
//    without limitation, any warranties of merchantability, non-infringement of
//    intellectual property rights, fitness for a particular purpose, error free
//    and in conformity with any international standard.  You agree to waive any
//    claim against MStar for any loss, damage, cost or expense that you may
//    incur related to your use of MStar Software.
//    In no event shall MStar be liable for any direct, indirect, incidental or
//    consequential damages, including without limitation, lost of profit or
//    revenues, lost or damage of data, and unauthorized system use.
//    You agree that this Section 4 shall still apply without being affected
//    even if MStar Software has been modified by MStar in accordance with your
//    request or instruction for your use, except otherwise agreed by both
//    parties in writing.
//
// 5. If requested, MStar may from time to time provide technical supports or
//    services in relation with MStar Software to you for your use of
//    MStar Software in conjunction with your or your customer's product
//    ("Services").
//    You understand and agree that, except otherwise agreed by both parties in
//    writing, Services are provided on an "AS IS" basis and the warranty
//    disclaimer set forth in Section 4 above shall apply.
//
// 6. Nothing contained herein shall be construed as by implication, estoppels
//    or otherwise:
//    (a) conferring any license or right to use MStar name, trademark, service
//        mark, symbol or any other identification;
//    (b) obligating MStar or any of its affiliates to furnish any person,
//        including without limitation, you and your customers, any assistance
//        of any kind whatsoever, or any information; or
//    (c) conferring any license or right under any intellectual property right.
//
// 7. These terms shall be governed by and construed in accordance with the laws
//    of Taiwan, R.O.C., excluding its conflict of law rules.
//    Any and all dispute arising out hereof or related hereto shall be finally
//    settled by arbitration referred to the Chinese Arbitration Association,
//    Taipei in accordance with the ROC Arbitration Law and the Arbitration
//    Rules of the Association by three (3) arbitrators appointed in accordance
//    with the said Rules.
//    The place of arbitration shall be in Taipei, Taiwan and the language shall
//    be English.
//    The arbitration award shall be final and binding to both parties.
//
//******************************************************************************
//<MStar Software>

package com.mstar.tv.tvplayer.ui.tuning;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.kguan.mtvplay.tvapi.K_ChannelModel;
import com.kguan.mtvplay.tvapi.K_Constants;
import com.kguan.mtvplay.tvapi.K_TvCommonManager;
import com.kguan.mtvplay.tvapi.K_TvMhlManager;
import com.kguan.mtvplay.tvapi.enumeration.K_EnumDtvScanStatus;
import com.kguan.mtvplay.tvapi.listener.K_OnAtvPlayerEventListener;
import com.kguan.mtvplay.tvapi.listener.K_OnDtvPlayerEventListener;
import com.kguan.mtvplay.tvapi.vo.atv.K_AtvEventScan;
import com.kguan.mtvplay.tvapi.vo.common.K_TvTypeInfo;
import com.kguan.mtvplay.tvapi.vo.dtv.K_DtvEventScan;
import com.kguan.mtvplay.tvapi.vo.dtv.K_DvbMuxInfo;
import com.mstar.android.MIntent;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tv.tvplayer.ui.holder.ViewHolder;
import com.mstar.tvframework.MstarBaseActivity;
import com.mstar.util.PropHelp;

public class ChannelTuning extends MstarBaseActivity {
	/** Called when the activity is first created. */

	private static final String TAG = "ChannelTuning";

	private static int ATV_MIN_FREQ = 48250;

	private static int ATV_MAX_FREQ = 877250;

	private static int ATV_EVENTINTERVAL = 500 * 1000;// every 500ms to show

	private static int dtvServiceCount = 0;

	private boolean isDtvAutoUpdateScan = false;

	private ViewHolder viewholder_channeltune;

	private K_TvCommonManager mTvCommonManager = null;

	private Time scanStartTime = new Time();

	private boolean isMhlOpen = false;

	private int scanPercent = -1;

	private int mCurrentRoute = K_Constants.TV_ROUTE_NONE;

	private K_ChannelModel mTvChannelManager = null;

	private K_OnAtvPlayerEventListener mAtvPlayerEventListener = null;

	private K_OnDtvPlayerEventListener mDtvPlayerEventListener = null;

	private Handler mAtvUiEventHandler = null;

	private Handler mDtvUiEventHandler = null;

	private BroadcastReceiver mReceiver = null;

	private class AtvPlayerEventListener extends K_OnAtvPlayerEventListener {

		@Override
		public boolean K_onAtvAutoTuningScanInfo(int what, K_AtvEventScan extra) {
			Log.d("Jason","ChannelTuning:K_onAtvAutoTuningScanInfo");
			Message msg = mAtvUiEventHandler.obtainMessage(what, extra);
			mAtvUiEventHandler.sendMessage(msg);
			return true;
		}

		@Override
		public boolean K_onAtvManualTuningScanInfo(int what, K_AtvEventScan extra) {
			Message msg = mAtvUiEventHandler.obtainMessage(what, extra);
			mAtvUiEventHandler.sendMessage(msg);
			return true;
		}

		@Override
		public boolean K_onAtvProgramInfoReady(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onSignalLock(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onSignalUnLock(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

/*		@Override
		public boolean onAtvAutoTuningScanInfo(int what, AtvEventScan extra) {
			Message msg = mAtvUiEventHandler.obtainMessage(what, extra);
			mAtvUiEventHandler.sendMessage(msg);
			return true;
		}

		@Override
		public boolean onAtvManualTuningScanInfo(int what, AtvEventScan extra) {
			Message msg = mAtvUiEventHandler.obtainMessage(what, extra);
			mAtvUiEventHandler.sendMessage(msg);
			return true;
		}

		@Override
		public boolean onSignalLock(int what) {
			return false;
		}

		@Override
		public boolean onSignalUnLock(int what) {
			return false;
		}

		@Override
		public boolean onAtvProgramInfoReady(int what) {
			return false;
		}*/
	}

	private class DtvPlayerEventListener extends K_OnDtvPlayerEventListener {

		@Override
		public boolean K_onAudioModeChange(int arg0, boolean arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onChangeTtxStatus(int arg0, boolean arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onCiLoadCredentialFail(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onDtvAutoTuningScanInfo(int what, K_DtvEventScan extra) {
			Message msg = mDtvUiEventHandler.obtainMessage(what, extra);
			mDtvUiEventHandler.sendMessage(msg);
			return true;
		}

		@Override
		public boolean K_onDtvAutoUpdateScan(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onDtvChannelNameReady(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onDtvPriComponentMissing(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onDtvProgramInfoReady(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onEpgTimerSimulcast(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onGingaStatusMode(int arg0, boolean arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onHbbtvStatusMode(int arg0, boolean arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onMheg5EventHandler(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onMheg5ReturnKey(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onMheg5StatusMode(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onOadDownload(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onOadHandler(int arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onOadTimeout(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onPopupScanDialogFrequencyChange(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onPopupScanDialogLossSignal(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onPopupScanDialogNewMultiplex(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onRctPresence(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onSignalLock(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onSignalUnLock(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onTsChange(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onUiOPExitServiceList(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onUiOPRefreshQuery(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onUiOPServiceList(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}/*

		@Override
		public boolean onDtvChannelNameReady(int what) {
			return false;
		}

		@Override
		public boolean onDtvAutoTuningScanInfo(int what, DtvEventScan extra) {
			Message msg = mDtvUiEventHandler.obtainMessage(what, extra);
			mDtvUiEventHandler.sendMessage(msg);
			return true;
		}

		@Override
		public boolean onDtvProgramInfoReady(int what) {
			return false;
		}

		@Override
		public boolean onCiLoadCredentialFail(int what) {
			return false;
		}

		@Override
		public boolean onEpgTimerSimulcast(int what, int arg1) {
			return false;
		}

		@Override
		public boolean onHbbtvStatusMode(int what, boolean arg1) {
			return false;
		}

		@Override
		public boolean onMheg5StatusMode(int what, int arg1) {
			return false;
		}

		@Override
		public boolean onMheg5ReturnKey(int what, int arg1) {
			return false;
		}

		@Override
		public boolean onOadHandler(int what, int arg1, int arg2) {
			return false;
		}

		@Override
		public boolean onOadDownload(int what, int arg1) {
			return false;
		}

		@Override
		public boolean onDtvAutoUpdateScan(int what) {
			return false;
		}

		@Override
		public boolean onTsChange(int what) {
			return false;
		}

		@Override
		public boolean onPopupScanDialogLossSignal(int what) {
			return false;
		}

		@Override
		public boolean onPopupScanDialogNewMultiplex(int what) {
			return false;
		}

		@Override
		public boolean onPopupScanDialogFrequencyChange(int what) {
			return false;
		}

		@Override
		public boolean onRctPresence(int what) {
			return false;
		}

		@Override
		public boolean onChangeTtxStatus(int what, boolean arg1) {
			return false;
		}

		@Override
		public boolean onDtvPriComponentMissing(int what) {
			return false;
		}

		@Override
		public boolean onAudioModeChange(int what, boolean arg1) {
			return false;
		}

		@Override
		public boolean onMheg5EventHandler(int what, int arg1) {
			return false;
		}

		@Override
		public boolean onOadTimeout(int what, int arg1) {
			return false;
		}

		@Override
		public boolean onGingaStatusMode(int what, boolean arg1) {
			return false;
		}

		@Override
		public boolean onSignalLock(int what) {
			return false;
		}

		@Override
		public boolean onSignalUnLock(int what) {
			return false;
		}

		@Override
		public boolean onUiOPRefreshQuery(int what) {
			return false;
		}

		@Override
		public boolean onUiOPServiceList(int what) {
			return false;
		}

		@Override
		public boolean onUiOPExitServiceList(int what) {
			return false;
		}
	*/}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.channeltuning);
		// Hisa 2016.03.04 add Freeze function start
		Intent intent = new Intent();//取消静像菜单
		intent.setAction(MIntent.ACTION_FREEZE_CANCEL_BUTTON);
		//K_TvPictureManager.getInstance().K_unFreezeImage();
		sendBroadcast(intent);  
		// Hisa 2016.03.04 add Freeze function end
		mReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals(
						TvIntent.ACTION_CIPLUS_TUNER_UNAVAIABLE)) {
					Log.i(TAG, "Receive ACTION_CIPLUS_TUNER_UNAVAIABLE...");
					finish();
				}
			}
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction(TvIntent.ACTION_CIPLUS_TUNER_UNAVAIABLE);
		registerReceiver(mReceiver, filter);

		mAtvUiEventHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				updateAtvTuningScanInfo((K_AtvEventScan) msg.obj);
			}
		};

		mDtvUiEventHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				updateDtvTuningScanInfo((K_DtvEventScan) msg.obj);
			}
		};

		viewholder_channeltune = new ViewHolder(ChannelTuning.this);
		viewholder_channeltune.findViewForChannelTuning();
		mTvCommonManager = K_TvCommonManager.getInstance();
		mTvChannelManager = K_ChannelModel.getInstance();

		dtvServiceCount = 0;
		scanStartTime.setToNow();

		if(PropHelp.newInstance().hasDtmb())
		{
			viewholder_channeltune.lineaR_cha_dtvprogram.setVisibility(View.VISIBLE);
			viewholder_channeltune.linear_cha_radioprogram.setVisibility(View.VISIBLE);
			viewholder_channeltune.linear_cha_dataprogram.setVisibility(View.VISIBLE);
			viewholder_channeltune.text_cha_tv.setText(R.string.str_cha_atv);
		}else
		{
			viewholder_channeltune.lineaR_cha_dtvprogram.setVisibility(View.GONE);
			viewholder_channeltune.linear_cha_radioprogram.setVisibility(View.GONE);
			viewholder_channeltune.linear_cha_dataprogram.setVisibility(View.GONE);
			viewholder_channeltune.text_cha_tv.setText(R.string.str_cha_tv);
		}
		if ((getIntent() != null) && (getIntent().getExtras() != null)) {
			isDtvAutoUpdateScan = getIntent().getBooleanExtra(
					"DtvAutoUpdateScan", false);
		}

		if (isDtvAutoUpdateScan) {
			viewholder_channeltune.text_cha_tuningprogress_type.setText("DTV");
			Log.e(TAG, "switchMSrvDtvRouteCmd 1");
			int m_nServiceNum = mTvChannelManager
					.K_getProgramCount(K_Constants.PROGRAM_COUNT_DTV);
			K_ChannelModel.K_DvbcScanParam sp = mTvChannelManager.new K_DvbcScanParam();
			int dvbcRouteIndex = mTvChannelManager
					.K_getSpecificDtvRouteIndex(K_Constants.TV_ROUTE_DVBC);
			if (dvbcRouteIndex < 0) {
				Log.e(TAG, "get route index error");
				return;
			}
			mTvChannelManager.K_switchMSrvDtvRouteCmd(dvbcRouteIndex);
			mTvChannelManager.K_dvbcgetScanParam(sp);

			if (m_nServiceNum > 0) {
				K_DvbMuxInfo dmi = mTvChannelManager.K_getCurrentMuxInfo();
				if (dmi != null) {
					sp.u32NITFrequency = dmi.getDvbMuxInfo().frequency;
					sp.CAB_Type = dmi.getDvbMuxInfo().modulationMode;
					sp.u16SymbolRate = (short) dmi.getDvbMuxInfo().symbRate;
					Log.e(TAG, "dmi.u32NITFrequencye: " + sp.u32NITFrequency);
					Log.e(TAG, "dmi.CAB_Type: " + sp.CAB_Type);
					Log.e(TAG, "dmi.u16SymbolRate: " + sp.u16SymbolRate);
					mTvChannelManager
							.K_setUserScanType(K_Constants.TV_SCAN_DTV);
					mTvChannelManager.K_setDvbcScanParam(sp.u16SymbolRate,
							sp.CAB_Type, sp.u32NITFrequency, 0, (short) 0x0000);
					mTvChannelManager.K_startQuickScan();
				} else {
					Log.e(TAG, "getCurrentMuxInfo error");
					return;
				}
			} else {
				Log.e(TAG, "m_nServiceNum = 0");
				return;
			}
		} else if (mTvChannelManager.K_getUserScanType() == K_Constants.TV_SCAN_ALL
				|| mTvChannelManager.K_getUserScanType() == K_Constants.TV_SCAN_DTV) {
			viewholder_channeltune.text_cha_tuningprogress_type.setText("DTV");
			K_TvTypeInfo tvinfo = mTvCommonManager.K_getTvInfo();
			int currentRouteIndex = mTvChannelManager.K_getCurrentDtvRouteIndex();
			mCurrentRoute = tvinfo.getTvTypeInfo().routePath[currentRouteIndex];
			int dtmbRouteIndex = mTvChannelManager
					.K_getSpecificDtvRouteIndex(K_Constants.TV_ROUTE_DTMB);
			if (K_Constants.TV_ROUTE_DVBC == mCurrentRoute) {
				K_ChannelModel.K_DvbcScanParam sp = mTvChannelManager.new K_DvbcScanParam();
				mTvChannelManager.K_switchMSrvDtvRouteCmd(currentRouteIndex);
				mTvChannelManager.K_dvbcgetScanParam(sp);
				if ((getIntent() != null)
						&& (getIntent().getIntArrayExtra("NitScanPara") != null)) {
					// NIT SCAN
					int[] data = getIntent().getIntArrayExtra("NitScanPara");
					sp.u32NITFrequency = data[0] * 1000;
					sp.CAB_Type = data[1];
					sp.u16SymbolRate = (short) data[2];
					mTvChannelManager.K_setDvbcScanParam(sp.u16SymbolRate,
							sp.CAB_Type, sp.u32NITFrequency, 905000,
							(short) 0x0000);
					mTvChannelManager.K_startDtvAutoScan();

				} else {
					// FULL SCAN
					mTvChannelManager.K_setDvbcScanParam(sp.u16SymbolRate,
							sp.CAB_Type, sp.u32NITFrequency, 0, (short) 0x0000);
					mTvChannelManager.K_startDtvFullScan();
				}
			} else if ((K_Constants.TV_ROUTE_DVBT == mCurrentRoute)
					|| K_Constants.TV_ROUTE_DVBT2 == mCurrentRoute) {
				Intent i = getIntent();
				boolean tmp = i.getBooleanExtra("isAustria", false);
				if (tmp) {
					mTvChannelManager
							.K_setBandwidth(K_Constants.RF_CHANNEL_BANDWIDTH_7_MHZ);
				} else {
					mTvChannelManager
							.K_setBandwidth(K_Constants.RF_CHANNEL_BANDWIDTH_8_MHZ);
				}
				mTvChannelManager.K_switchMSrvDtvRouteCmd(currentRouteIndex);
				mTvChannelManager.K_startDtvAutoScan();
			} else if ((K_Constants.TV_ROUTE_DVBS == mCurrentRoute)
					|| K_Constants.TV_ROUTE_DVBS2 == mCurrentRoute) {
				viewholder_channeltune.text_cha_tuningprogress_rf
						.setText(getResources().getString(
								R.string.str_cha_dtvautotuning_frequency));
				viewholder_channeltune.text_cha_tuningprogress_ch
						.setVisibility(View.GONE);
				mTvChannelManager.K_switchMSrvDtvRouteCmd(currentRouteIndex);
				// mTvChannelManager.startDtvFullScan();
				mTvChannelManager.K_startDtvAutoScan();
			} else {
				//nathan.liao 2014.11.04 add for auto tuning ANR error start  
				/*
					mTvChannelManager.switchMSrvDtvRouteCmd(dtmbRouteIndex);
				}*/
				//nathan.liao 2014.11.04 add for auto tuning ANR error end 
				mTvChannelManager.K_startDtvAutoScan();
			}
		} else {
			viewholder_channeltune.text_cha_tuningprogress_type.setText("ATV");
			String str = "0%49.25";
			viewholder_channeltune.text_cha_tuningprogress_val.setText(str);
			mTvChannelManager.K_startAtvAutoTuning(ATV_EVENTINTERVAL,
					ATV_MIN_FREQ, ATV_MAX_FREQ);
		}
	}

	@Override
	protected void onResume() {
		isMhlOpen = K_TvMhlManager.getInstance().K_getAutoSwitch();
		if (isMhlOpen)
			K_TvMhlManager.getInstance().K_setAutoSwitch(false);
		viewholder_channeltune.linear_cha_mainlinear
				.setVisibility(View.VISIBLE);

		mAtvPlayerEventListener = new AtvPlayerEventListener();
		K_ChannelModel.getInstance().K_registerOnAtvPlayerEventListener(
				mAtvPlayerEventListener);

		mDtvPlayerEventListener = new DtvPlayerEventListener();
		K_ChannelModel.getInstance().K_registerOnDtvPlayerEventListener(
				mDtvPlayerEventListener);

		super.onResume();
	}

	@Override
	protected void onPause() {
		if (isMhlOpen) {
			K_TvMhlManager.getInstance().K_setAutoSwitch(true);
		}
		if (mTvCommonManager.K_getCurrentTvInputSource() == K_Constants.INPUT_SOURCE_STORAGE) {
			return;
		}

		if (scanPercent <= 100) {
			channetuningActivityLeave();
			pauseChannelTuning();
		}

		K_ChannelModel.getInstance().K_unregisterOnAtvPlayerEventListener(
				mAtvPlayerEventListener);
		mAtvPlayerEventListener = null;

		K_ChannelModel.getInstance().K_unregisterOnDtvPlayerEventListener(
				mDtvPlayerEventListener);
		mDtvPlayerEventListener = null;

		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
		case KeyEvent.KEYCODE_MENU: {
			Time curTime = new Time();
			curTime.setToNow();
			if (curTime.after(scanStartTime)) {
				if ((curTime.toMillis(true) - scanStartTime.toMillis(true)) < 2000) {
					Toast toast = Toast.makeText(ChannelTuning.this,
							getResources().getString(R.string.str_wait_moment), 1);
					toast.show();
					return false;
				}
			}
			channetuningActivityLeave();
			viewholder_channeltune.linear_cha_mainlinear
					.setVisibility(View.GONE);
			ExitTuningInfoDialog exitTuning = new ExitTuningInfoDialog(this,
					R.style.Dialog);
			exitTuning.setOnDismissListener(new OnDismissListener() {
				@Override
				public void onDismiss(DialogInterface dialog) {
					if (mTvChannelManager.K_getTuningStatus() == K_Constants.TUNING_STATUS_NONE) {
						finish();// if leave tuning this page should hide
					} else {
						viewholder_channeltune.linear_cha_mainlinear
								.setVisibility(View.VISIBLE);
						if (mTvChannelManager.K_getTuningStatus() == K_Constants.TUNING_STATUS_ATV_AUTO_TUNING) {
							viewholder_channeltune.text_cha_tuningprogress_type
									.setText("ATV");
						}
					}
				}
			});
			exitTuning.show();
		}
			return true;
		case KeyEvent.KEYCODE_TV_INPUT:
			return true;
		default:
			break;
		}
		//return super.onKeyDown(keyCode, event);
		return true;
	}

	private void channetuningActivityLeave() {
		switch (mTvChannelManager.K_getTuningStatus()) {
		case K_Constants.TUNING_STATUS_ATV_AUTO_TUNING:
			mTvChannelManager.K_pauseAtvAutoTuning();
			break;
		case K_Constants.TUNING_STATUS_DTV_AUTO_TUNING:
		case K_Constants.TUNING_STATUS_DTV_FULL_TUNING:
			mTvChannelManager.K_pauseDtvScan();
			break;
		default:
			break;
		}
	}

	private void channetuningActivityExit() {
		Log.e(TAG, "channetuningActivityExit");
		Intent intent = new Intent(TvIntent.MAINMENU);
		intent.putExtra("currentPage", MainMenuActivity.CHANNEL_PAGE);
		startActivity(intent);
	}

	private void pauseChannelTuning() {
		switch (mTvChannelManager.K_getTuningStatus()) {
		case K_Constants.TUNING_STATUS_ATV_SCAN_PAUSING:
			mTvChannelManager.K_stopAtvAutoTuning();
			mTvChannelManager.K_changeToFirstService(
					K_Constants.FIRST_SERVICE_INPUT_TYPE_ATV,
					K_Constants.FIRST_SERVICE_DEFAULT);
			break;
		case K_Constants.TUNING_STATUS_DTV_SCAN_PAUSING:
			mTvChannelManager.K_stopDtvScan();
			if (mTvChannelManager.K_getUserScanType() == K_Constants.TV_SCAN_ALL) {
				boolean res = mTvChannelManager.K_stopAtvAutoTuning();
				if (res == false) {
					Log.e(TAG, "atvSetAutoTuningStart Error!!!");
				}
			} else {
				mTvChannelManager.K_changeToFirstService(
						K_Constants.FIRST_SERVICE_INPUT_TYPE_DTV,
						K_Constants.FIRST_SERVICE_DEFAULT);
			}
			break;
		default:
			break;
		}
	}

	private void updateAtvTuningScanInfo(K_AtvEventScan extra) {
		String str = new String();
		int percent = extra.getAtvEventScan().percent;
		int frequencyKHz = extra.getAtvEventScan().frequencyKHz;
		int scannedChannelNum = extra.getAtvEventScan().scannedChannelNum;
		int curScannedChannel = extra.getAtvEventScan().curScannedChannel;
		boolean bIsScaningEnable = extra.getAtvEventScan().bIsScaningEnable;

		scanPercent = percent;

		str = "" + scannedChannelNum;
		viewholder_channeltune.text_cha_tvprogram_val.setText(str);

		str = "" + curScannedChannel;
		viewholder_channeltune.text_cha_tuningprogress_num.setText(str);

		String sFreq = " " + (frequencyKHz / 1000) + "."
				+ (frequencyKHz % 1000) / 10 + "MHz";
		str = "" + percent + '%'+"       " + sFreq;
		viewholder_channeltune.text_cha_tuningprogress_val.setText(str);
		viewholder_channeltune.progressbar_cha_tuneprogress
				.setProgress(percent);
		viewholder_channeltune.text_cha_curch_val.setVisibility(View.GONE);
		if ((percent == 100 && bIsScaningEnable == false) || (percent > 100)
				|| (frequencyKHz > ATV_MAX_FREQ)) {
			mTvChannelManager.K_stopAtvAutoTuning();

				if (mTvChannelManager.K_getUserScanType() == K_Constants.TV_SCAN_ALL) {
					if (dtvServiceCount > 0) {
						if (mTvCommonManager.K_getCurrentTvInputSource() != K_Constants.INPUT_SOURCE_DTV) {
							mTvCommonManager
									.K_setInputSource(K_Constants.INPUT_SOURCE_DTV);
						}
						mTvChannelManager.K_changeToFirstService(
								K_Constants.FIRST_SERVICE_INPUT_TYPE_DTV,
								K_Constants.FIRST_SERVICE_DEFAULT);
					} else {
						if (mTvCommonManager.K_getCurrentTvInputSource() != K_Constants.INPUT_SOURCE_ATV) {
							mTvCommonManager
									.K_setInputSource(K_Constants.INPUT_SOURCE_ATV);
						}
						mTvChannelManager.K_changeToFirstService(
								K_Constants.FIRST_SERVICE_INPUT_TYPE_ATV,
								K_Constants.FIRST_SERVICE_DEFAULT);
					}
				} else {
					if (mTvCommonManager.K_getCurrentTvInputSource() != K_Constants.INPUT_SOURCE_ATV) {
						mTvCommonManager
								.K_setInputSource(K_Constants.INPUT_SOURCE_ATV);
					}
					mTvChannelManager.K_changeToFirstService(
							K_Constants.FIRST_SERVICE_INPUT_TYPE_ATV,
							K_Constants.FIRST_SERVICE_DEFAULT);
				}
				channetuningActivityExit();
		}
	}

	private void updateDtvTuningScanInfo(K_DtvEventScan extra) {
		String str;
		int dtv = extra.getDvEventScan().dtvSrvCount;
		int radio = extra.getDvEventScan().radioSrvCount;
		int data = extra.getDvEventScan().dataSrvCount;
		int percent = extra.getDvEventScan().scanPercentageNum;
		int currRFCh = extra.getDvEventScan().currRFCh;
		int scan_status = extra.getDvEventScan().scanStatus;
		int currFrequency = extra.getDvEventScan().currFrequency;
		scanPercent = percent;

		str = "" + dtv;
		viewholder_channeltune.text_cha_dtvprogram_val.setText(str);
		str = "" + radio;
		viewholder_channeltune.text_cha_radioprogram_val.setText(str);
		str = "" + data;
		viewholder_channeltune.text_cha_dataprogram_val.setText(str);
		str = "" + percent + '%';
		viewholder_channeltune.text_cha_tuningprogress_val.setText(str);

		if ((K_Constants.TV_ROUTE_DVBS == mCurrentRoute)
				|| K_Constants.TV_ROUTE_DVBS2 == mCurrentRoute) {
			str = "" + currFrequency;
		} else {
			str = "" + currRFCh;
		}
		viewholder_channeltune.text_cha_curch_val.setText(str);
		viewholder_channeltune.progressbar_cha_tuneprogress
				.setProgress(percent);

		if (scan_status == K_EnumDtvScanStatus.K_E_STATUS_SCAN_END.ordinal()) {
			if (mTvChannelManager.K_getUserScanType() == K_Constants.TV_SCAN_ALL) {
				dtvServiceCount = dtv + radio + data;
				viewholder_channeltune.text_cha_tuningprogress_type
						.setText("ATV");

				mTvChannelManager.K_startAtvAutoTuning(ATV_EVENTINTERVAL,
						ATV_MIN_FREQ, ATV_MAX_FREQ);
			} else if (mTvChannelManager.K_getUserScanType() == K_Constants.TV_SCAN_DTV) {
				mTvChannelManager.K_changeToFirstService(
						K_Constants.FIRST_SERVICE_INPUT_TYPE_DTV,
						K_Constants.FIRST_SERVICE_DEFAULT);
				if (isDtvAutoUpdateScan) {
					finish();
				} else {
					channetuningActivityExit();
				}
			}
		}
	}
}
