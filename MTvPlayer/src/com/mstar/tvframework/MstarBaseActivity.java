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

package com.mstar.tvframework;

import com.kguan.mtvplay.tvapi.K_ChannelModel;
import com.kguan.mtvplay.tvapi.K_Constants;
import com.kguan.mtvplay.tvapi.K_MKeyEvent;
import com.kguan.mtvplay.tvapi.K_TvCommonManager;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.SwitchPageHelper;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tv.tvplayer.ui.channel.ChannelControlActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

public class MstarBaseActivity extends Activity implements MstarBaseInterface {

	private int delayMillis = 5000;

	private int delayMessage = 88888888;

	private Intent intent;

	protected boolean alwaysTimeout = false;

	private boolean isGoingToBeClosed = true;

	private Handler timerHander = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == delayMessage) {
				onTimeOut();
			}
		}
	};

	protected void isGoingToBeClosed(boolean flag) {
		this.isGoingToBeClosed = flag;
	}

	@Override
	protected void onResume() {
		super.onResume();
		timerHander.sendEmptyMessageDelayed(delayMessage, delayMillis);
		isGoingToBeClosed = true;
	}

	@Override
	public void onUserInteraction() {
		super.onUserInteraction();
		if (timerHander.hasMessages(delayMessage)) {
			timerHander.removeMessages(delayMessage);
			timerHander.sendEmptyMessageDelayed(delayMessage, delayMillis);
		}
	}

	@Override
	protected void onPause() {
		timerHander.removeMessages(delayMessage);
		if (true == isGoingToBeClosed) {
			finish();
		}
		super.onPause();
	}

	@Override
	public void onTimeOut() {
		if (true == alwaysTimeout) {
			timerHander.sendEmptyMessageDelayed(delayMessage, delayMillis);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent arg1) {
		// TODO Auto-generated method stub
		if (SwitchPageHelper.goToProgrameListInfo(this, keyCode) == true) {
			finish();
			return true;
		}else if (onChannelChange(keyCode) == true) {
			finish();
			return true;
		}else if (SwitchPageHelper.goToSourceInfo(this, keyCode) == true) {
			finish();
			return true;
		} else if (SwitchPageHelper.goToEpgPage(this, keyCode) == true) {
			finish();
			return true;
		}else if (SwitchPageHelper.goToSubtitleLangPage(this, keyCode) == true) {
			finish();
			return true;
		} else if (SwitchPageHelper.goToAudioLangPage(this, keyCode) == true) {
			finish();
			return true;
		} else if (SwitchPageHelper.goToProgrameListInfo(this, keyCode) == true) {
			finish();
			return true;
		} else if (SwitchPageHelper.goToFavorateListInfo(this, keyCode) == true) {
			finish();
			return true;
		} else if (SwitchPageHelper.goToSleepMode(this, keyCode) == true) {
			finish();
			return true;
		} else if (SwitchPageHelper.goToTeletextPage(this, keyCode) == true) {
			finish();
			return true;
		} else if (onChannelChange(keyCode) == true) {
			return true;
		} else if (SwitchPageHelper.goTo3DMenuPage(this, keyCode) == true){
			finish();
			return true;
		}if (SwitchPageHelper.factoryControl(this, keyCode) == true) {
			return true;
		}
		switch (keyCode) {
		case KeyEvent.KEYCODE_MENU:
			intent = new Intent(this, MainMenuActivity.class);
			startActivity(intent);
			finish();
			return true;
		case K_MKeyEvent.KEYCODE_SOUND_MODE:
		case K_MKeyEvent.KEYCODE_PICTURE_MODE:
		case K_MKeyEvent.KEYCODE_SLEEP:
		case K_MKeyEvent.KEYCODE_ASPECT_RATIO:
			finish();
			break;
		default:
			break;
		}
		return super.onKeyDown(keyCode, arg1);
	}
	/**
	 * handle the up, down, return and 0-9 key
	 * 
	 * @param keyCode
	 * @return
	 */
	public boolean onChannelChange(int keyCode) {
	   K_ChannelModel mTvChannelManager = K_ChannelModel.getInstance();
		if (K_TvCommonManager.getInstance().K_getCurrentTvInputSource() == K_Constants.INPUT_SOURCE_ATV
				|| K_TvCommonManager.getInstance().K_getCurrentTvInputSource() == K_Constants.INPUT_SOURCE_DTV) {
			if (keyCode == KeyEvent.KEYCODE_CHANNEL_DOWN
					) {
				finish();
				mTvChannelManager.K_programDown();
				Intent intent = new Intent(TvIntent.ACTION_SOURCEINFO);
				intent.putExtra("info_key", true);
				this.startActivity(intent);
				return true;

			} else if (keyCode == KeyEvent.KEYCODE_CHANNEL_UP
					) {
				finish();
				mTvChannelManager.K_programUp();
				Intent intent = new Intent(TvIntent.ACTION_SOURCEINFO);
				intent.putExtra("info_key", true);
				this.startActivity(intent);
				return true;
			} else if (keyCode == K_MKeyEvent.KEYCODE_CHANNEL_RETURN) {
				finish();
				mTvChannelManager.K_returnToPreviousProgram();
				Intent intent = new Intent(TvIntent.ACTION_SOURCEINFO);
				intent.putExtra("info_key", true);
				this.startActivity(intent);
				return true;
			}
			if (K_TvCommonManager.getInstance().K_getCurrentTvInputSource() == K_Constants.INPUT_SOURCE_ATV
					|| K_TvCommonManager.getInstance().K_getCurrentTvInputSource() == K_Constants.INPUT_SOURCE_DTV) {
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
					finish();
					Intent intentChannelControl = new Intent(this,
							ChannelControlActivity.class);
					intentChannelControl.putExtra("KeyCode", keyCode);
					this.startActivity(intentChannelControl);
					return true;
				}
			}
		}
		return false;
	}
	public void onRemoveMessage()
	{
		timerHander.removeMessages(delayMessage);
	}
	public void onSendMessage()
	{
		timerHander.sendEmptyMessageDelayed(delayMessage, delayMillis);
	}
	public void setDelayMillis(int delayMillis) {
		this.delayMillis = delayMillis;
	}
}
