//<MStar Software>
//******************************************************************************
// MStar Software
// Copyright (c) 2010 - 2014 MStar Semiconductor, Inc. All rights reserved.
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

import android.app.Instrumentation;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kguan.mtvplay.tvapi.K_ChannelModel;
import com.kguan.mtvplay.tvapi.K_Constants;
import com.kguan.mtvplay.tvapi.K_TvCommonManager;
import com.kguan.mtvplay.tvapi.K_TvDvbChannelManager;
import com.kguan.mtvplay.tvapi.K_TvManager;
import com.kguan.mtvplay.tvapi.listener.K_OnDtvPlayerEventListener;
import com.kguan.mtvplay.tvapi.listener.K_OnMhlEventListener;
import com.kguan.mtvplay.tvapi.vo.common.K_DtvProgramSignalInfo;
import com.kguan.mtvplay.tvapi.vo.dtv.K_DtvEventScan;
import com.kguan.mtvplay.tvapi.vo.dtv.K_DvbMuxInfo;
import com.kguan.mtvplay.tvapi.vo.dtv.K_RfInfo;
import com.kguan.mtvplay.tvapi.vo.dtv.K_SatelliteInfo;
import com.mstar.android.MIntent;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tv.tvplayer.ui.holder.ViewHolder;
import com.mstar.tvframework.MstarBaseActivity;
import com.mstar.util.Tools;

public class DTVManualTuning extends MstarBaseActivity {
	/** Called when the activity is first created. */

	private static final String TAG = "DTVManualTuning";

	private int mMinCh = 0;

	private int mMaxCh = 0;

	private boolean mDirectChangeCh = false;

	private boolean mOnAutoSwitch = false;

	private static final int CHANNEL_MIN_AIR = 1;

	private static final int CHANNEL_MAX_AIR = 63;

	private static final int CHANNEL_MIN_CABLE = 1;

	private static final int CHANNEL_MAX_CABLE = 135;

	private static final int USER_CHANGE_CHANNEL_TIMEOUT = 2000;

	private static final int INVALID_PHYSICAL_CHANNEL_NUMBER = 0xFF;

	private ViewHolder viewholder_dtvmanualtuning;

	private int channelno = 0;

	private int modulationindex = 2;

	private int mSymbolRate;

	private int mFrequency;

	private int inputfreq = 0;

	private int inputsymbol = 0;

	private int mDvbtRouteIndex = K_Constants.TV_ROUTE_NONE;

	private int mDtmbRouteIndex = K_Constants.TV_ROUTE_NONE;

	private int mDvbcRouteIndex = K_Constants.TV_ROUTE_NONE;

	private int mPreviousChannelNumber = -1;

	private int mDvbsRouteIndex = K_Constants.TV_ROUTE_NONE;

	private String strfreq = new String();

	private String strsymbol = new String();

	private int CAB_Type = K_Constants.DVBC_CAB_TYPE_QAM_64;

	private int mMaxFrequencyNumber = 874000;

	private int mFrequencyDigitsBound = 7;

	private int mSymbolRateDigitsBound = 5;

	private int mDefaultFrequency = 394;

	private int mDefaultSymbolRate = 6875;

	private String[] modulationtype = { "16 QAM", "32 QAM", "64 QAM",
			"128 QAM", "256 QAM" };

	private boolean bManualScanByUser = false;

	private final static short DTV_SIGNAL_REFRESH_UI = 0x01;

	private K_DtvProgramSignalInfo signalInfo;

	private boolean mIsDtvTuning = false;

	private boolean mRunthread = true;

	private K_ChannelModel mTvChannelManager = null;

	private K_OnDtvPlayerEventListener mDtvEventListener = null;

	private Handler mDtvUiEventHandler = null;

	private Handler mDirectChangeChTimeout = new Handler();

	private boolean[] mSatelliteList = null;

	private String[] mSatelliteStrList = null;

	private String[] mDvbsLNBType = null;

	private BroadcastReceiver mReceiver = null;
	private int inputDigit = 0; // zjd,20140711
	private int channelNumberInput = 0; // zjd,20140711
	private Runnable mDirectChangeChTimeoutRunnable = new Runnable() {
		@Override
		public void run() {
			mDirectChangeCh = false;
		}
	};

	private class DtvEventListener extends K_OnDtvPlayerEventListener {

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

	private final Runnable runnable = new Runnable() {
		@Override
		public void run() {
			while (true) {
				if (!mRunthread)
					break;
				signalInfo = mTvChannelManager.K_getCurrentSignalInformation();

				dtvSignalHandler.sendEmptyMessageDelayed(DTV_SIGNAL_REFRESH_UI,
						500);
				try {
					Thread.sleep(600);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// dtvSignalHandler.postDelayed(runnable, 600);
		}
	};


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dtvmanualtuning);
		viewholder_dtvmanualtuning = new ViewHolder(DTVManualTuning.this);
		viewholder_dtvmanualtuning.findViewForDtvManualTuning();
		viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_start_state
				.setTextColor(Color.GRAY);
		viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_start_state
				.setText(R.string.str_cha_atvmanualtuning_starttuning_state_reminder);
		InitialProgressValueForSignalQuality();
		InitialProgressValueForSignalStrengh();

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

		mTvChannelManager = K_ChannelModel.getInstance();
		if (K_TvCommonManager.getInstance().K_getCurrentTvInputSource() != K_Constants.INPUT_SOURCE_DTV) {
			K_TvCommonManager.getInstance().K_setInputSource(
					K_Constants.INPUT_SOURCE_DTV);
		}
		isSearchByUser = false;
		mDvbtRouteIndex = mTvChannelManager
				.K_getSpecificDtvRouteIndex(K_Constants.TV_ROUTE_DVBT);
		if (mDvbtRouteIndex < 0) {
			mDvbtRouteIndex = mTvChannelManager
					.K_getSpecificDtvRouteIndex(K_Constants.TV_ROUTE_DVBT2);
		}
		mDtmbRouteIndex = mTvChannelManager
				.K_getSpecificDtvRouteIndex(K_Constants.TV_ROUTE_DTMB);
		mDvbcRouteIndex = mTvChannelManager
				.K_getSpecificDtvRouteIndex(K_Constants.TV_ROUTE_DVBC);
		mDvbsRouteIndex = mTvChannelManager
				.K_getSpecificDtvRouteIndex(K_Constants.TV_ROUTE_DVBS);
		if (mDvbsRouteIndex < 0) {
			mDvbsRouteIndex = mTvChannelManager
					.K_getSpecificDtvRouteIndex(K_Constants.TV_ROUTE_DVBS2);
		}

		getCurrentFreqAndSymRate();
		mTvChannelManager.K_stopDtvScan();
		updatedtvManualtuningComponents();
		bManualScanByUser = false;
			int currentIndex = mTvChannelManager.K_getCurrentDtvRouteIndex();
			if (mDvbsRouteIndex == currentIndex) {
				mMaxFrequencyNumber = 99999;
				mFrequencyDigitsBound = 6;
				mSymbolRateDigitsBound = 6;
				mDefaultFrequency = 0;
				mDefaultSymbolRate = 0;
				mDvbsLNBType = getResources().getStringArray(
						R.array.str_arr_dtvmanualtuning_lnbtype_vals);
				// init satellite list used for selecting satellite
				int satCount = K_TvDvbChannelManager.getInstance()
						.K_getCurrentSatelliteCount();
				mSatelliteStrList = new String[satCount];
				mSatelliteList = new boolean[satCount];
				for (int i = 0; i < satCount; i++) {
					// update satellite list
					K_SatelliteInfo satInfo = K_TvDvbChannelManager.getInstance()
							.K_getSatelliteInfo(i);
					mSatelliteStrList[i] = i + " " + satInfo.getSatelliteInfo().satName + " "
							+ mDvbsLNBType[satInfo.getSatelliteInfo().lnbType];
					// update default enable list to false
					mSatelliteList[i] = false;
				}
				// show current satellite naming as default one
				int satNumber = K_TvDvbChannelManager.getInstance()
						.K_getCurrentSatelliteNumber();
				mSatelliteList[satNumber] = true;
			}
		setOnFocusChangeListeners();
		mRunthread = true;
		new Thread(runnable).start();
		K_TvManager.getInstance().K_getMhlManager()
				.setOnMhlEventListener(new K_OnMhlEventListener() {

					@Override
					public boolean K_onAutoSwitch(int arg0, int arg1, int arg2) {
						Log.d(TAG, "onAutoSwitch");
						mOnAutoSwitch = true;
						finish();
						return false;
					}

					@Override
					public boolean K_onKeyInfo(int arg0, int arg1, int arg2) {
						// TODO Auto-generated method stub
						return false;
					}/*

					@Override
					public boolean onKeyInfo(int arg0, int arg1, int arg2) {
						Log.d(TAG, "onKeyInfo");
						return false;
					}

					@Override
					public boolean onAutoSwitch(int arg0, int arg1, int arg2) {
						Log.d(TAG, "onAutoSwitch");
						mOnAutoSwitch = true;
						finish();
						return false;
					}
				*/});

		mDtvUiEventHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				updateDtvTuningScanInfo((K_DtvEventScan) msg.obj);
			}
		};
	}

	private Handler dtvSignalHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case DTV_SIGNAL_REFRESH_UI:
				if (null == signalInfo) {
					return;
				}
				if (signalInfo.getDtvProgramSignalInfo().quality <= 0) {
					setProgressValueForSignalQuality(0);
					setProgressValueForSignalStrengh(0);
				} else {
					setProgressValueForSignalQuality(signalInfo.getDtvProgramSignalInfo().quality / 10);
					setProgressValueForSignalStrengh(signalInfo.getDtvProgramSignalInfo().strength / 10);
				}
				break;

			default:
				break;
			}

		}
	};

	public boolean MapKeyPadToIR(int keyCode, KeyEvent event) {
		String deviceName = InputDevice.getDevice(event.getDeviceId())
				.getName();
		if (!deviceName.equals("MStar Smart TV Keypad"))
			return false;
		switch (keyCode) {
		case KeyEvent.KEYCODE_CHANNEL_UP:
			keyInjection(KeyEvent.KEYCODE_DPAD_UP);
			return true;
		case KeyEvent.KEYCODE_CHANNEL_DOWN:
			keyInjection(KeyEvent.KEYCODE_DPAD_DOWN);
			return true;
		default:
			return false;
		}

	}

	private void keyInjection(final int keyCode) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN
				|| keyCode == KeyEvent.KEYCODE_DPAD_UP
				|| keyCode == KeyEvent.KEYCODE_DPAD_RIGHT
				|| keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			new Thread() {
				public void run() {
					try {
						Instrumentation inst = new Instrumentation();
						inst.sendKeyDownUpSync(keyCode);
					} catch (Exception e) {
						Log.e(TAG, e.toString());
					}
				}
			}.start();
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// If MapKeyPadToIR returns true,the previous keycode has been changed
		// to responding
		// android d_pad keys,just return.
		if (MapKeyPadToIR(keyCode, event))
			return true;
		Intent intent = new Intent();
		int currentid = getCurrentFocus().getId();
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_RIGHT:
		case KeyEvent.KEYCODE_VOLUME_UP:
			switch (currentid) {
			case R.id.linearlayout_cha_dtvmanualtuning_channelnum: {
				inputDigit = 0;// zjd,20140711
				String name = null;
				if ((mTvChannelManager.K_getCurrentDtvRouteIndex() == mDvbtRouteIndex)
						|| (mTvChannelManager.K_getCurrentDtvRouteIndex() == mDtmbRouteIndex)) {
					K_RfInfo rfInfo = null;
					rfInfo = mTvChannelManager.K_getRfInfo(
							K_Constants.NEXT_RF, channelno);
					channelno = rfInfo.getRfInfo().rfPhyNum;
					name = rfInfo.getRfInfo().rfName;
					viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_frequency_val
							.setText(Integer.toString(rfInfo.getRfInfo().frequency));
				}
				viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_channelnum_val
						.setText(name);
				break;
			}

			case R.id.linearlayout_cha_dtvmanualtuning_modulation:
				if (modulationindex == K_Constants.DVBC_CAB_TYPE_QAM_256)
					modulationindex = 0;
				else
					modulationindex++;
				CAB_Type = modulationindex;
				viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_modulation_val
						.setText(modulationtype[modulationindex]);
				break;
			case R.id.linearlayout_cha_dtvmanualtuning_symbol:
				break;
			/*
			 * case R.id.linearlayout_cha_dtvmanualtuning_frequency: {
			 * mFrequency = (mFrequency + 1) % mMaxFrequencyNumber;
			 * updatedtvManualtuningComponents(); } break;
			 */
			case R.id.linearlayout_cha_dtvmanualtuning_starttuning: {
				if (Tools.isBox()) {
					Log.d(TAG, "dtv manual tuning for box");
					// wait for the tuning done
					if (mIsDtvTuning) {
						Toast.makeText(this, R.string.wait_for_tuning_hint,
								Toast.LENGTH_SHORT).show();
					} else {
						// Hisa 2016.03.04 add Freeze function start
						Intent intentCancel = new Intent();//È¡Ïû¾²Ïñ²Ëµ¥
						intentCancel.setAction(MIntent.ACTION_FREEZE_CANCEL_BUTTON);
						//K_TvPictureManager.getInstance().K_unFreezeImage();
						sendBroadcast(intentCancel);  
						// Hisa 2016.03.04 add Freeze function end
						startdtvmanutuning();
					}
					return super.onKeyDown(keyCode, event);
				}
				Log.d(TAG, "dtv manual tuning");
				isSearchByUser = true;
				startdtvmanutuning();
			}
				break;
			default:
				break;
			}
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			switch (currentid) {
			case R.id.linearlayout_cha_dtvmanualtuning_channelnum:
				inputDigit = 0;// zjd,20140711
				String channelName = null;
				if ((mTvChannelManager.K_getCurrentDtvRouteIndex() == mDvbtRouteIndex)
						|| (mTvChannelManager.K_getCurrentDtvRouteIndex() == mDtmbRouteIndex)) {
					K_RfInfo rfInfo = null;
					rfInfo = mTvChannelManager.K_getRfInfo(
							K_Constants.PREVIOUS_RF, channelno);
					channelno = rfInfo.getRfInfo().rfPhyNum;
					channelName = rfInfo.getRfInfo().rfName;
					viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_frequency_val
							.setText(Integer.toString(rfInfo.getRfInfo().frequency));
				}
				viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_channelnum_val
						.setText(channelName);
				break;
			case R.id.linearlayout_cha_dtvmanualtuning_modulation:
				if (modulationindex == K_Constants.DVBC_CAB_TYPE_QAM_16)
					modulationindex = K_Constants.DVBC_CAB_TYPE_QAM_256;
				else
					modulationindex--;
				CAB_Type = modulationindex;
				viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_modulation_val
						.setText(modulationtype[modulationindex]);
				break;
			case R.id.linearlayout_cha_dtvmanualtuning_symbol:
				break;
			/*
			 * case R.id.linearlayout_cha_dtvmanualtuning_frequency: mFrequency
			 * = (mFrequency + mMaxFrequencyNumber - 1) % mMaxFrequencyNumber;
			 * updatedtvManualtuningComponents(); break;
			 */
			case R.id.linearlayout_cha_dtvmanualtuning_starttuning: {
				if (Tools.isBox()) {
					Log.d(TAG, "dtv manual tuning for box");
					// wait for the tuning done
					if (mIsDtvTuning) {
						Toast.makeText(this, R.string.wait_for_tuning_hint,
								Toast.LENGTH_SHORT).show();
					} else {
						// Hisa 2016.03.04 add Freeze function start
						Intent intentCancel = new Intent();//È¡Ïû¾²Ïñ²Ëµ¥
						intentCancel.setAction(MIntent.ACTION_FREEZE_CANCEL_BUTTON);
						//K_TvPictureManager.getInstance().K_unFreezeImage();
						sendBroadcast(intentCancel);  
						// Hisa 2016.03.04 add Freeze function end
						startdtvmanutuning();
					}
					return super.onKeyDown(keyCode, event);
				}
				Log.d(TAG, "dtv manual tuning");
				isSearchByUser = true;
				startdtvmanutuning();
			}
				break;
			default:
				break;
			}
			break;
		case KeyEvent.KEYCODE_0:
		case KeyEvent.KEYCODE_1:
		case KeyEvent.KEYCODE_2:
		case KeyEvent.KEYCODE_3:
		case KeyEvent.KEYCODE_4:
		case KeyEvent.KEYCODE_5:
		case KeyEvent.KEYCODE_6:
		case KeyEvent.KEYCODE_7:
		case KeyEvent.KEYCODE_8:
		case KeyEvent.KEYCODE_9:
			switch (currentid) {
			case R.id.linearlayout_cha_dtvmanualtuning_channelnum:
					inputDigit++;
					if (inputDigit > 2) {
						inputDigit = 0;
						channelNumberInput = 0;
						updatedtvManualtuningComponents();
						return true;
					}

					int inputnum = keyCode - KeyEvent.KEYCODE_0;

					if (inputDigit == 1) {
						channelNumberInput = inputnum;
					} else if (inputDigit == 2) {
						channelNumberInput = channelNumberInput * 10 + inputnum;
					}
					String str_num;
					str_num = Integer.toString(channelNumberInput);

					viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_channelnum_val
							.setText(str_num);
				mDirectChangeChTimeout
						.removeCallbacks(mDirectChangeChTimeoutRunnable);
				mDirectChangeChTimeout.postDelayed(
						mDirectChangeChTimeoutRunnable,
						USER_CHANGE_CHANNEL_TIMEOUT);
				return true;
			case R.id.linearlayout_cha_dtvmanualtuning_frequency:
				inputfreq = keyCode - KeyEvent.KEYCODE_0;
				inputFrequencyNumber(inputfreq);
				return true;
			case R.id.linearlayout_cha_dtvmanualtuning_symbol:
				inputsymbol = keyCode - KeyEvent.KEYCODE_0;
				inputSymbolNumber(inputsymbol);
				return true;
			default:
				break;
			}
			break;
		case KeyEvent.KEYCODE_ENTER:
		case KeyEvent.KEYCODE_TV_INPUT:
			switch (currentid) {
			case R.id.linearlayout_cha_dtvmanualtuning_starttuning: {
				if (Tools.isBox()) {
					Log.d(TAG, "dtv manual tuning for box");
					// wait for the tuning done
					if (mIsDtvTuning) {
						Toast.makeText(this, R.string.wait_for_tuning_hint,
								Toast.LENGTH_SHORT).show();
					} else {
						// Hisa 2016.03.04 add Freeze function start
						Intent intentCancel = new Intent();//È¡Ïû¾²Ïñ²Ëµ¥
						intentCancel.setAction(MIntent.ACTION_FREEZE_CANCEL_BUTTON);
						//K_TvPictureManager.getInstance().K_unFreezeImage();
						sendBroadcast(intentCancel);  
						// Hisa 2016.03.04 add Freeze function end
						startdtvmanutuning();
					}
					return super.onKeyDown(keyCode, event);
				}
				Log.d(TAG, "dtv manual tuning");
				isSearchByUser = true;
				startdtvmanutuning();
			}
				return true;
			case R.id.linearlayout_cha_dtvmanualtuning_frequency: {
				int freq = 0;
				if (strfreq != null) {
					if (strfreq.length() > 3 && strfreq.length() < 7) {
						freq = Integer.parseInt(strfreq);
						if (freq > 858000L || strfreq.length() >= 7) {
							mFrequency = 858000;
						} else if (freq < 52000) {
							mFrequency = 52000;
						} else {
							mFrequency = freq;
						}
					}
					strfreq = null;
					fretonu(mFrequency);
				}
			}
				return true;
			// ////////////////////////////////////////////zjd,20140711
			case R.id.linearlayout_cha_dtvmanualtuning_channelnum:
				if (inputDigit > 0) {
					inputDigit = 0;
					if (channelNumberInput <= CHANNEL_MAX_AIR && channelNumberInput > 0) {
						String channelName = null;
						if ((mTvChannelManager.K_getCurrentDtvRouteIndex() == mDvbtRouteIndex)
								|| (mTvChannelManager.K_getCurrentDtvRouteIndex() == mDtmbRouteIndex)) {
							K_RfInfo rfInfo = null;
							rfInfo = mTvChannelManager.K_getRfInfo(
									K_Constants.RF_INFO, channelNumberInput);
							channelno = rfInfo.getRfInfo().rfPhyNum;
							channelName = rfInfo.getRfInfo().rfName;
							viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_frequency_val
									.setText(Integer.toString(rfInfo.getRfInfo().frequency));
						}

						viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_channelnum_val
								.setText(channelName);
					}else
					{
						viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_channelnum_val
						.setText(Integer.toString(channelno));
					}
						return true;
					
				}

				return true;
			// ///////////////////////////////////////////////////
			default:
				break;
			}
			break;
		case KeyEvent.KEYCODE_BACK:
		case KeyEvent.KEYCODE_MENU:
			intent.setAction(TvIntent.MAINMENU);
			intent.putExtra("currentPage", MainMenuActivity.CHANNEL_PAGE);
			startActivity(intent);
			finish();
			return true;
		default:
			break;
		}
		if (KeyEvent.KEYCODE_TV_INPUT == keyCode)
			if (bManualScanByUser)
			return true;
			else
			finish();
		return super.onKeyDown(keyCode, event);
	}

	private static final int FINISH_DELAY_FOR_IDLE = 60 * 1000;

	private static final int FINISH_DELAY_FOR_PROG_FOUND = 5 * 1000;

	// Whether search is triggered by user or auto start when enter this screen.
	private boolean isSearchByUser = false;

	private void startdtvmanutuning() {
		mIsDtvTuning = true;
		String Sfreq = viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_frequency_val
				.getText().toString();
		String Ssymbol = viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_symbol_val
				.getText().toString();
		mSymbolRate = (short) Integer.parseInt(Ssymbol);
		mFrequency = Integer.parseInt(Sfreq);
		viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_start_state
				.setTextColor(Color.WHITE);
		viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_start_state
				.setText(R.string.str_cha_atvmanualtuning_starttuning_state);
		Log.d(TAG, "symb=" + mSymbolRate + " freq=" + mFrequency);
			if (mTvChannelManager.K_getCurrentDtvRouteIndex() == mDvbcRouteIndex) {
				mTvChannelManager.K_setDvbcScanParam((short) mSymbolRate,
						CAB_Type, 0, 0, (short) 0x0000);
				mTvChannelManager.K_setDtvManualScanByFreq(mFrequency * 1000);
				mTvChannelManager.K_startDtvManualScan();
			} else if (mTvChannelManager.K_getCurrentDtvRouteIndex() == mDvbtRouteIndex) {
				mTvChannelManager.K_setDtvManualScanByRF(channelno);
				mTvChannelManager.K_startDtvManualScan();
			} else if (mTvChannelManager.K_getCurrentDtvRouteIndex() == mDtmbRouteIndex) {
				mTvChannelManager.K_setDtvManualScanByRF(channelno);
				mTvChannelManager.K_startDtvManualScan();
			} 
		bManualScanByUser = true;
		mPreviousChannelNumber = channelno;
	}

	private void getCurrentFreqAndSymRate() {
		/*
		 * if current dtv program count is not zero, use current frequency and
		 * symbol rate otherwise using default frequency and default symbol
		 * rate.
		 */
		int m_nServiceNum = mTvChannelManager
				.K_getProgramCount(K_Constants.PROGRAM_COUNT_DTV);
		if (m_nServiceNum > 0) {
			K_DvbMuxInfo dmi = mTvChannelManager.K_getCurrentMuxInfo();
			if (dmi != null) {
				// dvbs frequency needn't to divide , bacause it's already MHz.
				if (mTvChannelManager.K_getCurrentDtvRouteIndex() == mDvbsRouteIndex) {
					mFrequency = dmi.getDvbMuxInfo().frequency;
				} else {
					mFrequency = (dmi.getDvbMuxInfo().frequency / 1000);
				}
				mSymbolRate = dmi.getDvbMuxInfo().symbRate;
			} else {
				// use default value if get muxinfo error
				mFrequency = mDefaultFrequency;
				mSymbolRate = mDefaultSymbolRate;
				Log.e(TAG, "getCurrentMuxInfo error");
			}

		} else {
			mFrequency = mDefaultFrequency;
			mSymbolRate = mDefaultSymbolRate;
		}

	}

	private void updatedtvManualtuningComponents() {
		// set item show/hide
		viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_symbol_val
				.setText(Integer.toString(mSymbolRate));
		strfreq = null;
		inputDigit = 0; 
	    channelNumberInput = 0; 
		/*viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_frequency_val
				.setText(Integer.toString(mFrequency));*/
		if ((mTvChannelManager.K_getCurrentDtvRouteIndex() == mDvbtRouteIndex)
				|| (mTvChannelManager.K_getCurrentDtvRouteIndex() == mDtmbRouteIndex)) {
			K_RfInfo rfInfo = null;
			rfInfo = mTvChannelManager.K_getRfInfo(
					K_Constants.FIRST_TO_SHOW_RF, 0);
			if (rfInfo != null) {
				channelno = rfInfo.getRfInfo().rfPhyNum;
				viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_frequency_val
				.setText(Integer.toString(rfInfo.getRfInfo().frequency));
				viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_channelnum_val
						.setText("" + channelno);
				mPreviousChannelNumber = channelno;
			}
			/*LinearLayout ln = (LinearLayout) findViewById(R.id.linearlayout_cha_dtvmanualtuning_frequency);
			ln.setVisibility(View.GONE);*/
			LinearLayout ln = (LinearLayout) findViewById(R.id.linearlayout_cha_dtvmanualtuning_modulation);
			ln.setVisibility(View.GONE);
			ln = (LinearLayout) findViewById(R.id.linearlayout_cha_dtvmanualtuning_symbol);
			ln.setVisibility(View.GONE);
		} else if (mTvChannelManager.K_getCurrentDtvRouteIndex() == mDvbsRouteIndex) {
			LinearLayout ln = (LinearLayout) findViewById(R.id.linearlayout_cha_dtvmanualtuning_channelnum);
			ln.setVisibility(View.GONE);
			ln = (LinearLayout) findViewById(R.id.linearlayout_cha_dtvmanualtuning_modulation);
			ln.setVisibility(View.GONE);
		} else {
			LinearLayout ln = (LinearLayout) findViewById(R.id.linearlayout_cha_dtvmanualtuning_channelnum);
			ln.setVisibility(View.GONE);
		}
	}

	private void setProgressValueForSignalQuality(int val) {
		if (val <= 10 && val > 0) {
			for (int i = 0; i <= val - 1; i++) {
				ImageView searchImage = (ImageView) (viewholder_dtvmanualtuning.linear_cha_dtvmanualtuning_signalquality_val
						.getChildAt(i));
				searchImage.setImageDrawable(getResources().getDrawable(
						R.drawable.picture_serchprogressbar_solid));
			}
			for (int i = val; i <= 9; i++) {
				ImageView searchImage = (ImageView) (viewholder_dtvmanualtuning.linear_cha_dtvmanualtuning_signalquality_val
						.getChildAt(i));
				searchImage.setImageDrawable(getResources().getDrawable(
						R.drawable.picture_serchprogressbar_empty));
			}
		} else if (val > 10) {
			for (int i = 0; i <= 9; i++) {
				ImageView searchImage = (ImageView) (viewholder_dtvmanualtuning.linear_cha_dtvmanualtuning_signalquality_val
						.getChildAt(i));
				searchImage.setImageDrawable(getResources().getDrawable(
						R.drawable.picture_serchprogressbar_solid));
			}
		} else if (val == 0) {
			for (int i = 0; i <= 9; i++) {
				ImageView searchImage = (ImageView) (viewholder_dtvmanualtuning.linear_cha_dtvmanualtuning_signalquality_val
						.getChildAt(i));
				searchImage.setImageDrawable(getResources().getDrawable(
						R.drawable.picture_serchprogressbar_empty));
			}
		}
	}

	private void InitialProgressValueForSignalQuality() {
		for (int i = 0; i <= 9; i++) {
			ImageView searchImage = (ImageView) (viewholder_dtvmanualtuning.linear_cha_dtvmanualtuning_signalquality_val
					.getChildAt(i));
			searchImage.setImageDrawable(getResources().getDrawable(
					R.drawable.picture_serchprogressbar_empty));
		}
	}

	private void setProgressValueForSignalStrengh(int val) {

		if (val <= 10 && val > 0) {
			for (int i = 0; i <= val - 1; i++) {
				ImageView searchImage = (ImageView) (viewholder_dtvmanualtuning.linear_cha_dtvmanualtuning_signalstrength_val
						.getChildAt(i));
				searchImage.setImageDrawable(getResources().getDrawable(
						R.drawable.picture_serchprogressbar_solid));
			}
			for (int i = val; i <= 9; i++) {
				ImageView searchImage = (ImageView) (viewholder_dtvmanualtuning.linear_cha_dtvmanualtuning_signalstrength_val
						.getChildAt(i));
				searchImage.setImageDrawable(getResources().getDrawable(
						R.drawable.picture_serchprogressbar_empty));
			}
		} else if (val > 10) {
			for (int i = 0; i <= 9; i++) {
				ImageView searchImage = (ImageView) (viewholder_dtvmanualtuning.linear_cha_dtvmanualtuning_signalstrength_val
						.getChildAt(i));
				searchImage.setImageDrawable(getResources().getDrawable(
						R.drawable.picture_serchprogressbar_solid));
			}
		} else if (val == 0) {
			for (int i = 0; i <= 9; i++) {
				ImageView searchImage = (ImageView) (viewholder_dtvmanualtuning.linear_cha_dtvmanualtuning_signalstrength_val
						.getChildAt(i));
				searchImage.setImageDrawable(getResources().getDrawable(
						R.drawable.picture_serchprogressbar_empty));
			}
		}
	}

	private void InitialProgressValueForSignalStrengh() {
		for (int i = 0; i <= 9; i++) {
			ImageView searchImage = (ImageView) (viewholder_dtvmanualtuning.linear_cha_dtvmanualtuning_signalstrength_val
					.getChildAt(i));
			searchImage.setImageDrawable(getResources().getDrawable(
					R.drawable.picture_serchprogressbar_empty));
		}
	}

	private void inputFrequencyNumber(int inputno) {
		int freq = 0;
		if(strfreq != null)
			strfreq = strfreq + Integer.toString(inputno);
			else strfreq = Integer.toString(inputno);
		freq = Integer.parseInt(strfreq);
		if (freq > mMaxFrequencyNumber) {
			strfreq = Integer.toString(inputno);
			// mFrequency = inputno;
		} else if (strfreq.length() >= mFrequencyDigitsBound) {
			strfreq = Integer.toString(inputno);
			// mFrequency = inputno;
		} else {
			// mFrequency = freq;
		}
		viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_frequency_val
				.setText(strfreq);
		return;
	}

	private void inputSymbolNumber(int inputno) {
		short symbol = 0;
		strsymbol = strsymbol + Integer.toString(inputno);
		symbol = (short) Integer.parseInt(strsymbol);
		if (strsymbol.length() >= mSymbolRateDigitsBound) {
			strsymbol = Integer.toString(inputno);
			mSymbolRate = (short) inputno;
		} else {
			mSymbolRate = symbol;
		}
		viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_symbol_val
				.setText(strsymbol);
		return;
	}

	@Override
	protected void onResume() {
		super.onResume();

		mDtvEventListener = new DtvEventListener();
		K_ChannelModel.getInstance().K_registerOnDtvPlayerEventListener(
				mDtvEventListener);
	}

	@Override
	protected void onPause() {
		// When the DTV searching is interrupted
		if (bManualScanByUser) {
			mTvChannelManager.K_stopDtvScan();
			if (!mOnAutoSwitch) {
					mTvChannelManager.K_changeToFirstService(
							K_Constants.FIRST_SERVICE_INPUT_TYPE_DTV,
							K_Constants.FIRST_SERVICE_DEFAULT);
			}
		}
		K_ChannelModel.getInstance().K_unregisterOnDtvPlayerEventListener(
				mDtvEventListener);
		mDtvEventListener = null;

		super.onPause();
	}

	@Override
	protected void onDestroy() {
		mRunthread = false;
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}

	private void updateDtvTuningScanInfo(K_DtvEventScan extra) {
		String str;

		if (isSearchByUser) {
			str = "" + extra.getDvEventScan().dtvSrvCount;
			viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_tuningresult_dtv_val
					.setText(str);

			str = "" + extra.getDvEventScan().radioSrvCount;
			viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_tuningresult_radio_val
					.setText(str);

			str = "" + extra.getDvEventScan().dataSrvCount;
			viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_tuningresult_data_val
					.setText(str);
		}

		setProgressValueForSignalQuality((extra.getDvEventScan().signalQuality / 10));
		setProgressValueForSignalStrengh((extra.getDvEventScan().signalStrength / 10));

		if (extra.getDvEventScan().scanStatus == K_Constants.E_STATUS_SCAN_END.ordinal()) {
			viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_start_state
					.setTextColor(Color.GRAY);
			viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_start_state
					.setText(R.string.str_cha_atvmanualtuning_starttuning_state_reminder);
				if ((extra.getDvEventScan().dtvSrvCount + extra.getDvEventScan().radioSrvCount + extra.getDvEventScan().dataSrvCount) > 0) {
					mTvChannelManager.K_changeToFirstService(
							K_Constants.FIRST_SERVICE_INPUT_TYPE_DTV,
							K_Constants.FIRST_SERVICE_DEFAULT);
					bManualScanByUser = false;

					int delay = FINISH_DELAY_FOR_IDLE;
					if ((extra.getDvEventScan().dtvSrvCount + extra.getDvEventScan().radioSrvCount + extra.getDvEventScan().dataSrvCount) > 0
							&& isSearchByUser) {
						delay = FINISH_DELAY_FOR_PROG_FOUND;
					}
				}
				isSearchByUser = false;
		}
	}

	private void fretonu(int u32Frequency) {
		Log.e("mstar.tvsetting.ui", "u32Frequency:" + u32Frequency);
		if(u32Frequency <= 52500L )channelno = 1;
		else if(u32Frequency <= 60500L )channelno = 2;
		else if(u32Frequency <= 68500L )channelno = 3;
		else if(u32Frequency <= 80000L )channelno = 4;
		else if(u32Frequency <= 88000L )channelno = 5;
		else if(u32Frequency <= 171000L )channelno = 6;
		else if(u32Frequency <= 179000L )channelno = 7;
		
		else if(u32Frequency <= 187000L )channelno = 8;
		else if(u32Frequency <= 195000L )channelno = 9;
		else if(u32Frequency <= 203000L )channelno = 10;
		else if(u32Frequency <= 211000L )channelno = 11;
		else if(u32Frequency <= 219000L )channelno = 12;
		else if(u32Frequency <= 474000L )channelno = 13;
		else //if(u32Frequency <= 610000L )
        {
        	channelno = (int)(((u32Frequency - 474000L) / 8000L) + 13);
        }
//        else
//        {
//        	channelno = (int)(((u32Frequency - 610000L) / 8000L) + 25);
//        }
        Log.e("TvApp", "channelno:" + channelno);
        String name = "";
        if ((mTvChannelManager.K_getCurrentDtvRouteIndex() == mDvbtRouteIndex)
				|| (mTvChannelManager.K_getCurrentDtvRouteIndex() == mDtmbRouteIndex)) {
        	K_RfInfo rfInfo = null;
			rfInfo = mTvChannelManager.K_getRfInfo(
					K_Constants.RF_INFO, channelno);
			channelno = rfInfo.getRfInfo().rfPhyNum;
			name = rfInfo.getRfInfo().rfName;
			viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_frequency_val
					.setText(Integer.toString(rfInfo.getRfInfo().frequency));
		}
		viewholder_dtvmanualtuning.text_cha_dtvmanualtuning_channelnum_val
				.setText(name);
		return;
	}
	
	private void setOnFocusChangeListeners() {
		OnFocusChangeListener FocuschangesListener = new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(inputDigit != 0 && channelNumberInput != 0)
				updatedtvManualtuningComponents();
			}
		};
		viewholder_dtvmanualtuning.linear_cha_dtvmanualtuning_channelfreq.setOnFocusChangeListener(FocuschangesListener);
		viewholder_dtvmanualtuning.linear_cha_dtvmanualtuning_channelnum.setOnFocusChangeListener(FocuschangesListener);
		
	}
}
