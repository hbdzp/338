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

package com.mstar.tv.tvplayer.ui.holder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;
import android.os.Handler;
import android.os.Looper;

import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.TVRootApp;
import com.mstar.tv.tvplayer.ui.channel.ProgramListViewActivity;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tv.tvplayer.ui.tuning.ATVManualTuning;
import com.mstar.tv.tvplayer.ui.tuning.ChannelTuning;
import com.mstar.tv.tvplayer.ui.tuning.DTVManualTuning;
import com.mstar.util.PropHelp;
import com.mstar.util.Tools;
import com.kguan.mtvplay.tvapi.K_ChannelModel;
import com.kguan.mtvplay.tvapi.K_Constants;
import com.kguan.mtvplay.tvapi.K_TvCiManager;
import com.kguan.mtvplay.tvapi.K_TvCommonManager;
import com.mstar.tv.tvplayer.ui.AutoTuningInitDialog;
import android.os.AsyncTask;
@SuppressLint("NewApi")
public class ChannelViewHolder {
	
	private K_ChannelModel kChannelModel;
	
	private K_TvCommonManager kTvCommonModel;
	
	private K_TvCiManager kTvCiManager;
	
	private static final String TAG = "ChannelViewHolder";

	protected LinearLayout linear_cha_autotuning;

	protected LinearLayout linear_cha_dtvmanualtuning;

	protected LinearLayout linear_cha_atvmanualtuning;

	protected LinearLayout linear_cha_programedit;

	private final Activity mChannelActivity;

	private final Intent mIntent = new Intent();

	private int focusedid = 0x00000000;

	protected TextView tvChaAtvManualtuning;
	private AutoTuningInitDialog progressDialog = null;
	private setDtvConfigTask task = new setDtvConfigTask();
	public ChannelViewHolder(Activity activity) {
		this.mChannelActivity = activity;
		kChannelModel = K_ChannelModel.getInstance();
		kTvCommonModel = K_TvCommonManager.getInstance();
		kTvCiManager = K_TvCiManager.getInstance();
	}

	public void findViews() {
		linear_cha_autotuning = (LinearLayout) mChannelActivity
				.findViewById(R.id.linearlayout_cha_autotuning);
		linear_cha_dtvmanualtuning = (LinearLayout) mChannelActivity
				.findViewById(R.id.linearlayout_cha_dtvmanualtuning);
		linear_cha_atvmanualtuning = (LinearLayout) mChannelActivity
				.findViewById(R.id.linearlayout_cha_atvmanualtuning);
		if (Tools.isBox()) {
			// make sure ATV is unusable for box
			linear_cha_atvmanualtuning.setVisibility(View.GONE);
		}
		linear_cha_programedit = (LinearLayout) mChannelActivity
				.findViewById(R.id.linearlayout_cha_programedit);
		tvChaAtvManualtuning = (TextView) mChannelActivity
				.findViewById(R.id.textview_cha_atvmanualtuning);

		int currInputSource = kTvCommonModel.K_getCurrentTvInputSource();
		 if (PropHelp.newInstance().hasDtmb()) {
	            linear_cha_dtvmanualtuning.setVisibility(View.VISIBLE);
	            tvChaAtvManualtuning.setText(R.string.str_cha_atvmanualtuning);

	        } else {
	            linear_cha_dtvmanualtuning.setVisibility(View.GONE);
	            tvChaAtvManualtuning.setText(R.string.str_cha_manualtuning);

	        }
		if (currInputSource == K_Constants.INPUT_SOURCE_DTV) {
			enableSingleItemOrNot(linear_cha_atvmanualtuning, false);
			enableSingleItemOrNot(linear_cha_dtvmanualtuning, true);
		} else {
				enableSingleItemOrNot(linear_cha_dtvmanualtuning, false);
			enableSingleItemOrNot(linear_cha_atvmanualtuning, true);
		}
		setOnClickLisenters();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		int currentid = -1;
		if (mChannelActivity.getCurrentFocus() != null) {
			currentid = mChannelActivity.getCurrentFocus().getId();
		}
		if (focusedid != currentid) {
			MainMenuActivity.selectedstatusforChannel = 0x00000000;
		}
		switch (keyCode) {
		case KeyEvent.KEYCODE_TV_INPUT:
            switch (currentid) {
            case R.id.linearlayout_cha_autotuning:
            	// nathan.liao 2014.11.04
				task.execute();
				break;
			case R.id.linearlayout_cha_dtvmanualtuning:
					Log.d(TAG, "isOpMode: "
							+ kTvCiManager.K_isOpMode());
					if (kTvCiManager.K_isOpMode()) {
						Handler handler = new Handler(
								Looper.getMainLooper());
						handler.post(new Runnable() {
							public void run() {
								Toast.makeText(
										mChannelActivity,
										R.string.str_op_forbid_channel_tuning_content,
										Toast.LENGTH_SHORT).show();
							}
						});
						break;
					}
					mIntent.setClass(mChannelActivity, DTVManualTuning.class);
					mChannelActivity.startActivity(mIntent);
					mChannelActivity.finish();
				break;
			case R.id.linearlayout_cha_atvmanualtuning:
					Log.d(TAG, "isOpMode: "
							+ kTvCiManager.K_isOpMode());
					if (kTvCiManager.K_isOpMode()) {
						Handler handler = new Handler(
								Looper.getMainLooper());
						handler.post(new Runnable() {
							public void run() {
								Toast.makeText(
										mChannelActivity,
										R.string.str_op_forbid_channel_tuning_content,
										Toast.LENGTH_SHORT).show();
							}
						});
						break;
					}

						mIntent.setClass(mChannelActivity,
								ATVManualTuning.class);
					mChannelActivity.startActivity(mIntent);
					mChannelActivity.finish();
				break;
			case R.id.linearlayout_cha_programedit:
				mIntent.setClass(mChannelActivity,
						ProgramListViewActivity.class);
				mChannelActivity.startActivity(mIntent);
				mChannelActivity.finish();
				break;
			default:
				break;
				}
            break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			switch (currentid) {
			case R.id.linearlayout_cha_autotuning:
			case R.id.linearlayout_cha_dtvmanualtuning:
			case R.id.linearlayout_cha_atvmanualtuning:
			case R.id.linearlayout_cha_programedit:
				focusedid = currentid;
				break;
			default:
				break;
			}
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			switch (currentid) {
			case R.id.linearlayout_cha_autotuning:
			case R.id.linearlayout_cha_dtvmanualtuning:
			case R.id.linearlayout_cha_atvmanualtuning:
			case R.id.linearlayout_cha_programedit:
				focusedid = currentid;
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
		return true;
	}

	private void setOnClickLisenters() {
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View view) {
				int currentid = mChannelActivity.getCurrentFocus().getId();
				if (focusedid != currentid)
					MainMenuActivity.selectedstatusforChannel = 0x00000000;
				switch (currentid) {
				case R.id.linearlayout_cha_autotuning:
					// nathan.liao 2014.11.04
					task.execute();
					break;
				case R.id.linearlayout_cha_dtvmanualtuning:
						Log.d(TAG, "isOpMode: "
								+ kTvCiManager.K_isOpMode());
						if (kTvCiManager.K_isOpMode()) {
							Handler handler = new Handler(
									Looper.getMainLooper());
							handler.post(new Runnable() {
								public void run() {
									Toast.makeText(
											mChannelActivity,
											R.string.str_op_forbid_channel_tuning_content,
											Toast.LENGTH_SHORT).show();
								}
							});
							break;
						}

						mIntent.setAction(TvIntent.ACTION_DTV_MANUAL_TUNING);
						if (mIntent.resolveActivity(mChannelActivity
								.getPackageManager()) != null) {
							mChannelActivity.startActivity(mIntent);
							mChannelActivity.finish();
					}
					break;
				case R.id.linearlayout_cha_atvmanualtuning:
						Log.d(TAG, "isOpMode: "
								+ kTvCiManager.K_isOpMode());
						if (kTvCiManager.K_isOpMode()) {
							Handler handler = new Handler(
									Looper.getMainLooper());
							handler.post(new Runnable() {
								public void run() {
									Toast.makeText(
											mChannelActivity,
											R.string.str_op_forbid_channel_tuning_content,
											Toast.LENGTH_SHORT).show();
								}
							});
							break;
						}

							mIntent.setAction(TvIntent.ACTION_ATV_MANUAL_TUNING);
						if (mIntent.resolveActivity(mChannelActivity
								.getPackageManager()) != null) {
							mChannelActivity.startActivity(mIntent);
							mChannelActivity.finish();
						}
					break;
				case R.id.linearlayout_cha_programedit:
					mIntent.setClass(mChannelActivity,
							ProgramListViewActivity.class);
					mChannelActivity.startActivity(mIntent);
					mChannelActivity.finish();
					break;
				default:
					break;
				}
			}
		};
		linear_cha_autotuning.setOnClickListener(listener);
		linear_cha_dtvmanualtuning.setOnClickListener(listener);
		linear_cha_atvmanualtuning.setOnClickListener(listener);
		linear_cha_programedit.setOnClickListener(listener);
	}

	void enableSingleItemOrNot(LinearLayout linearLayout, boolean isEnable) {
		if (!isEnable) {
			((TextView) (linearLayout.getChildAt(0))).setTextColor(Color.GRAY);
			linearLayout.setEnabled(false);
			linearLayout.setFocusable(false);
		} else {
			((TextView) (linearLayout.getChildAt(0))).setTextColor(Color.WHITE);
			linearLayout.setEnabled(true);
			linearLayout.setFocusable(true);
		}
	}

	void enableCompositeItemOrNot(LinearLayout linearLayout, boolean isEnable,
			short Count) {
		if (!isEnable) {
			for (short i = 1; i <= Count; i++) {
				((TextView) (linearLayout.getChildAt(i)))
						.setTextColor(Color.GRAY);
			}
			linearLayout.setEnabled(false);
			linearLayout.setFocusable(false);
		} else {
			for (short i = 1; i <= Count; i++) {
				((TextView) (linearLayout.getChildAt(i)))
						.setTextColor(Color.WHITE);
			}
			linearLayout.setEnabled(true);
			linearLayout.setFocusable(true);
		}
	}

	//nathan.liao 2014.11.04 add for auto tuning ANR error start  
	class setDtvConfigTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
		    progressDialog = new AutoTuningInitDialog(mChannelActivity, R.style.dialog);
			progressDialog.show();		
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			if (PropHelp.newInstance().hasDtmb()) {
				if (TVRootApp.isSourceDirty) {
					TVRootApp.isSourceDirtyPre = true;
					TVRootApp.isSourceDirty = false;
				}
				kChannelModel.K_setUserScanType(K_Constants.TV_SCAN_ALL);
				int dtmbRouteIndex = kChannelModel.K_getSpecificDtvRouteIndex(K_Constants.TV_ROUTE_DTMB);
				kChannelModel.K_switchMSrvDtvRouteCmd(dtmbRouteIndex);
			}
			kChannelModel.K_setSystemCountryId(K_Constants.CHINA);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			Intent intent = new Intent();
			intent.setClass(mChannelActivity, ChannelTuning.class);
			mChannelActivity.startActivity(intent);
			mChannelActivity.finish();
			super.onPostExecute(result);
		}
	}
	//nathan.liao 2014.11.04 add for auto tuning ANR error end
}
