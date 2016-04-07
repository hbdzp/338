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

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.kguan.mtvplay.tvapi.K_ChannelModel;
import com.kguan.mtvplay.tvapi.K_Constants;
import com.kguan.mtvplay.tvapi.K_SettingModel;
import com.kguan.mtvplay.tvapi.K_TvCecManager;
import com.kguan.mtvplay.tvapi.K_TvCommonManager;
import com.kguan.mtvplay.tvapi.K_TvS3DManager;
import com.mstar.tv.tvplayer.ui.holder.ChannelViewHolder;
import com.mstar.tv.tvplayer.ui.holder.PictureViewHolder;
import com.mstar.tv.tvplayer.ui.holder.SettingViewHolder;
import com.mstar.tv.tvplayer.ui.holder.SoundViewHolder;
import com.mstar.tv.tvplayer.ui.hotelmenu.HotelMenuActivity;
import com.mstar.tvframework.MstarBaseActivity;
import com.mstar.util.Constant;
import com.mstar.util.PropHelp;
import com.mstar.util.Tools;

public class MainMenuActivity extends MstarBaseActivity implements
		OnGestureListener {
	private static final String TAG = "MainMenuActivity";

	private GestureDetector detector;

	protected ViewFlipper viewFlipper = null;

	protected LayoutInflater lf;

	protected static boolean hasAdd;

	public static int selectedstatusforChannel = 0x00000000;

	public static int selectedsStatusForDemo = 0x00000000;

	protected int currentPage = PICTURE_PAGE;

	public final static int PICTURE_PAGE = 0;

	public final static int SOUND_PAGE = 1;

	public final static int CHANNEL_PAGE = 2;

	public final static int SETTING_PAGE = 3;

	public final static int TIME_PAGE = 5;

	public final static int DEMO_PAGE = 6;

	public final static int S3D_PAGE = 4;

	public final static int OPTION_PAGE = 7;

	public final static int LOCK_PAGE = 8;

	private int mMaxPageIdx = 4;

	protected PictureViewHolder pictureViewHolder;

	protected SoundViewHolder soundViewHolder;

	protected ChannelViewHolder menuOfChannelViewHolder;

	protected SettingViewHolder menuOfSettingViewHolder;

	protected LinearLayout MainMenu_Surface;

	private static boolean NeedSaveBitmap = true;

	public static Bitmap currentBitmapImg = null;

	public static KeyEvent currentKeyEvent = null;

	private static MainMenuActivity mainMenuActivity = null;

	// To remember focus
	private LinearLayout curLinearLayout;

	protected static int[] curFocusedViewIds;

	final static int LANGUAGE_CHANGE_MSG = 1080;

	private MainMenuPauseReceiver mainmenupausereceiver;

	private boolean needRestartMainMenu = false;

	private boolean onCreatFlag = false;

	protected static boolean bMainMenuFocused = false;

	private int mOptionSelectStatus = 0x00000000;

	private int mParentalControlSelectStatus = 0x00000000;

	private boolean mIsPwdCorrect = false;

	private int mSelectedStatusInSetting = 0x00000000;

	private boolean mIsAnimationEnd = true;
	
	private Queue<Integer> keyQueue;
	
	private int maxPasswdLength = 10;

	public static MainMenuActivity getInstance() {
		return mainMenuActivity;
	}

	AnimationListener mAnimationListener = new Animation.AnimationListener() {
		public void onAnimationStart(Animation animation) {
			mIsAnimationEnd = false;
		}

		public void onAnimationRepeat(Animation animation) {
			mIsAnimationEnd = false;
		}

		public void onAnimationEnd(Animation animation) {
			mIsAnimationEnd = true;
		}
	};

	protected Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (pictureViewHolder != null) {
				// pictureViewHolder.toHandleMsg(msg);
			}
			if (msg.what == LittleDownTimer.TIME_OUT_MSG) {
				setExitTypeResult("TimeOut");
				Intent intent = new Intent(MainMenuActivity.this,
						RootActivity.class);
				startActivity(intent);
			}
			if (msg.what == LittleDownTimer.SELECT_RETURN_MSG) {
				if (!(getCurrentFocus() instanceof LinearLayout))
					return;
				curLinearLayout = (LinearLayout) getCurrentFocus();
				if (curLinearLayout != null) {
					if (selectedstatusforChannel != 0x00000000
							&& mSelectedStatusInSetting != 0x00000000
							&& selectedsStatusForDemo != 0x00000000
							&& mOptionSelectStatus != 0x00000000
							&& mParentalControlSelectStatus != 0x00000000) {
						selectedstatusforChannel = 0x00000000;
						mSelectedStatusInSetting = 0x00000000;
						selectedsStatusForDemo = 0x00000000;
						mOptionSelectStatus = 0x00000000;
						mParentalControlSelectStatus = 0x00000000;
					}
					curLinearLayout.clearFocus();
					curLinearLayout.requestFocus();
					curLinearLayout.setSelected(false);
				}
			}
			if (msg.what == LANGUAGE_CHANGE_MSG) {
				restoreCurFocus();
				// recordCurFocusViewId();
				// saveFocusDataToSys();
				setContentView(R.layout.main_menu);
				viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper_main_menu);
				pictureViewHolder = null;
				soundViewHolder = null;
				menuOfChannelViewHolder = null;
				menuOfSettingViewHolder = null;
				initUIComponent(currentPage);
				LittleDownTimer.resetItem();
			}
			if (msg.what == 7758521) {
				{
					if ((getIntent() != null)
							&& (getIntent().getExtras() != null)) {
						currentPage = getIntent().getIntExtra("currentPage",
								PICTURE_PAGE);
						keyInjection(getIntent().getIntExtra("currentKeyCode",
								0));
					}
					if (onCreatFlag) {
						loadFocusDataFromSys();
						addCurrentView(currentPage);
						initUIComponent(currentPage);
						selectedstatusforChannel = 0x00000000;
						mSelectedStatusInSetting = 0x00000000;
						selectedsStatusForDemo = 0x00000000;
						mOptionSelectStatus = 0x00000000;
						mParentalControlSelectStatus = 0x00000000;
					}
					LittleDownTimer.resumeMenu();
					LittleDownTimer.resumeItem();
					currentBitmapImg = null;
					currentKeyEvent = null;
					// freshUI();
				}
				if (onCreatFlag) {
					// new timer service
					LittleDownTimer.setHandler(handler);
					mainmenupausereceiver = new MainMenuPauseReceiver();
					IntentFilter filter1 = new IntentFilter();
					filter1.addAction("mstar.tvsetting.ui.pausemainmenu");
					registerReceiver(mainmenupausereceiver, filter1);
					onCreatFlag = false;
				}
			}
		};
	};

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "--------------> startActivity->MainMenuActivity begin "
				+ System.currentTimeMillis());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		keyQueue = new LinkedList<Integer>();
		curFocusedViewIds = new int[mMaxPageIdx + 1];
		detector = new GestureDetector(this);
		mainMenuActivity = this;
		onCreatFlag = true;
		hasAdd = false;
		viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper_main_menu);
		MainMenu_Surface = (LinearLayout) findViewById(R.id.MainMenu);
		Log.i(TAG, "========== Display TV Menu ==========");
		Log.i(TAG,
				"========== Display TV Menu ========== "
						+ System.currentTimeMillis());

		// box platform
		if (Tools.isBox()) {
			// make sure the default antenna type is DVB-C for box
			new ConfigAntennaTypeAsyncTask().execute();
		}
		if (K_SettingModel.getInstance().K_isSystemLock()) {
			SharedPreferences share2 = getSharedPreferences("menu_check_pwd",
					Activity.MODE_PRIVATE);
			Editor editor = share2.edit();
			editor.putBoolean("pwd_ok", true);
			editor.commit();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (K_TvCecManager.getInstance().K_getCecConfiguration().getCecSetting().cecStatus == Constant.CEC_STATUS_ON) {
			K_TvCecManager.getInstance().K_disableDeviceMenu();
		}
		// Charles
		if (RootActivity.mExitDialog != null) {
			if (RootActivity.mExitDialog.isShowing()) {
				RootActivity.mExitDialog.dismiss();
			}
		}
		bMainMenuFocused = true;

		lf = LayoutInflater.from(MainMenuActivity.this);
		SharedPreferences share = getSharedPreferences("menu_check_pwd",
				Activity.MODE_PRIVATE);
		mIsPwdCorrect = share.getBoolean("pwd_ok", false);
		handler.sendEmptyMessage(7758521);
	}

	protected void addCurrentView(int pageId) {
		if (viewFlipper.getChildCount() > mMaxPageIdx) {
			return;
		}
		viewFlipper.removeAllViews();
		for (int i = 0; i <= pageId; i++) {
			addView(i);
		}
	}

	protected void addView(int id) {
		switch (id) {
		case 0:
			try {
				viewFlipper.addView(lf.inflate(R.layout.picture, null), 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 1:
			try {
				viewFlipper.addView(lf.inflate(R.layout.sound, null), 1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 2:
			try {
				viewFlipper.addView(lf.inflate(R.layout.channel, null), 2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 3:
			try {
				viewFlipper.addView(lf.inflate(R.layout.setting, null), 3);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}

	protected void addOtherView() {
		hasAdd = true;
		if (viewFlipper.getChildCount() >= mMaxPageIdx) {
			return;
		}

		int i = 0;
		if (viewFlipper.getChildCount() < (currentPage + 1)) {
			i = viewFlipper.getChildCount();
			for (; i < (currentPage + 1); i++) {
				addView(i);
			}
		}
		for (i = currentPage; i < mMaxPageIdx; i++) {
			addView(i + 1);
		}
	}

	@Override
	protected void onPause() {
		try {
			LittleDownTimer.pauseMenu();
			LittleDownTimer.pauseItem();
			bMainMenuFocused = false;
			SharedPreferences settings = this.getSharedPreferences(
					Constant.PREFERENCES_TV_SETTING, Context.MODE_PRIVATE);
			boolean flag = settings.getBoolean("_3Dflag", false);
			if (flag) {
				boolean drawDone = false;
				Canvas myCanvas = getBitmapCanvas();
				if (myCanvas != null) {
					MainMenu_Surface.draw(myCanvas);
					drawDone = true;
				}
				if (NeedSaveBitmap && drawDone) {
					try {
						String picName = "";
						int systemLanguage = K_TvCommonManager.getInstance()
								.K_getOsdLanguage();
						if (systemLanguage == K_Constants.TvLanguage_ENGLISH) {
							picName = String.format("mainmenu_eng_pic_%d",
									currentPage);
						} else if (systemLanguage == K_Constants.TvLanguage_CHINESE) {
							picName = String.format("mainmenu_chn_pic_%d",
									currentPage);
						} else if (systemLanguage == K_Constants.TvLanguage_ACHINESE) {
							picName = String.format("mainmenu_tw_pic_%d",
									currentPage);
						}
						saveToBitmap(currentBitmapImg, picName);
						Log.v(TAG, "drawBitmap saveToBitmap");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			recordCurFocusViewId();
			saveFocusDataToSys();
		} catch (Exception e) {
		}
		SharedPreferences share2 = getSharedPreferences("menu_check_pwd",
				Activity.MODE_PRIVATE);
		Editor editor = share2.edit();
		editor.putBoolean("pwd_ok", false);
		editor.commit();
		// selectedparental = 0x00000000;
		if (K_TvCecManager.getInstance().cecStatus == Constant.CEC_STATUS_ON) {
			K_TvCecManager.getInstance().K_enableDeviceMenu();
		}
		super.onPause();
	}

	@Override
	public void onUserInteraction() {
		LittleDownTimer.resetMenu();
		LittleDownTimer.resetItem();
		super.onUserInteraction();
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

	private boolean isSourceInTv() {
		int curis = K_TvCommonManager.getInstance().K_getCurrentTvInputSource();
		if (curis == K_Constants.INPUT_SOURCE_ATV
				|| curis == K_Constants.INPUT_SOURCE_DTV) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isSoureInHDMI() {
		int curis = K_TvCommonManager.getInstance().K_getCurrentTvInputSource();
		if (curis == K_Constants.INPUT_SOURCE_HDMI
				|| curis == K_Constants.INPUT_SOURCE_HDMI2
				|| curis == K_Constants.INPUT_SOURCE_HDMI3
				|| curis == K_Constants.INPUT_SOURCE_HDMI4) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean flag;
		SharedPreferences settings;

		// If MapKeyPadToIR returns true,the previous keycode has been changed
		// to responding
		// android d_pad keys,just return.
		if (MapKeyPadToIR(keyCode, event))
			return true;

		if (selectedstatusforChannel == 0x00000000
				&& mSelectedStatusInSetting == 0x00000000
				&& selectedsStatusForDemo == 0x00000000
				&& mOptionSelectStatus == 0x00000000
				&& mParentalControlSelectStatus == 0x00000000) {
			Intent intent;
			switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
			case KeyEvent.KEYCODE_MENU:
				setExitTypeResult("ManualExit");
				Intent intentRoot = new Intent(this, RootActivity.class);
				startActivity(intentRoot);
				return true;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				if (mIsAnimationEnd == false) {
					break;
				}
				if (!hasAdd) {
					addOtherView();
				}
				Animation leftAnimationFadeIn;
				leftAnimationFadeIn = AnimationUtils.loadAnimation(this,
						R.anim.right_in);
				this.viewFlipper.setInAnimation(leftAnimationFadeIn);
				leftAnimationFadeIn.setAnimationListener(mAnimationListener);
				this.viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(
						this, R.anim.right_out));
				recordCurFocusViewId();
				settings = this.getSharedPreferences(
						Constant.PREFERENCES_TV_SETTING, Context.MODE_PRIVATE);
				flag = settings.getBoolean("_3Dflag", false);
				if (flag) {
					currentKeyEvent = event;
					setExitTypeResult("PageSwitch");
					intent = new Intent(this, RootActivity.class);
					startActivity(intent);
					// finish();
					// overridePendingTransition(R.anim.zoomin,
					// R.anim.zoomout);
				} else {
					if (currentPage == 0) {
						currentPage = mMaxPageIdx;
						if (currentPage == S3D_PAGE) {
							if (!PropHelp.newInstance().has3d() || !isSoureInHDMI()) {
								currentPage--;
							}
						}
					} else {
						currentPage--;
						if (currentPage == CHANNEL_PAGE) {
							if (!isSourceInTv()||isCHLock()) {
								currentPage--;
							}
						}
					}
					initUIComponent(currentPage);
				}
				return true;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				if (mIsAnimationEnd == false) {
					break;
				}

				if (!hasAdd) {
					addOtherView();
				}
				Animation rightAnimationFadeIn = AnimationUtils.loadAnimation(
						this, R.anim.left_in);
				this.viewFlipper.setInAnimation(rightAnimationFadeIn);
				rightAnimationFadeIn.setAnimationListener(mAnimationListener);
				this.viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(
						this, R.anim.left_out));
				recordCurFocusViewId();
				settings = this.getSharedPreferences(
						Constant.PREFERENCES_TV_SETTING, Context.MODE_PRIVATE);
				flag = settings.getBoolean("_3Dflag", false);
				if (flag) {
					currentKeyEvent = event;
					setExitTypeResult("PageSwitch");
					intent = new Intent(this, RootActivity.class);
					startActivity(intent);
					// finish();
					// overridePendingTransition(R.anim.zoomin,
					// R.anim.zoomout);
				} else {
					if (currentPage == mMaxPageIdx) {
						currentPage = 0;
					} else {
						currentPage++;
						if (currentPage == CHANNEL_PAGE) {
							if (!isSourceInTv()||isCHLock()) {
								currentPage++;
							}
						}
						if (currentPage == S3D_PAGE) {
							if (!PropHelp.newInstance().has3d() || !isSoureInHDMI()) {
								currentPage = 0;
							}
						}
					}
					initUIComponent(currentPage);
				}
				return true;
			case KeyEvent.KEYCODE_M:
				return true;
			}
		}
		if (currentPage == PICTURE_PAGE) {
			pictureViewHolder.onKeyDown(keyCode, event);
			if (keyCode == KeyEvent.KEYCODE_TV_INPUT ||keyCode == KeyEvent.KEYCODE_MENU)
				return true;
		}
		if (currentPage == SOUND_PAGE) {
			soundViewHolder.onKeyDown(keyCode, event);
			if (keyCode == KeyEvent.KEYCODE_TV_INPUT||keyCode == KeyEvent.KEYCODE_MENU)
				return true;
		}
		if (currentPage == CHANNEL_PAGE) {
			menuOfChannelViewHolder.onKeyDown(keyCode, event);
			if (keyCode == KeyEvent.KEYCODE_TV_INPUT||keyCode == KeyEvent.KEYCODE_MENU)
				return true;
		}
		if (currentPage == SETTING_PAGE) {
			menuOfSettingViewHolder.onKeyDown(keyCode, event);
			if (keyCode == KeyEvent.KEYCODE_TV_INPUT||keyCode == KeyEvent.KEYCODE_MENU)
				return true;
		}

		// /focus loop
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			View view = getCurrentFocus();
			if (view != null) {
				if (view.getId() == R.id.linearlayout_pic_picturemode) {
					if (findViewById(R.id.linearlayout_pic_pcimagemode) != null
							&& findViewById(R.id.linearlayout_pic_pcimagemode)
									.isEnabled()) {
						findViewById(R.id.linearlayout_pic_pcimagemode)
								.requestFocus();
					} else if (findViewById(R.id.linearlayout_pic_imgnoisereduction) != null
                            && findViewById(R.id.linearlayout_pic_imgnoisereduction)
									.isEnabled()) {
						Log.v(TAG, "===KeyEvent.linearlayout_pic_imgnoisereduction===");
						findViewById(R.id.linearlayout_pic_imgnoisereduction)
								.requestFocus();
					}
					return true;
				}
				if (view.getId() == R.id.linearlayout_cha_autotuning) {
					if (findViewById(R.id.linearlayout_cha_programedit) != null) {
						findViewById(R.id.linearlayout_cha_programedit)
								.requestFocus();
					}
					return true;
				}
				if (view.getId() == R.id.linearlayout_set_menutime) {
					if (findViewById(R.id.linearlayout_set_restoretodefault) != null) {
						findViewById(R.id.linearlayout_set_restoretodefault)
								.requestFocus();
					}
					return true;

				}
				if (view.getId() == R.id.linearlayout_sound_soundmode) {
					if (findViewById(R.id.linearlayout_sound_separatehear) != null) {
						findViewById(R.id.linearlayout_sound_separatehear)
								.requestFocus();
					}
					return true;
				}
			}
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			View view = getCurrentFocus();
			if (view != null) {
				if (view.getId() == R.id.linearlayout_pic_pcimagemode) {
					if (findViewById(R.id.linearlayout_pic_picturemode) != null) {
						findViewById(R.id.linearlayout_pic_picturemode)
								.requestFocus();
					}
					return true;
				}

				if (view.getId() == R.id.linearlayout_pic_imgnoisereduction) {
					if (findViewById(R.id.linearlayout_pic_pcimagemode) == null
							|| !findViewById(R.id.linearlayout_pic_pcimagemode)
									.isEnabled()) {
						if (findViewById(R.id.linearlayout_pic_picturemode) != null) {
							findViewById(R.id.linearlayout_pic_picturemode)
									.requestFocus();
						}
					}
					return true;
				}
				if (view.getId() == R.id.linearlayout_cha_programedit) {
					if (findViewById(R.id.linearlayout_cha_autotuning) != null) {
						findViewById(R.id.linearlayout_cha_autotuning)
								.requestFocus();
					}
					return true;
				}
				if (view.getId() == R.id.linearlayout_set_restoretodefault) {
					if (findViewById(R.id.linearlayout_set_menutime) != null) {
						findViewById(R.id.linearlayout_set_menutime)
								.requestFocus();
					}
					return true;
				}
				if (view.getId() == R.id.linearlayout_sound_separatehear) {
					if (findViewById(R.id.linearlayout_sound_soundmode) != null) {
						findViewById(R.id.linearlayout_sound_soundmode)
								.requestFocus();
					}
					return true;
				}
			}
		}
		
		if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) ||(keyCode == KeyEvent.KEYCODE_VOLUME_UP)) {
			int curInputSource = K_TvCommonManager.getInstance()
					.K_getCurrentTvInputSource();
			if (K_TvCecManager.getInstance().cecStatus == 1) {
				
					if (curInputSource == K_Constants.INPUT_SOURCE_HDMI
							|| curInputSource == K_Constants.INPUT_SOURCE_HDMI2
							|| curInputSource == K_Constants.INPUT_SOURCE_HDMI3
							|| curInputSource == K_Constants.INPUT_SOURCE_HDMI4) {
						if (K_TvCommonManager.getInstance().K_isHdmiSignalMode() == true) {
							if (K_TvCecManager.getInstance().K_sendCecKey(keyCode)) {
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
						Log.d(TAG,"================");
						if (keyCode == KeyEvent.KEYCODE_VOLUME_UP
								|| keyCode == KeyEvent.KEYCODE_VOLUME_DOWN
								|| keyCode == KeyEvent.KEYCODE_VOLUME_MUTE
								|| keyCode == KeyEvent.KEYCODE_DPAD_LEFT
								|| keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
							if (K_TvCecManager.getInstance().K_sendCecKey(keyCode)) {
								Log.d(TAG, "send Cec key,keyCode is " + keyCode
										+ ", tv don't handl the key");
								return true;
							}
						}
					}
				
			}
		}
		keyQueue.offer(keyCode);
		String passwd = keyListToString(keyQueue);
		String factoryPasswd = PropHelp.newInstance().getFactoryPasswd();
		String hotelPasswd = PropHelp.newInstance().getHotelPasswd();
		String versionPasswd = PropHelp.newInstance().getVersionPasswd();
		String softInfoPasswd = PropHelp.newInstance().getSoftInfoPasswd(); //pwd=2013, zjd,20140714
		if (passwd.contains(factoryPasswd)) {
			keyQueue.clear();
			boolean hasFactory = PropHelp.newInstance().hasFactory();
			if (hasFactory) {
                try {
                    Intent intent = new Intent("ktc.tvsetting.factory.factoryMenu"); //mstar.tvsetting.factory.intent.action.MainmenuActivity
                    startActivity(intent);
                    finish();
                } catch (ActivityNotFoundException e){
                }
				return true;
			}
		} else if (passwd.contains(softInfoPasswd)) { 
			keyQueue.clear();
            try {
                Intent intent = new Intent("ktc.intent.action.softinfomenu");
                startActivity(intent);
                finish();
            } catch (ActivityNotFoundException e){
            }
			return true;
		} else if (passwd.contains(hotelPasswd)) {
			keyQueue.clear();
			boolean hasHotel = PropHelp.newInstance().hasHotel();
			if (hasHotel) {
                try {
                	Intent intent = new Intent(MainMenuActivity.this, 
    						HotelMenuActivity.class);
                    startActivity(intent);
                    finish();
                } catch (ActivityNotFoundException e){
                }
				return true;
			}
		} else if (passwd.contains(versionPasswd)) {
			 keyQueue.clear();
                Intent intent = new Intent(
                        "mstar.tvsetting.factory.intent.action.VersionsInfo");
                startActivity(intent);
                finish();
                return true;
        }
        //nathan.liao 2014.12.29 add for auto adjust color temp debug mode set start 
        else if (passwd.contains("8208")) {
            keyQueue.clear();
            Intent intent = new Intent("mstar.tvsetting.factory.intent.action.AutoColorTempAdjustSet");
            startActivity(intent);
            finish();
            return true;   
        }
        //nathan.liao 2014.12.29 add for auto adjust color temp debug mode set end
        else {
			if (keyQueue.size() == maxPasswdLength) {
				keyQueue.poll();
			}
		}
		if(keyCode == KeyEvent.KEYCODE_0 ||keyCode == KeyEvent.KEYCODE_1||keyCode == KeyEvent.KEYCODE_2
				||keyCode == KeyEvent.KEYCODE_3||keyCode == KeyEvent.KEYCODE_4||keyCode == KeyEvent.KEYCODE_5
				||keyCode == KeyEvent.KEYCODE_6||keyCode == KeyEvent.KEYCODE_7||keyCode == KeyEvent.KEYCODE_8
				||keyCode == KeyEvent.KEYCODE_9)
		{
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// ignore any key up event from MStar Smart TV Keypad
		String deviceName = InputDevice.getDevice(event.getDeviceId())
				.getName();
		if (deviceName.equals("MStar Smart TV Keypad")) {
			return true;
		}

		return super.onKeyUp(keyCode, event);
	}

	public boolean MapKeyPadToIR(int keyCode, KeyEvent event) {
		String deviceName = InputDevice.getDevice(event.getDeviceId())
				.getName();
		Log.d(TAG, "deviceName" + deviceName);
		if (!deviceName.equals("MStar Smart TV Keypad"))
			return false;
		switch (keyCode) {
		case KeyEvent.KEYCODE_CHANNEL_UP:
			keyInjection(KeyEvent.KEYCODE_DPAD_UP);
			return true;
		case KeyEvent.KEYCODE_CHANNEL_DOWN:
			keyInjection(KeyEvent.KEYCODE_DPAD_DOWN);
			return true;
		case KeyEvent.KEYCODE_VOLUME_UP:
			keyInjection(KeyEvent.KEYCODE_DPAD_RIGHT);
			return true;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			keyInjection(KeyEvent.KEYCODE_DPAD_LEFT);
			return true;
		default:
			return false;
		}

	}

	private void setExitTypeResult(String result) {
		// RootActivity.MENU_EXIT_TYPE = result;
	}

	protected void initUIComponent(int page) {
		viewFlipper.setDisplayedChild(page);
		switch (page) {
		case PICTURE_PAGE:
			if (pictureViewHolder == null) {
				pictureViewHolder = new PictureViewHolder(MainMenuActivity.this);
				pictureViewHolder.findViews();
				pictureViewHolder.LoadDataToUI();
			}
			break;
		case SOUND_PAGE:
			if (soundViewHolder == null) {
				soundViewHolder = new SoundViewHolder(MainMenuActivity.this);
				soundViewHolder.findViews();
				soundViewHolder.LoadDataToUI();
			}
			break;
		case CHANNEL_PAGE:
			menuOfChannelViewHolder = new ChannelViewHolder(
					MainMenuActivity.this);
			menuOfChannelViewHolder.findViews();
			break;
		case SETTING_PAGE:
			if (menuOfSettingViewHolder == null) {
				menuOfSettingViewHolder = new SettingViewHolder(
						MainMenuActivity.this, handler);
				menuOfSettingViewHolder.findViews();
				menuOfSettingViewHolder.LoadDataToUI();
			}
			break;
		}
		initCurFocus();
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(mainmenupausereceiver);
		// if (this.getSharedPreferences("TvSetting", 0).getBoolean("_3Dflag",
		// false)
		// && RootActivity.MENU_EXIT_TYPE.equals(""))
		// RootActivity.my3DHandler.sendEmptyMessageDelayed(RootActivity._3DAction.hide,
		// 300);
		if (needRestartMainMenu) {
			setExitTypeResult("Restart");
			needRestartMainMenu = false;
		}
		super.onDestroy();
	}

	private void saveToBitmap(Bitmap bitmap, String bitName) throws IOException {
		FileOutputStream fOut = openFileOutput(bitName + ".png",
				Activity.MODE_PRIVATE);
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Canvas getBitmapCanvas() {
		final int cl = MainMenu_Surface.getLeft();
		final int ct = MainMenu_Surface.getTop();
		final int cr = MainMenu_Surface.getRight();
		final int cb = MainMenu_Surface.getBottom();
		if (currentBitmapImg == null) {
			final int width = cr - cl;
			final int height = cb - ct;
			if (width <= 0 || height <= 0) {
				return null;
			}
			try {
				currentBitmapImg = Bitmap.createBitmap(width, height,
						Bitmap.Config.ARGB_8888);
			} catch (OutOfMemoryError e) {
				return null;
			}
		}
		Canvas myCanvas = new Canvas(currentBitmapImg);
		return myCanvas;
	}

	private void recordCurFocusViewId() {
		View view = getCurrentFocus();
		if (view != null) {
			curFocusedViewIds[currentPage] = view.getId();
		}
	}

	private void saveFocusDataToSys() {
		SharedPreferences.Editor editor = getSharedPreferences(
				Constant.PREFERENCES_TV_SETTING, Context.MODE_PRIVATE).edit();
		int i = 0;
		for (int id : curFocusedViewIds) {
			editor.putInt("page" + i, id);
			editor.commit();
			i++;
		}
	}

	private void initCurFocus() {
		if (curFocusedViewIds[currentPage] != 0) {
			View view = findViewById(curFocusedViewIds[currentPage]);
			if (view != null) {
				view.requestFocus();
			}
		}
	}

	private void restoreCurFocus() {
		curFocusedViewIds[PICTURE_PAGE] = R.id.linearlayout_pic_picturemode;
		curFocusedViewIds[SOUND_PAGE] = R.id.linearlayout_sound_soundmode;
		curFocusedViewIds[CHANNEL_PAGE] = R.id.linearlayout_cha_autotuning;
		curFocusedViewIds[SETTING_PAGE] = R.id.linearlayout_set_menutime;
	}

	private void loadFocusDataFromSys() {
		SharedPreferences sharedPreferences = getSharedPreferences(
				Constant.PREFERENCES_TV_SETTING, Context.MODE_PRIVATE);
		for (int i = 0; i < curFocusedViewIds.length; i++) {
			curFocusedViewIds[i] = sharedPreferences.getInt("page" + i, 0);
		}
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (e1.getX() - e2.getX() < -100) {
			this.viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.right_in));
			this.viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.right_out));
			// this.viewFlipper.showPrevious();
			if (currentPage == 0) {
				currentPage = mMaxPageIdx;
			} else {
				currentPage--;
				if (currentPage == CHANNEL_PAGE) {
					if (!isSourceInTv()) {
						currentPage--;
					}
				}
			}
			initUIComponent(currentPage);
			return true;
		} else if (e1.getX() - e2.getX() > 100) {
			this.viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.left_in));
			this.viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.left_out));
			// this.viewFlipper.showNext();
			if (currentPage == mMaxPageIdx) {
				currentPage = 0;
			} else {
				currentPage++;
				if (currentPage == CHANNEL_PAGE) {
					if (!isSourceInTv()) {
						currentPage++;
					}
				}
			}
			initUIComponent(currentPage);
			return true;
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return this.detector.onTouchEvent(event);
	}

	public boolean isPwdCorrect() {
		return mIsPwdCorrect;
	}

	public int getOptionSelectStatus() {
		return mOptionSelectStatus;
	}

	public void setOptionSelectStatus(int status) {
		mOptionSelectStatus = status;
	}

	public int getParentalControlSelectStatus() {
		return mParentalControlSelectStatus;
	}

	public void setParentalControlSelectStatus(int status) {
		mParentalControlSelectStatus = status;
	}

	public int getSettingSelectStatus() {
		return mSelectedStatusInSetting;
	}

	public void setSettingSelectStatus(int status) {
		mSelectedStatusInSetting = status;
	}

	private class MainMenuPauseReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			// mute
			if (action.equals("mstar.tvsetting.ui.pausemainmenu")) {
				Log.i(TAG, "--------------recieved-------------");
				// finish();
				return;
				// from.finish();
			}
		}
	}

	// for box only
	private class ConfigAntennaTypeAsyncTask extends
			AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			int currentRouteIndex = K_ChannelModel.getInstance()
					.K_getCurrentDtvRouteIndex();
			int dvbcRouteIndex = K_ChannelModel.getInstance()
					.K_getSpecificDtvRouteIndex(K_Constants.TV_ROUTE_DVBC);
			Log.d(TAG, "ConfigAntennaTypeAsyncTask, antenna "
					+ currentRouteIndex);
			if (currentRouteIndex != dvbcRouteIndex) {
				K_TvS3DManager.getInstance().K_setDisplayFormatForUI(
						K_Constants.THREE_DIMENSIONS_DISPLAY_FORMAT_NONE);
				K_ChannelModel.getInstance().K_switchMSrvDtvRouteCmd(
						dvbcRouteIndex);
			}

			return null;
		}
	}
	private String keyListToString(Queue<Integer> al) {
		StringBuilder sb = new StringBuilder();
		for (int key : al) {
			switch (key) {
				case KeyEvent.KEYCODE_0:
					sb.append("0");
					break;
				case KeyEvent.KEYCODE_1:
					sb.append("1");
					break;
				case KeyEvent.KEYCODE_2:
					sb.append("2");
					break;
				case KeyEvent.KEYCODE_3:
					sb.append("3");
					break;
				case KeyEvent.KEYCODE_4:
					sb.append("4");
					break;
				case KeyEvent.KEYCODE_5:
					sb.append("5");
					break;
				case KeyEvent.KEYCODE_6:
					sb.append("6");
					break;
				case KeyEvent.KEYCODE_7:
					sb.append("7");
				break;
			case KeyEvent.KEYCODE_8:
				sb.append("8");
				break;
			case KeyEvent.KEYCODE_9:
				sb.append("9");
				break;
			default:
				break;
			}
		}
		return sb.toString();
	}
	//ktc nathan.liao 20140905 for hotelmenu start
	private boolean isCHLock() {
			return getValueDatabase("Hotelmode") > 0 && getValueDatabase("SearchLock") > 0;
	}
	private int getValueDatabase(String tag) {
		int bAutoBacklightSign = 0;
		Cursor cursor = this.getContentResolver().query(
				Uri.parse("content://mstar.tv.usersetting/systemsetting"),
				null, null, null, null);
		if (cursor.moveToNext())
			bAutoBacklightSign = cursor.getInt(cursor
					.getColumnIndex(tag));
		cursor.close();
		return bAutoBacklightSign;
	}
	//ktc nathan.liao 20140905 for hotelmenu end
}
