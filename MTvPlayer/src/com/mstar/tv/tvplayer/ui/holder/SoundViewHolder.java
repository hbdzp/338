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

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kguan.mtvplay.tvapi.K_TvAudioManager;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.component.ComboButton;
import com.mstar.tv.tvplayer.ui.component.MyButton;
import com.mstar.tv.tvplayer.ui.component.SeekBarButton;
import com.mstar.tv.tvplayer.ui.settings.SeperateHearActivity;
//nathan.liao 2015.03.03 add for T4C1 board start
import com.mstar.util.PropHelp;
//nathan.liao 2015.03.03 add for T4C1 board end

public class SoundViewHolder {

	private K_TvAudioManager kSoundModel;

	private static final String TAG = "SoundViewHolder";

	protected static final int STEP = 1;

	protected ComboButton comboBtnSoundmode;
/*
	protected SeekBarButton seekBarBtnBass;

	protected SeekBarButton seekBarBtnTreble;*/

	protected SeekBarButton seekBarBtnBalance;
	
	protected SeekBarButton seekBarBtnEq120Hz;
	protected SeekBarButton seekBarBtnEq500Hz;
	protected SeekBarButton seekBarBtnEq1500Hz;
	protected SeekBarButton seekBarBtnEq5KHz;
	protected SeekBarButton seekBarBtnEq10KHz;

	protected ComboButton comboBtnSurround;

	protected ComboButton comboBtnSpdifoutput;

	protected MyButton btnSeperateHear;

	protected Activity activity;

	private TextView enterText;

	private TextView switchText;

	public SoundViewHolder(Activity act) {
		this.activity = act;
		kSoundModel = K_TvAudioManager.getInstance();
	}

	public void findViews() {
		enterText = (TextView) activity
				.findViewById(R.id.enter_value_text_sound);
		switchText = (TextView) activity
				.findViewById(R.id.switch_value_text_sound);
/*		seekBarBtnBass = new SeekBarButton(activity,
				R.id.linearlayout_sound_bass, STEP, true) {
			@Override
			public void doUpdate() {
				// TODO Auto-generated method stub
				if (kSoundModel != null) {
					kSoundModel.K_setBass(seekBarBtnBass.getProgress());
				}
			}
		};
		seekBarBtnTreble = new SeekBarButton(activity,
				R.id.linearlayout_sound_treble, STEP, true) {
			@Override
			public void doUpdate() {
				// TODO Auto-generated method stub
				if (kSoundModel != null) {
					kSoundModel.K_setTreble(seekBarBtnTreble.getProgress());
				}
			}
		};*/
		
		seekBarBtnEq120Hz = new SeekBarButton(activity,
				R.id.linearlayout_sound_eq_120hz, STEP, true) {
			@Override
			public void doUpdate() {
				// TODO Auto-generated method stub
				if (kSoundModel != null) {
					kSoundModel.K_setEqBand120(seekBarBtnEq120Hz.getProgress());
				}
			}
		};
		seekBarBtnEq500Hz = new SeekBarButton(activity,
				R.id.linearlayout_sound_eq_500hz, STEP, true) {
			@Override
			public void doUpdate() {
				// TODO Auto-generated method stub
				if (kSoundModel != null) {
					kSoundModel.K_setEqBand500(seekBarBtnEq500Hz.getProgress());
				}
			}
		};
		seekBarBtnEq1500Hz = new SeekBarButton(activity,
				R.id.linearlayout_sound_eq_1500hz, STEP, true) {
			@Override
			public void doUpdate() {
				// TODO Auto-generated method stub
				if (kSoundModel != null) {
					kSoundModel.K_setEqBand1500(seekBarBtnEq1500Hz.getProgress());
				}
			}
		};
		seekBarBtnEq5KHz = new SeekBarButton(activity,
				R.id.linearlayout_sound_eq_5000hz, STEP, true) {
			@Override
			public void doUpdate() {
				// TODO Auto-generated method stub
				if (kSoundModel != null) {
					kSoundModel.K_setEqBand5k(seekBarBtnEq5KHz.getProgress());
				}
			}
		};
		seekBarBtnEq10KHz = new SeekBarButton(activity,
				R.id.linearlayout_sound_eq_10000hz, STEP, true) {
			@Override
			public void doUpdate() {
				// TODO Auto-generated method stub
				if (kSoundModel != null) {
					kSoundModel.K_setEqBand10k(seekBarBtnEq10KHz.getProgress());
				}
			}
		};
		comboBtnSoundmode = new ComboButton(activity, activity.getResources()
				.getStringArray(R.array.str_arr_sound_soundmode_vals),
				R.id.linearlayout_sound_soundmode, 1, 2, true) {
			@Override
			public void doUpdate() {
				SetFocusableOrNotForUserMode();
				if (kSoundModel != null) {
					kSoundModel.K_setAudioSoundMode(comboBtnSoundmode.getIdx());
				}
				freshDataToUIWhenSoundModChange();
			}
		};
		SetFocusableOrNotForUserMode();
		seekBarBtnBalance = new SeekBarButton(activity,
				R.id.linearlayout_sound_balance, STEP, true) {
			@Override
			public void doUpdate() {
				// TODO Auto-generated method stub
				if (kSoundModel != null) {
					kSoundModel.K_setBalance(seekBarBtnBalance.getProgress());
					if (seekBarBtnBalance.getProgress() < 50) {
						seekBarBtnBalance.textViewProgress.setText("L"
								+ (50 - seekBarBtnBalance.getProgress()));
					} else if (seekBarBtnBalance.getProgress() == 50) {
						seekBarBtnBalance.textViewProgress.setText("0");
					} else {
						seekBarBtnBalance.textViewProgress.setText("R"
								+ (seekBarBtnBalance.getProgress() - 50));
					}
				}
			}
		};

		comboBtnSurround = new ComboButton(activity, activity.getResources()
				.getStringArray(R.array.str_arr_sound_surround_vals),
				R.id.linearlayout_sound_surround, 1, 2, true) {
			@Override
			public void doUpdate() {
				if (kSoundModel != null) {
					kSoundModel.K_setAudioSurroundMode(comboBtnSurround
							.getIdx());
				}
			}
		};

		comboBtnSpdifoutput = new ComboButton(activity, activity.getResources()
				.getStringArray(R.array.str_arr_sound_spdifoutput_vals),
				R.id.linearlayout_sound_spdifoutput, 1, 2, true) {
			@Override
			public void doUpdate() {
				// TODO Auto-generated method stub
				if (kSoundModel != null) {
					kSoundModel.K_setAudioSpdifOutMode(comboBtnSpdifoutput
							.getIdx());
				}
			}
		};

		comboBtnSpdifoutput.setItemEnable(1, false);
		// nathan.liao 2015.03.03 add for T4C1 board start
		if (PropHelp.newInstance().isHasT4C1Board()) {
			comboBtnSpdifoutput.setVisibility(false);
		} else {
			comboBtnSpdifoutput.setVisibility(true);
		}
		// nathan.liao 2015.03.03 add for T4C1 board end
		btnSeperateHear = new MyButton(activity,
				R.id.linearlayout_sound_separatehear);
		btnSeperateHear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(activity, SeperateHearActivity.class);
				activity.startActivity(intent);
				// activity.finish();
			}
		});
		setOnClickListeners();
		setOnFocusChangeListeners();
		comboBtnSoundmode.setFocused();
	}

	private void setOnClickListeners() {
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
		comboBtnSoundmode.setOnClickListener(comoBtnOnClickListener);
		comboBtnSurround.setOnClickListener(comoBtnOnClickListener);
		comboBtnSpdifoutput.setOnClickListener(comoBtnOnClickListener);
		OnClickListener seekBarBtnOnClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
		
		OnClickListener seekBarBtnEq120HzOnClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
		
		OnClickListener seekBarBtnEq500HzOnClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
		
		OnClickListener seekBarBtn1500HzOnClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
		
		OnClickListener seekBarBtn5KHzOnClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
		
		OnClickListener seekBarBtn10KHzOnClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
/*		seekBarBtnBass.setOnClickListener(seekBarBtnOnClickListener);
		seekBarBtnTreble.setOnClickListener(seekBarBtnOnClickListener);*/
		seekBarBtnBalance.setOnClickListener(seekBarBtnOnClickListener);
		
		seekBarBtnEq120Hz.setOnClickListener(seekBarBtnEq120HzOnClickListener);
		seekBarBtnEq500Hz.setOnClickListener(seekBarBtnEq500HzOnClickListener);
		seekBarBtnEq1500Hz.setOnClickListener(seekBarBtn1500HzOnClickListener);
		seekBarBtnEq5KHz.setOnClickListener(seekBarBtn5KHzOnClickListener);
		seekBarBtnEq10KHz.setOnClickListener(seekBarBtn10KHzOnClickListener);
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
		comboBtnSoundmode.setOnFocusChangeListener(comboBtnFocusListener);
		comboBtnSurround.setOnFocusChangeListener(comboBtnFocusListener);
		comboBtnSpdifoutput.setOnFocusChangeListener(comboBtnFocusListener);
		OnFocusChangeListener seekBarBtnFocusListenser = new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				LinearLayout container = (LinearLayout) v;
				container.getChildAt(1).setVisibility(View.INVISIBLE);
				setHelpTextSelect(false);
			}
		};
		
		OnFocusChangeListener seekBarBtnEq120HzFocusListenser = new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				LinearLayout container = (LinearLayout) v;
				container.getChildAt(1).setVisibility(View.INVISIBLE);
				setHelpTextSelect(false);
			}
		};
		
		OnFocusChangeListener seekBarBtnEq500HzFocusListenser = new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				LinearLayout container = (LinearLayout) v;
				container.getChildAt(1).setVisibility(View.INVISIBLE);
				setHelpTextSelect(false);
			}
		};
		
		OnFocusChangeListener seekBarBtnEq1500HzFocusListenser = new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				LinearLayout container = (LinearLayout) v;
				container.getChildAt(1).setVisibility(View.INVISIBLE);
				setHelpTextSelect(false);
			}
		};
		
		OnFocusChangeListener seekBarBtnEq5KHzFocusListenser = new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				LinearLayout container = (LinearLayout) v;
				container.getChildAt(1).setVisibility(View.INVISIBLE);
				setHelpTextSelect(false);
			}
		};
		
		OnFocusChangeListener seekBarBtnEq10KHzFocusListenser = new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				LinearLayout container = (LinearLayout) v;
				container.getChildAt(1).setVisibility(View.INVISIBLE);
				setHelpTextSelect(false);
			}
		};
/*		seekBarBtnBass.setOnFocusChangeListener(seekBarBtnFocusListenser);
		seekBarBtnTreble.setOnFocusChangeListener(seekBarBtnFocusListenser);
*/		seekBarBtnBalance.setOnFocusChangeListener(seekBarBtnFocusListenser);
		
		seekBarBtnEq120Hz.setOnFocusChangeListener(seekBarBtnEq120HzFocusListenser);
		seekBarBtnEq500Hz.setOnFocusChangeListener(seekBarBtnEq500HzFocusListenser);
		seekBarBtnEq1500Hz.setOnFocusChangeListener(seekBarBtnEq1500HzFocusListenser);
		seekBarBtnEq5KHz.setOnFocusChangeListener(seekBarBtnEq5KHzFocusListenser);
		seekBarBtnEq10KHz.setOnFocusChangeListener(seekBarBtnEq10KHzFocusListenser);
	}

	private void freshDataToUIWhenSoundModChange() {
		if (kSoundModel != null) {
/*			seekBarBtnBass.setProgress((short) kSoundModel.K_getBass());
			seekBarBtnTreble.setProgress((short) kSoundModel.K_getTreble());
*/			
			seekBarBtnEq120Hz.setProgress((short) kSoundModel.K_getEqBand120());
			seekBarBtnEq500Hz.setProgress((short) kSoundModel.K_getEqBand500());
			seekBarBtnEq1500Hz.setProgress((short) kSoundModel.K_getEqBand1500());
			seekBarBtnEq5KHz.setProgress((short) kSoundModel.K_getEqBand5k());
			seekBarBtnEq10KHz.setProgress((short) kSoundModel.K_getEqBand10k());
		}
	}

	public void LoadDataToUI() {
		if (kSoundModel != null) {
			// Refine performance with query DB by content provider to reduce
			// startup time in sound page.
			comboBtnSoundmode.setIdx(kSoundModel.K_getAudioSoundMode());
			seekBarBtnBalance.setProgress((short) kSoundModel.K_getBalance());
			comboBtnSurround.setIdx(kSoundModel.K_getAudioSurroundMode());
/*			seekBarBtnBass.setProgress((short) kSoundModel.K_getBass());
			seekBarBtnTreble.setProgress((short) kSoundModel.K_getTreble());
*/			
			seekBarBtnEq120Hz.setProgress((short) kSoundModel.K_getEqBand120());
			seekBarBtnEq500Hz.setProgress((short) kSoundModel.K_getEqBand500());
			seekBarBtnEq1500Hz.setProgress((short) kSoundModel.K_getEqBand1500());
			seekBarBtnEq5KHz.setProgress((short) kSoundModel.K_getEqBand5k());
			seekBarBtnEq10KHz.setProgress((short) kSoundModel.K_getEqBand10k());

			comboBtnSpdifoutput.setIdx(kSoundModel.K_getAudioSpdifOutMode());

			if (seekBarBtnBalance.getProgress() < 50) {
				seekBarBtnBalance.textViewProgress.setText("L"
						+ (50 - seekBarBtnBalance.getProgress()));
			} else if (seekBarBtnBalance.getProgress() == 50) {
				seekBarBtnBalance.textViewProgress.setText("0");
			} else {
				seekBarBtnBalance.textViewProgress.setText("R"
						+ (seekBarBtnBalance.getProgress() - 50));
			}
		}
		SetFocusableOrNotForUserMode();
	}

	private void SetFocusableOrNotForUserMode() {
		if (comboBtnSoundmode.getIdx() != 3) {
/*			seekBarBtnBass.setEnable(false);
			seekBarBtnBass.setFocusable(false);
			seekBarBtnTreble.setEnable(false);
			seekBarBtnTreble.setFocusable(false);
*/			
			seekBarBtnEq120Hz.setEnable(false);
			seekBarBtnEq120Hz.setFocusable(false);
			seekBarBtnEq500Hz.setEnable(false);
			seekBarBtnEq500Hz.setFocusable(false);
			seekBarBtnEq1500Hz.setEnable(false);
			seekBarBtnEq1500Hz.setFocusable(false);
			seekBarBtnEq5KHz.setEnable(false);
			seekBarBtnEq5KHz.setFocusable(false);
			seekBarBtnEq10KHz.setEnable(false);
			seekBarBtnEq10KHz.setFocusable(false);
		} else {
/*			seekBarBtnBass.setEnable(true);
			seekBarBtnBass.setFocusable(true);
			seekBarBtnTreble.setEnable(true);
			seekBarBtnTreble.setFocusable(true);
*/			
			seekBarBtnEq120Hz.setEnable(true);
			seekBarBtnEq120Hz.setFocusable(true);
			seekBarBtnEq500Hz.setEnable(true);
			seekBarBtnEq500Hz.setFocusable(true);
			seekBarBtnEq1500Hz.setEnable(true);
			seekBarBtnEq1500Hz.setFocusable(true);
			seekBarBtnEq5KHz.setEnable(true);
			seekBarBtnEq5KHz.setFocusable(true);
			seekBarBtnEq10KHz.setEnable(true);
			seekBarBtnEq10KHz.setFocusable(true);
		}
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
		int currentId = activity.getCurrentFocus().getId();
		switch (keyCode) {
		case KeyEvent.KEYCODE_TV_INPUT:
			switch (currentId) {
			case R.id.linearlayout_sound_soundmode:
			case R.id.linearlayout_sound_surround:
			case R.id.linearlayout_sound_spdifoutput:
				View v = (LinearLayout) activity.findViewById(currentId);
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
/*			case R.id.linearlayout_sound_treble:
			case R.id.linearlayout_sound_bass:*/
			case R.id.linearlayout_sound_balance:
			case R.id.linearlayout_sound_eq_120hz:
			case R.id.linearlayout_sound_eq_500hz:
			case R.id.linearlayout_sound_eq_1500hz:
			case R.id.linearlayout_sound_eq_5000hz:
			case R.id.linearlayout_sound_eq_10000hz:
				View v2 = (LinearLayout) activity.findViewById(currentId);
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
			case R.id.linearlayout_sound_separatehear:
				Intent intent = new Intent(activity, SeperateHearActivity.class);
				activity.startActivity(intent);
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
