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

package com.mstar.tv.tvplayer.ui.holder;

import java.io.File;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kguan.mtvplay.tvapi.K_ChannelModel;
import com.kguan.mtvplay.tvapi.K_Constants;
import com.kguan.mtvplay.tvapi.K_SettingModel;
import com.kguan.mtvplay.tvapi.K_TvCommonManager;
import com.kguan.mtvplay.tvapi.K_TvFactoryManager;
import com.kguan.mtvplay.tvapi.K_TvS3DManager;
import com.kguan.mtvplay.tvapi.K_TvTimerManager;
import com.mstar.tv.tvplayer.ui.LittleDownTimer;
import com.mstar.tv.tvplayer.ui.LocalUpdateSoftwareActivity;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.RootActivity;
import com.mstar.tv.tvplayer.ui.component.ComboButton;
import com.mstar.tv.tvplayer.ui.component.SeekBarButton;
import com.mstar.util.PropHelp;

public class SettingViewHolder {

	private K_SettingModel kSettingsModel;
	private K_TvCommonManager kTvCommonModel;
	private K_ChannelModel kChannelModel;
	private K_TvFactoryManager kTvFactoryModel;
	protected ComboButton comboBtnMenuTime;
	protected ComboButton comboBtnSwitchMode;
	protected ComboButton comboBtnSleepTime;
	protected ComboButton comboBtnPowerLogo;
	protected ComboButton comboBtnAutoBacklight;
	protected SeekBarButton seekBtnAjustBacklight;
	protected LinearLayout linear_set_softwareupdate;
	protected LinearLayout linear_set_restoretodefault;
	protected LinearLayout linear_setting_main;
	private TextView enterText;
	private TextView switchText;

	private Activity settingActivity;
	private AlertDialog resetDialog;
	private TextView menu_reset_yes;
	private TextView menu_reset_no;
	private int powerlogo = 0;

	protected boolean deflogoflag = false;
	protected boolean cap1flag = false;
	protected boolean cap2flag = false;
	protected boolean logoupdate = false;
	protected Intent intent = new Intent();
	protected MyPowerlogoSetAsyncTask mypowerlogoasyn;
	private Activity mActivity;
	
	// Hisa 2016.03.01 睡眠时间设置倒计时 start
	private TextView tvSleepTimeVal;
	// Hisa 2016.03.01 睡眠时间设置倒计时 end

	public SettingViewHolder(Activity activity, Handler handler) {
		this.settingActivity = activity;
		kSettingsModel = K_SettingModel.getInstance();
		kTvCommonModel = K_TvCommonManager.getInstance();
		kChannelModel = K_ChannelModel.getInstance();
		kTvFactoryModel = K_TvFactoryManager.getInstance();
		mActivity = activity;
	}

	public void findViews() {
		enterText = (TextView) settingActivity
				.findViewById(R.id.enter_value_text_setting);
		switchText = (TextView) settingActivity
				.findViewById(R.id.switch_value_text_setting);
		// Hisa 2016.03.01 睡眠时间设置倒计时 start
		tvSleepTimeVal = (TextView)settingActivity.findViewById(R.id.textview_set_sleeptime_val);
		// Hisa 2016.03.01 睡眠时间设置倒计时 end
		comboBtnMenuTime = new ComboButton(settingActivity, settingActivity
				.getResources()
				.getStringArray(R.array.str_arr_set_menutimetype),
				R.id.linearlayout_set_menutime, 1, 2, true) {

			@Override
			public void doUpdate() {
				if (kTvCommonModel != null) {
					kTvCommonModel.K_setOsdDuration(comboBtnMenuTime.getIdx());
					if (comboBtnMenuTime.getIdx() == 5) {
						LittleDownTimer.stopMenu();
					} else {
						LittleDownTimer.setMenuStatus(kTvCommonModel
								.K_getOsdTimeoutInSecond());
					}
				}
			}

		};

		comboBtnSwitchMode = new ComboButton(settingActivity, settingActivity
				.getResources().getStringArray(
						R.array.str_arr_set_switchmodetype),
				R.id.linearlayout_set_switchmode, 1, 2, true) {

			@Override
			public void doUpdate() {
				if (kChannelModel != null) {
					kChannelModel.K_setAtvChannelSwitchMode(comboBtnSwitchMode
							.getIdx());
				}
			}

		};
		comboBtnSleepTime = new ComboButton(settingActivity,
				settingActivity.getResources().getStringArray(
						R.array.str_arr_sleep_mode_vals),
				R.id.linearlayout_set_sleeptime, 1, 2, true) {

			@Override
			public void doUpdate() {
				if (kSettingsModel != null) {
					K_TvTimerManager.getInstance().K_setSleepTimeMode(kSettingsModel.K_getValues_EnumSleepTimeState(comboBtnSleepTime.getIdx()).ordinal());
				}
			}

		};
		
		comboBtnAutoBacklight = new ComboButton(settingActivity,
				settingActivity.getResources().getStringArray(
						R.array.str_arr_sound_avc_vals),
				R.id.linearlayout_set_autobacklightmode, 1, 2, true) {

			@Override
			public void doUpdate() {
				if (kSettingsModel != null) {
					kSettingsModel.K_setAutoBackLight(mActivity, comboBtnAutoBacklight
							.getIdx());
				}
			}

		};
		seekBtnAjustBacklight = new SeekBarButton(settingActivity,
				R.id.linearlayout_set_ajustbacklight, 1, true) {
			@Override
			public void doUpdate() {
				kSettingsModel.K_setAutonewBackLight(mActivity, seekBtnAjustBacklight
						.getProgress());
			}
		};

		comboBtnPowerLogo = new ComboButton(settingActivity, settingActivity
				.getResources().getStringArray(R.array.str_arr_powerlogotype),
				R.id.linearlayout_set_powerlogo, 1, 2, true) {

			@Override
			public void doUpdate() {
				if (kSettingsModel != null) {
					logoupdate = true;
					mypowerlogoasyn = new MyPowerlogoSetAsyncTask();
					mypowerlogoasyn.execute(comboBtnPowerLogo);
				}
			}

		};
		linear_set_softwareupdate = (LinearLayout) settingActivity
				.findViewById(R.id.linearlayout_cha_softwareupdate);
		linear_set_restoretodefault = (LinearLayout) settingActivity
				.findViewById(R.id.linearlayout_set_restoretodefault);
		linear_setting_main = (LinearLayout) settingActivity
				.findViewById(R.id.setting_main_menu);
		setOnFocusChangeListeners();
		setOnClickListeners();
		comboBtnMenuTime.setFocused();
	}

	public void LoadDataToUI() {
		if (kSettingsModel != null && kTvCommonModel != null) {
			comboBtnMenuTime.setIdx(kTvCommonModel.K_getOsdDuration());

			if (kTvCommonModel.K_getCurrentTvInputSource() == K_Constants.INPUT_SOURCE_ATV)
				comboBtnSwitchMode.setIdx(kSettingsModel
						.K_getAtvChannelSwitchModeIndex(mActivity));
			// Hisa 2016.03.01 睡眠时间设置倒计时 start
			comboBtnSleepTime.setIdx(K_TvTimerManager.getInstance().K_getSleepTimeMode());
			if (K_TvTimerManager.getInstance().K_getSleepTimeMode() != K_TvTimerManager.K_SLEEP_TIME_OFF)
				tvSleepTimeVal.setText(K_TvTimerManager.getInstance().K_getSleepTimeRemainMins() + "" + settingActivity.getString(R.string.str_setting_sleep_mode_remin_min));
			// Hisa 2016.03.01 睡眠时间设置倒计时 end
			comboBtnAutoBacklight.setIdx(kSettingsModel
					.K_getAutoBacklightIndex(mActivity));
			seekBtnAjustBacklight.setProgress((short) kSettingsModel
					.K_getAjustBacklightIndex(mActivity));

			deflogoflag = findFileFromCustomer("boot0.jpg");
			cap1flag = findFileFromCustomer("boot1.jpg");
			cap2flag = findFileFromCustomer("boot2.jpg");
			boolean hasLogo = PropHelp.newInstance().hasLogo();
			powerlogo = kTvFactoryModel.K_getOrdinal_TvFactory();
			Log.i("zjf", "powerlogo"+" "+powerlogo+" "+deflogoflag+" "+cap1flag+" "+cap2flag);
			if (powerlogo == 0 || (powerlogo == 1 && !deflogoflag)
					|| (powerlogo == 2 && !cap1flag)
					|| (powerlogo == 3 && !cap2flag)) {
				comboBtnPowerLogo.setItemEnable(2, false);
				comboBtnPowerLogo.setItemEnable(3, false);
			}

			if (hasLogo) {
				comboBtnPowerLogo.setVisibility(true);
				comboBtnPowerLogo.setIdx(powerlogo);
			} else {
				comboBtnPowerLogo.setVisibility(false);
			}
			if (kTvCommonModel.K_getCurrentTvInputSource() == K_Constants.INPUT_SOURCE_ATV) {
				comboBtnSwitchMode.setVisibility(true);
			} else {
				comboBtnSwitchMode.setVisibility(false);
			}
			boolean hasDynamicBacklight = PropHelp.newInstance()
					.hasDynamicBacklight();
			boolean isZh = PropHelp.newInstance().isZh();
			if (hasDynamicBacklight && isZh) {
				seekBtnAjustBacklight.setVisibility(true);
				comboBtnAutoBacklight.setVisibility(true);// open
			} else {
				seekBtnAjustBacklight.setVisibility(false);
				comboBtnAutoBacklight.setVisibility(false);// colse backliht;
			}
		}
	}

	private void setOnClickListeners() {
		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View view) {
				switch (view.getId()) {
				case R.id.linearlayout_cha_softwareupdate:
					startSoftUpdate();
					break;
				case R.id.linearlayout_set_restoretodefault:
					showRestoreDialog();
					break;
				default:
					break;
				}

			}
		};
		linear_set_softwareupdate.setOnClickListener(listener);
		linear_set_restoretodefault.setOnClickListener(listener);
		OnClickListener comoBtnOnClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (v.isSelected()) {
					LinearLayout container = (LinearLayout) v;
					container.getChildAt(1).setVisibility(View.VISIBLE);
					container.getChildAt(3).setVisibility(View.VISIBLE);
					setHelpTextSelect(true);
				} else {
					LinearLayout container = (LinearLayout) v;
					container.getChildAt(1).setVisibility(View.INVISIBLE);
					container.getChildAt(3).setVisibility(View.INVISIBLE);
					setHelpTextSelect(false);
				}
			}
		};
		comboBtnMenuTime.setOnClickListener(comoBtnOnClickListener);
		comboBtnSwitchMode.setOnClickListener(comoBtnOnClickListener);
		comboBtnSleepTime.setOnClickListener(comoBtnOnClickListener);
		comboBtnPowerLogo.setOnClickListener(comoBtnOnClickListener);
		comboBtnAutoBacklight.setOnClickListener(comoBtnOnClickListener);
		OnClickListener seekBarBtnOnClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.isSelected()) {
					LinearLayout container = (LinearLayout) v;
					container.getChildAt(1).setVisibility(View.VISIBLE);
					setHelpTextSelect(true);
				} else {
					LinearLayout container = (LinearLayout) v;
					container.getChildAt(1).setVisibility(View.INVISIBLE);
					setHelpTextSelect(false);
				}
			}
		};
		seekBtnAjustBacklight.setOnClickListener(seekBarBtnOnClickListener);

	}

	private void setOnFocusChangeListeners() {
		OnFocusChangeListener comboBtnFocusListener = new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				LinearLayout container = (LinearLayout) v;
				container.getChildAt(1).setVisibility(View.INVISIBLE);
				container.getChildAt(3).setVisibility(View.INVISIBLE);
				setHelpTextSelect(false);
			}
		};
		comboBtnMenuTime.setOnFocusChangeListener(comboBtnFocusListener);
		comboBtnSwitchMode.setOnFocusChangeListener(comboBtnFocusListener);
		comboBtnSleepTime.setOnFocusChangeListener(comboBtnFocusListener);
		comboBtnPowerLogo.setOnFocusChangeListener(comboBtnFocusListener);
		comboBtnAutoBacklight.setOnFocusChangeListener(comboBtnFocusListener);

		OnFocusChangeListener seekBarBtnFocusListenser = new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				LinearLayout container = (LinearLayout) v;
				container.getChildAt(1).setVisibility(View.INVISIBLE);
				setHelpTextSelect(false);
			}
		};
		seekBtnAjustBacklight
				.setOnFocusChangeListener(seekBarBtnFocusListenser);

	}

	private void startSoftUpdate() {
		Intent intent = new Intent();
		intent.setClass(settingActivity, LocalUpdateSoftwareActivity.class);
		settingActivity.startActivity(intent);
		settingActivity.finish();
	}

	private class MyPowerlogoSetAsyncTask extends
			AsyncTask<ComboButton, Void, String> {

		@Override
		protected String doInBackground(ComboButton... arg0) {
			// TODO Auto-generated method stub
			kTvFactoryModel.K_setEnvironmentPowerOnLogoMode(kSettingsModel
					.K_getValues(arg0[0].getIdx()));
			return "";
		}

	}

	private boolean findFileFromCustomer(String filename) {
		// TODO Find File From Customer function
		boolean ret = false;
		File file = new File("/tvconfig/" + filename);
		if (file != null && file.exists()) {
			ret = true;
		}
		return ret;
	}

	private void showRestoreDialog() {
		linear_setting_main.setVisibility(View.GONE);
		resetDialog = new AlertDialog.Builder(settingActivity).create();
		resetDialog.show();
		resetDialog.setContentView(R.layout.customer_dialog);
		menu_reset_yes = (TextView) resetDialog
				.findViewById(R.id.textview_menu_reset_yes);
		menu_reset_no = (TextView) resetDialog
				.findViewById(R.id.textview_menu_reset_no);
		menu_reset_yes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				kTvFactoryModel.K_setEnvironmentPowerOnMusicVolume(0xFF);
					int currInputSource = kTvCommonModel
							.K_getCurrentTvInputSource();
					if (currInputSource == K_Constants.INPUT_SOURCE_DTV) {
						K_TvS3DManager.getInstance()
								.K_setDisplayFormatForUI(K_Constants.THREE_DIMENSIONS_DISPLAY_FORMAT_NONE);
							// Because restore to factory
							// default value,reset
							// routeIndex to 0
						kChannelModel.K_setDtvAntennaType(0);
					}
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					RootActivity.setRebootFlag(true);
					kTvFactoryModel.K_restoreToDefault();
					kTvCommonModel.K_rebootSystem("reboot");
					resetDialog.dismiss();
			}
		});
		menu_reset_no.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				resetDialog.dismiss();
				linear_setting_main.setVisibility(View.VISIBLE);
				linear_set_restoretodefault.setFocusable(true);
				linear_set_restoretodefault.requestFocus();

			}
		});
		menu_reset_yes.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int keycode, KeyEvent keyevent) {
				// TODO Auto-generated method stub
				if (keyevent.getAction() == KeyEvent.ACTION_DOWN) {
					if (keycode == KeyEvent.KEYCODE_TV_INPUT) {
						// TODO Auto-generated method stub
						kTvFactoryModel
									.K_setEnvironmentPowerOnMusicVolume(0xFF);
							int currInputSource = kTvCommonModel
									.K_getCurrentTvInputSource();
							if (currInputSource == K_Constants.INPUT_SOURCE_DTV) {
								K_TvS3DManager.getInstance()
										.K_setDisplayFormatForUI(K_Constants.THREE_DIMENSIONS_DISPLAY_FORMAT_NONE);
									// Because restore to factory
									// default value,reset
									// routeIndex to 0
								kChannelModel.K_setDtvAntennaType(0);
							}
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							RootActivity.setRebootFlag(true);
							kTvFactoryModel.K_restoreToDefault();
							kTvCommonModel.K_rebootSystem("reboot");
							resetDialog.dismiss();
						return true;
					} else if (keycode == KeyEvent.KEYCODE_VOLUME_DOWN
							|| keycode == KeyEvent.KEYCODE_VOLUME_UP) {
						menu_reset_no.requestFocus();
						return true;
					}
				}
				return false;
			}
		});

		menu_reset_no.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int keycode, KeyEvent arg2) {
				// TODO Auto-generated method stub
				if (arg2.getAction() == KeyEvent.ACTION_DOWN) {
					if (keycode == KeyEvent.KEYCODE_TV_INPUT) {
						resetDialog.dismiss();
						linear_setting_main.setVisibility(View.VISIBLE);
						MainMenuActivity.getInstance().setSettingSelectStatus(
								0x10000000);
						linear_set_restoretodefault.setFocusable(true);
						linear_set_restoretodefault.requestFocus();

						return true;
					} else if (keycode == KeyEvent.KEYCODE_VOLUME_DOWN
							|| keycode == KeyEvent.KEYCODE_VOLUME_UP) {
						menu_reset_yes.requestFocus();
						return true;
					}
				}
				return false;
			}

		});
		resetDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface arg0) {
				// TODO Auto-generated method stub
				resetDialog.dismiss();
				linear_setting_main.setVisibility(View.VISIBLE);
				MainMenuActivity.getInstance().setSettingSelectStatus(
						0x10000000);
				linear_set_restoretodefault.setFocusable(true);
				linear_set_restoretodefault.requestFocus();
			}
		});
	}

	/**
	 * 菜单底部提示信息变化
	 * 
	 * @param flag
	 */
	private void setHelpTextSelect(boolean flag) {
		if (flag) {
			enterText.setText(R.string.str_menu_operating_cancel);
			switchText.setText(R.string.str_menu_operating_adjust);
		} else {
			enterText.setText(R.string.str_menu_operating_select);
			switchText.setText(R.string.str_menu_operating_switch);
		}
	}

	/**
	 * 按键板的Source键作为确认键使用
	 * 
	 * @param keyCode
	 * @param event
	 */
	public void onKeyDown(int keyCode, KeyEvent event) {
		int currentId = settingActivity.getCurrentFocus().getId();
		switch (keyCode) {
		case KeyEvent.KEYCODE_TV_INPUT:
			switch (currentId) {
			case R.id.linearlayout_set_menutime:
			case R.id.linearlayout_set_switchmode:
			case R.id.linearlayout_set_sleeptime:
			case R.id.linearlayout_set_powerlogo:
			case R.id.linearlayout_set_autobacklightmode:
				View v = (LinearLayout) settingActivity.findViewById(currentId);
				if (v.isSelected()) {
					LinearLayout container = (LinearLayout) v;
					container.getChildAt(1).setVisibility(View.VISIBLE);
					container.getChildAt(3).setVisibility(View.VISIBLE);
					setHelpTextSelect(true);
				} else {
					LinearLayout container = (LinearLayout) v;
					container.getChildAt(1).setVisibility(View.INVISIBLE);
					container.getChildAt(3).setVisibility(View.INVISIBLE);
					setHelpTextSelect(false);
				}
				break;
			case R.id.linearlayout_set_ajustbacklight:
				View v2 = (LinearLayout) settingActivity
						.findViewById(currentId);
				if (v2.isSelected()) {
					LinearLayout container = (LinearLayout) v2;
					container.getChildAt(1).setVisibility(View.VISIBLE);
					setHelpTextSelect(true);
				} else {
					LinearLayout container = (LinearLayout) v2;
					container.getChildAt(1).setVisibility(View.INVISIBLE);
					setHelpTextSelect(false);
				}
				break;
			case R.id.linearlayout_cha_softwareupdate:
				startSoftUpdate();
				break;
			case R.id.linearlayout_set_restoretodefault:
				showRestoreDialog();
				break;
			default:
				break;
			}
			break;

		default:
			break;
		}

	}
}
