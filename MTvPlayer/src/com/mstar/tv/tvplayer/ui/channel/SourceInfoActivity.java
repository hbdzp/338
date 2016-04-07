//<MStar Software>
//******************************************************************************
// MStar Software
// Copyright (c) 2010 - 2012 MStar Semiconductor, Inc. All rights reserved.
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kguan.mtvplay.tvapi.K_AtvManager;
import com.kguan.mtvplay.tvapi.K_ChannelModel;
import com.kguan.mtvplay.tvapi.K_Constants;
import com.kguan.mtvplay.tvapi.K_MKeyEvent;
import com.kguan.mtvplay.tvapi.K_TvCecManager;
import com.kguan.mtvplay.tvapi.K_TvCommonManager;
import com.kguan.mtvplay.tvapi.K_TvEpgManager;
import com.kguan.mtvplay.tvapi.K_TvManager;
import com.kguan.mtvplay.tvapi.K_TvPictureManager;
import com.kguan.mtvplay.tvapi.enumeration.K_EnumScanType;
import com.kguan.mtvplay.tvapi.listener.K_OnAtvPlayerEventListener;
import com.kguan.mtvplay.tvapi.listener.K_OnDtvPlayerEventListener;
import com.kguan.mtvplay.tvapi.listener.K_OnTvPlayerEventListener;
import com.kguan.mtvplay.tvapi.vo.K_ProgramInfo;
import com.kguan.mtvplay.tvapi.vo.atv.K_AtvEventScan;
import com.kguan.mtvplay.tvapi.vo.common.K_CecSetting;
import com.kguan.mtvplay.tvapi.vo.common.K_EnumAvdVideoStandardType;
import com.kguan.mtvplay.tvapi.vo.common.K_HbbtvEventInfo;
import com.kguan.mtvplay.tvapi.vo.common.K_PresentFollowingEventInfo;
import com.kguan.mtvplay.tvapi.vo.common.K_ProgramInfoQueryCriteria;
import com.kguan.mtvplay.tvapi.vo.common.K_VideoInfo;
import com.kguan.mtvplay.tvapi.vo.dtv.K_DtvAudioInfo;
import com.kguan.mtvplay.tvapi.vo.dtv.K_DtvEitInfo;
import com.kguan.mtvplay.tvapi.vo.dtv.K_DtvEventScan;
import com.kguan.mtvplay.tvapi.vo.dtv.K_DtvSubtitleInfo;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.RootActivity;
import com.mstar.tv.tvplayer.ui.SwitchPageHelper;
import com.mstar.tv.tvplayer.ui.TVRootApp;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tvframework.MstarBaseActivity;
import com.mstar.util.PropHelp;
import com.mstar.util.TvEvent;

public class SourceInfoActivity extends MstarBaseActivity {

	private static final String TAG = "SourceInfoActivity";

	private static final int CMD_SET_CURRENT_TIME = 0x00;

	private static final int CMD_UPDATE_SOURCE_INFO = 0X01;

	private static final int CMD_SIGNAL_LOCK = 0x02;

	private final int MAX_TIMES = 10;

	private final int mCecStatusOn = 1;

	private int mCount = 0;

	private static final int MAX_VALUE_OF_ONE_DIGIT = 9;

	private static final int MAX_VALUE_OF_TWO_DIGIT = 99;

	private static final int MAX_VALUE_OF_THREE_DIGIT = 999;

	private K_ChannelModel mTvChannelManager = null;

	private int mInputSource = K_Constants.INPUT_SOURCE_NONE;

	private int mPreviousInputSource = K_Constants.INPUT_SOURCE_NONE;

	private K_VideoInfo mVideoInfo = null;

	private String mStr_video_info = null;

	private int mCurChannelNumber = 1;

	private ImageView mTvImageView = null;

	private TextView mTitle = null;

	private TextView mInfo = null;

	private ImageView mFirstTvNumberIcon = null;

	private ImageView mSecondTvNumberIcon = null;

	private ImageView mThirdTvNumberIcon = null;

	private TextView mSource_info_tvnumber;

	private TextView mSource_info_tvname;

	private TextView mSource_info_teletext;

	private TextView mSource_info_program_name;

	private TextView mSource_info_Subtitle;

	private TextView mSource_info_mheg5;

	private TextView mSource_info_video_format;

	private TextView mSource_info_audio_format;

	private TextView mSource_info_program_type;

	private TextView mSource_info_program_period;

	private TextView mSource_info_description;

	private TextView mSource_info_digital_TV;

	private TextView mSource_info_language;

	private TextView mSource_info_imageformat;

	private TextView mSource_info_soundformat;

	private TextView mSource_info_audioformat;

	private String mStr;

	private TextView mCurrentTime = null;

	private K_TvEpgManager mTvEpgManager = null;
	private K_TvCecManager mTvCecManager = null;

	protected TimerTask mTimerTask;

	// protected ST_Time curDateTime;
	public static boolean isDTVChannelNameReady = true;

	// private static boolean isATVProgramInfoReady = true;
	protected Timer mTimer;

	private Intent mIntent = null;

	private K_DtvEitInfo mDtveitinfo;

	private int[] mNumberResIds = { R.drawable.popup_img_number_0,
			R.drawable.popup_img_number_1, R.drawable.popup_img_number_2,
			R.drawable.popup_img_number_3, R.drawable.popup_img_number_4,
			R.drawable.popup_img_number_5, R.drawable.popup_img_number_6,
			R.drawable.popup_img_number_7, R.drawable.popup_img_number_8,
			R.drawable.popup_img_number_9 };
	private int[] mNumberResIds_aft = { R.drawable.aft_popup_img_number_0,
			R.drawable.aft_popup_img_number_1, R.drawable.aft_popup_img_number_2,
			R.drawable.aft_popup_img_number_3, R.drawable.aft_popup_img_number_4,
			R.drawable.aft_popup_img_number_5, R.drawable.aft_popup_img_number_6,
			R.drawable.aft_popup_img_number_7, R.drawable.aft_popup_img_number_8,
			R.drawable.aft_popup_img_number_9 };

	private String[] mAudioTypeDisplayString = { "MPEG", "AC3", "AAC", "AC3P" };

	private String[] mServiceTypeDisplayString = { "", "DTV", "RADIO", "DATA",
			"UNITED_TV", "INVALID" };

	private String[] mVideoTypeDisplayString = { "", "MPEG", "H.264", "AVS",
			"VC1" };

	private K_OnAtvPlayerEventListener mAtvPlayerEventListener = null;

	private K_OnDtvPlayerEventListener mDtvPlayerEventListener = null;

	private K_OnTvPlayerEventListener mTvPlayerEventListener = null;
	private String preTaskTag = "";
	private LinearLayout infoLinear = null;
	private boolean isStop=false;

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
			Log.i(TAG, "K_onEpgUpdateList()");
			SourceInfoActivity.this.runOnUiThread(new Runnable() {
				public void run() {
					if (K_TvCommonManager.getInstance().K_getCurrentTvInputSource() == K_Constants.INPUT_SOURCE_DTV) {
						updateSourceInFo();
					}
				}
			});
			return true;
		}

		@Override
		public boolean K_onHbbtvUiEvent(int arg0, K_HbbtvEventInfo arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onPopupDialog(int arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onPvrNotifyAlwaysTimeShiftProgramNotReady(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onPvrNotifyAlwaysTimeShiftProgramReady(int arg0) {
			// TODO Auto-generated method stub
			return false;
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
			// TODO Auto-generated method stub
			return false;
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
			// TODO Auto-generated method stub
			return false;
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
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onScreenSaverMode(int arg0, int arg1) {
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
		public boolean K_onTvProgramInfoReady(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}
/*		@Override
		public boolean onScreenSaverMode(int what, int arg1) {
			return false;
		}

		@Override
		public boolean onHbbtvUiEvent(int what, HbbtvEventInfo eventInfo) {
			return false;
		}

		@Override
		public boolean onPopupDialog(int what, int arg1, int arg2) {
			return false;
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
			return false;
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
			return false;
		}

		@Override
		public boolean onPvrNotifyUsbRemoved(int what, int arg1) {
			return false;
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
			return false;
		}

		@Override
		public boolean onPvrNotifyAlwaysTimeShiftProgramNotReady(int what) {
			return false;
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
			return false;
		}

		@Override
		public boolean onSignalUnLock(int what) {
			return false;
		}

		@Override
		public boolean onEpgUpdateList(int what, int arg1) {
			Log.i(TAG, "onEpgUpdateList()");
			SourceInfoActivity.this.runOnUiThread(new Runnable() {
				public void run() {
					if (K_TvCommonManager.getInstance().K_getCurrentTvInputSource() == K_Constants.INPUT_SOURCE_DTV) {
						updateSourceInFo();
					}
				}
			});
			return true;
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
		}*/
	}

	private class AtvPlayerEventListener extends K_OnAtvPlayerEventListener {

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
		public boolean K_onAtvProgramInfoReady(int arg0) {
			Bundle b = new Bundle();
			Message msg = myHandler.obtainMessage();
			msg.what = CMD_UPDATE_SOURCE_INFO;
			b.putInt("CmdIndex", TvEvent.ATV_PROGRAM_INFO_READY);
			myHandler.sendMessage(msg);
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
		@Override
		public boolean onAtvAutoTuningScanInfo(int what, AtvEventScan extra) {
			return false;
		}

		@Override
		public boolean onAtvManualTuningScanInfo(int what, AtvEventScan extra) {
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
		public boolean onAtvProgramInfoReady(int what) {
			Bundle b = new Bundle();
			Message msg = myHandler.obtainMessage();
			msg.what = CMD_UPDATE_SOURCE_INFO;
			b.putInt("CmdIndex", TvEvent.ATV_PROGRAM_INFO_READY);
			myHandler.sendMessage(msg);
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
			SourceInfoActivity.isDTVChannelNameReady = true;

			Bundle b = new Bundle();
			Message msg = myHandler.obtainMessage();
			msg.what = CMD_UPDATE_SOURCE_INFO;
			b.putInt("CmdIndex", TvEvent.DTV_CHANNELNAME_READY);
			myHandler.sendMessage(msg);
			return true;
		}

		@Override
		public boolean K_onDtvPriComponentMissing(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onDtvProgramInfoReady(int arg0) {
			Bundle b = new Bundle();
			Message msg = myHandler.obtainMessage();
			msg.what = CMD_UPDATE_SOURCE_INFO;
			b.putInt("CmdIndex", (int) (TvEvent.DTV_PROGRAM_INFO_READY));
			msg.setData(b);
			myHandler.sendMessage(msg);
			return true;
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

/*		@Override
		public boolean onDtvChannelNameReady(int what) {
			SourceInfoActivity.isDTVChannelNameReady = true;

			Bundle b = new Bundle();
			Message msg = myHandler.obtainMessage();
			msg.what = CMD_UPDATE_SOURCE_INFO;
			b.putInt("CmdIndex", TvEvent.DTV_CHANNELNAME_READY);
			myHandler.sendMessage(msg);
			return true;
		}

		@Override
		public boolean onDtvAutoTuningScanInfo(int what, DtvEventScan extra) {
			return false;
		}

		@Override
		public boolean onDtvProgramInfoReady(int what) {
			Bundle b = new Bundle();
			Message msg = myHandler.obtainMessage();
			msg.what = CMD_UPDATE_SOURCE_INFO;
			b.putInt("CmdIndex", (int) (TvEvent.DTV_PROGRAM_INFO_READY));
			msg.setData(b);
			myHandler.sendMessage(msg);
			return true;
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

	protected Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle bundle = msg.getData();
			switch (msg.what) {
			case CMD_SET_CURRENT_TIME:
				String time = new SimpleDateFormat("HH:mm yyyy/MM/dd E")
						.format(new Date());
				mCurrentTime.setText(getString(R.string.str_time_time) + " : "
						+ time);
				break;

			case CMD_UPDATE_SOURCE_INFO:
				int CmdIndex = bundle.getInt("CmdIndex");
				onMSrvMsgCmd(CmdIndex);
				break;

			case CMD_SIGNAL_LOCK:
				updateSourceInFo();
				break;

			default:
				break;
			}
		}
	};

	private void onMSrvMsgCmd(int index) {
		if (index == TvEvent.DTV_CHANNELNAME_READY) {
			updateChannelInfo(true);
		} else if (index == TvEvent.DTV_PROGRAM_INFO_READY) {
			updateSourceInFo();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent paraIntent = getIntent();
		if (paraIntent != null) {
			preTaskTag = paraIntent.getStringExtra("task_tag");
			
		}
		mTvEpgManager = K_TvEpgManager.getInstance();
		mTvCecManager = K_TvCecManager.getInstance();
		IntentFilter filter = new IntentFilter();
		filter.addAction(TvIntent.ACTION_SOURCE_INFO_DISAPPEAR);
		filter.addAction(TvIntent.ACTION_SIGNAL_LOCK);
		registerReceiver(mReceiver, filter);
		mTvChannelManager = K_ChannelModel.getInstance();
		checkInputSourceAndInitView();
		setInvisible();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
		// receive dtvlistener call back
		mCount = 0;

		mAtvPlayerEventListener = new AtvPlayerEventListener();
		K_ChannelModel.getInstance().K_registerOnAtvPlayerEventListener(
				mAtvPlayerEventListener);
		mDtvPlayerEventListener = new DtvPlayerEventListener();
		K_ChannelModel.getInstance().K_registerOnDtvPlayerEventListener(
				mDtvPlayerEventListener);
		mTvPlayerEventListener = new TvPlayerEventListener();
		K_ChannelModel.getInstance().K_registerOnTvPlayerEventListener(
				mTvPlayerEventListener);

		if (mInputSource == K_Constants.INPUT_SOURCE_ATV
				|| mInputSource == K_Constants.INPUT_SOURCE_DTV) {
			try {
				if (mTimer == null)
					mTimer = new Timer();
				if (mTimerTask == null)
					mTimerTask = getTimerTask();
				if (mTimer != null && mTimerTask != null)
					mTimer.schedule(mTimerTask, 10, 1000);
			} catch (Exception e) {
			}
		}
		if (mInputSource == K_Constants.INPUT_SOURCE_HDMI
				|| mInputSource == K_Constants.INPUT_SOURCE_HDMI2
				|| mInputSource == K_Constants.INPUT_SOURCE_HDMI3
				|| mInputSource == K_Constants.INPUT_SOURCE_HDMI4) {
			// this will call updateSourceInFo() delay.
			myHandler.sendEmptyMessageDelayed(CMD_SIGNAL_LOCK, 1000);
		} else {
				// this will call updateSourceInFo() delay.
				myHandler.sendEmptyMessageDelayed(CMD_SIGNAL_LOCK, 300);
		}
		if (mInputSource == K_Constants.INPUT_SOURCE_YPBPR) {
			try {
				if (mTimer == null)
					mTimer = new Timer();
				if (mTimerTask == null)
					mTimerTask = getTimerTask();
				if (mTimer != null && mTimerTask != null)
					mTimer.schedule(mTimerTask, 10, 1000);
			} catch (Exception e) {
			}
		}
		isStop=false;

	}

	@Override
	protected void onPause() {
		Log.d(TAG, "onPause");
		if (preTaskTag != null) {
			Log.d("SourceInFoActivity", preTaskTag);
			if ("input_source_changed".equals(preTaskTag)) {
				TVRootApp.isSourceDirty = true;
			}
		}
		K_ChannelModel.getInstance().K_registerOnAtvPlayerEventListener(
				mAtvPlayerEventListener);
		mAtvPlayerEventListener = null;
		K_ChannelModel.getInstance().K_registerOnDtvPlayerEventListener(
				mDtvPlayerEventListener);
		mDtvPlayerEventListener = null;
		K_ChannelModel.getInstance().K_registerOnTvPlayerEventListener(
				mTvPlayerEventListener);
		mTvPlayerEventListener = null;

		try {
			if (mTimerTask != null) {
				mTimerTask.cancel();
				mTimerTask = null;
			}
			mTimer.cancel();
			mTimer = null;
		} catch (Exception e) {
		}

		super.onPause();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}

	private void setInvisible() {
		if (mInputSource == K_Constants.INPUT_SOURCE_ATV
				|| mInputSource == K_Constants.INPUT_SOURCE_DTV) {
			mFirstTvNumberIcon.setVisibility(View.INVISIBLE);
			mSecondTvNumberIcon.setVisibility(View.INVISIBLE);
			mThirdTvNumberIcon.setVisibility(View.INVISIBLE);
			/*if (mInputSource == K_Constants.INPUT_SOURCE_DTV) {
				mFourthTvNumberIcon.setVisibility(View.INVISIBLE);
				mTvDotIcon.setVisibility(View.INVISIBLE);
				mDigitMinorTvNumberIcon1.setVisibility(View.INVISIBLE);
				mDigitMinorTvNumberIcon2.setVisibility(View.INVISIBLE);
				mDigitMinorTvNumberIcon3.setVisibility(View.INVISIBLE);
				mDigitMinorTvNumberIcon4.setVisibility(View.INVISIBLE);
			}*/

			mCurrentTime.setVisibility(View.INVISIBLE);
			mSource_info_tvnumber.setVisibility(View.INVISIBLE);
			mSource_info_tvname.setVisibility(View.INVISIBLE);
		}
	}

	private void checkInputSourceAndInitView() {
		mPreviousInputSource = mInputSource;
		mInputSource = K_TvCommonManager.getInstance().K_getCurrentTvInputSource();

		if (mInputSource == mPreviousInputSource) {
			return;
		}

		if (mInputSource == K_Constants.INPUT_SOURCE_ATV) {
			setContentView(R.layout.source_info_atv);
			mTvImageView = (ImageView) findViewById(R.id.source_info_imageView);
			mTitle = (TextView) findViewById(R.id.source_info_title);
			mFirstTvNumberIcon = (ImageView) findViewById(R.id.source_info_image1);
			mSecondTvNumberIcon = (ImageView) findViewById(R.id.source_info_image2);
			mThirdTvNumberIcon = (ImageView) findViewById(R.id.source_info_image3);
			mCurrentTime = (TextView) findViewById(R.id.source_info_tvtime);
			mSource_info_tvnumber = (TextView) findViewById(R.id.source_info_tvnumber);
			mSource_info_tvname = (TextView) findViewById(R.id.source_info_tvname);
			mSource_info_imageformat = (TextView) findViewById(R.id.source_info_imageformat);
			mSource_info_soundformat = (TextView) findViewById(R.id.source_info_soundformat);
			mSource_info_audioformat = (TextView) findViewById(R.id.source_info_audioformat);
			mTitle.setText("ATV");
		} else if (mInputSource == K_Constants.INPUT_SOURCE_DTV) {
			setContentView(R.layout.source_info_dtv);
			((TextView) findViewById(R.id.source_info_description_title))
					.setText(getString(R.string.str_dtv_source_info_description)
							+ " : ");
			mTvImageView = (ImageView) findViewById(R.id.source_info_imageView);
			mTitle = (TextView) findViewById(R.id.source_info_title);
			mFirstTvNumberIcon = (ImageView) findViewById(R.id.source_info_image1);
			mSecondTvNumberIcon = (ImageView) findViewById(R.id.source_info_image2);
			mThirdTvNumberIcon = (ImageView) findViewById(R.id.source_info_image3);
			/*mFourthTvNumberIcon = (ImageView) findViewById(R.id.source_info_image4);
			mTvDotIcon = (ImageView) findViewById(R.id.source_info_image_dot);
			mDigitMinorTvNumberIcon1 = (ImageView) findViewById(R.id.source_info_image_minor_1);
			mDigitMinorTvNumberIcon2 = (ImageView) findViewById(R.id.source_info_image_minor_2);
			mDigitMinorTvNumberIcon3 = (ImageView) findViewById(R.id.source_info_image_minor_3);
			mDigitMinorTvNumberIcon4 = (ImageView) findViewById(R.id.source_info_image_minor_4);*/
			mCurrentTime = (TextView) findViewById(R.id.source_info_tvtime);
			mSource_info_tvnumber = (TextView) findViewById(R.id.source_info_tvnumber);
			mSource_info_tvname = (TextView) findViewById(R.id.source_info_tvname);
			mSource_info_teletext = (TextView) findViewById(R.id.source_info_teletext);
			mSource_info_program_name = (TextView) findViewById(R.id.source_info_program_name);
			mSource_info_Subtitle = (TextView) findViewById(R.id.source_info_Subtitle);
			mSource_info_mheg5 = (TextView) findViewById(R.id.source_info_mheg5);
			mSource_info_video_format = (TextView) findViewById(R.id.source_info_video_format);
			mSource_info_audio_format = (TextView) findViewById(R.id.source_info_audio_format);
			mSource_info_program_type = (TextView) findViewById(R.id.source_info_program_type);
			mSource_info_program_period = (TextView) findViewById(R.id.source_info_program_period);
			mSource_info_description = (TextView) findViewById(R.id.source_info_description);
			mSource_info_digital_TV = (TextView) findViewById(R.id.source_info_digital_TV);
			mSource_info_language = (TextView) findViewById(R.id.source_info_language);
			mTitle.setText("DTV");
		} else {
			setContentView(R.layout.source_info);
			infoLinear = (LinearLayout)findViewById(R.id.source_info_up_bg);
			infoLinear.setVisibility(View.INVISIBLE);
			mInfo = (TextView) findViewById(R.id.source_info_textview);
			mTvImageView = (ImageView) findViewById(R.id.source_info_imageView);
			mTitle = (TextView) findViewById(R.id.source_info_title);
		}
	}

	private void clearTvPartialSourceInfo() {
		if (mInputSource == K_Constants.INPUT_SOURCE_ATV) {
			mSource_info_imageformat.setText("");
			mSource_info_soundformat.setText("");
			mSource_info_audioformat.setText("");
		} else if (mInputSource == K_Constants.INPUT_SOURCE_DTV) {
			mSource_info_teletext.setText("");
			mSource_info_program_name.setText("");
			mSource_info_Subtitle.setText("");
			mSource_info_mheg5.setText("");
			mSource_info_video_format.setText("");
			mSource_info_audio_format.setText("");
			mSource_info_program_type.setText("");
			mSource_info_program_period.setText("");
			mSource_info_description.setText("");
			mSource_info_digital_TV.setText("");
			mSource_info_language.setText("");
		}
	}

	private String getCurProgrameName() {
		int pgNum = mTvChannelManager.K_getCurrentChannelNumber();
		int st = K_Constants.SERVICE_TYPE_ATV;
			if (mInputSource == K_Constants.INPUT_SOURCE_DTV) {
				st = K_Constants.SERVICE_TYPE_DTV;
			}
		String pgName = mTvChannelManager.K_getProgramName(pgNum, st, 0x00);
		return pgName;
	}

	private String getCurProgrameName(short num) {
		int pgNum = mTvChannelManager.K_getCurrentChannelNumber();
		int st = -1;
		switch ((int) num) {
		case 0:
			st = K_Constants.SERVICE_TYPE_ATV;
			break;
		case 3:
			st = K_Constants.SERVICE_TYPE_DATA;
			break;
		case 1:
			st = K_Constants.SERVICE_TYPE_DTV;
			break;
		case 5:
			st = K_Constants.SERVICE_TYPE_INVALID;
			break;
		case 2:
			st = K_Constants.SERVICE_TYPE_RADIO;
			break;
		case 4:
			st = K_Constants.SERVICE_TYPE_UNITED_TV;
			break;
		}
		String pgName = mTvChannelManager.K_setProgramName(pgNum, st, 0x00);
		return pgName;
	}

	private void updateChannelInfo(boolean bPresent) {
		int videostandard = K_Constants.AVD_VIDEO_STANDARD_PAL_BGHI;
		checkInputSourceAndInitView();
		clearTvPartialSourceInfo();
		mCurChannelNumber = mTvChannelManager.K_getCurrentChannelNumber();

		if (mInputSource == K_Constants.INPUT_SOURCE_DTV) {
			//K_ProgramInfo CurrProg_Info = K_ProgramInfo.getInstance();
			K_ProgramInfo CurrProg_Info = new K_ProgramInfo();
			K_ProgramInfoQueryCriteria cr = new K_ProgramInfoQueryCriteria();
					CurrProg_Info = K_ChannelModel
							.getInstance()
							.K_getProgramInfo(
									cr,
									K_Constants.PROGRAM_INFO_TYPE_CURRENT);
				

				if (CurrProg_Info != null) {
					mCurChannelNumber = CurrProg_Info.getProgram().number;
						mSource_info_tvname
								.setText(getString(R.string.str_textview_record_chaneel_name)
										+ " : "
										+ getCurProgrameName((CurrProg_Info.getProgram().serviceType)));
						mSource_info_tvnumber
								.setText(getString(R.string.str_cha_dtvmanualtuning_channelno)
										+ " : " + mCurChannelNumber);
						numberToIcon(mCurChannelNumber);
					
				} else {
					Log.v(TAG, "CurrProg_Info is NULL!!");
					return;
				}

				Log.d("Jason","===================== ");
					K_PresentFollowingEventInfo pfEvtInfo = mTvEpgManager.K_getEpgPresentFollowingEventInfo(
							CurrProg_Info.getProgram().serviceType, CurrProg_Info.getProgram().number,
							bPresent, K_Constants.EPG_DETAIL_DESCRIPTION);
					if (pfEvtInfo.getPresentFollowingEventInfo() == null) {
						return;
					}
					mDtveitinfo = mTvEpgManager.K_getEitInfo(bPresent);
			Log.d("Jason","===================== ");

				if (mDtveitinfo.getDtvEitInfo() != null) {
					mSource_info_program_name
							.setText(mDtveitinfo.getDtvEitInfo().eitCurrentEventPf.eventName);

					/*mSource_info_age
							.setText(getString(R.string.str_dtv_source_info_age)
									+ ":"
									+ mDtveitinfo.eitCurrentEventPf.parentalControl);*/
				}
				K_DtvSubtitleInfo subtitleInfo = mTvChannelManager
						.K_getSubtitleInfo();
				mSource_info_Subtitle
						.setText(getString(R.string.str_dtv_source_info_Subtitle)
								+ " : " + subtitleInfo.getDtvSubtitleInfo().subtitleServiceNumber);

				if (mTvChannelManager.K_isTtxChannel()) {
					mSource_info_teletext
							.setText(getString(R.string.str_dtv_source_info_teletext));
				} else {
					mStr = "";
					mSource_info_teletext.setText(mStr);
				}
				if (pfEvtInfo.getPresentFollowingEventInfo() != null){
					if (pfEvtInfo.getPresentFollowingEventInfo().componentInfo.mheg5Service) {
							mStr = getString(R.string.str_dtv_source_info_mheg5);		
					} else {
						mStr = "";
					}
				}
				mSource_info_mheg5.setText(mStr);
				Log.d("Jason","<<pfEvtInfo == null>> = " + (pfEvtInfo == null));
				Log.d("Jason","<<pfEvtInfo.getPresentFollowingEventInfo() == null>> = " + (pfEvtInfo.getPresentFollowingEventInfo() == null));
				updateVideoType(pfEvtInfo.getPresentFollowingEventInfo().componentInfo.getVideoType()
						.ordinal());

				updateAudioInfo(pfEvtInfo.getPresentFollowingEventInfo().componentInfo.isAd);

				updateServiceType(CurrProg_Info);

				mSource_info_program_period
						.setText(getString(R.string.str_dtv_source_info_program_period)
								+ " : "
								+ (pfEvtInfo.getPresentFollowingEventInfo().eventInfo.durationTime / 60)
								+ "Min");

				if (mDtveitinfo.getDtvEitInfo() != null) {
					// show short description
					if (mDtveitinfo.getDtvEitInfo().eitCurrentEventPf.shortEventText.length() > 0) {
						mStr = mDtveitinfo.getDtvEitInfo().eitCurrentEventPf.shortEventText;
					} else if (mDtveitinfo.getDtvEitInfo().eitCurrentEventPf.extendedEventText
							.length() > 0) {
						mStr = mDtveitinfo.getDtvEitInfo().eitCurrentEventPf.extendedEventText;
					} else if (mDtveitinfo.getDtvEitInfo().eitCurrentEventPf.extendedEventItem
							.length() > 0) {
						mStr = mDtveitinfo.getDtvEitInfo().eitCurrentEventPf.extendedEventItem;
					} else {
						mStr = "";
					}
					mSource_info_description.setText(mStr);

					mSource_info_digital_TV
							.setText(getString(R.string.str_dtv_source_info_resolution)
									+ " : " + mStr_video_info);

					mSource_info_description.requestFocus();
				}
		} else if (mInputSource == K_Constants.INPUT_SOURCE_ATV) {
			K_ProgramInfo pinfo = mTvChannelManager.K_getCurrentProgramInfo();
			int dispCh = mCurChannelNumber;
			if (pinfo.getProgram() == null) {
				return;
			}
			mCurChannelNumber = pinfo.getProgram().number;
				mSource_info_tvname
						.setText(getString(R.string.str_textview_record_chaneel_name)
								+ " : " + getCurProgrameName());
				dispCh = mCurChannelNumber + 1;
			mSource_info_tvnumber
					.setText(getString(R.string.str_cha_dtvmanualtuning_channelno)
							+ " : " + dispCh);
			//ktc nathan.liao 2014.09.03 add for channel aft and skip channel start 
			int IsNeedAft = 1;
			int IsSkippedSign = 0;
				IsNeedAft =	K_AtvManager.getInstance().K_getAtvProgramInfo(K_Constants.E_IS_AFT_NEED, pinfo.getProgram().number);
				IsSkippedSign = K_AtvManager.getInstance().K_getAtvProgramInfo(K_Constants.E_IS_PROGRAM_SKIPPED, pinfo.getProgram().number);

			if((0 == IsNeedAft)||(1 == IsSkippedSign))
				numberToIcon_aft(dispCh);
			else
				numberToIcon(dispCh);
			//ktc nathan.liao 2014.09.03 add for channel aft and skip channel start 

			videostandard = mTvChannelManager.K_getAvdVideoStandard();

			// get video standard
			switch (videostandard) {
			case K_Constants.AVD_VIDEO_STANDARD_PAL_BGHI:
			case K_Constants.AVD_VIDEO_STANDARD_PAL_M:
			case K_Constants.AVD_VIDEO_STANDARD_PAL_N:
			case K_Constants.AVD_VIDEO_STANDARD_PAL_60:
				mStr = "PAL";
				break;
			case K_Constants.AVD_VIDEO_STANDARD_NTSC_44:
			case K_Constants.AVD_VIDEO_STANDARD_NTSC_M:
				mStr = "NTSC";
				break;
			case K_Constants.AVD_VIDEO_STANDARD_SECAM:
				mStr = "SECAM";
				break;
			default:
				mStr = "";
			}
			mSource_info_imageformat
					.setText(getString(R.string.str_atv_source_imageformat)
							+ " : " + mStr);
			mStr = getATVSoundFormat();
			mSource_info_soundformat
					.setText(getString(R.string.str_atv_source_soundformat)
							+ " : " + mStr);
			switch (mTvChannelManager.K_getAtvAudioStandard()) {
			case K_Constants.ATV_AUDIO_STANDARD_BG:
				mStr = "BG";
				break;
			case K_Constants.ATV_AUDIO_STANDARD_DK:
				mStr = "DK";
				break;
			case K_Constants.ATV_AUDIO_STANDARD_I:
				mStr = " I";
				break;
			case K_Constants.ATV_AUDIO_STANDARD_L:
				mStr = " L";
				break;
			case K_Constants.ATV_AUDIO_STANDARD_M:
				mStr = " M ";
				break;
			default:
				mStr = "BG";
			}
			mSource_info_audioformat
					.setText(getString(R.string.str_atv_source_audioformat)
							+ " : " + mStr);

		}
	}

	private void updateAudioInfo(boolean isAD) {
		K_DtvAudioInfo audioInfo = mTvChannelManager.K_getAudioInfo();
		if (audioInfo.getDtvAudioInfo().audioInfos.length > 0) {
			if (audioInfo.getDtvAudioInfo().audioInfos[audioInfo.getDtvAudioInfo().currentAudioIndex] != null) {
				mStr = "";
				if (audioInfo.getDtvAudioInfo().audioInfos[audioInfo.getDtvAudioInfo().currentAudioIndex].audioType < mAudioTypeDisplayString.length) {
					mStr = mAudioTypeDisplayString[audioInfo.getDtvAudioInfo().audioInfos[audioInfo.getDtvAudioInfo().currentAudioIndex].audioType];
				}

				if (isAD) {
					mStr += "  AD";
				}

				mSource_info_audio_format
						.setText(getString(R.string.str_dtv_source_info_audio_format)
								+ " : " + mStr);
			}
		}
	}


	private void updateServiceType(K_ProgramInfo pi) {
		mStr = "";
		if (pi.getProgram().serviceType < mServiceTypeDisplayString.length) {
			mStr = mServiceTypeDisplayString[pi.getProgram().serviceType];
		}

		mSource_info_program_type
				.setText(getString(R.string.str_dtv_source_info_program_type)
						+ " : " + mStr);
	}

	private void updateVideoType(int type) {
		mStr = "";
		if (type < mVideoTypeDisplayString.length) {
			mStr = mVideoTypeDisplayString[type];
		}
		mSource_info_video_format
				.setText(getString(R.string.str_dtv_source_info_program_format)
						+ " : " + mStr);
	}

	private String getATVSoundFormat() {
		String mStr = "";
		switch (K_TvCommonManager.getInstance().K_getATVMtsMode()) {
		case K_Constants.ATV_AUDIOMODE_MONO:
			mStr = getResources().getString(R.string.str_sound_format_mono);
			break;
		case K_Constants.ATV_AUDIOMODE_DUAL_A:
			mStr = getResources().getString(R.string.str_sound_format_dual_a);
			break;
		case K_Constants.ATV_AUDIOMODE_DUAL_AB:
			mStr = getResources().getString(R.string.str_sound_format_dual_ab);
			break;
		case K_Constants.ATV_AUDIOMODE_DUAL_B:
			mStr = getResources().getString(R.string.str_sound_format_dual_b);
			break;
		case K_Constants.ATV_AUDIOMODE_FORCED_MONO:
			mStr = getResources().getString(
					R.string.str_sound_format_forced_mono);
			break;
		case K_Constants.ATV_AUDIOMODE_G_STEREO:
			mStr = getResources().getString(R.string.str_sound_format_g_stereo);
			break;
		case K_Constants.ATV_AUDIOMODE_HIDEV_MONO:
			mStr = getResources().getString(
					R.string.str_sound_format_hidev_mono);
			break;
		case K_Constants.ATV_AUDIOMODE_K_STEREO:
			mStr = getResources().getString(R.string.str_sound_format_k_stereo);
			break;
		case K_Constants.ATV_AUDIOMODE_LEFT_LEFT:
			mStr = getResources()
					.getString(R.string.str_sound_format_left_left);
			break;
		case K_Constants.ATV_AUDIOMODE_LEFT_RIGHT:
			mStr = getResources().getString(
					R.string.str_sound_format_left_right);
			break;
		case K_Constants.ATV_AUDIOMODE_MONO_SAP:
			mStr = getResources().getString(R.string.str_sound_format_mono_sap);
			break;
		case K_Constants.ATV_AUDIOMODE_NICAM_DUAL_A:
			mStr = getResources().getString(
					R.string.str_sound_format_nicam_dual_a);
			break;
		case K_Constants.ATV_AUDIOMODE_NICAM_DUAL_AB:
			mStr = getResources().getString(
					R.string.str_sound_format_nicam_dual_ab);
			break;
		case K_Constants.ATV_AUDIOMODE_NICAM_DUAL_B:
			mStr = getResources().getString(
					R.string.str_sound_format_nicam_dual_b);
			break;
		case K_Constants.ATV_AUDIOMODE_NICAM_MONO:
			mStr = getResources().getString(
					R.string.str_sound_format_nicam_mono);
			break;
		case K_Constants.ATV_AUDIOMODE_NICAM_STEREO:
			mStr = getResources().getString(
					R.string.str_sound_format_nicam_stereo);
			break;
		case K_Constants.ATV_AUDIOMODE_RIGHT_RIGHT:
			mStr = getResources().getString(
					R.string.str_sound_format_right_right);
			break;
		case K_Constants.ATV_AUDIOMODE_STEREO_SAP:
			mStr = getResources().getString(
					R.string.str_sound_format_stereo_sap);
			break;
		case K_Constants.ATV_AUDIOMODE_INVALID:
		default:
			mStr = getResources().getString(R.string.str_sound_format_unknown);
			break;
		}
		return mStr;
	}

	/**
	 * Select source icon according to the input source
	 */
	private void selectIconByInputSource() {
		selectVideoInfo();
		switch (mInputSource) {
		case K_Constants.INPUT_SOURCE_VGA:
			setSourceInfo(R.drawable.grid_menu_pc,
					getResources().getString(R.string.str_sourceinfo_VGA));
			break;
		case K_Constants.INPUT_SOURCE_CVBS:
			//nathan.liao 2015.03.03 add for T4C1 board start
			if(PropHelp.newInstance().isHasT4C1Board())
				setSourceInfo(R.drawable.grid_menu_avi,
						getResources().getString(R.string.str_sourceinfo_CVBS));
			else
			//nathan.liao 2015.03.03 add for T4C1 board end
				setSourceInfo(R.drawable.grid_menu_avi,
						getResources().getString(R.string.str_sourceinfo_CVBS1));
			break;
		case K_Constants.INPUT_SOURCE_CVBS2:
			setSourceInfo(R.drawable.grid_menu_avi,
					getResources().getString(R.string.str_sourceinfo_CVBS2));
			break;
		case K_Constants.INPUT_SOURCE_CVBS3:
			setSourceInfo(R.drawable.grid_menu_avi, "CVBS3");
			break;
		case K_Constants.INPUT_SOURCE_CVBS4:
			setSourceInfo(R.drawable.grid_menu_avi, "CVBS4");
			break;
		case K_Constants.INPUT_SOURCE_CVBS5:
			setSourceInfo(R.drawable.grid_menu_avi, "CVBS5");
			break;
		case K_Constants.INPUT_SOURCE_CVBS6:
			setSourceInfo(R.drawable.grid_menu_avi, "CVBS6");
			break;
		case K_Constants.INPUT_SOURCE_CVBS7:
			setSourceInfo(R.drawable.grid_menu_avi, "CVBS7");
			break;
		case K_Constants.INPUT_SOURCE_CVBS8:
			setSourceInfo(R.drawable.grid_menu_avi, "CVBS8");
			break;
		case K_Constants.INPUT_SOURCE_SVIDEO:
			setSourceInfo(R.drawable.grid_menu_sv, "SVIDEO");
			break;
		case K_Constants.INPUT_SOURCE_SVIDEO2:
			setSourceInfo(R.drawable.grid_menu_sv, "SVIDEO2");
			break;
		case K_Constants.INPUT_SOURCE_SVIDEO3:
			setSourceInfo(R.drawable.grid_menu_sv, "SVIDEO3");
			break;
		case K_Constants.INPUT_SOURCE_SVIDEO4:
			setSourceInfo(R.drawable.grid_menu_sv, "SVIDEO4");
			break;
		case K_Constants.INPUT_SOURCE_YPBPR:
			setSourceInfo(R.drawable.grid_menu_yuv,
					getResources().getString(R.string.str_sourceinfo_YPBPR));
			break;
		case K_Constants.INPUT_SOURCE_YPBPR2:
			setSourceInfo(R.drawable.grid_menu_yuv, "YPBPR2");
			break;
		case K_Constants.INPUT_SOURCE_YPBPR3:
			setSourceInfo(R.drawable.grid_menu_yuv, "YPBPR3");
			break;
		case K_Constants.INPUT_SOURCE_SCART:
			setSourceInfo(R.drawable.grid_menu_scart, "SCART");
			break;
		case K_Constants.INPUT_SOURCE_SCART2:
			setSourceInfo(R.drawable.grid_menu_scart, "SCART2");
			break;
		case K_Constants.INPUT_SOURCE_HDMI:
			if (K_TvCommonManager.getInstance().K_isHdmiSignalMode() == false)
				setSourceInfo(R.drawable.grid_menu_hdmi, "HDMI1");
			else {

				if (K_TvCommonManager.getInstance().K_isHdmiSignalMode() == true) {
					setSourceInfo(R.drawable.grid_menu_hdmi, "HDMI1");
				} else {
					setSourceInfo(R.drawable.grid_menu_pc, "DVI");
				}
			}
			break;
		case K_Constants.INPUT_SOURCE_HDMI2:
			if (K_TvCommonManager.getInstance().K_isHdmiSignalMode() == false)
				setSourceInfo(R.drawable.grid_menu_hdmi, "HDMI2");
			else {
				if (K_TvCommonManager.getInstance().K_isHdmiSignalMode() == true) {
					setSourceInfo(R.drawable.grid_menu_hdmi, "HDMI2");
				} else {
					setSourceInfo(R.drawable.grid_menu_pc, "DVI2");
				}
			}
			break;
		case K_Constants.INPUT_SOURCE_HDMI3:
			if (K_TvCommonManager.getInstance().K_isHdmiSignalMode() == false)
				setSourceInfo(R.drawable.grid_menu_hdmi, "HDMI3");
			else {
				if (K_TvCommonManager.getInstance().K_isHdmiSignalMode() == true) {
					setSourceInfo(R.drawable.grid_menu_hdmi, "HDMI3");
				} else {
					setSourceInfo(R.drawable.grid_menu_pc, "DVI3");
				}
			}
			break;
		case K_Constants.INPUT_SOURCE_HDMI4:
			if (K_TvCommonManager.getInstance().K_isHdmiSignalMode() == true) {
				setSourceInfo(R.drawable.grid_menu_hdmi, "HDMI4");
			} else {
				setSourceInfo(R.drawable.grid_menu_pc, "DVI4");
			}
			break;
		case K_Constants.INPUT_SOURCE_ATV:
		case K_Constants.INPUT_SOURCE_DTV:
			updateChannelInfo(true);
			break;
		case K_Constants.INPUT_SOURCE_DVI:
			setSourceInfo(R.drawable.grid_menu_pc, "DVI");
			break;
		case K_Constants.INPUT_SOURCE_DVI2:
			setSourceInfo(R.drawable.grid_menu_pc, "DVI2");
			break;
		case K_Constants.INPUT_SOURCE_DVI3:
			setSourceInfo(R.drawable.grid_menu_pc, "DVI3");
			break;
		case K_Constants.INPUT_SOURCE_DVI4:
			setSourceInfo(R.drawable.grid_menu_pc, "DVI4");
			break;
		default:
			break;
		}
	}

	private void setSourceInfo(int resId, String strtitle) {
		mTvImageView.setImageResource(resId);
		mTitle.setText(strtitle);
		mInfo.setText(mStr_video_info);
	}

	private void selectVideoInfo() {
		if (mVideoInfo.getVideoInfo().vResolution != 0) {
			int s16FrameRateShow = (mVideoInfo.getVideoInfo().frameRate + 5) / 10;
			int scanType = K_Constants.E_PROGRESSIVE.ordinal();
		
				scanType = mVideoInfo.K_getScanType();
			switch (mInputSource) {
			case K_Constants.INPUT_SOURCE_ATV:
			case K_Constants.INPUT_SOURCE_DTV:
				if (scanType == K_EnumScanType.K_E_PROGRESSIVE.ordinal()) {
					mStr_video_info = mVideoInfo.getVideoInfo().vResolution + "P";
				} else {
					mStr_video_info = mVideoInfo.getVideoInfo().vResolution + "I";
				}
				break;
			case K_Constants.INPUT_SOURCE_VGA:
			case K_Constants.INPUT_SOURCE_DVI:
			case K_Constants.INPUT_SOURCE_DVI2:
			case K_Constants.INPUT_SOURCE_DVI3:
			case K_Constants.INPUT_SOURCE_DVI4:
				mStr_video_info = mVideoInfo.getVideoInfo().hResolution + "X"
						+ mVideoInfo.getVideoInfo().vResolution + "@" + s16FrameRateShow
						+ "Hz";
				break;
			case K_Constants.INPUT_SOURCE_HDMI:
			case K_Constants.INPUT_SOURCE_HDMI2:
			case K_Constants.INPUT_SOURCE_HDMI3:
			case K_Constants.INPUT_SOURCE_HDMI4:
				if (K_TvCommonManager.getInstance().K_isHdmiSignalMode() == true) {
					if (mVideoInfo.getVideoInfo().vResolution == 1080
							|| mVideoInfo.getVideoInfo().vResolution == 720
							|| mVideoInfo.getVideoInfo().vResolution == 480
							|| mVideoInfo.getVideoInfo().vResolution == 576) {
						if (scanType == K_EnumScanType.K_E_PROGRESSIVE.ordinal()) {
							mStr_video_info = mVideoInfo.getVideoInfo().vResolution + "P";
						} else {
							mStr_video_info = mVideoInfo.getVideoInfo().vResolution + "I";
						}
						mStr_video_info += "@" + s16FrameRateShow + "Hz";
					}else{
						mStr_video_info = mVideoInfo.getVideoInfo().hResolution + "X"
								+ mVideoInfo.getVideoInfo().vResolution + "@" + s16FrameRateShow
								+ "Hz";
					}
				} else {
					mStr_video_info = mVideoInfo.getVideoInfo().hResolution + "X"
							+ mVideoInfo.getVideoInfo().vResolution + "@" + s16FrameRateShow
							+ "Hz";
				}
				break;
			case K_Constants.INPUT_SOURCE_YPBPR:
			case K_Constants.INPUT_SOURCE_YPBPR2:
			case K_Constants.INPUT_SOURCE_YPBPR3:
				if (scanType == K_EnumScanType.K_E_PROGRESSIVE.ordinal()) {
					mStr_video_info = mVideoInfo.getVideoInfo().vResolution + "P";
				} else {
					mStr_video_info = mVideoInfo.getVideoInfo().vResolution + "I";
				}
				mStr_video_info += "@" + s16FrameRateShow + "Hz";
				break;
			case K_Constants.INPUT_SOURCE_CVBS:
			case K_Constants.INPUT_SOURCE_CVBS2:
			case K_Constants.INPUT_SOURCE_CVBS3:
				K_EnumAvdVideoStandardType videoStandard = null;
				videoStandard = K_TvManager.getInstance().K_getVideoStandard();
				if(videoStandard != null)
				{
					if( videoStandard.getEnumAvdVideoStandardType() == K_Constants.SECAM)
                    {
						mStr_video_info = "SECAM";
                    }else if(videoStandard.getEnumAvdVideoStandardType() == K_Constants.PAL_60 || videoStandard.getEnumAvdVideoStandardType() == K_Constants.PAL_BGHI
                                    ||videoStandard.getEnumAvdVideoStandardType() == K_Constants.PAL_M || videoStandard.getEnumAvdVideoStandardType() == K_Constants.PAL_N)
                    {
                    	mStr_video_info = "PAL";
                    }else if(videoStandard.getEnumAvdVideoStandardType() == K_Constants.NTSC_44 || videoStandard.getEnumAvdVideoStandardType() == K_Constants.NTSC_M)
                    {
                    	mStr_video_info = "NTSC";
                    }else
                    {
                    	mStr_video_info = "AUTO";
                    }
				}
				break;
			default:
				if (scanType == K_EnumScanType.K_E_PROGRESSIVE.ordinal()) {
					mStr_video_info = mVideoInfo.getVideoInfo().vResolution + "P";
				} else {
					mStr_video_info = mVideoInfo.getVideoInfo().vResolution + "I";
				}
				break;
			}
		}
		if (K_ChannelModel.getInstance().K_isSignalStabled() == false)
			mStr_video_info = "";
		if (mStr_video_info == "X") {
			mStr_video_info = "";
		}
	}

	public ArrayList<String> numberToPicture(int num) {
		ArrayList<String> strArray = new ArrayList<String>();
		String mStr = num + "";
		for (int i = 0; i < mStr.length(); i++) {
			char ch = mStr.charAt(i);
			strArray.add("" + ch);
		}
		return strArray;
	}
	//ktc nathan.liao 2014.09.03 add for channel aft and skip channel start 
	public ArrayList<String> numberToPicture_aft(int num) {
		ArrayList<String> strArray = new ArrayList<String>();
		String str = num + "";
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			strArray.add("" + ch);
		}
		return strArray;
	}
	public void numberToIcon_aft(int number) {
		ArrayList<Integer> n = getResoulseID_aft(numberToPicture_aft(number));
		if (number <= MAX_VALUE_OF_ONE_DIGIT) {
			mFirstTvNumberIcon.setImageResource(n.get(0));
			mSecondTvNumberIcon.setVisibility(View.GONE);
			mThirdTvNumberIcon.setVisibility(View.GONE);
			/*if (mInputSource == K_Constants.INPUT_SOURCE_DTV) {
				mFourthTvNumberIcon.setVisibility(View.GONE);
			}*/
		} else if ((MAX_VALUE_OF_ONE_DIGIT < number)
				&& (number <= MAX_VALUE_OF_TWO_DIGIT)) {
			mFirstTvNumberIcon.setImageResource(n.get(0));
			mSecondTvNumberIcon.setImageResource(n.get(1));
			mSecondTvNumberIcon.setVisibility(View.VISIBLE);
			mThirdTvNumberIcon.setVisibility(View.GONE);
			/*if (mInputSource == K_Constants.INPUT_SOURCE_DTV) {
				mFourthTvNumberIcon.setVisibility(View.GONE);
			}*/
		} else if ((MAX_VALUE_OF_TWO_DIGIT < number)
				&& (number <= MAX_VALUE_OF_THREE_DIGIT)) {
			mFirstTvNumberIcon.setImageResource(n.get(0));
			mSecondTvNumberIcon.setImageResource(n.get(1));
			mThirdTvNumberIcon.setImageResource(n.get(2));
			mSecondTvNumberIcon.setVisibility(View.VISIBLE);
			mThirdTvNumberIcon.setVisibility(View.VISIBLE);
			/*if (mInputSource == K_Constants.INPUT_SOURCE_DTV) {
				mFourthTvNumberIcon.setVisibility(View.GONE);
			}*/
		} else {
			mFirstTvNumberIcon.setImageResource(n.get(0));
			mSecondTvNumberIcon.setImageResource(n.get(1));
			mThirdTvNumberIcon.setImageResource(n.get(2));
			//mFourthTvNumberIcon.setImageResource(n.get(3));
			mSecondTvNumberIcon.setVisibility(View.VISIBLE);
			mThirdTvNumberIcon.setVisibility(View.VISIBLE);
			/*if (mInputSource == K_Constants.INPUT_SOURCE_DTV) {
				mFourthTvNumberIcon.setVisibility(View.VISIBLE);
			}*/
		}
		/*if (mInputSource == K_Constants.INPUT_SOURCE_DTV) {
			mTvDotIcon.setVisibility(View.GONE);
			mDigitMinorTvNumberIcon1.setVisibility(View.GONE);
			mDigitMinorTvNumberIcon2.setVisibility(View.GONE);
			mDigitMinorTvNumberIcon3.setVisibility(View.GONE);
			mDigitMinorTvNumberIcon4.setVisibility(View.GONE);
		}*/
	}
	
	public ArrayList<Integer> getResoulseID_aft(ArrayList<String> mStr) {
		ArrayList<Integer> n = new ArrayList<Integer>();
		for (String string : mStr) {
			Integer resId = mNumberResIds_aft[Integer.parseInt(string)];
			n.add(resId);
		}
		return n;
	}
	//ktc nathan.liao 2014.09.03 add for channel aft and skip channel end
	public ArrayList<Integer> getResoulseID(ArrayList<String> mStr) {
		ArrayList<Integer> n = new ArrayList<Integer>();
		for (String string : mStr) {
			Integer resId = mNumberResIds[Integer.parseInt(string)];
			n.add(resId);
		}
		return n;
	}

	public void numberToIcon(int number) {
		ArrayList<Integer> n = getResoulseID(numberToPicture(number));
		if (number <= MAX_VALUE_OF_ONE_DIGIT) {
			mFirstTvNumberIcon.setImageResource(n.get(0));
			mSecondTvNumberIcon.setVisibility(View.GONE);
			mThirdTvNumberIcon.setVisibility(View.GONE);
			/*if (mInputSource == K_Constants.INPUT_SOURCE_DTV) {
				mFourthTvNumberIcon.setVisibility(View.GONE);
			}*/
		} else if ((MAX_VALUE_OF_ONE_DIGIT < number)
				&& (number <= MAX_VALUE_OF_TWO_DIGIT)) {
			mFirstTvNumberIcon.setImageResource(n.get(0));
			mSecondTvNumberIcon.setImageResource(n.get(1));
			mSecondTvNumberIcon.setVisibility(View.VISIBLE);
			mThirdTvNumberIcon.setVisibility(View.GONE);
			/*if (mInputSource == K_Constants.INPUT_SOURCE_DTV) {
				mFourthTvNumberIcon.setVisibility(View.GONE);
			}*/
		} else if ((MAX_VALUE_OF_TWO_DIGIT < number)
				&& (number <= MAX_VALUE_OF_THREE_DIGIT)) {
			mFirstTvNumberIcon.setImageResource(n.get(0));
			mSecondTvNumberIcon.setImageResource(n.get(1));
			mThirdTvNumberIcon.setImageResource(n.get(2));
			mSecondTvNumberIcon.setVisibility(View.VISIBLE);
			mThirdTvNumberIcon.setVisibility(View.VISIBLE);
			/*if (mInputSource == K_Constants.INPUT_SOURCE_DTV) {
				mFourthTvNumberIcon.setVisibility(View.GONE);
			}*/
		} else {
			mFirstTvNumberIcon.setImageResource(n.get(0));
			mSecondTvNumberIcon.setImageResource(n.get(1));
			mThirdTvNumberIcon.setImageResource(n.get(2));
			//mFourthTvNumberIcon.setImageResource(n.get(3));
			mSecondTvNumberIcon.setVisibility(View.VISIBLE);
			mThirdTvNumberIcon.setVisibility(View.VISIBLE);
			/*if (mInputSource == K_Constants.INPUT_SOURCE_DTV) {
				mFourthTvNumberIcon.setVisibility(View.VISIBLE);
			}*/
		}
		/*if (mInputSource == K_Constants.INPUT_SOURCE_DTV) {
			mTvDotIcon.setVisibility(View.GONE);
			mDigitMinorTvNumberIcon1.setVisibility(View.GONE);
			mDigitMinorTvNumberIcon2.setVisibility(View.GONE);
			mDigitMinorTvNumberIcon3.setVisibility(View.GONE);
			mDigitMinorTvNumberIcon4.setVisibility(View.GONE);
		}*/
	}

	public void majorMinorToIcon(int majorNum, int minorNum) {
		ArrayList<Integer> n = getResoulseID(numberToPicture(majorNum));
		if (majorNum <= MAX_VALUE_OF_ONE_DIGIT) {
			mFirstTvNumberIcon.setImageResource(n.get(0));
			mSecondTvNumberIcon.setVisibility(View.GONE);
			mThirdTvNumberIcon.setVisibility(View.GONE);
			/*if (mInputSource == K_Constants.INPUT_SOURCE_DTV) {
				mFourthTvNumberIcon.setVisibility(View.GONE);
			}*/
		} else if ((MAX_VALUE_OF_ONE_DIGIT < majorNum)
				&& (majorNum <= MAX_VALUE_OF_TWO_DIGIT)) {
			mFirstTvNumberIcon.setImageResource(n.get(0));
			mSecondTvNumberIcon.setImageResource(n.get(1));
			mSecondTvNumberIcon.setVisibility(View.VISIBLE);
			mThirdTvNumberIcon.setVisibility(View.GONE);
			/*if (mInputSource == K_Constants.INPUT_SOURCE_DTV) {
				mFourthTvNumberIcon.setVisibility(View.GONE);
			}*/
		} else if ((MAX_VALUE_OF_TWO_DIGIT < majorNum)
				&& (majorNum <= MAX_VALUE_OF_THREE_DIGIT)) {
			mFirstTvNumberIcon.setImageResource(n.get(0));
			mSecondTvNumberIcon.setImageResource(n.get(1));
			mThirdTvNumberIcon.setImageResource(n.get(2));
			mSecondTvNumberIcon.setVisibility(View.VISIBLE);
			mThirdTvNumberIcon.setVisibility(View.VISIBLE);
			/*if (mInputSource == K_Constants.INPUT_SOURCE_DTV) {
				mFourthTvNumberIcon.setVisibility(View.GONE);
			}*/
		} else {
			mFirstTvNumberIcon.setImageResource(n.get(0));
			mSecondTvNumberIcon.setImageResource(n.get(1));
			mThirdTvNumberIcon.setImageResource(n.get(2));
			//mFourthTvNumberIcon.setImageResource(n.get(3));
			mSecondTvNumberIcon.setVisibility(View.VISIBLE);
			mThirdTvNumberIcon.setVisibility(View.VISIBLE);
			/*if (mInputSource == K_Constants.INPUT_SOURCE_DTV) {
				mFourthTvNumberIcon.setVisibility(View.VISIBLE);
			}*/
		}

		if (minorNum == K_Constants.ONEPARTCHANNEL_MINOR_NUM) {
			/*
			 * For ONE-PART channel, the minor number is defined as 0xFFFF. In a
			 * case: MajorNum = 15, MinorNum = 65535(0xFFFF), channel number
			 * will be displayed as "15.0" in channellist menu. User inputs
			 * "15.0" to tune to the channel. It needs to convert "0" to
			 * "0xFFFF" before doing channel switching.
			 */
			minorNum = 0;
		}
		n = getResoulseID(numberToPicture(minorNum));
		/*if ((mTvDotIcon != null) && (mDigitMinorTvNumberIcon1 != null)
				&& (mDigitMinorTvNumberIcon2 != null)
				&& (mDigitMinorTvNumberIcon3 != null)
				&& (mDigitMinorTvNumberIcon4 != null)) {
			if (minorNum <= MAX_VALUE_OF_ONE_DIGIT) {
				mTvDotIcon.setVisibility(View.VISIBLE);
				mDigitMinorTvNumberIcon1.setImageResource(n.get(0));
				mDigitMinorTvNumberIcon1.setVisibility(View.VISIBLE);
				mDigitMinorTvNumberIcon2.setVisibility(View.GONE);
				mDigitMinorTvNumberIcon3.setVisibility(View.GONE);
				mDigitMinorTvNumberIcon4.setVisibility(View.GONE);
			} else if ((MAX_VALUE_OF_ONE_DIGIT < minorNum)
					&& (minorNum <= MAX_VALUE_OF_TWO_DIGIT)) {
				mTvDotIcon.setVisibility(View.VISIBLE);
				mDigitMinorTvNumberIcon1.setImageResource(n.get(0));
				mDigitMinorTvNumberIcon1.setVisibility(View.VISIBLE);
				mDigitMinorTvNumberIcon2.setImageResource(n.get(1));
				mDigitMinorTvNumberIcon2.setVisibility(View.VISIBLE);
				mDigitMinorTvNumberIcon3.setVisibility(View.GONE);
				mDigitMinorTvNumberIcon4.setVisibility(View.GONE);
			} else if ((MAX_VALUE_OF_TWO_DIGIT < minorNum)
					&& (minorNum <= MAX_VALUE_OF_THREE_DIGIT)) {
				mTvDotIcon.setVisibility(View.VISIBLE);
				mDigitMinorTvNumberIcon1.setImageResource(n.get(0));
				mDigitMinorTvNumberIcon1.setVisibility(View.VISIBLE);
				mDigitMinorTvNumberIcon2.setImageResource(n.get(1));
				mDigitMinorTvNumberIcon2.setVisibility(View.VISIBLE);
				mDigitMinorTvNumberIcon3.setImageResource(n.get(2));
				mDigitMinorTvNumberIcon3.setVisibility(View.VISIBLE);
				mDigitMinorTvNumberIcon4.setVisibility(View.GONE);
			} else {
				mTvDotIcon.setVisibility(View.VISIBLE);
				mDigitMinorTvNumberIcon1.setImageResource(n.get(0));
				mDigitMinorTvNumberIcon1.setVisibility(View.VISIBLE);
				mDigitMinorTvNumberIcon2.setImageResource(n.get(1));
				mDigitMinorTvNumberIcon2.setVisibility(View.VISIBLE);
				mDigitMinorTvNumberIcon3.setImageResource(n.get(2));
				mDigitMinorTvNumberIcon3.setVisibility(View.VISIBLE);
				mDigitMinorTvNumberIcon4.setImageResource(n.get(3));
				mDigitMinorTvNumberIcon4.setVisibility(View.VISIBLE);
			}
		}*/
	}

	private TimerTask getTimerTask() {
		mTimerTask = new TimerTask() {
			@Override
			public void run() {
				if (mInputSource == K_Constants.INPUT_SOURCE_ATV
						|| (mInputSource == K_Constants.INPUT_SOURCE_DTV)) {
					myHandler.sendEmptyMessage(CMD_SET_CURRENT_TIME);
				}
				mCount++;
				if (mCount < MAX_TIMES && mCount % 2 == 0// the period is 2s
						&& mInputSource == K_Constants.INPUT_SOURCE_YPBPR) {
					myHandler.sendEmptyMessage(CMD_SIGNAL_LOCK);
				}
			}
		};
		return mTimerTask;
	}

	public void updateSourceInFo() {
		super.onRemoveMessage();
		checkInputSourceAndInitView();
		clearTvPartialSourceInfo();
		if (mInputSource == K_Constants.INPUT_SOURCE_ATV
				|| mInputSource == K_Constants.INPUT_SOURCE_DTV) {
			mFirstTvNumberIcon.setVisibility(View.VISIBLE);
			mSecondTvNumberIcon.setVisibility(View.VISIBLE);
			mThirdTvNumberIcon.setVisibility(View.VISIBLE);
			if (mInputSource == K_Constants.INPUT_SOURCE_DTV)
			mCurrentTime.setVisibility(View.VISIBLE);
			else
				mCurrentTime.setVisibility(View.INVISIBLE);
			mSource_info_tvnumber.setVisibility(View.VISIBLE);
			mSource_info_tvname.setVisibility(View.VISIBLE);
		} else
		{
			infoLinear.setVisibility(View.VISIBLE);
		}
		mVideoInfo = K_TvPictureManager.getInstance().K_getVideoInfo();
		selectIconByInputSource();
		super.onSendMessage();
	}

	public ArrayList<K_ProgramInfo> getAllProgramList() {
		K_ProgramInfo pgi = null;
		int indexBase = 0;
		ArrayList<K_ProgramInfo> progInfoList = new ArrayList<K_ProgramInfo>();
		int currInputSource = K_TvCommonManager.getInstance()
				.K_getCurrentTvInputSource();
		int m_nServiceNum = 0;
		if (currInputSource == K_Constants.INPUT_SOURCE_ATV) {
			indexBase = mTvChannelManager
					.K_getProgramCount(K_Constants.PROGRAM_COUNT_DTV);
			if (indexBase == 0xFFFFFFFF) {
				indexBase = 0;
			}
			m_nServiceNum = mTvChannelManager
					.K_getProgramCount(K_Constants.PROGRAM_COUNT_ATV_DTV);

		} else {
			indexBase = 0;
			m_nServiceNum = mTvChannelManager
					.K_getProgramCount(K_Constants.PROGRAM_COUNT_DTV);
		}

		for (int k = indexBase; k < m_nServiceNum; k++) {
				pgi = mTvChannelManager.K_getProgramInfoByIndex(k);
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
		K_ProgramInfo currentProgInfo = mTvChannelManager.K_getCurrentProgramInfo();
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
		K_ProgramInfo currentProgInfo = mTvChannelManager.K_getCurrentProgramInfo();
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		mCount = 0;
		K_CecSetting setting = mTvCecManager.K_getCecConfiguration();
		switch (mInputSource) {
		case K_Constants.INPUT_SOURCE_HDMI:
		case K_Constants.INPUT_SOURCE_HDMI2:
		case K_Constants.INPUT_SOURCE_HDMI3:
		case K_Constants.INPUT_SOURCE_HDMI4: {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				break;
			}
			if ((setting.getCecSetting().cecStatus == mCecStatusOn)
					&& (K_TvCommonManager.getInstance().K_isHdmiSignalMode() == true)) {
				if (mTvCecManager.K_sendCecKey(keyCode)) {
					Log.d(TAG, "onKeyDown return TRUE");
					return true;
				}
			}
			break;
		}
		case K_Constants.INPUT_SOURCE_CVBS:
		case K_Constants.INPUT_SOURCE_CVBS2:
		case K_Constants.INPUT_SOURCE_CVBS3:
		case K_Constants.INPUT_SOURCE_CVBS4:
		case K_Constants.INPUT_SOURCE_YPBPR:
		case K_Constants.INPUT_SOURCE_YPBPR2:
		case K_Constants.INPUT_SOURCE_YPBPR3:
		case K_Constants.INPUT_SOURCE_DTV:
		case K_Constants.INPUT_SOURCE_ATV: {
			if (keyCode == KeyEvent.KEYCODE_VOLUME_UP
					|| keyCode == KeyEvent.KEYCODE_VOLUME_DOWN
					|| keyCode == KeyEvent.KEYCODE_VOLUME_MUTE) {
				if (setting.getCecSetting().cecStatus == mCecStatusOn) {
					if (mTvCecManager.K_sendCecKey(keyCode)) {
						Log.d(TAG, "onKeyDown return TRUE");
						return true;
					}
				}
			}
			break;
		}
		default:
			break;
		}

		if (SwitchPageHelper.goToMenuPage(this, keyCode) == true) {
			finish();
			return true;
		} else if (SwitchPageHelper.goToEpgPage(this, keyCode) == true) {
			finish();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN
				|| keyCode == KeyEvent.KEYCODE_CHANNEL_DOWN) {
			if (mInputSource == K_Constants.INPUT_SOURCE_ATV) {
					mTvChannelManager.K_programDown();
					updateChannelInfo(true);
					return true;

			} else if (mInputSource == K_Constants.INPUT_SOURCE_DTV) {
				if (isDTVChannelNameReady == true) {
					isDTVChannelNameReady = false;
					mTvChannelManager.K_programDown();
					startSourceInfo();
					return true;
				} else {
					return true;
				}
			} else {
				return true;
			}
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_UP
				|| keyCode == KeyEvent.KEYCODE_CHANNEL_UP) {

			if (mInputSource == K_Constants.INPUT_SOURCE_ATV) {
					mTvChannelManager.K_programUp();
					updateChannelInfo(true);
					return true;

			} else if (mInputSource == K_Constants.INPUT_SOURCE_DTV) {
				if (isDTVChannelNameReady == true) {
					isDTVChannelNameReady = false;
					mTvChannelManager.K_programUp();
					startSourceInfo();
					return true;
				} else {
					return true;
				}
			} else {
				return true;
			}
		} else if (keyCode == K_MKeyEvent.KEYCODE_CHANNEL_RETURN) {
			if (mInputSource == K_Constants.INPUT_SOURCE_ATV
					|| mInputSource == K_Constants.INPUT_SOURCE_DTV) {
				mTvChannelManager.K_returnToPreviousProgram();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				updateChannelInfo(true);
				return true;
			} else {
				return true;
			}
		} else if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_MENU) {
			finish();
			return true;
		} else if (keyCode >= KeyEvent.KEYCODE_1
				&& keyCode <= KeyEvent.KEYCODE_9) {
			if (mInputSource == K_Constants.INPUT_SOURCE_ATV
					|| mInputSource == K_Constants.INPUT_SOURCE_DTV) {
			Intent intentChannelControl = new Intent(this,
					ChannelControlActivity.class);
			intentChannelControl.putExtra("KeyCode", keyCode);
			this.startActivity(intentChannelControl);
			finish();
			return true;
			}
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			String deviceName = InputDevice.getDevice(event.getDeviceId())
					.getName();
			if (deviceName.equals("MStar Smart TV IR Receiver")
					|| deviceName.equals("MStar Smart TV Keypad")) {
				updateChannelInfo(false);
			} else {
				Log.d(TAG, "deviceName is:" + deviceName);
			}
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			String deviceName = InputDevice.getDevice(event.getDeviceId())
					.getName();
			if (deviceName.equals("MStar Smart TV IR Receiver")
					|| deviceName.equals("MStar Smart TV Keypad")) {
				updateChannelInfo(true);
			} else {
				Log.d(TAG, "deviceName is:" + deviceName);
			}
			return true;
		}else if (keyCode == KeyEvent.KEYCODE_KTC_ATV
				|| keyCode == KeyEvent.KEYCODE_KTC_AV
				|| keyCode == KeyEvent.KEYCODE_KTC_YPBPR
				|| keyCode == KeyEvent.KEYCODE_KTC_HDMI
				|| keyCode == KeyEvent.KEYCODE_KTC_VGA
				|| keyCode == KeyEvent.KEYCODE_KTC_USB) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		isStop=true;
	}

	/**
	 * Invoked by MstarBaseActivity in which send the 10s delay.
	 */
	@Override
	public void onTimeOut() {
		super.onTimeOut();
		if(!isStop){
		mIntent = new Intent(this, RootActivity.class);
		startActivity(mIntent);
		}
		finish();
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(TvIntent.ACTION_SIGNAL_LOCK)) {
				myHandler.sendEmptyMessageDelayed(CMD_SIGNAL_LOCK, 1000);// this
			} else if (intent.getAction().equals(
					TvIntent.ACTION_SOURCE_INFO_DISAPPEAR)) {
				finish();
			}
		}
	};

	/**
	 * Open SourceInfoActivity Added by gerard.jiang for "0396073" in 2013/05/18
	 */
	private void startSourceInfo() {
		boolean isCurrentProgramLocked = mTvChannelManager
				.K_getCurrentProgramInfo().getProgram().isLock;
		if (isCurrentProgramLocked) {
			Intent intent = new Intent(this, SourceInfoActivity.class);
			intent.putExtra("info_key", true);
			startActivity(intent);
		}
	}
}
