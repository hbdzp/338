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

package com.mstar.tv.tvplayer.ui.channel;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kguan.mtvplay.tvapi.K_ChannelModel;
import com.kguan.mtvplay.tvapi.K_Constants;
import com.kguan.mtvplay.tvapi.K_TvCommonManager;
import com.kguan.mtvplay.tvapi.listener.K_OnAtvPlayerEventListener;
import com.kguan.mtvplay.tvapi.listener.K_OnDtvPlayerEventListener;
import com.kguan.mtvplay.tvapi.vo.K_ProgramInfo;
import com.kguan.mtvplay.tvapi.vo.atv.K_AtvEventScan;
import com.kguan.mtvplay.tvapi.vo.dtv.K_DtvEventScan;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.SwitchPageHelper;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tvframework.MstarBaseActivity;

public class ChannelControlActivity extends MstarBaseActivity {

	private final static String TAG = "ChannelControlActivity";

	private final static int WAIT_EXPIRE_TIME = 3000;

	private final static int DIRECT_TUNE_MESSAGE = 1;

	private final static int FINISH_DELAY_FOR_IDLE = 2 * 1000;

	private final static int FINISH_DELAY_FOR_PROG_FOUND = 3 * 1000;

	private int mInputDigitMajor = 0;

	private int mChannelNumberInput = 0;

	private int mPreChannelNumber = 0;

	private int mCurRFScanNumber = 0;

	private boolean mIsDtvTuning = false;

	private boolean mIsAtvTuning = false;

	private ImageView mTvNumberIcon1;

	private ImageView mTvNumberIcon2;

	private ImageView mTvNumberIcon3;

	private ImageView mTvNumberIcon4;

	private ImageView mTvNumberIcon5;

	private ImageView mTvNumberIcon6;

	private ImageView mTvNumberIcon7;

	private ImageView mTvNumberIcon8;

	private ImageView mTvNumberIconDot;

	private TextView mChannelScan;

	private TextView mChannelNum;

	private int mInputSource = K_Constants.INPUT_SOURCE_DTV;

	private K_ChannelModel mTvChannelManager = null;

	private K_TvCommonManager mTvCommonManager = null;

	private K_OnDtvPlayerEventListener mDtvPlayerEventListener = null;

	// FIXME: remove if use new method instead of handler
	private Handler mDtvUiEventHandler = null;

	private K_OnAtvPlayerEventListener mAtvPlayerEventListener = null;

	// FIXME: remove if use new method instead of handler
	private Handler mAtvUiEventHandler = null;

	private Context mThisContext = this;
	private int is_enter_press = 0;

	private ArrayList<K_ProgramInfo> mProgramNumbers = new ArrayList<K_ProgramInfo>();

	private final static int[] mNumberResIds = { R.drawable.popup_img_number_0,
			R.drawable.popup_img_number_1, R.drawable.popup_img_number_2,
			R.drawable.popup_img_number_3, R.drawable.popup_img_number_4,
			R.drawable.popup_img_number_5, R.drawable.popup_img_number_6,
			R.drawable.popup_img_number_7, R.drawable.popup_img_number_8,
			R.drawable.popup_img_number_9 };

	private Handler finishHandler = new Handler();

	private Runnable finishTask = new Runnable() {

		@Override
		public void run() {
			Intent intent = new Intent(TvIntent.ACTION_SOURCEINFO);
			intent.putExtra("info_key", true);
			mThisContext.startActivity(intent);
			ChannelControlActivity.this.finish();
		}
	};

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case DIRECT_TUNE_MESSAGE:
				if (isProgramExist()) {
					if (mInputSource == K_Constants.INPUT_SOURCE_ATV) {
							mTvChannelManager.K_selectProgram(
									(mChannelNumberInput - 1),
									K_Constants.SERVICE_TYPE_ATV);
					} else if (mInputSource == K_Constants.INPUT_SOURCE_DTV) {
							mTvChannelManager.K_selectProgram(
									mChannelNumberInput,
									K_Constants.SERVICE_TYPE_DTV);
					}
				} 
				is_enter_press = 0;
				mChannelNumberInput = 0;
				mInputDigitMajor = 0;

				if ((mIsDtvTuning == false) && (mIsAtvTuning == false)) {
					Intent intent = new Intent(TvIntent.ACTION_SOURCEINFO);
					intent.putExtra("info_key", true);
					mThisContext.startActivity(intent);
					finish();
				}
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "OnCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.channelcontrol);

		mTvChannelManager = K_ChannelModel.getInstance();
		mTvCommonManager = K_TvCommonManager.getInstance();

		mTvNumberIcon1 = (ImageView) findViewById(R.id.channel_control_num1);
		mTvNumberIcon2 = (ImageView) findViewById(R.id.channel_control_num2);
		mTvNumberIcon3 = (ImageView) findViewById(R.id.channel_control_num3);
		mTvNumberIcon4 = (ImageView) findViewById(R.id.channel_control_num4);
		mTvNumberIconDot = (ImageView) findViewById(R.id.channel_control_dot);
		mTvNumberIcon5 = (ImageView) findViewById(R.id.channel_control_num5);
		mTvNumberIcon6 = (ImageView) findViewById(R.id.channel_control_num6);
		mTvNumberIcon7 = (ImageView) findViewById(R.id.channel_control_num7);
		mTvNumberIcon8 = (ImageView) findViewById(R.id.channel_control_num8);
		mChannelScan = (TextView) findViewById(R.id.channel_control_scan);
		mChannelNum = (TextView) findViewById(R.id.channel_control_channels);

			mTvNumberIconDot.setVisibility(View.GONE);
			mTvNumberIcon4.setVisibility(View.GONE);
			mTvNumberIcon5.setVisibility(View.GONE);
			mTvNumberIcon6.setVisibility(View.GONE);
			mTvNumberIcon7.setVisibility(View.GONE);
			mTvNumberIcon8.setVisibility(View.GONE);

		mChannelScan.setVisibility(View.GONE);
		mChannelNum.setVisibility(View.GONE);

		mInputSource = mTvCommonManager.K_getCurrentTvInputSource();
		int channel_key = getIntent()
				.getIntExtra("KeyCode", KeyEvent.KEYCODE_1);
		getProgList();
			tenKeyCode(channel_key);
		// FIXME: remove if use new method instead of handler
		mDtvUiEventHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				updateDtvTuningScanInfo((K_DtvEventScan) msg.obj);
			}
		};
		// FIXME: remove if use new method instead of handler
		mAtvUiEventHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				updateAtvTuningScanInfo((K_AtvEventScan) msg.obj);
			}
		};
	}

	@Override
	protected void onResume() {
		super.onResume();
		mDtvPlayerEventListener = new DtvEventListener();
		mAtvPlayerEventListener = new AtvPlayerEventListener();
		mTvChannelManager
				.K_registerOnDtvPlayerEventListener(mDtvPlayerEventListener);
		mTvChannelManager
				.K_registerOnAtvPlayerEventListener(mAtvPlayerEventListener);
	}

	@Override
	protected void onPause() {
		if (mIsDtvTuning) {
			mTvChannelManager.K_stopDtvScan();
		}
		mTvChannelManager
				.K_registerOnDtvPlayerEventListener(mDtvPlayerEventListener);
		mTvChannelManager
				.K_registerOnAtvPlayerEventListener(mAtvPlayerEventListener);
		mDtvPlayerEventListener = null;
		mAtvPlayerEventListener = null;
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (SwitchPageHelper.goToMenuPage(this, keyCode) == true) {
			finish();
			return true;
		} else if (SwitchPageHelper.goToEpgPage(this, keyCode) == true) {
			finish();
			return true;
		}
		switch (keyCode) {
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
				tenKeyCode(keyCode);
			return true;
		case KeyEvent.KEYCODE_ENTER:
			tenKeyCode(keyCode);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private boolean tenKeyCode(int keyCode) {
		if (keyCode == KeyEvent.KEYCODE_ENTER) {
			if (is_enter_press == 0) 
			{
				mHandler.removeMessages(DIRECT_TUNE_MESSAGE);
				mHandler.sendEmptyMessageDelayed(DIRECT_TUNE_MESSAGE,
						5);
				is_enter_press = 1;
			}
		} else {
		mInputDigitMajor++;

		if (mInputDigitMajor > 3) {
			mTvNumberIcon1.setVisibility(View.INVISIBLE);
			mTvNumberIcon2.setVisibility(View.INVISIBLE);
			mTvNumberIcon3.setVisibility(View.INVISIBLE);
			mInputDigitMajor = 1;
		}

		int inputnum = keyCode - KeyEvent.KEYCODE_0;
		ArrayList<Integer> n = getResoulseID(numberToPicture(inputnum));

		if (mInputDigitMajor == 1) {
			mChannelNumberInput = inputnum;
			mTvNumberIcon1.setImageResource(n.get(0));
			mTvNumberIcon1.setVisibility(View.VISIBLE);
		} else if (mInputDigitMajor == 2) {
			mChannelNumberInput = mChannelNumberInput * 10 + inputnum;
			mTvNumberIcon2.setImageResource(n.get(0));
			mTvNumberIcon2.setVisibility(View.VISIBLE);
		} else if (mInputDigitMajor == 3) {
			mChannelNumberInput = mChannelNumberInput * 10 + inputnum;
			mTvNumberIcon3.setImageResource(n.get(0));
			mTvNumberIcon3.setVisibility(View.VISIBLE);
		} else {
			return true;
		}
		Log.i(TAG, "mChannelNumberInput = " + mChannelNumberInput);
		mHandler.removeMessages(DIRECT_TUNE_MESSAGE);
		mHandler.sendEmptyMessageDelayed(DIRECT_TUNE_MESSAGE, WAIT_EXPIRE_TIME);
		}
		return true;
	}

	private ArrayList<String> numberToPicture(int num) {
		ArrayList<String> strArray = new ArrayList<String>();
		String str = num + "";
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			strArray.add("" + ch);
		}
		return strArray;
	}

	private ArrayList<Integer> getResoulseID(ArrayList<String> str) {
		ArrayList<Integer> n = new ArrayList<Integer>();
		for (String string : str) {
			Integer resId = mNumberResIds[Integer.parseInt(string)];
			n.add(resId);
		}
		return n;
	}

	private void getProgList() {
		K_ProgramInfo programInfo = null;
		int m_nServiceNum = mTvChannelManager
				.K_getProgramCount(K_Constants.PROGRAM_COUNT_ATV_DTV);
		for (int k = 0; k < m_nServiceNum; k++) {
				programInfo = (K_ProgramInfo)mTvChannelManager.K_getProgramInfoByIndex(k);
			if (programInfo != null) {
				if (programInfo.getProgram().isDelete == true) {
					continue;
				} else {
					mProgramNumbers.add(programInfo);
				}
			}
		}
	}

	private boolean isProgramExist() {
		boolean programIsExist = false;

			/*
			 * For internal TV model(China mainland), ATV channel number start
			 * from 0. And for external TV model, ATV channel number start from
			 * 1. Here, we do program exist check by checking input index number
			 * with program count size inside mProgramNumbers array list.
			 */
			int curInputSrc = mTvCommonManager.K_getCurrentTvInputSource();
			if (K_Constants.INPUT_SOURCE_ATV == curInputSrc) {
				for(K_ProgramInfo atvpi : mProgramNumbers)
				{
					if((mChannelNumberInput-1) == atvpi.getProgram().number && atvpi.getProgram().serviceType ==  K_Constants.SERVICE_TYPE_ATV)
					{
						programIsExist = true;
						break;
					}
				}
			} else if (K_Constants.INPUT_SOURCE_DTV == curInputSrc) {
				for (K_ProgramInfo pi : mProgramNumbers) {
					if (mChannelNumberInput == pi.getProgram().number) {
						programIsExist = true;
						break;
					}
				}
			}
		return programIsExist;
	}

	private void updateAtvTuningScanInfo(K_AtvEventScan extra) {
		Log.i(TAG, "[percent]:" + extra.getAtvEventScan().percent);
		Log.i(TAG, "[frequencyKHz]:" + extra.getAtvEventScan().frequencyKHz);
		Log.i(TAG, "[scannedChannelNum]:" + extra.getAtvEventScan().scannedChannelNum);
		Log.i(TAG, "[curScannedChannel]:" + extra.getAtvEventScan().curScannedChannel);

		if (extra.getAtvEventScan().scannedChannelNum > 0) {
			K_ChannelModel.getInstance().K_genMixProgList(false);
			mChannelNum.setText(R.string.str_channelcontrol_done);
			K_ChannelModel.getInstance().K_setChannel(mCurRFScanNumber - 1, true);
		} else {
			mChannelNum.setText(R.string.str_channelcontrol_notfound);
			if (mPreChannelNumber > mTvChannelManager.K_getProgramCtrl(
					K_Constants.ATV_PROG_CTRL_GET_MAX_CHANNEL, 0, 0)) {
				mTvChannelManager.K_changeToFirstService(
						K_Constants.FIRST_SERVICE_INPUT_TYPE_ATV,
						K_Constants.FIRST_SERVICE_MENU_SCAN);
			} else {
				mTvChannelManager.K_selectProgram(mPreChannelNumber,
						K_Constants.SERVICE_TYPE_ATV);
			}
		}
		finishHandler.postDelayed(finishTask, FINISH_DELAY_FOR_IDLE);
	}

	private void updateDtvTuningScanInfo(K_DtvEventScan extra) {
		if (extra.getDvEventScan().scanStatus == K_Constants.E_STATUS_SCAN_END.ordinal()) {
			mIsDtvTuning = false;
			String str;
			mTvChannelManager.K_changeToFirstService(
					K_Constants.FIRST_SERVICE_INPUT_TYPE_DTV,
					K_Constants.FIRST_SERVICE_DEFAULT);
			finishHandler.removeCallbacks(finishTask);
			mChannelScan.setText(R.string.str_channelcontrol_done);

			int delay = FINISH_DELAY_FOR_IDLE;
			if ((extra.getDvEventScan().dtvSrvCount + extra.getDvEventScan().radioSrvCount + extra.getDvEventScan().dataSrvCount) > 0) {
				str = " "
						+ (extra.getDvEventScan().dtvSrvCount + extra.getDvEventScan().radioSrvCount + extra.getDvEventScan().dataSrvCount);
				mChannelNum.setText(String.valueOf(str));
				delay = FINISH_DELAY_FOR_PROG_FOUND;
			} else {
				mChannelNum.setText(R.string.str_channelcontrol_notfound);
			}
			finishHandler.postDelayed(finishTask, delay);
		}
	}

	private class AtvPlayerEventListener extends K_OnAtvPlayerEventListener {

		@Override
		public boolean K_onAtvAutoTuningScanInfo(int arg0, K_AtvEventScan arg1) {
			Message msg = mAtvUiEventHandler.obtainMessage(arg0, arg1);
			mAtvUiEventHandler.sendMessage(msg);
			return true;
		}

		@Override
		public boolean K_onAtvManualTuningScanInfo(int arg0, K_AtvEventScan arg1) {
			// TODO Auto-generated method stub
			return false;
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
/*
		// FIXME: remove if use new method instead of handler
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
		}

		@Override
		public boolean onAtvAutoTuningScanInfo(int what, AtvEventScan extra) {
			// TODO Auto-generated method stub
			return false;
		}
*/
	}

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
		public boolean K_onDtvAutoTuningScanInfo(int arg0, K_DtvEventScan arg1) {
			Message msg = mDtvUiEventHandler.obtainMessage(arg0, arg1);
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
		}
/*
		@Override
		public boolean onDtvChannelNameReady(int what) {
			return false;
		}

		// FIXME: remove if use new method instead of handler
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
		}*/
	}
}
