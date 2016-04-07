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

package com.mstar.tv.tvplayer.ui;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.AlertDialog;
import android.app.IActivityManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.kguan.mtvplay.tvapi.K_ChannelModel;
import com.kguan.mtvplay.tvapi.K_Constants;
import com.kguan.mtvplay.tvapi.K_MKeyEvent;
import com.kguan.mtvplay.tvapi.K_SettingModel;
import com.kguan.mtvplay.tvapi.K_TvCecManager;
import com.kguan.mtvplay.tvapi.K_TvCiManager;
import com.kguan.mtvplay.tvapi.K_TvCommonManager;
import com.kguan.mtvplay.tvapi.K_TvManager;
import com.kguan.mtvplay.tvapi.K_TvMhlManager;
import com.kguan.mtvplay.tvapi.K_TvPictureManager;
import com.kguan.mtvplay.tvapi.K_TvS3DManager;
import com.kguan.mtvplay.tvapi.listener.K_OnAtvPlayerEventListener;
import com.kguan.mtvplay.tvapi.listener.K_OnCecCtrlEventListener;
import com.kguan.mtvplay.tvapi.listener.K_OnCiStatusChangeEventListener;
import com.kguan.mtvplay.tvapi.listener.K_OnDtvPlayerEventListener;
import com.kguan.mtvplay.tvapi.listener.K_OnMhlEventListener;
import com.kguan.mtvplay.tvapi.listener.K_OnTvEventListener;
import com.kguan.mtvplay.tvapi.listener.K_OnTvPlayerEventListener;
import com.kguan.mtvplay.tvapi.listener.K_OnUiEventListener;
import com.kguan.mtvplay.tvapi.vo.K_ProgramInfo;
import com.kguan.mtvplay.tvapi.vo.atv.K_AtvEventScan;
import com.kguan.mtvplay.tvapi.vo.common.K_HbbtvEventInfo;
import com.kguan.mtvplay.tvapi.vo.dtv.K_DtvEventScan;
import com.mstar.android.MIntent;
import com.mstar.android.tv.TvLanguage;
import com.mstar.android.tv.widget.TvView;
import com.mstar.tv.tvplayer.ui.channel.ChannelControlActivity;
import com.mstar.tv.tvplayer.ui.dtv.CimmiActivity;
import com.mstar.tvframework.MstarUIActivity;
import com.mstar.util.Constant;
import com.mstar.util.Constant.ScreenSaverMode;
import com.mstar.util.Constant.SignalProgSyncStatus;
import com.mstar.util.DataBaseUtil;
import com.mstar.util.PropHelp;
import com.mstar.util.Tools;
import com.mstar.util.TvEvent;
import com.mstar.util.Utility;
public class RootActivity extends MstarUIActivity implements
View.OnKeyListener{
	private static final String TAG = "RootActivity";
	private boolean log = true;

	// 15min.
	private static final int NO_SIGNAL_SHUTDOWN_TIME = 15 * 60 * 1000;

	private static final int SCREENSAVER_DEFAULT_STATUS = -1;

	public boolean bCmd_TvApkExit = false;

	private TvView tvView = null;

	private boolean mIsSignalLock = true;

	private static boolean mIsActive = false;

	private static boolean mIsBackKeyPressed = false;

	private volatile int mScreenSaverStatus = SCREENSAVER_DEFAULT_STATUS;

	private static boolean mIsScreenSaverShown = false;

	private static boolean mIsMsrvStarted = false;

	private static String TV_APK_START = "com.mstar.tv.ui.tvstart";

	private static String TV_APK_END = "com.mstar.tv.ui.tvend";

	private final static String PREFERENCES_INPUT_SOURCE = "INPUT_SOURCE";

	private final static String PREFERENCES_PREVIOUS_INPUT_SOURCE = "PREVIOUS_INPUT_SOURCE";

	private K_TvPictureManager mTvPictureManager = null;

	private K_TvS3DManager mTvS3DManager = null;

	private K_ChannelModel mK_ChannelModel = null;

	private K_TvMhlManager mTvMhlManager = null;

	private K_TvCecManager mTvCecManager = null;
	
	private K_TvCommonManager mTvCommon = null;

	private static boolean isFirstPowerOn = true;

	private static int systemAutoTime = 0;

	private boolean mIsExiting = false;

	private final int mCecStatusOn = 1;

	private int mPreviousInputSource = K_Constants.INPUT_SOURCE_NONE;

	protected static AlertDialog mExitDialog;

	// now close 3D function, when open, it
	private boolean _3Dflag = false;

	// shall be deleted
	private static RootActivity rootActivity = null;

	private static boolean isReboot = false;

	private boolean mIskeyLocked = false;

	private PowerManager mPowerManager = null;

	private AlertDialog mCiPlusOPRefreshDialog = null;

	private BroadcastReceiver mReceiver = null;

	private K_OnDtvPlayerEventListener mDtvPlayerEventListener = null;

	private K_OnAtvPlayerEventListener mAtvPlayerEventListener = null;

	private K_OnTvPlayerEventListener mTvPlayerEventListener = null;
	private K_OnMhlEventListener mMhlEventListener = null;

	private K_OnTvEventListener mTvEventListener = null;

	private K_OnUiEventListener mUiEventListener = null;

	private K_OnCiStatusChangeEventListener mCiStatusChangeEventListener = null;

	private K_OnCecCtrlEventListener mCecCtrlEventListener = null;

	private Toast mCcKeyToast;

	private TextView mNoSignalView = null;

	//private TextView mFreezeView = null;

	private boolean mIsPowerOn = false;

	private boolean mIsToPromptPassword = false;

	private DataBaseUtil mDataBaseUtil;
	private final int CEC_INFO_DISPLAY_TIMEOUT = 1000;
//==================ktc add ==============
	private boolean isOnPauseFlag = false;
	private int SCREEN_WIDTH;
	private int SCREEN_HEIGHT;
	private int TEXT_WIDTH;
	private int TEXT_HEIGHT;
	private int posX;
	private int posY;
	private int X_STEP = 2;
	private int Y_STEP = 2;
	private static final int REFRESH_POS = 1001;
	private Timer mRefreshTimer = null;
	private MyTimerTask mRefreshTask = null;
	//ktc nathan.liao 20140905 for hotelmenu start
	private View rllyPassword;
	private LinearLayout llyPasswdContainer;
	private String[] mEnterPassword;
	private Button[] mPasswdViews;
	private int[] mPasswdIds;
	boolean isShowPsd = false;
	//mengwt 20141014 
	boolean disableNoSignal=false;
	//mengwt 20141014
	boolean isHotelMenuLockFlag = false;//nathan.liao 2014.11.11
	//ktc nathan.liao 20140905 for hotelmenu end
		boolean isFromUSB = false;
		
		private boolean mIsCecDialogCanceled = false;
		private ProgressDialog mCecInfoDialog = null;
//==================ktc end ==============

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
		public boolean K_onDtvAutoTuningScanInfo(int arg0, K_DtvEventScan arg1) {
			// TODO Auto-generated method stub
			return false;
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
			return true;
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
			if(log)
				Log.d(TAG, "SIGNAL Lock***");
				Message m = Message.obtain();
				m.arg1 = TvEvent.SIGNAL_LOCK;
				signalLockHandler.sendMessage(m);
				return true;
		}

		@Override
		public boolean K_onSignalUnLock(int arg0) {
			if(log)
				Log.d(TAG, "SIGNAL UnLock***");
				Message m = Message.obtain();
				m.arg1 = TvEvent.SIGNAL_UNLOCK;
				signalLockHandler.sendMessage(m);
				// Hisa 2016.03.04 add Freeze function start
				Log.d("Jason","无信号状态");	
				Intent intent = new Intent();//取消静像菜单
					intent.setAction(MIntent.ACTION_FREEZE_CANCEL_BUTTON);
					K_TvPictureManager.getInstance().K_unFreezeImage();
					sendBroadcast(intent);  
				// Hisa 2016.03.04 add Freeze function end
				return true;
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
			if(log)
				Log.i(TAG, "get CI+ OP event EV_CI_OP_REFRESH_QUERY");
				K_TvCiManager.getInstance().ciClearOPSearchSuspended();
				displayOpRefreshconfirmation();
				return true;
		}

		@Override
		public boolean K_onUiOPServiceList(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}
/*

		public boolean onDtvChannelNameReady(int what) {
			return false;
		}

		public boolean onDtvProgramInfoReady(int what) {
			return false;
		}

		public boolean onCiLoadCredentialFail(int what) {
			return false;
		}

		public boolean onEpgTimerSimulcast(int what, int arg1) {
			return false;
		}

		public boolean onHbbtvStatusMode(int what, boolean arg1) {
			return false;
		}

		public boolean onMheg5StatusMode(int what, int arg1) {
			return false;
		}

		public boolean onMheg5ReturnKey(int what, int arg1) {
			return false;
		}

		public boolean onOadHandler(int what, int arg1, int arg2) {
			return true;
		}

		public boolean onOadDownload(int what, int arg1) {
			return false;
		}

		public boolean onDtvAutoUpdateScan(int what) {
			return false;
		}

		public boolean onTsChange(int what) {
			Log.d(TAG,"onTsChanage");
			return false;
		}

		public boolean onPopupScanDialogLossSignal(int what) {
			Log.d(TAG,"onPopupScanDialogLossSignal");
			return false;
		}

		public boolean onPopupScanDialogNewMultiplex(int what) {
			return false;
		}

		public boolean onPopupScanDialogFrequencyChange(int what) {
			return false;
		}

		public boolean onRctPresence(int what) {
			return false;
		}

		public boolean onChangeTtxStatus(int what, boolean arg1) {
			return false;
		}

		public boolean onDtvPriComponentMissing(int what) {
			return false;
		}

		public boolean onAudioModeChange(int what, boolean arg1) {
			return false;
		}

		public boolean onMheg5EventHandler(int what, int arg1) {
			return false;
		}

		public boolean onOadTimeout(int what, int arg1) {
			return false;
		}

		public boolean onGingaStatusMode(int what, boolean arg1) {
			return false;
		}

		public boolean onSignalLock(int what) {
			if(log)
			Log.d(TAG, "SIGNAL Lock***");
			Message m = Message.obtain();
			m.arg1 = TvEvent.SIGNAL_LOCK;
			signalLockHandler.sendMessage(m);
			return true;
		}

		public boolean onSignalUnLock(int what) {
			if(log)
			Log.d(TAG, "SIGNAL UnLock***");
			Message m = Message.obtain();
			m.arg1 = TvEvent.SIGNAL_UNLOCK;
			signalLockHandler.sendMessage(m);
			// Hisa 2016.03.04 add Freeze function start
			Log.d("Jason","无信号状态");	
			Intent intent = new Intent();//取消静像菜单
				intent.setAction(MIntent.ACTION_FREEZE_CANCEL_BUTTON);
				K_TvPictureManager.getInstance().K_unFreezeImage();
				sendBroadcast(intent);  
			// Hisa 2016.03.04 add Freeze function end
			return true;
		}

		public boolean onUiOPRefreshQuery(int what) {
			if(log)
			Log.i(TAG, "get CI+ OP event EV_CI_OP_REFRESH_QUERY");
			K_TvCiManager.getInstance().ciClearOPSearchSuspended();
			displayOpRefreshconfirmation();
			return true;
		}

		public boolean onUiOPServiceList(int what) {
			return false;
		}

		public boolean onUiOPExitServiceList(int what) {
			return false;
		}

		public boolean onDtvAutoTuningScanInfo(int what, DtvEventScan extra) {
			// TODO Auto-generated method stub
			return false;
		}
*/
	}

	private class AtvPlayerEventListener extends K_OnAtvPlayerEventListener {

/*		@Override
		public boolean onAtvAutoTuningScanInfo(int what, AtvEventScan extra) {
			return false;
		}
*/
/*		@Override
		public boolean onAtvManualTuningScanInfo(int what, AtvEventScan extra) {
			return false;
		}*/

/*		@Override
		public boolean onSignalLock(int what) {
			Log.d(TAG, "SIGNAL Lock***");
			Message m = Message.obtain();
			m.arg1 = TvEvent.SIGNAL_LOCK;
			signalLockHandler.sendMessage(m);
			return true;
		}
*/
/*		@Override
		public boolean onSignalUnLock(int what) {
			if(log)
			Log.d(TAG, "SIGNAL UnLock***");
			Message m = Message.obtain();
			m.arg1 = TvEvent.SIGNAL_UNLOCK;
			signalLockHandler.sendMessage(m);
			return true;
		}*/

		@Override
		public boolean K_onAtvProgramInfoReady(int what) {
			return false;
		}

		@Override
		public boolean K_onAtvAutoTuningScanInfo(int arg0, K_AtvEventScan arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onAtvManualTuningScanInfo(int arg0, K_AtvEventScan arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onSignalLock(int arg0) {
			Log.d(TAG, "K_SIGNAL Lock***");
			Message m = Message.obtain();
			m.arg1 = TvEvent.SIGNAL_LOCK;
			signalLockHandler.sendMessage(m);
			return false;
		}

		@Override
		public boolean K_onSignalUnLock(int arg0) {
			if(log)
			Log.d(TAG, "K_SIGNAL UnLock***");
			Message m = Message.obtain();
			m.arg1 = TvEvent.SIGNAL_UNLOCK;
			signalLockHandler.sendMessage(m);
			return true;
		}
	}
	private class MhlEventListener extends K_OnMhlEventListener{
		Intent intent;
		@Override
		public boolean K_onAutoSwitch(int arg0, final int arg1, int arg2) {
			if(log)
				Log.d(TAG, "onAutoSwitch");
				intent = new Intent(TvIntent.ACTION_START_ROOTACTIVITY);
				intent.putExtra("task_tag", "input_source_changed");
				new Thread(new Runnable() {
					@Override
					public void run() {
						mTvS3DManager
								.K_setDisplayFormatForUI(K_Constants.THREE_DIMENSIONS_DISPLAY_FORMAT_NONE);
						K_TvCommonManager.getInstance().K_setInputSource(arg1);
						startActivity(intent);
					}
				}).start();
				return false;
		}

		@Override
		public boolean K_onKeyInfo(int arg0, int arg1, int arg2) {
			if(log)
				Log.d(TAG, "onKeyInfo");
				return false;
		}
		
	}
	private class TvPlayerEventListener extends K_OnTvPlayerEventListener {

		@Override
		public boolean K_on4k2kHDMIDisableDualView(int arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_on4k2kHDMIDisablePip(int arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_on4k2kHDMIDisablePop(int arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_on4k2kHDMIDisableTravelingMode(int arg0, int arg1,
				int arg2) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onDtvChannelInfoUpdate(int arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onDtvPsipTsUpdate(int arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onEmerencyAlert(int arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onEpgUpdateList(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onHbbtvUiEvent(int arg0, K_HbbtvEventInfo arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onPopupDialog(int arg0, int arg1, int arg2) {
			Log.d(TAG, "onPopupDialog(" + arg1 + "," + arg2 + ")");
			if (K_Constants.POPUP_DIALOG_SHOW == arg1) {
				mIsToPromptPassword = true;
				if (true == mIsActive) {
				}
			} else if (K_Constants.POPUP_DIALOG_HIDE == arg1) {
				mIsToPromptPassword = false;
			}
			return true;
		}

		@Override
		public boolean K_onPvrNotifyAlwaysTimeShiftProgramNotReady(int arg0) {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean K_onPvrNotifyAlwaysTimeShiftProgramReady(int arg0) {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean K_onPvrNotifyCiPlusProtection(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onPvrNotifyCiPlusRetentionLimitUpdate(int arg0,
				int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onPvrNotifyOverRun(int arg0) {
			RootActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					Toast toast = Toast.makeText(RootActivity.this,
							R.string.str_disk_too_slow, Toast.LENGTH_LONG);
					toast.show();
				}
			});
			return true;
		}

		@Override
		public boolean K_onPvrNotifyParentalControl(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onPvrNotifyPlaybackBegin(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onPvrNotifyPlaybackSpeedChange(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onPvrNotifyPlaybackStop(int arg0) {
			RootActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					Toast toast = Toast.makeText(RootActivity.this,
							"pvr playback is stopped", Toast.LENGTH_SHORT);
					toast.show();
				}
			});
			return true;
		}

		@Override
		public boolean K_onPvrNotifyPlaybackTime(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onPvrNotifyRecordSize(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onPvrNotifyRecordStop(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onPvrNotifyRecordTime(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onPvrNotifyTimeShiftOverwritesAfter(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onPvrNotifyTimeShiftOverwritesBefore(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onPvrNotifyUsbRemoved(int arg0, int arg1) {
			RootActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					Toast toast = Toast.makeText(RootActivity.this,
							"The disk is full, please check the disk capacity",
							Toast.LENGTH_LONG);
					toast.show();
				}
			});
			return true;
		}

		@Override
		public boolean K_onScreenSaverMode(int arg0, int arg1) {
			// TODO Auto-generated method stub
			mScreenSaverStatus = arg1;
			if(log)
			{
			Log.d(TAG, "EV_SCREEN_SAVER_MODE***");
			Log.d(TAG, "onScreenSaverMode receive status:" + mScreenSaverStatus);
			}
			// getCurrentInputSource takes much time so leave it to subthread.
			new Thread() {
				public void run() {
					int curInputSource = K_TvCommonManager.getInstance()
							.K_getCurrentTvInputSource();
					Message m = Message.obtain();
					m.arg1 = mScreenSaverStatus;
					m.arg2 = curInputSource;
					if (!isHotelMenuLockFlag) {	
						screenSaverHandler.sendMessage(m);
					}
				};
			}.start();

			return true;
		}

		@Override
		public boolean K_onSignalLock(int arg0) {
			//if(log)
			Log.d(TAG, "K_SIGNAL Lock***");
			Message m = Message.obtain();
			m.arg1 = TvEvent.SIGNAL_LOCK;
			signalLockHandler.sendMessage(m);
			return true;
		}

		@Override
		public boolean K_onSignalUnLock(int arg0) {
			//if(log)
			Log.d(TAG, "K_SIGNAL UnLock***");
			Message m = Message.obtain();
			m.arg1 = TvEvent.SIGNAL_UNLOCK;
			signalLockHandler.sendMessage(m);
			return true;
		}

		@Override
		public boolean K_onTvProgramInfoReady(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}/*

		@Override
		public boolean onScreenSaverMode(int what, int arg1) {
			mScreenSaverStatus = arg1;
			if(log)
			{
			Log.d(TAG, "EV_SCREEN_SAVER_MODE***");
			Log.d(TAG, "onScreenSaverMode receive status:" + mScreenSaverStatus);
			}
			// getCurrentInputSource takes much time so leave it to subthread.
			new Thread() {
				public void run() {
					int curInputSource = K_TvCommonManager.getInstance()
							.K_getCurrentTvInputSource();
					Message m = Message.obtain();
					m.arg1 = mScreenSaverStatus;
					m.arg2 = curInputSource;
					if (!isHotelMenuLockFlag) {	
						screenSaverHandler.sendMessage(m);
					}
				};
			}.start();

			return true;
		}

		@Override
		public boolean onHbbtvUiEvent(int what, HbbtvEventInfo eventInfo) {
			return false;
		}

		@Override
		public boolean onPopupDialog(int what, int arg1, int arg2) {
			Log.d(TAG, "onPopupDialog(" + arg1 + "," + arg2 + ")");
			if (K_Constants.POPUP_DIALOG_SHOW == arg1) {
				mIsToPromptPassword = true;
				if (true == mIsActive) {
				}
			} else if (K_Constants.POPUP_DIALOG_HIDE == arg1) {
				mIsToPromptPassword = false;
			}
			return true;
		}

		@Override
		public boolean onPvrNotifyPlaybackTime(int what, int arg1) {
			return false;
		}

		@Override
		public boolean onPvrNotifyPlaybackSpeedChange(int what) {
			return false;
		}

		@Override
		public boolean onPvrNotifyRecordTime(int what, int arg1) {
			return false;
		}

		@Override
		public boolean onPvrNotifyRecordSize(int what, int arg1) {
			return false;
		}

		@Override
		public boolean onPvrNotifyRecordStop(int what) {
			return false;
		}

		@Override
		public boolean onPvrNotifyPlaybackStop(int what) {
			RootActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					Toast toast = Toast.makeText(RootActivity.this,
							"pvr playback is stopped", Toast.LENGTH_SHORT);
					toast.show();
				}
			});
			return true;
		}

		@Override
		public boolean onPvrNotifyPlaybackBegin(int what) {
			return false;
		}

		@Override
		public boolean onPvrNotifyTimeShiftOverwritesBefore(int what, int arg1) {
			return false;
		}

		@Override
		public boolean onPvrNotifyTimeShiftOverwritesAfter(int what, int arg1) {
			return false;
		}

		@Override
		public boolean onPvrNotifyOverRun(int what) {
			RootActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					Toast toast = Toast.makeText(RootActivity.this,
							R.string.str_disk_too_slow, Toast.LENGTH_LONG);
					toast.show();
				}
			});
			return true;
		}

		@Override
		public boolean onPvrNotifyUsbRemoved(int what, int arg1) {
			RootActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					Toast toast = Toast.makeText(RootActivity.this,
							"The disk is full, please check the disk capacity",
							Toast.LENGTH_LONG);
					toast.show();
				}
			});
			return true;
		}

		@Override
		public boolean onPvrNotifyCiPlusProtection(int what) {
			return false;
		}

		@Override
		public boolean onPvrNotifyParentalControl(int what, int arg1) {
			return false;
		}

		@Override
		public boolean onPvrNotifyAlwaysTimeShiftProgramReady(int what) {
			Log.d(TAG, "onPvrNotifyAlwaysTimeShiftProgramReady");
			return true;
		}

		@Override
		public boolean onPvrNotifyAlwaysTimeShiftProgramNotReady(int what) {
			Log.d(TAG, "onPvrNotifyAlwaysTimeShiftProgramNotReady");
			return true;
		}

		@Override
		public boolean onPvrNotifyCiPlusRetentionLimitUpdate(int what, int arg1) {
			return false;
		}

		@Override
		public boolean onTvProgramInfoReady(int what) {
			return false;
		}

		@Override
		public boolean onSignalLock(int what) {
			//if(log)
			Log.d(TAG, "SIGNAL Lock***");
			Message m = Message.obtain();
			m.arg1 = TvEvent.SIGNAL_LOCK;
			signalLockHandler.sendMessage(m);
			return true;
		}

		@Override
		public boolean onSignalUnLock(int what) {
			//if(log)
			Log.d(TAG, "SIGNAL UnLock***");
			Message m = Message.obtain();
			m.arg1 = TvEvent.SIGNAL_UNLOCK;
			signalLockHandler.sendMessage(m);
			return true;
		}

		@Override
		public boolean onEpgUpdateList(int what, int arg1) {
			return false;
		}

		@Override
		public boolean on4k2kHDMIDisablePip(int what, int arg1, int arg2) {
			return false;
		}

		@Override
		public boolean on4k2kHDMIDisablePop(int what, int arg1, int arg2) {
			return false;
		}

		@Override
		public boolean on4k2kHDMIDisableDualView(int what, int arg1, int arg2) {
			return false;
		}

		@Override
		public boolean on4k2kHDMIDisableTravelingMode(int what, int arg1,
				int arg2) {
			return false;
		}

		@Override
		public boolean onDtvPsipTsUpdate(int what, int arg1, int arg2) {
			return false;
		}

		@Override
		public boolean onEmerencyAlert(int what, int arg1, int arg2) {
			return false;
		}

		@Override
		public boolean onDtvChannelInfoUpdate(int what, int info, int arg2) {
			return false;
		}

	*/}

	private class TvEventListener extends K_OnTvEventListener {

		@Override
		public boolean K_on4k2kHDMIDisableDualView(int arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_on4k2kHDMIDisablePip(int arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_on4k2kHDMIDisablePop(int arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_on4k2kHDMIDisableTravelingMode(int arg0, int arg1,
				int arg2) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onAtscPopupDialog(int arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onDeadthEvent(int arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onDtvReadyPopupDialog(int arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onScartMuteOsdMode(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onScreenSaverMode(int arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onSignalLock(int arg0) {
			if(log)
				Log.d(TAG, "SIGNAL Lock***");
				Message m = Message.obtain();
				m.arg1 = TvEvent.SIGNAL_LOCK;
				signalLockHandler.sendMessage(m);
				return true;
		}

		@Override
		public boolean K_onSignalUnlock(int arg0) {
			if(log)
				Log.d(TAG, "SIGNAL UnLock***");
				Message m = Message.obtain();
				m.arg1 = TvEvent.SIGNAL_UNLOCK;
				signalLockHandler.sendMessage(m);
				return true;
		}

		@Override
		public boolean K_onUnityEvent(int arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			return false;
		}

/*		@Override
		public boolean onDtvReadyPopupDialog(int what, int arg1, int arg2) {
			return false;
		}

		@Override
		public boolean onScartMuteOsdMode(int what) {
			return false;
		}

		@Override
		public boolean onSignalUnlock(int what) {
			if(log)
			Log.d(TAG, "SIGNAL UnLock***");
			Message m = Message.obtain();
			m.arg1 = TvEvent.SIGNAL_UNLOCK;
			signalLockHandler.sendMessage(m);
			return true;
		}

		@Override
		public boolean onSignalLock(int what) {
			if(log)
			Log.d(TAG, "SIGNAL Lock***");
			Message m = Message.obtain();
			m.arg1 = TvEvent.SIGNAL_LOCK;
			signalLockHandler.sendMessage(m);
			return true;
		}

		@Override
		public boolean onUnityEvent(int what, int arg1, int arg2) {
			return false;
		}

		@Override
		public boolean onScreenSaverMode(int what, int arg1, int arg2) {
			return false;
		}

		@Override
		public boolean onAtscPopupDialog(int what, int arg1, int arg2) {
			return false;
		}

		@Override
		public boolean onDeadthEvent(int what, int arg1, int arg2) {
			return false;
		}

		@Override
		public boolean on4k2kHDMIDisablePip(int what, int arg1, int arg2) {
			return false;
		}

		@Override
		public boolean on4k2kHDMIDisablePop(int what, int arg1, int arg2) {
			return false;
		}

		@Override
		public boolean on4k2kHDMIDisableDualView(int what, int arg1, int arg2) {
			return false;
		}

		@Override
		public boolean on4k2kHDMIDisableTravelingMode(int what, int arg1,
				int arg2) {
			return false;
		}*/
	}

	public static RootActivity getInstance() {
		return rootActivity;
	}

	/**
	 * 1. update No signal activity 2. start TV apk
	 */
	protected Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == Constant.ROOTACTIVITY_RESUME_MESSAGE) {
				/*
				 * Modified by gerard.jiang for "0386249" in 2013/04/28. Add
				 * reboot flag When system is suspending (isScreenOn == false),
				 * do not start No Signal Activity. by Desmond Pu 2013/12/18
				 */
				if (_3Dflag == false && !isReboot
						&& (mPowerManager.isScreenOn()))
				/***** Ended by gerard.jiang 2013/04/28 *****/
				{
					updateTvSourceSignal();
				}
				// Notfiy event queue to start sending pending event
				try {
					mTvCommon
							.K_setTvosCommonCommand(Constant.TV_EVENT_LISTENER_READY);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (msg.what == 900) {
				executePreviousTask(getIntent());

				SharedPreferences settings = getSharedPreferences(
						Constant.PREFERENCES_TV_SETTING, Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean("_3Dflag", _3Dflag);
				editor.commit();
				checkSystemAutoTime();
			} else if (msg.what == Constant.ROOTACTIVITY_TVPROMINFOREADY_MESSAGE) {
                int curInputSource = K_TvCommonManager.getInstance().K_getCurrentTvInputSource(); 				
                if ((K_Constants.INPUT_SOURCE_CVBS <= curInputSource)
                        && (K_Constants.INPUT_SOURCE_CVBS_MAX > curInputSource)
                        || (K_Constants.INPUT_SOURCE_YPBPR <= curInputSource)
                        && (K_Constants.INPUT_SOURCE_YPBPR_MAX > curInputSource)
                        || (K_Constants.INPUT_SOURCE_HDMI <= curInputSource)
                        && (K_Constants.INPUT_SOURCE_HDMI_MAX > curInputSource)) {
                    Utility.startSourceInfo(RootActivity.this);
                }
            } else if (msg.what == Constant.ROOTACTIVITY_CANCEL_DIALOG) {
                if (null != mCecInfoDialog) {
                    if (false == mIsCecDialogCanceled) {
                        mHandler.sendEmptyMessageDelayed(Constant.ROOTACTIVITY_CANCEL_DIALOG, CEC_INFO_DISPLAY_TIMEOUT);
                    } else {
                        mCecInfoDialog.dismiss();
                        mCecInfoDialog = null;
                        mIsCecDialogCanceled = false;
                    }
                }
            }
			else if (msg.what == REFRESH_POS) {
				mNoSignalView.layout(posX, posY, posX + TEXT_WIDTH, posY
						+ TEXT_HEIGHT);
			}
		};
	};

	public boolean isBackKeyPressed() {
		return mIsBackKeyPressed;
	}

	// ---------------------------------------------------
	@Override
	public void onCreate(Bundle savedInstanceState) {
		if(log)
		Log.i(TAG, "onCreate()");
		setContentView(R.layout.root);
		//ktc nathan.liao 20140905 for hotelmenu start
		mDataBaseUtil = DataBaseUtil.getInstance(this);
		rllyPassword = findViewById(R.id.rllyPassword);
		llyPasswdContainer = (LinearLayout)findViewById(R.id.llyPasswdContainer);
		int passwdLength = PropHelp.newInstance().getHotelChlockPasswd().length();

		mEnterPassword = new String[passwdLength];
		mPasswdIds = new int[passwdLength];
		mPasswdViews = new Button[passwdLength];
		isHotelMenuLockFlag = isCurrentChannelLock();
		LayoutInflater inflater = LayoutInflater.from(this);
		int passwdId = 10020;
		for (int i = 0; i < passwdLength; i++) {
			View view = inflater.inflate(R.layout.btn_passwd_item, null);
			Button button = (Button) view.findViewById(R.id.edtPasswd);
			int id = passwdId + i;
			button.setId(id);
			mPasswdIds[i] = id;
			mPasswdViews[i] = button;
			button.setOnKeyListener((OnKeyListener) this);
			int width = getResources().getDimensionPixelSize(
					R.dimen.hotel_passwd_item_width);
			LinearLayout.LayoutParams params = new LayoutParams(width, width);
			llyPasswdContainer.addView(view, params);
		}
		isShowPsd = false;
		//ktc nathan.liao 20140905 for hotelmenu end
		rootActivity = this;
		_3Dflag = false;
		super.onCreate(savedInstanceState);
		SCREEN_HEIGHT = getWindowManager().getDefaultDisplay().getHeight();
		SCREEN_WIDTH = getWindowManager().getDefaultDisplay().getWidth();
		mTvCommon = K_TvCommonManager.getInstance();
		mTvPictureManager = K_TvPictureManager.getInstance();
		mTvS3DManager = K_TvS3DManager.getInstance();
		mK_ChannelModel = K_ChannelModel.getInstance();
		// tvAudioManager =TvAudioManager.getInstance();
		mTvCecManager = K_TvCecManager.getInstance();
		mNoSignalView = (TextView) findViewById(R.id.NoSiganlText);
		mNoSignalView.setText(R.string.str_no_signal);
		createSurfaceView();
		mIsMsrvStarted = true;
		mHandler.sendEmptyMessage(900);
		mTvMhlManager = K_TvMhlManager.getInstance();
		mPowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		mIsPowerOn = getIntent().getBooleanExtra("isPowerOn", false);
		mRefreshTimer = new Timer();

		// for support input source change intent send from source hot key
		if (getIntent() != null && getIntent().getAction() != null) {
			if (getIntent().getAction().equals(TvIntent.ACTION_SOURCE_CHANGE)) {
				/*
				 * clear screen saver status after changing input source and
				 * hide NoSignalView before change input source.
				 */
				mScreenSaverStatus = SCREENSAVER_DEFAULT_STATUS;
				showNoSignalView(false);
				int inputsource = getIntent().getIntExtra("inputSrc",
						K_Constants.INPUT_SOURCE_ATV);
				if(log)
				Log.i(TAG, "onCreate:inputsource = " + inputsource);
				startSourceChange(inputsource);
			}
		}

		mReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals(TvIntent.ACTION_EXIT_TV_APK)) {
					tvApkExitHandler();
				}
				//mengwt 20141014
				else if(intent.getAction().equals("com.mstar.tvsetting.hotkey.DisableNoSignal")){
					disableNoSignal=true;
				}
				//mengwt 20141014
			}
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction(TvIntent.ACTION_EXIT_TV_APK);
		//mengwt 20141014
		filter.addAction("com.mstar.tvsetting.hotkey.DisableNoSignal");
		//mengwt 20141014
		registerReceiver(mReceiver, filter);

		LayoutInflater factory = LayoutInflater.from(this);
		final View layout = factory.inflate(
				R.layout.ciplus_oprefresh_confirmation_dialog, null);

		mCiPlusOPRefreshDialog = new AlertDialog.Builder(this)
				.setTitle(getString(R.string.str_ciplus_op_confirmation_title))
				.setView(layout)
				.setIconAttribute(android.R.attr.alertDialogIcon)
				.setPositiveButton(getString(android.R.string.yes),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								K_TvCiManager.getInstance().K_sendCiOpSearchStart(
										false);
								updateScreenSaver();
							}
						})
				.setNegativeButton(getString(android.R.string.no),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								updateScreenSaver();
							}
						}).setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						updateScreenSaver();
					}
				}).create();

		mExitDialog = new AlertDialog.Builder(this)
				.setTitle(R.string.str_root_alert_dialog_title)
				.setMessage(R.string.str_root_alert_dialog_message)
				.setPositiveButton(R.string.str_root_alert_dialog_confirm,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								LittleDownTimer.destory();
								mIsBackKeyPressed = false;
								dialog.dismiss();
								mIsExiting = true;
								//mwt 20140825
								K_TvManager.getInstance().K_setTvosCommonCommand("SetAutoSleepOffStatus");
								////
								//mengwt 20141015
								Settings.System.putInt(getContentResolver(), "source_hot_key_disable", 1);
								//mengwt 20141015
								finish();
							}
						})
				.setNegativeButton(R.string.str_root_alert_dialog_cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								mIsActive = true;
								dialog.dismiss();
								mIsBackKeyPressed = false;
								bCmd_TvApkExit = false;
								updateScreenSaver();
								mIskeyLocked = false;
							}
						}).setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						mIskeyLocked = false;
						mIsActive = true;
						dialog.dismiss();
						mIsBackKeyPressed = false;
						bCmd_TvApkExit = false;
						updateScreenSaver();

						// ***add by allen.sun 2013-5-27
						// Adaptation different resolutions in STB
						if (Tools.isBox()) {
							new Thread() {
								@Override
								public void run() {
									try {
										Thread.sleep(500);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									Intent pipIntent = new Intent(
											"com.mstar.pipservice");
									pipIntent.putExtra("cmd", "visible");
									RootActivity.this.startService(pipIntent);
								}
							}.start();
						}
						// ***and end
					}

				}).create();
		K_TvManager.getInstance().K_registerOnMhlEventListener(mMhlEventListener);
		/*
		K_TvManager.getInstance().K_getMhlManager()
				.setOnMhlEventListener(new com.kguan.mtvplay.tvapi.K_Listeners.K_OnMhlEventListener() {
					Intent intent;

					@Override
					public boolean onKeyInfo(int arg0, int arg1, int arg2) {
						if(log)
						Log.d(TAG, "onKeyInfo");
						return false;
					}

					@Override
					public boolean onAutoSwitch(int arg0, final int arg1, int arg2)
					{
						if(log)
						Log.d(TAG, "onAutoSwitch");
						intent = new Intent(TvIntent.ACTION_START_ROOTACTIVITY);
						intent.putExtra("task_tag", "input_source_changed");
						new Thread(new Runnable() {
							@Override
							public void run() {
								mTvS3DManager
										.K_setDisplayFormatForUI(K_Constants.THREE_DIMENSIONS_DISPLAY_FORMAT_NONE);
								K_TvCommonManager.getInstance().K_setInputSource(
										arg1);
								startActivity(intent);
							}
						}).start();
						return false;
					}
				});
				*/
		mDtvPlayerEventListener = new DtvPlayerEventListener();
		K_ChannelModel.getInstance().K_registerOnDtvPlayerEventListener(
				mDtvPlayerEventListener);
		mTvPlayerEventListener = new TvPlayerEventListener();
		mMhlEventListener = new MhlEventListener();
		K_ChannelModel.getInstance().K_registerOnTvPlayerEventListener(
				mTvPlayerEventListener);
		mAtvPlayerEventListener = new AtvPlayerEventListener();
		K_ChannelModel.getInstance().K_registerOnAtvPlayerEventListener(
				mAtvPlayerEventListener);
		mTvEventListener = new TvEventListener();
		K_TvCommonManager.getInstance().registerOnTvEventListener(
				mTvEventListener);
		mUiEventListener = new UiEventListener();
		K_TvCiManager.getInstance().K_registerOnUiEventListener(mUiEventListener);
		mCiStatusChangeEventListener = new CiStatusChangeEventListener();
		K_TvCiManager.getInstance().K_registerOnCiStatusChangeEventListener(
				mCiStatusChangeEventListener);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if(log)
		Log.i(TAG, "onConfigurationChanged(), newConfig:" + newConfig);
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onStart() {
		Log.i(TAG, "onStart()");
		super.onStart();
		mCecCtrlEventListener = new CecCtrlEventListener();
		K_TvCecManager.getInstance().K_registerOnCecCtrlEventListener(mCecCtrlEventListener);
		disableNoSignal=false;

	}

	@Override
	protected void onResume() {
		if(log)
		Log.i(TAG, "onResume()");
		isHotelMenuLockFlag = isCurrentChannelLock();
		//mengwt 20141015
		Settings.System.putInt(getContentResolver(), "source_hot_key_disable", 0);
		//mengwt 20141015
		int curInputSource = mTvCommon.K_getCurrentTvInputSource();
		if(K_Constants.INPUT_SOURCE_STORAGE!=curInputSource)
		{
			mIsSignalLock = mTvCommon.K_isSignalStable(curInputSource);
			if(isOnPauseFlag = true)
			{
				isOnPauseFlag = false;
				if(!mIsSignalLock)
				{
					showNoSignalView(true);
				}
			}
		}
		//ktc nathan.liao 20150314 for show nosignl when from usb start
		else 
		{
			if(isOnPauseFlag = true)
			{
				isFromUSB = true;
			}
		}
		//ktc nathan.liao 20150314 for show nosignl when from usb end
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (mTvPictureManager.K_is4K2KMode(true)) {
					sendBroadcast(new Intent("enter.4k2k"));
				}
			}
		}, 1000);
		super.onResume();

		Log.d(TAG, "onResume() mScreenSaverStatus = " + mScreenSaverStatus);
		sendBroadcast(new Intent(TV_APK_START));

		// get previous inputsource from preferences
		SharedPreferences settings = getSharedPreferences(
				PREFERENCES_INPUT_SOURCE, Context.MODE_PRIVATE);
		mPreviousInputSource = settings.getInt(
				PREFERENCES_PREVIOUS_INPUT_SOURCE,
				K_Constants.INPUT_SOURCE_ATV);
		if(log)
		Log.d(TAG, "get previous input source from preference:"
				+ mPreviousInputSource);

		mIskeyLocked = false;
		tvView.setBackgroundColor(Color.TRANSPARENT);
		mIsBackKeyPressed = false;
		mHandler.sendEmptyMessage(Constant.ROOTACTIVITY_RESUME_MESSAGE);
		if (K_Constants.INPUT_SOURCE_ATV == mTvCommon
				.K_getCurrentTvInputSource()) {
			SharedPreferences share = getSharedPreferences("atv_ttx",
					Context.MODE_PRIVATE);
			boolean atvttx = share.getBoolean("ATV_TTXOPEN", false);
			if (atvttx) {
				K_ChannelModel.getInstance().K_openTeletext(
						K_Constants.TTX_MODE_SUBTITLE_NAVIGATION);
			}
		}
		if (TVRootApp.isSourceDirtyPre) {
			TVRootApp.isSourceDirtyPre = false;
			TVRootApp.isSourceDirty = true;
		}
	}

	private void checkSystemAutoTime() {
		try {
			systemAutoTime = Settings.System.getInt(getContentResolver(),
					Settings.Global.AUTO_TIME);
		} catch (SettingNotFoundException e) {
			systemAutoTime = 0;
		}

		if (systemAutoTime > 0) {
			int curInputSource = K_TvCommonManager.getInstance()
					.K_getCurrentTvInputSource();
			if (curInputSource == K_Constants.INPUT_SOURCE_DTV) {
				Settings.System.putInt(getContentResolver(),
						Settings.Global.AUTO_TIME, 0);
			}
		}
	}

	@Override
	protected void onStop() {
		if(log)
		Log.d(TAG, "onStop()");
		sendBroadcast(new Intent(TV_APK_END));
		if (mCiPlusOPRefreshDialog != null
				&& mCiPlusOPRefreshDialog.isShowing()) {
			mCiPlusOPRefreshDialog.dismiss();
		}

		if (mExitDialog != null) {
			mExitDialog.dismiss();
		}
		mIsPowerOn = false;
		K_TvCecManager.getInstance().K_unregisterOnCecCtrlEventListener(
				mCecCtrlEventListener);
		mCecCtrlEventListener = null;

		// switch input source to storage for releasing TV relative resrouce
		if (K_TvCommonManager.getInstance().K_getCurrentTvInputSource() != K_Constants.INPUT_SOURCE_STORAGE) {
			
			if(log)
			Log.i(TAG, "onStop(): Switch input source to storage...");
			K_TvCommonManager.getInstance().K_setInputSource(
					K_Constants.INPUT_SOURCE_STORAGE);
		}

		super.onStop();
	}

	private void updateTvSourceSignal() {
		int curInputSource = K_Constants.INPUT_SOURCE_NONE;
		curInputSource = K_TvCommonManager.getInstance()
				.K_getCurrentTvInputSource();
		if(log)
		Log.i(TAG, "curInputSource is :" + curInputSource);
		boolean noChangeSource = getIntent().getBooleanExtra(
				"no_change_source", false);
		getIntent().removeExtra("no_change_source");
		if(log)
		Log.d(TAG, "mIsMsrvStarted:" + mIsMsrvStarted);
		if (mIsMsrvStarted == true) {
			/**
			 * If current inputsource is storage, it means apk resume from mm.
			 * We need to change inputsource to previous tv inputsource.
			 */
			if ((K_Constants.INPUT_SOURCE_STORAGE == curInputSource)
					|| (K_Constants.INPUT_SOURCE_NONE == curInputSource)) {
				if (!noChangeSource && !mIsPowerOn) {
					new Thread(new Runnable() {
						public void run() {
							if(log)
							Log.d(TAG, "Change InputSource to previous :"
									+ mPreviousInputSource);
							K_TvCommonManager.getInstance().K_setInputSource(
									mPreviousInputSource);
							TVRootApp.setSourceDirty(true);
							if (mPreviousInputSource == K_Constants.INPUT_SOURCE_ATV) {
								K_ChannelModel
										.getInstance()
										.K_changeToFirstService(
												K_Constants.FIRST_SERVICE_INPUT_TYPE_ATV,
												K_Constants.FIRST_SERVICE_DEFAULT);
							} else if (mPreviousInputSource == K_Constants.INPUT_SOURCE_DTV) {
								K_ChannelModel
										.getInstance()
										.K_changeToFirstService(
												K_Constants.FIRST_SERVICE_INPUT_TYPE_DTV,
												K_Constants.FIRST_SERVICE_DEFAULT);
							}
						}
					}).start();
					K_TvPictureManager.getInstance()
							.K_setDynamicBackLightThreadSleep(false);
				}
			} else {
		//nathan.liao 2014.10.14 for menu reset power on source error start
                if (mIsPowerOn == true) {
                    new Thread(new Runnable() {
                            public void run() {
                            Log.d(TAG, "Change InputSource to previous :" + mPreviousInputSource);
                            int curInputSource = K_TvCommonManager.getInstance()
        							.K_getCurrentTvInputSource();
                            // acquire resource control from resource manager
                            K_TvCommonManager.getInstance().K_setInputSource(curInputSource);
                            }
                            }).start();
                }
		
		//nathan.liao 2014.10.14 for menu reset power on source error end

				if (isFirstPowerOn == false) {
					if (curInputSource == K_Constants.INPUT_SOURCE_ATV) {
						startThread(true);
					} else if (curInputSource == K_Constants.INPUT_SOURCE_DTV) {
						startThread(false);
					}
				}
				isFirstPowerOn = false; // move here for first power on
			}
			int swMode = mK_ChannelModel.K_getAtvChannelSwitchMode();
			if (swMode == K_Constants.CHANNEL_SWITCH_MODE_FREEZESCREEN) {
				mK_ChannelModel.K_setChannelChangeFreezeMode(true);
			} else {
				mK_ChannelModel.K_setChannelChangeFreezeMode(false);
			}
			mIsMsrvStarted = false;
		}
		curInputSource = K_TvCommonManager.getInstance()
				.K_getCurrentTvInputSource();
		mIsActive = true;
		if (curInputSource == K_Constants.INPUT_SOURCE_ATV
				|| curInputSource == K_Constants.INPUT_SOURCE_DTV) {
			//nathan.liao 2014.10.14 add for pass word menu and nosigal menu overlay
			if (isHotelMenuLockFlag) {
				isShowPsd = true;
				for (Button btn : mPasswdViews) {
					btn.setFocusable(true);
				}
				resetInput();
				
				rllyPassword.setVisibility(View.VISIBLE);
			} else {
				for (Button btn : mPasswdViews) {
					btn.setFocusable(false);
				}
				isShowPsd = false;
				rllyPassword.setVisibility(View.GONE);
			}
		} else {
			isShowPsd = false;
			for (Button btn : mPasswdViews) {
				btn.setFocusable(false);
			}
			rllyPassword.setVisibility(View.GONE);
		}
		//ktc nathan.liao 20150314 for show nosignl when from usb start
		final int mCurInputSource = curInputSource;
		if(isFromUSB&&!isShowPsd)
		{
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					isFromUSB = false;
					isOnPauseFlag = false;
					if(!mTvCommon.K_isSignalStable(mCurInputSource))
					{
						showNoSignalView(true);
					}
				}
			}, 3000);

		}
		if (!isShowPsd)
		updateScreenSaver();
		if (curInputSource == K_Constants.INPUT_SOURCE_DTV) {
			mK_ChannelModel.K_setUserScanType(K_Constants.TV_SCAN_DTV);
		} else {
			mK_ChannelModel.K_setUserScanType(K_Constants.TV_SCAN_ATV);
		}
	}

	@Override
	protected void onPause() {
		if(log)
		Log.d(TAG, "onPause()");
		if (null != mCecInfoDialog) {
            mHandler.removeMessages(Constant.ROOTACTIVITY_CANCEL_DIALOG);
            mCecInfoDialog.dismiss();
        }
        mCecInfoDialog = null;
		if (mCcKeyToast != null) {
			mCcKeyToast.cancel();
		}
		mIsCecDialogCanceled = false;
		if (true == mIsExiting) {
			if(log)
			Log.i(TAG, "Exiting, prepare to change souce");
			mIsExiting = false;
		}
		mIsActive = false;
		TVRootApp.setSourceDirty(false);

		int curInputSource = K_Constants.INPUT_SOURCE_NONE;
		curInputSource = K_TvCommonManager.getInstance()
				.K_getCurrentTvInputSource();

		/**
		 * If user switch between tv and launch too fast, the currentInputSource
		 * had not changed from storage to last input source. Don't need to
		 * update mPreviousInputSource
		 */
		if ((K_Constants.INPUT_SOURCE_STORAGE != curInputSource)
				&& (K_Constants.INPUT_SOURCE_NONE != curInputSource)) {
			mPreviousInputSource = curInputSource;
		if(log)
			Log.d(TAG, "Save previous inputsource :" + mPreviousInputSource);
			SharedPreferences settings = getSharedPreferences(
					PREFERENCES_INPUT_SOURCE, Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = settings.edit();
			editor.putInt(PREFERENCES_PREVIOUS_INPUT_SOURCE,
					mPreviousInputSource);
			editor.commit();
		}

		if (mExitDialog != null) {
			mExitDialog.dismiss();
		}
		
		if(mNoSignalView.getVisibility() == TextView.VISIBLE)
		{
			showNoSignalView(false);
		}
		isOnPauseFlag = true;
		rllyPassword.setVisibility(View.GONE);
		super.onPause();
	}

	@Override
	protected void onRestart() {
		if(log)
		Log.i(TAG, "onRestart()");
		isFirstPowerOn = true;
		mIsMsrvStarted = true;
		super.onRestart();
	}

	@Override
	public void onDestroy() {
		if(log)
		Log.i(TAG, "onDestroy()");

		if (mCiPlusOPRefreshDialog != null
				&& mCiPlusOPRefreshDialog.isShowing()) {
			mCiPlusOPRefreshDialog.dismiss();
		}

		if (mExitDialog != null && mExitDialog.isShowing()) {
			mExitDialog.dismiss();
		}
		if (systemAutoTime > 0) {
			int curInputSource = K_TvCommonManager.getInstance()
					.K_getCurrentTvInputSource();
			if (curInputSource == K_Constants.INPUT_SOURCE_DTV) {
				Settings.System.putInt(getContentResolver(),
						Settings.Global.AUTO_TIME, systemAutoTime);
			}
		}
		
		K_TvManager.getInstance().K_unregisterOnMhlEventListener(mMhlEventListener);
		K_TvPictureManager.getInstance().K_setDynamicBackLightThreadSleep(true);
		K_ChannelModel.getInstance().K_unregisterOnAtvPlayerEventListener(
				mAtvPlayerEventListener);
		mAtvPlayerEventListener = null;
		K_ChannelModel.getInstance().K_unregisterOnDtvPlayerEventListener(
				mDtvPlayerEventListener);
		mDtvPlayerEventListener = null;
		K_ChannelModel.getInstance().K_unregisterOnTvPlayerEventListener(
				mTvPlayerEventListener);
		mTvPlayerEventListener = null;
		K_TvCommonManager.getInstance().unregisterOnTvEventListener(
				mTvEventListener);
		mTvEventListener = null;
		K_TvCiManager.getInstance().K_unregisterOnUiEventListener(mUiEventListener);
		mUiEventListener = null;
		K_TvCiManager.getInstance().K_unregisterOnCiStatusChangeEventListener(
				mCiStatusChangeEventListener);
		mCiStatusChangeEventListener = null;
		unregisterReceiver(mReceiver);
		mIsActive = false;
		TVRootApp.setSourceDirty(false);
		super.onDestroy();
	}

	@Override
	protected void onNewIntent(final Intent intent) {
		super.onNewIntent(intent);
		executePreviousTask(intent);
		setIntent(intent);

		// In case RootActivity under onStop() stage, we have to handle input
		// source change intent here
		if (getIntent() != null && getIntent().getAction() != null) {
			if (getIntent().getAction().equals(TvIntent.ACTION_SOURCE_CHANGE)) {
				/*
				 * clear screen saver status after changing input source and
				 * hide NoSignalView before change input source.
				 */
				mScreenSaverStatus = SCREENSAVER_DEFAULT_STATUS;
				showNoSignalView(false);
				int inputsource = getIntent().getIntExtra("inputSrc",
						K_Constants.INPUT_SOURCE_ATV);
				if(log)
				Log.i(TAG, "onNewIntent:inputsource = " + inputsource);
				startSourceChange(inputsource);
			}
		}
	}

	private void executePreviousTask(final Intent paramIntent) {
		if (paramIntent != null) {
			String taskTag = paramIntent.getStringExtra("task_tag");
			if ("input_source_changed".equals(taskTag)) {
				TVRootApp.setSourceDirty(true);
			}
		}
	}

	public ArrayList<K_ProgramInfo> getAllProgramList() {
		K_ProgramInfo pgi = null;
		int indexBase = 0;
		ArrayList<K_ProgramInfo> progInfoList = new ArrayList<K_ProgramInfo>();
		int currInputSource = K_TvCommonManager.getInstance()
				.K_getCurrentTvInputSource();
		int m_nServiceNum = 0;
		if (currInputSource == K_Constants.INPUT_SOURCE_ATV) {
			indexBase = mK_ChannelModel
					.K_getProgramCount(K_Constants.PROGRAM_COUNT_DTV);
			if (indexBase == 0xFFFFFFFF) {
				indexBase = 0;
			}
			m_nServiceNum = mK_ChannelModel
					.K_getProgramCount(K_Constants.PROGRAM_COUNT_ATV_DTV);
		} else {
			indexBase = 0;
			m_nServiceNum = mK_ChannelModel
					.K_getProgramCount(K_Constants.PROGRAM_COUNT_DTV);
		}
		if(log)
		{
		Log.d(TAG, "indexBase:" + indexBase);
		Log.d(TAG, "m_nServiceNum:" + m_nServiceNum);
		}
		for (int k = indexBase; k < m_nServiceNum; k++) {
				pgi = mK_ChannelModel.K_getProgramInfoByIndex(k);
			if (pgi != null) {
				if ((pgi.getProgram().isDelete == true) || (pgi.getProgram().isVisible == false)) {
					continue;
				} else {
					progInfoList.add(pgi);
				}
			}
		}
		return progInfoList;
	}

	public K_ProgramInfo getNextProgramm() {

		ArrayList<K_ProgramInfo> progInfoList = getAllProgramList();
		if (progInfoList == null || progInfoList.size() == 0) {
			return null;
		}
		K_ProgramInfo currentProgInfo = mK_ChannelModel.K_getCurrentProgramInfo();
		if (currentProgInfo.getProgram() == null) {
			return null;
		}
		K_ProgramInfo next = null;
		for (int i = 0; i < progInfoList.size(); i++) {
			K_ProgramInfo pi = progInfoList.get(i);
			if (currentProgInfo.getProgram().frequency == pi.getProgram().frequency
					&& currentProgInfo.getProgram().serviceId == pi.getProgram().serviceId
					&& currentProgInfo.getProgram().number == pi.getProgram().number
					&& currentProgInfo.getProgram().progId == pi.getProgram().progId) {
				if (i < progInfoList.size() - 1) {
					next = progInfoList.get(i + 1);
					break;
				}
				if (i == progInfoList.size() - 1) {
					next = progInfoList.get(0);
				}

			}
		}
		return next;

	}

	public K_ProgramInfo getPreviousProgram() {
		ArrayList<K_ProgramInfo> progInfoList = getAllProgramList();
		K_ProgramInfo currentProgInfo = mK_ChannelModel.K_getCurrentProgramInfo();
		if (currentProgInfo.getProgram() == null) {
			return null;
		}
		K_ProgramInfo previous = null;
		for (int i = 0; i < progInfoList.size(); i++) {
			K_ProgramInfo pi = progInfoList.get(i);
			if (currentProgInfo.getProgram().number == pi.getProgram().number
					&& currentProgInfo.getProgram().serviceType == pi.getProgram().serviceType) {

				if (i == 0) {
					previous = progInfoList.get(progInfoList.size() - 1);
					break;
				}
				if (i > 0) {
					previous = progInfoList.get(i - 1);
				}

			}
		}
		return previous;
	}

	/**
	 * handle the up, down, return and 0-9 key
	 * 
	 * @param keyCode
	 * @return
	 */
	public boolean onChannelChange(int keyCode) {
		if (K_TvCommonManager.getInstance().K_getCurrentTvInputSource() == K_Constants.INPUT_SOURCE_ATV
				|| K_TvCommonManager.getInstance().K_getCurrentTvInputSource() == K_Constants.INPUT_SOURCE_DTV) {
			if (keyCode == KeyEvent.KEYCODE_CHANNEL_DOWN
					|| keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
				// Hisa 2016.03.04 add Freeze function start
				Log.d("Jason","切换频道");
					Intent intent = new Intent();//取消静像菜单
					intent.setAction(MIntent.ACTION_FREEZE_CANCEL_BUTTON);
					//K_TvPictureManager.getInstance().K_unFreezeImage();
					sendBroadcast(intent);  
					// Hisa 2016.03.04 add Freeze function end
				mK_ChannelModel.K_programDown();
				startSourceInfo();
				return true;

			} else if (keyCode == KeyEvent.KEYCODE_CHANNEL_UP
					|| keyCode == KeyEvent.KEYCODE_DPAD_UP) {
				// Hisa 2016.03.04 add Freeze function start
				Log.d("Jason","切换频道");
					Intent intent = new Intent();//取消静像菜单
					intent.setAction(MIntent.ACTION_FREEZE_CANCEL_BUTTON);
					//K_TvPictureManager.getInstance().K_unFreezeImage();
					sendBroadcast(intent);  
				// Hisa 2016.03.04 add Freeze function end
				mK_ChannelModel.K_programUp();
				startSourceInfo();
				return true;
			} else if (keyCode == K_MKeyEvent.KEYCODE_CHANNEL_RETURN) {
				mK_ChannelModel.K_returnToPreviousProgram();
				Intent intent = new Intent(TvIntent.ACTION_SOURCEINFO);
				intent.putExtra("info_key", true);
				this.startActivity(intent);
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
				Intent intentChannelControl = new Intent(this,
						ChannelControlActivity.class);
				intentChannelControl.putExtra("KeyCode", keyCode);
				this.startActivity(intentChannelControl);
				return true;
			}
		}
		return false;
	}

	static boolean canRepeatKey = true;

	static int preKeyCode = KeyEvent.KEYCODE_UNKNOWN;

	Handler mRepeatHandler = new Handler();

	Runnable mRepeatRun = new Runnable() {
		public void run() {
			canRepeatKey = true;
		}
	};

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {

		if (sendGingaKey(keyCode, event)) {
			if(log)
			Log.i(TAG, "onKeyUp:sendGingaKey success!");
			return true;
		}

		if (mTvMhlManager.K_CbusStatus() == true
				&& mTvMhlManager.K_IsMhlPortInUse() == true) {
			if (mTvMhlManager.K_IRKeyProcess(keyCode, true) == true) {
				SystemClock.sleep(140);
				return true;
			}
		}
		return super.onKeyUp(keyCode, event);
	};
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 if(PropHelp.newInstance().hasHotel())
	        {
	        	if(isShowPsd)
	        	{
	        		if(keyCode == K_MKeyEvent.KEYCODE_ASPECT_RATIO)
	        			return true;
	        	}
	        }

		final boolean down = event.getAction() == KeyEvent.ACTION_DOWN;
		if (down
				&& (keyCode == KeyEvent.KEYCODE_M || keyCode == KeyEvent.KEYCODE_MENU)) {

			if (!canRepeatKey && !(preKeyCode == keyCode)) {
				preKeyCode = keyCode;
			} else {
				preKeyCode = keyCode;
				mRepeatHandler.removeCallbacks(mRepeatRun);
				canRepeatKey = false;
				mRepeatHandler.postDelayed(mRepeatRun, 2000);
			}
		}

		if (sendHbbTVKey(keyCode)) {
			if(log)
			Log.i(TAG, "onKeyDown:sendHbbTVKey success!");
			return true;
		}

		// arrange CEC key
		if (sendCecKey(keyCode)) {
			if(log)
			Log.i(TAG, "onKeyDown:sendCecKey success!");
			return true;
		}

		// arrange Mheg5 key
		if (sendMheg5Key(keyCode)) {
			if(log)
			Log.i(TAG, "onKeyDown:sendMhegKey success!");
			return true;
		}

		// arrange Ginga key
		if (sendGingaKey(keyCode, event)) {
			if(log)
			Log.i(TAG, "onKeyDown:sendGingaKey success!");
			return true;
		}

		if (mTvMhlManager.K_CbusStatus() == true
				&& mTvMhlManager.K_IsMhlPortInUse() == true) {
			if (mTvMhlManager.K_IRKeyProcess(keyCode, false) == true) {
				SystemClock.sleep(140);
				return true;
			}
		}

		if (!Constant.lockKey && keyCode != KeyEvent.KEYCODE_BACK) {
			return true;
		}
		if (_3Dflag) {
		}
		if(log)
		Log.d(TAG, " onKeyDown  keyCode = " + keyCode);
		switch (keyCode) {
		case K_MKeyEvent.KEYCODE_CC:
			if (mTvCommon.K_isSupportModule(K_Constants.MODULE_CC)
					|| mTvCommon
							.K_isSupportModule(K_Constants.MODULE_BRAZIL_CC)) {
				if (mCcKeyToast == null) {
					mCcKeyToast = new Toast(this);
					mCcKeyToast.setGravity(Gravity.CENTER, 0, 0);
				}
				TextView tv = new TextView(RootActivity.this);
				tv.setTextSize(Constant.CCKEY_TEXTSIZE);
				tv.setTextColor(Color.WHITE);
				tv.setAlpha(Constant.CCKEY_ALPHA);
				mCcKeyToast.setView(tv);
				mCcKeyToast.setDuration(Toast.LENGTH_SHORT);

				int closedCaptionMode = K_SettingModel.getInstance()
						.K_getNextClosedCaptionMode();
				K_SettingModel.getInstance().K_setClosedCaptionMode(
						closedCaptionMode);
				K_SettingModel.getInstance().K_stopCc();
				if (K_Constants.CLOSED_CAPTION_OFF != closedCaptionMode) {
					K_SettingModel.getInstance().K_startCc();
				}
				String[] closedCaptionStrings = getResources().getStringArray(
						R.array.str_arr_option_closed_caption);
				if (0 <= closedCaptionMode
						&& closedCaptionStrings.length > closedCaptionMode) {
					tv.setText(getResources().getString(
							R.string.str_option_caption)
							+ " " + closedCaptionStrings[closedCaptionMode]);
				}
				mCcKeyToast.show();
			}
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			String deviceName = InputDevice.getDevice(event.getDeviceId())
					.getName();
			if (deviceName.equals("MStar Smart TV IR Receiver")
					|| deviceName.equals("MStar Smart TV Keypad")) {
				AudioManager audiomanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
				/*
				 * Adjust the volume in on key down since it is more responsive
				 * to the user.
				 */
				if (audiomanager != null) {
					int flags = AudioManager.FLAG_SHOW_UI
							| AudioManager.FLAG_VIBRATE;
					audiomanager
							.adjustVolume(
									keyCode == KeyEvent.KEYCODE_DPAD_RIGHT ? AudioManager.ADJUST_RAISE
											: AudioManager.ADJUST_LOWER, flags);
				}
			} else {
				if(log)
				Log.d(TAG, "deviceName is:" + deviceName);
			}
			break;
		case KeyEvent.KEYCODE_PROG_YELLOW:
		case KeyEvent.KEYCODE_PROG_BLUE:
		case KeyEvent.KEYCODE_PROG_GREEN:
		case KeyEvent.KEYCODE_PROG_RED:
		case KeyEvent.KEYCODE_ENTER:
			break;
		case KeyEvent.KEYCODE_BACK:
			if (mIsBackKeyPressed == false) {
				mIsActive = false;
				mIsBackKeyPressed = true;
				if (!mIskeyLocked) {
					mIskeyLocked = true;
					showExitDialog();
				}
			}
			return true;
		case KeyEvent.KEYCODE_M:
		case KeyEvent.KEYCODE_MENU:
			break;
		}
		if (!mIskeyLocked
				&& SwitchPageHelper.goToMenuPage(this, keyCode) == true) {
			mIskeyLocked = true;
			if(log)
			Log.d(TAG, "onKeyDown->goToMenuPage  keyCode = " + keyCode);
			return true;
		} else if (SwitchPageHelper.goToSourceInfo(this, keyCode) == true) {
			return true;
		} else if (SwitchPageHelper.goToEpgPage(this, keyCode) == true) {
			return true;
		} else if (SwitchPageHelper.goToSubtitleLangPage(this, keyCode) == true) {
			return true;
		} else if (SwitchPageHelper.goToAudioLangPage(this, keyCode) == true) {
			return true;
		} else if (SwitchPageHelper.goToProgrameListInfo(this, keyCode) == true) {
			return true;
		} else if (SwitchPageHelper.goToFavorateListInfo(this, keyCode) == true) {
			return true;
		} else if (SwitchPageHelper.goToSleepMode(this, keyCode) == true) {
			return true;
		} else if (SwitchPageHelper.goToTeletextPage(this, keyCode) == true) {
			return true;
		} else if (onChannelChange(keyCode) == true) {
			return true;
		} else if (SwitchPageHelper.goTo3DMenuPage(this, keyCode) == true){
				return true;
		}else if (SwitchPageHelper.factoryControl(this, keyCode) == true) {
			return true;
        	}
		return super.onKeyDown(keyCode, event);
	}

	private void createSurfaceView() {
		tvView = (TvView) findViewById(R.id.tranplentview);
		Boolean isPowerOn = getIntent() != null ? getIntent().getBooleanExtra(
				"isPowerOn", false) : false;
		if(log)
		Log.d(TAG, "isPowerOn = " + isPowerOn);
		String taskTag = getIntent().getStringExtra("task_tag");
		// true means don't set window size which will cause black screen
		tvView.openView(isPowerOn || "input_source_changed".equals(taskTag));
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case Constant.CHANNEL_LOCK_RESULT_CODE:
			if (data.getExtras().getBoolean("result")) {
				mNoSignalView.setVisibility(TextView.INVISIBLE);
			}
			break;
		default:
			if(log)
			Log.w(TAG, "onActivityResult resultCode not match");
			break;
		}
	}

	/*
	 * update NoSignalView Text String by Screen Saver Status and input source.
	 */
	private Handler signalLockHandler = new Handler() {
		public void handleMessage(Message msg) {
			int lockStatus = msg.arg1;
			if (TvEvent.SIGNAL_LOCK == lockStatus) {
				mIsSignalLock = true;
			} else if (TvEvent.SIGNAL_UNLOCK == lockStatus) {
				mIsSignalLock = false;
			}

			int curInputSource = K_TvCommonManager.getInstance()
					.K_getCurrentTvInputSource();
			mIsToPromptPassword = false;
			if (mIsSignalLock) {
				/*
				 * send broadcast to those who need to know signal lock status.
				 * ex: SourceInfoActivity will get this intent when it is alive,
				 * and update source info content.
				 */
				sendBroadcast(new Intent(TvIntent.ACTION_SIGNAL_LOCK));

				switch (curInputSource) {
				case K_Constants.INPUT_SOURCE_DTV: {
					/* show SourceInfo before updating NoSignalView */
					startSourceInfo();
					/* update NoSignalView string by screen saver status */
					if (ScreenSaverMode.DTV_SS_INVALID_SERVICE == mScreenSaverStatus) {
						mNoSignalView.setText(R.string.str_invalid_service);
					} else if (ScreenSaverMode.DTV_SS_NO_CI_MODULE == mScreenSaverStatus) {
						mNoSignalView.setText(R.string.str_no_ci_module);
					} else if (ScreenSaverMode.DTV_SS_CI_PLUS_AUTHENTICATION == mScreenSaverStatus) {
						mNoSignalView
								.setText(R.string.str_ci_plus_authentication);
					} else if (ScreenSaverMode.DTV_SS_SCRAMBLED_PROGRAM == mScreenSaverStatus) {
						mNoSignalView.setText(R.string.str_scrambled_program);
					} else if (ScreenSaverMode.DTV_SS_UNSUPPORTED_FORMAT == mScreenSaverStatus) {
						/*
						 * FIXME: atsc screen saver status is separate by flag
						 * in supernova enum of screen saver should be sync
						 * between each tv system.
						 */
							mNoSignalView.setText(R.string.str_unsupported);
					} else if (ScreenSaverMode.DTV_SS_CH_BLOCK == mScreenSaverStatus) {
						mIsToPromptPassword = true;
						mNoSignalView.setText(R.string.str_channel_block);
					} else if (ScreenSaverMode.DTV_SS_PARENTAL_BLOCK == mScreenSaverStatus) {
						mIsToPromptPassword = true;
						mNoSignalView.setText(R.string.str_parental_block);
					} else if (ScreenSaverMode.DTV_SS_AUDIO_ONLY == mScreenSaverStatus) {
						mNoSignalView.setText(R.string.str_audio_only);
					} else if (ScreenSaverMode.DTV_SS_DATA_ONLY == mScreenSaverStatus) {
						mNoSignalView.setText(R.string.str_data_only);
					} else if (ScreenSaverMode.DTV_SS_COMMON_VIDEO == mScreenSaverStatus) {
						// Reset NoSignalView string to default string : No
						// Signal
						mNoSignalView.setText(R.string.str_no_signal);
					} else if (ScreenSaverMode.DTV_SS_INVALID_PMT == mScreenSaverStatus) {
						mNoSignalView.setText(R.string.str_invalid_pmt);
					} else if (SCREENSAVER_DEFAULT_STATUS == mScreenSaverStatus) {
						/*
						 * Status fall into a default value case
						 * (SCREENSAVER_DEFAULT_STATUS) skip updating
						 * NoSignalView, it will be updated when
						 * ScreenSaverStatus be updated.
						 */
						if(log)
						Log.i(TAG,
								"Default ScreenSaver status, wait screenSaverHandler updating NoSignalView.");
						break;
					} else {
						if(log)
						Log.w(TAG,
								"Current Screen Saver Status is unrecognized");
						if(log)
						Log.w(TAG, "status: " + mScreenSaverStatus);
						break;
					}
					if(log)
					Log.d(TAG, "screen saver status is " + mScreenSaverStatus);
					/*
					 * update NoSignalView Visibility by signal lock flag and
					 * screen saver status
					 */
					if (ScreenSaverMode.DTV_SS_COMMON_VIDEO != mScreenSaverStatus||((PropHelp.newInstance().hasHotel())&&ScreenSaverMode.DTV_SS_CH_BLOCK != mScreenSaverStatus)) {
						if(PropHelp.newInstance().hasHotel())
	                	{
	                		if(!(isShowPsd||isHotelMenuLockFlag))
	                		{
	                			mIsScreenSaverShown = true;
								showNoSignalView(true);
	                		}
	                	}else
	                	{
	                		mIsScreenSaverShown = true;
							showNoSignalView(true);
	                	}
					} else {
						mIsScreenSaverShown = false;
						showNoSignalView(false);
					}
					break;
				}
				case K_Constants.INPUT_SOURCE_HDMI:
				case K_Constants.INPUT_SOURCE_HDMI2:
				case K_Constants.INPUT_SOURCE_HDMI3:
				case K_Constants.INPUT_SOURCE_HDMI4:
					if ((SignalProgSyncStatus.STABLE_UN_SUPPORT_MODE == mScreenSaverStatus)
							|| (SignalProgSyncStatus.UNSTABLE == mScreenSaverStatus)) {
						mNoSignalView.setText(R.string.str_unsupported);
						mIsScreenSaverShown = true;
						showNoSignalView(true);
					} else if (SignalProgSyncStatus.STABLE_SUPPORT_MODE == mScreenSaverStatus) {
						mIsScreenSaverShown = false;
						showNoSignalView(false);
					}
					startSourceInfo();
					break;
				case K_Constants.INPUT_SOURCE_VGA:
					if ((SignalProgSyncStatus.STABLE_UN_SUPPORT_MODE == mScreenSaverStatus)
							|| (SignalProgSyncStatus.UNSTABLE == mScreenSaverStatus)) {
						mNoSignalView.setText(R.string.str_unsupported);
						mIsScreenSaverShown = true;
						showNoSignalView(true);
					} else if (SignalProgSyncStatus.STABLE_SUPPORT_MODE == mScreenSaverStatus) {
						mIsScreenSaverShown = false;
						showNoSignalView(false);
						startSourceInfo();
					} else if (SignalProgSyncStatus.AUTO_ADJUST == mScreenSaverStatus) {
						mNoSignalView.setText(R.string.str_auto_adjust);
						mIsScreenSaverShown = true;
						showNoSignalView(true);
						signalLockHandler.postDelayed(new Runnable() {
							@Override
							public void run() {
								mIsScreenSaverShown = false;
								showNoSignalView(false);
								startSourceInfo();
							}
						}, 3000);
					}
					break;
				case K_Constants.INPUT_SOURCE_CVBS:
				case K_Constants.INPUT_SOURCE_CVBS2:
				case K_Constants.INPUT_SOURCE_CVBS3:
				case K_Constants.INPUT_SOURCE_CVBS4:
					mIsScreenSaverShown = false;
					showNoSignalView(false);
					startSourceInfo();
					break;
				case K_Constants.INPUT_SOURCE_YPBPR:
				case K_Constants.INPUT_SOURCE_YPBPR2:
				case K_Constants.INPUT_SOURCE_YPBPR3:
					mIsScreenSaverShown = false;
					showNoSignalView(false);
					startSourceInfo();
					break;
				case K_Constants.INPUT_SOURCE_STORAGE:
					mIsScreenSaverShown = false;
					showNoSignalView(false);
					break;
				default:
					mIsScreenSaverShown = false;
					showNoSignalView(false);
					startSourceInfo();
					break;
				}
			} else {
				// signal unlock case
				switch (curInputSource) {
				case K_Constants.INPUT_SOURCE_ATV:
					// atv would not show nosignal text even signal unlock.
					mIsScreenSaverShown = false;
					showNoSignalView(false);
					mIsSignalLock = true;
					return;
				default:
					mScreenSaverStatus = SCREENSAVER_DEFAULT_STATUS;
					mNoSignalView.setText(R.string.str_no_signal);
					if(log)
					Log.d(TAG, "unlock show nosignal");
					mIsScreenSaverShown = true;
					if (!isOnPauseFlag)
					{	
					showNoSignalView(true);
					}
				}
			}
		};
	};

	/**
	 * Used to handle scrren saver mode, decide if we need to show NoSignal
	 * Text. Each inputsource will have itself situation. Status can be
	 * referenced in two enum: EnumScreenMode, EnumSignalProgSyncStatus.
	 */
	private Handler screenSaverHandler = new Handler() {
		public void handleMessage(Message msg) {
			int curInputSource = K_TvCommonManager.getInstance()
					.K_getCurrentTvInputSource();
			int status = msg.arg1;
			switch (curInputSource) {
			case K_Constants.INPUT_SOURCE_ATV:
			case K_Constants.INPUT_SOURCE_DTV:
				/* update NoSignalView string by screen saver status */
				if (ScreenSaverMode.DTV_SS_INVALID_SERVICE == status) {
					mIsScreenSaverShown = true;
					mNoSignalView.setText(R.string.str_invalid_service);
				} else if (ScreenSaverMode.DTV_SS_NO_CI_MODULE == status) {
					mIsScreenSaverShown = true;
					mNoSignalView.setText(R.string.str_no_ci_module);
				} else if (ScreenSaverMode.DTV_SS_CI_PLUS_AUTHENTICATION == status) {
					mIsScreenSaverShown = true;
					mNoSignalView.setText(R.string.str_ci_plus_authentication);
				} else if (ScreenSaverMode.DTV_SS_SCRAMBLED_PROGRAM == status) {
					mIsScreenSaverShown = true;
					mNoSignalView.setText(R.string.str_scrambled_program);
				} else if (ScreenSaverMode.DTV_SS_UNSUPPORTED_FORMAT == status) {
					/*
					 * FIXME: atsc screen saver status is separate by flag in
					 * supernova enum of screen saver should be sync between
					 * each tv system.
					 */
					mIsScreenSaverShown = true;
						mNoSignalView.setText(R.string.str_unsupported);
				} else if (ScreenSaverMode.DTV_SS_AUDIO_ONLY == status) {
					mIsScreenSaverShown = true;
					mNoSignalView.setText(R.string.str_audio_only);
				} else if (ScreenSaverMode.DTV_SS_DATA_ONLY == status) {
					mIsScreenSaverShown = true;
					mNoSignalView.setText(R.string.str_data_only);
				} else if (ScreenSaverMode.DTV_SS_COMMON_VIDEO == status) {
					// Reset NoSignalView string to default string : No Signal
					mIsScreenSaverShown = false;
					mNoSignalView.setText(R.string.str_no_signal);
				} else if (ScreenSaverMode.DTV_SS_INVALID_PMT == status) {
					mIsScreenSaverShown = true;
					mNoSignalView.setText(R.string.str_invalid_pmt);
				}
				break;
			case K_Constants.INPUT_SOURCE_VGA:
				if (SignalProgSyncStatus.STABLE_UN_SUPPORT_MODE == status) {
					mNoSignalView.setText(R.string.str_unsupported);
					mIsScreenSaverShown = true;
					showNoSignalView(true);
				} else if (SignalProgSyncStatus.AUTO_ADJUST == status) {
					mIsScreenSaverShown = true;
					showNoSignalView(true);
					screenSaverHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							mIsScreenSaverShown = false;
							showNoSignalView(false);
							startSourceInfo();
						}
					}, 3000);
				} else if (SignalProgSyncStatus.STABLE_SUPPORT_MODE == status) {
					mIsScreenSaverShown = false;
					showNoSignalView(false);
					startSourceInfo();
				}
				break;
			case K_Constants.INPUT_SOURCE_CVBS:
			case K_Constants.INPUT_SOURCE_CVBS2:
			case K_Constants.INPUT_SOURCE_CVBS3:
			case K_Constants.INPUT_SOURCE_CVBS4:
				if (ScreenSaverMode.DTV_SS_PARENTAL_BLOCK == mScreenSaverStatus) {
					mNoSignalView.setText(R.string.str_parental_block);
					if (true == mIsActive) {
					}
					mIsScreenSaverShown = true;
					showNoSignalView(true);
				} else if (ScreenSaverMode.DTV_SS_COMMON_VIDEO == mScreenSaverStatus) {
					mNoSignalView.setText(R.string.str_no_signal);
					mIsScreenSaverShown = false;
					showNoSignalView(false);
				}
				break;
			case K_Constants.INPUT_SOURCE_YPBPR:
			case K_Constants.INPUT_SOURCE_YPBPR2:
			case K_Constants.INPUT_SOURCE_YPBPR3:
				break;
			case K_Constants.INPUT_SOURCE_HDMI:
			case K_Constants.INPUT_SOURCE_HDMI2:
			case K_Constants.INPUT_SOURCE_HDMI3:
			case K_Constants.INPUT_SOURCE_HDMI4:
				if ((SignalProgSyncStatus.UNSTABLE == status)
						|| (SignalProgSyncStatus.STABLE_UN_SUPPORT_MODE == status)) {
					mIsScreenSaverShown = true;
					showNoSignalView(true);
				} else if (SignalProgSyncStatus.STABLE_SUPPORT_MODE == status) {
					mIsScreenSaverShown = false;
					showNoSignalView(false);
				}
				break;
			default:
				break;
			}
			if(mIsScreenSaverShown)
			startNoSignal();
		};
	};

	private void tvApkExitHandler() {
		mIsActive = false;
		bCmd_TvApkExit = true;
		mIsBackKeyPressed = true;
		showExitDialog();
		return;
	}

	private class CecCtrlEventListener extends K_OnCecCtrlEventListener {

		@Override
		public boolean K_onCecCtrlEvent(int what, int arg1, int arg2) {
			switch (what) {
			case K_Constants.TVCEC_STANDBY: {
				if(log)
				Log.i(TAG, "&&&&&&&&&&______________EV_CEC_STANDBY");
				K_TvCommonManager.getInstance().standbySystem("cec");
			}
				break;
            case K_Constants.TVCEC_SET_MENU_LANGUAGE: {
                Log.i(TAG, "EV_CEC_SET_MENU_LANGUAGE");
                try {
                    IActivityManager am = ActivityManagerNative.getDefault();
                    Configuration config = am.getConfiguration();
                    config.locale = TvLanguage.getLocale(arg1, config.locale);

                    // indicate this isn't some passing default - the user
                    // wants
                    // this remembered
                    config.userSetLocale = true;
                    am.updateConfiguration(config);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
                break;
            case K_Constants.TVCEC_SOURCE_SWITCH: {
                Log.i(TAG, "EV_CEC_SOURCE_SWITCH");
                startSourceChange(arg1);
            }
                break;
            case K_Constants.TVCEC_SEL_DIGITAL_SERVICE_DVB: {
                Log.i(TAG, "EV_CEC_SEL_DIGITAL_SERVICE_DVB");
                Log.i(TAG, "msg.arg1: " + arg1 + " msg.arg2: " + arg2);
                mK_ChannelModel.K_selectProgram(arg1, K_Constants.SERVICE_TYPE_DTV);
            }
                break;
            case K_Constants.TVCEC_UPDATE_EDID: {
                if (null == mCecInfoDialog) {
                    if (K_Constants.CEC_DIALOG_SHOW == arg1) {
                        mCecInfoDialog = ProgressDialog.show(RootActivity.this, "", getString(R.string.str_updating_edid), true, false);
                        mIsCecDialogCanceled = false;
                        mHandler.sendEmptyMessageDelayed(Constant.ROOTACTIVITY_CANCEL_DIALOG, CEC_INFO_DISPLAY_TIMEOUT);
                    }
                } else {
                    if (K_Constants.TVCEC_STANDBY == arg1) {
                        mIsCecDialogCanceled = true;
                    }
                }
            }
                break;
            default: {
				if(log)
                Log.i(TAG, "Unknown message type " + what);
            }
                break;
        
			}
			return true;
		}/*
		public boolean onCecCtrlEvent(int what, int arg1, int arg2) {
			switch (what) {
			case K_Constants.TVCEC_STANDBY: {
				if(log)
				Log.i(TAG, "&&&&&&&&&&______________EV_CEC_STANDBY");
				K_TvCommonManager.getInstance().standbySystem("cec");
			}
				break;
            case K_Constants.TVCEC_SET_MENU_LANGUAGE: {
                Log.i(TAG, "EV_CEC_SET_MENU_LANGUAGE");
                try {
                    IActivityManager am = ActivityManagerNative.getDefault();
                    Configuration config = am.getConfiguration();
                    config.locale = TvLanguage.getLocale(arg1, config.locale);

                    // indicate this isn't some passing default - the user
                    // wants
                    // this remembered
                    config.userSetLocale = true;
                    am.updateConfiguration(config);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
                break;
            case K_Constants.TVCEC_SOURCE_SWITCH: {
                Log.i(TAG, "EV_CEC_SOURCE_SWITCH");
                startSourceChange(arg1);
            }
                break;
            case K_Constants.TVCEC_SEL_DIGITAL_SERVICE_DVB: {
                Log.i(TAG, "EV_CEC_SEL_DIGITAL_SERVICE_DVB");
                Log.i(TAG, "msg.arg1: " + arg1 + " msg.arg2: " + arg2);
                mK_ChannelModel.K_selectProgram(arg1, K_Constants.SERVICE_TYPE_DTV);
            }
                break;
            case K_Constants.TVCEC_UPDATE_EDID: {
                if (null == mCecInfoDialog) {
                    if (K_Constants.CEC_DIALOG_SHOW == arg1) {
                        mCecInfoDialog = ProgressDialog.show(RootActivity.this, "", getString(R.string.str_updating_edid), true, false);
                        mIsCecDialogCanceled = false;
                        mHandler.sendEmptyMessageDelayed(Constant.ROOTACTIVITY_CANCEL_DIALOG, CEC_INFO_DISPLAY_TIMEOUT);
                    }
                } else {
                    if (K_Constants.TVCEC_STANDBY == arg1) {
                        mIsCecDialogCanceled = true;
                    }
                }
            }
                break;
            default: {
				if(log)
                Log.i(TAG, "Unknown message type " + what);
            }
                break;
        
			}
			return true;
		}
	*/}

	protected class UiEventListener extends K_OnUiEventListener {

		@Override
		public boolean K_onUiEvent(int what) {
			switch (what) {
			case K_Constants.TVCI_UI_DATA_READY: {
				if (K_Constants.INPUT_SOURCE_STORAGE != mTvCommon
						.K_getCurrentTvInputSource()) {
					ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
					String foreGroundActivity = am.getRunningTasks(1).get(0).topActivity
							.getClassName();
					if (foreGroundActivity
							.equals(CimmiActivity.class.getName()) == false) {
						Intent intent = new Intent(RootActivity.this,
								CimmiActivity.class);
						RootActivity.this.startActivity(intent);
					}
				}
			}
				break;
			case K_Constants.TVCI_UI_CARD_INSERTED: {
				Toast toast = Toast.makeText(RootActivity.this,
						R.string.str_cimmi_hint_ci_inserted, 5);
				toast.show();
			}
				break;
			case K_Constants.TVCI_UI_CARD_REMOVED: {
				Toast toast = Toast.makeText(RootActivity.this,
						R.string.str_cimmi_hint_ci_removed, 5);
				toast.show();
				if (mScreenSaverStatus == ScreenSaverMode.DTV_SS_SCRAMBLED_PROGRAM) {
					mScreenSaverStatus = ScreenSaverMode.DTV_SS_NO_CI_MODULE;
					sendBroadcast(new Intent(TvIntent.ACTION_REDRAW_NOSIGNAL));
				}
			}
				break;
			default: {
			}
				break;
			}
			return true;
		}/*
		@Override
		public boolean onUiEvent(int what) {
			switch (what) {
			case K_Constants.TVCI_UI_DATA_READY: {
				if (K_Constants.INPUT_SOURCE_STORAGE != mTvCommon
						.K_getCurrentTvInputSource()) {
					ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
					String foreGroundActivity = am.getRunningTasks(1).get(0).topActivity
							.getClassName();
					if (foreGroundActivity
							.equals(CimmiActivity.class.getName()) == false) {
						Intent intent = new Intent(RootActivity.this,
								CimmiActivity.class);
						RootActivity.this.startActivity(intent);
					}
				}
			}
				break;
			case K_Constants.TVCI_UI_CARD_INSERTED: {
				Toast toast = Toast.makeText(RootActivity.this,
						R.string.str_cimmi_hint_ci_inserted, 5);
				toast.show();
			}
				break;
			case K_Constants.TVCI_UI_CARD_REMOVED: {
				Toast toast = Toast.makeText(RootActivity.this,
						R.string.str_cimmi_hint_ci_removed, 5);
				toast.show();
				if (mScreenSaverStatus == ScreenSaverMode.DTV_SS_SCRAMBLED_PROGRAM) {
					mScreenSaverStatus = ScreenSaverMode.DTV_SS_NO_CI_MODULE;
					sendBroadcast(new Intent(TvIntent.ACTION_REDRAW_NOSIGNAL));
				}
			}
				break;
			default: {
			}
				break;
			}
			return true;
		}
	*/}

	private class CiStatusChangeEventListener extends
			K_OnCiStatusChangeEventListener {

		@Override
		public boolean K_onCiStatusChanged(int what, int arg1, int arg2) {
			Log.i(TAG, "onCiStatusChanged(), what:" + what);
			switch (what) {
			case K_Constants.TVCI_STATUS_CHANGE_TUNER_OCCUPIED: {
				switch (arg1) {
				case K_Constants.CI_NOTIFY_CU_IS_PROGRESS: {
					openNotifyMessage(getString(R.string.str_cam_upgrade_alarm));
				}
					break;
				case K_Constants.CI_NOTIFY_OP_IS_TUNING: {
					openNotifyMessage(getString(R.string.str_op_tuning_alarm));
				}
					break;
				default: {
					Log.d(TAG, "Unknown CI occupied status, arg1 = " + arg1);
				}
					break;
				}
				Log.i(TAG,
						"sendBroadcast CIPLUS_TUNER_UNAVAIABLE intent: status = "
								+ arg1);
				Intent intent = new Intent(
						TvIntent.ACTION_CIPLUS_TUNER_UNAVAIABLE);
				intent.putExtra(Constant.TUNER_AVAIABLE, false);
				sendBroadcast(intent);
			}
				break;
			default: {
			}
				break;

			}
			return true;
		}/*
		@Override
		public boolean onCiStatusChanged(int what, int arg1, int arg2) {
			Log.i(TAG, "onCiStatusChanged(), what:" + what);
			switch (what) {
			case K_Constants.TVCI_STATUS_CHANGE_TUNER_OCCUPIED: {
				switch (arg1) {
				case K_Constants.CI_NOTIFY_CU_IS_PROGRESS: {
					openNotifyMessage(getString(R.string.str_cam_upgrade_alarm));
				}
					break;
				case K_Constants.CI_NOTIFY_OP_IS_TUNING: {
					openNotifyMessage(getString(R.string.str_op_tuning_alarm));
				}
					break;
				default: {
					Log.d(TAG, "Unknown CI occupied status, arg1 = " + arg1);
				}
					break;
				}
				Log.i(TAG,
						"sendBroadcast CIPLUS_TUNER_UNAVAIABLE intent: status = "
								+ arg1);
				Intent intent = new Intent(
						TvIntent.ACTION_CIPLUS_TUNER_UNAVAIABLE);
				intent.putExtra(Constant.TUNER_AVAIABLE, false);
				sendBroadcast(intent);
			}
				break;
			default: {
			}
				break;

			}
			return true;
		}
	*/}

	Thread t_atv = null;

	Thread t_dtv = null;

	private void startThread(boolean type) {
		if (type) {
			// atv
			if (t_atv == null) {
				t_atv = new Thread(new Runnable() {
					@Override
					public void run() {
						mK_ChannelModel.K_changeToFirstService(
								K_Constants.FIRST_SERVICE_INPUT_TYPE_ATV,
								K_Constants.FIRST_SERVICE_DEFAULT);
						t_atv = null;
					}
				});
				t_atv.start();
			}
		} else {
			if (t_dtv == null) {
				// dtv
				t_dtv = new Thread(new Runnable() {
					@Override
					public void run() {
						mK_ChannelModel.K_changeToFirstService(
								K_Constants.FIRST_SERVICE_INPUT_TYPE_DTV,
								K_Constants.FIRST_SERVICE_DEFAULT);
						t_dtv = null;
					}
				});
				t_dtv.start();
			}
		}
	}

	private void startNoSignal() {
		if(log)
		Log.d(TAG, "mIsSignalLock: " + mIsSignalLock + " mIsScreenSaverShown: "
				+ mIsScreenSaverShown);

		if (mIsSignalLock) {

			if (mIsScreenSaverShown) {
				showNoSignalView(true);
			} else {
				showNoSignalView(false);
			}
		} else {
			showNoSignalView(true);
		}
	}

	private void startSourceChange(final int inputsource) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				K_TvCommonManager.getInstance().K_setInputSource(inputsource);
				if (inputsource == K_Constants.INPUT_SOURCE_ATV) {
					int curChannelNumber = K_ChannelModel.getInstance()
							.K_getCurrentChannelNumber();
					if (curChannelNumber > 0xFF) {
						curChannelNumber = 0;
					}
					K_ChannelModel.getInstance().K_setAtvChannel(
							curChannelNumber);
				} else if (inputsource == K_Constants.INPUT_SOURCE_DTV) {
					K_ChannelModel.getInstance().K_playDtvCurrentProgram();
				}
			}
		}).start();
	}

	/*
	 * FIXME: By Android Api Guide, getRunningTasks should not be used in our
	 * code's core section. It is only using for debugging. But we do not have a
	 * better method to determinant whether RootActivity is in top or not, So we
	 * used this method temporarily.
	 */
	private boolean isTopActivity(String className) {
		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		ComponentName cn = manager.getRunningTasks(1).get(0).topActivity;
		String topActivityName = cn.getClassName();
		return topActivityName.equals(className);
	}

	/**
	 * get back key status in root activity
	 * 
	 * @return boolean , true is back key pressed.
	 */
	public static boolean getBackKeyStatus() {
		return mIsBackKeyPressed;
	}

	/**
	 * get root activity status
	 * 
	 * @return boolean , true means root activity is active now.
	 */
	public static boolean getActiveStatus() {
		return mIsActive;
	}

	/**
	 * Add API when system is reboot.
	 * 
	 * @author gerard.jiang
	 * @serialData 2013/04/28
	 * @param flag
	 */
	public static void setRebootFlag(boolean flag) {
		isReboot = flag;
	}

	/**
	 * Show the exit dialog.
	 * 
	 * @author gerard.jiang
	 * @param aDialog
	 */
	private void showExitDialog() {
		// ***add by allen.sun 2013-5-27
		// Adaptation different resolutions in STB
		if (Tools.isBox()) {
			if(log)
			Log.i(TAG, "start com.mstar.pipservice");
			Intent pipIntent = new Intent("com.mstar.pipservice");
			pipIntent.putExtra("cmd", "invisible");
			RootActivity.this.startService(pipIntent);
		}
		// ***and end
		mExitDialog.setOwnerActivity(rootActivity);
		mExitDialog.show();
	}

	/**
	 * Open SourceInfoActivity after checking program lock. if input source is
	 * not tv, this function works as starting source info activity.
	 */
	private void startSourceInfo() {
		int curInput = K_TvCommonManager.getInstance().K_getCurrentTvInputSource();
			/*
			 * when RootActivity is not running, we don't start activity to
			 * interrupt other menu, so we send SIGNAL_LOCK action to source
			 * info for updating content if SourceInfoActivity is alive, its
			 * BoradcastReceiver will handle this event.
			 */
			if (false == isTopActivity(RootActivity.class.getName())) {
				sendBroadcast(new Intent(TvIntent.ACTION_SIGNAL_LOCK));
			} else {
				Intent intent = new Intent(TvIntent.ACTION_SOURCEINFO);
				startActivity(intent);
			}
	}

	private void displayOpRefreshconfirmation() {
		this.runOnUiThread(new Runnable() {
			public void run() {
				mCiPlusOPRefreshDialog.show();
			}
		});
	}

	private void showNoSignalView(boolean isShown) {
		if(log)
		Log.d(TAG, "show nosignal view :" + isShown);
		int curInputSource = mTvCommon.K_getCurrentTvInputSource();
		//nathan.liao 2014.10.14 add for pass word menu and nosigal menu overlay start
		if (isShown && !isHotelMenuLockFlag) {
				if(!isOnPauseFlag&&!disableNoSignal){
					if(curInputSource!=K_Constants.INPUT_SOURCE_ATV){
						if (TextView.INVISIBLE == mNoSignalView.getVisibility()) {
							mNoSignalView.setVisibility(TextView.VISIBLE);
							mRefreshTask = new MyTimerTask();
							mRefreshTimer.schedule(mRefreshTask, 1000, 500);
						}
					}
				}
			} else {
				if (TextView.VISIBLE == mNoSignalView.getVisibility()) {
					mNoSignalView.setVisibility(TextView.INVISIBLE);
					if(mRefreshTask != null)
					mRefreshTask.cancel();
				}
			}
		
	}

	private boolean sendCecKey(int keyCode) {
		int curInputSource = K_TvCommonManager.getInstance()
				.K_getCurrentTvInputSource();
		if (K_TvCecManager.getInstance().cecStatus == mCecStatusOn) {
			if (curInputSource == K_Constants.INPUT_SOURCE_HDMI
					|| curInputSource == K_Constants.INPUT_SOURCE_HDMI2
					|| curInputSource == K_Constants.INPUT_SOURCE_HDMI3
					|| curInputSource == K_Constants.INPUT_SOURCE_HDMI4) {
				if (K_TvCommonManager.getInstance().K_isHdmiSignalMode() == true) {
					if (mTvCecManager.K_sendCecKey(keyCode)) {
						if(log)
						Log.d(TAG, "send Cec key,keyCode is " + keyCode
								+ ", tv don't handl the key");
						return true;
					}
				}
			} else if (curInputSource == K_Constants.INPUT_SOURCE_DTV
					|| curInputSource == K_Constants.INPUT_SOURCE_ATV
					|| curInputSource == K_Constants.INPUT_SOURCE_CVBS
					|| curInputSource == K_Constants.INPUT_SOURCE_CVBS2
					|| curInputSource == K_Constants.INPUT_SOURCE_CVBS3
					|| curInputSource == K_Constants.INPUT_SOURCE_CVBS4
					|| curInputSource == K_Constants.INPUT_SOURCE_YPBPR
					|| curInputSource == K_Constants.INPUT_SOURCE_YPBPR2
					|| curInputSource == K_Constants.INPUT_SOURCE_YPBPR3) {
				if (keyCode == KeyEvent.KEYCODE_VOLUME_UP
						|| keyCode == KeyEvent.KEYCODE_VOLUME_DOWN
						|| keyCode == KeyEvent.KEYCODE_VOLUME_MUTE
						|| keyCode == KeyEvent.KEYCODE_DPAD_LEFT
						|| keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
					if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
						keyCode = KeyEvent.KEYCODE_VOLUME_DOWN;
					}else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
						keyCode = KeyEvent.KEYCODE_VOLUME_UP;
					}
					if (mTvCecManager.K_sendCecKey(keyCode)) {
						if(log)
						Log.d(TAG, "send Cec key,keyCode is " + keyCode
								+ ", tv don't handl the key");
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean sendGingaKey(int keyCode, KeyEvent event) {
		final boolean down = event.getAction() == KeyEvent.ACTION_DOWN;

		if (K_TvCommonManager.getInstance().K_getCurrentTvInputSource() == K_Constants.INPUT_SOURCE_DTV) {
			if (K_SettingModel.getInstance().K_isGingaRunning()) {
				if (down) {
					if (K_SettingModel.getInstance().K_processKey(keyCode, true)) {
						if(log)
						Log.i(TAG, "onKeyDown GingaStatusMode:processKey");
						return true;
					}
				} else {
					if (K_SettingModel.getInstance().K_processKey(keyCode, false)) {
						if(log)
						Log.i(TAG, "onKeyUp GingaStatusMode:processKey");
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean sendHbbTVKey(int keyCode) {
		if (K_TvCommonManager.getInstance().K_getCurrentTvSystem() <= K_Constants.TV_SYSTEM_DTMB
				&& K_TvCommonManager.getInstance().K_getCurrentTvInputSource() == K_Constants.INPUT_SOURCE_DTV) {
			if (K_SettingModel.getInstance().K_isHbbTVEnabled()) {
				if (K_SettingModel.getInstance().K_hbbtvKeyHandler(keyCode)) {
					if(log)
					Log.i(TAG, "onKeyDown HbbTV:sendHbbTVKey");
					return true;
				}
			}
		}
		return false;
	}

	private boolean sendMheg5Key(int keyCode) {
		boolean ret = false;
		if (K_TvManager.getInstance().K_isMheg5Running()) {
			ret = K_TvManager.getInstance().K_sendMheg5Key(keyCode);
		} else {
			if(log)
				Log.i(TAG, "isMheg5Running return fali!");
		}
		return ret;
	}

	private void updateScreenSaver() {

		/*
		 * FIXME: This function is called only when tv first boot up. FIXME:
		 * because unlock event maybe post before tvapk is ready. FIXME: so we
		 * use this function to draw nosignal when first boot up.
		 */
		Boolean isPowerOn = getIntent() != null ? getIntent().getBooleanExtra(
				"isPowerOn", false) : false;
		if (!isPowerOn) {
			if(log)
			Log.d(TAG,
					"only use updateScreenSaver to update NoSignalTextView when first boot up");
			return;
		}
		/**
		 * if apk is not exiting or need to update screen saver, send screen
		 * saver status or signal lock status to handler for updating screen.
		 */
		int curInputSource = mTvCommon.K_getCurrentTvInputSource();
		int curSubInputSource = mTvCommon.K_getCurrentSubInputSource()
				.ordinal();
		mIsSignalLock = mTvCommon.K_isSignalStable(curInputSource);

		if (!mIsSignalLock && !bCmd_TvApkExit) {
			boolean bSubPopSignalLock = false;
			if (K_SettingModel.getInstance() != null) {
				if (K_SettingModel.getInstance().K_isPipModeEnabled()) {
					if (K_SettingModel.getInstance().K_getPipMode() == K_Constants.E_PIP_MODE_POP) {
						if (mTvCommon.K_isSignalStable(curSubInputSource)) {
							bSubPopSignalLock = true;
						}
					}
				}
			}
			/**
			 * Send Signal Unlock to signalLock Handler if sub inputsource is
			 * signal stabled and inputsource is not changing now.
			 */
			if (!bSubPopSignalLock && !TVRootApp.getSourceDirty()) {
				Message msgLock = Message.obtain();
				msgLock.arg1 = TvEvent.SIGNAL_UNLOCK;
				signalLockHandler.sendMessage(msgLock);
			}
		}
		
		/**
		 * update screen saver status to screenSaverHandler if screen saver need
		 * to show and apk is not exiting.
		 */
		if (mIsScreenSaverShown && !bCmd_TvApkExit && !mIsBackKeyPressed) {
			Message msgSaver = Message.obtain();
			msgSaver.arg1 = mScreenSaverStatus;
			msgSaver.arg2 = curInputSource;
			if(log)
			Log.d(TAG, "update screen saver source :" + curInputSource
					+ " status :" + msgSaver.arg1);
			screenSaverHandler.sendMessage(msgSaver);
		}
	}

	private void openNotifyMessage(final String msg) {
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			public void run() {
				Toast.makeText(RootActivity.this, msg, Toast.LENGTH_LONG)
						.show();
			}
		});
	}
	//==================ktc add start for nosigal move===============
	 class MyTimerTask extends TimerTask {
			@Override
			public void run() {
				TEXT_WIDTH = mNoSignalView.getWidth();
				TEXT_HEIGHT = mNoSignalView.getHeight();
				posX = mNoSignalView.getLeft();
				posY = mNoSignalView.getTop();
				
				/* Log.d("nathanliao", "text width: " + TEXT_WIDTH + "; text height" +
				  TEXT_HEIGHT); Log.d("DEBUG", "posX: " + posX + "; posY" + posY);*/
				
				posY += Y_STEP;
				posX += X_STEP;
				if ((posX + TEXT_WIDTH) > SCREEN_WIDTH) {
					X_STEP = -2;
					posX -= 2;
				} else if (posX < 0) {
					X_STEP = 2;
					posX += 2;
				}

				if ((posY + TEXT_HEIGHT) > SCREEN_HEIGHT) {
					Y_STEP = -2;
					posY -= 2;
				} else if (posY < 0) {
					Y_STEP = 2;
					posY += 2;
				}
				mHandler.sendEmptyMessage(REFRESH_POS);
			}
		};
		//==================ktc add end for nosigal move=====================
		//ktc nathan.liao 20140905 for hotelmenu start
		
		private void checkInput() {
	    	boolean correctedRule = checkInputRule();
	    	if (correctedRule) {
	    		String password = getInputPassword();
	    		boolean correctedPassword = verifyPasswd(password);
	    		if (correctedPassword){
	    			rllyPassword.setVisibility(View.GONE);
					for (Button btn : mPasswdViews) {
						btn.setFocusable(false);
					}
	    			resetInput();
	    			mDataBaseUtil.updateDatabase_systemsetting("bIsBlocked", (short) 0);

		   	        K_TvManager.getInstance().K_setTvosCommonCommand("SetChannelLock");
	    			isHotelMenuLockFlag = isCurrentChannelLock();
	    		} else {
	    			resetInput();
	    			Toast.makeText(RootActivity.this,
	    					R.string.password_incorrect_hint, Toast.LENGTH_SHORT).show();
	    		}
	    	}
	    }

	    /**
	     * 检查是否输入完�?	     * @return
	     */
	    private boolean checkInputRule() {
	    	for (String passwd : mEnterPassword) {
	    		if (TextUtils.isEmpty(passwd))
	    			return false;
	    	}

	    	return true;
	    }

	    private String getInputPassword() {
	    	StringBuffer sb = new StringBuffer();
	    	for (String passwd : mEnterPassword) {
	    		sb.append(passwd);
	    	}

	    	return sb.toString();
	    }

	    private void resetInput() {
	    	for (int i = 0; i < mEnterPassword.length; i++) {
	    		mEnterPassword[i] = null;
	    		mPasswdViews[i].setText("");
	    	}

	    	mPasswdViews[0].requestFocus();
	    }

	    /**
	     * 验证密码
	     * @param password
	     * @return
	     */
	    private boolean verifyPasswd(String password) {
	    	String hotelPasswd = PropHelp.newInstance().getHotelChlockPasswd();
	    	return hotelPasswd.equals(password);
	    }
	    

		//ktc nathan.liao 20140905 for hotelmenu end

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			if (keyCode == KeyEvent.KEYCODE_DPAD_UP
					|| keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
				return true;
			}
			if ((keyCode == KeyEvent.KEYCODE_1
	    			|| keyCode == KeyEvent.KEYCODE_2
	    			|| keyCode == KeyEvent.KEYCODE_3
	    			|| keyCode == KeyEvent.KEYCODE_4
	    			|| keyCode == KeyEvent.KEYCODE_5
	    			|| keyCode == KeyEvent.KEYCODE_6
	    			|| keyCode == KeyEvent.KEYCODE_7
	    			|| keyCode == KeyEvent.KEYCODE_8
	    			|| keyCode == KeyEvent.KEYCODE_9
	    			|| keyCode == KeyEvent.KEYCODE_0)) {
				if (event.getAction() != KeyEvent.ACTION_DOWN)
					return true;

				String value = String.valueOf(keyCode - KeyEvent.KEYCODE_0);
				for (int i = 0; i < mPasswdIds.length; i++) {
					if (mPasswdIds[i] == v.getId()) {
						mPasswdViews[i].setText("*");
						mEnterPassword[i] = value;
						if (i < mPasswdIds.length - 1)
							mPasswdViews[i + 1].requestFocus();
						else
							checkInput();
						return true;
					}
				}
	    	} else if (event.getAction() == KeyEvent.ACTION_DOWN &&
	    			keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
	    		for (int i = 0; i < mPasswdIds.length; i++) {
					if (mPasswdIds[i] == v.getId()) {
						resetInput();
						return true;
					}
				}
	    	} else if (event.getAction() == KeyEvent.ACTION_DOWN &&
	    			keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
	    		for (int i = 0; i < mPasswdIds.length; i++) {
					if (mPasswdIds[i] == v.getId()) {
						resetInput();
						return true;
					}
				}
	    	}

			return false;
		}
		//nathan.liao 2014.10.14 add for pass word menu and nosigal menu overlay start
		private boolean isCurrentChannelLock()
		{
			boolean flag = false;
			int curInputSource = K_TvCommonManager.getInstance()
					.K_getCurrentTvInputSource();
			if(log)
			Log.v(TAG, "======nathan=====curInputSource="+curInputSource);
			if (curInputSource == K_Constants.INPUT_SOURCE_ATV
					|| curInputSource == K_Constants.INPUT_SOURCE_DTV)
			{
				if(!isReboot)
				{
				boolean hotelMode = mDataBaseUtil.getValueDatabase_systemsetting("Hotelmode") > 0 ? true : false;
				boolean isBlocked = mDataBaseUtil.getValueDatabase_systemsetting("bIsBlocked") > 0 ? true : false;
				K_ProgramInfo info = K_ChannelModel.getInstance().K_getCurrentProgramInfo();
					if(log)
					Log.v(TAG, "hotelMode="+hotelMode+"=====isBlocked="+isBlocked+"========info.isLock"+info.getProgram().isLock);
				if(hotelMode && isBlocked && info.getProgram().isLock)
				{
					flag = true;
				}
			}
			}
			return flag;
		}
		//nathan.liao 2014.10.14 add for pass word menu and nosigal menu overlay end
}
