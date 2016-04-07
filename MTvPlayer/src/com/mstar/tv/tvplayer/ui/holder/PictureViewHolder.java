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
import android.app.ActivityManagerNative;
import android.app.IActivityManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;

import com.kguan.mtvplay.tvapi.K_Constants;
import com.kguan.mtvplay.tvapi.K_TvPictureManager;
import com.kguan.mtvplay.tvapi.K_TvCommonManager;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.component.ComboButton;
import com.mstar.tv.tvplayer.ui.component.MyButton;
import com.mstar.tv.tvplayer.ui.component.SeekBarButton;
import com.mstar.tv.tvplayer.ui.settings.PCImageModeDialogActivity;

public class PictureViewHolder {

	private K_TvPictureManager kPictureModel;
	
	private K_TvCommonManager kTvCommonModel;

	private int mArcType = K_Constants.VIDEO_ARC_MAX;

	protected static final int STEP = 1;

	protected static final String TAG = "PictureViewHolder";

	private int mPictureMode = K_Constants.PICTURE_MODE_NORMAL;

	protected ProgressDialog progressDialog;

	protected ComboButton comboBtnPictureMode;

	protected SeekBarButton seekBtnContrast;

	protected SeekBarButton seekBtnBrightness;

	protected SeekBarButton seekBtnSharpness;

	protected SeekBarButton seekBtnSaturation;

	protected SeekBarButton seekBtnHue;

	protected ComboButton comboBtnColorTemperature;

	protected ComboButton comboBtnZoomMode;

	protected ComboButton comboBtnImageNoiseReduction;

	protected MyButton myButtonPcImageMode;

	protected Activity activity;

	private TextView enterText;
	private TextView switchText;

	public PictureViewHolder(Activity act) {
		this.activity = act;
		kPictureModel = K_TvPictureManager.getInstance();
		kTvCommonModel = K_TvCommonManager.getInstance();
		IActivityManager am = ActivityManagerNative.getDefault();
	}

	public void findViews() {
		enterText = (TextView) activity
				.findViewById(R.id.enter_value_text_picture);
		switchText = (TextView) activity
				.findViewById(R.id.switch_value_text_picture);
		seekBtnContrast = new SeekBarButton(activity,
				R.id.linearlayout_pic_contrast, STEP, true) {
			@Override
			public void doUpdate() {
				if (kPictureModel != null) {
					kPictureModel.K_setVideoItem(K_Constants.PICTURE_CONTRAST,
							(int) seekBtnContrast.getProgress());
				}
			}
		};
		seekBtnBrightness = new SeekBarButton(activity,
				R.id.linearlayout_pic_brightness, STEP, true) {
			@Override
			public void doUpdate() {
				if (kPictureModel != null) {
					kPictureModel.K_setVideoItem(
							K_Constants.PICTURE_BRIGHTNESS,
							(int) seekBtnBrightness.getProgress());
				}
			}
		};
		seekBtnSharpness = new SeekBarButton(activity,
				R.id.linearlayout_pic_sharpness, STEP, true) {
			@Override
			public void doUpdate() {
				if (kPictureModel != null) {
					kPictureModel.K_setVideoItem(K_Constants.PICTURE_SHARPNESS,
							(int) seekBtnSharpness.getProgress());
				}
			}
		};
		seekBtnHue = new SeekBarButton(activity, R.id.linearlayout_pic_hue,
				STEP, true) {
			@Override
			public void doUpdate() {
				if (kPictureModel != null) {
					kPictureModel.K_setVideoItem(K_Constants.PICTURE_HUE,
							(int) seekBtnHue.getProgress());
				}
			}
		};
		seekBtnSaturation = new SeekBarButton(activity,
				R.id.linearlayout_pic_saturation, STEP, true) {
			@Override
			public void doUpdate() {
				if (kPictureModel != null) {
					kPictureModel.K_setVideoItem(
							K_Constants.PICTURE_SATURATION,
							(int) seekBtnSaturation.getProgress());
				}
			}
		};

		comboBtnPictureMode = new ComboButton(activity,activity
				.getResources().getStringArray(
						R.array.str_arr_pic_picturemode_vals) ,
				R.id.linearlayout_pic_picturemode, 1, 2, true) {
			@Override
			public void doUpdate() {
				int specifyPicMode = K_Constants.PICTURE_MODE_NORMAL;
				if (kPictureModel != null) {
					specifyPicMode = comboBtnPictureMode.getIdx();
					kPictureModel
							.K_setPictureMode(comboBtnPictureMode.getIdx());
					comboBtnZoomMode.setIdx(mArcType);
					Log.i(TAG, "mPictureMode = " + mPictureMode);
					Log.i(TAG, "specifyPicMode = " + specifyPicMode);
					Log.i(TAG, "mArcType = " + mArcType);
					if (mPictureMode == K_Constants.PICTURE_MODE_GAME
							|| mPictureMode == K_Constants.PICTURE_MODE_AUTO
							|| mPictureMode == K_Constants.PICTURE_MODE_PC
							|| specifyPicMode == K_Constants.PICTURE_MODE_GAME
							|| specifyPicMode == K_Constants.PICTURE_MODE_AUTO
							|| specifyPicMode == K_Constants.PICTURE_MODE_PC) {
						kPictureModel.K_setVideoArcType(mArcType);
					}
					mPictureMode = specifyPicMode;
				}
				freshDataToUIWhenPicModChange();
				SetFocusableForUserMode();
			}
		};
		comboBtnColorTemperature = new ComboButton(activity, activity
				.getResources().getStringArray(
						R.array.str_arr_pic_colortemperature_vals),
				R.id.linearlayout_pic_colortemperature, 1, 2, true) {
			@Override
			public void doUpdate() {
				if (kPictureModel != null) {
					kPictureModel.K_setColorTemprature(comboBtnColorTemperature
							.getIdx());
				}
				freshDataToUIWhenColorTmpChange();
			}
		};
		comboBtnZoomMode = new ComboButton(activity,activity
				.getResources().getStringArray(
						R.array.str_arr_pic_zoommode_vals) ,
				R.id.linearlayout_pic_zoommode, 1, 2, true) {
			@Override
			public void doUpdate() {
				if (kPictureModel != null) {
					kPictureModel.K_setVideoArcType(comboBtnZoomMode.getIdx());
				}
			}
		};
		comboBtnImageNoiseReduction = new ComboButton(activity, activity
				.getResources().getStringArray(
						R.array.str_arr_pic_imgnoisereduction_vals),
				R.id.linearlayout_pic_imgnoisereduction, 1, 2, true) {
			@Override
			public void doUpdate() {
				if (kPictureModel != null) {
					kPictureModel
							.K_setNoiseReduction(comboBtnImageNoiseReduction
									.getIdx());
				}
			}
		};
		comboBtnImageNoiseReduction.setItemEnable(4, false);

		myButtonPcImageMode = new MyButton(activity,
				R.id.linearlayout_pic_pcimagemode);
		myButtonPcImageMode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(activity,
						PCImageModeDialogActivity.class);
				activity.startActivity(intent);
				activity.finish();
			}
		});

		LinearLayout pccontainer = (LinearLayout) activity
				.findViewById(R.id.linearlayout_pic_pcimagemode);
		TextView pctextview = (TextView) (pccontainer.getChildAt(0));
		if (kPictureModel != null) {
			if (kTvCommonModel.K_getCurrentTvInputSource() == K_Constants.INPUT_SOURCE_VGA) {
				pccontainer.setVisibility(View.VISIBLE);
				/*pccontainer.setFocusable(false);
				pccontainer.setEnabled(false);
				pctextview.setTextColor(Color.GRAY);*/
				LinearLayout pic_picturemode = (LinearLayout) activity
	                      .findViewById(R.id.linearlayout_pic_picturemode);
					pic_picturemode.setNextFocusUpId(R.id.linearlayout_pic_pcimagemode);
			} else {
				pccontainer.setVisibility(View.GONE);
				/*pccontainer.setFocusable(true);
				pccontainer.setEnabled(true);
				pctextview.setTextColor(Color.WHITE);*/
				LinearLayout imgnoise = (LinearLayout) activity
                        .findViewById(R.id.linearlayout_pic_imgnoisereduction);
                imgnoise.setNextFocusDownId(R.id.linearlayout_pic_picturemode);
			}
		}
		initPictureMode();
		setOnFocusChangeListeners();
		setOnClickListeners();
		comboBtnPictureMode.setFocused();
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
		comboBtnPictureMode.setOnClickListener(comoBtnOnClickListener);
		comboBtnColorTemperature.setOnClickListener(comoBtnOnClickListener);
		comboBtnZoomMode.setOnClickListener(comoBtnOnClickListener);
		comboBtnImageNoiseReduction.setOnClickListener(comoBtnOnClickListener);
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
		seekBtnContrast.setOnClickListener(seekBarBtnOnClickListener);
		seekBtnBrightness.setOnClickListener(seekBarBtnOnClickListener);
		seekBtnSharpness.setOnClickListener(seekBarBtnOnClickListener);
		seekBtnSaturation.setOnClickListener(seekBarBtnOnClickListener);
		seekBtnHue.setOnClickListener(seekBarBtnOnClickListener);
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
		comboBtnPictureMode.setOnFocusChangeListener(comboBtnFocusListener);
		comboBtnColorTemperature
				.setOnFocusChangeListener(comboBtnFocusListener);
		comboBtnZoomMode.setOnFocusChangeListener(comboBtnFocusListener);
		comboBtnImageNoiseReduction
				.setOnFocusChangeListener(comboBtnFocusListener);
		OnFocusChangeListener seekBarBtnFocusListenser = new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				LinearLayout container = (LinearLayout) v;
				container.getChildAt(1).setVisibility(View.INVISIBLE);
				setHelpTextSelect(false);
			}
		};
		seekBtnContrast.setOnFocusChangeListener(seekBarBtnFocusListenser);
		seekBtnBrightness.setOnFocusChangeListener(seekBarBtnFocusListenser);
		seekBtnSharpness.setOnFocusChangeListener(seekBarBtnFocusListenser);
		seekBtnSaturation.setOnFocusChangeListener(seekBarBtnFocusListenser);
		seekBtnHue.setOnFocusChangeListener(seekBarBtnFocusListenser);
	}

	public void LoadDataToUI() {
		if (kPictureModel != null) {
			mPictureMode = kPictureModel.K_getPictureMode();
			comboBtnPictureMode.setIdx(mPictureMode);
			// Refine performance with query DB by content provider to reduce
			// startup time in picture page.

			seekBtnContrast.setProgress((short) kPictureModel
					.K_getVideoItem(K_Constants.PICTURE_CONTRAST));
			seekBtnBrightness.setProgress((short) kPictureModel
					.K_getVideoItem(K_Constants.PICTURE_BRIGHTNESS));
			seekBtnSharpness.setProgress((short) kPictureModel
					.K_getVideoItem(K_Constants.PICTURE_SHARPNESS));
			seekBtnHue.setProgress((short) kPictureModel
					.K_getVideoItem(K_Constants.PICTURE_HUE));
			seekBtnSaturation.setProgress((short) kPictureModel
					.K_getVideoItem(K_Constants.PICTURE_SATURATION));
			short colorTempIdx = getColorTemperatureSetting();
			comboBtnColorTemperature.setIdx(colorTempIdx);
			comboBtnImageNoiseReduction.setIdx(kPictureModel
					.K_getNoiseReduction());
			comboBtnZoomMode.setIdx(kPictureModel.K_getVideoArcType());
		}
		SetFocusableForUserMode();
	}

	private short getColorTemperatureSetting() {
		short colorTempIdx = 0;
		colorTempIdx = (short)kPictureModel.K_getColorTemprature();
		return colorTempIdx;
	}

	private void updateBtnColorTemperature() {
		comboBtnColorTemperature.setIdx(getColorTemperatureSetting());
		freshDataToUIWhenColorTmpChange();
	}

	private void freshDataToUIWhenPicModChange() {
		if (kPictureModel != null) {
			seekBtnContrast.setProgress((short) kPictureModel
					.K_getVideoItem(K_Constants.PICTURE_CONTRAST));
			seekBtnBrightness.setProgress((short) kPictureModel
					.K_getVideoItem(K_Constants.PICTURE_BRIGHTNESS));
			seekBtnSharpness.setProgress((short) kPictureModel
					.K_getVideoItem(K_Constants.PICTURE_SHARPNESS));
			seekBtnHue.setProgress((short) kPictureModel
					.K_getVideoItem(K_Constants.PICTURE_HUE));
			seekBtnSaturation.setProgress((short) kPictureModel
					.K_getVideoItem(K_Constants.PICTURE_SATURATION));
			comboBtnImageNoiseReduction.setIdx(kPictureModel
					.K_getNoiseReduction());
			comboBtnZoomMode.setIdx(kPictureModel.K_getVideoArcType());

			updateBtnColorTemperature();
		}
	}

	private void initPictureMode() {
		if (kPictureModel != null) {
			comboBtnZoomMode.setItemEnable(K_Constants.PICTURE_VIDEO_ARC_AUTO, false);
			int inputSrc = kTvCommonModel.K_getCurrentTvInputSource();
			if (inputSrc == K_Constants.INPUT_SOURCE_HDMI
					|| inputSrc == K_Constants.INPUT_SOURCE_HDMI2
					|| inputSrc == K_Constants.INPUT_SOURCE_HDMI3
					|| inputSrc == K_Constants.INPUT_SOURCE_HDMI4) {
				try {
					if (kTvCommonModel.K_isHdmiSignalMode() == true
							&& (kPictureModel.K_getvResolution() == 1080
									|| kPictureModel.K_getvResolution() == 720
									|| kPictureModel.K_getvResolution() == 480 || kPictureModel
									.K_getvResolution() == 576)
							&& kPictureModel.K_getScanType() != K_Constants.E_PROGRESSIVE) {
						comboBtnZoomMode.setItemEnable(K_Constants.PICTURE_VIDEO_ARC_DOTBYDOT, false);
					} else {
						comboBtnZoomMode.setItemEnable(K_Constants.PICTURE_VIDEO_ARC_DOTBYDOT, true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				comboBtnZoomMode.setItemEnable(K_Constants.PICTURE_VIDEO_ARC_DOTBYDOT, false);
			}
			if (inputSrc == K_Constants.INPUT_SOURCE_VGA || isSourceDVI()) {

				comboBtnZoomMode.setItemEnable(K_Constants.PICTURE_VIDEO_ARC_DOTBYDOT, false);
				comboBtnZoomMode.setItemEnable(K_Constants.PICTURE_VIDEO_ARC_ZOOM1, false);
				comboBtnZoomMode.setItemEnable(K_Constants.PICTURE_VIDEO_ARC_ZOOM2, false);
			}

			if (kTvCommonModel.K_isSignalStable(inputSrc))// michael_ktc
			{
				comboBtnZoomMode.setEnable(true);
			} else {
				comboBtnZoomMode.setEnable(false);
			}
		}
	}

	private void freshDataToUIWhenColorTmpChange() {

		if (kPictureModel != null) {
			comboBtnImageNoiseReduction.setIdx(kPictureModel
					.K_getNoiseReduction());
		}
	}

	private void SetFocusableForUserMode() {
		if (comboBtnPictureMode.getIdx() != 3) {
			seekBtnContrast.setEnable(false);
			seekBtnBrightness.setEnable(false);
			seekBtnSharpness.setEnable(false);
			seekBtnHue.setEnable(false);
			seekBtnSaturation.setEnable(false);
			seekBtnContrast.setFocusable(false);
			seekBtnBrightness.setFocusable(false);
			seekBtnSharpness.setFocusable(false);
			seekBtnHue.setFocusable(false);
			seekBtnSaturation.setFocusable(false);
		} else {
			seekBtnContrast.setEnable(true);
			seekBtnBrightness.setEnable(true);
			seekBtnSharpness.setEnable(true);
			seekBtnHue.setEnable(true);
			seekBtnSaturation.setEnable(true);
			seekBtnContrast.setFocusable(true);
			seekBtnBrightness.setFocusable(true);
			seekBtnSharpness.setFocusable(true);
			seekBtnHue.setFocusable(true);
			seekBtnSaturation.setFocusable(true);
			int currentInputSource = kTvCommonModel.K_getCurrentTvInputSource();
			if (currentInputSource == K_Constants.INPUT_SOURCE_DTV
					|| currentInputSource == K_Constants.INPUT_SOURCE_HDMI
					|| currentInputSource == K_Constants.INPUT_SOURCE_HDMI2
					|| currentInputSource == K_Constants.INPUT_SOURCE_HDMI3
					|| currentInputSource == K_Constants.INPUT_SOURCE_HDMI4) {
				seekBtnHue.setEnable(false);
				seekBtnHue.setFocusable(false);
				// 分量下色调不可调
			} else if (currentInputSource == K_Constants.INPUT_SOURCE_YPBPR
					|| currentInputSource == K_Constants.INPUT_SOURCE_YPBPR2
					|| currentInputSource == K_Constants.INPUT_SOURCE_YPBPR3) {
				seekBtnHue.setEnable(false);
				seekBtnHue.setFocusable(false);
				// VAG下色调、清晰度、饱和度不可调
			} else if (currentInputSource == K_Constants.INPUT_SOURCE_VGA) {
				seekBtnHue.setEnable(false);
				seekBtnSharpness.setEnable(false);
				seekBtnSaturation.setEnable(false);
				seekBtnHue.setFocusable(false);
				seekBtnSharpness.setFocusable(false);
				seekBtnSaturation.setFocusable(false);
				comboBtnImageNoiseReduction.setEnable(false);
				// ATV 和 AV 下NTSC制式色调可调
			} else if (currentInputSource == K_Constants.INPUT_SOURCE_ATV
					|| currentInputSource == K_Constants.INPUT_SOURCE_CVBS
					|| currentInputSource == K_Constants.INPUT_SOURCE_CVBS2
					|| currentInputSource == K_Constants.INPUT_SOURCE_CVBS3
					|| currentInputSource == K_Constants.INPUT_SOURCE_CVBS4
					|| currentInputSource == K_Constants.INPUT_SOURCE_CVBS5
					|| currentInputSource == K_Constants.INPUT_SOURCE_CVBS6
					|| currentInputSource == K_Constants.INPUT_SOURCE_CVBS7
					|| currentInputSource == K_Constants.INPUT_SOURCE_CVBS8) {
				if (kPictureModel.K_getVideoStandard() != null) {
					if (kPictureModel.K_getVideoStandard() == K_Constants.NTSC_44
							|| kPictureModel.K_getVideoStandard() == K_Constants.NTSC_M) {
						seekBtnHue.setEnable(true);
						seekBtnHue.setFocusable(true);
					} else {
						seekBtnHue.setEnable(false);
						seekBtnHue.setFocusable(false);
						seekBtnHue.setProgress((short) 50);
					}
				}
			}
		}
	}

	private boolean isSourceDVI() {
		if (kPictureModel != null) {
			int curInputSource = kTvCommonModel.K_getCurrentTvInputSource();
			if (curInputSource >= K_Constants.INPUT_SOURCE_DVI
					&& curInputSource < K_Constants.INPUT_SOURCE_DVI_MAX) {
				return true;
			} else if (curInputSource >= K_Constants.INPUT_SOURCE_HDMI
					&& curInputSource < K_Constants.INPUT_SOURCE_HDMI_MAX) {
				if (kTvCommonModel.K_isHdmiSignalMode()) {
					return false;
				} else {
					return true;
				}
			}
		}
		return false;
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
			case R.id.linearlayout_pic_picturemode:
			case R.id.linearlayout_pic_colortemperature:
			case R.id.linearlayout_pic_zoommode:
			case R.id.linearlayout_pic_imgnoisereduction:
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
			case R.id.linearlayout_pic_contrast:
			case R.id.linearlayout_pic_brightness:
			case R.id.linearlayout_pic_hue:
			case R.id.linearlayout_pic_saturation:
			case R.id.linearlayout_pic_sharpness:
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
			case R.id.linearlayout_pic_pcimagemode:
				Intent intent = new Intent(activity,
						PCImageModeDialogActivity.class);
				activity.startActivity(intent);
				activity.finish();
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
