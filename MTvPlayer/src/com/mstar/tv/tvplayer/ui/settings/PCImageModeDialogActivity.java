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

package com.mstar.tv.tvplayer.ui.settings;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mstar.tv.tvplayer.ui.R;
import com.kguan.mtvplay.tvapi.K_TvPictureManager;
import com.mstar.tv.tvplayer.ui.LittleDownTimer;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.RootActivity;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tv.tvplayer.ui.component.MyButton;
import com.mstar.tv.tvplayer.ui.component.SeekBarButton;
import com.mstar.tvframework.MstarBaseActivity;

public class PCImageModeDialogActivity extends MstarBaseActivity {
	private SeekBarButton seekBtnClock;

	private SeekBarButton seekBtnPhase;

	private SeekBarButton seekBtnHorizontalPos;

	private SeekBarButton seekBtnVerticalPos;

	private short clock = 0;

	private short phase = 0;

	private short horizontalPos = 0;

	private short verticalPos = 0;

	private MyButton myBtnBack;

	private static final int STEP = 1;

	private ProgressDialog progressDialog;

	private Activity activity = null;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 8888) {
				seekBtnClock = new SeekBarButton(activity,
						R.id.linearlayout_pic_clock, STEP, false) {
					@Override
					public void doUpdate() {
						// TODO Auto-generated method stub
						K_TvPictureManager.getInstance().K_setPCClock(
								seekBtnClock.getProgress());
					}
				};
				seekBtnPhase = new SeekBarButton(activity,
						R.id.linearlayout_pic_phase, STEP, false) {
					@Override
					public void doUpdate() {
						// TODO Auto-generated method stub
						K_TvPictureManager.getInstance().K_setPCPhase(
								seekBtnPhase.getProgress());
					}
				};
				seekBtnHorizontalPos = new SeekBarButton(activity,
						R.id.linearlayout_pic_horizontalpos, STEP, false) {
					@Override
					public void doUpdate() {
						// TODO Auto-generated method stub
						K_TvPictureManager.getInstance().K_setPCHPos(
								seekBtnHorizontalPos.getProgress());
					}
				};
				seekBtnVerticalPos = new SeekBarButton(activity,
						R.id.linearlayout_pic_verticalpos, STEP, false) {
					@Override
					public void doUpdate() {
						// TODO Auto-generated method stub
						K_TvPictureManager.getInstance().K_setPCVPos(
								seekBtnVerticalPos.getProgress());
					}
				};
				myBtnBack = new MyButton(activity,
						R.id.linearlayout_pic_pcimagemodeback);
				myBtnBack.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						MyAsyncTask task = new MyAsyncTask(getProgressDialog());
						task.execute();
					}
				});
				LoadDataToDialog();
				setOnFocusChangeListeners();
			}
			if (msg.what == LittleDownTimer.TIME_OUT_MSG) {
				Intent intent = new Intent(PCImageModeDialogActivity.this,
						RootActivity.class);
				startActivity(intent);
				finish();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		setContentView(R.layout.pc_image_mode_dialog);
		clock = (short) K_TvPictureManager.getInstance().K_getPCClock();
		phase = (short) K_TvPictureManager.getInstance().K_getPCPhase();
		horizontalPos = (short) K_TvPictureManager.getInstance().K_getPCHPos();
		verticalPos = (short) K_TvPictureManager.getInstance().K_getPCVPos();
		handler.sendEmptyMessageDelayed(8888, 100);

		LittleDownTimer.setHandler(handler);
	}

	private ProgressDialog getProgressDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage(this.getResources().getString(
					R.string.str_pic_pcimagemode_waiting));
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(true);
		}
		return progressDialog;
	}

	@Override
	protected void onResume() {
		LittleDownTimer.resumeMenu();
		super.onResume();
	}

	@Override
	public void onUserInteraction() {
		LittleDownTimer.resetMenu();
		super.onUserInteraction();
	}

	@Override
	protected void onPause() {
		Log.d("TvApp", "PcImageModeDialogActivity onPause");
		LittleDownTimer.pauseMenu();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(TvIntent.MAINMENU);
		intent.putExtra("currentPage", MainMenuActivity.PICTURE_PAGE);
		startActivity(intent);
		finish();
		super.onBackPressed();
	}

	private void LoadDataToDialog() {
		clock = (short) K_TvPictureManager.getInstance().K_getPCClock();
		phase = (short) K_TvPictureManager.getInstance().K_getPCPhase();
		horizontalPos = (short) K_TvPictureManager.getInstance().K_getPCHPos();
		verticalPos = (short) K_TvPictureManager.getInstance().K_getPCVPos();

		seekBtnClock.setProgress(clock);
		seekBtnPhase.setProgress(phase);
		seekBtnHorizontalPos.setProgress(horizontalPos);
		seekBtnVerticalPos.setProgress(verticalPos);
	}

	private void setOnFocusChangeListeners() {
		OnFocusChangeListener seekBarBtnFocusListenser = new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					LinearLayout container = (LinearLayout) v;
					container.getChildAt(1).setVisibility(View.VISIBLE);
				} else {
					LinearLayout container = (LinearLayout) v;
					container.getChildAt(1).setVisibility(View.INVISIBLE);
				}
			}
		};
		seekBtnClock.setOnFocusChangeListener(seekBarBtnFocusListenser);
		seekBtnPhase.setOnFocusChangeListener(seekBarBtnFocusListenser);
		seekBtnHorizontalPos.setOnFocusChangeListener(seekBarBtnFocusListenser);
		seekBtnVerticalPos.setOnFocusChangeListener(seekBarBtnFocusListenser);
	}
	public class MyAsyncTask extends AsyncTask<Void, Void, Boolean>
	{
		ProgressDialog myProgress;
		public MyAsyncTask(ProgressDialog progressDialog)
		{
			super();
			myProgress = progressDialog;
			
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			myProgress.show();
		}
		@Override
		protected Boolean doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			boolean flag = K_TvPictureManager.getInstance().K_execAutoPc();
			return flag;
		}
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			myProgress.dismiss();
			if(result)
			{
				Toast.makeText(
						activity,
						activity.getResources().getString(
								R.string.str_pic_pcimagemode_success),
						Toast.LENGTH_SHORT).show();
			}else
			{
				Toast.makeText(
						activity,
						activity.getResources().getString(
								R.string.str_pic_pcimagemode_failed),
						Toast.LENGTH_SHORT).show();
			}
			LoadDataToDialog();
			LittleDownTimer.resetMenu();
		}
	}
}
